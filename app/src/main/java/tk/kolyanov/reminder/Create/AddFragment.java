package tk.kolyanov.reminder.Create;

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

public class AddFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_fragment, null);

        final EditText headerEdit = (EditText)view.findViewById(R.id.headerEdit);
        final EditText descriptionEdit = (EditText)view.findViewById(R.id.descriptionEdit);
        Button addButton = (Button)view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remind remind = new Remind(headerEdit.getText().toString(),
                        descriptionEdit.getText().toString());
                DataBaseHelper mDataBaseHelper = DataBaseHelper.getInstance(getActivity());
                mDataBaseHelper.add(remind);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
