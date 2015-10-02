package tk.kolyanov.reminder;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.Locale;


public class AddDialog extends DialogFragment {

    Calendar mCalendar;
    DateFormat dateFormat;
    SimpleDateFormat timeFormat;
    public static String TIME_PATTERN = "HH:mm";
    StartDialog mStartDialog;
    AlarmManager mAlarmManager;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        mCalendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        mStartDialog = (StartDialog)getActivity();

        View view = getActivity().getLayoutInflater().inflate(R.layout.add_fragment, null);

        final EditText headerEdit = (EditText)view.findViewById(R.id.headerEdit);

        final EditText descriptionEdit = (EditText)view.findViewById(R.id.descriptionEdit);

        final Button timeButton = (Button)view.findViewById(R.id.timeButton);
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

        return new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme)
                .setTitle(R.string.addButton)
                .setView(view)
                .setPositiveButton(R.string.addButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Remind remind = new Remind(headerEdit.getText().toString(),
                                descriptionEdit.getText().toString(), mCalendar);
                        DataBaseHelper mDataBaseHelper = DataBaseHelper.getInstance(getActivity());
                        long id = mDataBaseHelper.add(remind);
                        mStartDialog.noyifyAdapter();
                        mAlarmManager = (AlarmManager)getActivity()
                                .getSystemService(Context.ALARM_SERVICE);

                        Intent intent = new Intent(getActivity(), RemindReceiver.class);
                        intent.putExtra("header", remind.getHeader());
                        intent.putExtra("description", remind.getDescription());
                        intent.putExtra("id", id);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0,
                                intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        mAlarmManager.set(AlarmManager.RTC_WAKEUP, remind.getDateTime(),
                                pendingIntent);
                    }
                })
                .create();
    }

}
