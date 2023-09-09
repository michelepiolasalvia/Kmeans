package eccezioni;

/**
 * Classse ServerException.
 * Eccezione causata da una connessione fallita col server.
 */
public class ServerException extends Exception{
	
	/**
	 * Costruttore della classe ServerException.
	 * @param eccezione Stringa generata dall'eccezione.
	 */
	public ServerException(String eccezione) {
		super(eccezione);
	}
}

