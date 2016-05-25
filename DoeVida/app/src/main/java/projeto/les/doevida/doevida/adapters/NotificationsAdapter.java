package projeto.les.doevida.doevida.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.models.Notification;

public class NotificationsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Notification> items;
    Context context;

    public NotificationsAdapter(Context context, List<Notification> items) {
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

        Notification item = items.get(position);
        convertView = mInflater.inflate(R.layout.my_item_notification, null);

//        Date date = item.getDate();
//        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
//        String date_send = format1.format(date);

        String status = "";
        String label_name = "Nome do paciente";
        String name = "";

        if (item.getTitle().equals("Solicitacao de sangue")){
            status = "PEDIDO DE SANGUE";
            name = item.getForm().getPatientName();
        }
        if (item.getTitle().equals("Confirmacao de doacao")){
            status = "DOAÇÃO REALIZADA";
            label_name = "Nome do doador";
            name = item.getDonorName();
        }
        else if (item.getTitle().equals("Pedido aceito")){
            status = "PEDIDO ACEITO";
            label_name = "Nome do doador";
            name = item.getDonorName();
        }
        else if (item.getTitle().equals("Doacao recebida")){
            status = "DOAÇÃO RECEBIDA";
            name = item.getPatientName();
        }


        ((TextView) convertView.findViewById(R.id.tv_label_name)).setText(label_name);
        ((TextView) convertView.findViewById(R.id.tv_name)).setText(name);
//        ((TextView) convertView.findViewById(R.id.tv_date)).setText(date_send);
        ((TextView) convertView.findViewById(R.id.tv_status)).setText(status);

        return convertView;
    }
}
