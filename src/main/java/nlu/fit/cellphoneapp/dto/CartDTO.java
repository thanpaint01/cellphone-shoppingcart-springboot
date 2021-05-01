package nlu.fit.cellphoneapp.dto;


import nlu.fit.cellphoneapp.others.Link;

public class CartDTO {
    private int id;
    private int productID;
    private int userID;
    private int amount;
    private double totalPrice;
    private String productName;
    private String productImg;
    private double productPrice;
    private int active;


    public CartDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = Link.HOST +productImg;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "CartDTO{" +
                "id=" + id +
                ", productID=" + productID +
                ", userID=" + userID +
                ", amount=" + amount +
                ", totalPrice=" + totalPrice +
                ", productName='" + productName + '\'' +
                ", productImg='" + productImg + '\'' +
                ", active=" + active +
                '}';
    }
}

