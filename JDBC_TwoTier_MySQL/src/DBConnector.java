import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by charles on 6/22/15.
 */
public class DBConnector {

    private String mDriver;
    private String mDatabase;
    private String mUser;
    private String mPassword;
    private Connection mConnection;
    private boolean isConnected = false;

    /**
     * Construct the initial parameters
     * @param driver
     * @param database
     * @param user
     * @param password
     */
    public DBConnector(String driver, String database, String user, String password) {
        this.mDriver = driver;
        this.mDatabase = database;
        this.mUser = user;
        this.mPassword = password;
    }

    /**
     * Open a new connection
     * @see http://stackoverflow.com/questions/4172692/how-to-make-a-mysql-connection-in-java
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void OpenConnection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(mDriver);
            mConnection = DriverManager.getConnection(mDatabase, mUser, mPassword);
        }catch(SQLException e) {
            isConnected = false;
            throw new SQLException();

        }
        isConnected = true;

    }

    /**
     * Close the blasted connection
     * @throws SQLException
     */
    public void CloseConnection() throws IllegalStateException, SQLException{
        if(!isConnected) {
            throw new IllegalStateException("Not connected to database");
        }
        try {
            mConnection.close();
        }catch(SQLException e) {
            isConnected = true;
            throw new SQLException();

        }
        isConnected = false;
    }

    /*
    getters and setters
     */
    public String getmDriver() {
        return mDriver;
    }

    public String getmDatabase() {
        return mDatabase;
    }

    public String getmUser() {
        return mUser;
    }

    public String getmPassword() {
        return mPassword;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public Connection getmConnection() {
        return mConnection;
    }

    public void setmDatabase(String mDatabase) {
        this.mDatabase = mDatabase;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

}
