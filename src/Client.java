import java.net.URL;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

/*
    HTTP is a class that provides a client interface for making POST and GET requests
    from a server using the Hypertext Transfer Protocol version 1.0 as described in RFC1945
 */

public class Client {

    public Client(){
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
    String crlf = "\r\n";

    public String POST(URL url, HashMap<String, String> headers, String payload, String mode, String flag) throws IOException {
        String host = url.getHost();
        String path = url.getPath();

        Socket connection = new Socket(host, 80);

        PrintWriter http_message = new PrintWriter(connection.getOutputStream(), true);
        StringBuilder message_builder = new StringBuilder();

        headers.put("Date", DateTimeFormatter.RFC_1123_DATE_TIME
                .withZone(ZoneOffset.UTC)
                .format(Instant.now()));

        String request_line = "POST " + path + " " + version + crlf;
        message_builder.append(request_line);

        if(mode.equals("-d")){

            headers.put("Content-length", Integer.toString(payload.length()));
            headers.forEach((name, value) -> message_builder.append(name).append(": ")
                    .append(value)
                    .append(crlf));
            message_builder.append(crlf)
                    .append(payload)
                    .append(crlf);

        } else if(mode.equals("-f")){

            //Handle file
            File payload_file = new File(payload);
            Scanner file_scan = new Scanner(payload_file);
            StringBuilder data_string = new StringBuilder();
            while(file_scan.hasNextLine()){
                data_string.append(file_scan.nextLine());
            }

            headers.put("Content-length", Integer.toString(data_string.toString().length()));
            headers.forEach((name, value) -> message_builder.append(name).append(": ")
                    .append(value)
                    .append(crlf));
            message_builder.append(crlf)
                    .append(data_string.toString())
                    .append(crlf);

        } else {
        System.out.println("ERROR: Either -d flag or -f flag must be used, but not both");
        }

        if(flag.equals("-v")){
            System.out.println("VERBOSE OUTPUT");
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

        String[] response = response_builder.toString().split(crlf+crlf
        );
        if(flag.equals("-v")){
            System.out.println("VERBOSE OUTPUT");
            System.out.println(response[0]+"\n");
        }
        System.out.println("REGULAR OUTPUT");
        System.out.println(response[1]);

        return response_builder.toString();
    }

    public String GET(URL url, HashMap<String, String> headers, String flag) throws IOException {
        String host = url.getHost();
        String params = url.getQuery();
        String path = (params != null) ? url.getPath()+"?"+url.getQuery() : url.getPath();

        Socket connection = new Socket(host, 80);

        PrintWriter http_message = new PrintWriter(connection.getOutputStream(), true);
        StringBuilder message_builder = new StringBuilder();

        String request_line = "GET " + path + " " + version + crlf;
        message_builder.append(request_line);
        headers.forEach((name, value) -> message_builder.append(name).append(": ").append(value).append(crlf));

        if(flag.equals("-v")){
            System.out.println("VERBOSE OUTPUT");
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

        String[] response = response_builder.toString().split(crlf+crlf);
        if(flag.equals("-v")){
            System.out.println("VERBOSE OUTPUT");
            System.out.println(response[0]+"\n");
        }
        System.out.println("REGULAR OUTPUT");
        System.out.println(response[1]);

        return response_builder.toString();
    }
}