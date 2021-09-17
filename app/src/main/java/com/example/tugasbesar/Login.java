package com.example.tugasbesar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {
    EditText user, pass;
    TextView daftar;
    Button login;
    RequestQueue requestQueue;
    String nameUser, passUser;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        daftar = findViewById(R.id.txtDaftar);
        login = findViewById(R.id.login);
        requestQueue = Volley.newRequestQueue(Login.this);
        progressDialog = new ProgressDialog(Login.this);
        getSupportActionBar().hide();

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUpActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String URL_SIGNIN = "https://192.168.13.27/ServerTugasBesar/login.php";
                progressDialog.setMessage("Silakan tunggu, cek data ke server sedang berlangsung");
                progressDialog.show();
                nameUser = user.getText().toString().trim();
                passUser = pass.getText().toString().trim();

                StringRequest sr = new StringRequest(Request.Method.POST, URL_SIGNIN, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, response, Toast.LENGTH_LONG).show();
                        if (response.equalsIgnoreCase("Login Berhasil!")){
                            Intent intent = new Intent(Login.this, Home.class);
                            intent.putExtra("name", user.getText().toString());
                            startActivity(intent);
                            nameUser = "";
                            passUser = "";
                        } else{
                            clearData();
                            startActivity(new Intent(Login.this, Login.class));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", nameUser);
                        params.put("password", passUser);
                        return params;
                    }
                };
                requestQueue.add(sr);
            }
        });
    }

    private void clearData() {
        user.setText("");
        pass.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Login.this, SplashScreen.class));
    }

}

