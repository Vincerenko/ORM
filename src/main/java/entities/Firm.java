package entities;

import annotations.Column;
import annotations.Entity;
import annotations.Id;

@Entity(name ="Firm")
public class Firm {
    @Id
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name ="power")
    private String power;

    public Firm(String name, String power) {
        this.name = name;
        this.power = power;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPower() {
        return power;
    }
}
