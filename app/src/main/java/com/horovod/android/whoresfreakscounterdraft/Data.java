package com.horovod.android.whoresfreakscounterdraft;

import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Data {

    private static LinkedList<Dude> dudes = new LinkedList<>();

    public static final String KEY_UPDATE_DUDES = "whoresfreakscounterdraft_KEY_UPDATE_DUDES";
    public static final String KEY_DELETE_DUDE = "whoresfreakscounterdraft_KEY_DELETE_DUDE";
    public static final String KEY_IDNUMBER = "KEY_IDNUMBER";
    public static final String KEY_DESCRIPTION = "KEY_DESCRIPTION";
    public static DudeFragment dudeFragment;

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



}
