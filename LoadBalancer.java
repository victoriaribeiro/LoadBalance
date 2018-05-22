import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class LoadBalancer {

	private static ServerSocket socketServer = null;
	private static ServerSocket socketClient = null;
	private static Socket clientSocket = null;
	private static Socket conectServerSocket = null;
	// public static int time = 0;
	ArrayBlockingQueue fila = new ArrayBlockingQueue<String>(5000);
	// Fila fila = new Fila();

	private static BufferedReader in = null;

	private PrintStream os = null;

	private static final int maxClientsCount = 2;
	private static final int maxServersCount = 3;
	// private static final clientThread[] threadsClient = new
	// clientThread[maxClientsCount];
	// private static final serverThread[] threadsServer = new
	// serverThread[maxServersCount];

	public static void main(String[] args) throws IOException {
		new LoadBalancer().run();
		// try {

		// } catch (IOException e) {
		// System.out.println(e);
		// }
		// System.out.println("Porta 12345 aberta!");

		// System.out.println("Aguardando conexão do cliente...");

		// while (true) {
		// try {
		// clientThread c1 = new clientThread(socketClient.accept());

		// clientSocket = socketClient.accept();

		// if (clientSocket != null) {

		// System.out.println("cliente conectado " + clientSocket);
		// in = new BufferedReader(new
		// InputStreamReader(clientSocket.getInputStream()));

		// while (true) {
		// try {
		// String line = in.readLine();

		// System.out.println(line);

		// } catch (IOException ex) {
		// System.err.println(ex);
		// }
		// }
		// }

		// conectServerSocket = socketServer.accept();

		// if (conectServerSocket != null) {
		// System.out.println("server conectado " + conectServerSocket);
		// }

		// int i = 0;

		// for (i = 0; i < maxServersCount; i++) {
		// if (threadsServer[i] == null) {
		// (threadsServer[i] = new serverThread(conectServerSocket,
		// threadsServer)).start();
		// break;
		// }
		// }

		// for (i = 0; i < maxClientsCount; i++) {
		// if (threadsClient[i] == null) {
		// (threadsClient[i] = new clientThread(clientSocket, threadsClient)).start();
		// break;
		// }
		// }
		// if (i == maxClientsCount) {
		// PrintStream os = new PrintStream(clientSocket.getOutputStream());
		// System.out.println("O servidor está muito ocupado. Tente mais tarde.");
		// clientSocket.close();
		// }

		// } catch (IOException e) {
		// System.out.println(e);
		// }
		// }
	}

	public void run() {

		try {
			socketClient = new ServerSocket(12345);
			socketServer = new ServerSocket(54321);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		while(true){
			try{
				new clientThread(socketClient.accept(), fila).start();
				new serverThread(socketServer.accept(),fila).start();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}

	}
}

class clientThread extends Thread {

	ArrayBlockingQueue<String> fila = null;
	private BufferedReader in = null;
	private Socket clientSocket = null;


	public clientThread(Socket clientSocket, ArrayBlockingQueue fila) {
		this.clientSocket = clientSocket;
		this.fila = fila;
	}

	public void run() {

		try {
			in = new BufferedReader(new	InputStreamReader(clientSocket.getInputStream()));

			while (true) {
				String line = in.readLine();
				if(line !=null){
					fila.add(line);				
					//fila.printFila();
				}				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

class serverThread extends Thread {

	ArrayBlockingQueue<String> fila = null;
	private BufferedReader in = null;
	private Socket serverSocket = null;


	public serverThread(Socket serverSocket, ArrayBlockingQueue fila) {
		this.serverSocket = serverSocket;
		this.fila = fila;
	}

	public void run() {

		while(true){
			System.out.println(fila.toString());
		}

		// try {
		// 	in = new BufferedReader(new	InputStreamReader(serverSocket.getInputStream()));
			
		// 	String name = is.readLine().trim();
		// 	while (true) {
		// 		String line = is.readLine();

		// 		int num = Integer.parseInt(line);
		// 		f.add(line);
		// 		System.out.println("<" + name + "&gr; " + line);
		// 		f.printFila();
		// 	}
		// } catch (IOException e) {

		// }

	}
}
