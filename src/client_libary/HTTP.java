package client_libary;
import java.net.URL;
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

    public String POST(URL url, HashMap<String, String> headers, String payload, String mode, String flag){
        // POST method
        return "response";
    }

    public String GET(URL url, HashMap<String, String> headers, String flag) throws IOException {

        // GET method
        String host = url.getHost();
        String path = url.getPath();
        String params = url.getQuery();

        Socket connection = new Socket(host, 80);

        PrintWriter http_message = new PrintWriter(connection.getOutputStream(), true);
        StringBuilder message_builder = new StringBuilder();

        String request_line = "GET " + path + " " + version + crlf;
        message_builder.append(request_line);
        if(params != null){
            headers.put("Content-Length: ", Integer.toString(params.length()));
            headers.forEach((name, value) -> message_builder.append(name).append(" : ").append(value).append(crlf));
            message_builder.append(crlf).append(params);
        }else {
            headers.forEach((name, value) -> message_builder.append(name).append(" : ").append(value).append(crlf));
        }

        if(flag.equals("-v")){
            System.out.println(message_builder.toString());
        }

        http_message.println(message_builder.toString());

        if(flag.equals("-v")){
            System.out.println("\nMessage Sent!\n");
        }

        BufferedReader http_response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response_builder = new StringBuilder();

        int next = http_response.read();
        while(next != -1){
            response_builder.append((char)next);
            next = http_response.read();
        }

        http_message.close();
        http_response.close();
        connection.close();

        if(flag.equals("-v")){
            System.out.println(response_builder.toString());
        }

        System.out.println("Request Complete");

        return response_builder.toString();
    }
}