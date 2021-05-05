package nlu.fit.cellphoneapp.entities;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nlu.fit.cellphoneapp.converters.ImageAddress;
import nlu.fit.cellphoneapp.converters.ImageAddressConventer;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Convert(converter = ImageAddressConventer.class)
    private ImageAddress img;
    private String name;
    private double price;
    private int amount;
    private int warranty;
    @Convert(converter = ImageAddressConventer.class)
    private ImageAddress img01;
    @Convert(converter = ImageAddressConventer.class)
    private ImageAddress img02;
    @Convert(converter = ImageAddressConventer.class)
    private ImageAddress img03;
    @Convert(converter = ImageAddressConventer.class)
    private ImageAddress img04;
    private String size;
    @Column(name = "selfie_camera")
    private String selfieCamera;
    @Column(name = "main_camera")
    private String mainCamera;
    private int active;

    //relationship to other table
    @ManyToOne(fetch = FetchType.LAZY)
    private Ram ram;
    @ManyToOne(fetch = FetchType.LAZY)
    private Rom rom;
    @ManyToOne(fetch = FetchType.LAZY)
    private Pin pin;
    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<CartItem> cartItems = new ArrayList<>();
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public void deleteCartItem(CartItem c){
        cartItems.remove(c);
//        for (CartItem cc: c.getProduct().cartItems) {
//            System.out.println("ccinProduct="+cc);
//            c.getProduct().cartItems.remove(cc);
//        }
        c.getProduct().cartItems.remove(this);
    }


}
