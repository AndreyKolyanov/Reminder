package tk.kolyanov.reminder.Create;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.date.MonthAdapter;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import tk.kolyanov.reminder.DataBase.DataBaseHelper;
import tk.kolyanov.reminder.Retrieve.MainActivity;
import tk.kolyanov.reminder.R;
import tk.kolyanov.reminder.Objects.Remind;

public class AddFragment extends Fragment {

    Calendar mCalendar;
    DateFormat dateFormat;
    SimpleDateFormat timeFormat;
    public static String TIME_PATTERN = "HH:mm";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mCalendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        View view = inflater.inflate(R.layout.add_fragment, null);

        final EditText headerEdit = (EditText)view.findViewById(R.id.headerEdit);

        final EditText descriptionEdit = (EditText)view.findViewById(R.id.descriptionEdit);

        Button addButton = (Button)view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remind remind = new Remind(headerEdit.getText().toString(),
                        descriptionEdit.getText().toString(), mCalendar);
                DataBaseHelper mDataBaseHelper = DataBaseHelper.getInstance(getActivity());
                mDataBaseHelper.add(remind);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

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
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(),
                        "datePicker");
            }
        });

        return view;
    }

}
