package projeto.les.doevida.doevida.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import projeto.les.doevida.doevida.R;
import projeto.les.doevida.doevida.Utils.HttpListener;
import projeto.les.doevida.doevida.Utils.HttpUtils;
import projeto.les.doevida.doevida.Utils.MySharedPreferences;
import projeto.les.doevida.doevida.adapters.DonorsAdapter;
import projeto.les.doevida.doevida.adapters.RequestedMeAdapter;
import projeto.les.doevida.doevida.models.User;

public class RequestedMeActivity extends AppCompatActivity {

    private ListView lv_requested_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_me);

        lv_requested_me = (ListView) findViewById(R.id.lv_requested_me);
        lv_requested_me.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
