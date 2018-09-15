package com.superlifesecretcode.app.data.model.studygroups.studtgroupplans;

public class StudyGroupPlanData {
    private String plan_id;
    private String plan_type;
    private String plan_cost;
    private String plan_title;
    private String plan_description;
    private String plan_validity;
    private String currency_code;

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }

    public String getPlan_cost() {
        return plan_cost;
    }

    public void setPlan_cost(String plan_cost) {
        this.plan_cost = plan_cost;
    }

    public String getPlan_title() {
        return plan_title;
    }

    public void setPlan_title(String plan_title) {
        this.plan_title = plan_title;
    }

    public String getPlan_description() {
        return plan_description;
    }

    public void setPlan_description(String plan_description) {
        this.plan_description = plan_description;
    }

    public String getPlan_validity() {
        return plan_validity;
    }

    public void setPlan_validity(String plan_validity) {
        this.plan_validity = plan_validity;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }

    private String currency_symbol;
}
