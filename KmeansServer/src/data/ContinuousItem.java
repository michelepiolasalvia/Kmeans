package data;

/**
 * Classe concreta ContinuousItem.
 * Estende la classe Item
 * Modella una coppia <attributo continuo - valore numerico>
 */
class ContinuousItem extends Item{
	
	/**
	 * Costruttore della classe ContinuousItem
	 * @param attribute attributo coinvolto nell'item
	 * @param value valore dell'item
	 */
	ContinuousItem(Attribute attribute, Double value) {
		super(attribute, value);	
	}
	
	/**
	 * Funzione utilizzata per calcolare la distanza tra due ContinuousItem.
	 * @param a item del quale si vuole calcolare la distanza da quello attuale
	 * @return la distanza tra i valori scalati dei due item
	 */
	double distance(Object a) {
	    ContinuousAttribute attribute = (ContinuousAttribute) this.getAttribute();
	    return Math.abs(attribute.getScaledValue((double) this.getValue()) - attribute.getScaledValue((double) a));
	}

	

	
}

