import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class cliente implements Runnable {

    private static Socket clienteSocket = null;

    private static BufferedReader in = null;
    private static PrintWriter out = null;

    public static void main(String args[]) throws UnknownHostException, IOException {
        String host = "localhost";

        try {
            clienteSocket = new Socket(host, 12345);
            in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            out = new PrintWriter(clienteSocket.getOutputStream(), true);

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host " + host);
        }

        if (clienteSocket != null && out != null) {
            try {
                new Thread(new cliente()).start();
            } catch (Exception e) {
                System.err.println("Exception:  " + e);
            }
        }

    }

    @Override
    public void run() {

        Random rand = new Random();
        while (true) {
            if (rand.nextInt(100) % 2 == 0) {
                out.println("Leitura");
            } else {
                int num = rand.nextInt(999999)+1;
                out.println(Integer.toString(num));
            }
            try {
                int time = rand.nextInt(150) + 50;
                Thread.sleep(time);
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }

        }
    }
}