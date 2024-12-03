import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Download {
    public static void main(String[] args) {
        try {
            String fileName = "image.jpg";
            String website = "https://via.placeholder.com/150";

            System.out.println("Downloading File From : " + website);

            // Create a URL object
            URL url = new URL(website);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set User-Agent header to mimic a browser
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");

            // Get the input stream
            InputStream inputStream = connection.getInputStream();

            // Create an output stream to save the file
            OutputStream outputStream = new FileOutputStream(fileName);

            byte[] buffer = new byte[2048];
            int length;

            // Read from the input stream and write to the output stream
            while ((length = inputStream.read(buffer)) != -1) {
                System.out.println("Buffer Read of length: " + length);
                outputStream.write(buffer, 0, length);
            }

            // Close streams
            inputStream.close();
            outputStream.close();

            System.out.println("File downloaded successfully: " + fileName);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
