package Models;

import java.io.Serializable;
public class Pais  implements Serializable, Model {
    private static final long serialVersionUID = 1002L;
    String nome;
    String codigo;
    String slug;
    float latitude;
    float longitude;
    public static String toString(){
        return this.nome + " " + this.slug + " " + this.codigo;
    }
    
}
