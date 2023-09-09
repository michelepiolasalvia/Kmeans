package database;

import java.sql.*;
import java.util.*;
import database.TableSchema.Column;

/**
 *Classe TableData
 *modella l’insieme di transazioni collezionate in una tabella. 
 *La singola transazione è modellata dalla classe Example.
 */
public class TableData {

	/** Oggetto della classe DbAccess*/
    private DbAccess db;


    /**
     * Costruttore della classe TableData
     * @param db Oggetto Dbaccess
     */
    public TableData(DbAccess db) {

        this.db=db;
    }

    /**
     * Funzione getDistinctTransazioni.
     * Estrae tutte le tuple distinte dalla tabella passata come parametro.
     * @param table nome della tabella nel database
     * @return  Lista di transazioni distinte memorizzate nella tabella
     * @throws SQLException errore nell'interazione col db.
     * @throws EmptySetException errore quando si ha un resultset vuoto.
     * @throws DatabaseConnectionException errore di connessione al database.
     */
    public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException, DatabaseConnectionException {
        List<Example> transazioni = new ArrayList<>();

        try {
            // Ottieni la connessione al database
            db.initConnection();

            // Otteni lo schema della tabella specificata
            TableSchema schema = new TableSchema(db, table);

            // Esegui la query per estrarre le tuple distinte dalla tabella
            String query = "SELECT DISTINCT * FROM " + table;
            Statement stmt = db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Verifica se il resultset è vuoto
            if (!rs.next()) {
                throw new EmptySetException();
            }

            // Crea oggetti Example per ogni tupla del resultset
            do {
                Example example = new Example();

                // Estrai i valori dei campi dal resultset e aggiungili all'oggetto Example
                for (int i = 0; i < schema.getNumberOfAttributes(); i++) {
                    Column column = schema.getColumn(i);
                    String columnName = column.getColumnName();
                    Object value;

                    // Verifica se il campo è di tipo numerico o di tipo stringa
                    if (column.isNumber()) {
                        value = rs.getDouble(columnName); 
                    } else {
                        value = rs.getString(columnName);
                    }
                    example.add(value);
                }
                transazioni.add(example);
            } while (rs.next());

            // Chiudi il resultset, lo statement e la connessione
            rs.close();
            stmt.close();
            db.closeConnection();
        } catch (DatabaseConnectionException e) {
            throw new DatabaseConnectionException();
        }
        return transazioni;
    }

    /**
     * Funzione getDistinctColumnValues.
     * Formula ed esegue una interrogazione SQL per estrarre i valori distinti
     * ordinati di column e popolare un insieme da restituire 
     * @param table Nome della tabella
     * @param column Nome della colonna nella tabella
     * @return Insieme di valori distinti ordinati in modalità ascendente che l’attributo identificato da
	 * nome column assume nella tabella identificata dal nome table
     * @throws SQLException errore di interazione col db.
     * @throws DatabaseConnectionException errore di connessione al database.
     */
    public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException, DatabaseConnectionException {
        Set<Object> distinctValues = new TreeSet<>();

        try {
            // Ottieni la connessione al database
            db.initConnection();

            // Esegui la query per estrarre i valori distinti ordinati della colonna specificata
            String query = "SELECT DISTINCT " + column.getColumnName() + " FROM " + table + " ORDER BY " + column.getColumnName();
            Statement stmt = db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Aggiungi i valori distinti all'insieme
            while (rs.next()) {
                Object value;

                // Verifica se il campo è di tipo numerico o di tipo stringa
                if (column.isNumber()) {
                    value = rs.getDouble(1);
                } else {
                    value = rs.getString(1);
                }
                distinctValues.add(value);
            }

            // Chiudi il resultset, lo statement e la connessione
            rs.close();
            stmt.close();
            db.closeConnection();
        } catch (DatabaseConnectionException e) {
            throw new DatabaseConnectionException();
        }
        return distinctValues;
    }

    /**
     * Funzione getAggregateColumnValue
     * Formula ed esegue una interrogazione SQL per estrarre il valore aggregato (valore minimo o valore massimo)
     * cercato nella colonna di nome column della tabella di nome table. 
     * @param table Nome della tabella
     * @param column Nome della colonna
     * @param aggregate operatore SQL di aggregazione (min,max) 
     * @return Aggregato cercato.
     * @throws SQLException errore di interazione col db.
     * @throws NoValueException errore quando la query non genera nessun risultato.
     */
    public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) throws SQLException, NoValueException {
        
    	Object aggregateValue = null;
        try {
            // Ottieni la connessione al database
            db.initConnection();
         // Seleziona l'operatore di aggregazione corrispondente
            String aggregateOperator = "";
            if (aggregate == QUERY_TYPE.MIN) {
                aggregateOperator = "MIN";
            } else if (aggregate == QUERY_TYPE.MAX) {
                aggregateOperator = "MAX";
            }

            // Esegui la query per calcolare l'aggregato desiderato nella colonna specificata
            String query = "SELECT " + aggregateOperator + "(" + column.getColumnName() + ") FROM " + table;
            Statement stmt = db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Se il resultset ha almeno una riga, ottieni il valore dell'aggregato
            if (rs.next()) {
            	if (column.isNumber()) {
                    aggregateValue = rs.getDouble(1);
                } else {
                    aggregateValue = rs.getString(1);
                }
            }

            // Chiudi il resultset, lo statement e la connessione
            rs.close();
            stmt.close();
            db.closeConnection();

            // Solleva un'eccezione se il valore aggregato è null
            if (aggregateValue == null) {
                throw new NoValueException();
            }
        } catch (DatabaseConnectionException e) {
            System.out.println("Errore di connessione.");
        }
        return aggregateValue;
    }





}