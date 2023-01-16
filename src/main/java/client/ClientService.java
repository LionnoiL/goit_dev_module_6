package client;

import databaseServices.SqlHelper;
import parameters.StatementParameters;

import java.util.List;

public class ClientService {

    public long create(String name) {

        if (!ClientValidator.validateName(name)) {
            throw new RuntimeException("Invalid name length");
        }

        StatementParameters<String, Long > parametrs = new StatementParameters<>(name);

        return new SqlHelper<Long>().insertOne(
                "INSERT INTO client (name) VALUES (?);",
                parametrs
        );
    }

    public String getById(long id) {

        if (!ClientValidator.validateId(id)) {
            throw new RuntimeException("Invalid id");
        }

        StatementParameters<Long, String> parameters = new StatementParameters<>(id);

        Client client = new SqlHelper<Client>().getOne("SELECT * FROM client WHERE id = ?",
                parameters,
                new ClientMapper());

        if (client != null) {
            return client.getName();
        }

        return null;
    }

    public void setName(long id, String name) {

        if (!ClientValidator.validateId(id)) {
            throw new RuntimeException("Invalid id");
        }

        if (!ClientValidator.validateName(name)) {
            throw new RuntimeException("Invalid name length");
        }

        StatementParameters<String, Long> parameters = new StatementParameters<>(name, id);

        new SqlHelper<>().execSql("UPDATE client SET name = ? WHERE id = ?"
                , parameters);
    }

    public void deleteById(long id) {

        if (!ClientValidator.validateId(id)) {
            throw new RuntimeException("Invalid id");
        }

        StatementParameters<Long, String> parameters = new StatementParameters<>(id);

        new SqlHelper<>().execSql("DELETE project_worker WHERE project_id in (SELECT id FROM project WHERE client_id = ?)"
                , parameters);

        new SqlHelper<>().execSql("DELETE project WHERE client_id = ?"
                , parameters);

        new SqlHelper<>().execSql("DELETE client WHERE id = ?"
                , parameters);
    }

    public List<Client> listAll() {
        return new SqlHelper<Client>().getAll("SELECT * FROM client", new ClientMapper());
    }
}
