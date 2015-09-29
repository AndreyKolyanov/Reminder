package tk.kolyanov.reminder;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

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

       FloatingActionButton addButton = (FloatingActionButton)findViewById(R.id.fab);

        addButton.attachToListView(listView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

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
