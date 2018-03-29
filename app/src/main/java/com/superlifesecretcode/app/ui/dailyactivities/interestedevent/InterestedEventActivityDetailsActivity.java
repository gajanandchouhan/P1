package com.superlifesecretcode.app.ui.dailyactivities.interestedevent;

import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.interesetdevent.InterestedEventdata;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.news.NewsResponseData;
import com.superlifesecretcode.app.data.model.news.SingleNewsResponseModel;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.events.EventDetailsActivity;
import com.superlifesecretcode.app.ui.sharing_submit.ShareListView;
import com.superlifesecretcode.app.util.CommonUtils;
import com.superlifesecretcode.app.util.ConstantLib;
import com.superlifesecretcode.app.util.ImageLoadUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterestedEventActivityDetailsActivity extends BaseActivity implements View.OnClickListener, InterestedEventView {


    private UserDetailResponseData userData;
    private LanguageResponseData conversionData;
    private InterestedEventPresenter presenter;
    private String id;
    ImageView imageViewShare, imageView;
    RelativeLayout relativeLayout;
    private TextView textViewTitle1, textViewTime, textViewInterested,
            textViewAddr, textViewDesc;

    @Override
    protected int getContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        return R.layout.activity_interetesed_event_details;
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    protected void initializeView() {
        imageView = findViewById(R.id.imageView);
        relativeLayout = findViewById(R.id.button_interested);
        textViewTitle1 = findViewById(R.id.textView_title_event);
        textViewTime = findViewById(R.id.textView_time);
        textViewInterested = findViewById(R.id.textview_interested);
        textViewAddr = findViewById(R.id.textView_addr);
        imageViewShare = findViewById(R.id.imageView_share);
        textViewDesc = findViewById(R.id.textView_desc);
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        id = getIntent().getBundleExtra("bundle").getString("id");
        setUpToolbar();
        getDetails();
    }

    private void getDetails() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("announcement_id", id);
        presenter.getDetails(params, headers);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        textViewTitle.setText(conversionData != null ? conversionData.getEvent_activity() : "Event+Activities");
    }

    @Override
    protected void initializePresenter() {
        presenter = new InterestedEventPresenter(this);
        presenter.setView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }


    @Override
    public void setEventData(List<InterestedEventdata> interestedEventResponseDataList) {

    }

    @Override
    public void setDetails(SingleNewsResponseModel newsResponseModel) {
        if (newsResponseModel != null) {
            setUpData(newsResponseModel.getData());
        }
    }

    @Override
    public void onUpdateInteresed() {
        InterestedEventCalendarActivity.UPDATED = true;
        onBackPressed();
    }

    private void setUpData(final NewsResponseData newsResponseModel) {
        if (newsResponseModel != null) {
            textViewTitle1.setText(newsResponseModel.getAnnouncement_name());
            textViewTime.setText(CommonUtils.getformattedDateFromString(ConstantLib.INPUT_DATE_TIME_FORMATE, ConstantLib.OUTPUT_DATE_TIME_FORMATE, newsResponseModel.getAnnouncement_date() + " " + newsResponseModel.getAnnouncement_time()));
            ImageLoadUtils.loadImage(newsResponseModel.getImage(), imageView);
            textViewAddr.setText(newsResponseModel.getVenue());
            textViewInterested.setText(conversionData.getInterested());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Spanned spanned = Html.fromHtml(newsResponseModel.getAnnouncement_description(), Html.FROM_HTML_MODE_LEGACY);
                textViewDesc.setText(spanned);
            } else {
                Spanned spanned = Html.fromHtml(newsResponseModel.getAnnouncement_description());
                textViewDesc.setText(spanned);
            }
            relativeLayout.setSelected(newsResponseModel.getUserIntrested() != null && newsResponseModel.getUserIntrested().equalsIgnoreCase("1"));
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userIntrested = newsResponseModel.getUserIntrested();
                    if (userIntrested != null && userIntrested.equalsIgnoreCase("1")) {
                        updateEventInterest("0", newsResponseModel.getAnnouncement_id());
                    } else {
                        updateEventInterest("1", newsResponseModel.getAnnouncement_id());
                    }
                }
            });

            imageViewShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommonUtils.shareContent(InterestedEventActivityDetailsActivity.this, Html.fromHtml(newsResponseModel.getAnnouncement_description()).toString());
                }
            });
        }
    }

    public void updateEventInterest(String interested, String id) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        HashMap<String, String> params = new HashMap<>();
        params.put("announcement_id", id);
        params.put("interest", interested);
        params.put("user_id", userData.getUser_id());
        presenter.makeInterested(params, headers);
    }
}
