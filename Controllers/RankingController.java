package Controllers;
import Models.*;
import java.util.*;
import java.time.format.*;
import java.time.*;
import java.io.*;

public class RankingController {
    public static List<Medicao> rankingGeral(String dataInicio, String dataFinal, String tipo){
        List<Medicao> casos = new ArrayList<Medicao>();
        Estatistica caso = new Total("total", new ArrayList<Medicao>());
        RankingController.getEstatisticas(caso, dataInicio, dataFinal, tipo);
        RankingController.calculaCasos(caso,casos, null);
        RankingController.ordenaMedicao(casos);
        return casos;
    }
    public static List<Medicao> rankingCrescimento(String dataInicio, String dataFinal, String tipo){
        List<Medicao> casos = new ArrayList<Medicao>();
        Estatistica caso = new TotalCrescimento("total crescimento", new ArrayList<Medicao>());
        RankingController.getEstatisticas(caso, dataInicio, dataFinal, tipo);
        RankingController.calculaCasos(caso,casos, null);
        RankingController.ordenaMedicao(casos);
        for(int i = 0; i < casos.size(); i++){
            System.out.println(casos.get(i));
        }
        return casos;
    }
    public static List<Medicao> rankingMortalidade(String dataInicio, String dataFinal){
        List<Medicao> casos = new ArrayList<Medicao>();
        Estatistica caso = new TotalMortalidade("total mortalidade", new ArrayList<Medicao>());
        Total casoTotal = new Total("total", new ArrayList<Medicao>());
        RankingController.getEstatisticas(caso, dataInicio, dataFinal, "mortos");
        RankingController.getEstatisticas(casoTotal, dataInicio, dataFinal, "confirmados");
        RankingController.calculaCasos(caso,casos, casoTotal);
        RankingController.ordenaMedicao(casos);
        return casos;
    }
    public static void salvarPesquisa(String dataInicio, String dataFinal, String categoria, float raio, String caminhoArquivo){
		ArrayList<HashMap<String, String>> historico = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("dataInicio", dataInicio);
		map.put("dataFinal", dataFinal);
		map.put("categoria", categoria);
		if(categoria.equals("proximos")){
			map.put("raio", Float.toString(raio));
		}
		historico.add(map);
		FileController.escreverArquivoSer(caminhoArquivo, historico);
        return;
    }
	public static ArrayList<HashMap<String, String>> pegarPesquisa(String caminhoArquivo){
		return (ArrayList<HashMap<String,String>>)FileController.lerArquivoSer(caminhoArquivo);
	}
    private static void getEstatisticas(Estatistica caso, String dataInicio, String dataFinal, String tipo){
        ArrayList<Medicao> dados = (ArrayList<Medicao>) FileController.lerArquivoSer("resources/samples");
        dataInicio =  dataInicio + " 00:00";
        dataFinal =  dataFinal + " 00:00";
        DateTimeFormatter formatadorDeData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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
    }
    private static void calculaCasos(Estatistica caso, List<Medicao> casos, Estatistica casoTotal){
        for(int i = 0; i < caso.getObservacoes().size() - 1 ; i++){
            if(caso.getObservacoes().get(i).getPais().getSlug().equals(
                caso.getObservacoes().get(i+1).getPais().getSlug())){
                    float valor;
                    if(casoTotal != null){
                        float valorTotal = casoTotal.getObservacoes().get(i).getCasos();
                        float valorMortos = caso.getObservacoes().get(i).getCasos();
                        valor = (valorMortos/valorTotal) * 100;
                    }
                    else{
                        valor = caso.valor(i);
                    }
                    Medicao medicao = caso.getObservacoes().get(i);
                    medicao.setCasos(valor);
                    casos.add(medicao);
                }
            else if(i != 0 && !(caso.getObservacoes().get(i-1).getPais().getSlug().equals(
                caso.getObservacoes().get(i).getPais().getSlug()))){
                float valor;
                if(casoTotal != null){
                    float valorTotal = casoTotal.getObservacoes().get(i).getCasos();
                    float valorMortos = caso.getObservacoes().get(i).getCasos();
                    valor = (valorMortos/valorTotal) * 100;
                }
                else{
                    if(caso.getNome().equals("total crescimento")){
                        valor = caso.getObservacoes().get(i).getCasos() * 100;
                    }
                    else{
                        valor = caso.getObservacoes().get(i).getCasos();
                    }
                }
                Medicao medicao = caso.getObservacoes().get(i);
                medicao.setCasos(valor);
                casos.add(caso.getObservacoes().get(i));
            }
        }
    }
    private static void ordenaMedicao(List<Medicao> lista){
        Collections.sort(lista, new Comparator<Medicao>(){
        public int compare(Medicao one, Medicao two) {
            Float filterOne =  one.getCasos();
            Float filterTwo =  two.getCasos();
            return filterTwo.compareTo(filterOne);
            }
        });
    }
}
