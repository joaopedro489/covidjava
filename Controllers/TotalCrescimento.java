package Controllers;
import Models.*;
import java.util.*;

public class TotalCrescimento extends Estatistica {
    public TotalCrescimento(String nome, List<Medicao> observacoes){
        super(nome, observacoes);
    }
    @Override
    public float valor(int i){
        return ((this.getObservacoes().get(i+1).getCasos() -
                this.getObservacoes().get(i).getCasos())
            / this.getObservacoes().get(i).getCasos()) * 100;
    }
}
