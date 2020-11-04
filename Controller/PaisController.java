package Controller;
import Models.*;

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

    }

}
