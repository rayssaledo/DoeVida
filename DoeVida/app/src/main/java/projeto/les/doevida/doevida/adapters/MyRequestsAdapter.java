//package projeto.les.doevida.doevida.adapters;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import projeto.les.doevida.doevida.R;
//import projeto.les.doevida.doevida.models.Request;
//import projeto.les.doevida.doevida.models.User;
//
///**
// * Created by Andreza on 16/04/2016.
// */
//public class MyRequestsAdapter extends BaseAdapter {
//
//    private LayoutInflater mInflater;
//    private List<Request> items;
//    Context context;
//
//    public MyRequestsAdapter (Context context, List<Request> items) {
//        this.items = items;
//        mInflater = LayoutInflater.from(context);
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return items.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return items.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Request item = items.get(position);
//        convertView = mInflater.inflate(R.layout.my_item_my_registration, null);
//
//        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
//        String dateRequest = format1.format(item.getRequestDate());
//
//        ((TextView) convertView.findViewById(R.id.tv_requester_name)).setText(item.getRequesterName());
//        ((TextView) convertView.findViewById(R.id.tv_date_request)).setText(dateRequest);
//
//        return convertView;
//    }
//}

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

/**
 * Created by Lucas on 16/04/2016.
 */
public class MyRequestsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Form> items;
    Context context;

    public MyRequestsAdapter(Context context, List<Form> items) {
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

        convertView = mInflater.inflate(R.layout.my_item_my_registration, null);

        Form item = items.get(position);

        ((TextView) convertView.findViewById(R.id.tv_requester_name)).setText(item.getPatientName());
        // ((TextView) convertView.findViewById(R.id.tv_date_request)).setText(item.getDeadline().toString());//TODO data do pedido e nao deadline
        //((TextView) convertView.findViewById(R.id.tv_status)).setText("");

        return convertView;
    }
}


