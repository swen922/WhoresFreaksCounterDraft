package com.horovod.android.whoresfreakscounterdraft;

import java.util.Date;

public class Dude {

    private DudeType dudeType;
    private String dateString;
    private String description;
    private String spinnerSelected;

    public Dude(DudeType dudeType, String dateString, String description, String spinner) {
        this.dudeType = dudeType;
        this.dateString = dateString;
        this.description = description;
        this.spinnerSelected = spinner;
    }

    // Constructor with Date formatting to two Strings
    public Dude(DudeType dudeType, Date date, String description, String spinner) {
        this.dudeType = dudeType;
        this.dateString = Util.formatDate(date);
        this.description = description;
        this.spinnerSelected = spinner;
    }

    public DudeType getDudeType() {
        return dudeType;
    }

    public String getDateString() {
        return dateString;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpinnerSelected() {
        return spinnerSelected;
    }

    public void setSpinnerSelected(String spinnerSelected) {
        this.spinnerSelected = spinnerSelected;
    }
}
