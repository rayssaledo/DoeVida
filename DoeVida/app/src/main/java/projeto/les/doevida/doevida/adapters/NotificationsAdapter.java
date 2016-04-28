package projeto.les.doevida.doevida.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.Request;
import projeto.les.doevida.doevida.models.User;

/**
 * Created by Rayssa- on 15/04/2016.
 */
public class NotificationsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Request> items;
    Context context;

    public NotificationsAdapter(Context context, List<Request> items) {
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

        Request item = items.get(position);
        convertView = mInflater.inflate(R.layout.my_item_notification, null);

        Date dateLastDonation = item.getRequestDate();
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        String lastDonation = format1.format(dateLastDonation);

        ((TextView) convertView.findViewById(R.id.tv_donor_name)).setText(item.getRequesterName());
        ((TextView) convertView.findViewById(R.id.tv_date_last_donation)).setText(lastDonation);

        return convertView;
    }
}
