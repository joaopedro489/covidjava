package Controllers;
import Models.*;
import java.util.List;
import java.time.LocalDateTime;

/**
 * @author Filipe Souza, Gabriel Ottoboni, João Pedro Silva
 *
 *<h1>
 *     Classe abstrata que lida com o cálculo dos dados para o projeto final.
 *</h1>
 */
public abstract class Estatistica {
    private String nome;
    private List<Medicao> observacoes;

    /**
     * Construtor das Estatísticas
     * <p>
     *     Cria um List de medições para guardar
     *     as diversas informações dos países.
     * </p>
     */
    public Estatistica(String nome, List<Medicao> observacoes) {
        this.nome = nome;
        this.observacoes = observacoes;
    }

    /**
     * Getter do nome da Estatística.
     * Retorna o nome da Estatística.
     * @return nome da Estatística.
     */
    public String getNome() {
       return nome;
    }

    /**
     * Setter do nome da Estatística.
     * Edita o nome da Estatística.
     * @param nome Nome da Estatística.
     */
    public void setNome(String nome) {
       this.nome = nome;
    }

    /**
     * Getter da lista de observações das medições.
     * Retorna a lista de observações das medições.
     * @return Observações das medições.
     */
    public List<Medicao> getObservacoes() {
       return observacoes;
    }

    /**
     * Setter da lista de observações das medições.
     * Edita a lista de observações das medições.
     * @param observacoes Observações das medições.
     */
    public void setObservacoes(List<Medicao> observacoes) {
       this.observacoes = observacoes;
    }

    /**
     * Adiciona uma nova medição na lista de medições.
     * @param observacao Uma medição.
     */
    public void inclui(Medicao observacao){
        this.observacoes.add(observacao);
    }

    /**
     * Pega a data de início da medição.
     * @return Data de início da medição.
     */
    public LocalDateTime dataInicio(){
        return this.observacoes.get(0).getMomento();
    }

    /**
     * Pega a data de término da medição.
     * @return Data de término da medição.
     */
    public LocalDateTime dataFinal(){
        return this.observacoes.get(1).getMomento();
    }

    /**
     * Cálculo da diferença entre o dado seguinte e o anterior.
     * @param i A posição atual na lista dos dados.
     * @return A diferença dos dados.
     */
    public float valor(int i) {
        return this.observacoes.get(i+1).getCasos() -
            this.observacoes.get(i).getCasos() < 0 ? 0 :
                this.observacoes.get(i+1).getCasos() -
                this.observacoes.get(i).getCasos();
    }

    /**
     * Transforma uma Estatistica em String no formato necessário
     * para ser salvo em um arquivo.
     * @return os dados de uma estatística em formato de string.
     */
    public String toString() {
        return this.nome + " " + this.observacoes.toString();
    }
}
