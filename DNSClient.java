import java.net.*;
import java.util.Scanner;

public class DNSClient {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter the DNS server address (e.g., localhost): ");
            String serverAddress = scanner.nextLine();
            InetAddress serverInetAddress = InetAddress.getByName(serverAddress);

            System.out.println("Enter a domain name to resolve (or type 'exit' to quit):");
            while (true) {
                System.out.print("Domain: ");
                String domain = scanner.nextLine();

                if (domain.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                }

                // Send the query
                byte[] requestBytes = domain.getBytes();
                DatagramPacket requestPacket = new DatagramPacket(
                    requestBytes, requestBytes.length, serverInetAddress, 4000
                );
                socket.send(requestPacket);

                // Receive the response
                byte[] responseBytes = new byte[1024];
                DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length);
                socket.receive(responsePacket);
                String response = new String(responsePacket.getData(), 0, responsePacket.getLength()).trim();
                System.out.println("Resolved IP address: " + response);
            }
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
