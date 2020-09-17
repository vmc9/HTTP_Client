package client_libary;
import java.util.HashMap;
import java.net.Socket;
import java.io.*;

/*
    HTTP is a class that provides a client interface for making POST and GET requests
    from a server using the Hypertext Transfer Protocol version 1.0 as described in RFC1945
 */

public class HTTP {

    public HTTP(){
        System.out.println("HTTP Client Created");
        this.version = "HTTP/1.0";
    }

    String version;
    String crlf = "\r\n";

    public String POST(String url, HashMap<String, String> headers, String payload, String mode, String flag){
        // POST method
        return "response";
    }

    public String GET(String url, HashMap<String, String> headers, String flag) throws IOException {

        // GET method
        Socket connection = new Socket(url, 80);
        PrintWriter http_message = new PrintWriter(connection.getOutputStream());
        BufferedReader http_response = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder message_builder = new StringBuilder();
        String request_line = "GET " + url + " " + version + crlf;
        message_builder.append(request_line);
        headers.forEach((name, value) -> message_builder.append(name).append(" : ").append(value).append(crlf));

        if(flag.equals("-v")){
            System.out.println(message_builder.toString());
        }
        http_message.print(message_builder.toString());

        StringBuilder response_builder = new StringBuilder();
        String next;
        while(true){
            next = http_response.ready() ? http_response.readLine() : null;
            if(next.equals(null)){
                break;
            }else{
                response_builder.append(next);
            }
        }

        if(flag.equals("-v")){
            System.out.println(response_builder.toString());
        }
        return response_builder.toString();
    }
}