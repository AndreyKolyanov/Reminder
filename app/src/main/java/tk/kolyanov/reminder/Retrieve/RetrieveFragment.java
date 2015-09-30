package tk.kolyanov.reminder.Retrieve;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import tk.kolyanov.reminder.DataBase.DataBaseHelper;
import tk.kolyanov.reminder.R;
import tk.kolyanov.reminder.Objects.Remind;
import tk.kolyanov.reminder.Update.UpdateActivity;

public class RetrieveFragment extends Fragment {

    DataBaseHelper mDataBaseHelper;
    Remind mRemind;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.retrieve_fragment, null);

        mDataBaseHelper = DataBaseHelper.getInstance(getActivity());

        Bundle args = getArguments();
        mRemind = new Remind(args.getLong("id"), args.getString("header"),
                args.getString("description"));

        TextView header = (TextView)view.findViewById(R.id.headerView);
        header.setText(mRemind.getHeader());

        TextView description = (TextView)view.findViewById(R.id.descriptionView);
        description.setText(mRemind.getDescription());

        Button updateButton = (Button)view.findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateActivity.class);
                intent.putExtra("id", mRemind.getId());
                intent.putExtra("header", mRemind.getHeader());
                intent.putExtra("description", mRemind.getDescription());
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
