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
import projeto.les.doevida.doevida.models.Form;


public class RequestsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Form> items;
    Context mContext;

    public RequestsAdapter(Context context, List<Form> items) {
        mContext = context;
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

//        Date deadline_date = item.getDeadline();
//        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
//        String deadline  = format1.format(deadline_date);

        Bitmap imageBloodType = null;

        if (item.getTypeOfBlood().equals("A-")){
            imageBloodType = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.blood_a_negative);
        } else if (item.getTypeOfBlood().equals("A+")){
            imageBloodType = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.blood_a_positive);
        } else if (item.getTypeOfBlood().equals("AB-")){
            imageBloodType = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.blood_ab_negative);
        } else if (item.getTypeOfBlood().equals("AB+")){
            imageBloodType = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.blood_ab_positive);
        } else if (item.getTypeOfBlood().equals("B-")){
            imageBloodType = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.blood_b_negative);
        } else if (item.getTypeOfBlood().equals("B+")){
            imageBloodType = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.blood_b_positive);
        } else if (item.getTypeOfBlood().equals("O-")){
            imageBloodType = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.blood_o_negativo);
        } else if (item.getTypeOfBlood().equals("O+")){
            imageBloodType = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.blood_o_positive);
        }

        ((ImageView) convertView.findViewById(R.id.iv_blood_type)).setImageBitmap(imageBloodType);
        ((TextView) convertView.findViewById(R.id.tv_patient_name)).setText(item.getPatientName());
        ((TextView) convertView.findViewById(R.id.tv_hospital_name)).setText(item.getHospitalName());
        ((TextView) convertView.findViewById(R.id.tv_city_name)).setText(item.getCity());
        ((TextView) convertView.findViewById(R.id.tv_state_name)).setText(item.getState());
//        ((TextView) convertView.findViewById(R.id.tv_deadline)).setText(deadline);

        return convertView;
    }
}
