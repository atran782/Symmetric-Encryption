import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.util.*;

public class InfoCollectionServer {
    public static void main(String[] args) throws IOException { 
        SSLServerSocketFactory sslserversocketfactory = null;
        SSLServerSocket sslserversocket = null;
        boolean listening = true;
        
        try {
            sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(5180);
        } catch (Exception exception) {
            exception.printStackTrace();
			System.exit(0);
        }
        
        
        while(listening){
				SSLSocket client = (SSLSocket) sslserversocket.accept();
                System.out.println("New Client Accepted");
                new InfoCollectionServerThread(client).start();
        }
    }
}

class InfoCollectionServerThread extends Thread {
    SSLSocket sslsocket;
    
    public InfoCollectionServerThread(SSLSocket sslsocket){
        this.sslsocket = sslsocket;   
    }
    
    public void run(){
        //get our session from our socket to get info related to this session
        SSLSession sesh = sslsocket.getSession();
        System.out.println("Peer Host is: " + sesh.getPeerHost());
        System.out.println("Cipher Suite is: " + sesh.getCipherSuite());
        System.out.println("Protocol is: " + sesh.getProtocol());
        System.out.println("Session id is: " + sesh.getId());
        System.out.println("The creation time is: " + sesh.getCreationTime());
        System.out.println("The last access time was: " + sesh.getLastAccessedTime());
        try{
					InputStream inputstream = sslsocket.getInputStream();
					InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
					BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

					OutputStream outputstream = sslsocket.getOutputStream();
					OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
					BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

					String string = null;
					Boolean check = true;
					String userdetails = null;
					while (check) {
							//Open file and begin collecting information
							try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("myfile.txt", true)))) {
								// a - send user name
                                //when we write we need to append a newline or it will wait for more text
								bufferedwriter.write("User Name:\n"); 
                                //flush the buffer
                                bufferedwriter.flush();
                                //Get response from client
								string = bufferedreader.readLine();
								userdetails = "User Name: " + string;
								out.println(userdetails);
								// b - send full name
								bufferedwriter.write("Full Name:\n");
                                bufferedwriter.flush();
								string = bufferedreader.readLine();
								userdetails = "Full Name: " + string;
								out.println(userdetails);
								// c - send address
								bufferedwriter.write("Address:\n");
                                bufferedwriter.flush();
								string = bufferedreader.readLine();
								userdetails = "Address: " + string;
								out.println(userdetails);
								// d - send phone number
								bufferedwriter.write("Phone Number:\n");
                                bufferedwriter.flush();
								string = bufferedreader.readLine();
								userdetails = "Phone Number: " + string;
								out.println(userdetails);
								// e - send email address
								bufferedwriter.write("Email Address:\n");
                                bufferedwriter.flush();
								string = bufferedreader.readLine();
								userdetails = "Email Address: " + string;
								out.println(userdetails);
								//add extra space so file looks better
								out.println();
								//close the file
								out.close();
							}catch (IOException e) {
								// do nothing
							}
							//ask if they want to add more users
							bufferedwriter.write("Add More Users? yes/no\n");
                            bufferedwriter.flush();
							string = bufferedreader.readLine();
							string.toLowerCase();
							if(!string.equals("yes")){
								break;
							}
					}
					
					sslsocket.close();
				
				}catch (Exception e){
					e.printStackTrace();
				}
    }
}