import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class LoadBalancer {

	private static ServerSocket socketServer = null;
	private static ServerSocket socketClient = null;
	private Semaphore semaphore = new Semaphore(1);
	ArrayBlockingQueue fila = new ArrayBlockingQueue<String>(5000);


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
				new serverThread(socketServer.accept(), socketServer.accept(), socketServer.accept(),fila, semaphore).start();
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
	private Socket serverSocket2 = null;
	private Socket serverSocket3 = null;
	private String operacao = null;
	private Semaphore semaphore = null;
	private PrintWriter out1 = null;
	private PrintWriter out2= null;
	private PrintWriter out3 = null;
	Random rand = new Random();


	public serverThread(Socket serverSocket, Socket serverSocket2, Socket serverSocket3, ArrayBlockingQueue fila, Semaphore semaphore) {
		this.serverSocket = serverSocket;
		this.serverSocket2 = serverSocket2;
		this.serverSocket3 = serverSocket3;
		this.fila = fila;
		this.semaphore = semaphore;
		try{
			out1 = new PrintWriter(serverSocket.getOutputStream(), true);
			out2 = new PrintWriter(serverSocket2.getOutputStream(), true);
			out3 = new PrintWriter(serverSocket3.getOutputStream(), true);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void run() {



		int num = 0;
		
		while(true){
			try {
				this.operacao = fila.take();
			} catch (Exception e) {				
				e.printStackTrace();
			}

			num = rand.nextInt(3)+1;

			switch (num) {
				case 1:
					out1.println("servidor 1");
					break;
				case 2:
					out2.println("servidor 2");
					break;
				case 3:
					out3.println("servidor 3");
					break;
				default:
					break;
			}

		}

	}
}
