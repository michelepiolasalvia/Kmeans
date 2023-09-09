package data;
/**
 * Classe DiscreteItem che estende la classe Item e rappresenta una coppia (Attributo discreto- valore discreto).
 */
public class DiscreteItem extends Item {
	
	/**
	 * Costruttore della classe DiscreItem.<br>
	 * Comportamento: Invoca il costruttore della classe madre
	 * @param attribute da assegnare all'item
	 * @param value valore da assegnare all'item
	 */
	DiscreteItem(DiscreteAttribute attribute, String value)
	{
		super(attribute, value);
	}
	
	/**
	 * Funzione per calcolare la distanza tra due item discreti.<br>
	 * Comportamento: Restituisce 0 se (getValue().equals(a)) , 1 altrimenti.
	 * @param a Oggetto del quale si vuole calcolare la distanza dall'oggetto attuale
	 * @return Restituisce 0 o 1 in base all'esito della funzione
	 */
	double distance(Object a)
	{
		if(this.getValue().equals(a))
			return 0;
		else
			return 1;
	}
}
