package Controllers;
import Models.*;
import java.util.*;
import java.lang.Math.*;
import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpClient.*;
import java.time.LocalDateTime;

import org.json.simple.*;
import org.json.simple.parser.*;

public class PaisController {
    public static void getPaisesApi(){
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
               ArrayList medicoes = new ArrayList();
               for (Object pais : respostaJson) {
                   String country = (String) ((JSONObject) pais).get("Country");
                   String slug = (String) ((JSONObject) pais).get("Slug");
                   String iso2 = (String) ((JSONObject) pais).get("ISO2");
                   System.out.println("indo pegar lat e long do " + country);
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
    public static List<HashMap<String,String>> comparacaoRaio(String dataInicio,
        String dataFinal, float raio){
        ArrayList<HashMap<String, String>> listaDistancia = new ArrayList<HashMap<String, String>>();
        ArrayList<Medicao> casos = (ArrayList<Medicao>) RankingController.rankingCrescimento(dataInicio,
            dataFinal, "confirmados");
        Medicao maior = casos.get(0);
        for (Medicao pais : casos) {
            float distancia = distancia(maior.getPais().getLatitude(),
                maior.getPais().getLongitude(), pais.getPais().getLatitude(),
                pais.getPais().getLongitude());
            if(raio >= Math.abs(distancia) && !(pais.getPais().getSlug().equals(
                maior.getPais().getSlug()))){
                HashMap<String, String> map = new HashMap<String,String>();
                map.put("nome", pais.getPais().getNome());
                map.put("slug", pais.getPais().getSlug());
                map.put("confirmados", Float.toString(pais.getCasos()));
                map.put("distancia", Float.toString(distancia));
                listaDistancia.add(map);
            }
        }
        PaisController.ordenaDistancia(listaDistancia);
        return listaDistancia;
    }
    private static ArrayList<HashMap<String, String>> getPaisLatLonApi(String slug){
        HttpClient cliente = HttpClient.newBuilder()
                               .version(Version.HTTP_2)
                               .followRedirects(Redirect.ALWAYS)
                               .build();

        HttpRequest requisicao = HttpRequest.newBuilder()
                                .uri(URI.create("https://api.covid19api.com/live/country/" + slug))
                                .build();
        try{
            HttpResponse<String> resposta;
            while(true){
                resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
                if(resposta.statusCode() == 200) break;
            }
            try{
                JSONArray respostaJson = (JSONArray) new JSONParser().parse(resposta.body());
                ArrayList<HashMap<String, String>> pais = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> map = new HashMap<String, String>();
                String latitude = "0";
                String longitude = "0";
                if(respostaJson.size() != 0){
                    latitude = (String) ((JSONObject) respostaJson.get(0)).get("Lat");
                    longitude = (String) ((JSONObject) respostaJson.get(0)).get("Lon");
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
        Collections.sort(listaDistancia, new Comparator<HashMap<String, String>>(){
        public int compare(HashMap<String, String> one, HashMap<String, String> two) {
            Float primeiraDistancia = Float.parseFloat(one.get("distancia"));
            Float segundaDistancia = Float.parseFloat(two.get("distancia"));
            return primeiraDistancia.compareTo(segundaDistancia);
        }
        });
    }
    private static float distancia(float lat1, float lon1, float lat2, float lon2){
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
