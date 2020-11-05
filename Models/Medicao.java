package Models;

import java.time.LocalDateTime;
import java.io.Serializable;

public class Medicao implements Serializable, Model {
    private static final long serialVersionUID = 1001L;
    public enum StatusCaso{
        CONFIRMADOS,RECUPERADOS, MORTOS;
    }
    Pais pais;
    LocalDataTime momento;
    int casos;
    StatusCaso status;

    public static String toString(){
        return this.pais.nome + " " + this.pais.slug + " " + this.momento
            + " " + this.casos + " " + this.status;
    }

}
