import java.util.Scanner;

public class ErrorCorrectionSimulator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the original 4-bit data
        System.out.println("Enter 4 data bits (space-separated, 0 or 1):");
        int[] data = new int[4];
        for (int i = 0; i < 4; i++) {
            data[i] = scanner.nextInt();
        }

        // Generate the Hamming Code (7 bits: 4 data + 3 parity)
        int[] hammingCode = new int[7];
        hammingCode[2] = data[0];
        hammingCode[4] = data[1];
        hammingCode[5] = data[2];
        hammingCode[6] = data[3];

        // Calculate parity bits
        hammingCode[0] = hammingCode[2] ^ hammingCode[4] ^ hammingCode[6];
        hammingCode[1] = hammingCode[2] ^ hammingCode[5] ^ hammingCode[6];
        hammingCode[3] = hammingCode[4] ^ hammingCode[5] ^ hammingCode[6];

        System.out.println("Generated Hamming Code:");
        for (int bit : hammingCode) {
            System.out.print(bit + " ");
        }
        System.out.println();

        // Simulate error
        System.out.println("Enter a position to flip (1-7, or 0 for no error):");
        int errorPos = scanner.nextInt();
        if (errorPos > 0) {
            hammingCode[errorPos - 1] ^= 1; // Flip the bit
        }

        System.out.println("Hamming Code with potential error:");
        for (int bit : hammingCode) {
            System.out.print(bit + " ");
        }
        System.out.println();

        // Detect and correct error
        int p1 = hammingCode[0] ^ hammingCode[2] ^ hammingCode[4] ^ hammingCode[6];
        int p2 = hammingCode[1] ^ hammingCode[2] ^ hammingCode[5] ^ hammingCode[6];
        int p4 = hammingCode[3] ^ hammingCode[4] ^ hammingCode[5] ^ hammingCode[6];

        int errorPosition = p1 * 1 + p2 * 2 + p4 * 4;

        if (errorPosition > 0) {
            System.out.println("Error detected at position: " + errorPosition);
            hammingCode[errorPosition - 1] ^= 1; // Correct the error
            System.out.println("Corrected Hamming Code:");
            for (int bit : hammingCode) {
                System.out.print(bit + " ");
            }
            System.out.println();
        } else {
            System.out.println("No error detected.");
        }

        scanner.close();
    }
}
