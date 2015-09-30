package tk.kolyanov.reminder.Retrieve;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import tk.kolyanov.reminder.Create.AddActivity;
import tk.kolyanov.reminder.DataBase.DataBaseHelper;
import tk.kolyanov.reminder.R;
import tk.kolyanov.reminder.Objects.Remind;
import tk.kolyanov.reminder.Adapters.RemindAdapter;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper mDataBaseHelper;
    RemindAdapter mRemindAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataBaseHelper = DataBaseHelper.getInstance(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.view);
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Remind remind = mRemindAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, RetrieveActivity.class);
                intent.putExtra("id", remind.getId());
                intent.putExtra("header", remind.getHeader());
                intent.putExtra("description", remind.getDescription());
                intent.putExtra("datetime", remind.getDateTime());
                startActivity(intent);
            }
        });

       FloatingActionButton addButton = (FloatingActionButton)findViewById(R.id.fab);

        addButton.attachToListView(listView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        mRemindAdapter = new RemindAdapter(this, getData());
        listView.setAdapter(mRemindAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mRemindAdapter.updateData(getData());
        mRemindAdapter.notifyDataSetChanged();
    }

    public List<Remind> getData(){
        return mDataBaseHelper.getAllElements();
    }


}
