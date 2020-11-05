package Models;
import java.io.Serializable;
public abstract class Estatistica implements Serializable, Model {
    private static final long serialVersionUID = 1000L;
    String nome;
    List<Medicao> observacoes;

    public Estatistica(String nome, List<Medicao> observacoes) {
        this.nome = nome;
        this.observacoes = observacoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Medicao> getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(List<Medicao> observacoes) {
        this.observacoes = observacoes;
    }

    public static String toString() {
        return this.nome + "\t" + this.observacoes.toString() + "\n";
    }
}
