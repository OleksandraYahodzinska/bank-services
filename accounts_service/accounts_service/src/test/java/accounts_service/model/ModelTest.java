package accounts_service.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void testSavingsModel() {
        Savings savings = new Savings(1, 1000.0);

        assertEquals(1000.0, savings.getBalance());
        assertEquals(1, savings.getId());
    }

    @Test
    void testCreditModel() {
        Credit credit = new Credit(2, 500.0, 2000.0);

        assertEquals(500.0, credit.getBalance());
        assertEquals(2000.0, credit.getCreditLimit());
        assertEquals(2, credit.getId());
    }

    @Test
    void testClientModel() {
        Client client = new Client(1, "Oleksandra", "Yahodzinska");
        client.setName("Sasha");

        assertEquals("Sasha", client.getName());
        assertEquals("Yahodzinska", client.getSurname());
        assertEquals(1, client.getId());
    }
}