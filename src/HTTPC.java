import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class HTTPC {
    public static void main(String[] args){
        try {
            Client client = new Client();

            if(args.length > 0 && args[0].equals("TEST")){
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

            }else if(args.length > 0){
                //Variables
                String command_method = args[0];
                URL url = null;
                String data = "";
                String file = "";
                String verbose = "";

                boolean nextIsHeader = false;
                boolean nextIsFile = false;
                boolean nextIsData = false;

                HashMap<String, String> headers = new HashMap<>();
                int i = 0;
                for(String block : args){
                    if(nextIsHeader){
                        String[] header = block.split(":");
                        headers.put(header[0], header[1]);
                    }

                    if(nextIsData){
                        data = block.replace("\'", "\"")+" "+args[i+1];
                    } else if(nextIsFile){
                        file = block;
                    }

                    if(block.equals("-v")){
                        verbose = "-v";
                    }

                    //URL
                    if(block.contains("http")){
                        url = new URL((block));
                    }

                    //Options
                    nextIsHeader = block.equals("-h");
                    nextIsData = block.equals("-d");
                    nextIsFile = block.equals("-f");

                    i++;
                }

                if(url!= null){
                    if(command_method.equals("POST") || command_method.equals("post")){
                        if(!data.equals("")){
                            client.POST(url, headers, data, "-d",verbose);
                        } else if(!file.equals("")){
                            client.POST(url, headers, file, "-f",verbose);
                        } else{
                            System.out.println("COMMAND ERROR: -d or -f is required");
                        }
                    } else if(command_method.equals("GET") || command_method.equals("get")){
                        client.GET(url, headers, verbose);
                    } else if(command_method.equals("HELP") || command_method.equals("help")) {

                    }else {
                        System.out.println("COMMAND ERROR: POST or GET required");
                    }
                } else{
                    System.out.println("COMMAND ERROR: URL is required");
                }
            } else{
                System.out.println("java HTTPC help for usage");
            }
        } catch (IOException exception){
            System.out.println(exception.toString());
        }
    }
}


