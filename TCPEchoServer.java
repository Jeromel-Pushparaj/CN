import java.io.*;
import java.net.*;

public class TCPEchoServer {
    public static void main(String[] args) {
        int port = 4000; // Server port

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Echo server is running on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle client connection
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            PrintWriter writer = new PrintWriter(output, true);
        ) {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received: " + message);
                writer.println("Echo: " + message); // Send the echo response
            }
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Client disconnected");
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
