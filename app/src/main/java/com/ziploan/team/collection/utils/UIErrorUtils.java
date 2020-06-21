package com.ziploan.team.collection.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ziploan.team.App;
import com.ziploan.team.R;
import com.ziploan.team.collection.model.bank_names.Response;
import com.ziploan.team.verification_module.base.BaseActivity;
import com.ziploan.team.verification_module.caching.DatabaseManger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UIErrorUtils {

    public static void showToast(String message) {
        Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    public static void showDialogue(Context context, String message, DialogInterface.OnClickListener onYesClickListener) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_NoActionBar);
        } else {
            builder = new AlertDialog.Builder(context);
        }

        builder.setTitle(null)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, onYesClickListener)
                .setNegativeButton("", null).setIcon(null)
                .show();
    }

    public static void hideSoftKeyboard(Context context) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) (context).getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(((AppCompatActivity) context).getCurrentFocus().getWindowToken(), 0);
    }

    public static void hideFromDialogue(Context context, EditText editText){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

//    public static void hideSoftKeyboard(Context activity) {
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        //Find the currently focused view, so we can grab the correct window token from it.
//        View view = activity.getCurrentFocus();
//        //If no view currently has focus, create a new one, just so we can grab a window token from it
//        if (view == null) {
//            view = new View(activity);
//        }
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }

    public static void showSoftKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }

    public static void errorMethod(TextInputLayout pEditText, String textErrors, boolean invalid) {
        if (invalid) {
            pEditText.setErrorEnabled(true);
            pEditText.setError(textErrors);
            pEditText.requestFocus();
        } else {
            pEditText.setError(null);
            pEditText.setErrorEnabled(false);
        }
    }

    public static void scrollToView(final ScrollView scrollViewParent, final View view) {
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }

    private static void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }


    public static void getBankAdapter(final Context context,final AppCompatSpinner completeTextView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Response> responses = new ArrayList<>(App.BankData);
                Response response = new Response();
                response.setBankCode(null);
                response.setBankDisplayName("Select Bank");
                responses.add(0,response);
                ArrayAdapter<Response> arrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_item,responses);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                completeTextView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        }, 500);
    }

    public static void getBankAdapter(final Context context,final AutoCompleteTextView completeTextView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (App.BankData!=null && App.BankData.size()>0){
                    ArrayAdapter<Response> arrayAdapter = new ArrayAdapter<Response>(context, R.layout.bank_name_text_layout,App.BankData);
                    completeTextView.setAdapter(arrayAdapter);
                    completeTextView.setThreshold(1);
                    arrayAdapter.notifyDataSetChanged();
                }

            }
        }, 500);
    }

    public static void showSpinnerWithStringArray(final Context context, final AppCompatSpinner spinner, final ArrayList<String> data){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                        (context, R.layout.spinner_item,
                                data);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

                spinner.setAdapter(spinnerArrayAdapter);
            }
        }, 500);
    }

    public static boolean validateIFSC(String pan) {
        Pattern pattern = Pattern.compile("[A-Za-z]{4}0[A-Z0-9a-z]{6}");
        Matcher matcher = pattern.matcher(pan.toUpperCase());
        if (matcher.matches()) {
            return true;
        } else
            return false;
    }
}
