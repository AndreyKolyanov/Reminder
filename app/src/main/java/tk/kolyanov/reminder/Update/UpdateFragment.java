package tk.kolyanov.reminder.Update;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import tk.kolyanov.reminder.DataBase.DataBaseHelper;
import tk.kolyanov.reminder.Retrieve.MainActivity;
import tk.kolyanov.reminder.R;
import tk.kolyanov.reminder.Objects.Remind;

public class UpdateFragment extends Fragment {

    DataBaseHelper mDataBaseHelper;
    Remind mRemind;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_fragment, null);

        Bundle args = getArguments();
        mDataBaseHelper = DataBaseHelper.getInstance(getActivity());

        mRemind = new Remind(args.getLong("id"), args.getString("header"),
                args.getString("description"));

        final EditText headerEdit = (EditText)view.findViewById(R.id.headerEdit);
        headerEdit.setText(mRemind.getHeader());

        final EditText descriptionEdit = (EditText)view.findViewById(R.id.descriptionEdit);
        descriptionEdit.setText(mRemind.getDescription());

        Button updateButton = (Button)view.findViewById(R.id.addButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRemind.setHeader(headerEdit.getText().toString());
                mRemind.setDescription(descriptionEdit.getText().toString());

                mDataBaseHelper.update(mRemind);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
