package Controllers;
import Models.*;
import java.util.*;
import java.time.format.*;
import java.time.*;

public class RankingController {
    public static List<Medicao> rankingGeral(String dataInicio, String dataFinal, String tipo){
        ArrayList<Medicao> dados = (ArrayList<Medicao>) FileController.lerArquivo("samples");
        List<Medicao> casos = new ArrayList<Medicao>();
        Total caso = new Total("total", new ArrayList<Medicao>());
        dataInicio =  dataInicio + " 00:00";
        dataFinal =  dataFinal + " 00:00";
        DateTimeFormatter formatadorDeData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Medicao dado : dados) {
                if(dado.getMomento().equals(LocalDateTime.parse(dataInicio, formatadorDeData)) ||
                    dado.getMomento().equals(LocalDateTime.parse(dataFinal, formatadorDeData))){
                switch(tipo.toLowerCase()){
                case "confirmados" -> {
                    if(dado.getStatus() == Medicao.StatusCaso.CONFIRMADOS)
                        caso.inclui(dado);
                    }
                case "mortos" -> {
                    if( dado.getStatus() == Medicao.StatusCaso.MORTOS)
                        caso.inclui(dado);
                    }
                case "recuperados" -> {
                    if( dado.getStatus() == Medicao.StatusCaso.RECUPERADOS)
                        caso.inclui(dado);
                    }
                }
            }
        }
        for(int i = 0; i < caso.getObservacoes().size() - 1 ; i++){
            if(caso.getObservacoes().get(i).getPais().getSlug().equals(
                caso.getObservacoes().get(i+1).getPais().getSlug())){
                    Medicao medicao = caso.getObservacoes().get(i);
                    medicao.setCasos(caso.getObservacoes().get(i+1).getCasos() -
                        caso.getObservacoes().get(i).getCasos());
                    casos.add(medicao);
                    continue;
                }
            if(i == 0) continue;
            if(!(caso.getObservacoes().get(i-1).getPais().getSlug().equals(
                caso.getObservacoes().get(i).getPais().getSlug()))){
                casos.add(caso.getObservacoes().get(i));
            }
        }
        return casos;
    }
    public static void rankingCrescimento(String dataInicio, String dataFinal){

    }
    public static void rankingMortalidade(String dataInicio, String dataFinal){

    }
}
