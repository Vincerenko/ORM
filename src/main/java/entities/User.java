package entities;

import ann.Column;
import ann.Entity;
import ann.Id;

@Entity(name = "user")
public class User {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    public User(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
