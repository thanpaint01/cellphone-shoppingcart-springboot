package nlu.fit.cellphoneapp.entities;


import lombok.Getter;
import lombok.Setter;
import nlu.fit.cellphoneapp.converters.ImageAddress;
import nlu.fit.cellphoneapp.converters.ImageAddressConventer;

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
            orphanRemoval = true
    )
    private List<CartItem> cartItems = new ArrayList<>();





}
