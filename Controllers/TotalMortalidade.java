package Controllers;
import Models.*;
import java.util.List;

/**
 * @author Filipe Souza, Gabriel Ottoboni, João Pedro Silva
 *
 *<h1>
 *     Subclasse de Estatística que representa o valor dos
 *     dados taxa de mortalidade para o projeto final.
 *</h1>
 */
public class TotalMortalidade extends Estatistica {

    /**
     * Construtor da Taxa de Mortalidade
     * <p>
     *     Cria um List de medições para guardar
     *     as diversas informações dos países.
     * </p>
     */
    public TotalMortalidade(String nome, List<Medicao> observacoes){
        super(nome, observacoes);
    }

}
