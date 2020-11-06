package Controllers;
import Models.*;
import java.util.*;

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
           HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

           try {
               JSONArray respostaJson = (JSONArray) new JSONParser().parse(resposta.body());
               ArrayList paises = new ArrayList();
               ArrayList medicoes = new ArrayList();
               for (Object pais : respostaJson) {
                   String country = (String) ((JSONObject) pais).get("Country");
                   String slug = (String) ((JSONObject) pais).get("Slug");
                   String iso2 = (String) ((JSONObject) pais).get("ISO2");
                   ArrayList<HashMap<String, String>> dados = getPaisLatLonApi(slug);


                   Pais paisArquivo = new Pais(country, iso2, slug,
                        Float.parseFloat(dados.get(0).get("latitude")), Float.parseFloat(dados.get(0).get("longitude")));
                   paises.add(paisArquivo);
               }
               FileController.escreverArquivo("countries", paises);
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
    public static ArrayList<HashMap<String, String>> getPaisLatLonApi(String slug){
        HttpClient cliente = HttpClient.newBuilder()
                               .version(Version.HTTP_2)
                               .followRedirects(Redirect.ALWAYS)
                               .build();

        HttpRequest requisicao = HttpRequest.newBuilder()
                                .uri(URI.create("https://api.covid19api.com/live/country/" + slug))
                                .build();
        try{
            System.out.println("++++++++++++++++");
            System.out.println(slug);
            System.out.println("----------------");
            HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

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
                return null;
            }

        }
        catch (IOException e) {
            System.err.println("Problema com a conexão");
            e.printStackTrace();
            return null;

        }
        catch (InterruptedException e) {
            System.err.println("Requisição interrompida");
            e.printStackTrace();
            return null;
        }

    }
    public static void main(String[] args) {
    }
}
