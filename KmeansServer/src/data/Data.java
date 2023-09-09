package data;
import java.sql.SQLException;
import java.util.*;

import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.Example;
import database.QUERY_TYPE;
import database.TableData;
import database.TableSchema;

 
/**
 * Classe concreta Data utilizzata per modellare l'insieme di transazioni (o tuple).
 */
public class Data {
	//Attributi
	
	/**Una lista di tipo Example utilizzata per rappresentare le transazioni.*/
	private List<Example> data = new ArrayList<>(); 
	/**Cardinalita' dell'insieme di transazioni (numero di righe in data).*/
	private final int numberOfExamples;
	/**Lista di attributi utilizzata per rappresentare gli attributi discreti.*/
	private final List<Attribute> attributeSet;
	
	
	/**
	 *Costruttore della classe Data.<br>
	 *Comportamento: Inizializza la lista data con transazioni di esempio (senza esempi duplicati).<br>
	 *Inizializza attributeSet creando cinque oggetti di tipo DiscreteAttribute, uno per ciascun attributo.<br>
	 *Inizializza numberOfExamples
	 * @throws Exception eccezione generata da errori con le transazioni.
	 */
	public Data(String Nametable) throws Exception {
		DbAccess db = new DbAccess();
		db.initConnection();
		TableData tbData = new TableData(db);
		TableSchema tbSchema = new TableSchema(db, Nametable);
		try {
			data = tbData.getDistinctTransazioni(Nametable);
		} catch (Exception e) {
			 throw new Exception("Errore creazione data");
		} 
		
		numberOfExamples = data.size();
		attributeSet = new ArrayList<>();
		for(int i = 0; i < tbSchema.getNumberOfAttributes();i++) {
			try {
				if(!tbSchema.getColumn(i).isNumber()) {
					TreeSet<String> values = new TreeSet<>();
					for(Object obj : tbData.getDistinctColumnValues(Nametable,tbSchema.getColumn(i))){
						values.add((String) obj);
					}
					attributeSet.add(new DiscreteAttribute(tbSchema.getColumn(i).getColumnName(), i, values));
				}else {
					double min = (Double) tbData.getAggregateColumnValue(Nametable,tbSchema.getColumn(i), QUERY_TYPE.MIN);
					double max = (Double) tbData.getAggregateColumnValue(Nametable,tbSchema.getColumn(i), QUERY_TYPE.MAX); 
					attributeSet.add(new ContinuousAttribute(tbSchema.getColumn(i).getColumnName(), i, min, max));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (DatabaseConnectionException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Restituisce il numero di righe del dataSet.<br>
	 * Comportamento: Restituisce numberOfExamples
	 * @return Il numero di esempi (righe)
	 */
	public int getNumberOfExamples(){
		return numberOfExamples;
	}
	/**
	 * Restituisce il numero di colonne del dataSet.<br>
	 * Comportamento: Restituisce la dimensione di attributeSet
	 * @return Il numero di attributi (colonne)
	 */
	public int getNumberOfAttributes(){
		return attributeSet.size();
	}
	
	/**
	 * Funzione che restituisce un valore del dataset.<br>
	 * @param exampleIndex Indice di riga a cui accedere
	 * @param attributeIndex Indice di colonna a cui accedere
	 * @return L'elemento in riga exampleIndex e colonna attributeIndex
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		Example e = data.get(exampleIndex);
		return e.get(attributeIndex);
	}
	
	/**
	 * Funzione che restituisce l'attributo in posizione index di attributeSet.<br>
	 * @param index L'identificativo numerico dell'attributo
	 * @return L'attributo in posizione index di attributeSet
	 */
	public Attribute getAttribute(int index){
		return attributeSet.get(index);
	}
	
	/**
	 * Funzione che crea una stringa a partire da tutti gli elementi della tabella in base al suo schema.<br>
	 * Comportamento: Crea una stringa in cui memorizza lo schema della tabella (vedi attributeSet) e le transazioni memorizzate in data,
	 * opportunamente enumerate. 
	 * @return La stringa creata come descritto
	 */
	public String toString(){
		String outputString ="";
		//Stampa DiscreteAttribute
		for(int i=0;i<getNumberOfAttributes();i++){
			outputString += (getAttribute(i).getName()) +", ";
		}
		outputString += "\n";
		
		
		for(int y=0; y<getNumberOfExamples(); y++) {
			outputString += (y+1) +": ";
			for(int x=0; x<getNumberOfAttributes();x++) {
				outputString +=getAttributeValue(y,x).toString() + ", ";  
			}
			outputString += "\n";
		}
		return outputString;		
	}
	
	/**
	 * Funzione che restituisce un oggetto "Tuple" che contiene tutti i valori discreti della riga con indice in input.<br>
	 * Comportamento: Crea e restituisce un oggetto di Tuple che modella come 
	 * sequenza di coppie Attributo-valore la i-esima riga in data.
	 * @param index Indice di riga
	 * @return Gli elementi della tupla indicata
	 */
	public Tuple getItemSet(int index){
		Tuple tuple = new Tuple(attributeSet.size());
	    Example example = data.get(index);
	    int i = 0;
	    for (Attribute attribute : attributeSet) {
	    	Object value = example.get(i);
	    	if(attribute instanceof DiscreteAttribute) {
	    		DiscreteItem item = new DiscreteItem((DiscreteAttribute) attributeSet.get(i), (String) value);
		    	tuple.add(item, i);
		    	i++;
	    	}else if(attribute instanceof ContinuousAttribute) {
	    		ContinuousItem item = new ContinuousItem((ContinuousAttribute) attributeSet.get(i), (Double) value);
	    		tuple.add(item, i);
	    		i++;
	    	}
	    }
	    	return tuple;
	}
	
	/**
	 * Funzione utilizzata per generare degli indici casuali.<br>
	 * Comportamento: Viene utilizzato un oggetto "Random" per generare i numeri casuali e un ciclo "for" per generare gli indici casuali.
	 * Viene anche verificato che gli indici casuali siano unici all'interno dell'array "centroidIndexes" per evitare la ripetizione.<br>
	 * @param k Numero di cluster da generare
	 * @return Un array contenente gli indici delle righe, ottenuti randomicamente
	 * @throws OutOfRangeSampleSize eccezione generata da un input che non rispetta il range delle transazioni
	 */
	public int[] sampling(int k) throws OutOfRangeSampleSize{
		if (k<=0 || k>data.size())
		{
			throw new OutOfRangeSampleSize();
		}
		
		int centroidIndexes[]=new int[k];
		Random rand=new Random();
		rand.setSeed(System.currentTimeMillis());
		for (int i=0;i<k;i++){
			boolean found=false;
			int c;
			do{
				found=false;
				c=rand.nextInt(getNumberOfExamples());
				for(int j=0;j<i;j++) {
					if(compare(centroidIndexes[j],c)){
						found=true;
						break;
					}
				}
			}
			while(found);
			centroidIndexes[i]=c;
		}
		return centroidIndexes;
	}

	/**
	 * Funzione utilizzata per confrontare tutti gli attributi delle due righe  con indici specificati in input.<br>
	 * Comportamento: Restituisce vero se le due righe di data contengono gli stessi valori, falso altrimenti
	 * @param i Indice di una delle due righe da comparare
	 * @param j Indice di una delle due righe da comparare
	 * @return L'esito del confronto (true se uguali, false altrimenti)
	 */
	private boolean compare(int i, int j)
	{
		boolean f=false;
		Object a=null;
		Object c=null;
		for(int b=0;b<getNumberOfAttributes();b++)
		{	
			a=getAttributeValue(i,b);
			c=getAttributeValue(j,b);
			if(a.equals(c))
			{
				f = true;
			}
			else {
				f = false;
				return f;
			}
		}
		return f;	
	}	
	
	
	/**
	 * Richiama una funzione effettuando un casting sul parametro dato in input e ne restituisce il risultato.<br>
	 * Comportamento: Restituisce computePrototype(idList, (DiscreteAttribute)attribute)
	 * @param idList ArraySet contenente gli indici delle righe
	 * @param attribute attributo rispetto al quale calcolare il prototipo (centroide)
	 * @return L'esito della funzione computePrototype(idList, (DiscreteAttribute) attribute)
	 */
	Object computePrototype(Set<Integer> idList, Attribute attribute)
	{
		if(attribute instanceof ContinuousAttribute) {
			 return computePrototype(idList, (ContinuousAttribute)attribute); 
		}else{
			return computePrototype(idList, (DiscreteAttribute)attribute);
		}
	}
		
	/**
	 * Funzione che calcola il prototipo per un determinato attributo discreto a partire da un insieme di elementi identificati da idList.<br>
	 * Comportamento: Determina il valore che occorre piu' frequentemente per attribute nel sottoinsieme di dati individuato da idList
	 * @param idList Insieme degli indici delle righe di data appartenenti ad un cluster
	 * @param attribute attributo discreto rispetto al quale calcolare il prototipo (centroide)
	 * @return L'elemento che occorre più frequentemente per attribute
	 */
	String computePrototype(Set<Integer> idList, DiscreteAttribute attribute) {
	    Object[] pos = idList.toArray();
	    int max = 0;
	    int app = 0;
	    String str = "";
	    for (int i = 0; i < pos.length; i++) {
	        for (int k = 0; k < getNumberOfAttributes(); k++) {
	            app = attribute.frequency(this, idList, getAttributeValue((int) pos[i], k).toString());
	            if (max < app) {
	                max = app;
	                str = getAttributeValue((int) pos[i], k).toString();
	            }
	        }
	    }
	    return str;
	}
	
	/**
	 * Funzione che calcola il valore prototipo come media.
	 * @param idList array contenente gli indici di riga da visitare
	 * @param attribute attributo corrispondente alla colonna da visitare
	 * @return Il valore prototipo ottenuto come media delle celle visitate.
	 */
	Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
		double somma = 0.0;
		for (int i : idList) {
				double value = (double) getAttributeValue(i,attribute.getIndex());	
				somma += value;
		}
		return somma/idList.size();
	}
		
}


