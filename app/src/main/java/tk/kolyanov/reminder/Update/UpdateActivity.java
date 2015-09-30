package tk.kolyanov.reminder.Update;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tk.kolyanov.reminder.R;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        Long id = intent.getLongExtra("id", 0);
        String header = intent.getStringExtra("header");
        String description = intent.getStringExtra("description");

        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putString("header", header);
        args.putString("description", description);

        Fragment update = new UpdateFragment();
        update.setArguments(args);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, update);
        ft.addToBackStack(null);
        ft.commit();
    }


}
