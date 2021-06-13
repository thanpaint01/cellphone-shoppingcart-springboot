package nlu.fit.cellphoneapp.DTOs;

import nlu.fit.cellphoneapp.converters.ImageAddress;
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
    private String img01;
    private String img02;
    private String img03;
    private String img04;
    private String title01;
    private String title02;
    private String title03;
    private String title04;
    private String script01;
    private String script02;
    private String script03;
    private String script04;
    private int active;

    public String getImg04() {
        return img04;
    }

    public void setImg04(String img04) {
        this.img04 = img04;
    }

    public String getTitle04() {
        return title04;
    }

    public void setTitle04(String title04) {
        this.title04 = title04;
    }

    public String getScript04() {
        return script04;
    }

    public void setScript04(String script04) {
        this.script04 = script04;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImg01() {
        return img01;
    }

    public void setImg01(String img01) {
        this.img01 = img01;
    }

    public String getImg02() {
        return img02;
    }

    public void setImg02(String img02) {
        this.img02 = img02;
    }

    public String getImg03() {
        return img03;
    }

    public void setImg03(String img03) {
        this.img03 = img03;
    }

    public String getTitle01() {
        return title01;
    }

    public void setTitle01(String title01) {
        this.title01 = title01;
    }

    public String getTitle02() {
        return title02;
    }

    public void setTitle02(String title02) {
        this.title02 = title02;
    }

    public String getTitle03() {
        return title03;
    }

    public void setTitle03(String title03) {
        this.title03 = title03;
    }

    public String getScript01() {
        return script01;
    }

    public void setScript01(String script01) {
        this.script01 = script01;
    }

    public String getScript02() {
        return script02;
    }

    public void setScript02(String script02) {
        this.script02 = script02;
    }

    public String getScript03() {
        return script03;
    }

    public void setScript03(String script03) {
        this.script03 = script03;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Product toProductEntity(){
        Product product = new Product();
        product.setName(this.name);
        product.setImg(new ImageAddressConventer().convertToEntityAttribute(img));
        product.setActive(active);
        product.setImg01(new ImageAddressConventer().convertToEntityAttribute(img01));
        product.setImg02(new ImageAddressConventer().convertToEntityAttribute(img02));
        product.setImg03(new ImageAddressConventer().convertToEntityAttribute(img03));
        product.setImg04(new ImageAddressConventer().convertToEntityAttribute(img04));
        product.setSelfieCamera(selfieCamera);
        product.setMainCamera(mainCamera);
        product.setSize(size);
        product.setPrice(price);
        product.setWarranty(12);
        product.setAmount(30);

        return product;
    }
}
