package Controllers;
import Models.*;
import java.util.ArrayList;
import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpClient.*;
import java.time.LocalDateTime;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * @author Filipe Souza, Gabriel Ottoboni, João Pedro Silva
 *
 *<h1>
 *     Classe que representa a Controller das Medições para o projeto final.
 *     Feito para pegar os dados da api da COVID.
 *</h1>
 */
public class MedicaoController {

    /**
     * Utiliza a api da COVID para pegar os dados mundiais
     * da situação da COVID, adiciona a listas e salva em
     * um arquivo .ser.
     */
    public static void getDadosApi() {

        ArrayList<Pais> paises = (ArrayList<Pais>) FileController.lerArquivoSer("resources/countries");
        ArrayList<Medicao> medicoes = new ArrayList<Medicao>();
        HttpClient cliente = HttpClient.newBuilder()
                               .version(Version.HTTP_2)
                               .followRedirects(Redirect.ALWAYS)
                               .build();

        for (Pais pais : paises) {
            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create("https://api.covid19api.com/total/dayone/country/" + pais.getSlug()))
                                    .build();
            try {
                HttpResponse<String> response;
                do {
                    response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
                } while (response.statusCode() != 200);

                try {
                    JSONArray responseJson = (JSONArray) new JSONParser().parse(response.body());
                    for(Object dados : responseJson){
                        String data = String.valueOf(((JSONObject) dados).get("Date"));
                        Medicao medicaoConfirmados = new Medicao(pais,
                             LocalDateTime.parse(data.split("Z")[0]),
                             Integer.parseInt(String.valueOf(((JSONObject) dados).get("Confirmed"))),
                             Medicao.StatusCaso.CONFIRMADOS);
                        Medicao medicaoMortos = new Medicao(pais,
                             LocalDateTime.parse(data.split("Z")[0]),
                             Integer.parseInt(String.valueOf(((JSONObject) dados).get("Deaths"))),
                             Medicao.StatusCaso.MORTOS);
                        Medicao medicaoRecuperados = new Medicao(pais,
                             LocalDateTime.parse(data.split("Z")[0]),
                             Integer.parseInt(String.valueOf(((JSONObject) dados).get("Recovered"))),
                             Medicao.StatusCaso.RECUPERADOS);
                         medicoes.add(medicaoConfirmados);
                         medicoes.add(medicaoMortos);
                         medicoes.add(medicaoRecuperados);
                }
             }
             catch (ParseException e) {
                 System.err.println("Resposta inválida");
                 e.printStackTrace();
                 System.exit(1);
             }
            }
            catch (IOException e) {
                System.err.println("Problema com a conexão");
                e.printStackTrace();
                System.exit(1);
            }
            catch (InterruptedException e) {
                System.err.println("Requisição interrompida");
                e.printStackTrace();
                System.exit(1);
            }
         }
         FileController.escreverArquivoSer("resources/samples", medicoes);

    }
}
