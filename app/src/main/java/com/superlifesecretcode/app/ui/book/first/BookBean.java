package com.superlifesecretcode.app.ui.book.first;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class BookBean implements Serializable{

    String author_name,description,id,image,name,stock;
    double price;
    double discount_applied=0;
    double price_after_discount;
    boolean selected  = false;
    int quantity=0;
    ArrayList<Discount> discount;
    boolean expand_collapase = false;

    public double getPrice_after_discount() {
        return price_after_discount;
    }

    public void setPrice_after_discount(double price_after_discount) {
        this.price_after_discount = price_after_discount;
    }

    public double getDiscount_applied() {
        return discount_applied;
    }

    public void setDiscount_applied(double discount_applied) {
        this.discount_applied = discount_applied;
    }

    public boolean isExpand_collapase() {
        return expand_collapase;
    }

    public void setExpand_collapase(boolean expand_collapase) {
        this.expand_collapase = expand_collapase;
    }

    public ArrayList<Discount> getDiscount() {
        return discount;
    }

    public void setDiscount(ArrayList<Discount> discount) {
        this.discount = discount;
    }

    public static Comparator<BookBean> getPriceComparater() {
        return PriceComparater;
    }

    public static void setPriceComparater(Comparator<BookBean> priceComparater) {
        PriceComparater = priceComparater;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    /*Comparator for sorting the list by price no*/
    public static Comparator<BookBean> PriceComparater = new Comparator<BookBean>() {
        public int compare(BookBean s1, BookBean s2) {

            int price1 = (int) s1.getPrice();
            int price2 = (int) s2.getPrice();

            /*For ascending order*/
//            return rollno1-rollno2;

            /*For descending order*/
           return price2-price1;
        }};
}



