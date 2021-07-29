package orm.entities;

import annotations.Column;
import annotations.Entity;
import annotations.Id;
import annotations.OneToMany;

import java.util.List;
import java.util.Objects;

@Entity(name ="firm")
public class Firm {

    @Id
    private Long id;
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

    public Firm() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPower() {
        return power;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Firm firm = (Firm) o;
        return Objects.equals(id, firm.id) && Objects.equals(name, firm.name) && Objects.equals(power, firm.power)&& Objects.equals(users, firm.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, power,users);
    }

    @Override
    public String toString() {
        return "Firm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", power='" + power + '\'' +
                ", users=" + users +
                '}';
    }
}
