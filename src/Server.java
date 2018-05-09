import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private static ServerSocket serverSocket;
    private static final int PORT = 1234;
    private static ArrayList<ServerThread> clientThreads = new ArrayList<ServerThread>();
    public static int connectedCount = 0;
    public static String phrase = null;

    public static void main(String[] args) {

        System.out.println("Opening port...\n");
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for client to connect");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected!");
                connectedCount++;
                ServerThread serverThread = null;
                if (connectedCount == 1) {
                    serverThread = new ServerThread(socket, 2);
                } else {
                    serverThread = new ServerThread(socket,1);
                }
                clientThreads.add(serverThread);
                serverThread.start();
            }
        } catch(IOException ioEx) {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }

        System.out.println("Closing server...");
        System.exit(0);
    }
}

class ServerThread extends Thread {
    Socket s;
    Scanner socketInput;
    PrintWriter socketOutput;
    int numOfInts;

    public ServerThread(Socket s, int numOfInts) {
        this.s = s;
        this.numOfInts = numOfInts;
        try {
            socketInput = new Scanner(s.getInputStream());
            socketOutput = new PrintWriter(s.getOutputStream(), true);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    public void run() {
        do {
            String input[] = socketInput.nextLine().split(":");
            for (int i = 0; i < input.length; i++) {
                System.out.println(input[i]);
            }

            if (input[0].equals("Retrieve")) {
                System.out.println(Server.phrase);
                socketOutput.println(Server.phrase);
                return;
            } else if (input[0].charAt(0) == '1') {
                socketOutput.println("1 connected");
            } else if (input[0].charAt(0) == '2') {
                socketOutput.println("2 connected");
            } else {
                System.out.println("IT GOT INTO HERE ON " + input[0]);
                if (input[0].equals("SET")) {
                    System.out.println(input[1]);
                    Server.phrase = input[1];
                    System.out.println(input[0] + input[1]);
                } else {
                    if (Server.phrase.toUpperCase().contains(input[1])) {
                        socketOutput.println("YES");
                        System.out.println(input[0]);
                    } else {
                        socketOutput.println("NO");
                        System.out.println(input[1]);
                    }
                }
            }

            while (Server.connectedCount < 2) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException iEx) {
                    iEx.printStackTrace();
                }
            }

            if (Server.connectedCount <= 2) {
                System.out.println("Both connected");
                socketOutput.println("Both connected\n");
            }
            numOfInts--;
        } while (numOfInts == 1);
    }
}
