import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class DNSServer {
    private static final int PORT = 4000; // Port to run the server on
    private static final Map<String, String> DNS_DATABASE = new HashMap<>();

    static {
        // Populate some example domains and IPs
        DNS_DATABASE.put("example.com", "93.184.216.34");
        DNS_DATABASE.put("google.com", "142.250.183.238");
        DNS_DATABASE.put("yahoo.com", "74.6.143.25");
    }

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("DNS Server is running on port " + PORT);

            byte[] buffer = new byte[1024];

            while (true) {
                // Receive the request
                DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(requestPacket);
                String domain = new String(requestPacket.getData(), 0, requestPacket.getLength()).trim();
                System.out.println("Received query for domain: " + domain);

                // Resolve the domain
                String ipAddress = DNS_DATABASE.getOrDefault(domain, "Domain not found");
                byte[] responseBytes = ipAddress.getBytes();

                // Send the response
                DatagramPacket responsePacket = new DatagramPacket(
                    responseBytes, responseBytes.length, requestPacket.getAddress(), requestPacket.getPort()
                );
                socket.send(responsePacket);
                System.out.println("Responded to client: " + ipAddress);
            }
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}
