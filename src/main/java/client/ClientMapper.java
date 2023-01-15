package client;

import utils.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements Mapper {

    public Client map(ResultSet rs) {
        try {
            Client client = new Client();
            client.setName(rs.getString("name"));
            client.setId(rs.getLong("id"));
            return client;
        } catch (SQLException e) {
            new RuntimeException();
        }
        return null;
    }
}
