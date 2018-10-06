package com.superlifesecretcode.app.ui.book.five;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ramotion.foldingcell.FoldingCell;
import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.SuperLifeSecretCodeApp;
import com.superlifesecretcode.app.data.model.BaseResponseModel;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.first.FirstBookActivity;
import com.superlifesecretcode.app.ui.book.second.DeliveryData;
import com.superlifesecretcode.app.ui.book.six.SixthBookActivity;
import com.superlifesecretcode.app.ui.picker.DropDownWindow;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ImageLoadUtils;
import com.superlifesecretcode.app.util.ImagePickerUtils;
import com.superlifesecretcode.app.util.PermissionConstant;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
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
    TextView textview_plus, tv_payment_date, tv_payment_type;
    ArrayList<String> payment_type_list;
    String payment_type = "", payment_date = "", payment_type_string = "", payment_date_string = "";
    TextView payment_date_heading, payment_type_heading, textView_title;
    private LanguageResponseData languageResponseData;
    double delivery_charges = 0.0;

    @Override
    protected int getContentView() {
        return R.layout.activity_book_fifth;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CommonUtils.book_stake == true) {
            finish();
        }
    }

    @Override
    protected void initializeView() {
        bankArrayList = new ArrayList<>();
        receipt_list = new ArrayList<>();
        languageResponseData = SuperLifeSecretCodeApp.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        total_amont = SuperLifeSecretPreferences.getInstance().getString("total_amount");
        textview_payment = findViewById(R.id.textview_payment);
        textview_attachmentline = findViewById(R.id.textview_attachmentline);
        edittext_enteramount = findViewById(R.id.edittext_enteramount);
        rechcyclerview_bankslist = findViewById(R.id.rechcyclerview_bankslist);
        back_image = findViewById(R.id.back_image);
        textview_total = findViewById(R.id.textview_total);
        textview_total_price = findViewById(R.id.textview_total_price);
        payment_date_heading = findViewById(R.id.payment_date_heading);
        payment_type_heading = findViewById(R.id.payment_type_heading);
        try {
            textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", Double.parseDouble(total_amont)));
        } catch (Exception e) {
            textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + total_amont);
        }
        //textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + total_amont);
        textView_title = findViewById(R.id.textView_title);
        textView_title.setText(SuperLifeSecretPreferences.getInstance().getString("book_title"));
        textview_back = findViewById(R.id.textview_back);
        textview_next = findViewById(R.id.textview_next);
        // imageview_receipt = findViewById(R.id.imageview_receipt);
        receipt_recyclerview = findViewById(R.id.receipt_recyclerview);
        textview_plus = findViewById(R.id.textview_plus);
        tv_payment_date = findViewById(R.id.tv_payment_date);
        tv_payment_type = findViewById(R.id.tv_payment_type);
        payment_type_list = new ArrayList<>();
        payment_type_list.add(languageResponseData.getBank_transfer());
        payment_type_list.add(languageResponseData.getCash_deposit());
        payment_type_list.add(languageResponseData.getCheque_deposit());

        rechcyclerview_bankslist.setLayoutManager(new LinearLayoutManager(this));
        bankAapter = new BankAapter(bankArrayList, this, new BankListener());
        rechcyclerview_bankslist.setAdapter(bankAapter);

        receipt_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        receiptAapter = new ReceiptAapter(receipt_list, this, new ReceiptListener(), true);
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
                String bank_name = "";
                String account_number = "";
                boolean check_any_selected = false;
                for (int a = 0; a < bankArrayList.size(); a++) {
                    if (bankArrayList.get(a).isSelected()) {
                        check_any_selected = true;
                        bank_name = bankArrayList.get(a).getBank_name();
                        account_number = bankArrayList.get(a).getAccount_number();
                        break;
                    }
                }
                if (payment_type.equals("")) {
                    CommonUtils.showSnakeBar(FifthBookActivity.this, languageResponseData.getPlease_select_pay_mode());
                    return;
                }

                if (payment_date.equals("")) {
                    CommonUtils.showSnakeBar(FifthBookActivity.this, languageResponseData.getPlease_select_pay_date());
                    return;
                }

                if (check_any_selected == false) {
                    CommonUtils.showSnakeBar(FifthBookActivity.this, languageResponseData.getPlease_select_bank());
                    return;
                }
                if (receipt_list == null || receipt_list.size() == 0) {
                    CommonUtils.showSnakeBar(FifthBookActivity.this, languageResponseData.getPlease_add_receipts());
                    return;
                }
                showDialog(bank_name, account_number);
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
        tv_payment_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showgGenderSelection();
            }
        });
        tv_payment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(tv_payment_date, (System.currentTimeMillis() - 1000));
            }
        });
        setUpConversion();
    }


    private void showgGenderSelection() {
        DropDownWindow.show(this, tv_payment_type, payment_type_list, new DropDownWindow.SelectedListner() {
            @Override
            public void onSelected(String value, int position) {
                tv_payment_type.setText(value);
                payment_type_string = value;
                payment_type = String.valueOf(position + 1);
            }
        });
    }

    private void showDatePicker(final TextView textView, long minDate) {
        CommonUtils.showDatePickerWithMax(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                payment_date = CommonUtils.getAppendedDate(i, i1, i2);
                payment_date_string = CommonUtils.getFromatttedDate(i, i1, i2);
                tv_payment_date.setText(CommonUtils.getFromatttedDate(i, i1, i2));
            }
        }, minDate);
    }

    private void orderWebservice() {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("book_type", SuperLifeSecretPreferences.getInstance().getString("book_type"));
        builder.addFormDataPart("order_for", SuperLifeSecretPreferences.getInstance().getString("book_order_for"));

        if (SuperLifeSecretPreferences.getInstance().getString("book_order_for").equals("2")) {
            builder.addFormDataPart("other_person_name", SuperLifeSecretPreferences.getInstance().getString("other_person_name"));
            builder.addFormDataPart("other_person_mobile", SuperLifeSecretPreferences.getInstance().getString("other_person_contact"));
        }

        builder.addFormDataPart("name", SuperLifeSecretPreferences.getInstance().getString("book_full_name"));
        builder.addFormDataPart("mobile", SuperLifeSecretPreferences.getInstance().getString("book_mobile"));
        builder.addFormDataPart("email", SuperLifeSecretPreferences.getInstance().getString("book_email"));

        int deliverytype = 0;
        if (SuperLifeSecretPreferences.getInstance().getString("status_old_store_address").equals("0")) {
            builder.addFormDataPart("address", SuperLifeSecretPreferences.getInstance().getString("book_address"));
            builder.addFormDataPart("pin_code", SuperLifeSecretPreferences.getInstance().getString("book_pin_code"));
            builder.addFormDataPart("state", SuperLifeSecretPreferences.getInstance().getString("book_state"));
            builder.addFormDataPart("city", SuperLifeSecretPreferences.getInstance().getString("book_city"));
            deliverytype = 1;
        } else if (SuperLifeSecretPreferences.getInstance().getString("status_old_store_address").equals("1")) {
            deliverytype = 1;
            builder.addFormDataPart("store_id", SuperLifeSecretPreferences.getInstance().getString("book_old_address_id"));
        } else if (SuperLifeSecretPreferences.getInstance().getString("status_old_store_address").equals("2")) {
            deliverytype = 2;
            builder.addFormDataPart("store_id", "");
        }
       // builder.addFormDataPart("store_id", SuperLifeSecretPreferences.getInstance().getString("book_old_address_id"));
//        Toast.makeText(this, "delivery type  "+deliverytype, Toast.LENGTH_SHORT).show();
        builder.addFormDataPart("delevery_type", "" + deliverytype);
        builder.addFormDataPart("bank_id", SuperLifeSecretPreferences.getInstance().getString("bank_id"));

        ArrayList<BookBean> bookBeanList = SuperLifeSecretPreferences.getInstance().getSelectedBooksList();
        String books = "";
        for (int p = 0; p < bookBeanList.size(); p++) {
            if (p == (bookBeanList.size() - 1)) {
                books = books.concat(bookBeanList.get(p).getId() + "*" + bookBeanList.get(p).getQuantity() + "*" + (bookBeanList.get(p).getPrice_after_discount()+bookBeanList.get(p).getDiscount_applied())+ "*" + bookBeanList.get(p).getDiscount_applied());
            } else {
                books = books.concat(bookBeanList.get(p).getId() + "*" + bookBeanList.get(p).getQuantity() + "*" +  (bookBeanList.get(p).getPrice_after_discount()+bookBeanList.get(p).getDiscount_applied()) + "*" + bookBeanList.get(p).getDiscount_applied()+ ",");
            }
        }
        builder.addFormDataPart("books", books);
        builder.addFormDataPart("payment_date", payment_date);
        builder.addFormDataPart("payment_mode", payment_type);
        builder.addFormDataPart("delivery_charge", SuperLifeSecretPreferences.getInstance().getString("delivery_charges"));
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
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        RequestBody finalRequestBody = builder.build();
        fifthBookPresenter.bookOrder(finalRequestBody, headers);
    }

    private void setUpConversion() {

        if (languageResponseData != null) {
            textview_back.setText(languageResponseData.getBack());
            textview_next.setText(languageResponseData.getNext());
            textview_attachmentline.setText(languageResponseData.getAttached_file());
            textview_total.setText(languageResponseData.getTotal());
            payment_type_heading.setText(languageResponseData.getPayment_mode());
            payment_date_heading.setText(languageResponseData.getPayment_date());
            tv_payment_type.setText("-" + languageResponseData.getSelect() + "-");
            tv_payment_date.setText("-" + languageResponseData.getSelect() + "-");
            try {
                textview_payment.setText("" + languageResponseData.getPlease_pay() + " " + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", Double.parseDouble(total_amont)) + " " + languageResponseData.getOne_following_account());
                // textview_total_price.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", Double.parseDouble(total_amont)));
            } catch (Exception e) {
                textview_payment.setText("" + languageResponseData.getPlease_pay() + " " + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + total_amont + " " + languageResponseData.getOne_following_account());
            }
            edittext_enteramount.setHint(languageResponseData.getPlease_attach_file());
        }
    }

    private void pickImage() {
        if (receipt_list.size() <= 5) {
            CropImage.activity()
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .start(this);
        } else {
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
        if (bankBean.getStatus().equals("true")) {
            Intent i = new Intent(FifthBookActivity.this, SixthBookActivity.class);
            // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Toast.makeText(this, "" + bankBean.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "" + bankBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void showDialog(String bank_name, String account_number) {
        dialog = new Dialog(FifthBookActivity.this);
//        dialog = new Dialog(new ContextThemeWrapper(this, R.style.DialogSlideAnim));
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_receipt2);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_Final;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv_header = dialog.findViewById(R.id.textView_title);
        tv_header.setText(languageResponseData.getYour_order());
        final FoldingCell fc = (FoldingCell) dialog.findViewById(R.id.folding_cell);
        fc.initialize(2000, getResources().getColor(R.color.colorPrimary), 10);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fc.toggle(false);
            }
        }, 400);

        RecyclerView dialog_book_recyclerview = dialog.findViewById(R.id.dialog_book_recyclerview);
        dialog_book_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<BookBean> bookArrayList = SuperLifeSecretPreferences.getInstance().getSelectedBooksList();

        double disocunt = 0;
        for (int ii = 0 ; ii < bookArrayList.size() ; ii++){
            disocunt = disocunt + bookArrayList.get(ii).getDiscount_applied();
        }

        DialogBookAapter bookAapterDetail = new DialogBookAapter(bookArrayList, FifthBookActivity.this);
        dialog_book_recyclerview.setAdapter(bookAapterDetail);

        TextView book_type, order_for, delivery_type, delivery_address;
        TextView user_name, user_country, tv_receipt;
        TextView grant_total;
        TextView tv_order_date, tv_order_detail_dialog;
        TextView tv_book_type, tv_order_for, tv_delivery_type, tv_delivery_address, tv_user_name, tv_user_country;
        TextView tv_delivery, tv_grand_total, tv_paymentdetail, textView_title;

        TextView bankname, accountno, payment_mode, payment_date_dialog, tv_subtotal, tv_deliverycharges;
        TextView tv_bankname, tv_accountno, tv_payment_mode, tv_payment_date_dialog, subtotal, deliverycharges;
        LinearLayout address_linear_dialog, linear_other_person_dialog;
        TextView tv_are_you_sure, tv_yes, tv_no, tv_other_person_name_dialog, other_person_name_dialog;
        TextView tv_other_person_mobile_dialog, other_person_mobile_dialog;
        ImageView dialog_back_image;
        TextView tv_order_type_dialog, order_type_dialog;
        TextView tv_grand_total_first , grand_total_first;
        TextView tv_dialog_email , dialog_email , tv_dialog_mobile , dialog_mobile , grand_total_first_cut;

        tv_other_person_name_dialog = dialog.findViewById(R.id.tv_other_person_name_dialog);
        other_person_name_dialog = dialog.findViewById(R.id.other_person_name_dialog);
        tv_other_person_mobile_dialog = dialog.findViewById(R.id.tv_other_person_mobile_dialog);
        other_person_mobile_dialog = dialog.findViewById(R.id.other_person_mobile_dialog);
        address_linear_dialog = dialog.findViewById(R.id.address_linear_dialog);
        tv_book_type = dialog.findViewById(R.id.tv_book_type);
        tv_order_for = dialog.findViewById(R.id.tv_order_for);
        tv_delivery_type = dialog.findViewById(R.id.tv_delivery_type);
        tv_delivery_address = dialog.findViewById(R.id.tv_delivery_address);
        tv_user_name = dialog.findViewById(R.id.tv_user_name);
        tv_user_country = dialog.findViewById(R.id.tv_user_country);
        tv_order_date = dialog.findViewById(R.id.tv_order_date);
        tv_paymentdetail = dialog.findViewById(R.id.tv_paymentdetail);
        tv_subtotal = dialog.findViewById(R.id.tv_subtotal);
        tv_delivery = dialog.findViewById(R.id.tv_delivery);
        tv_grand_total = dialog.findViewById(R.id.tv_grand_total);
        tv_receipt = dialog.findViewById(R.id.tv_receipt);
        payment_date_dialog = dialog.findViewById(R.id.payment_date_dialog);
        tv_payment_date_dialog = dialog.findViewById(R.id.tv_payment_date_dialog);
        subtotal = dialog.findViewById(R.id.subtotal);
        tv_are_you_sure = dialog.findViewById(R.id.tv_are_you_sure);
        tv_yes = dialog.findViewById(R.id.tv_yes);
        tv_no = dialog.findViewById(R.id.tv_no);
        book_type = dialog.findViewById(R.id.book_type);
        order_for = dialog.findViewById(R.id.order_for);
        delivery_type = dialog.findViewById(R.id.delivery_type);
        delivery_address = dialog.findViewById(R.id.delivery_address);
        user_name = dialog.findViewById(R.id.user_name);
        user_country = dialog.findViewById(R.id.user_country);
        grant_total = dialog.findViewById(R.id.grant_total);
        tv_order_detail_dialog = dialog.findViewById(R.id.tv_order_detail_dialog);
        RecyclerView dialog_receipt_recyclerview = dialog.findViewById(R.id.dialog_receipt_recyclerview);
        linear_other_person_dialog = dialog.findViewById(R.id.linear_other_person_dialog);
        tv_order_type_dialog = dialog.findViewById(R.id.tv_order_type_dialog);
        order_type_dialog = dialog.findViewById(R.id.order_type_dialog);
        tv_dialog_email = dialog.findViewById(R.id.tv_dialog_email);
        dialog_email = dialog.findViewById(R.id.dialog_email);
        tv_dialog_mobile = dialog.findViewById(R.id.tv_dialog_mobile);
        dialog_mobile = dialog.findViewById(R.id.dialog_mobile);
        grand_total_first_cut = dialog.findViewById(R.id.grand_total_first_cut);

        bankname = dialog.findViewById(R.id.bankname);
        tv_bankname = dialog.findViewById(R.id.tv_bank_name);
        tv_accountno = dialog.findViewById(R.id.tv_accountno);
        accountno = dialog.findViewById(R.id.accountno);
        payment_mode = dialog.findViewById(R.id.paymentmode);
        tv_payment_mode = dialog.findViewById(R.id.tv_paymentmode);
        dialog_back_image = dialog.findViewById(R.id.back_image);
        tv_deliverycharges = dialog.findViewById(R.id.tv_deliverycharges);
        deliverycharges = dialog.findViewById(R.id.deliverycharges);
        tv_grand_total_first = dialog.findViewById(R.id.tv_grand_total_first);
        grand_total_first = dialog.findViewById(R.id.grand_total_first);

        tv_are_you_sure.setText(languageResponseData.getAre_you_sure_continue());
        tv_bankname.setText(languageResponseData.getBank_name());
        tv_accountno.setText(languageResponseData.getAccount_number());
        tv_order_detail_dialog.setText(languageResponseData.getOrder_detail());
        tv_book_type.setText(languageResponseData.getBook_type());
        tv_order_for.setText(languageResponseData.getOrder_for());
        tv_payment_mode.setText(languageResponseData.getPayment_mode());
        tv_delivery_type.setText(languageResponseData.getDelivery_type());
        tv_user_name.setText(languageResponseData.getUsername());
        tv_user_country.setText(languageResponseData.getUser_country());
        tv_receipt.setText(languageResponseData.getReceipts());
        tv_grand_total.setText(languageResponseData.getGrandtotal());
        tv_paymentdetail.setText(languageResponseData.getPayment_detail());
        //order_detail.setText(languageResponseData.getOrder_detail());
        tv_payment_date_dialog.setText(languageResponseData.getPayment_date());
        tv_deliverycharges.setText(languageResponseData.getDelivery_charges());
        tv_subtotal.setText(languageResponseData.getSubtotal());
        tv_order_date.setText(languageResponseData.getOrder_date());
        tv_other_person_name_dialog.setText(languageResponseData.getOther_person());
        tv_other_person_mobile_dialog.setText(languageResponseData.getOther_mobile());
        tv_grand_total_first.setText(languageResponseData.getTotal());
        tv_dialog_email.setText(languageResponseData.getEmail_id());
        tv_dialog_mobile.setText(languageResponseData.getContact_number());
        if (SuperLifeSecretPreferences.getInstance().getString("final_dialog_email").equals("")) {
            tv_dialog_email.setVisibility(View.GONE);
            tv_dialog_mobile.setVisibility(View.GONE);
            dialog_email.setVisibility(View.GONE);
            dialog_mobile.setVisibility(View.GONE);
        }else {
            tv_dialog_email.setVisibility(View.VISIBLE);
            tv_dialog_mobile.setVisibility(View.VISIBLE);
            dialog_email.setVisibility(View.VISIBLE);
            dialog_mobile.setVisibility(View.VISIBLE);
        }
        dialog_email.setText(SuperLifeSecretPreferences.getInstance().getString("final_dialog_email"));
        dialog_mobile.setText(SuperLifeSecretPreferences.getInstance().getString("final_dialog_phone"));

        bankname.setText(bank_name);
        accountno.setText(account_number);
        payment_mode.setText(payment_type_string);
        payment_date_dialog.setText(payment_date_string);
        tv_yes.setText(languageResponseData.getYes());
        tv_no.setText(languageResponseData.getNo());
        tv_order_type_dialog.setText(languageResponseData.getOrder_type());

        if (SuperLifeSecretPreferences.getInstance().getString("book_order_for").equals("2")) {
            linear_other_person_dialog.setVisibility(View.VISIBLE);
        } else {
            linear_other_person_dialog.setVisibility(View.GONE);
        }
        if (!SuperLifeSecretPreferences.getInstance().getString("other_person_name").equals("")) {
            linear_other_person_dialog.setVisibility(View.VISIBLE);
        } else {
            linear_other_person_dialog.setVisibility(View.GONE);
        }
        other_person_name_dialog.setText("" + SuperLifeSecretPreferences.getInstance().getString("other_person_name"));
        other_person_mobile_dialog.setText("" + SuperLifeSecretPreferences.getInstance().getString("other_person_contact"));
        if (SuperLifeSecretPreferences.getInstance().getString("book_type").equals("1")) {
            book_type.setText(languageResponseData.getBuying());
        } else {
            book_type.setText(languageResponseData.getPrinting());
        }
        if (SuperLifeSecretPreferences.getInstance().getString("book_order_for").equals("1")) {
            order_for.setText(languageResponseData.getOwn());
        } else {
            order_for.setText(languageResponseData.getBehalf());
        }
        if (SuperLifeSecretPreferences.getInstance().getString("book_designated_type").equals("1")) {
            order_type_dialog.setText(languageResponseData.getDesignated());
        } else {
            order_type_dialog.setText(languageResponseData.getDistribute());
        }
        delivery_type.setText("" + SuperLifeSecretPreferences.getInstance().getString("delivery_type_text"));
        user_name.setText(SuperLifeSecretPreferences.getInstance().getString("book_full_name"));

        UserDetailResponseData userDetailResponseData = SuperLifeSecretPreferences.getInstance().getUserData();
        user_country.setText(userDetailResponseData.getCountryName());

        if (SuperLifeSecretPreferences.getInstance().getString("book_designated_type").equals("2"))
            address_linear_dialog.setVisibility(View.GONE);
        else {
            address_linear_dialog.setVisibility(View.VISIBLE);
            delivery_address.setText(SuperLifeSecretPreferences.getInstance().getString("final_dialog_address"));
        }
        String totalamount = SuperLifeSecretPreferences.getInstance().getString("total_amount");
        double dountl_total_amount = Double.parseDouble(totalamount);
