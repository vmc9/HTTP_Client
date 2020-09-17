import client_libary.HTTP;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class Main {
    public static void main(String[] args){
        HTTP client = new HTTP();
        HashMap <String, String> headers = new HashMap<>();
        try {

            URL url = new URL("http://httpbin.org/get?course=networking&assignment=1");
            client.GET(url, headers, "-v");

        } catch (IOException exception){

            System.out.println(exception.toString());

        }
    }
}
