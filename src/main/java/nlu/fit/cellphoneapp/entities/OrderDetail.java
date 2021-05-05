package nlu.fit.cellphoneapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int amount;
    @Column(name = "initail_price")
    private double initialPrice;
    @Column(name = "saled_price")
    private double saledPrice;
    @Column(name = "total_price")
    private double totalPrice;
    private double price;
    private int active;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", amount=" + amount +
                ", initialPrice=" + initialPrice +
                ", saledPrice=" + saledPrice +
                ", totalPrice=" + totalPrice +
                ", price=" + price +
                ", active=" + active +
                ", order=" + order +
                ", product=" + product +
                '}';
    }
}
