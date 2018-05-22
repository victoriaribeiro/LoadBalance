import java.net.*;
import java.io.*;
import java.util.*;

public class LoadBalancer {

	private static ServerSocket socketServer = null;
	private static ServerSocket socketClient = null;
	private static Socket clientSocket = null;
	private static Socket conectServerSocket = null;
	// public static int time = 0;
	Fila fila = new Fila();
	
	private static BufferedReader in = null;

	private PrintStream os = null;

	private static final int maxClientsCount = 2;
	private static final int maxServersCount = 3;
	private static final clientThread[] threadsClient = new clientThread[maxClientsCount];
	private static final serverThread[] threadsServer = new serverThread[maxServersCount];

	public static void main(String[] args) throws IOException {

		try {
			socketClient = new ServerSocket(12345);
			socketServer = new ServerSocket(54321);
		} catch (IOException e) {
			System.out.println(e);
		}
		System.out.println("Porta 12345 aberta!");

		System.out.println("Aguardando conexão do cliente...");

		while (true) {
			try {
				clientSocket = socketClient.accept();
				if (clientSocket != null) {

					System.out.println("cliente conectado " + clientSocket);
					in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

					

					while (true) {
						try {
							String line = in.readLine();							

							System.out.println(line);

						} catch (IOException ex) {
							System.err.println(ex);
						}
					}
				}

				conectServerSocket = socketServer.accept();

				if (conectServerSocket != null) {
					System.out.println("server conectado " + conectServerSocket);
				}

				int i = 0;

				for (i = 0; i < maxServersCount; i++) {
					if (threadsServer[i] == null) {
						(threadsServer[i] = new serverThread(conectServerSocket, threadsServer)).start();
						break;
					}
				}

				for (i = 0; i < maxClientsCount; i++) {
					if (threadsClient[i] == null) {
						(threadsClient[i] = new clientThread(clientSocket, threadsClient)).start();
						break;
					}
				}
				if (i == maxClientsCount) {
					PrintStream os = new PrintStream(clientSocket.getOutputStream());
					System.out.println("O servidor está muito ocupado. Tente mais tarde.");
					clientSocket.close();
				}

			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}


class clientThread extends Thread {

	Fila f = new Fila();
	private DataInputStream is = null;
	private Socket clientSocket = null;
	private final clientThread[] threads;
	private int maxClientsCount;

	public clientThread(Socket clientSocket, clientThread[] threads) {
		this.clientSocket = clientSocket;
		this.threads = threads;
		maxClientsCount = threads.length;
	}

	public void run() {
		int maxClientsCount = this.maxClientsCount;
		clientThread[] threads = this.threads;

		try {
			is = new DataInputStream(clientSocket.getInputStream());
			String name = is.readLine().trim();
			while (true) {
				String line = is.readLine();

				int num = Integer.parseInt(line);
				f.add(num);
				System.out.println("<" + name + "&gr; " + line);
				f.printFila();
			}
		} catch (IOException e) {

		}

	}
}

class serverThread extends Thread {

	Fila f = new Fila();
	private DataInputStream is = null;
	private Socket serverSocket = null;
	private final serverThread[] threads;
	private int maxServersCount;

	public serverThread(Socket serverSocket, serverThread[] threads) {
		this.serverSocket = serverSocket;
		this.threads = threads;
		maxServersCount = threads.length;
	}

	public void run() {
		int maxServersCount = this.maxServersCount;
		serverThread[] threads = this.threads;

		try {
			is = new DataInputStream(serverSocket.getInputStream());
			String name = is.readLine().trim();
			while (true) {
				String line = is.readLine();

				int num = Integer.parseInt(line);
				f.add(num);
				System.out.println("<" + name + "&gr; " + line);
				f.printFila();
			}
		} catch (IOException e) {

		}

	}
}
