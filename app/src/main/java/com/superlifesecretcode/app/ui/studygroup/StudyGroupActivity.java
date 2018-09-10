package com.superlifesecretcode.app.ui.studygroup;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.model.studygroups.StudyGroupData;
import com.superlifesecretcode.app.data.model.studygroups.StudyGroupDetails;
import com.superlifesecretcode.app.data.model.userdetails.UserDetailResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;
import com.superlifesecretcode.app.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyGroupActivity extends BaseActivity implements StudyGroupView {


    private LanguageResponseData conversionData;
    private UserDetailResponseData userData;
    private RecyclerView recyclerView;
    private List<StudyGroupDetails> list;
    private StudyGroupListAdapter adapter;
    private TabLayout tabLayout;
    private StudyGroupPresenter presenter;
    private List<StudyGroupDetails> subScribeGroupList;
    private List<StudyGroupDetails> newGroupList;

    @Override
    protected int getContentView() {
        return R.layout.activity_study_group;
    }

    @Override
    protected void initializeView() {
        conversionData = SuperLifeSecretPreferences.getInstance().getConversionData();
        userData = SuperLifeSecretPreferences.getInstance().getUserData();
        setUpToolbar();
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.getTabAt(0).setText("New");
        tabLayout.getTabAt(1).setText("My Subscription");
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudyGroupListAdapter(list,this);
        recyclerView.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(listener);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + userData.getApi_token());
        presenter.getStudyGroup(headers);
    }

    @Override
    protected void initializePresenter() {
        presenter = new StudyGroupPresenter(this);
        presenter.setView(this);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView textViewTitle = findViewById(R.id.textView_title);
        if (conversionData != null)
            textViewTitle.setText(conversionData.getStudy_group());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    list.clear();
                    if (newGroupList != null) {
                        list.addAll(newGroupList);
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    list.clear();
                    if (subScribeGroupList != null) {
                        list.addAll(subScribeGroupList);

                    }
                    adapter.notifyDataSetChanged();
                    break;

            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    public void setStudyGroupList(StudyGroupData data) {
        if (data != null) {
            newGroupList = data.getNew_groups();
            subScribeGroupList = data.getSubcribed_groups();
            list.clear();
            if (tabLayout.getSelectedTabPosition() == 0 && newGroupList != null) {
                list.addAll(newGroupList);
            } else if (subScribeGroupList != null) {
                list.addAll(subScribeGroupList);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
