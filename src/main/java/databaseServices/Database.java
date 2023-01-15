package databaseServices;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {

    private static BasicDataSource dataSource = null;

    static {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:./megasoft");
        dataSource.setUsername("");
        dataSource.setPassword("");

        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxTotal(25);

        migrate();
    }

    private Database() {

    }

    public static void setDataSource(BasicDataSource dataSource) {
        Database.dataSource = dataSource;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

    public static void migrate() {
        Flyway flyway = Flyway
                .configure()
                .dataSource(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword())
                .load();
        flyway.migrate();
    }

}
