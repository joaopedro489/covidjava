package Models;

import java.io.Serializable;

public class Pais extends Model implements Serializable {
    private static final long serialVersionUID = 1002L;
    private String nome;
    private String codigo;
    private String slug;
    private float latitude;
    private float longitude;

    public Pais(String nome, String codigo, String slug, float latitude, float longitude) {
        this.nome = nome;
        this.codigo = codigo;
        this.slug = slug;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String toString(){
        return this.nome + " " + this.slug + " " + this.codigo;
    }

}
