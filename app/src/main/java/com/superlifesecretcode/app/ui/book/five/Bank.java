package com.superlifesecretcode.app.ui.book.five;

public class Bank {

    String id , bank_name , account_number , bank_icon;
    boolean selected;


    public String getBank_icon() {
        return bank_icon;
    }

    public void setBank_icon(String bank_icon) {
        this.bank_icon = bank_icon;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }
}
