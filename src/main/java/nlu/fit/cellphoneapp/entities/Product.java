package nlu.fit.cellphoneapp.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String img;
    private String name;
    private double price;
    private int amount;
    private int warranty;
    private String img01;
    private String img02;
    private String img03;
    private String img04;
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





}
