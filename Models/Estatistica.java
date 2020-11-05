package Models;
import java.io.Serializable;
public abstract class Estatistica implements Serializable, Model {
    private static final long serialVersionUID = 1000L;
    String nome;
    List<Medicao> observacoes;
    public static String toString(){
        return this.nome + "\t" + this.observacoes.toString() + "\n";
    }
}
