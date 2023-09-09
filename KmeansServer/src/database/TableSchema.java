package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Classe TableSchema. modella lo schema di una tabella nel database relazionale
 */
public class TableSchema {
	private DbAccess db;
	
	/** Lista di colonne che rappresenta la tabella*/
	private List<Column> tableSchema = new ArrayList<Column>();

	/**
	 * Inner class che rappresenta una colonna della tabella.
	 */
	public class Column {
		/** Nome della colonna */
		private String name;
		/** Tipo della colonna */
		private String type;

		/**
		 * Costruttore di column.
		 * @param name Nome della colonna
		 * @param type tipo della colonna
		 */
		Column(String name, String type) {
			this.name = name;
			this.type = type;
		}

		/**
		 * @return il nome della colonna.
		 */
		public String getColumnName() {
			return name;
		}

		/**
		 * @return true se il tipo è numerico, false altrimenti.
		 */
		public boolean isNumber() {
			return type.equals("number");
		}

		/**
		 * Descrive la colonna sottoforma di stringa.
		 */
		public String toString() {
			return name + ":" + type;
		}
	}

	/**
	 * Costruttore di TableSchema.
	 * @param db Oggetto DbAccess
	 * @param tableName Nome della tabella
	 * @throws SQLException errore di interazione col db.
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException {
		this.db = db;
		HashMap<String, String> mapSQL_JAVATypes = new HashMap<String, String>();
		mapSQL_JAVATypes.put("CHAR", "string");
		mapSQL_JAVATypes.put("VARCHAR", "string");
		mapSQL_JAVATypes.put("LONGVARCHAR", "string");
		mapSQL_JAVATypes.put("BIT", "string");
		mapSQL_JAVATypes.put("SHORT", "number");
		mapSQL_JAVATypes.put("INT", "number");
		mapSQL_JAVATypes.put("LONG", "number");
		mapSQL_JAVATypes.put("FLOAT", "number");
		mapSQL_JAVATypes.put("DOUBLE", "number");

		Connection con = db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);

		while (res.next()) {

			if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
				tableSchema.add(
						new Column(res.getString("COLUMN_NAME"), mapSQL_JAVATypes.get(res.getString("TYPE_NAME"))));
		}
		res.close();
	}

	/** 
	 * @return il numero di colonne nella tabella.
	 */
	public int getNumberOfAttributes() {
		return tableSchema.size();
	}

	/**
	 * @param index indice della colonna che si vuole
	 * @return la colonna corrispondente all'indice
	 */
	public Column getColumn(int index) {
		return tableSchema.get(index);
	}
}
