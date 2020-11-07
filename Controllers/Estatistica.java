package Controllers;
import Models.*;
import java.io.Serializable;
import java.util.*;
import java.time.LocalDateTime;
public abstract class Estatistica{
    private String nome;
    private List<Medicao> observacoes;
    public Estatistica(String nome, List<Medicao> observacoes) {
        this.nome = nome;
        this.observacoes = observacoes;
    }
    public String getNome() {
       return nome;
   }
   public void setNome(String nome) {
       this.nome = nome;
   }

   public List<Medicao> getObservacoes() {
       return observacoes;
   }

   public void setObservacoes(List<Medicao> observacoes) {
       this.observacoes = observacoes;
   }

    public void inclui(Medicao observacao){
        this.observacoes.add(observacao);
    }
    public LocalDateTime dataInicio(){
        return this.observacoes.get(0).getMomento();
    }
    public LocalDateTime dataFinal(){
        return this.observacoes.get(1).getMomento();
    }
    public float valor(){
        return (float) this.observacoes.get(1).getCasos()
            /(float) this.observacoes.get(0).getCasos();
    }
    public String toString() {
        return this.nome + " " + this.observacoes.toString();
    }
}
