package com.superlifesecretcode.app.data.model.studygroups;

import java.util.List;

public class StudyGroupData {
    List<StudyGroupDetails> new_groups;
    List<StudyGroupDetails> subcribed_groups;

    public List<StudyGroupDetails> getNew_groups() {
        return new_groups;
    }

    public void setNew_groups(List<StudyGroupDetails> new_groups) {
        this.new_groups = new_groups;
    }

    public List<StudyGroupDetails> getSubcribed_groups() {
        return subcribed_groups;
    }

    public void setSubcribed_groups(List<StudyGroupDetails> subcribed_groups) {
        this.subcribed_groups = subcribed_groups;
    }
}
