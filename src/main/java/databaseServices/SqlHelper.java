package databaseServices;

import utils.Mapper;
import parameters.StatementParameters;
import utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SqlHelper<T> {

    public static void executeQuery(String sql, Consumer<ResultSet> consumer) {

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()
        ) {
            consumer.accept(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static int executeUpdateSql(String sql) {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void executeSqlFile(String fileName) {
        String sql = Utils.getFilesLines(fileName);
        executeUpdateSql(sql);
    }

    public List<T> getAll(String sql, StatementParameters parametrs, Mapper mapper) {

        List<T> result = new ArrayList<T>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (Connection connection = Database.getConnection()) {
            statement = connection.prepareStatement(sql);
            parametrs.fillStatement(statement);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.add((T) mapper.map(resultSet));
            }

            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;

    }

    public List<T> getAll(String sql, Mapper mapper) {

        List<T> result = new ArrayList<T>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (Connection connection = Database.getConnection()) {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.add((T) mapper.map(resultSet));
            }

            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;

    }

    public T getOne(String sql, StatementParameters parametrs, Mapper mapper) {

        List<T> list = getAll(sql, parametrs, mapper);

        if (list.size() == 0) {
            return null;
        }

        return list.get(0);

    }

    public long insertOne(String sql, StatementParameters parametrs) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long result = -1;

        try (Connection connection = Database.getConnection()) {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            parametrs.fillStatement(statement);
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            result = resultSet.getInt(1);

            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;

    }

    public void execSql(String sql, StatementParameters parametrs) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try (Connection connection = Database.getConnection()) {
            statement = connection.prepareStatement(sql);
            parametrs.fillStatement(statement);
            statement.executeUpdate();

            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
