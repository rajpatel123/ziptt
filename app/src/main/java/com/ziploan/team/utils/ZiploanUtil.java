package com.ziploan.team.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.UnicodeSet;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.ziploan.team.R;
import com.ziploan.team.verification_module.caching.FilterItem;
import com.ziploan.team.verification_module.caching.FilterKey;
import com.ziploan.team.verification_module.customviews.TouchImageView;
import com.ziploan.team.verification_module.customviews.textview.CustomTextViewRobotoM;
import com.ziploan.team.verification_module.customviews.textview.CustomTextViewRobotoR;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZIploan-Nitesh on 2/4/2017.
 */
public class ZiploanUtil {
    public static String getStringFromInputStream(InputStream inputStream) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try  {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                if(nextLine!=null && nextLine.length()>0){
                    builder.append(nextLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static String changeDateFormat(String dateStr, String fromFormat, String toFormat) {
        if(dateStr.length()==0)
            return "";
        try {
            Date date = new SimpleDateFormat(fromFormat).parse(dateStr);
            String formattedDate = new SimpleDateFormat(toFormat).format(date);
            return formattedDate!=null?formattedDate:"";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String changeBorrowerDateFormat(String dateStr) {
        try{
            return changeDateFormat(dateStr.substring(0,dateStr.indexOf(' ')).trim(),"yyyy-MM-dd","dd MMM yyyy").toUpperCase();
        }catch (Exception e){
            return dateStr!=null?dateStr.toUpperCase():"";
        }
    }

    public static void showItemPopUp(Context mContext, View view, String... items) {
        PopupWindow popWindow = new PopupWindow();
        LinearLayout llView = new LinearLayout(mContext);
        llView.setOrientation(LinearLayout.VERTICAL);
        llView.setBackgroundResource(R.drawable.layout_background_round_corner_button);
        llView.setGravity(Gravity.CENTER_HORIZONTAL);
        llView.setPadding(10,10,10,10);
        for(int i=0; i<items.length;i++){
            TextView tvItem = new CustomTextViewRobotoM(mContext);
            tvItem.setGravity(Gravity.CENTER);
            tvItem.setText(items[i]);
            llView.addView(tvItem);
        }
        popWindow.setContentView(llView);
        int[] a = new int[2];
        view.getLocationInWindow(a);
        popWindow.showAtLocation(((Activity)mContext).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0 , a[1]+view.getHeight());

        if (Build.VERSION.SDK_INT != 24) {
            popWindow.update(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        int x= a[0];
        int y = a[1]+view.getHeight();
        popWindow.update(x,y,view.getWidth()-20,100);
        dimBehind(popWindow);
    }


    public static void dimBehind(PopupWindow popupWindow) {
        dimBehind(popupWindow.getContentView());
    }

    public static void dimBehind(View view) {
        View container;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            container = (View) view.getParent();
        } else {
            container = view;
        }
        Context context = view.getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if(container.getLayoutParams() instanceof WindowManager.LayoutParams){
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.3f;
            wm.updateViewLayout(container, p);
        }else {
            WindowManager.LayoutParams p = ((Activity)context).getWindow().getAttributes();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.3f;
            ((Activity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            //wm.updateViewLayout(container, p);
        }
    }

    public static String getUserStatusLabel(boolean status){
        return status?"Verified":"Verify";
    }

    public static float dp2px(android.content.res.Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    public static float sp2px(android.content.res.Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
    @SuppressLint("NewApi")
    public static String getRealPathFromURI(Context context, Uri uri) {
        if(Build.VERSION.SDK_INT>=19){
            return RealPathUtil.getRealPathFromURI_API19(context,uri);
        }else {
            return RealPathUtil.getRealPathFromURI_API11to18(context,uri);
        }
    }


    public static boolean isAadhaarValid(String value) {
        return value.length()==12 && TextUtils.isDigitsOnly(value);
    }


    public static Dialog getMessageCustomDialog(Context context, int layout, Spanned s) {
        return getMessageCustomDialog(context,layout,s,Color.parseColor("#000000"));
    }

    public static Dialog getMessageCustomDialog(Context context, int layout, String s) {
        return getMessageCustomDialog(context,layout,Html.fromHtml(s),Color.parseColor("#000000"));
    }
    public static Dialog getMessageCustomDialog(Context context, int layout, String s, int colorCode) {
        return getMessageCustomDialog(context,layout,Html.fromHtml(s),colorCode);
    }
    public static Dialog getMessageCustomDialog(Context context, int layout, Spanned s, int colorCode) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        final Dialog dialog = new Dialog(contextWrapper);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(layout);
        ((TextView)dialog.findViewById(R.id.tv_message)).setText(s);
        ((TextView)dialog.findViewById(R.id.tv_message)).setTextColor(colorCode);

        return dialog;
    }

    public static Dialog getCustomDialog(Context context, int layout) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        final Dialog dialog = new Dialog(contextWrapper);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(layout);

        return dialog;
    }

    public static Dialog getCustomDialogCollection(Context context, int layout) {
        ContextWrapper contextWrapper = new ContextWrapper(context);
        final Dialog dialog = new Dialog(contextWrapper);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(layout);
        dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        return dialog;
    }

    public static int getIntFromString(String s) {
        return Integer.parseInt(!TextUtils.isEmpty(s)?s:"0");
    }

    public static String capitalize(String string) {
        if(TextUtils.isEmpty(string)) {
            return string;
        } else {
            char ch = string.charAt(0);
            return Character.isTitleCase(ch)?string:Character.toTitleCase(ch) + string.substring(1);
        }
    }

    public static String getUrlWithoutParams(String s) {
        if(s.contains("?")){
            return s.substring(0,s.indexOf("?"));
        }else {
            return s;
        }
    }

    public static boolean checkInternetConnection(Context mContext) {
        if (ConnectivityReceiver.isConnected(mContext)) return true;
        else{
            return false;
        }
    }

    public static void showAlertInfo(Context context,String msg) {
        final Dialog dialog = ZiploanUtil.getMessageCustomDialog(context, R.layout.dialog_common_prompt, msg);
        dialog.setCancelable(false);
        dialog.findViewById(R.id.tv_okay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        try{
            dialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getFileName(String s3UrlPath) {
        if(TextUtils.isEmpty(s3UrlPath))
            return s3UrlPath;
        try{
            return s3UrlPath.substring(s3UrlPath.lastIndexOf('_')+1,s3UrlPath.length());
        }catch (Exception e){
            e.printStackTrace();
        }
        return s3UrlPath;
    }

    public static String getPhotoUrlFromPhotoArray(List<ZiploanPhoto> arrPhoto) {
        if(arrPhoto!=null && arrPhoto.size()>0 && arrPhoto.get(0)!=null && arrPhoto.get(0).getPhotoPath()!=null){
            return arrPhoto.get(0).getPhotoPath();
        }
        return "";
    }

    public static boolean isGPSEnabled(Context context){
        final LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
        return manager.isProviderEnabled( LocationManager.GPS_PROVIDER );
    }

    public static boolean isNetworkLocationServiceEnabled(Context context){
        final LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
        return manager.isProviderEnabled( LocationManager.NETWORK_PROVIDER );
    }

    public static boolean isLocationProviderEnabled(Context context){
        return (isGPSEnabled(context) || isNetworkLocationServiceEnabled(context));
    }

    public static void openPhotoInZoom(Context context,String photoPath) {
        final Dialog dialog = ZiploanUtil.getCustomDialog(context, R.layout.dialog_zoom_image);
        dialog.setCancelable(true);
        final TouchImageView imageView = (TouchImageView) dialog.findViewById(R.id.image_view);
        LinearLayout llOuter = (LinearLayout) dialog.findViewById(R.id.ll_outer);
        final Point size = ZiploanSPUtils.getInstance(context).getScreenSize();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        params.width = size.x;
        params.height = size.y;
        imageView.setLayoutParams(params);
        if(URLUtil.isValidUrl(photoPath)){
            Picasso.with(context).load(photoPath).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imageView.setBitmap(bitmap,size);
                    imageView.setScaleType(ImageView.ScaleType.MATRIX);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }else {
            Picasso.with(context).load(new File(photoPath)).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imageView.setBitmap(bitmap,size);
                    imageView.setScaleType(ImageView.ScaleType.MATRIX);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }

        try {
            if (context != null && !((Activity) context).isFinishing())
                dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(Context mContext, String msg) {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    public static String getDateFromTimestamp(long timeInMillis, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(timeInMillis));
    }
    /**
     * Check mobileno is valid or not
     * @param mobileno mobileno that needs to be checked
     * @return Return true if mobileno is valid otherwise false
     */
    public static boolean isMobileValid(String mobileno) {
        if (TextUtils.isEmpty(mobileno))
            return false;
        String regexMobileIndia = "([7-9]{1}[0-9]{9})";
        Pattern pattern = Pattern.compile(regexMobileIndia);
        Matcher matcher = pattern.matcher(mobileno);
        return matcher.matches();
    }

    public static String keyToText(String key) {
        try{
            return capitalize(key.replaceAll("_"," "));
        }catch (Exception e){
            e.printStackTrace();
        }
        return key;
    }

    public static HashMap<String, ArrayList<FilterItem>> getDeepCopyListItems(HashMap<String, ArrayList<FilterItem>> filterItems) {
        HashMap<String, ArrayList<FilterItem>> two = new HashMap<>();
        Iterator iterator = filterItems.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next().toString();
            ArrayList<FilterItem> items = filterItems.get(key);
            ArrayList<FilterItem> itemsCopy = new ArrayList<>();
            for(FilterItem t : items){
                FilterItem copy = deepCopy(t);
                itemsCopy.add(copy);
            }
            two.put(key,itemsCopy);
        }
        return two;
    }

    public static FilterItem deepCopy(FilterItem input){
        FilterItem copy = new FilterItem();
        copy.setFilter_id(input.getFilter_id());
        copy.setFilter_name(input.getFilter_name());
        copy.setSelected(input.isSelected());

        return copy;
    }

    public static List<FilterKey> getDeepCopyList(ArrayList<FilterKey> filtersKey) {
        List<FilterKey> two = new ArrayList<FilterKey>();
        for(FilterKey t : filtersKey){
            FilterKey copy = deepCopy(t);
            two.add(copy);
        }
        return two;
    }


    public static FilterKey deepCopy(FilterKey input){
        FilterKey copy = new FilterKey();
        copy.setKey_name(input.getKey_name());
        copy.setIs_filter_selected(input.is_filter_selected());
        copy.setIs_selected(input.is_selected());

        return copy;
    }

    public static String fetchAppVerison(Context context){
        String versionName = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * Before calling this method, you need to set required text to the TextView
     * @param textView
     * @param text
     * @param onClickListener
     */
    public static void setClickableLink(TextView textView, String text, final View.OnClickListener onClickListener) {
        if(text.length()==0)
            return;
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                onClickListener.onClick(textView);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
            }
        };
        ss.setSpan(clickableSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);
    }

    /**
     * Check email is valid or not
     * @param email Email address that needs to be checked
     * @return Return true if email is valid otherwise false
     */
    public static boolean isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public static String emptyIfNull(String string) {
        if(string==null){
            return "";
        }
        return string;
    }

    public static ArrayList<String> extractUrlList(List<ZiploanPhoto> photo_url) {
        ArrayList<String> urlString = new ArrayList<>();
        for (ZiploanPhoto photo : photo_url){
            if(!TextUtils.isEmpty(photo.getRemote_path())){
                urlString.add(photo.getRemote_path());
            }
        }
        return urlString;

    }

    public static void scrollToView(final ScrollView scrollViewParent, final View view) {
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        scrollViewParent.smoothScrollTo(0, childOffset.y);
        view.requestFocus();
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


    public static String loadJSONFromAsset(Context context,String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}