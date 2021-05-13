package nlu.fit.cellphoneapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

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
    @JsonIgnore
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

    public void updateTotalPrice(){
        setTotalPrice(this.getAmount()*this.getProduct().getPrice());
    }





}
