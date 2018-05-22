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

    private static Socket clienteS = null;
    private static DataInputStream is = null;

    public static void main(String args[]) throws UnknownHostException, IOException {
        String host = "localhost";

        try {
            clienteS = new Socket(host, 54321);
            is = new DataInputStream(clienteS.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host " + host);
        }

        if (clienteS != null && is != null) {
            String responseLine;
            try {
                while ((responseLine = is.readLine()) != null) {
                    System.out.println(responseLine);
                    if (responseLine.indexOf("*** Bye") != -1)
                        break;
                }
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }

    }

    @Override
    public void run() {

    }
}