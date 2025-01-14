package com.horovod.android.whoresfreakscounterdraft;

import android.widget.ArrayAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Data {

    private static LinkedList<Dude> dudes = new LinkedList<>();
    private static List<String> whoresSpinner = new ArrayList<>();
    private static List<String> freaksSpinner = new ArrayList<>();

    public static final String KEY_CREATE_DUDE = "whoresfreakscounterdraft_KEY_CREATE_DUDE";
    public static final String KEY_UPDATE_DUDE = "whoresfreakscounterdraft_KEY_UPDATE_DUDE";
    public static final String KEY_DELETE_DUDE = "whoresfreakscounterdraft_KEY_DELETE_DUDE";
    public static final String KEY_CLEAR_LIST = "whoresfreakscounterdraft_KEY_CLEAR_LIST";
    public static final String KEY_IDNUMBER = "whoresfreakscounterdraft_KEY_IDNUMBER";
    public static final String KEY_DESCRIPTION = "whoresfreakscounterdraft_KEY_DESCRIPTION";
    public static final String KEY_SPINNER = "whoresfreakscounterdraft_KEY_SPINNER";
    public static final String KEY_SPINNER_ITEM = "whoresfreakscounterdraft_KEY_SPINNER_ITEM";
    public static final String KEY_DUDETYPE = "whoresfreakscounterdraft_KEY_DUDETYPE";
    public static final String KEY_SPINNER_EDIT = "whoresfreakscounterdraft_KEY_SPINNER_EDIT";
    public static final String KEY_SPINNER_EDIT_ITEM = "whoresfreakscounterdraft_KEY_SPINNER_EDIT_ITEM";
    public static final String KEY_SPINNER_UPDATE_ITEM = "whoresfreakscounterdraft_KEY_SPINNER_UPDATE_ITEM";
    public static final String KEY_PREVIOUS_ITEM = "whoresfreakscounterdraft_KEY_PREVIOUS_ITEM";


    public static CreateFragment createFragment;
    public static DudeFragment dudeFragment;
    public static SpinnerEditFragment spinnerEditFragment;
    public static SpinnerEditItemFragment spinnerEditItemFragment;


    public static void createDude(String dt, String description, int spinnerPos) {
        Dude newDude = new Dude(dt, new Date(), description, spinnerPos);
        dudes.addFirst(newDude);
    }


    public static Dude getDude(int index) {
        if (dudes.size() > index) {
            return dudes.get(index);
        }
        return null;
    }

    public static Dude getDudeFirst() {
        if (!dudes.isEmpty()) {
            return dudes.getFirst();
        }
        return null;
    }

    public static Dude getDudeLast() {
        if (!dudes.isEmpty()) {
            return dudes.getLast();
        }
        return null;
    }

    public static void addDude(Dude dude) {
        dudes.add(dude);
    }

    public static void addDudeFirst(Dude dude) {
        dudes.addFirst(dude);
    }

    public static void addDudeLast(Dude dude) {
        dudes.addLast(dude);
    }

    public static boolean removeDude(int index) {
        if (dudes.size() > index) {
            dudes.remove(index);
            return true;
        }
        return false;
    }


    public static List<Dude> getDudes() {
        return dudes;
    }

    public static void setDudes(List<Dude> newDudes) {
        Data.dudes = new LinkedList<>(newDudes);
    }

    public static void clearDudes() {
        Data.dudes.clear();
    }

    public static List<String> getWhoresSpinner() {
        return whoresSpinner;
    }

    public static void setWhoresSpinner(List<String> whoresSpinner) {
        Data.whoresSpinner = new ArrayList<>(whoresSpinner);
    }

    public static List<String> getFreaksSpinner() {
        return freaksSpinner;
    }

    public static void setFreaksSpinner(List<String> freaksSpinner) {
        Data.freaksSpinner = new ArrayList<>(freaksSpinner);
    }
}
