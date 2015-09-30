package tk.kolyanov.reminder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

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

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reminder_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.header = (TextView)convertView.findViewById(R.id.header);
            viewHolder.description = (TextView)convertView.findViewById(R.id.description);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.header.setText(getItem(position).getHeader());
        viewHolder.description.setText(getItem(position).getDescription());

        return convertView;
    }
}
