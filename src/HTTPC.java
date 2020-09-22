import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class HTTPC {
    public static void main(String[] args){
        try {
            Client client = new Client();
            if(args.length == 0){
                System.out.println("java HTTPC help for usage");
            }else if(args.length == 1){
                if(args[0].equals("TEST")){
                    //Default test
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
                } else if(args[0].equals("HELP") || args[0].equals("help")){
                    //HELP display
                    System.out.println("HTTPC is a curl-like application supporting HTTP protocl v 1.0 only");
                    System.out.println("Usage:\n httpc command [arguments] \n" +
                            "GET \t executes a HTTP GET request and prints the response\n" +
                            "POST \t executes a HTTP POST request and prints the response\n" +
                            "HELP \t prtins this screen\n\n" +
                            "Use \"HTTPC help [command]\" for more information about a command\n");
                }
            }else if(args.length == 2) {
                String command_method = args[0];
                //Command HELP displays
                if ((command_method.equals("HELP") || command_method.equals("help"))) {
                    if (args[1].equals("POST")) {
                        System.out.println("httpc help post\n" +
                                "usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\n" +
                                "Post executes a HTTP POST request for a given URL with inline data or from\n" +
                                "file.\n" +
                                "-v Prints the detail of the response such as protocol, status,\n" +
                                "and headers.\n" +
                                "-h key:value Associates headers to HTTP Request with the format\n" +
                                "'key:value'.\n" +
                                "-d string Associates an inline data to the body HTTP POST request.\n" +
                                "-f file Associates the content of a file to the body HTTP POST\n" +
                                "request.\n" +
                                "Either [-d] or [-f] can be used but not both.");
                    } else if (args[1].equals("GET")) {
                        System.out.println("httpc help get\n" +
                                "usage: httpc get [-v] [-h key:value] URL\n" +
                                "Get executes a HTTP GET request for a given URL.\n" +
                                "-v Prints the detail of the response such as protocol, status,\n" +
                                "and headers.\n" +
                                "-h key:value Associates headers to HTTP Request with the format\n" +
                                "'key:value'.");
                    }
                }
            }else {
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
                    //Get Headers
                    if(nextIsHeader){
                        String[] header = block.split(":");
                        headers.put(header[0], header[1]);
                    }
                    //Inline Data
                    if(nextIsData){
                        data = block.replace("'", "\"")+" "+args[i+1];
                    } else if(nextIsFile){ //File Data
                        file = block;
                    }
                    //Verbose
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
                    } else if(command_method.equals("GET")){
                        client.GET(url, headers, verbose);
                    } else {
                        System.out.println("COMMAND ERROR: POST or GET required");
                    }
                } else{
                    System.out.println("COMMAND ERROR: URL is required");
                }
            }
        } catch (IOException exception){
            System.out.println(exception.toString());
        }
    }
}