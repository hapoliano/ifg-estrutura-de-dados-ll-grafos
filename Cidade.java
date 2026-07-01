// Representa um vértice do grafo — uma cidade atendida pela companhia aérea
public class Cidade {

    private String nome;
    private String estado;

    public Cidade(String nome, String estado) {
        this.nome = nome;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public String getEstado() {
        return estado;
    }

    // Exibe a cidade no formato "Nome - Estado"
    @Override
    public String toString() {
        return nome + " - " + estado;
    }
}
