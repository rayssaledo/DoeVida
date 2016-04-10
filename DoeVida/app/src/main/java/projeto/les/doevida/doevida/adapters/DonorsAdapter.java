package projeto.les.doevida.doevida.adapters;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.User;

public class DonorsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<User> items;
    Context context;

    public DonorsAdapter(Context context, List<User> items) {
        this.items = items;
        mInflater = LayoutInflater.from(context);
        this.context = context;
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

        Date dateLastDonation = item.getLastDonation();
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        String lastDonation = format1.format(dateLastDonation);

        Bitmap imageBlood = BitmapFactory.decodeResource(context.getResources(), R.mipmap.blood);
        ((ImageView) convertView.findViewById(R.id.iv_blood_type)).setImageBitmap(imageBlood);

        if (item.getTypeOfBlood().equals("A-")){
            Bitmap imageBloodType = BitmapFactory.decodeResource(context.getResources(), R.mipmap.blood_a_negative);
            ((ImageView) convertView.findViewById(R.id.iv_blood_type)).setImageBitmap(imageBloodType);
        } else if (item.getTypeOfBlood().equals("A+")){
            Bitmap imageBloodType = BitmapFactory.decodeResource(context.getResources(), R.mipmap.blood_a_positive);
            ((ImageView) convertView.findViewById(R.id.iv_blood_type)).setImageBitmap(imageBloodType);
        } else if (item.getTypeOfBlood().equals("AB-")){
            Bitmap imageBloodType = BitmapFactory.decodeResource(context.getResources(), R.mipmap.blood_ab_negative);
            ((ImageView) convertView.findViewById(R.id.iv_blood_type)).setImageBitmap(imageBloodType);
        } else if (item.getTypeOfBlood().equals("AB+")){
            Bitmap imageBloodType = BitmapFactory.decodeResource(context.getResources(), R.mipmap.blood_ab_positive);
            ((ImageView) convertView.findViewById(R.id.iv_blood_type)).setImageBitmap(imageBloodType);
        } else if (item.getTypeOfBlood().equals("B-")){
            Bitmap imageBloodType = BitmapFactory.decodeResource(context.getResources(), R.mipmap.blood_b_negative);
            ((ImageView) convertView.findViewById(R.id.iv_blood_type)).setImageBitmap(imageBloodType);
        } else if (item.getTypeOfBlood().equals("B+")){
            Bitmap imageBloodType = BitmapFactory.decodeResource(context.getResources(), R.mipmap.blood_b_positive);
            ((ImageView) convertView.findViewById(R.id.iv_blood_type)).setImageBitmap(imageBloodType);
        } else if (item.getTypeOfBlood().equals("O-")){
            Bitmap imageBloodType = BitmapFactory.decodeResource(context.getResources(), R.mipmap.blood_o_negativo);
            ((ImageView) convertView.findViewById(R.id.iv_blood_type)).setImageBitmap(imageBloodType);
        } else if (item.getTypeOfBlood().equals("O+")){
            Bitmap imageBloodType = BitmapFactory.decodeResource(context.getResources(), R.mipmap.blood_o_positive);
            ((ImageView) convertView.findViewById(R.id.iv_blood_type)).setImageBitmap(imageBloodType);
        }

        ((TextView) convertView.findViewById(R.id.tv_donor_name)).setText(item.getName());
        ((TextView) convertView.findViewById(R.id.tv_city_name)).setText(item.getCity());
        ((ImageView) convertView.findViewById(R.id.iv_donates)).setImageBitmap(imageBlood);
        ((TextView) convertView.findViewById(R.id.tv_number_donates)).setText(null);
        ((TextView) convertView.findViewById(R.id.tv_date_last_donation)).setText(lastDonation);

        return convertView;
    }

}
