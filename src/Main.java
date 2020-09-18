import client_libary.HTTP;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class Main {
    public static void main(String[] args){
        HTTP client = new HTTP();
        HashMap <String, String> headers = new HashMap<>();
        headers.put("From", "student@demo.com");
        try {
            URL url = new URL("http://httpbin.org/get?course=networking&assignment=1");
            client.GET(url, headers, "-v");

            headers.put("Content-type", "application/json");
            url = new URL("http://httpbin.org/post");
            client.POST(url, headers, "{\"Assignment\" : 1}", "-d", "-v");

        } catch (IOException exception){

            System.out.println(exception.toString());

        }
    }
}
