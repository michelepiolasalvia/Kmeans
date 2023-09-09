package data;
/**
 * Classe concreta ContinuousAttribute che estende la classe Attribute e modella un attributo continuo (numerico). 
 *  Tale classe include i metodi per la normalizzazione del dominio dell'attributo nell'intervallo [0,1]
 *  al fine da rendere confrontabili attributi aventi domini diversi
 */
public class ContinuousAttribute extends Attribute {
	//Attributi
	/**Rappresenta l'estremo massimo dell'intervallo di valori (dominio) che l'attributo puo' assumere.*/
	private final double max;
	/**Rappresenta l'estremo minimo dell'intervallo di valori (dominio) che l'attributo puo' assumere.*/
	private final double min;

	//Metodi
	/**
	 * Costruttore della classe Continuos Attribute.<br>
	 * Comportamento : Invoca il costruttore della classe madre e inizializza i membri aggiunti per estensione
	 * @param name Nome dell'attributo
	 * @param index Indice dell'attributo
	 * @param min valore minimo dell'attributo
	 * @param max valore massimo dell'attributo
	 */
	public ContinuousAttribute(String name,int index, double min, double max){
		super(name,index);
		this.min = min;
		this.max = max;
		
	}
	
	/**
	 * Normalizza il valore dato in input nell'intervallo [0,1].<br>
	 * Comportamento: Calcola e restituisce il valore normalizzato del parametro passato in input.
	 * La normalizzazione ha come codominio lo intervallo [0,1].
	 * @param v Valore dell'attributo da normalizzare
	 * @return Il valore normalizzato
	 */
	double getScaledValue(double v) { 
		v = ((v-min)/(max-min));
		return v;
	}
	
	
}
