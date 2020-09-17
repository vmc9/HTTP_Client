import client_libary.HTTP;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        HTTP client = new HTTP();
        HashMap <String, String> headers = new HashMap<String, String>();
        try {
            client.GET("http://www.google.ca", headers, "-v");
        }catch (IOException error){
            System.out.println((error.toString()));
        }
    }
}
