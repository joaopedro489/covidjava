package Controllers;
import Models.*;
import java.util.*;
import java.time.format.*;
import java.time.*;

/**
 * @author Filipe Souza, Gabriel Ottoboni, João Pedro Silva
 *
 *<h1>
 *     Classe que representa a Controller do Ranking para o projeto final.
 *     Programa feito para calcular os rankings com os dados da COVID
 *     e o histórico de busca.
 *</h1>
 */
public class RankingController {

    /**
     * Busca e ordena decrescentemente os dados de COVID
     * dos países, em relação aos seus valores absolutos.
     *
     * @param dataInicio data inicial da busca.
     * @param dataFinal data final da busca.
     * @param tipo tipo de busca.
     * @return o ranking ordenado pelos valores absolutos.
     */
    public static List<Medicao> rankingGeral(String dataInicio, String dataFinal, String tipo) {
        List<Medicao> casos = new ArrayList<>();
        Estatistica caso = new Total("total", new ArrayList<>());
        RankingController.getEstatisticas(caso, dataInicio, dataFinal, tipo);
        RankingController.calculaCasos(caso,casos, null);
        RankingController.ordenaMedicao(casos);
        return casos;
    }

    /**
     * Busca e ordena decrescentemente os dados de COVID
     * dos países, em relação às suas taxas de crescimento.
     *
     * @param dataInicio data inicial da busca.
     * @param dataFinal data final da busca.
     * @param tipo tipo de busca.
     * @return o ranking ordenado pela taxa de crescimento.
     */
    public static List<Medicao> rankingCrescimento(String dataInicio, String dataFinal, String tipo) {
        List<Medicao> casos = new ArrayList<>();
        Estatistica caso = new TotalCrescimento("total crescimento", new ArrayList<>());
        RankingController.getEstatisticas(caso, dataInicio, dataFinal, tipo);
        RankingController.calculaCasos(caso,casos, null);
        RankingController.ordenaMedicao(casos);
        return casos;
    }

    /**
     * Busca e ordena decrescentemente os dados de COVID
     * dos países, em relação às suas taxas de mortalidade.
     *
     * @param dataInicio data inicial da busca.
     * @param dataFinal data final da busca.
     * @return o ranking ordenado pela sua taxa de mortalidade.
     */
    public static List<Medicao> rankingMortalidade(String dataInicio, String dataFinal){
        List<Medicao> casos = new ArrayList<>();
        Estatistica caso = new TotalMortalidade("total mortalidade", new ArrayList<>());
        Total casoTotal = new Total("total", new ArrayList<>());
        RankingController.getEstatisticas(caso, dataInicio, dataFinal, "mortos");
        RankingController.getEstatisticas(casoTotal, dataInicio, dataFinal, "confirmados");
        RankingController.calculaCasos(caso,casos, casoTotal);
        RankingController.ordenaMedicao(casos);
        return casos;
    }

    /**
     * Salva a pesquisa feita em um arquivo .ser.
     *
     * @param dataInicio data inicial da busca.
     * @param dataFinal data final da busca.
     * @param categoria categoria da busca.
     * @param raio distância máxima do país com maior número de casos confirmados.
     * @param caminhoArquivo caminho (path) do arquivo.
     */
    public static void salvarPesquisa(String dataInicio, String dataFinal, String categoria, float raio, String caminhoArquivo){
		ArrayList<HashMap<String, String>> historico = new ArrayList<>();
		HashMap<String, String> map = new HashMap<>();
		map.put("dataInicio", dataInicio);
		map.put("dataFinal", dataFinal);
		map.put("categoria", categoria);
		if(categoria.equals("próximos")){
			map.put("raio", Float.toString(raio));
		}
		historico.add(map);
		FileController.escreverArquivoSer(caminhoArquivo, historico);
    }

    /**
     * Retorna um ArrayList com a última pesquisa salva.
     *
     * @param caminhoArquivo caminho (path) do arquivo.
     * @return ArrayList com a última pesquisa.
     */
	public static ArrayList<HashMap<String, String>> pegarPesquisa(String caminhoArquivo){
		return (ArrayList<HashMap<String,String>>) FileController.lerArquivoSer(caminhoArquivo);
	}

    private static void getEstatisticas(Estatistica caso, String dataInicio, String dataFinal, String tipo) {
        ArrayList<Medicao> dados = (ArrayList<Medicao>)
		 FileController.lerArquivoSer("resources/samples.ser");
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

    private static void calculaCasos(Estatistica caso, List<Medicao> casos, Estatistica casoTotal) {
        for(int i = 0; i < caso.getObservacoes().size() - 1 ; i++){
            if(caso.getObservacoes().get(i).getPais().getSlug().equals(
                caso.getObservacoes().get(i+1).getPais().getSlug())) {
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

    private static void ordenaMedicao(List<Medicao> lista) {
        Collections.sort(lista, new Comparator<>() {
        public int compare(Medicao one, Medicao two) {
            Float filterOne = one.getCasos();
            Float filterTwo = two.getCasos();
            return filterTwo.compareTo(filterOne);
            }
        });
    }
}
