import java.util.*;

public class Grafo {

    private Map<String, Cidade> cidades;
    private Map<String, List<Rota>> arestas; // lista de adjacência: cidade → suas rotas

    public Grafo() {
        this.cidades = new LinkedHashMap<>();
        this.arestas = new LinkedHashMap<>();
    }

    // Retorna os nomes de todas as cidades cadastradas
    public List<String> getNomesCidades() {
        return new ArrayList<>(cidades.keySet());
    }

    // Adiciona uma nova cidade ao grafo
    public void adicionarCidade(Cidade cidade) {
        String nome = cidade.getNome();
        if (cidades.containsKey(nome)) {
            System.out.println("Cidade já cadastrada: " + nome);
            return;
        }
        cidades.put(nome, cidade);
        arestas.put(nome, new ArrayList<>()); // inicializa lista de rotas vazia
        System.out.println("Cidade adicionada: " + cidade);
    }

    // Cria uma conexão bidirecional entre duas cidades
    public void adicionarRota(String cidadeOrigem, String cidadeDestino) {
        if (!cidades.containsKey(cidadeOrigem)) {
            System.out.println("Cidade não encontrada: " + cidadeOrigem);
            return;
        }
        if (!cidades.containsKey(cidadeDestino)) {
            System.out.println("Cidade não encontrada: " + cidadeDestino);
            return;
        }
        // grafo não direcionado: adiciona a rota nos dois sentidos
        arestas.get(cidadeOrigem).add(new Rota(cidades.get(cidadeOrigem), cidades.get(cidadeDestino)));
        arestas.get(cidadeDestino).add(new Rota(cidades.get(cidadeDestino), cidades.get(cidadeOrigem)));
        System.out.println("Rota adicionada: " + cidadeOrigem + " <-> " + cidadeDestino);
    }

    // Exibe todas as cidades cadastradas
    public void listarCidades() {
        System.out.println("\n=== Cidades Cadastradas ===");
        if (cidades.isEmpty()) {
            System.out.println("Nenhuma cidade cadastrada.");
            return;
        }
        int i = 1;
        for (Cidade cidade : cidades.values()) {
            System.out.println(i + ". " + cidade);
            i++;
        }
        System.out.println("Total: " + cidades.size() + " cidade(s)");
    }

    // Exibe todas as rotas disponíveis (sem duplicatas)
    public void listarRotas() {
        System.out.println("\n=== Rotas Disponíveis ===");
        Set<String> exibidas = new HashSet<>();
        for (List<Rota> rotas : arestas.values()) {
            for (Rota rota : rotas) {
                String nomeOrigem = rota.getOrigem().getNome();
                String nomeDestino = rota.getDestino().getNome();
                // chave ordenada evita exibir a mesma rota duas vezes
                String chave = nomeOrigem.compareTo(nomeDestino) < 0
                        ? nomeOrigem + "|" + nomeDestino
                        : nomeDestino + "|" + nomeOrigem;
                if (!exibidas.contains(chave)) {
                    System.out.println("  " + rota);
                    exibidas.add(chave);
                }
            }
        }
    }

    // Verifica se existe rota usando Busca em Largura
    public boolean existeRotaBuscaEmLargura(String cidadeOrigem, String cidadeDestino) {
        if (!validarCidades(cidadeOrigem, cidadeDestino)) return false;
        if (cidadeOrigem.equals(cidadeDestino)) return true;

        Set<String> visitados = new HashSet<>();
        Queue<String> fila = new LinkedList<>();

        fila.add(cidadeOrigem);
        visitados.add(cidadeOrigem);

        System.out.println("[Largura] Buscando rota: " + cidadeOrigem + " -> " + cidadeDestino);
        System.out.print("[Largura] Ordem de visita: ");

        while (!fila.isEmpty()) {
            String atual = fila.poll();
            System.out.print(atual + " ");

            if (atual.equals(cidadeDestino)) {
                System.out.println("\n[Largura] Destino encontrado!");
                return true;
            }

            for (Rota rota : arestas.get(atual)) {
                String vizinho = rota.getDestino().getNome();
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    fila.add(vizinho);
                }
            }
        }

        System.out.println("\n[Largura] Destino não alcançável.");
        return false;
    }

    // Verifica se existe rota usando Busca em Profundidade
    public boolean existeRotaBuscaEmProfundidade(String cidadeOrigem, String cidadeDestino) {
        if (!validarCidades(cidadeOrigem, cidadeDestino)) return false;
        if (cidadeOrigem.equals(cidadeDestino)) return true;

        Set<String> visitados = new HashSet<>();
        Deque<String> pilha = new LinkedList<>();

        pilha.push(cidadeOrigem);
        visitados.add(cidadeOrigem);

        System.out.println("[Profundidade] Buscando rota: " + cidadeOrigem + " -> " + cidadeDestino);
        System.out.print("[Profundidade] Ordem de visita: ");

        while (!pilha.isEmpty()) {
            String atual = pilha.pop();
            System.out.print(atual + " ");

            if (atual.equals(cidadeDestino)) {
                System.out.println("\n[Profundidade] Destino encontrado!");
                return true;
            }

            for (Rota rota : arestas.get(atual)) {
                String vizinho = rota.getDestino().getNome();
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    pilha.push(vizinho);
                }
            }
        }

        System.out.println("\n[Profundidade] Destino não alcançável.");
        return false;
    }

    private boolean validarCidades(String cidadeOrigem, String cidadeDestino) {
        if (!cidades.containsKey(cidadeOrigem)) {
            System.out.println("Cidade de origem não encontrada: " + cidadeOrigem);
            return false;
        }
        if (!cidades.containsKey(cidadeDestino)) {
            System.out.println("Cidade de destino não encontrada: " + cidadeDestino);
            return false;
        }
        return true;
    }
}
