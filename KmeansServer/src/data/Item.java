package data;
import java.io.Serializable;
import java.util.Set;

/**
 * classe astratta Item che modella un generico item (coppia attributo-valore).
 */
public abstract class Item implements Serializable{
	/**Attributo coinvolto nell'item.*/
	private final Attribute attribute;
	/**Valore assegnato all'attributo.*/
	private Object value;
	
	/**
	 * Costruttore della classe Item.<br>
	 * Comportamento: Inizializza i valori dei membri attributi
	 * @param attribute attributo da assegnare all'oggetto
	 * @param value valore dell'attributo
	 */
	Item(Attribute attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}

	/**
	 * Funzione per ottenere l'attributo attribute della classe.<br>
	 * Comportamento: Restituisce attribute
	 * @return Attribute L'attributo dell'item
	 */
	Attribute getAttribute() {
		return attribute;
	}
	
	/**
	 * Funzione per ottenere l'attributo value della classe.<br>
	 * Comportamente: Restituisce value
	 * @return Value value dell'item
	 */
	Object getValue() {
		return value;
	}

	
	/**
	 * Funzione che restituisce value come stringa.<br>
	 * Comportamento: Restituisce value come stringa
	 * @return Value il valore value come String
	 */	
	@Override
	public String toString() {
	    return value.toString();
	}
	
	
	/**
	 * Funzione astratta per calcolare la distanza tra due item.<br>
	 * La funzione e' astratta per avere un'implementazione diversa per item discreto e item continuo<br>
	 * @param a Oggetto del quale si vuole calcolare la distanza dall'oggetto attuale
	 * @return La distanza tra l'oggetto attuale e l'oggetto in input
	 */
	abstract double distance(Object a);
	
	/**
	 * Funzione per aggiornare il dataset.<br>
	 * Comportamento: Modifica il membro value, assegnandogli il valore restituito da data.computePrototype(clusteredData,attribute);
	 * @param data dataset data
	 * @param clusteredData insieme di indici delle righe della matrice in data che formano il cluster
	 */
	public void update(Data data, Set<Integer>  clusteredData)
	{
	    value = data.computePrototype(clusteredData, attribute);
	}
	
}
