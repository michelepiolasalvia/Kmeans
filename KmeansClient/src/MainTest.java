import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import eccezioni.ServerException;
import keyboardinput.Keyboard;


/**
 * Classe MainTest del client. 
 * Punto di ingresso per il client, regola il menù e la comunicazione col server
 */
public class MainTest {

	
	/** flusso di output */
	private ObjectOutputStream out;
	/** flusso di input */
	private ObjectInputStream in ; 
	
	
	/**
	 * Costruttore della classe MainTest.
	 * Tenta la connessione col serverSocket.
	 * @param ip indirizzo del server al quale ci si vuole connettere.
	 * @param port porta sulla quale si vuole avvenga la comunicazione.
	 * @throws IOException eccezione nel caso in cui fallisca la connessione
	 */
	public MainTest(String ip, int port) throws IOException{
		InetAddress addr = InetAddress.getByName(ip); //ip
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, port); //Port
		System.out.println(socket);
		
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());	; // stream con richieste del client
	}
	
	/**
	 * Funzione menu.
	 * Funzione che regola la scelta dell'operazione da eseguire da parte dell'utente.
	 * @return 1 se si vuole caricare un file pre-esistente 2 se si vuole interagire col db
	 */
	private int menu(){
		int answer;
		System.out.println("Scegli una opzione");
		do{
			System.out.println("(1) Carica Cluster da File");
			System.out.println("(2) Carica Dati");
			System.out.print("Risposta:");
			answer=Keyboard.readInt();
		}
		while(answer<=0 || answer>2);
		return answer;
		
	}

	/**
	 * Funzione learningFromFile.
	 * Richiamata quando l'utente vuole leggere un risultato ottenuto precedentemente,
	 * salvato su un file.
	 * @return il risultato della lettura del file.
     * @throws SocketException Se si verifica un errore nella comunicazione tramite socket.
     * @throws ServerException Se il server restituisce un errore durante l'operazione di memorizzazione.
     * @throws IOException Se si verifica un errore durante la comunicazione con il server.
     * @throws ClassNotFoundException Se si verifica un errore nel caricamento delle classi serializzate.
	 */
	private String learningFromFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(3);
		
		System.out.print("Nome tabella:");
		String tabName=Keyboard.readString();
		out.writeObject(tabName);
		System.out.print("Numero iterate:");
		int k=Keyboard.readInt();
		out.writeObject(k);
		String result = (String)in.readObject();
		if(result.equals("OK"))
			return (String)in.readObject();
		else throw new ServerException("Errore caricamento file.");
		
	}
	
	/**
     * Memorizza i dati provenienti da una tabella del database.
     * @throws SocketException Se si verifica un errore nella comunicazione tramite socket.
     * @throws ServerException Se il server restituisce un errore durante l'operazione di memorizzazione.
     * @throws IOException Se si verifica un errore durante la comunicazione con il server.
     * @throws ClassNotFoundException Se si verifica un errore nel caricamento delle classi serializzate.
     */
	private void storeTableFromDb() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(0);
		System.out.print("Nome tabella:");
		String tabName=Keyboard.readString();
		out.writeObject(tabName);
		String result = (String)in.readObject();
		if(!result.equals("OK"))
			throw new ServerException("Errore apertura DB");	
	}
	
	/**
	 * Funzione learningFromDbTable.
	 * Richiamata quando l'utente vuole inviare una nuova richiesta al server,
	 * il quale risponde col risultato ottenuto con i valori richiesti dall'utente.
	 * @return il risultato dell'operazione di Kmeans come stringa.
     * @throws SocketException Se si verifica un errore nella comunicazione tramite socket.
     * @throws ServerException Se il server restituisce un errore durante l'operazione di memorizzazione.
     * @throws IOException Se si verifica un errore durante la comunicazione con il server.
     * @throws ClassNotFoundException Se si verifica un errore nel caricamento delle classi serializzate.
	 */
	private String learningFromDbTable() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(1);
		System.out.print("Numero di cluster:");
		int k=Keyboard.readInt();
		out.writeObject(k);
		String result = (String)in.readObject();
		if(result.equals("OK")){
			result = "Clustering output:\n"+in.readObject();
			return (String) result;
		}
		else throw new ServerException("Errore lettura da DB");
		
		
	}
	
	/**
	 * Procedura StoreClusterInFile.
	 * Salva il risultato ottenuto da learningFromDbTable su file.
     * @throws SocketException Se si verifica un errore nella comunicazione tramite socket.
     * @throws ServerException Se il server restituisce un errore durante l'operazione di memorizzazione.
     * @throws IOException Se si verifica un errore durante la comunicazione con il server.
     * @throws ClassNotFoundException Se si verifica un errore nel caricamento delle classi serializzate.
	 */
	private void storeClusterInFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(2);
		
		
		String result = (String)in.readObject();
		if(!result.equals("OK"))
			 throw new ServerException(result);
		
	}
	
	/**
	 * Main del client.
	 * Se avviene la connessione col serverSocket esegue il loop di interazione col server,
	 * tramite il quale è possibile effettuare richieste.
	 * @param args indirizzo ip e porta inseriti come argomenti da linea di comando.
	 */
	public static void main(String[] args) {
		String ip=args[0];
		String answer = "";
		int port=new Integer(args[1]).intValue();
		MainTest main=null;
		try{
			main=new MainTest(ip,port);
		}
		catch (IOException e){
			System.out.println(e);
			return;
		}
		
		
		do{
			int menuAnswer=main.menu();
			sceltaOpzione : switch(menuAnswer)
			{
				case 1:
					try {
						String kmeans=main.learningFromFile();
						System.out.println(kmeans);
					}
					catch(java.io.EOFException e) {
						System.out.println("File non esistente");
					}
					
					catch (SocketException e) {
						System.out.println(e);
						return;
					}
					catch (FileNotFoundException e) {
						System.out.println(e);
						return ;
					} catch (IOException e) {
						System.out.println(e);
						return;
					} catch (ClassNotFoundException e) {
						System.out.println(e);
						return;
					}
					catch (ServerException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2: // learning from db
				
					while(true){
						try{
							main.storeTableFromDb();
							break; //esce fuori dal while
						}
						
						catch (SocketException e) {
							System.out.println(e);
							return;
						}
						catch (FileNotFoundException e) {
							System.out.println(e);
							return;
							
						} catch (IOException e) {
							System.out.println(e);
							return;
						} catch (ClassNotFoundException e) {
							System.out.println(e);
							return;
						}
						catch (ServerException e) {
							System.out.println(e.getMessage());
							break sceltaOpzione;
							
						}
					} //end while [viene fuori dal while con un db (in alternativa il programma termina)
						
					answer="y";//itera per learning al variare di k
					do{
						try
						{
							String clusterSet=main.learningFromDbTable();
							System.out.println(clusterSet);
							
							main.storeClusterInFile();
									
						}
						catch (SocketException e) {
							System.out.println(e);
							return;
						}
						catch (FileNotFoundException e) {
							System.out.println(e);
							return;
						} 
						catch (ClassNotFoundException e) {
							System.out.println(e);
							return;
						}catch (IOException e) {
							System.out.println(e);
							return;
						}
						catch (ServerException e) {
							System.out.println(e.getMessage());
						}
						do {
							System.out.print("Vuoi ripetere l'esecuzione?(y/n)");
							answer=Keyboard.readString();
						}
						while(!answer.equals("y") && !answer.equals("n"));
					}
					while(answer.equals("y"));
					break; //fine case 2
					default:
					System.out.println("Opzione non valida!");
			}
			do{
				System.out.print("Vuoi scegliere una nuova operazione da menu?(y/n)");
				answer = Keyboard.readString();
			}
			while(!answer.equals("y") && !answer.equals("n"));
			}
		while(answer.equals("y"));
		}
	}