package Controllers;
import Models.*;
import java.util.List;

/**
 * @author Filipe Souza, Gabriel Ottoboni, João Pedro Silva
 *
 *<h1>
 *     Subclasse de Estatística que representa o valor absoluto
 *     dos dados para o projeto final.
 *</h1>
 */
public class Total extends Estatistica {

    /**
     * Construtor do Valor Absoluto
     * <p>
     *     Cria um List de medições para guardar
     *     as diversas informações dos países.
     * </p>
     */
    public Total(String nome, List<Medicao> observacoes){
        super(nome, observacoes);
    }

}
