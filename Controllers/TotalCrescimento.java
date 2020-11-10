package Controllers;
import Models.*;
import java.util.List;

/**
 * @author Filipe Souza, Gabriel Ottoboni, João Pedro Silva
 *
 *<h1>
 *     Subclasse de Estatística que representa a taxa de
 *     crescimento dos dados para o projeto final.
 *</h1>
 */
public class TotalCrescimento extends Estatistica {

    /**
     * Construtor da Taxa de Crescimento
     * <p>
     *     Cria um List de medições para guardar
     *     as diversas informações dos países.
     * </p>
     */
    public TotalCrescimento(String nome, List<Medicao> observacoes){
        super(nome, observacoes);
    }

    /**
     * Cálculo da diferença entre o dado seguinte e o anterior.
     * @param i A posição atual na lista dos dados.
     * @return A diferença dos dados.
     */
    @Override
    public float valor(int i) {
		if(this.getObservacoes().get(i).getCasos() == 0){
			return this.getObservacoes().get(i+1).getCasos() * 100;
		}
        float diferenca = this.getObservacoes().get(i+1).getCasos() -
                this.getObservacoes().get(i).getCasos() < 0 ? 0 :
                this.getObservacoes().get(i+1).getCasos() -
                        this.getObservacoes().get(i).getCasos();
        return (diferenca / this.getObservacoes().get(i).getCasos()) * 100;
    }
}
