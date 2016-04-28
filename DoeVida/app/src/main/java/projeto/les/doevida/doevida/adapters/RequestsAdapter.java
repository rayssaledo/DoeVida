package projeto.les.doevida.doevida.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.Form;


public class RequestsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Form> items;
    Context context;

    public RequestsAdapter(Context context, List<Form> items) {
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

        convertView = mInflater.inflate(R.layout.my_item_requests, null);

        Form item = items.get(position);

        ((TextView) convertView.findViewById(R.id.tv_name)).setText(item.getPatientName());
        ((TextView) convertView.findViewById(R.id.tv_hospital)).setText(item.getHospitalName());
        ((TextView) convertView.findViewById(R.id.tv_city)).setText(item.getCity());
        ((TextView) convertView.findViewById(R.id.tv_name)).setText(item.getPatientName());
      //  ((TextView) convertView.findViewById(R.id.tv_deadline)).setText(item.getDeadline());




        ((TextView) convertView.findViewById(R.id.tv_date_request)).setText(item.getDeadline().toString());//TODO data do pedido e nao deadline
        //((TextView) convertView.findViewById(R.id.tv_status)).setText("");

        return convertView;
    }
}
