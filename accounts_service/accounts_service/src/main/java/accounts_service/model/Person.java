package accounts_service.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class Person implements Comparable<Person>, Serializable {
    @Id
    protected int id;
    protected String name;
    protected String surname;

    public Person() {}

    public Person(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);
    }
}