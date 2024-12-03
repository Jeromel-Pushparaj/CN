import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static Set<Socket> clientSockets = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) throws IOException {
        int port = 4000;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Chat server started on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            clientSockets.add(clientSocket);
            System.out.println("New client connected: " + clientSocket.getInetAddress());
            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received: " + message);
                broadcastMessage(message, clientSocket);
            }
        
        } catch (IOException e) {
            System.out.println("Client disconnected: " + clientSocket.getInetAddress());
        } finally {
            try {
                clientSockets.remove(clientSocket);
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private static void broadcastMessage(String message, Socket sender) {
        synchronized (clientSockets) {
            for (Socket socket : clientSockets) {
                if (socket != sender) {
                    try {
                        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                        writer.println(message);
                    } catch (IOException e) {
                        System.out.println("Error broadcasting message: " + e.getMessage());
                    }
                }
            }
        }
    }
}
