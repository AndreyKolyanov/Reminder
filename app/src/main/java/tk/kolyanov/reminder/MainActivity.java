package tk.kolyanov.reminder;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements StartDialog {

    RemindAdapter mRemindAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.view);
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Remind remind = mRemindAdapter.getItem(position);
                RetrieveDialog retrieveDialog = (RetrieveDialog) RetrieveDialog.newInstance(remind);
                startDialog(retrieveDialog, "retrieve");
            }
        });

       FloatingActionButton addButton = (FloatingActionButton)findViewById(R.id.fab);

        addButton.attachToListView(listView);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialog addDialog = new AddDialog();
                startDialog(addDialog, "add");
            }
        });

        mRemindAdapter = new RemindAdapter(this);
        listView.setAdapter(mRemindAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void startDialog(DialogFragment fragment, String tag) {
        FragmentManager fragmentManager = getFragmentManager();
        fragment.show(fragmentManager, tag);
    }

    @Override
    public void noyifyAdapter() {
        mRemindAdapter.notifyDataSetChanged();
    }
}
