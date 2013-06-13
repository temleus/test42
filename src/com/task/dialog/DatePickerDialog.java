package com.task.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;

/**
 * @author Leus Artem
 * @since 11.06.13
 */
public class DatePickerDialog extends DialogFragment {

    private Date date;
    private OnDateSetListener listener;

    public DatePickerDialog() {
    }

    public DatePickerDialog(final Date date, final OnDateSetListener listener) {
        this.date = date;
        OnDateSetListener wrapperListener = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                if(!date.equals(c.getTime())){
                    listener.onDateSet(view, year, monthOfYear, dayOfMonth);
                }
            }
        };
        this.listener = wrapperListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return new android.app.DatePickerDialog(getActivity(), listener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
    }
}
