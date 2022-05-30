package com.sample.pharmacy;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Medicines {

    private SimpleStringProperty id;
    private SimpleStringProperty medicineName;
    private SimpleStringProperty companyName;
    private SimpleStringProperty orderDate;
    private SimpleIntegerProperty quantity;
    private SimpleIntegerProperty price;
    private SimpleIntegerProperty total;

    public Medicines(String id,String medicineName,String companyName,String orderDate,Integer quantity,Integer price,Integer total) {
        this.id=new SimpleStringProperty(id);
        this.medicineName=new SimpleStringProperty(medicineName);
        this.companyName=new SimpleStringProperty(companyName);
        this.orderDate=new SimpleStringProperty(orderDate);
        this.quantity=new SimpleIntegerProperty(quantity);
        this.price=new SimpleIntegerProperty(price);
        this.total=new SimpleIntegerProperty(total);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }
    public String getMedicineName() {
        return medicineName.get();
    }

    public void setMedicineName(String medicineName) {
        this.medicineName.set(medicineName);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public String getOrderDate() {
        return orderDate.get();
    }

    public void setOrderDate(String orderDate) {
        this.orderDate.set(orderDate);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getTotal() {
        return total.get();
    }

    public void setTotal(int total) {
        this.total.set(total);
    }

}
