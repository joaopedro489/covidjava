package Controllers;
import Models.*;
import java.util.*;
import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpClient.*;
import java.time.LocalDateTime;
import java.time.format.*;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * @author Filipe Souza, Gabriel Ottoboni, João Pedro Silva
 *
 *<h1>
 *     Classe que representa a Controller dos Países para o projeto final.
 *     Feito para pegar os dados da api da COVID.
 *</h1>
 */
public class PaisController {

    /**
     * Utiliza a api da COVID para pegar os dados dos países.
     */
    public static void getPaisesApi() {
        HttpClient cliente = HttpClient.newBuilder()
                               .version(Version.HTTP_2)
                               .followRedirects(Redirect.ALWAYS)
                               .build();
        HttpRequest requisicao = HttpRequest.newBuilder()
                                .uri(URI.create("https://api.covid19api.com/countries"))
                                .build();

       try {
           HttpResponse<String> resposta;
           resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

           try {
               JSONArray respostaJson = (JSONArray) new JSONParser().parse(resposta.body());
               ArrayList paises = new ArrayList();
               for (Object pais : respostaJson) {
                   String country = (String) ((JSONObject) pais).get("Country");
                   String slug = (String) ((JSONObject) pais).get("Slug");
                   String iso2 = (String) ((JSONObject) pais).get("ISO2");
                   System.out.println("indo pegar lat e long do " + slug);
                   ArrayList<HashMap<String, String>> dados = getPaisLatLonApi(slug);
                   System.out.println("voltando " + country);


                   Pais paisArquivo = new Pais(country, iso2, slug,
                        Float.parseFloat(dados.get(0).get("latitude")), Float.parseFloat(dados.get(0).get("longitude")));
                   paises.add(paisArquivo);
               }
               FileController.escreverArquivoSer("countries", paises);
           }
           catch (ParseException e) {
               System.err.println("Resposta inválida");
               e.printStackTrace();
           }
       }
       catch (IOException e) {
           System.err.println("Problema com a conexão");
           e.printStackTrace();
       }
       catch (InterruptedException e) {
           System.err.println("Requisição interrompida");
           e.printStackTrace();
       }
    }

    /**
     * Recebe os parâmetros de busca e retorna uma lista organizada dos
     * países mais próximos do país com maior taxa de crescimento de casos confirmados.
     *
     * @param dataInicio data inicial da busca.
     * @param dataFinal data final da busca.
     * @param raio raio do círculo da distância dos países.
     * @return lista de medições ordenada por distância.
     */
    public static List<Medicao> comparacaoRaio(String dataInicio,
        String dataFinal, float raio) {
        ArrayList<HashMap<String, String>> listaDistancia = new ArrayList<HashMap<String, String>>();
		DateTimeFormatter formatadorDeData = DateTimeFormatter.ISO_DATE_TIME;
		List<Medicao> medicoes = new ArrayList<Medicao>();
        ArrayList<Medicao> casos = (ArrayList<Medicao>)	RankingController.rankingCrescimento(dataInicio,
            dataFinal, "confirmados");
        Medicao maior = casos.get(0);
		medicoes.add(maior);
        for (Medicao pais : casos) {
            float distancia = distancia(maior.getPais().getLatitude(),
                maior.getPais().getLongitude(), pais.getPais().getLatitude(),
                pais.getPais().getLongitude());
            if(raio >= Math.abs(distancia) && !(pais.getPais().getSlug().equals(
                maior.getPais().getSlug()))){
                HashMap<String, String> map = new HashMap<String,String>();
                map.put("nome", pais.getPais().getNome());
                map.put("slug", pais.getPais().getSlug());
				map.put("latitude", Float.toString(pais.getPais().getLatitude()));
				map.put("longitude", Float.toString(pais.getPais().getLongitude()));
				map.put("momento", pais.getMomento().format(formatadorDeData));
                map.put("casos", Float.toString(pais.getCasos()));
                map.put("distancia", Float.toString(distancia));
                listaDistancia.add(map);
            }
        }
        PaisController.ordenaDistancia(listaDistancia);
        for (HashMap<String, String> stringStringHashMap : listaDistancia) {
            Pais pais = new Pais(stringStringHashMap.get("nome"), "teste", stringStringHashMap.get("slug"), Float.parseFloat(stringStringHashMap.get("latitude")), Float.parseFloat(stringStringHashMap.get("longitude")));
            Medicao medicao = new Medicao(pais, LocalDateTime.parse(stringStringHashMap.get("momento"), formatadorDeData), Float.parseFloat(stringStringHashMap.get("casos")), Medicao.StatusCaso.CONFIRMADOS);
            medicoes.add(medicao);
        }
        return medicoes;
    }

