import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 4000;

        try (
            Socket socket = new Socket(host, port);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Connected to the chat server. Start typing messages...");

            // Thread to read messages from the server
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = serverReader.readLine()) != null) {
                        System.out.println("Server: " + serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            // Main thread to send messages to the server
            String userMessage;
            while ((userMessage = consoleReader.readLine()) != null) {
                writer.println(userMessage);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
