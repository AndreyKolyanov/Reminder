package tk.kolyanov.reminder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RemindAdapter extends BaseAdapter {
    private Context mContext;
    private List<Remind> mItems;
    DataBaseHelper mDataBaseHelper;
    StartDialog mStartDialog;

    public RemindAdapter( Activity activity){
        mContext = activity;
        mDataBaseHelper = DataBaseHelper.getInstance(mContext);
        mItems = mDataBaseHelper.getAllElements();
        mStartDialog = (StartDialog)activity;
    }

    static class ViewHolder{
        TextView header;
        TextView description;
        TextView day;
        TextView month;
        TextView time;
        SwipeLayout swipeLayout;

        ImageButton updateButton;
        ImageButton deleteButton;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Remind getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Remind remind = getItem(position);

        SimpleDateFormat dateFormatDay = new SimpleDateFormat("d", Locale.getDefault());
        SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMM", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reminder_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.header = (TextView)convertView.findViewById(R.id.header);
            viewHolder.description = (TextView)convertView.findViewById(R.id.description);
            viewHolder.day = (TextView)convertView.findViewById(R.id.miniCalendarDay);
            viewHolder.month = (TextView)convertView.findViewById(R.id.miniCalendarMonth);
            viewHolder.time = (TextView)convertView.findViewById(R.id.miniCalendarClock);

            viewHolder.swipeLayout = (SwipeLayout)convertView.findViewById(R.id.swipeLayout);

            viewHolder.updateButton = (ImageButton)convertView.findViewById(R.id.updateButton);
            viewHolder.deleteButton = (ImageButton)convertView.findViewById(R.id.deleteButton);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.header.setText(remind.getHeader());
        viewHolder.description.setText(remind.getDescription());

        viewHolder.day.setText(dateFormatDay.format(new Date(remind.getDateTime())));
        viewHolder.month.setText(dateFormatMonth.format(new Date(remind.getDateTime())));
        viewHolder.time.setText(timeFormat.format(new Date(remind.getDateTime())));

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

        viewHolder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remind remind = getItem(position);
                UpdateDialog updateDialog = (UpdateDialog)UpdateDialog.newInstance(remind);
                mStartDialog.startDialog(updateDialog, "update");
            }
        });

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remind remind = getItem(position);
                mDataBaseHelper.deleteElement(remind);
                mItems.remove(remind);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public void notifyDataSetChanged(){
        mItems = mDataBaseHelper.getAllElements();
        super.notifyDataSetChanged();
    }
}
