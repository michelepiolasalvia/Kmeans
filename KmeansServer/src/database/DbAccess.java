package database;

import java.sql.*;

/**
 * Classe DbAccess.
 *Gestisce la connessione al server.
 */
public class DbAccess {
	/** Stringa che indica il driver jdbc */
	private static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";	
	/**Nome del dbms*/
	private final String DBMS = "jdbc:mysql";
	/**Nome del server */
	private final String SERVER = "localhost";
	/**Nome del database */
	private final String DATABASE = "MapDB";
	/**Porta del db*/
	private final int PORT = 3306;
	/**Nome utente per accedere al db*/
	private final String USER_ID = "MapUser";
	/**Password per accedere al db */
	private final String PASSWORD = "map";
	/** Oggetto di tipo Connection, contiene le informazioni per la connessione */
	private Connection conn;
	
	/**
	 * Procedura initConnection.
	 * impartisce al class loader l’ordine di caricare il driver mysql, inizializza la connessione riferita da conn
	 * @throws DatabaseConnectionException eccezione se la connessione al db fallisce.
	 */
	 public void initConnection() throws DatabaseConnectionException{
		try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
		} catch(ClassNotFoundException e) {
			System.out.println("[!] Driver not found: " + e.getMessage());
			throw new DatabaseConnectionException();
		} catch(InstantiationException e){
			System.out.println("[!] Error during the instantiation : " + e.getMessage());
			throw new DatabaseConnectionException();
		} catch(IllegalAccessException e){
			System.out.println("[!] Cannot access the driver : " + e.getMessage());
			throw new DatabaseConnectionException();
		}
		String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
					+ "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
			
		System.out.println("Connection's String: " + connectionString);
			
			
		try {			
			conn = DriverManager.getConnection(connectionString);
		} catch(SQLException e) {
			System.out.println("[!] SQLException: " + e.getMessage());
			System.out.println("[!] SQLState: " + e.getSQLState());
			System.out.println("[!] VendorError: " + e.getErrorCode());
			throw new DatabaseConnectionException();
		}
	}
	
	/**
	 * Funzione getConnection.
	 * @return l'oggetto di tipo Connection
	 */
	public Connection getConnection() {
		return conn;
	}
	
	/**
	 * Funzione closeConnection().
	 * Chiude la connessione
	 * @throws SQLException Errore nell'accesso al db
	 */
	public void closeConnection() throws SQLException{
		conn.close();
	}
	
	
}
