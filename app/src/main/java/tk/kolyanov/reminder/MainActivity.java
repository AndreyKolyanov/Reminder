package tk.kolyanov.reminder;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.view);
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }

        ListView listView = (ListView)findViewById(R.id.listView);
        RemindAdapter remindAdapter = new RemindAdapter(this, getData());
        listView.setAdapter(remindAdapter);
    }

    public ArrayList<Remind> getData(){
        ArrayList<Remind> data = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            data.add(new Remind("Header #" + i, "Description #" + i));
        }
        return data;
    }


}
