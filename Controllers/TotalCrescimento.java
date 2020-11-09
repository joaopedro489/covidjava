package Controllers;
import Models.*;
import java.util.*;

public class TotalCrescimento extends Estatistica {
    public TotalCrescimento(String nome, List<Medicao> observacoes){
        super(nome, observacoes);
    }
    @Override
    public float valor(int i){
		if(this.getObservacoes().get(i).getCasos() == 0){
			return this.getObservacoes().get(i+1).getCasos() * 100;
		}
        float diferenca = this.getObservacoes().get(i+1).getCasos() -
                this.getObservacoes().get(i).getCasos() < 0 ? 0 :
                this.getObservacoes().get(i+1).getCasos() -
                        this.getObservacoes().get(i).getCasos();
        return (diferenca / this.getObservacoes().get(i).getCasos()) * 100;
    }
}
