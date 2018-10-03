package com.superlifesecretcode.app.ui.kpi_summery;

import java.util.ArrayList;

public class CountryActivity {

    ArrayList<StudySharingBean> study_groups;
    ArrayList<StudySharingBean> on_site_sharing;

    public CountryActivity(ArrayList<StudySharingBean> study_groups, ArrayList<StudySharingBean> on_site_sharing) {
        this.study_groups = study_groups;
        this.on_site_sharing = on_site_sharing;
    }

    public ArrayList<StudySharingBean> getStudy_groups() {
        return study_groups;
    }

    public ArrayList<StudySharingBean> getOn_site_sharing() {
        return on_site_sharing;
    }
}
