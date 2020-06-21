package com.ziploan.team.verification_module.borrowerdetails.ekitsign;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import com.ziploan.team.App;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatePickerFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {
        private static final String TAG = "DatePickerFragment";
        final Calendar c = Calendar.getInstance();

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Set the current date as the default date
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Return a new instance of DatePickerDialog

            DatePickerDialog dialog = new DatePickerDialog(getActivity(), DatePickerFragment.this, year, month, day);

            Calendar maxDate = Calendar.getInstance();
            maxDate.set(Calendar.DAY_OF_MONTH, day);
            maxDate.set(Calendar.MONTH, month);
            maxDate.set(Calendar.YEAR, year - 21);

            dialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
            return dialog;

//            return new DatePickerDialog(getActivity(), DatePickerFragment.this, year, month, day);
        }

        // called when a date has been selected
        public void onDateSet(DatePicker view, int year, int month, int day) {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            String selectedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(c.getTime());

            Log.d(TAG, "onDateSet: " + selectedDate);
            // send date back to the target fragment
            getTargetFragment().onActivityResult(
                    getTargetRequestCode(),
                    Activity.RESULT_OK,
                    new Intent().putExtra("selectedDate", selectedDate)
            );
        }
    }
