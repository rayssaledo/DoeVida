package projeto.les.doevida.doevida.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import projeto.les.doevida.doevida.R;

public class UserCadastreActivity extends AppCompatActivity {

    private EditText input_date_of_birth, input_date_of_last_donation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cadastre);

        input_date_of_birth = (EditText) findViewById(R.id.input_date_of_birth);
        input_date_of_last_donation = (EditText) findViewById(R.id.input_date_of_last_donation);

        MaskEditTextChangedListener maskDateOfBirth = new MaskEditTextChangedListener("##/##/####", input_date_of_birth);
        MaskEditTextChangedListener maskDateOfLastDonation = new MaskEditTextChangedListener("##/##/####", input_date_of_last_donation);

        input_date_of_birth.addTextChangedListener(maskDateOfBirth);
        input_date_of_last_donation.addTextChangedListener(maskDateOfLastDonation);
    }

}
