package client_libary;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.net.Socket;
import java.io.*;


/*
    HTTP is a class that provides a client interface for making POST and GET requests
    from a server using the Hypertext Transfer Protocol version 1.0 as described in RFC1945
 */

public class HTTP {

    public HTTP(){
        System.out.println("HTTP Client Created\n");
        this.version = "HTTP/1.0";
        String greeting =   "   \\\\    Most\n" +
                            "   (o>   Common\n" +
                            "\\\\_//)   Alternative\n" +
                            " \\_/_)   To\n" +
                            "  _|_    Avian Carrier\n";
        System.out.println(greeting);
    }

    String version;
    Calendar request_time = Calendar.getInstance();
    String crlf = "\r\n";

    public String POST(URL url, HashMap<String, String> headers, String payload, String mode, String flag){
        // POST method
        return "response";
    }

    public String GET(URL url, HashMap<String, String> headers, String flag) throws IOException {
        String host = url.getHost();
        String params = url.getQuery();
        String path = (params != null) ? url.getPath()+"?"+url.getQuery() : url.getPath();

        Socket connection = new Socket(host, 80);

        PrintWriter http_message = new PrintWriter(connection.getOutputStream(), true);
        StringBuilder message_builder = new StringBuilder();

        headers.put("Date", DateTimeFormatter.RFC_1123_DATE_TIME
                .withZone(ZoneOffset.UTC)
                .format(Instant.now()));

        String request_line = "GET " + path + " " + version + crlf;
        message_builder.append(request_line);
        headers.forEach((name, value) -> message_builder.append(name).append(" : ").append(value).append(crlf));

        if(flag.equals("-v")){
            System.out.println(message_builder.toString());
        }

        http_message.println(message_builder.toString());

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

        return response_builder.toString();
    }
}