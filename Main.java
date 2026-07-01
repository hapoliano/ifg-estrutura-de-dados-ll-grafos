import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Grafo grafo = new Grafo();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro(scanner);
            if (opcao == -1) {
                System.out.println("Opção inválida. Digite um número do menu.");
                continue;
            }

            switch (opcao) {
                case 1 -> cadastrarCidade(grafo, scanner);
                case 2 -> cadastrarRota(grafo, scanner);
                case 3 -> grafo.listarCidades();
                case 4 -> grafo.listarRotas();
                case 5 -> verificarRota(grafo, scanner, true);
                case 6 -> verificarRota(grafo, scanner, false);
                case 0 -> System.out.println("Encerrando o sistema.");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    // Lê um inteiro do scanner; retorna -1 se a entrada não for numérica
    private static int lerInteiro(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Exibe as opções do menu principal
    private static void exibirMenu() {
        System.out.println("\n=== Sistema de Rotas Aéreas ===");
        System.out.println("1. Cadastrar cidade");
        System.out.println("2. Cadastrar rota");
        System.out.println("3. Listar cidades");
        System.out.println("4. Listar rotas");
        System.out.println("5. Verificar rota (Busca em Largura)");
        System.out.println("6. Verificar rota (Busca em Profundidade)");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // Lê nome e país e adiciona a cidade ao grafo
    private static void cadastrarCidade(Grafo grafo, Scanner scanner) {
        System.out.print("Nome da cidade: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Estado: ");
        String estado = scanner.nextLine().trim();
        grafo.adicionarCidade(new Cidade(nome, estado));
    }

    // Exibe lista numerada de todas as cidades e retorna a escolhida, ou null se entrada inválida
    private static String escolherCidade(List<String> cidades, Scanner scanner) {
        for (int i = 0; i < cidades.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + cidades.get(i));
        }
        System.out.print("Opção: ");
        int idx = lerInteiro(scanner) - 1;
        if (idx < 0 || idx >= cidades.size()) {
            System.out.println("Opção inválida. Retornando ao menu.");
            return null;
        }
        return cidades.get(idx);
    }

    // Exibe as cidades disponíveis e adiciona a rota ao grafo
    private static void cadastrarRota(Grafo grafo, Scanner scanner) {
        List<String> cidades = grafo.getNomesCidades();
        if (cidades.size() < 2) {
            System.out.println("Cadastre pelo menos 2 cidades antes de adicionar uma rota.");
            return;
        }

        System.out.println("Selecione a cidade de origem:");
        String origem = escolherCidade(cidades, scanner);
        if (origem == null) return;

        System.out.println("Selecione a cidade de destino:");
        String destino = escolherCidade(cidades, scanner);
        if (destino == null) return;

        grafo.adicionarRota(origem, destino);
    }

    // Exibe as cidades disponíveis e verifica se existe rota entre as escolhidas
    private static void verificarRota(Grafo grafo, Scanner scanner, boolean usarBFS) {
        List<String> cidades = grafo.getNomesCidades();
        if (cidades.size() < 2) {
            System.out.println("Cadastre pelo menos 2 cidades antes de verificar uma rota.");
            return;
        }

        System.out.println("Selecione a cidade de origem:");
        String origem = escolherCidade(cidades, scanner);
        if (origem == null) return;

        System.out.println("Selecione a cidade de destino:");
        String destino = escolherCidade(cidades, scanner);
        if (destino == null) return;

        boolean resultado = usarBFS
                ? grafo.existeRotaBuscaEmLargura(origem, destino)
                : grafo.existeRotaBuscaEmProfundidade(origem, destino);
        System.out.println("Existe rota de \"" + origem + "\" para \"" + destino + "\"? "
                + (resultado ? "SIM" : "NÃO"));
    }
}
