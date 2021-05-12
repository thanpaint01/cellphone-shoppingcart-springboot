package nlu.fit.cellphoneapp.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="item_cart")
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name="total_price")
    private double totalPrice;
    private int amount;
    private int active;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne
    private Product product;


    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", amount=" + amount +
                ", active=" + active +
                ", user=" + user +
                ", product=" + product +
                '}';
    }





}
