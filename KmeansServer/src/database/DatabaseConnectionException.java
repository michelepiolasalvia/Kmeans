package database;

/**
 * Classe DatabaseConnectionException.
 * Eccezione richiamata quando la connessione al db non ha successo.
 * Estende la classe Exception.
 */
public class DatabaseConnectionException extends Exception{
	
	/**
	 * Costruttore della classe DatabaseConnectionException.
	 * Richiama il costruttore della classe madre Exception passando un
	 * messaggio come parametro.
	 */
	public DatabaseConnectionException() {
		super("Connessione al DB fallita.");
	}
}
