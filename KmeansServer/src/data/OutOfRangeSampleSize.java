package data;
/**
 * Classe per gestire l'inserimento di un input OutOfRange.
 */
public class OutOfRangeSampleSize extends Exception{
	
	/**
	 * Procedura che stampa l'errore dell'input.
	 */
	public OutOfRangeSampleSize()
	{
		super("Il numero k di cluster inserito da tastiera è <= di 0 o maggiore rispetto al numero di centroidi generabili dall'insieme di transazioni.");
	}
}
