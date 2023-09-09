import java.io.*;
import java.net.Socket;
import java.nio.file.spi.FileSystemProvider;
import java.util.zip.Inflater;

import data.Data;
import mining.ClusterSet;
import mining.KMeansMiner;

/**
 * Classe ServerOneClient.
 * Gestisce un socket per la comunicazione col client,
 * estende la classe Thread per poter avere più comunicazioni
 * client-server in parallelo.
 */
public class ServerOneClient extends Thread{
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;
	KMeansMiner kmeans;
	
	/**
	 * Costruttore della classe ServerOneClient.
	 * Riceve un socket dal multiServer quando accept() ha successo nel serverSocket.
	 * Crea i flussi di stream di I/O tramite le funzioni getInputstream e getOutputStream del socket.
	 * Infine starta il thread ed inizia a ricevere ed inviare dati.
	 * @param s Socket creato dal MultiServer quando accetta una connessione da client. 
	 * @throws IOException Se si verifica un errore durante la comunicazione
	 */
	public ServerOneClient(Socket s) throws IOException{
		this.socket = s;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
        
        start();
	}
	
	/**
	 * Override della procedura run della classe Thread.
	 * In base alle opzioni del menu scelte dal client, processa le richieste
	 * ed invia al client le risposte attese.
	 * Chiude la connessione ed il socket quando termina la comunicazione.
	 */
	@Override
	public void run() {
		while(true) {
			System.out.println("Client connesso.");
			ObjectInputStream inF = null;
			Object nomeTable = null;
			Data data = null;
			try {
				while(true) {		
					Object scelta = in.readObject();
					if((int)scelta == 3) {
						nomeTable = in.readObject();
					    Object numCl = in.readObject();
					    String fileName = nomeTable+"_"+numCl+".dat";
					    inF = new ObjectInputStream(new FileInputStream("Salvataggi\\"+fileName));
					    out.writeObject("OK");
				    	kmeans = new KMeansMiner("Salvataggi\\"+fileName);  	
					    Object response = kmeans.getC().toString();
				    	out.writeObject(response);
			       
					} else if((int) scelta == 0) {
			        	nomeTable = in.readObject();
			        	try {
							data = new Data((String) nomeTable); 
							out.writeObject("OK");
						} catch (Exception e) {
							out.writeObject("NO");
						}
			        } else if((int) scelta == 1) {
		        		Object numCl = in.readObject();
		        		if((int) numCl > 0 && (int) numCl <= data.getNumberOfExamples()) {
			        		KMeansMiner kmeans = new KMeansMiner((int) numCl);             
			        		kmeans.kmeans(data);
			        		out.writeObject("OK");
			        		out.writeObject(kmeans.getC().toString(data));
			        		if((int) in.readObject() == 2) {
			        			String fileName = nomeTable+"_"+numCl+".dat";
			        			kmeans.salva("Salvataggi\\"+fileName);
			        			out.writeObject("OK");
			        		}
		        		}else{
		        			out.writeObject("NO");
		        		}
		        	}
				}
		
		        
			} catch (java.net.SocketException e) {
				System.out.println("Client Disconnesso.");
				try {
		        	// Chiudi le risorse
					in.close();
			        out.close();
					socket.close();
					if(inF != null) {
						inF.close();
					}
					break;
				} catch (IOException e1) {
					System.out.println("Errore nella chiusura delle risorse.");
				}
			} catch (java.io.FileNotFoundException e) {
				System.out.println("File non trovato");
				try {
					out.writeObject("NO");
				} catch (IOException e1) {
					System.out.println("errore invio messaggio");
				}
			} catch (Exception e) {
				System.out.print(e);
				System.out.println("Client Disconnesso.");
				try {
		        	// Chiudi le risorse
					in.close();
			        out.close();
					socket.close();
					inF.close();
					break;
				} catch (IOException e1) {
					System.out.println("Errore nella chiusura delle risorse.");
				}
				break;
		    }
		}
	}
}
