package com.horovod.android.whoresfreakscounterdraft;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Loader {

    private final String FILENAME = "FILENAME";
    private final String FILENAME_SPINNER_WHORE = "FILENAME_SPINNER_WHORE";
    private final String FILENAME_SPINNER_FREAK = "FILENAME_SPINNER_FREAK";


    private Context myContext;

    public Loader(Context myContext) {
        this.myContext = myContext;
    }

    public void writeBaseToJSON() {

        /*JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_DATA_LIST, new ArrayList(Data.getDudes()));
        } catch (JSONException e) {

        }*/

        Gson gson = new Gson();
        String outputJSON = gson.toJson(new ArrayList<>(Data.getDudes()));

        try (FileOutputStream out =  myContext.openFileOutput(FILENAME, Context.MODE_PRIVATE)) {
            out.write(outputJSON.getBytes());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readBaseFromJSON() {

        StringBuilder sb = new StringBuilder("");

        try (FileInputStream inn = myContext.openFileInput(FILENAME)) {
            if (inn.available() > 0) {
                byte[] input = new byte[inn.available()];
                while (inn.read(input) != -1) {
                    sb.append(new String(input));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String input = sb.toString();
        if (!input.isEmpty()) {
            Gson gson = new Gson();
            //JSONObject inputObject = null;
            List<Dude> inputList = gson.fromJson(input, new TypeToken<List<Dude>>(){}.getType());
            if (inputList != null) {
                Data.setDudes(inputList);
            }

            /*Log.i("LOGGGGINGG ||| ", input);
            Log.i("LOGGGGINGG ||| ", inputList.toString());
            Log.i("LOGGGGINGG ||| ", Data.getDudes().toString());
            Log.i("LOGGGGINGG ||| ", Data.getDude(0).toString());
            Log.i("LOGGGGINGG ||| ", Data.getDude(1).toString());*/

        }

    }


    public void writeWhoreSpinnerToJSON() {

        Gson gson = new Gson();
        List<String> whoresSpinner;

        if (!Data.getWhoresSpinner().isEmpty()) {
            whoresSpinner = new ArrayList<>(Data.getWhoresSpinner());
        }
        else {
            whoresSpinner = new ArrayList<>(Arrays.asList(myContext.getResources().getStringArray(R.array.whores_string_array)));
        }

        String outputWhoreSpinner = gson.toJson(whoresSpinner);

        try (FileOutputStream out =  myContext.openFileOutput(FILENAME_SPINNER_WHORE, Context.MODE_PRIVATE)) {
            out.write(outputWhoreSpinner.getBytes());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void writeFreakSpinnerToJSON() {

        Gson gson = new Gson();
        List<String> freaksSpinner;

        if (!Data.getFreaksSpinner().isEmpty()) {
            freaksSpinner = new ArrayList<>(Data.getFreaksSpinner());
        }
        else {
            freaksSpinner = new ArrayList<>(Arrays.asList(myContext.getResources().getStringArray(R.array.freaks_string_array)));
        }

        String outputFreakSpinner = gson.toJson(freaksSpinner);

        try (FileOutputStream out =  myContext.openFileOutput(FILENAME_SPINNER_FREAK, Context.MODE_PRIVATE)) {
            out.write(outputFreakSpinner.getBytes());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void readWhoreSpinnerFromJSON() {

        StringBuilder sb = new StringBuilder("");

        try (FileInputStream inn = myContext.openFileInput(FILENAME_SPINNER_WHORE)) {
            if (inn.available() > 0) {
                byte[] input = new byte[inn.available()];
                while (inn.read(input) != -1) {
                    sb.append(new String(input));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String input = sb.toString();

        Log.i("LOGGGINGGG |||| ", "WhoresInput.isEmpty() = " + input.isEmpty());

        if (!input.isEmpty()) {
            Gson gson = new Gson();
            List<String> inputList = gson.fromJson(input, new TypeToken<List<String>>(){}.getType());
            if (inputList != null) {
                Data.setWhoresSpinner(inputList);
            }
        }
    }

    public void readFreakSpinnerFromJSON() {

        StringBuilder sb = new StringBuilder("");

        try (FileInputStream inn = myContext.openFileInput(FILENAME_SPINNER_FREAK)) {
            if (inn.available() > 0) {
                byte[] input = new byte[inn.available()];
                while (inn.read(input) != -1) {
                    sb.append(new String(input));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String input = sb.toString();

        Log.i("LOGGGINGGG |||| ", "FreaksInput.isEmpty() = " + input.isEmpty());

        if (!input.isEmpty()) {
            Gson gson = new Gson();
            List<String> inputList = gson.fromJson(input, new TypeToken<List<String>>(){}.getType());
            if (inputList != null) {
                Data.setFreaksSpinner(inputList);
            }
        }
    }

}
