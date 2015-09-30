package tk.kolyanov.reminder.Retrieve;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tk.kolyanov.reminder.R;

public class RetrieveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive);

        Intent intent = getIntent();
        Long id = intent.getLongExtra("id", 0);
        String header = intent.getStringExtra("header");
        String description = intent.getStringExtra("description");
        Long dateTime = intent.getLongExtra("datetime", 0);

        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putString("header", header);
        args.putString("description", description);
        args.putLong("datetime", dateTime);
        
        Fragment retrieve = new RetrieveFragment();
        retrieve.setArguments(args);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, retrieve);
        ft.addToBackStack(null);
        ft.commit();
    }

}
