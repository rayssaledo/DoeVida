package projeto.les.doevida.doevida.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.User;

public class DonorsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<User> items;

    public DonorsAdapter(Context context, List<User> items) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        User item = items.get(position);
        convertView = mInflater.inflate(R.layout.my_item_donor, null);

        ((ImageView) convertView.findViewById(R.id.iv_blood_type)).setImageBitmap(null);
        ((TextView) convertView.findViewById(R.id.tv_donor_name)).setText(item.getName());
        ((TextView) convertView.findViewById(R.id.tv_city)).setText(item.getCity());
        ((ImageView) convertView.findViewById(R.id.iv_number_donates)).setImageBitmap(null);
        ((TextView) convertView.findViewById(R.id.tv_last_donation)).setText(item.getLastDonation().toString());

        return convertView;
    }

}
