package projeto.les.doevida.doevida.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.User;

/**
 * Created by Lucas on 16/04/2016.
 */
public class RequestedMeAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<User> items;
    Context context;

    public RequestedMeAdapter(Context context, List<User> items) {
        this.items = items;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.my_item_requested_me, null);

        ((TextView) convertView.findViewById(R.id.tv_name)).setText("");
        ((TextView) convertView.findViewById(R.id.tv_date_request)).setText("");
        ((TextView) convertView.findViewById(R.id.tv_status)).setText("");

        return convertView;
    }
}

