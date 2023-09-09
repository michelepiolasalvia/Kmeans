package mining;
import java.io.Serializable;

import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

/**
 * Classe ClusterSet che modella un cluster.
 */
public class ClusterSet implements Serializable{
	/** Array di cluster.*/
	private final Cluster[] C;
	/**Posizione valida per la memorizzazione di un nuovo cluster in C.*/
	private int i=0;

	/**
	 * Costruttore della classe ClusterSet.<br>
	 * Comportamento:Viene creato l'oggetto array riferito da C
	 * @param k Numero di cluster da generare
	 */
	ClusterSet(int k) throws OutOfRangeSampleSize
	{
		if(k<=0) {
			throw new OutOfRangeSampleSize();
		}
		C=new Cluster[k];
	}
	
	/**
	 * Procedura che assegna c a C[i] e incrementa i.<br>
	 * Comportamento: Assegna c a C[i] e incrementa i.
	 * @param c cluster da aggiungere
	 */
	void add(Cluster c)
	{
		C[i]=c;
		i++;
	}
	
	/**
	 * Funzione per accedere all'elemento in posizione i di C[].<br>
	 * Comportamento: Restituisce C[i]<br>
	 * @param i posizione di C alla quale si vuole accedere
	 * @return Elemento in posizione i dell'array C[]
	 */
	Cluster get(int i)
	{
		return C[i];
	}
	
	/**
	 * Funzione utilizzata per inizializzare i centroidi.<br>
	 * Comportamento: Sceglie i centroidi, crea un cluster per ogni centroide e lo memorizza in C
	 * @param data Il dataset data
	 * @throws OutOfRangeSampleSize eccezione generata da un input che non rispetta il range delle transazioni 
	 */
	void initializeCentroids(Data data) throws OutOfRangeSampleSize
	{
			int centroidIndexes[]=data.sampling(C.length);
			for(int i=0;i<centroidIndexes.length;i++)
			{
				Tuple centroidI=data.getItemSet(centroidIndexes[i]);
				add(new Cluster(centroidI));
			}	
	}

	
	/**
	 * Funzione utilizzata per trovare il cluster piu' vicino alla tupla.<br>
	 * Comportamento: Calcola la distanza tra la tupla riferita da tuple ed il centroide
	 * di ciascun cluster in C e restituisce il cluster piu' vicino
	 * @param tuple Riferimento ad un oggetto Tuple
	 * @return Cluster piu' vicino alla tupla passata
	 */
	
	Cluster nearestCluster(Tuple tuple)
	{
		double tem=0;
		double min = C[0].getCentroid().getDistance(tuple);
		Cluster distanza=this.get(0);
		for(int i=0;i<C.length;i++){
			tem=C[i].getCentroid().getDistance(tuple);
			if(min>tem){
				distanza=C[i];
				min=tem;
			}
		} 
		return distanza;
	}
		
	/**
	 * Funzione utilizzata per trovare il cluster della tupla data in input.<br>
	 * Comportamento: Identifica e restituisce il cluster a cui la tupla rappresentate l'esempio identificato da id.<br> 
	 * Se la tupla non e' inclusa in nessun cluster restituisce null
	 * @param id Indice di una riga nella matrice in Data
	 * @return Il cluster a cui appartiene la tupla o null se non appartiene a nessun cluster
	 */
	Cluster currentCluster(int id)
	{
		for(int i=0; i<C.length;i++)
		{
			if(C[i].contain(id))
			{
				return C[i];
			}
		}
		return null;
	}
	
	/**
	 * Procedura utilizzata per calcolare il nuovo centroide di ogni cluster.<br>
	 * Comportamento: Calcola il nuovo centroide per ciascun cluster in C
	 * @param data Dataset data
	 */
	
	void updateCentroids(Data data)
	{
		for(int i=0; i<C.length;i++)
		{
			this.get(i).computeCentroid(data);
		}
	}
	
	/**
	 * Funzione utilizzata per rappresentare sottoforma di stringa ciascun centroide dell'insieme dei cluster.<br>
	 * Comportamento:Restituisce una stringa fatta da ciascun centroide dell'insieme dei cluster.
	 * @return Stringa che descrive tutti i centroidi del cluster
	 */
	public String toString() 
	{
		String cent="";
		for(int i=0; i<C.length;i++)
		{
			cent += i + ": " + C[i].toString() + "\n"; 
		}
		return cent;
	}
	
	/**
	 * Funzione utilizzata per rappresentare lo stato dei cluster sottoforma di stringa.<br>
	 * Comportamento: Restituisce una stringa che descriva lo stato di ciascun cluster in C.
	 * @param data Dataset data
	 * @return La stringa che rappresenta lo stato di ogni cluster in C
	 */
	public String toString(Data data){
		String str="";
		for(int i=0;i<C.length;i++){
			if (C[i]!=null){
				str += i+":"+C[i].toString(data)+"\n";
			}
		}
		return str;
	}

	
	
	



}
