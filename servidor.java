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

public class servidor implements Runnable {

    private static Socket servidorSocket = null;

    private static BufferedReader in = null;
    private static PrintWriter out = null;

    public static void main(String args[]) throws UnknownHostException, IOException {
        String host = "localhost";

        try {
            servidorSocket = new Socket(host, 54321);

            in = new BufferedReader(new InputStreamReader(servidorSocket.getInputStream()));
            out = new PrintWriter(servidorSocket.getOutputStream(), true);

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host " + host);
        }

        if (servidorSocket != null && out != null) {
            try {
                new Thread(new servidor()).start();
            } catch (Exception e) {
                System.err.println("Exception:  " + e);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String line = in.readLine();

                System.out.println("LEU" + line);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}