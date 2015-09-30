package tk.kolyanov.reminder.Retrieve;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tk.kolyanov.reminder.DataBase.DataBaseHelper;
import tk.kolyanov.reminder.R;
import tk.kolyanov.reminder.Objects.Remind;
import tk.kolyanov.reminder.Update.UpdateActivity;

public class RetrieveFragment extends Fragment {

    DataBaseHelper mDataBaseHelper;
    Remind mRemind;

    Calendar mCalendar;
    DateFormat dateFormat;
    SimpleDateFormat timeFormat;
    public static String TIME_PATTERN = "HH:mm";

    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.retrieve_fragment, null);

        mCalendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        mDataBaseHelper = DataBaseHelper.getInstance(getActivity());

        Bundle args = getArguments();
        mRemind = new Remind(args.getLong("id"), args.getString("header"),
                args.getString("description"), args.getLong("datetime"));

        TextView header = (TextView)view.findViewById(R.id.headerView);
        header.setText(mRemind.getHeader());

        TextView description = (TextView)view.findViewById(R.id.descriptionView);
        description.setText(mRemind.getDescription());

        TextView time = (TextView)view.findViewById(R.id.timeView);
        time.setText(timeFormat.format(new Date(mRemind.getDateTime())));

        TextView date = (TextView)view.findViewById(R.id.dateView);
        date.setText(dateFormat.format(new Date(mRemind.getDateTime())));

        Button updateButton = (Button)view.findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateActivity.class);
                intent.putExtra("id", mRemind.getId());
                intent.putExtra("header", mRemind.getHeader());
                intent.putExtra("description", mRemind.getDescription());
                intent.putExtra("datetime", mRemind.getDateTime());
                startActivity(intent);
            }
        });

        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBaseHelper.deleteElement(mRemind);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
