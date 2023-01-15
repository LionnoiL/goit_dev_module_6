package client;

import databaseServices.Database;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ClientServiceTest {

    ClientService clientService;

    @BeforeEach
    void beforEach() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("");
        dataSource.setPassword("");

        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxTotal(25);

        Database.setDataSource(dataSource);

        Database.migrate();

        clientService = new ClientService();
    }

    @Test
    void getById() {
        String byId = clientService.getById(5);
        System.out.println("byId = " + byId);
    }

    @Test
    void create() {
        long id = clientService.create("Andriy");
        String name = clientService.getById(id);
        Assertions.assertEquals(name, "Andriy");
    }

    @Test
    void setName() {
        clientService.setName(2, "Gnat");
        String name = clientService.getById(2);
        Assertions.assertEquals(name, "Gnat");
    }

    @Test
    void deleteById() {
        clientService.deleteById(5);
        String name = clientService.getById(5);
        Assertions.assertEquals(name, null);
    }

    @Test
    void listAll() {
        List<Client> clients = clientService.listAll();
        System.out.println("clients = " + clients);
    }
}