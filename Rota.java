// Representa uma aresta do grafo — rota aérea direta entre duas cidades
public class Rota {

    private Cidade origem;
    private Cidade destino;

    public Rota(Cidade origem, Cidade destino) {
        this.origem = origem;
        this.destino = destino;
    }

    public Cidade getOrigem() {
        return origem;
    }

    public Cidade getDestino() {
        return destino;
    }

    // Exibe a rota no formato "Origem <-> Destino"
    @Override
    public String toString() {
        return origem.getNome() + " <-> " + destino.getNome();
    }
}
