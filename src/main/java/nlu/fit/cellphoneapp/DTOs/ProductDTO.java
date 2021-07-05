package nlu.fit.cellphoneapp.DTOs;

import nlu.fit.cellphoneapp.converters.ImageAddressConventer;
import nlu.fit.cellphoneapp.entities.Product;

public class ProductDTO {
    private String id;
    private String name;
    private double price;
    private String img;
    private int brandID;
    private int ramID;
    private int romID;
    private int pinID;
    private String size;
    private String selfieCamera;
    private String mainCamera;
    private String longDescription;
    private int active;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public int getRamID() {
        return ramID;
    }

    public void setRamID(int ramID) {
        this.ramID = ramID;
    }

    public int getRomID() {
        return romID;
    }

    public void setRomID(int romID) {
        this.romID = romID;
    }

    public int getPinID() {
        return pinID;
    }

    public void setPinID(int pinID) {
        this.pinID = pinID;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSelfieCamera() {
        return selfieCamera;
    }

    public void setSelfieCamera(String selfieCamera) {
        this.selfieCamera = selfieCamera;
    }

    public String getMainCamera() {
        return mainCamera;
    }

    public void setMainCamera(String mainCamera) {
        this.mainCamera = mainCamera;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Product toProductEntity() {
        Product product = new Product();
        product.setName(this.name);
        product.setActive(active);
        product.setLongDescription(longDescription);
        product.setSelfieCamera(selfieCamera);
        product.setMainCamera(mainCamera);
        product.setSize(size);
        product.setPrice(price);
        product.setWarranty(12);
        product.setAmount(30);

        return product;
    }
}
