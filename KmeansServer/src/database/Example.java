package database;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Example, utilizzata per modellare ciascuna transazione.
 * Implementa l'interfaccia comparable
 */
public class Example implements Comparable<Example>{
	/**  array di Object che rappresentano la singola transazione (o riga di una tabella) */
	private List<Object> example=new ArrayList<Object>();

	/**
	 * Aggiunge l'oggetto o in coda ad example.
	 * @param o Oggetto da aggiungere
	 */
	public void add(Object o){
		example.add(o);
	}
	
	/**
	 * Restituisce lo i-esimo riferimento collezionato in example.
	 * @param i indice del riferimento al quale si vuole accedere.
	 * @return L'oggetto in posizione i
	 */
	public Object get(int i){
		return example.get(i);
	}
	
	/**
	 * Restituisce 0, -1, 1 sulla base del risultato del confronto. 
	 * 0 se i due esempi includono gli stessi valori. 
	 * Altrimenti il risultato del compareTo(...) invocato sulla prima coppia di valori in disaccordo.
	 * @param ex esempio da confrontare con quello attuale
	 * @return esito del confronto. 0,  -1 oppure 1
	 */
	public int compareTo(Example ex) {
		
		int i=0;
		for(Object o:ex.example){
			if(!o.equals(this.example.get(i)))
				return ((Comparable)o).compareTo(example.get(i));
			i++;
		}
		return 0;
	}
	
	/**
	 * Restituisce una stringa che rappresenta lo stato di example
	 * @return La stringa che rappresenta l'esempio.
	 */
	public String toString(){
		String str="";
		for(Object o:example)
			str+=o.toString()+ " ";
		return str;
	}
	
}