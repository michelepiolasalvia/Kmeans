package database;

/**
 * Class NoValueException.
 * Richiamata quando la query non genera nessun valore.
 * Estende la classe Exception.
 */
public class NoValueException extends Exception{
	
	/**
	 * Costruttore della classe NoValueException.
	 * Richiama il costruttore della classe madre Exception passando come parametro un messaggio.
	 */
	public NoValueException() {
		super("Il valore non esiste nel result set");
	}
}
