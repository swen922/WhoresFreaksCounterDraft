package com.horovod.android.whoresfreakscounterdraft;

import java.util.Date;

public class ItemList {

    private DudeType dudeType;
    private int idNumber;
    private String dateString;
    private String timeString;
    private String description;

    public ItemList(DudeType dudeType, int idNumber, String dateString, String timeString, String description) {
        this.dudeType = dudeType;
        this.idNumber = idNumber;
        this.dateString = dateString;
        this.timeString = timeString;
        this.description = description;
    }

    // Constructor with Date formatting to two Strings
    public ItemList(DudeType dudeType, int idNumber, Date date, String description) {
        this.dudeType = dudeType;
        this.idNumber = idNumber;
        this.dateString = Util.formatDate(date);
        this.timeString = Util.formatTime(date);
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

    public String getTimeString() {
        return timeString;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
