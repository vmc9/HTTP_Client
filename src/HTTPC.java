import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class HTTPC {
    public static void main(String[] args){
        String mode = "TEST";
        try {
            Client client = new Client();
            if(mode.equals("TEST") || args[0].equals("TEST")){

                HashMap<String, String> headers = new HashMap<>();
                headers.put("From", "student@demo.com");
                URL url;

                //GET TEST
                System.out.println("GET METHOD TEST\n");
                url = new URL("http://httpbin.org/get?course=networking&assignment=1");
                client.GET(url, headers, "-v");

                url = new URL("http://httpbin.org/post");

                //POST FILE TEST
                System.out.println("POST FILE TEST\n");
                client.POST(url, headers, "test.json", "-f", "-v");

                //POST INLINE TEST
                System.out.println("POST INLINE TEST\n");
                client.POST(url, headers, "{\"Assignment\" : 1}", "-d", "-v");

            }else{
                for(String block : args){
                    System.out.println(block);
                }
                String command_method;
                boolean verbose;
                HashMap<String, String> headers = getHeaders(args);
                String payload_type;

            }

        } catch (IOException exception){
            System.out.println(exception.toString());

        }
    }

    public static HashMap<String, String> getHeaders(String[] input){
        HashMap<String, String> headers = new HashMap<>();
        int index = 1;
        while(index < input.length){
            if(input[index-1].contains("-h") && input[index].contains(":")){
                String[] header = input[index].split(":");
                headers.put(header[0], header[1]);
            }
            index++;
        }
        return headers;
    }
}


