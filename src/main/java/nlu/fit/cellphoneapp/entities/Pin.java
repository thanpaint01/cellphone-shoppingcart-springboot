package nlu.fit.cellphoneapp.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="pin")
@Getter
@Setter
public class Pin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String pin;
    private int active;

    //OneToMany Relation product table
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pin_id")
    private List<Product> products = new ArrayList<>();
}
