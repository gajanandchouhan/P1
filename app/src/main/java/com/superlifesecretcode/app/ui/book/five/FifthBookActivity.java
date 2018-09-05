package com.superlifesecretcode.app.ui.book.five;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.first.FirstBookActivity;
import com.superlifesecretcode.app.ui.book.six.SixthBookActivity;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import com.superlifesecretcode.app.util.ImagePickerUtils;
import com.superlifesecretcode.app.util.PermissionConstant;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FifthBookActivity extends BaseActivity implements FifthBookView {

    TextView textview_payment, textview_attachmentline;
    EditText edittext_enteramount;
    RecyclerView rechcyclerview_bankslist;
    FifthBookPresenter fifthBookPresenter;
    BankAapter bankAapter;
    ArrayList<Bank> bankArrayList;
    private UserDetailResponseData userData;
    ImageView back_image;
    TextView textview_total, textview_total_price, textview_back, textview_next;
    RecyclerView receipt_recyclerview;
    public ArrayList<Receipt> receipt_list;
    ReceiptAapter receiptAapter;
    Dialog dialog;
    String total_amont;
    TextView textview_plus;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_fifth;
    }

    @Override
    protected void initializeView() {
        bankArrayList = new ArrayList<>();
        receipt_list = new ArrayList<>();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        total_amont = SuperLifeSecretPreferences.getInstance().getString("total_amount");
        textview_payment = findViewById(R.id.textview_payment);
        textview_attachmentline = findViewById(R.id.textview_attachmentline);
        edittext_enteramount = findViewById(R.id.edittext_enteramount);
        rechcyclerview_bankslist = findViewById(R.id.rechcyclerview_bankslist);
        back_image = findViewById(R.id.back_image);
        textview_total = findViewById(R.id.textview_total);
        textview_total_price = findViewById(R.id.textview_total_price);
        textview_total_price.setText(total_amont);
        textview_back = findViewById(R.id.textview_back);
        textview_next = findViewById(R.id.textview_next);
        // imageview_receipt = findViewById(R.id.imageview_receipt);
        receipt_recyclerview = findViewById(R.id.receipt_recyclerview);
        textview_plus = findViewById(R.id.textview_plus);

        rechcyclerview_bankslist.setLayoutManager(new LinearLayoutManager(this));
        bankAapter = new BankAapter(bankArrayList, this, new BankListener());
        rechcyclerview_bankslist.setAdapter(bankAapter);

        receipt_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        receiptAapter = new ReceiptAapter(receipt_list, this, new ReceiptListener());
        receipt_recyclerview.setAdapter(receiptAapter);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        fifthBookPresenter.getBankList(headers);

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textview_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bankArrayList == null || bankArrayList.size() == 0) {
                    CommonUtils.showSnakeBar(FifthBookActivity.this, "Please select bank!");
                    return;
                }
                if (receipt_list == null || receipt_list.size() == 0) {
                    CommonUtils.showSnakeBar(FifthBookActivity.this, "Please upload receipt!");
                    return;
                }
                showDialog();
            }
        });

        edittext_enteramount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.hasPermissions(FifthBookActivity.this, PermissionConstant.PERMISSION_PROFILE)) {
                    pickImage();
                } else {
                    ActivityCompat.requestPermissions(FifthBookActivity.this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
                }
            }
        });

        textview_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.hasPermissions(FifthBookActivity.this, PermissionConstant.PERMISSION_PROFILE)) {
                    pickImage();
                } else {
                    ActivityCompat.requestPermissions(FifthBookActivity.this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
                }
            }
        });
    }

    private void orderWebservice() {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("book_type", SuperLifeSecretPreferences.getInstance().getString("book_type"));
        Log.e("book_type", "" + SuperLifeSecretPreferences.getInstance().getString("book_type"));
        builder.addFormDataPart("order_for", SuperLifeSecretPreferences.getInstance().getString("book_order_for"));
        Log.e("order_for", "" + SuperLifeSecretPreferences.getInstance().getString("book_order_for"));
        builder.addFormDataPart("name", SuperLifeSecretPreferences.getInstance().getString("book_full_name"));
        Log.e("name", "" + SuperLifeSecretPreferences.getInstance().getString("book_full_name"));

        if (SuperLifeSecretPreferences.getInstance().getString("book_order_for").equals("2")) {
            Log.e("book_order_for", "" + SuperLifeSecretPreferences.getInstance().getString("book_order_for"));
            builder.addFormDataPart("mobile", SuperLifeSecretPreferences.getInstance().getString("book_mobile"));
            Log.e("mobile", "" + SuperLifeSecretPreferences.getInstance().getString("book_mobile"));
            builder.addFormDataPart("email", SuperLifeSecretPreferences.getInstance().getString("book_email"));
            Log.e("email", "" + SuperLifeSecretPreferences.getInstance().getString("book_email"));
        } else {
            Log.e("book_order_for", "" + SuperLifeSecretPreferences.getInstance().getString("book_order_for"));
            builder.addFormDataPart("mobile", "");
            Log.e("mobile", "");
            builder.addFormDataPart("email", "");
            Log.e("email", "");
        }

        int deliverytype = 0;
        if (SuperLifeSecretPreferences.getInstance().getString("status_old_store_address").equals("0")) {
            Log.e("status_old_store_adres", ""+SuperLifeSecretPreferences.getInstance().getString("status_old_store_address"));
            builder.addFormDataPart("address", SuperLifeSecretPreferences.getInstance().getString("book_address"));
            Log.e("address", ""+SuperLifeSecretPreferences.getInstance().getString("book_address"));
            builder.addFormDataPart("pin_code", SuperLifeSecretPreferences.getInstance().getString("book_pin_code"));
            Log.e("pin_code", ""+SuperLifeSecretPreferences.getInstance().getString("book_pin_code"));
            builder.addFormDataPart("state", SuperLifeSecretPreferences.getInstance().getString("book_state"));
            Log.e("state", ""+SuperLifeSecretPreferences.getInstance().getString("book_state"));
            builder.addFormDataPart("city", SuperLifeSecretPreferences.getInstance().getString("book_city"));
            Log.e("city", ""+SuperLifeSecretPreferences.getInstance().getString("book_city"));
            deliverytype = 1;
        } else if (SuperLifeSecretPreferences.getInstance().getString("status_old_store_address").equals("1")) {
            Log.e("status_old_store_adres", ""+SuperLifeSecretPreferences.getInstance().getString("status_old_store_address"));
            builder.addFormDataPart("address", "");
            Log.e("address", "");
            builder.addFormDataPart("pin_code", "");
            Log.e("pin_code", "");
            builder.addFormDataPart("state", "");
            Log.e("state", "");
            builder.addFormDataPart("city", "");
            Log.e("city", "");
            deliverytype = 1;
            builder.addFormDataPart("old_shipping_address", SuperLifeSecretPreferences.getInstance().getString("book_old_address_id"));
            Log.e("old_shipping_address", ""+SuperLifeSecretPreferences.getInstance().getString("book_old_address_id"));
        } else if (SuperLifeSecretPreferences.getInstance().getString("status_old_store_address").equals("2")) {
            Log.e("status_old_store_adres", ""+SuperLifeSecretPreferences.getInstance().getString("status_old_store_address"));
            deliverytype = 2;
            builder.addFormDataPart("store_id", SuperLifeSecretPreferences.getInstance().getString("book_store_id"));
        }
        builder.addFormDataPart("delevery_type", "" + deliverytype);
        Log.e("delevery_type", ""+deliverytype);
        builder.addFormDataPart("bank_id", SuperLifeSecretPreferences.getInstance().getString("bank_id"));
        Log.e("bank_id", ""+SuperLifeSecretPreferences.getInstance().getString("bank_id"));

        ArrayList<BookBean> bookBeanList = SuperLifeSecretPreferences.getInstance().getSelectedBooksList();
        String books = "";
        for (int p = 0; p < bookBeanList.size(); p++) {
            if (p == (bookBeanList.size() - 1)) {
                books = books.concat(bookBeanList.get(p).getId() + "*" + bookBeanList.get(p).getQuantity() + "*" + bookBeanList.get(p).getQuantity());
            } else {
                books = books.concat(bookBeanList.get(p).getId() + "*" + bookBeanList.get(p).getQuantity() + "*" + bookBeanList.get(p).getQuantity() + ",");
            }
        }
        builder.addFormDataPart("books", books);
        Log.e("books", ""+books.toString());
        for (int i = 0; i < receipt_list.size(); i++) {
            try {
                if (receipt_list.get(i) != null) {
                    File file = new File(receipt_list.get(i).getReceipt_image_path());
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    builder.addFormDataPart("receipts[]", file.getName(), requestBody);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        Log.e("receipts", ""+receipt_list.toString());
        Log.e("builder", "" + builder.toString());
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        RequestBody finalRequestBody = builder.build();
        Log.e("builder", "" + finalRequestBody.toString());
        fifthBookPresenter.bookOrder(finalRequestBody, headers);
    }

    private void pickImage() {
        if (receipt_list.size()<=5){
            CropImage.activity()
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .start(this);
        }else {
            textview_plus.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                edittext_enteramount.setVisibility(View.GONE);

                Receipt receipt = new Receipt();
                receipt.setReceipt_uri(result.getUri());
                receipt.setReceipt_image_path(ImagePickerUtils.getPath(this, result.getUri()));
                receipt_list.add(receipt);
                receiptAapter.notifyDataSetChanged();
                textview_plus.setVisibility(View.VISIBLE);
//                back_image.setImageURI(result.getUri());
//                imagePath = ImagePickerUtils.getPath(this, result.getUri());
//                if (imagePath != null) {
//                    ImageLoadUtils.loadImage(imagePath, imageview_receipt);
//                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public class ReceiptListener {
        public void onImageSelect() {
            if (CommonUtils.hasPermissions(FifthBookActivity.this, PermissionConstant.PERMISSION_PROFILE)) {
                pickImage();
            } else {
                ActivityCompat.requestPermissions(FifthBookActivity.this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
            }
        }

        public void onDeleteImage(int position) {
            receipt_list.remove(position);
            receiptAapter.notifyDataSetChanged();
        }
    }


    public class BankListener {
        public void onSelectBank(boolean selected_status, int pos) {
            for (int i = 0; i < bankArrayList.size(); i++) {
                if (i == pos) {
                    if (selected_status == true) {
                        bankArrayList.get(i).setSelected(false);
                    } else {
                        SuperLifeSecretPreferences.getInstance().putString("bank_id", bankArrayList.get(i).getId());
                        bankArrayList.get(i).setSelected(true);
                    }
                } else {
                    bankArrayList.get(i).setSelected(false);
                }
            }
            bankAapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initializePresenter() {
        fifthBookPresenter = new FifthBookPresenter(this);
        fifthBookPresenter.setView(this);
    }

    @Override
    public void getBanks(BankBean bankBean) {
        bankArrayList.clear();
        bankArrayList.addAll(bankBean.getBankList());
        bankAapter.notifyDataSetChanged();
    }

    @Override
    public void onOrderSuccess(BaseResponseModel bankBean) {
        Toast.makeText(this, "" + bankBean.getMessage(), Toast.LENGTH_SHORT).show();
        if (bankBean.getStatus().equals("true")) {
            Intent i = new Intent(FifthBookActivity.this, SixthBookActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Toast.makeText(this, "" + bankBean.getStatus(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "" + bankBean.getStatus(), Toast.LENGTH_SHORT).show();
        }
    }

    public void showDialog() {
        dialog = new Dialog(FifthBookActivity.this, R.style.DialogCustomTheme);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_receipt);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        BigReceiptAapter receiptAapter;
        ImageView imageview_cross = dialog.findViewById(R.id.imageview_cross);
        ImageView imageview_right = dialog.findViewById(R.id.imageview_right);
//        ImageView corss = dialog.findViewById(R.id.corss);
        RecyclerView recyclerView = dialog.findViewById(R.id.recycler_view);
        LinearLayout bank_linear  = dialog.findViewById(R.id.bank_linear);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        BigReceiptAapter bigReceiptAapter = new BigReceiptAapter(receipt_list, this);
        recyclerView.setAdapter(bigReceiptAapter);

        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        imageview_cross.startAnimation(startAnimation);
//        corss.startAnimation(startAnimation);
        imageview_right.startAnimation(startAnimation);
        bank_linear.startAnimation(startAnimation);
        imageview_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        imageview_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              orderWebservice();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}
