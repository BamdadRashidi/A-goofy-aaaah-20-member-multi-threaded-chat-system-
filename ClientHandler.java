import java.io.*;
import java.net.Socket;
import java.util.Random;

public class ClientHandler implements Runnable {


    /*
            what this class does basically is that it allows connection from one client to the server and vice-versa
            using this it takes the message written from the client and sends it to the server acting like a bridge of sorts utilizing the decorator design structure
            basically client1.write("blah blah"); ---------> socket.getinputstream ---------> clienthandler.bufferedreader (this will get the message and yeets it into the server)
            reverse of this applies as well
     */
    private static int counter = 1;
    private static final Object lock = new Object();
    private Socket socket;
    private int id;

    // creating a lock so that only one thread has access to the id attribute at a time and can assign a unique id from 1 to 20 at a time.
    public ClientHandler(Socket socket) {
        synchronized (lock) {
            Random rand = new Random();
            this.id = counter++;
        }
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                //setting shit up fo reading and writing
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ) {
            // same thing with client class from here onward
            String message;
            while ((message = br.readLine()) != null) {
                System.out.println("Client" + id + ": " + message);
                bw.write("got message");
                bw.newLine();
                bw.flush();

                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}
