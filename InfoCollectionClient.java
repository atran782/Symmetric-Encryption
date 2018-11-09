import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.util.*;

public class InfoCollectionClient {
    public static void main(String[] args) {
        
        try {
			Scanner input = new Scanner(System.in);
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			String ip = "atran13@cs3750a.msudenver.edu";
			int port = 5180;
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(ip, port);

            InputStream inputstream = sslsocket.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            OutputStream outputstream = sslsocket.getOutputStream();
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
            BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);
							
            //get our session from our socket to get info related to this session
            SSLSession sesh = sslsocket.getSession();
            System.out.println("Peer Host is: " + sesh.getPeerHost());
            System.out.println("Cipher Suite is: " + sesh.getCipherSuite());
            System.out.println("Protocol is: " + sesh.getProtocol());
            System.out.println("Session id is: " + sesh.getId());
            System.out.println("The creation time is: " + sesh.getCreationTime());
            System.out.println("The last access time was: " + sesh.getLastAccessedTime());

            String string = null;
            String response = null;
            Boolean check = true;
            while (check) {
                //read next line being sent from server
                string = bufferedreader.readLine();
                response = null;
                switch (string) {
                    case "User Name:":
							System.out.println(string);
							//our response needs a newline character otherewise it will wait since it expects more input
							response = input.nextLine() + "\n";
                            //write out to the server
                            bufferedwriter.write(response);
                            //needs to be flushed
                            bufferedwriter.flush();
                            break;
                    case "Full Name:":
                            System.out.println(string);
                            response = input.nextLine() + "\n";
                            bufferedwriter.write(response);
                            bufferedwriter.flush();
                            break;
                    case "Address:":
                            System.out.println(string);
                            response = input.nextLine() + "\n";
                            bufferedwriter.write(response);
                            bufferedwriter.flush();
                            break;
                    case "Phone Number:":
                            System.out.println(string);
                            response = input.nextLine() + "\n";
                            bufferedwriter.write(response);
                            bufferedwriter.flush();
                            break;
                    case "Email Address:":
                            System.out.println(string);
                            response = input.nextLine() + "\n";
                            bufferedwriter.write(response);
                            bufferedwriter.flush();
                            break;
                    case "Add More Users? yes/no":
                            System.out.println(string);
                            response = input.nextLine();
                            response.toLowerCase();
                        if(!response.equals("yes")){
                                check = false;
                        }
                            response = response + "\n";
                            bufferedwriter.write(response);
                            bufferedwriter.flush();
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid response from server: " + string);
                }
										
            }
                
                sslsocket.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        
    }
}