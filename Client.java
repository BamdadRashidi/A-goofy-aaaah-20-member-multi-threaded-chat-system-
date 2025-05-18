import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        // a socket to connect to the server
        // input readers and writers to read and write data from client and buffers for maximum efficiency.
        Socket ClientSocket = null;
        InputStreamReader clientInr = null;
        OutputStreamWriter clientOutw = null;
        BufferedReader clientIn = null;
        BufferedWriter clientOut = null;

        // connecting and setting everything up based on the socket.
        try{
            ClientSocket = new Socket("localHost",4444);
            clientInr = new InputStreamReader(ClientSocket.getInputStream());
            clientOutw = new OutputStreamWriter(ClientSocket.getOutputStream());
            clientIn = new BufferedReader(clientInr);
            clientOut = new BufferedWriter(clientOutw);

            Scanner scanner = new Scanner(System.in);


            // as long as the user doesn't type exit, we can send messages into group chat.
            while(true){
                String Message = scanner.nextLine();
                clientOut.write(Message);
                // we use newLine method to tell the program this is where the message ends as it's not a huge ass message
                clientOut.newLine();
                // immediate sending of the message is done by the method below
                clientOut.flush();


                System.out.println("Server: " + clientIn.readLine());

                if(Message.equalsIgnoreCase("exit")){
                    break;
                }
            }
        }
        // shutting down everything because why not :D
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            try{
                if(ClientSocket != null && clientInr != null && clientOut != null && clientIn != null && clientOutw != null){
                    ClientSocket.close();
                    clientInr.close();
                    clientOutw.close();
                    clientIn.close();
                    clientOut.close();
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

}
