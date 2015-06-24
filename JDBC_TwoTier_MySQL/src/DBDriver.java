import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

/**
 * Created by charles on 6/23/15.
 * <h1>DBDriver</h1>
 * <p>
 *     This class returns the result set for a JTable primarly taken and adapted from the link below
 * </p>
 * @see "http://stackoverflow.com/questions/10620448/most-simple-code-to-populate-jtable-from-resultset"
 */
public class DBDriver {
    private DBConnector mDBConnector;


    public DBDriver(DBConnector db) {
        this.mDBConnector = db;
    }

    /**
     * <p> Not to be used </p>
     * @deprecated
     * @param tableModel pass in the DefaultTableModel
     * @return
     */
    public DefaultTableModel clearData(DefaultTableModel tableModel) {
        //TODO: remove pls
        tableModel.setDataVector(new String[][]{new String[]{""}}, new String[]{""});
        return tableModel;
    }

    public DefaultTableModel loadData(String query)
            throws SQLException, IllegalStateException{
        DefaultTableModel tableModel = new DefaultTableModel();
        if(!mDBConnector.isConnected()) {
            throw new IllegalStateException("Not Connected To Database");
        }
        Statement stmt = this.mDBConnector.getmConnection().createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData metaData = rs.getMetaData();

        // Names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }

        // Data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int i = 1; i <= columnCount; i++) {
                vector.add(rs.getObject(i));
            }
            data.add(vector);
        }

        tableModel.setDataVector(data, columnNames);

        return tableModel;
    }

    /**
     *
     * @param query query that will get passed through the JDBC driver
     * @return return boolean for use later
     * @throws SQLException
     * @throws IllegalStateException
     */
    public boolean updateData(String query)
            throws SQLException, IllegalStateException{
        int res;
        if(!mDBConnector.isConnected()) {
            throw new IllegalStateException("Not Connected to Database");
        }
        try {
            Statement stmt = this.mDBConnector.getmConnection().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            res = stmt.executeUpdate(query);
        }catch(SQLException e) {
            throw e;
        }
        return (res>0);
    }




}
