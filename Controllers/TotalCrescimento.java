package Controllers;
import Models.*;
import java.util.*;

public class TotalCrescimento extends Estatistica{
    public TotalCrescimento(String nome, List<Medicao> observacoes){
        super(nome, observacoes);
    }
    @Override
    public float valor(int i){
        return this.observacoes.get(i+1).getCasos()
            / this.observacoes.get(i).getCasos();
    }
}
