package data;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe concreta DiscreteAttribute che estende la classe Attribute e rappresenta un attributo discreto (categorico).
 * Implementa l'interfaccia iterable.
 */
public class DiscreteAttribute extends Attribute implements Iterable<String>{
	//Attributi
	/**
	 * Treeset di tipo String.<br>
	 * Contiene i nomi degli attributi discreti.
	 */
	private final TreeSet<String> values;
	
	
	//Metodi
	/**
	 * Costruttore della classe DiscreteAttribute.<br>
	 * Comportamento: Invoca il costruttore della classe madre e
	 * inizializza il membro values con il parametro in input.
	 * @param name Nome dell'attributo
	 * @param index identificativo numerico dell'attributo
	 * @param values treeset di stringhe rappresentanti il dominio dell'attributo
	 */
	DiscreteAttribute(String name, int index, TreeSet<String> values){
		super(name, index);
		this.values = values;
		
	}
	
	
	/**
	 * Funzione che restituisce il numero di valori discreti nel dominio.<br>
	 * Comportamento: Restituisce la dimensione di values
	 * @return Numero di valori discreti nel dominio dell'attributo
	 */
	public int getNumberOfDistinctValues(){
		return values.size();
	}
	
	

	
	/**
	 * Restituisce un valore intero che rappresenta il numero di volte che la stringa "v" compare nel dataset.
	 * Comportamento: Determina il numero di volte che il valore v compare in corrispondenza dell'attributo corrente (indice di colonna)
	 *  			  negli esempi memorizzati in data e indicizzate (per riga) da idList
	 * @param data Riferimento ad un oggetto data, cioè il dataset
	 * @param idList riferimento ad un oggetto ArraySet contenente gli indici delle righe a cui accedere.
	 * @param v valore discreto
	 * @return Il numero di occorrenze di v nel dataset in corrispondenza della colonna corrente e righe specificate
	 */
	int frequency(Data data, Set<Integer>  idList, String v) {
		Object[] rowsToCheck = idList.toArray();
		int freq=0;
		for(int i=0; i<rowsToCheck.length; i++) {
			if(v.equals(data.getAttributeValue((int) rowsToCheck[i],getIndex())))
				freq++;
		}
		return freq;
	}	
	
	/**
	 *  Restituisce l'iteratore associato al TreeSet 'values'.
	 */
	public Iterator<String> iterator()
	{
		return values.iterator(); 
	}
	
	
	
}
