package mining;
import java.io.*;
import data.Data;
import data.OutOfRangeSampleSize;

/**
 * Classe KMeansMiner che include l'implementazione dell'algoritmo kmeans.
 */
public class KMeansMiner {
	/**Oggetto di tipo ClusterSet.*/
	private final ClusterSet C;
	
	/**
	 * Costruttore della classe KMeansMiner.<br>
	 * Comportamento: Crea l'oggetto array riferito da C
	 * @param k Numero di cluster da generare
	 * @throws OutOfRangeSampleSize eccezione generata da un input che non rispetta il range delle transazioni.
	 */
	public KMeansMiner(int k) throws OutOfRangeSampleSize
	{
		C = new ClusterSet(k);
	}
	
	/**
	 * Costruttore della classe KMeansMiner.
	 * Estra le informazioni dal file specificato.
	 * @param fileName Nome del file da cui caricare
	 * @throws IOException eccezione generata da errori di IO.
	 * @throws ClassNotFoundException Se si verifica un errore nel caricamento delle classi serializzate.
	 */
	public KMeansMiner(String fileName) throws IOException, ClassNotFoundException {
        try {
          ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
          C = (ClusterSet) in.readObject();
          in.close();
        } catch (IOException e) {
          throw new IOException("Errore di IO");
        } catch (ClassNotFoundException e) {
          throw new ClassNotFoundException("Classe non trovata");
        }
      }
      /**
       * Funzione salva.
       * Salva il risultato ottenuto in un file.
       * @param fileName nome del file su cui si vuole salvare.
       * @throws IOException Se si verifica un errore durante la comunicazione con il server.
       */
      public void salva(String fileName) throws IOException {
        try {
          ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
          out.writeObject(this.C);
          out.close();
        } catch (IOException e) {
          throw new IOException("Errore di I/O");
        }
      }
	
	/**
	 * Funzione utilizzata per accedere a clusterSet. C<br>
	 * Comportamento: Restituisce C
	 * @return L'oggetto clusterSet
	 */
	public ClusterSet getC()
	{
		return C;
	}
	
	/**
	 * Funzione che implementa l'algoritmo kmeans.<br>
	 * Comportamento: Esegue l'algoritmo k-means eseguendo i passi dello pseudo-codice:<br>
	 * 1. Scelta casuale di centroidi per k clusters<br>
	 * 2. Assegnazione di ciascuna riga della matrice in data al cluster avente centroide piu' vicino all'esempio.<br>
	 * 3. Calcolo dei nuovi centroidi per ciascun cluster<br>
	 * 4. Ripete i passi 2 e 3. finche' due iterazioni consecuitive non restituiscono centroidi uguali .
	 * @param data Dataset data
	 * @return Numero di iterazioni eseguite 
	 * @throws OutOfRangeSampleSize eccezione generata da un input che non rispetta il range delle transazioni.
	 */
	public int kmeans(Data data) throws OutOfRangeSampleSize{
		int numberOfIterations=0;
		//STEP 1
		C.initializeCentroids(data);
		boolean changedCluster=false;
		do{
			numberOfIterations++;
			//STEP 2
			changedCluster=false;
				for(int i=0;i<data.getNumberOfExamples();i++){
					Cluster nearestCluster = C.nearestCluster(
					data.getItemSet(i));
					Cluster oldCluster=C.currentCluster(i);
					boolean currentChange=nearestCluster.addData(i);
					if(currentChange)
						changedCluster=true;
						//rimuovo la tupla dal vecchio cluster
					if(currentChange && oldCluster!=null)
						//il nodo va rimosso dal suo vecchio cluster
						oldCluster.removeTuple(i);
				}
			//STEP 3
				
			C.updateCentroids(data);
		}
		while(changedCluster);
		
		return numberOfIterations;
	}

}
