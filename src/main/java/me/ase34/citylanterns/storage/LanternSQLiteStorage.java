package me.ase34.citylanterns.storage;

import java.io.File;
import java.sql.DriverManager;
import java.sql.Statement;

import java.sql.Connection;

public class LanternSQLiteStorage extends LanternSQLStorage {

    private File file;    
    private Connection conn;
    
    @Override
    protected void createTables() throws Exception {
        Statement stmt = getConnection().createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS `" + table + "` (" +
                "`world` VARCHAR(45) NOT NULL ," +
                "`x` INT NOT NULL ," +
                "`y` INT NOT NULL ," +
                "`z` INT NOT NULL ," +
                "`group` VARCHAR(45) NOT NULL);" +
                "CREATE UNIQUE INDEX IF NOT EXISTS `unique` ON `lanterns` (" +
                "`world` ASC, " +
                "`x` ASC, " +
                "`y` ASC, " +
                "`z` ASC);");
    }

    public LanternSQLiteStorage(File file, String table) {
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
