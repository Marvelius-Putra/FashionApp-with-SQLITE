package com.example.fashion_express;

public class History {
    //data model history
    private int historyId;
    private int userId ;
    private int productId;
    private String transactionDate;
    private int amountProduct;
    private int totalPrice;

    public History(){

    }

    public History(int historyId, int userId, int productId, String transactionDate, int amountProduct, int totalPrice) {
        this.historyId = historyId;
        this.userId = userId;
        this.productId = productId;
        this.transactionDate = transactionDate;
        this.amountProduct = amountProduct;
        this.totalPrice = totalPrice;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getAmountProduct() {
        return amountProduct;
    }

    public void setAmountProduct(int amountProduct) {
        this.amountProduct = amountProduct;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
