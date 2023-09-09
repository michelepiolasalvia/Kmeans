import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe MultiServer.
 * Crea il serverSocket e gestice il multithreading.
 */
public class MultiServer {
	/**Porta messa a disposizione dal server per la connessione**/
	private int port = 8080;
	
	/**
	 * Main della classe MultiServer.
	 * Tenta la creazione di un serverSocket sulla porta
	 * @param args Argomenti da linea di comando
	 * @throws IOException Se si verifica un errore durante la comunicazione
	 */
	public static void main(String[] args) throws IOException {
		MultiServer mt = new MultiServer(8080);
	}
	
	/**
	 * Costruttore della classe MultiServer.
	 * Imposta la porta sulla quale creare il serverSocket e richiama
	 * la procedura run().
	 * @param port porta sulla quale si apre la connessione.
	 * @throws IOException Se si verifica un errore durante la comunicazione.
	 */
	public MultiServer(int port) throws IOException {
		this.port = port;
		run();
	}
	
	/**
	 * Procedura run.
	 * Attende richieste di connessione da parte dei client tramite la funzione accept(),
	 * se riceve una richiesta tenta di mettere a disposizione un socket per la comunicazione.
	 * @throws IOException Se si verifica un errore durante la comunicazione.
	 */
	public void run() throws IOException {
		ServerSocket serverSocket = null;
		serverSocket = new ServerSocket(port);
        System.out.println("Server avviato sulla porta " + port);
        try {
            while (true) {
                System.out.println("In attesa di connessioni...");
                Socket socket = serverSocket.accept();
                try {
                	new ServerOneClient(socket);
				} catch (IOException e) {
					socket.close();
				}
            }
        } finally {
        	serverSocket.close();
        }

	}
}
      

