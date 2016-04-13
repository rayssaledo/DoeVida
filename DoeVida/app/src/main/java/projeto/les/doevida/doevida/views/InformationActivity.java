package projeto.les.doevida.doevida.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import projeto.les.doevida.doevida.R;

public class InformationActivity extends AppCompatActivity {

    private Button btn_more_information1;
    private Button btn_more_information2;
    private Button btn_more_information3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        btn_more_information1 = (Button) findViewById(R.id.btn_more_information1);
        btn_more_information1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView(InformationActivity.this, MoreInformation1Activity.class);
            }
        });

        btn_more_information2 = (Button) findViewById(R.id.btn_more_information2);
        btn_more_information2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView(InformationActivity.this, MoreInformation2Activity.class);
            }
        });

        btn_more_information3 = (Button) findViewById(R.id.btn_more_information3);
        btn_more_information3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView(InformationActivity.this, MoreInformation3Activity.class);
            }
        });
    }

    public void setView(Context context, Class classe) {
        Intent it = new Intent();
        it.setClass(context, classe);
        startActivity(it);
    }
}
