package nlu.fit.cellphoneapp.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order")
@Getter
@Setter
public class Order {
    public enum STATUS {
        PENDING(1), DELIVERING(2), SUCCESS(3), CANCELED(4);
        private final int value;

        STATUS(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "status")
    private int orderStatus;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "address")
    private String address;
    @Column(name = "name")
    private String nameOfClient;
    @Column(name = "phone")
    private String phoneNumberOfClient;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "active")
    private int active;
    @Column(name = "payment")
    private String payment;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderStatus='" + orderStatus + '\'' +
                ", createdDate=" + createdDate +
                ", address='" + address + '\'' +
                ", nameOfClient='" + nameOfClient + '\'' +
                ", phoneNumberOfClient='" + phoneNumberOfClient + '\'' +
                ", totalPrice=" + totalPrice +
                ", active=" + active +
                ", user=" + user +
                '}';
    }

    public String toStringStatus() {
        switch (this.orderStatus) {
            case Order.STATUS.PENDING.value():
                return "Đang tiếp nhận";
            case STATUS.DELIVERING.value():
                return "Đang vận chuyển";
            case STATUS.SUCCESS.value():
                return "Giao hàng thành công";
            default:
                return "Hủy đơn hàng";
        }
    }
}