package orm.entities;

import annotations.Column;
import annotations.Entity;
import annotations.Id;
import annotations.OneToMany;

import java.util.List;

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
