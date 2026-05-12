package accounts_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class Client extends Person {

    public Client() {
    }

    public Client(int id, String name, String surname) {
        super(id, name, surname);
    }
}