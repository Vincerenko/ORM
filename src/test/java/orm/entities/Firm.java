package orm.entities;

import annotations.Column;
import annotations.Entity;
import annotations.Id;
import annotations.OneToMany;

import java.util.List;
import java.util.Objects;

@Entity(name ="Firm")
public class Firm {

    @Id
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name ="power")
    private String power;

    @OneToMany(idName = "user_id")
    private List<User> users;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Firm firm = (Firm) o;
        return Objects.equals(id, firm.id) && Objects.equals(name, firm.name) && Objects.equals(power, firm.power) && Objects.equals(users, firm.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, power, users);
    }
}
