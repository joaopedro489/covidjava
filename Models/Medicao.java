package Models;

import java.time.LocalDateTime;
import java.io.Serializable;

public class Medicao extends Model implements Serializable {
    private static final long serialVersionUID = 1001L;
    public enum StatusCaso {
        CONFIRMADOS,RECUPERADOS, MORTOS;
    }
    private Pais pais;
    private LocalDateTime momento;
    private int casos;
    private StatusCaso status;

    public Medicao(Pais pais, LocalDateTime momento, int casos, Models.Medicao.StatusCaso status) {
        this.pais = pais;
        this.momento = momento;
        this.casos = casos;
        this.status = status;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public LocalDateTime getMomento() {
        return momento;
    }

    public void setMomento(LocalDateTime momento) {
        this.momento = momento;
    }

    public int getCasos() {
        return casos;
    }

    public void setCasos(int casos) {
        this.casos = casos;
    }

    public Models.Medicao.StatusCaso getStatus() {
        return status;
    }

    public void setStatus(Models.Medicao.StatusCaso status) {
        this.status = status;
    }

    public String toString(){
        return this.pais.getNome() + " " + this.pais.getSlug() + " " + this.momento
            + " " + this.casos + " " + this.status;
    }

}
