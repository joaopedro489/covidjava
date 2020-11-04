package Models;
import java.io.Serializable;
public class Estatistica implements Serializable {
    private static final long serialVersionUID = 1000L;
    String nome;
    List<Medicao> observacoes;
}
