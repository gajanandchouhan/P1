package com.superlifesecretcode.app.ui.book.five;

import android.net.Uri;

public class Receipt {
    String receipt_id;
    String receipt_image_path;
    Uri receipt_uri;

    public String getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(String receipt_id) {
        this.receipt_id = receipt_id;
    }

    public String getReceipt_image_path() {
        return receipt_image_path;
    }

    public void setReceipt_image_path(String receipt_image_path) {
        this.receipt_image_path = receipt_image_path;
    }

    public Uri getReceipt_uri() {
        return receipt_uri;
    }

    public void setReceipt_uri(Uri receipt_uri) {
        this.receipt_uri = receipt_uri;
    }
}
