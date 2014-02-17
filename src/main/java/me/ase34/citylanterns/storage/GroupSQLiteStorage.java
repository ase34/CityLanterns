package me.ase34.citylanterns.storage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class GroupSQLiteStorage extends GroupSQLStorage {

    private File file;    
    private Connection conn;
    
    @Override
    protected void createTables() throws Exception {
        Statement stmt = getConnection().createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS `" + table + "` (" +
                "`name` VARCHAR(45) NOT NULL ," +
                "`daytime` BIGINT NOT NULL ," +
                "`nighttime` BIGINT NOT NULL ," +
                "`thundering` BOOLEAN NOT NULL );" +
                "CREATE UNIQUE INDEX IF NOT EXISTS `unique` ON `lanterns` (`name` ASC);");
    }

    public GroupSQLiteStorage(File file, String table) {
        super(table);
        this.file = file;
    }

    @Override
    public Connection getConnection() throws Exception {
        if (conn != null && !conn.isClosed()) {
            return conn;
        }
        
        file.createNewFile();
        
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
        return conn;
    }

}
