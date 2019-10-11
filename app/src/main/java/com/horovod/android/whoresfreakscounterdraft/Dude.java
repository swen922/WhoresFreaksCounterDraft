package com.horovod.android.whoresfreakscounterdraft;

import java.util.Date;

public class Dude {

    private DudeType dudeType;
    private int idNumber;
    private String dateString;
    private String description;

    public Dude(DudeType dudeType, int idNumber, String dateString, String description) {
        this.dudeType = dudeType;
        this.idNumber = idNumber;
        this.dateString = dateString;
        this.description = description;
    }

    // Constructor with Date formatting to two Strings
    public Dude(DudeType dudeType, int idNumber, Date date, String description) {
        this.dudeType = dudeType;
        this.idNumber = idNumber;
        this.dateString = Util.formatDate(date);
        this.description = description;
    }

    public DudeType getDudeType() {
        return dudeType;
    }

    public int getIdNumber() {
        return idNumber;
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

}
