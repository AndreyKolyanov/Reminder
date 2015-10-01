package tk.kolyanov.reminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateDialog extends DialogFragment {

    Calendar mCalendar;
    DateFormat dateFormat;
    SimpleDateFormat timeFormat;
    public static String TIME_PATTERN = "HH:mm";
    Remind mRemind;
    StartDialog mStartDialog;

    public static Fragment newInstance(Remind remind){

        Bundle args = new Bundle();

        args.putLong("id", remind.getId());
        args.putString("header", remind.getHeader());
        args.putString("description", remind.getDescription());
        args.putLong("datetime", remind.getDateTime());

        UpdateDialog fragment = new UpdateDialog();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        mCalendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        View view = getActivity().getLayoutInflater().inflate(R.layout.add_fragment, null);

        mStartDialog = (StartDialog)getActivity();

        Bundle args = getArguments();

        mRemind = new Remind(args.getLong("id"), args.getString("header"),
                args.getString("description"), args.getLong("datetime"));

        mCalendar.setTime(new Date(mRemind.getDateTime()));

        final EditText headerEdit = (EditText)view.findViewById(R.id.headerEdit);
        headerEdit.setText(mRemind.getHeader());

        final EditText descriptionEdit = (EditText)view.findViewById(R.id.descriptionEdit);
        descriptionEdit.setText(mRemind.getDescription());

        final Button timeButton = (Button)view.findViewById(R.id.timeButton);
        timeButton.setText(timeFormat.format(mCalendar.getTime()));
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay,
                                          int minute) {
                        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        mCalendar.set(Calendar.MINUTE, minute);
                        timeButton.setText(timeFormat.format(mCalendar.getTime()));
                    }
                }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true)
                        .show(getFragmentManager(), "timePicker");
            }
        });

        final Button dateButton = (Button)view.findViewById(R.id.dateButton);
        dateButton.setText(dateFormat.format(mCalendar.getTime()));
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog datePickerDialog, int year,
                                          int monthOfYear, int dayOfMonth) {
                        mCalendar.set(year, monthOfYear, dayOfMonth);
                        dateButton.setText(dateFormat.format(mCalendar.getTime()));
                    }
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH))
                        .show(getFragmentManager(), "datePicker");
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.updateButton)
                .setView(view)
                .setPositiveButton(R.string.updateButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRemind.setHeader(headerEdit.getText().toString());
                        mRemind.setDescription(descriptionEdit.getText().toString());
                        mRemind.setDateTime(mCalendar.getTime().getTime());
                        DataBaseHelper mDataBaseHelper = DataBaseHelper.getInstance(getActivity());
                        mDataBaseHelper.update(mRemind);
                        mStartDialog.noyifyAdapter();
                    }
                })
                .create();
    }
}
