package com.superlifesecretcode.app.ui.kpi_summery;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;
import com.superlifesecretcode.app.ui.dailyactivities.personalevent.PersonalEventCalendarActivity;
import com.superlifesecretcode.app.ui.events.EventActivity;
import com.superlifesecretcode.app.ui.news.NewsActivity;
import com.superlifesecretcode.app.ui.sharing_submit.SubmitActivity;
import com.superlifesecretcode.app.ui.sharing_submit.SubmitListActivity;
import com.superlifesecretcode.app.util.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KpiActivity extends BaseActivity implements KpiView {

    KpiPresenter kpiPresenter;
    private UserDetailResponseData userData;
    TextView tv_totalpoint, tv_totalpoint_count;
    TextView tv_myannouncement, tv_sharing_haeder;
    TextView tv_news, tv_unreadnews_header, tv_unreadnewscount;
    TextView tv_event, tv_unattented_header, tv_unattented_header_count, tv_postcount;
    TextView tv_dialy_activity, tv_done, tv_done_count, tv_activity_header, tv_activity_header_count;
    TextView tv_sharing, tv_study_group, tv_onsite_sharing, tv_activity, tv_your_sharing;
    RecyclerView recyclerview_onsitesharing, recyclerview_stydy_group;
    LanguageResponseData languageResponseData;
    SummeryAapter summeryAapter;
    SummeryAapter summeryAapter2;
    ArrayList<StudySharingBean> studylist;
    ArrayList<StudySharingBean> onSiteSharing;
    RelativeLayout relative_news, relative_event, raletive_dailyactivity, relative_sharing;
    ImageView iv_sharing , back_image;
    String send;
    @Override
    protected int getContentView() {
        return R.layout.activity_kpi;
    }

    @Override
    protected void initializeView() {
        languageResponseData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        relative_news = findViewById(R.id.relative_news);
        relative_event = findViewById(R.id.relative_event);
        raletive_dailyactivity = findViewById(R.id.raletive_dailyactivity);
        relative_sharing = findViewById(R.id.relative_sharing);
        tv_totalpoint = findViewById(R.id.tv_totalpoint);
        tv_totalpoint_count = findViewById(R.id.tv_totalpoint_count);
        tv_myannouncement = findViewById(R.id.tv_myannouncement);
        tv_news = findViewById(R.id.tv_news);
        tv_activity = findViewById(R.id.tv_activity);
        tv_unreadnews_header = findViewById(R.id.tv_unreadnews_header);
        tv_unreadnewscount = findViewById(R.id.tv_unreadnewscount);
        tv_event = findViewById(R.id.tv_event);
        tv_postcount = findViewById(R.id.tv_postcount);

        tv_unattented_header = findViewById(R.id.tv_unattented_header);
        tv_unattented_header_count = findViewById(R.id.tv_unattented_header_count);
        recyclerview_onsitesharing = findViewById(R.id.recyclerview_onsitesharing);
        recyclerview_stydy_group = findViewById(R.id.recyclerview_stydy_group);
        tv_dialy_activity = findViewById(R.id.tv_dialy_activity);
        tv_done = findViewById(R.id.tv_done);
        tv_done_count = findViewById(R.id.tv_done_count);
        tv_activity_header = findViewById(R.id.tv_activity_header);
        tv_activity_header_count = findViewById(R.id.tv_activity_header_count);
        tv_sharing = findViewById(R.id.tv_sharing);
        tv_study_group = findViewById(R.id.tv_study_group);
        tv_onsite_sharing = findViewById(R.id.tv_onsite_sharing);
        tv_sharing_haeder = findViewById(R.id.tv_sharing_haeder);
        tv_your_sharing = findViewById(R.id.tv_your_sharing);
        iv_sharing = findViewById(R.id.iv_sharing);
        back_image = findViewById(R.id.back_image);
        tv_totalpoint.setText(languageResponseData.getTotal_points());
        tv_myannouncement.setText(languageResponseData.getAnnouncement());
        tv_event.setText(languageResponseData.getEvents());
        tv_news.setText(languageResponseData.getNews());
        tv_unreadnews_header.setText(languageResponseData.getUnread_news() + " :");
        tv_unattented_header.setText(languageResponseData.getUnread_events() + " :");
        tv_dialy_activity.setText(languageResponseData.getDaily_activities());
        tv_activity.setText(languageResponseData.getActivity());
        tv_done.setText(languageResponseData.getDone() + ":");
        tv_activity_header.setText(languageResponseData.getPending() + " :");
        tv_sharing.setText(languageResponseData.getSharing());
        tv_onsite_sharing.setText(languageResponseData.getOnsite());
        tv_sharing_haeder.setText(languageResponseData.getTotal_post() + ":");
        tv_your_sharing.setText(languageResponseData.getYour_sharing_liked());
        tv_study_group.setText(languageResponseData.getStudy_group());

        studylist = new ArrayList<>();
        onSiteSharing = new ArrayList<>();
        summeryAapter = new SummeryAapter(studylist, this);
        summeryAapter2 = new SummeryAapter(onSiteSharing, this);
        recyclerview_stydy_group.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_onsitesharing.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_stydy_group.setAdapter(summeryAapter);
        recyclerview_onsitesharing.setAdapter(summeryAapter2);

        relative_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startActivity(KpiActivity.this, NewsActivity.class);
            }
        });
        relative_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startActivity(KpiActivity.this, EventActivity.class);
            }
        });
        raletive_dailyactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startActivity(KpiActivity.this, PersonalEventCalendarActivity.class);
            }
        });
        relative_sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startActivity(KpiActivity.this, SubmitListActivity.class);
            }
        });
        iv_sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        send);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initializePresenter() {
        kpiPresenter = new KpiPresenter(this);
        kpiPresenter.setView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + userData.getApi_token());
        kpiPresenter.getKpiSummeryData(header);
    }

    @Override
    public void getKpiSummeryData(KPI kpi) {

        if (kpi.getData().getCountry_activities().getStudy_groups().size()==0){
            tv_study_group.setVisibility(View.GONE);
            recyclerview_stydy_group.setVisibility(View.GONE);
        }else {
            tv_study_group.setVisibility(View.VISIBLE);
            recyclerview_stydy_group.setVisibility(View.VISIBLE);
        }

        if (kpi.getData().getCountry_activities().getOn_site_sharing().size()==0){
            tv_onsite_sharing.setVisibility(View.GONE);
            recyclerview_onsitesharing.setVisibility(View.GONE);
        }else {
            tv_onsite_sharing.setVisibility(View.VISIBLE);
            recyclerview_onsitesharing.setVisibility(View.VISIBLE);
        }

        studylist.clear();
        studylist.addAll(kpi.getData().getCountry_activities().getStudy_groups());
        summeryAapter.notifyDataSetChanged();

        onSiteSharing.clear();
        onSiteSharing.addAll(kpi.getData().getCountry_activities().getOn_site_sharing());
        summeryAapter2.notifyDataSetChanged();

        send = "I have earned "+ kpi.getData().getDaily_activities().getPoints()+" ";

        tv_totalpoint_count.setText(kpi.getData().getDaily_activities().getPoints());
        tv_done_count.setText(kpi.getData().getDaily_activities().getCompleted());
        String stringUnwantedcount = "<font color=#D3382B>" + kpi.getData().getDaily_activities().getIncompleted() + "</font>" + " <font color=#000000>" + languageResponseData.getMore_to_go() + " </font>";
        tv_activity_header_count.setText(Html.fromHtml(stringUnwantedcount));
        tv_totalpoint_count.setText(kpi.getData().getDaily_activities().getPoints());
        tv_unreadnewscount.setText(kpi.getData().getAnnouncements().getUnreadNews());
        tv_postcount.setText(kpi.getData().getSharings().getTotal_sharings());
        tv_unattented_header_count.setText(kpi.getData().getAnnouncements().getUnreadEvents());
        String your_liked = "<font color=#000000>" + languageResponseData.getYour_sharing_liked() + " </font> " + " <font color=#D33828>" + kpi.getData().getSharings().getTotal_sharings_liked() + " </font>"
                + "<font color=#000000>" + languageResponseData.getTimes();
        //<font color=#D3382B>abc </font>
        tv_your_sharing.setText(Html.fromHtml(your_liked));
        //tv_sharing.setText(""+ CommonUtils.getColorfulText(this,"abc", Color.valueOf(R.color.red)));
    }
}
