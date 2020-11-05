package Controllers;
import Models.*;
import java.util.*;

import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpClient.*;

import org.json.simple.*;
import org.json.simple.parser.*;

public class PaisController {
    public static void getPaises(){
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
               ArrayList<Pais> paises = new ArrayList<Pais>();
               for (Object pais : respostaJson) {
                   String country = (String) ((JSONObject) pais).get("Country");
                   String slug = (String) ((JSONObject) pais).get("Slug");
                   String iso2 = (String) ((JSONObject) pais).get("ISO2");
                   ArrayList<HashMap<String, String>> pais = getPaisCasos(slug)


                   Pais pais = new
                   Medicao medicao = new
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
    public static ArrayList<HashMap<String, String>> getPaisCasos(String slug){
        HttpClient cliente = HttpClient.newBuilder()
                               .version(Version.HTTP_2)
                               .followRedirects(Redirect.ALWAYS)
                               .build();

        HttpRequest requisicao = HttpRequest.newBuilder()
                                .uri(URI.create("https://api.covid19api.com/total/dayone/country/" + slug))
                                .build();
        try{
            HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());

            try{
                JSONArray respostaJson = (JSONArray) new JSONParser().parse(resposta.body());
                ArrayList<HashMap<String, String>> pais = new ArrayList<HashMap<String, String>>();
                for (Object dados : respostaJson) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("latitude", (String) ((JSONObject) dados).get("Lat"));
                    map.put("longitude", (String) ((JSONObject) dados).get("Lon"));
                    map.put("confirmados", (String) ((JSONObject) dados).get("Confirmed"));
                    map.put("mortos", (String) ((JSONObject) dados).get("Deaths"));
                    map.put("recuperados", (String) ((JSONObject) dados).get("Recovered"));
                    map.put("data", (String) ((JSONObject) dados).get("Date"));

                    pais.add(map);
                }
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
    public static void updatePaises(){

    }

}
