package server.db;

import server.service.StatusCode;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDriver {
    private DBConfig config;

    public DatabaseDriver(DBConfig config) throws SQLException {
        this.config = config;
    }

    /**
     * Initializes DB and inserts 1 user ONLY, if no rows are present
     * in bankuser table
     * @throws SQLException
     */
    public void init() throws SQLException, InterruptedException {
        /**
         * This code is executed for test purposes only.
         * <br>
         * Since we are initializing the DB by server code,
         * we have a wait-for.it.sh shell script that
         * awaits for TCP connections to the DB.
         * <br>
         * Sometimes, while the connection is active,
         * the DB is not really ready to accept connections.
         * <br>
         * The Thread.sleep for 2 seconds is for that reason.
         */
        Thread.sleep(2000);

        var conn = DriverManager.getConnection(config.getURL(),
                config.getUsername(), config.getPassword());

        var statement = conn.createStatement();
        var query = "CREATE TABLE IF NOT EXISTS bankuser(" +
                        "id bigserial primary key," +
                        "username text unique not null," +
                        "password text not null," +
                        "balance double precision not null);" +
                    "INSERT INTO bankuser (username, password, balance) " +
                    "SELECT 'user', 'user', 25000 " +
                    "WHERE NOT EXISTS (SELECT 1 FROM bankuser);";
        statement.execute(query);
    }

    public int withdraw(long userID, double amount) throws SQLException {
        var conn = DriverManager.getConnection(config.getURL(),
                config.getUsername(), config.getPassword());

        var statement = conn.createStatement();
        var query = "SELECT balance FROM bankuser " +
                    "WHERE id="+userID+";";
        var rs = statement.executeQuery(query);

        /* Check if result set is empty */
        if (!rs.next())
            return StatusCode.USER_NOT_FOUND;

        double balance = rs.getDouble("balance");
        if (balance < amount)
            return StatusCode.INSUFFICIENT_BALANCE;

        statement.execute("UPDATE bankuser " +
                             "SET balance=balance-"+amount+" " +
                             "WHERE id="+userID+";");
        statement.close();

        return StatusCode.OK;
    }

    public int deposit(long userID, double amount) throws SQLException {
        var conn = DriverManager.getConnection(config.getURL(),
                config.getUsername(), config.getPassword());

        var statement = conn.createStatement();
        var query = "SELECT balance FROM bankuser " +
                "WHERE id="+userID+";";
        var rs = statement.executeQuery(query);

        /* Check if result set is empty */
        if (!rs.next())
            return StatusCode.USER_NOT_FOUND;

        statement.execute("UPDATE bankuser " +
                             "SET balance=balance+" + amount + " " +
                             "WHERE id="+userID+";");
        statement.close();

        return StatusCode.OK;
    }

    public double balance(long userID) throws SQLException {
        var conn = DriverManager.getConnection(config.getURL(),
                config.getUsername(), config.getPassword());

        var statement = conn.createStatement();
        var query = "SELECT balance FROM bankuser " +
                    "WHERE id=" + userID + ";";

        var rs = statement.executeQuery(query);
        if (!rs.next())
            return StatusCode.USER_NOT_FOUND;

        double balance = rs.getDouble("balance");
        statement.close();

        return balance;
    }
}
