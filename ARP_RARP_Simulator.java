import java.util.HashMap;
import java.util.Scanner;

public class ARP_RARP_Simulator {

    private HashMap<String, String> arpTable;

    public ARP_RARP_Simulator() {
        arpTable = new HashMap<>();

        // Adding some dummy data for simulation
        arpTable.put("192.168.1.1", "00:14:22:01:23:45");
        arpTable.put("192.168.1.2", "00:14:22:01:23:46");
        arpTable.put("192.168.1.3", "00:14:22:01:23:47");
    }

    // Simulate ARP: IP to MAC resolution
    public String resolveIPtoMAC(String ipAddress) {
        return arpTable.getOrDefault(ipAddress, "MAC Address not found");
    }

    // Simulate RARP: MAC to IP resolution
    public String resolveMACtoIP(String macAddress) {
        for (String ip : arpTable.keySet()) {
            if (arpTable.get(ip).equals(macAddress)) {
                return ip;
            }
        }
        return "IP Address not found";
    }

    public static void main(String[] args) {
        ARP_RARP_Simulator simulator = new ARP_RARP_Simulator();
        Scanner scanner = new Scanner(System.in);

        System.out.println("ARP/RARP Simulator");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Resolve IP to MAC (ARP)");
            System.out.println("2. Resolve MAC to IP (RARP)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter IP Address: ");
                    String ipAddress = scanner.nextLine();
                    String macAddress = simulator.resolveIPtoMAC(ipAddress);
                    System.out.println("MAC Address: " + macAddress);
                    break;

                case 2:
                    System.out.print("Enter MAC Address: ");
                    String inputMac = scanner.nextLine();
                    String resolvedIP = simulator.resolveMACtoIP(inputMac);
                    System.out.println("IP Address: " + resolvedIP);
                    break;

                case 3:
                    System.out.println("Exiting simulator.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
