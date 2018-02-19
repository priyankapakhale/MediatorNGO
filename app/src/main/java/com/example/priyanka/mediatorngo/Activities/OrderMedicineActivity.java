package com.example.priyanka.mediatorngo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.priyanka.mediatorngo.Network.ApiClient;
import com.example.priyanka.mediatorngo.Network.ApiInterface;
import com.example.priyanka.mediatorngo.R;
import com.google.firebase.auth.FirebaseAuth;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by priyanka on 1/31/18.
 */

public class OrderMedicineActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText TFmedicine_name, TFbarcode,TFquantity;
    private String medicine_name,barcode;
    private int quantity;

    private String email_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_medicine);
        mAuth = FirebaseAuth.getInstance();
        email_id = mAuth.getCurrentUser().getEmail();

        Log.d("email",email_id);
        TFmedicine_name = findViewById(R.id.medicine_name);
        TFbarcode = findViewById(R.id.barcode);
        TFquantity = findViewById(R.id.quantity);

    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.B_order_medicine: orderMedicine(); break;
        }

    }

    public void orderMedicine()
    {
        Log.d("requestpickup", "I am here");
        medicine_name = TFmedicine_name.getText().toString();
        barcode = TFbarcode.getText().toString();
        quantity = Integer.parseInt(TFquantity.getText().toString());


        ApiInterface apiService = ApiClient.getStringClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.orderMedicineForNGO(email_id,medicine_name,barcode,quantity);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(),"Order Placed Successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderMedicineActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


            }
        });

    }

}
