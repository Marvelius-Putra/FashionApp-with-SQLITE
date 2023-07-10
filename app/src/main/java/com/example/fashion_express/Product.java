package com.example.fashion_express;

public class Product {
    //atribut produk
    private int productId;
    private String productName;
    private String category;
    private int price;
    private String storeName;
    private int stok;
    private String imgurl;

    public Product(){

    }

    public Product(int productId, String productName, String category, int price, String storeName, int stok, String imgurl) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.storeName = storeName;
        this.stok = stok;
        this.imgurl = imgurl;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
