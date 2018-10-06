package com.superlifesecretcode.app.ui.studygroup.paymentproof;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.five.Bank;
import com.superlifesecretcode.app.ui.book.five.BankBean;
import com.superlifesecretcode.app.ui.book.five.DialogBookAapter;
import com.superlifesecretcode.app.ui.book.five.FifthBookActivity;
import com.superlifesecretcode.app.ui.book.five.FifthBookPresenter;
import com.superlifesecretcode.app.ui.book.five.Receipt;
import com.superlifesecretcode.app.ui.book.five.ReceiptAapter;
import com.superlifesecretcode.app.ui.book.second.DeliveryData;
import com.superlifesecretcode.app.ui.picker.DropDownWindow;
import com.superlifesecretcode.app.ui.studygroup.ThanksActivity;
import com.superlifesecretcode.app.util.CommonUtils;
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
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.book.first.BookBean;
import com.superlifesecretcode.app.ui.book.five.Bank;
import com.superlifesecretcode.app.ui.book.five.BankBean;
import com.superlifesecretcode.app.ui.book.five.DialogBookAapter;
import com.superlifesecretcode.app.ui.book.five.FifthBookActivity;
import com.superlifesecretcode.app.ui.book.five.FifthBookPresenter;
import com.superlifesecretcode.app.ui.book.five.Receipt;
import com.superlifesecretcode.app.ui.book.five.ReceiptAapter;
import com.superlifesecretcode.app.ui.book.second.DeliveryData;
import com.superlifesecretcode.app.ui.picker.DropDownWindow;
import com.superlifesecretcode.app.ui.studygroup.ThanksActivity;
import com.superlifesecretcode.app.util.CommonUtils;
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

public class PaymentProofActivity extends BaseActivity implements PaymentProofView {


