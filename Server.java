import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    /* Here we have 2 variables for the port number for everyone to connect to and the maximum amount of clients
    * before the computer fucking blows up!
    */
    private static final int PORT = 4444;
    private static final int MAX_CLIENTS = 20;

    public static void main(String[] args) {

        // we create a thread pool so that we have different users (make sure the client class is allowed to have multiple versions of it to be executed)
        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);

        // we utilize try and catch to make a ServerSocket which acts as a pathway for all clients to connect to the server.
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // message to make sure what in the fuck is going on.
            System.out.println("Server is listening on port " + PORT);

            /* this part runs forever and it is responsible for waiting for a client to connect
            * once someone connects, a new client handler object is made to handle well.... clients and execute whatever code is in the clienthandler.
            */

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                pool.execute(clientHandler);
            }

            // normal stuff
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}
