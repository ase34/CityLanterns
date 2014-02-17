package me.ase34.citylanterns.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class GroupMySQLStorage extends GroupSQLStorage {

    private Connection conn;
    private String url;
    
    public GroupMySQLStorage(String url, String table) {
        super(table);
        this.url = url;
    }
    
    @Override
    public Connection getConnection() throws Exception {
        if (conn != null && !conn.isClosed()) {
            return conn;
        }
        
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(url);
        return conn;
    }

    @Override
    protected void createTables() throws Exception {
        Statement stmt = getConnection().createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS `" + table + "` (" +
                "`name` VARCHAR(45) NOT NULL ," +
                "`daytime` BIGINT NOT NULL ," +
                "`nighttime` BIGINT NOT NULL ," +
                "`thundering` BOOLEAN NOT NULL ," +
                "UNIQUE INDEX `unique` (`world` ASC, `x` ASC, `y` ASC, `z` ASC) )");
    }

}
