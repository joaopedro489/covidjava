package Models;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * @author Filipe Souza, Gabriel Ottoboni, João Pedro Silva
 *
 *<h1>
 *     Classe que lida com os dados de cada país relacionados
 *     à COVID para o projeto final.
 *</h1>
 */
public class Medicao implements Serializable {
    private static final long serialVersionUID = 1001L;
    public enum StatusCaso {
        CONFIRMADOS, RECUPERADOS, MORTOS;
    }
    private Pais pais;
    private LocalDateTime momento;
    private float casos;
    private StatusCaso status;

    /**
     * Construtor da classe Medição.
     *
     * @param pais instância da classe país.
     * @param momento data relacionada à medição.
     * @param casos número de casos.
     * @param status tipo de caso.
     */
    public Medicao(Pais pais, LocalDateTime momento, float casos, Models.Medicao.StatusCaso status) {
        this.pais = pais;
        this.momento = momento;
        this.casos = casos;
        this.status = status;
    }

    /**
     * Getter do país.
     * Retorna o país.
     * @return o país.
     */
    public Pais getPais() {
        return pais;
    }

    /**
     * Setter do país.
     * Edita o país.
     * @param pais o país que será editado.
     */
    public void setPais(Pais pais) {
        this.pais = pais;
    }

    /**
     * Getter do dia relacionado à medição.
     * Retorna do dia relacionado à medição.
     * @return do dia relacionado à medição.
     */
    public LocalDateTime getMomento() {
        return momento;
    }

    /**
     * Setter da data relacionada à medição.
     * Edita a data relacionada à medição.
     * @param momento a data relacionada à medição.
     */
    public void setMomento(LocalDateTime momento) {
        this.momento = momento;
    }

    /**
     * Getter dos casos.
     * Retorna dos casos.
     * @return dos casos.
     */
    public float getCasos() {
        return casos;
    }

    /**
     * Setter do número de casos.
     * Edita o número de casos.
     * @param casos o número de casos que será editado.
     */
    public void setCasos(float casos) {
        this.casos = casos;
    }

    /**
     * Getter do tipo de caso.
     * Retorna o tipo de caso.
     * @return o tipo de caso.
     */
    public Models.Medicao.StatusCaso getStatus() {
        return status;
    }

    /**
     * Setter do tipo de casos.
     * Edita o tipos de casos.
     * @param status o tipo de casos que será editado.
     */
    public void setStatus(Models.Medicao.StatusCaso status) {
        this.status = status;
    }

    /**
     * Transforma uma Medição em String no formato necessário
     * para ser salvo em um arquivo.
     * @return os dados de uma medição em formato de string.
     */
    public String toString(){
        return this.pais.getNome() + "\t" + this.pais.getSlug() + "\t" + this.momento
            + "\t" + this.casos + "\t" + this.status + "\n";
    }

}
