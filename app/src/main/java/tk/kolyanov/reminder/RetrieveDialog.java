package tk.kolyanov.reminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class RetrieveDialog extends DialogFragment {

    Calendar mCalendar;
    DateFormat dateFormat;
    SimpleDateFormat timeFormat;
    public static String TIME_PATTERN = "HH:mm";

    public static Fragment newInstance(Remind remind){

        Bundle args = new Bundle();

        args.putString("header", remind.getHeader());
        args.putString("description", remind.getDescription());
        args.putLong("datetime", remind.getDateTime());

        RetrieveDialog fragment = new RetrieveDialog();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        mCalendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        View view = getActivity().getLayoutInflater().inflate(R.layout.retrieve_fragment, null);

        Bundle args = getArguments();

        TextView header = (TextView)view.findViewById(R.id.headerView);
        header.setText(args.getString("header"));

        TextView description = (TextView)view.findViewById(R.id.descriptionView);
        description.setText(args.getString("description"));

        TextView time = (TextView)view.findViewById(R.id.timeView);
        time.setText(timeFormat.format(new Date(args.getLong("datetime", 0))));

        TextView date = (TextView)view.findViewById(R.id.dateView);
        date.setText(dateFormat.format(new Date(args.getLong("datetime", 0))));

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
