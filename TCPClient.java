import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        String host = "localhost"; // Server hostname
        int port = 4000;          // Server port

        try (
            Socket socket = new Socket(host, port);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            System.out.println("Connected to the server. Type your messages:");

            String userInput;
            while ((userInput = consoleReader.readLine()) != null) {
                writer.println(userInput); // Send message to server
                String response = reader.readLine(); // Receive response
                System.out.println(response); // Print server response
            }
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