    private LanguageResponseData conversionData;
    private UserDetailResponseData userData;
    //private String planId;
    TextView textview_payment, textview_attachmentline;
    EditText edittext_enteramount;
    RecyclerView rechcyclerview_bankslist;
    PaymentProfPresenter paymentProfPresenter;
    StudyGroupBankAapter bankAapter;
    ArrayList<Bank> bankArrayList;
    TextView textview_total, textview_total_price, textview_back, textview_next;
    RecyclerView receipt_recyclerview;
    public ArrayList<Receipt> receipt_list;
    StudyGroupReceiptAapter receiptAapter;
    Dialog dialog;
    TextView textview_plus, tv_payment_date, tv_payment_type;
    ArrayList<String> payment_type_list;
    String payment_type = "", payment_date = "", payment_type_string = "", payment_date_string = "";
    String study_group_id = "" , plan_type = "";
    TextView payment_date_heading, payment_type_heading;
    private String planCost;
    private String groupName;
    private String currency;
    private String planTitle;

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_proof;
    }

    @Override
    protected void initializeView() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
       // planId = getIntent().getStringExtra("plan_id");
        planCost = getIntent().getStringExtra("plan_cost");
        groupName = getIntent().getStringExtra("group_name");
        currency = getIntent().getStringExtra("plan_currency");
        planTitle = getIntent().getStringExtra("plan_title");
        study_group_id = getIntent().getStringExtra("study_group_id");
        plan_type = getIntent().getStringExtra("plan_type");
        setUpToolbar();
        bankArrayList = new ArrayList<>();
        receipt_list = new ArrayList<>();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        textview_payment = findViewById(R.id.textview_payment);
        textview_attachmentline = findViewById(R.id.textview_attachmentline);
        edittext_enteramount = findViewById(R.id.edittext_enteramount);
        rechcyclerview_bankslist = findViewById(R.id.rechcyclerview_bankslist);
        textview_total = findViewById(R.id.textview_total);
        textview_total_price = findViewById(R.id.textview_total_price);
        payment_date_heading = findViewById(R.id.payment_date_heading);
        payment_type_heading = findViewById(R.id.payment_type_heading);
        textview_back = findViewById(R.id.textview_back);
        textview_next = findViewById(R.id.textview_next);
        // imageview_receipt = findViewById(R.id.imageview_receipt);
        receipt_recyclerview = findViewById(R.id.receipt_recyclerview);
        textview_plus = findViewById(R.id.textview_plus);
        tv_payment_date = findViewById(R.id.tv_payment_date);
        tv_payment_type = findViewById(R.id.tv_payment_type);
        textview_total_price.setText(String.format("%s %s", currency, planCost));
        payment_type_list = new ArrayList<>();
        payment_type_list.add(conversionData.getBank_transfer());
        payment_type_list.add(conversionData.getCash_deposit());
        payment_type_list.add(conversionData.getCheque_deposit());

        rechcyclerview_bankslist.setLayoutManager(new LinearLayoutManager(this));
        bankAapter = new StudyGroupBankAapter(bankArrayList, this, new BankListener());
        rechcyclerview_bankslist.setAdapter(bankAapter);

        receipt_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        receiptAapter = new StudyGroupReceiptAapter(receipt_list, this, new ReceiptListener(), true);
        receipt_recyclerview.setAdapter(receiptAapter);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        paymentProfPresenter.getBankList(headers);


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
                    CommonUtils.showSnakeBar(PaymentProofActivity.this, conversionData.getPlease_select_pay_mode());
                    return;
                }

                if (payment_date.equals("")) {
                    CommonUtils.showSnakeBar(PaymentProofActivity.this, conversionData.getPlease_select_pay_date());
                    return;
                }

                if (getBankId() == null || getBankId().isEmpty()) {
                    CommonUtils.showSnakeBar(PaymentProofActivity.this, conversionData.getPlease_select_bank());
                    return;
                }
                if (receipt_list == null || receipt_list.size() == 0) {
                    CommonUtils.showSnakeBar(PaymentProofActivity.this, conversionData.getPlease_add_receipts());
                    return;
                }
                showDialog(bank_name, account_number);
            }
        });

        edittext_enteramount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.hasPermissions(PaymentProofActivity.this, PermissionConstant.PERMISSION_PROFILE)) {
                    pickImage();
                } else {
                    ActivityCompat.requestPermissions(PaymentProofActivity.this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
                }
            }
        });

        textview_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.hasPermissions(PaymentProofActivity.this, PermissionConstant.PERMISSION_PROFILE)) {
                    pickImage();
                } else {
                    ActivityCompat.requestPermissions(PaymentProofActivity.this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
                }
            }
        });
        tv_payment_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentModeSelection();
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


    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(conversionData.getPayment_detail());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPaymentModeSelection() {
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

        builder.addFormDataPart("payment_date", payment_date);
        builder.addFormDataPart("payment_mode", payment_type);
        //builder.addFormDataPart("plan_id", planId);
        builder.addFormDataPart("bank_id", getBankId());
        builder.addFormDataPart("study_group_id", study_group_id);
        builder.addFormDataPart("plan_type", plan_type);
        for (int i = 0; i < receipt_list.size(); i++) {
            try {
                if (receipt_list.get(i) != null) {
                    File file = new File(receipt_list.get(i).getReceipt_image_path());
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    builder.addFormDataPart("payment_receipt[]", file.getName(), requestBody);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        RequestBody finalRequestBody = builder.build();
        paymentProfPresenter.subscribePlan(finalRequestBody, headers);
    }

    private void setUpConversion() {

        if (conversionData != null) {
            textview_back.setText(conversionData.getBack());
            textview_next.setText(conversionData.getContinuee());
            textview_attachmentline.setText(conversionData.getAttached_file());
            textview_total.setText(conversionData.getTotal());
            payment_type_heading.setText(conversionData.getPayment_mode());
            payment_date_heading.setText(conversionData.getPayment_date());
            tv_payment_type.setText(conversionData.getSelect());
            tv_payment_date.setText(conversionData.getSelect());
            textview_payment.setText(String.format("%s %s %s %s", conversionData.getPlease_pay(), currency, planCost, conversionData.getOne_following_account()));
            edittext_enteramount.setHint(conversionData.getPlease_attach_file());
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

    @Override
    public void getBanks(BankBean bankBean) {
        bankArrayList.clear();
        bankArrayList.addAll(bankBean.getBankList());
        bankAapter.notifyDataSetChanged();
    }

    @Override
    public void onSubscribed() {
        finish();
        CommonUtils.startActivity(this, ThanksActivity.class);
    }

    public class ReceiptListener {
        public void onImageSelect() {
            if (CommonUtils.hasPermissions(PaymentProofActivity.this, PermissionConstant.PERMISSION_PROFILE)) {
                pickImage();
            } else {
                ActivityCompat.requestPermissions(PaymentProofActivity.this, PermissionConstant.PERMISSION_PROFILE, PermissionConstant.CODE_PROFILE);
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
        paymentProfPresenter = new PaymentProfPresenter(this);
        paymentProfPresenter.setView(this);
    }

  /*  @Override
    public void getBanks(BankBean bankBean) {
        bankArrayList.clear();
        bankArrayList.addAll(bankBean.getBankList());
        bankAapter.notifyDataSetChanged();
    }*/


    public void showDialog(String bank_name, String account_number) {
        dialog = new Dialog(PaymentProofActivity.this, R.style.DialogCustomTheme);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_subscription_confirm);
        dialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation_Final;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final FoldingCell fc = (FoldingCell) dialog.findViewById(R.id.folding_cell);
        fc.initialize(2000, getResources().getColor(R.color.colorPrimary), 5);
        TextView tv_header = dialog.findViewById(R.id.textView_title);
        tv_header.setText(conversionData.getSubscription_plans());

        TextView group_name, plan, cost, user_name;
        TextView email, mobile, tv_receipt;
        TextView grant_total;
        TextView tv_order_detail_dialog;
        TextView tv_group_name, tv_plan, tv_cost, tv_user_name, tv_email, tv_mobile_num;
        TextView tv_grand_total, tv_paymentdetail;

        TextView bankname, accountno, payment_mode, payment_date_dialog, tv_subtotal;
        TextView tv_bankname, tv_accountno, tv_payment_mode, tv_payment_date_dialog, subtotal;
        TextView tv_are_you_sure, tv_yes, tv_no;
        ImageView dialog_back_image;

        tv_group_name = dialog.findViewById(R.id.tv_group_name);
        tv_plan = dialog.findViewById(R.id.tv_plan);
        tv_cost = dialog.findViewById(R.id.tv_cost);
        tv_email = dialog.findViewById(R.id.tv_email);
        tv_user_name = dialog.findViewById(R.id.tv_user_name);
        tv_mobile_num = dialog.findViewById(R.id.tv_mobile);
        tv_paymentdetail = dialog.findViewById(R.id.tv_paymentdetail);
        tv_subtotal = dialog.findViewById(R.id.tv_subtotal);
        tv_grand_total = dialog.findViewById(R.id.tv_grand_total);
        tv_receipt = dialog.findViewById(R.id.tv_receipt);
        payment_date_dialog = dialog.findViewById(R.id.payment_date_dialog);
        tv_payment_date_dialog = dialog.findViewById(R.id.tv_payment_date_dialog);
        subtotal = dialog.findViewById(R.id.subtotal);
        tv_are_you_sure = dialog.findViewById(R.id.tv_are_you_sure);
        tv_yes = dialog.findViewById(R.id.tv_yes);
        tv_no = dialog.findViewById(R.id.tv_no);
        group_name = dialog.findViewById(R.id.group_name);
        plan = dialog.findViewById(R.id.plan);
        cost = dialog.findViewById(R.id.cost);
        email = dialog.findViewById(R.id.email);
        user_name = dialog.findViewById(R.id.user_name);
        mobile = dialog.findViewById(R.id.mobile);
        grant_total = dialog.findViewById(R.id.grant_total);
        tv_order_detail_dialog = dialog.findViewById(R.id.tv_order_detail_dialog);
        RecyclerView dialog_receipt_recyclerview = dialog.findViewById(R.id.dialog_receipt_recyclerview);

        bankname = dialog.findViewById(R.id.bankname);
        tv_bankname = dialog.findViewById(R.id.tv_bank_name);
        tv_accountno = dialog.findViewById(R.id.tv_accountno);
        accountno = dialog.findViewById(R.id.accountno);
        payment_mode = dialog.findViewById(R.id.paymentmode);
        tv_payment_mode = dialog.findViewById(R.id.tv_paymentmode);
        dialog_back_image = dialog.findViewById(R.id.back_image);

        tv_are_you_sure.setText(conversionData.getAre_you_sure_confirm_subscription());
        tv_bankname.setText(conversionData.getBank_name() + ":");
        tv_accountno.setText(conversionData.getAccount_number() + ":");
        tv_order_detail_dialog.setText(conversionData.getSubscription_detail() + ":");
        tv_group_name.setText(conversionData.getGroup_name()+":");
        tv_plan.setText(conversionData.getSubscription_plans()+":");
        tv_cost.setText(conversionData.getCost()+":");
        tv_payment_mode.setText(conversionData.getPayment_mode());
        tv_email.setText(conversionData.getEmail() + ":");
        tv_user_name.setText(conversionData.getUsername() + ":");
        tv_mobile_num.setText(conversionData.getMobile_no() + ":");
        tv_receipt.setText(conversionData.getReceipts() + ":");
        tv_grand_total.setText(conversionData.getGrandtotal() + ":");
        tv_paymentdetail.setText(conversionData.getPayment_detail() + ":");
        //order_detail.setText(conversionData.getOrder_detail());
        tv_payment_date_dialog.setText(conversionData.getPayment_date() + ":");
        tv_subtotal.setText(conversionData.getSubtotal() + ":");

        bankname.setText(bank_name);
        accountno.setText(account_number);
        payment_mode.setText(payment_type_string);
        payment_date_dialog.setText(payment_date_string);
        tv_yes.setText(conversionData.getYes());
        tv_no.setText(conversionData.getNo());

        group_name.setText(groupName);
        plan.setText(planTitle);
        cost.setText(currency + " " + planCost);
        user_name.setText(userData.getUsername());
        email.setText(userData.getEmail());
        mobile.setText(String.format("%s%s", userData.getPhone_code(), userData.getMobile()));

        grant_total.setText(currency + " " + planCost);
        dialog_receipt_recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        StudyGroupReceiptAapter receiptAapter2 = new StudyGroupReceiptAapter(receipt_list, this, new ReceiptListener(), false);
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
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fc.toggle(false);
            }
        }, 400);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }


    private String getBankId() {

        for (Bank bank : bankArrayList) {
            if (bank.isSelected()) {
                return bank.getId();
            }
        }
        return null;
    }
}
