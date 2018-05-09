import java.io.*;
import java.net.*;
import java.util.*;

public class ClientTestTwo {
    private static InetAddress host;
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            host = InetAddress.getLocalHost();
        }
        catch(UnknownHostException uhEx)
        {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        accessServer();
    }

    private static void accessServer() {
        Socket link = null;

        try {
            link = new Socket(host,PORT);
            Scanner input =	new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream());

            String message = "test string";
            output.println(message);

            String response = input.nextLine();
            while (!response.equals("2")) {
            }
            System.out.println("\nSERVER> BOTH CLIENTS CONNECTED");
        } catch(IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            System.out.println("\n* Closing connection... *");
            while (true) {

            }
        }
    }
}