package com.example.tugasbesar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    EditText username, email, password, name;
    Button insert;
    RequestQueue requestQueue;
    String nameUser, emailUser, passUser, fullName;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        username = findViewById(R.id.edit_username);
        password = findViewById(R.id.edit_password);
        email = findViewById(R.id.edit_email);
        name = findViewById(R.id.edit_nama);
        insert = findViewById(R.id.btn_signup);
        requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        progressDialog = new ProgressDialog(SignUpActivity.this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String URL_SIGNUP = "https://192.168.13.27/ServerTugasBesar/signup.php";
                progressDialog.setMessage("Silakan tunggu, mendaftarkan akun");
                progressDialog.show();
                nameUser = username.getText().toString().trim();
                emailUser = email.getText().toString().trim();
                passUser = password.getText().toString().trim();
                fullName = name.getText().toString().trim();

                StringRequest sr = new StringRequest(Request.Method.POST, URL_SIGNUP, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_LONG).show();
                        if (response.equalsIgnoreCase("Sign up berhasil!")){
                            startActivity(new Intent(SignUpActivity.this, Login.class));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        clearData();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", nameUser);
                        params.put("email", emailUser);
                        params.put("password", passUser);
                        params.put("name", fullName);
                        return params;
                    }
                };
                requestQueue.add(sr);
            }
        });
    }

    private void clearData() {
        username.setText("");
        email.setText("");
        password.setText("");
        name.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUpActivity.this, Login.class));
    }
}

