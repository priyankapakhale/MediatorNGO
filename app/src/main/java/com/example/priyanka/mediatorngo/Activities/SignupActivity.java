package com.example.priyanka.mediatorngo.Activities;

/**
 * Created by priyanka on 1/31/18.
 */


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priyanka.mediatorngo.Models.NGO;
import com.example.priyanka.mediatorngo.Network.ApiClient;
import com.example.priyanka.mediatorngo.Network.ApiInterface;
import com.example.priyanka.mediatorngo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private FirebaseAuth mAuth;

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.contact_no) EditText _contactNoText;
    @InjectView(R.id.address) EditText _addressText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;
    @InjectView(R.id.area) EditText _areaText;
    @InjectView(R.id.registration_no) EditText _registrationNoText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }



    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String contact_no = _contactNoText.getText().toString();
        String address = _addressText.getText().toString();
        String area = _areaText.getText().toString();
        String registration_no = _registrationNoText.getText().toString();

        // TODO: Implement your own signup logic here.

        NGO v = new NGO();
        v.setAddress(address);
        v.setArea(area);
        v.setContactNo(contact_no);
        v.setEmailId(email);
        v.setName(name);
        v.setPassword(password);
        v.setRegistrationNo(registration_no);

        //Store data on Server
        createNGO(v);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }



    public void createNGO(final NGO v)
    {

        ApiInterface apiService = ApiClient.getStringClient().create(ApiInterface.class);
        Call <ResponseBody> call = apiService.addNGO(v.getName(),v.getContactNo(), v.getAddress(),v.getArea(),v.getRegistrationNo(),v.getEmailId(),v.getPassword());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("response",response.toString());
                authenticate(v);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Failure",t.getMessage());
            }
        });




    }

    public void authenticate(NGO v)
    {
        mAuth.createUserWithEmailAndPassword(v.getEmailId(), v.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("message");

                            myRef.setValue("Hello, World!");

                            Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });

    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}