    private static ArrayList<HashMap<String, String>> getPaisLatLonApi(String slug) {
        HttpClient cliente = HttpClient.newBuilder()
                               .version(Version.HTTP_2)
                               .followRedirects(Redirect.ALWAYS)
                               .build();

        HttpRequest requisicao = HttpRequest.newBuilder()
                                .uri(URI.create("https://api.covid19api.com/live/country/" + slug))
                                .build();
        try {
            HttpResponse<String> resposta;
			System.out.println("==========================");
            do {
                resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
                System.out.println(resposta.statusCode());
            } while (resposta.statusCode() != 200);

			if(resposta.body().length() == 3){
				requisicao = HttpRequest.newBuilder()
										.uri(URI.create("https://api.covid19api.com/dayone/country/" + slug + "/status/confirmed"))
										.build();

                do {
                    resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
                    System.out.println(resposta.statusCode());
                    System.out.println(resposta.body());
                } while (resposta.statusCode() != 200);
			}
			System.out.println("==========================");

            try{
                JSONArray respostaJson = (JSONArray) new JSONParser().parse(resposta.body());
                ArrayList<HashMap<String, String>> pais = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> map = new HashMap<String, String>();
                String latitude = "0";
                String longitude = "0";
                if(respostaJson.size() != 0){
					System.out.println("==========================");
                    latitude = (String) ((JSONObject) respostaJson.get(0)).get("Lat");
					System.out.println(latitude);
                    longitude = (String) ((JSONObject) respostaJson.get(0)).get("Lon");
					System.out.println(longitude);
					System.out.println("==========================");
                }
                map.put("latitude", latitude);
                map.put("longitude", longitude);
                pais.add(map);
                return pais;
            }
            catch (ParseException e) {
                System.err.println("Resposta inválida");
                e.printStackTrace();
                System.exit(1);
                return null;
            }

        }
        catch (IOException e) {
            System.err.println("Problema com a conexão");
            e.printStackTrace();
            System.exit(1);
            return null;

        }
        catch (InterruptedException e) {
            System.err.println("Requisição interrompida");
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    private static void ordenaDistancia(List<HashMap<String, String>> listaDistancia){
        Collections.sort(listaDistancia, new Comparator<HashMap<String, String>>() {
            public int compare(HashMap<String, String> one, HashMap<String, String> two) {
                Float primeiraDistancia = Float.parseFloat(one.get("distancia"));
                Float segundaDistancia = Float.parseFloat(two.get("distancia"));
                return primeiraDistancia.compareTo(segundaDistancia);
            }
        });
    }

    private static float distancia(float lat1, float lon1, float lat2, float lon2) {
        float raioDaTerra = 6371;

        float dLat = (lat2-lat1)* (float)Math.PI/180;
        float dLon = (lon2-lon1)*(float)Math.PI/180;
        double haversine =  Math.sin(dLat/2)* Math.sin(dLat/2) +
         Math.cos(lat1*Math.PI/180)*Math.cos(lat2*Math.PI/180)
            *Math.sin(dLon/2)*Math.sin(dLon/2);

        double anguloCentral = 2 * Math.atan2(Math.sqrt(haversine),
            Math.sqrt(1-haversine));
        return raioDaTerra * (float) anguloCentral;
    }
}
