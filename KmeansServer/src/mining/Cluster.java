package mining;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import data.Data;
import data.Tuple;

/**
 * Classe cluster che modella un cluster.<br>
 * Ogni cluster e' composto da un centroide, ovvero una tupla che rappresenta il centro del cluster,
 * e un insieme di transazioni che appartengono al cluster.
 */
public class Cluster implements Serializable {
	/** Il centroide del cluster.*/
	private final Tuple centroid;
	
	/** L'insieme di dati che sono stati assegnati a un determinato cluster.*/
	private final Set<Integer> clusteredData; 

	/**
	 * Costruttore della classe Cluster.<br>
	 * Comportamento: Inizializza gli attributi dell'oggetto
	 * @param centroid Il centroide del cluster
	 */
	Cluster(Tuple centroid){
		this.centroid=centroid;
		clusteredData=new HashSet<>();
	}
		
	/**
	 * Funzione che restituisce il centroide del cluster.<br>
	 * Comportamento: Restituisce il centroide
	 * @return Il centroide del cluster
	 */
	Tuple getCentroid(){
		return centroid;
	}
	
	/**
	 * Procedura che aggiorna il centroide.<br>
	 * Comportamento: Calcola il nuovo valore del centroide a partire da clusteredData
	 * @param data Il dataset contenente i dati
	 */
	void computeCentroid(Data data) {
		for(int i=0;i<centroid.getLenght();i++){
			centroid.get(i).update(data,clusteredData);
		}
	}
	
	
	/**
	 * Funzione che aggiunge un id a clusteredData.<br>
	 * Comportamento: Restituisce il valore in base se cambia o meno il cluster
	 * @param id L'identificativo da aggiungere
	 * @return True se la tupla cambia cluster
	 */
	boolean addData(int id){
		return clusteredData.add(id);
		
	}
	
	/**
	 * Verifica se un id e' presente nel cluster.<br>
	 * Comportamento:Verifica se una transazione e' clusterizzata nell'array corrente
	 * @param id Id del quale si vuole controllare la presenza in clusteredData
	 * @return true se l'id e' presente false altrimenti
	 */
	boolean contain(int id){
		return clusteredData.contains(id);
	}
	
	/**
	 * Procedura che rimuove un identificatore dal cluster.<br>
	 * Comportamento: Rimuove la tupla che ha cambiato il cluster
	 * @param id Id che si vuole rimuovere
	 */
	void removeTuple(int id){
		clusteredData.remove(id);
		
	}
	
	/**
	 * Funzione che descrive la struttura del centroide indicandone le coordinate.<br>
	 * Comportamento: Descrive il centroide concatenando le sue coordinate in una stringa
	 * @return stringa rappresentante la struttura del centroide
	 */
	public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLenght();i++)
			str+=centroid.get(i)+", ";
		str+=")";
		return str;
	}
	
	/**
	 * Funzione che fornisce una rappresentazione testuale del centroide del cluster e degli elementi che gli appartengono. 
	 * Comportamento: Fornisce la rappresentazione del cluster sottoforma di stringa e rappresenta la distanza degli esempi dal centroide 
	 * @param data Il dataset contenente i dati
	 * @return La stringa che descrive il cluster
	 */
	public String toString(Data data) {
	      String str = "Centroid=(";
	      for (int i = 0; i < centroid.getLenght(); i++) {
	          str += centroid.get(i) + " ";
	      }
	      str += ")\nExamples:\n";

	      for (int i : clusteredData) {
	          str += "[";
	          for (int j = 0; j < data.getNumberOfAttributes(); j++) {
	              str += data.getAttributeValue(i, j) + " ";
	          }
	          str += "] dist=" + getCentroid().getDistance(data.getItemSet(i)) + "\n";
	      }
	      str += "\nAvgDistance=" + getCentroid().avgDistance(data, clusteredData);
	      return str;
	  }

}
