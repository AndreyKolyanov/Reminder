package tk.kolyanov.reminder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tk.kolyanov.reminder.R;
import tk.kolyanov.reminder.Objects.Remind;

public class RemindAdapter extends BaseAdapter {
    private Context mContext;
    private List<Remind> mItems;

    public RemindAdapter(Context c, List<Remind> items){
        mContext = c;
        mItems = items;
    }

    static class ViewHolder{
        TextView header;
        TextView description;
        TextView day;
        TextView month;
        TextView time;
    }

    public void updateData(List<Remind> items){
        mItems = items;
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
    public View getView(int position, View convertView, ViewGroup parent) {
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

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.header.setText(remind.getHeader());
        viewHolder.description.setText(remind.getDescription());

        viewHolder.day.setText(dateFormatDay.format(new Date(remind.getDateTime())));
        viewHolder.month.setText(dateFormatMonth.format(new Date(remind.getDateTime())));
        viewHolder.time.setText(timeFormat.format(new Date(remind.getDateTime())));

        return convertView;
    }
}
