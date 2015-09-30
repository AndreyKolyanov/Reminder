package tk.kolyanov.reminder.Create;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import tk.kolyanov.reminder.R;


public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.view);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        Fragment add = new AddFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, add);
        ft.addToBackStack(null);
        ft.commit();
    }
}
