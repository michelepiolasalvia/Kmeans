package data;

import java.io.Serializable;
import java.util.Set;

/**
 * Classe Tuple che rappresenta una tupla come sequenza di coppie attributo-valore.
 */
public class Tuple implements Serializable {
	
	/** Array di item.*/
	private final Item[] tuple;
	
	
	/**
	 * Costruttore della classe Tuple.<br>
	 * Comportamento: Costruisce l'oggetto riferito da tuple
	 * @param size Numero di item che costituira' la tupla 
	 */
	Tuple(int size)
	{
		tuple=new Item[size];
	}
	
	
	/**
	 * Funzione per ottenere la lunghezza dell'array tuple.<br>
	 * Comportamento: Restituisce tuple.length
	 * @return Lunghezza dell'array tuple
	 */
	public int getLenght()
	{
		return tuple.length;
	}
	
	/**
	 * Funzione per ottenere l'elemento in posizione i dell'array tuple.<br>
	 * Comportamento: Restituisce l'item in posizione i
	 * @param i Indice che indica la posizione dell'array tuple che si vuole visionare
	 * @return L'item in posizione i nell'array tuple
	 */
	public Item get(int i)
	{
		return tuple[i];
	}
	
	/**
	 * Procedura per aggiungere un elemento c in una data posizione i dell'array tuple.<br>
	 * Comportamento: Memorizza c in tuple[i]
	 * @param c Elemento c da aggiungere all'array
	 * @param i Intero i che indica la posizione in cui aggiungere c
	 */
	void add(Item c, int i)
	{
		tuple[i]=c;
	}
	
	
	/**
	 * Funzione per calcolare la distanza tra due tuple.<br>
	 * Comportamento: Determina la distanza tra la tupla riferita da obj e la tupla corrente.<br> 
	 * La distanza e' ottenuta come la somma delle distanze tra gli item in posizioni eguali nelle due tuple
	 * @param obj Tupla che si vuole confrontare con quella corrente
	 * @return Distanza ottenuta dalla somma delle distanza tra gli item in posizioni uguali nelle due tuple
	 */
	public double getDistance(Tuple obj)
	{
		double distanza=0;
		for(int i=0;i<tuple.length;i++)
		{
			distanza+=tuple[i].distance(obj.get(i).getValue());
		}
		return distanza;
	}
	
	/**
	 * Funzione per calcolare la distanza media tra la tupla corrente e le altre.<br>
	 * Comportamento: restituisce la media delle distanze tra la tupla corrente e quelle ottenibili dalle righe della matrice
	 * in data aventi indice in clusteredData.
	 * @param data dataset data
	 * @param clusteredData indici da verificare
	 * @return Distanza media della tupla corrente dalle altre
	 */
	public double avgDistance(Data data, Set<Integer> clusteredData) {
	      double sumD = 0.0;
	      for (int index : clusteredData) {
	          Tuple tuple = data.getItemSet(index);
	          double d = getDistance(tuple);
	          sumD += d;
	      }
	      double p = sumD / clusteredData.size();
	      return p;
	  }
	
	
	
	
	
}
