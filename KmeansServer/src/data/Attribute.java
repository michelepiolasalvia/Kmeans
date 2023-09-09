package data;

import java.io.Serializable;

/**
*La classe astratta Attribute. 
*Rappresenta un attributo con un nome ed un indice
*/
public abstract class Attribute implements Serializable{
	//Attributi
	/** Nome simbolico dell'attributo. */
	private final String name;
	/** Identificativo numerico dell'attributo */
	private final int index;
	//Metodi
	
	/**
	 * Costruttore della classe Attribute.<br>
	 * Comportamento: Inizializza i valori dei membri name, index
	 * @param name Nome dell'attributo
	 * @param index Identificativo numerico dell'attributo (primo, secondo ... attributo della tupla)
	 */
	 Attribute(String name, int index){
		this.name = name;
		this.index = index;
	}
	
	/**	Restituisce il nome dell'attributo.<br>
	 *	Comportamento: Restituisce name
	 * 	@return Il nome dell'attributo 	
	*/
	 String getName() {
		 return name;
	 }
	 
	 
	 /**
	 * Restituisce l'indice dell'attributo.<br>
	 * Comportamento: Restituisce index;
	 * @return L'indice dell'attributo
	 */
	 int getIndex() {
		 return index;
	 }
	 
	 /**
	  * Restituisce una rappresentazione in stringa del nome dell'Attribute.<br>
	  *	Comportamento: Restituisce name
	  * @return Il nome dell'Attribute rappresentato come stringa
	  */
	 @Override
	 public String toString(){
		 return name;
	 }	
}
