package com.helix.demo.testtool.dbunit;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class DBUnitTool {

    public static IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("Test/testtesttool/dbunit/user.xml"));
    }

    public static Connection getConnection() throws Exception {
        Connection conn = null;
        if (Objects.isNull(conn)) {
            String url = "jdbc:postgresql://127.0.0.1:5432/test";
            String name = "test";
            String pwd = "test";
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, name, pwd);
        }
        return conn;
    }

    public static IDatabaseConnection getPostgresqlDBConn() throws Exception {
        DatabaseConnection conn = new DatabaseConnection(getConnection());
        DatabaseConfig dbConfig = conn.getConfig();
        dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
        dbConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
        return conn;
    }
}