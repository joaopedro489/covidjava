package Models;

import java.io.Serializable;

/**
 * @author Filipe Souza, Gabriel Ottoboni, João Pedro Silva
 *
 *<h1>
 *     Classe que lida com os países para o projeto final.
 *</h1>
 */
public class Pais implements Serializable {
    private static final long serialVersionUID = 1002L;
    private String nome;
    private String codigo;
    private String slug;
    private float latitude;
    private float longitude;

    /**
     * Construtor do País.
     *
     * @param nome nome do país.
     * @param codigo código do país.
     * @param slug nome padrão para ser usado nas buscas.
     * @param latitude latitude do país.
     * @param longitude longitude do país.
     */
    public Pais(String nome, String codigo, String slug, float latitude, float longitude) {
        this.nome = nome;
        this.codigo = codigo;
        this.slug = slug;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Getter do nome do país.
     * Retorna o nome do país.
     * @return o nome do país.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setter do nome do país.
     * Edita o nome do país.
     * @param nome o nome do país que será editado.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Getter do código do país.
     * Retorna o código do país.
     * @return o código do país.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Setter do código do país.
     * Edita o código do país.
     * @param codigo o nome do país que será editado.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Getter do slug do país.
     * Retorna o slug do país.
     * @return o slug do país.
     */
    public String getSlug() {
        return slug;
    }

    /**
     * Setter do slug do país.
     * Edita o slug do país.
     * @param slug o nome do país que será editado.
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * Getter da latitude do país.
     * Retorna a latitude do país.
     * @return a latitude do país.
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Setter da latitude do país.
     * Edita a latitude do país.
     * @param latitude a latitude do país que será editado.
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter da longitude do país.
     * Retorna a longitude do país.
     * @return a longitude do país.
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Setter da longitude do país.
     * Edita a longitude do país.
     * @param longitude a longitude do país que será editado.
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * Transforma um País em String no formato necessário
     * para ser salvo em um arquivo.
     * @return os dados de um país em formato de string.
     */
    public String toString() {
        return this.nome + " " + this.slug + " " + this.codigo + " " + this.latitude
            + " " + this.longitude;
    }

}