//        ArrayList<DeliveryData> deliveryDataArrayList = SuperLifeSecretPreferences.getInstance().getDeliveryChargesList();
//        for (int i = 0; i < deliveryDataArrayList.size(); i++) {
//            double from = Double.parseDouble(deliveryDataArrayList.get(i).getRange_from());
//            double to = Double.parseDouble(deliveryDataArrayList.get(i).getRange_to());
//            if (dountl_total_amount >= from && dountl_total_amount <= to) {
//                delivery_charges = Double.parseDouble(deliveryDataArrayList.get(i).getDelivery_charge());
//            } else if (dountl_total_amount >= to) {
//                delivery_charges = Double.parseDouble(deliveryDataArrayList.get(i).getDelivery_charge());
//            }
//        }
        delivery_charges = Double.parseDouble(SuperLifeSecretPreferences.getInstance().getString("delivery_charges"));
        try {
            deliverycharges.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", delivery_charges));
        } catch (Exception e) {
            deliverycharges.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + delivery_charges);
        }
        double grandtotal = delivery_charges + dountl_total_amount;
        try {
            grant_total.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", grandtotal));
        } catch (Exception e) {
            grant_total.setText(" " + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + grandtotal);
        }

        ArrayList<BookBean> selectBookList = SuperLifeSecretPreferences.getInstance().getSelectedBooksList();
        double total_cut_amount = 0.0;
        double discount_cut = 0.0;
        for (int i = 0 ; i < selectBookList.size() ; i++){
            total_cut_amount = total_cut_amount + (selectBookList.get(i).getPrice()*selectBookList.get(i).getQuantity());
            discount_cut = discount_cut + selectBookList.get(i).getDiscount_applied();
        }
        if (discount_cut==0.0)
            grand_total_first_cut.setVisibility(View.GONE);
        else grand_total_first_cut.setVisibility(View.VISIBLE);
        grand_total_first_cut.setPaintFlags(grand_total_first_cut.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        grand_total_first_cut.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", total_cut_amount));
        try {
            //tv_grand_total_first.setText(""+(dountl_total_amount-disocunt));
            grand_total_first.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", dountl_total_amount));
            subtotal.setText("" + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + String.format(Locale.getDefault(), "%,.2f", dountl_total_amount));
        } catch (Exception e) {
            grand_total_first.setText(" " + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + dountl_total_amount);
            subtotal.setText(" " + SuperLifeSecretPreferences.getInstance().getString("book_currency") + " " + dountl_total_amount);
        }
        dialog_receipt_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        ReceiptAapter receiptAapter2 = new ReceiptAapter(receipt_list, this, new ReceiptListener(), false);
        dialog_receipt_recyclerview.setAdapter(receiptAapter2);
        dialog_receipt_recyclerview.setClickable(false);

        dialog_back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                orderWebservice();
            }
        });
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
