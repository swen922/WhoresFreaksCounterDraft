package com.horovod.android.whoresfreakscounterdraft;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Data {

    private static volatile int idNumber = 0;
    private static Map<Integer, Dude>  dudes = new TreeMap<>();
    public static final String KEY_ARGS_INDEX = "KEY_ARGS_INDEX";
    public static final String KEY_UPDATE_DUDES = "whoresfreakscounterdraft_KEY_UPDATE_DUDES";
    public static final String KEY_IDNUMBER = "KEY_IDNUMBER";
    public static final String KEY_DESCRIPTION = "KEY_DESCRIPTION";
    public static DudeFragment dudeFragment;

    public static Dude getDude(int idNumber) {
        if (dudes.containsKey(idNumber)) {
            return dudes.get(idNumber);
        }
        return null;
    }

    public static void putDude(int idNumber, Dude dude) {
        dudes.put(idNumber, dude);
    }

    public static Map<Integer, Dude> getDudes() {
        return new HashMap<>(dudes);
    }

    public static void setDudes(Map<Integer, Dude> newdudes) {
        dudes = newdudes;
    }

    public static int getIdNumber() {
        return idNumber;
    }

    public static void setIdNumber(int newIDNumber) {
        idNumber = newIDNumber;
    }
}
