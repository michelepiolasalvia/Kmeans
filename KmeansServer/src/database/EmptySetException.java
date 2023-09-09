package database;

/**
 * Class EmptySetException.
 * Richiamata quando si ha un resultset vuoto in seguito ad una query.
 * Estende la classe Exception.
 */
public class EmptySetException extends Exception{
	
	/**
	 * Costruttore della classe EmptySetException.
	 * Richiama il costruttore della classe madre Exception passando come parametro un messaggio.
	 */
	public EmptySetException() {
		super("Il result set è vuoto");
	}
}
