package com.example.tugasbesar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Gaji extends AppCompatActivity {
 Spinner spinkeluarga,spinstatus;
 Button submit;
 EditText idpegawai,nama,gaji_pokok,keluarga;
 TextView output;
 double tunjangan_keluarga=0.0;
 double gaji=0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaji);
        spinkeluarga = (Spinner) findViewById(R.id.spin1);
        spinstatus = (Spinner) findViewById(R.id.spinstatus);
        idpegawai = (EditText) findViewById(R.id.edtpegawai);
        nama = (EditText) findViewById(R.id.edtnama);
        gaji_pokok = (EditText) findViewById(R.id.edtgaji);
        keluarga=(EditText)findViewById(R.id.edttkel) ;
        submit = (Button) findViewById(R.id.btnproses);
        Spin_status();
        spinStatusSelected();
        status();
        Spinner_Tkel();
        Spinner_TkelSelected();
        TunjanganKel();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaksi();
            }
        });
    }



    private void Spin_status() {
            spinstatus = (Spinner) findViewById(R.id.spinstatus);
            List<String> ListStts = new ArrayList<String>();
            ListStts.add("=====Status Pegawai=====");
            ListStts.add("Pegawai Negeri Sipil(PNS)");
            ListStts.add("Pegawai Kontrak");
            ListStts.add("Pegawai Honorer");
            ArrayAdapter<String> Status = new ArrayAdapter<String>(Gaji.this, android.R.layout.simple_spinner_item, ListStts);
            Status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinstatus.setAdapter(Status);
        }
    private void spinStatusSelected() {
        spinstatus=(Spinner) findViewById(R.id.spinstatus);
        spinstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Pegawai Negeri Sipil(PNS)")) {
                    spinstatus.setEnabled(true);
                    spinstatus.setClickable(true);
                } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Pegawai Kontrak")){
                    spinstatus.setEnabled(true);
                spinstatus.setClickable(true);
            } else {
                spinstatus.setEnabled(false);
                spinstatus.setClickable(false);
            }
        }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void status() {
        spinstatus=(Spinner)findViewById(R.id.spinstatus);
        gaji_pokok=(EditText)findViewById(R.id.edtgaji);
        spinstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()){
                    case "Pegawai Negeri Sipil(PNS)" :
                        gaji =4000000;
                        break;
                    case "Pegawai Kontrak" :
                        gaji=2500000;
                        break;
                    case "Pegawai Honorer":
                        gaji=1500000 ;
                        break;
                }
                gaji_pokok.setText("Rp."+gaji);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void Spinner_Tkel(){
        spinkeluarga=(Spinner) findViewById(R.id.spin1);
            List<String> Listkel = new ArrayList<String>();
            Listkel.add("===Kategori Tunjangan===");
            Listkel.add("AK1");
            Listkel.add("AK2");
            Listkel.add("AK3");
            Listkel.add("AK4");
            ArrayAdapter<String> Tkel = new ArrayAdapter<String>(Gaji.this, android.R.layout.simple_spinner_item, Listkel);
            Tkel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinkeluarga.setAdapter(Tkel);


        }
   private void Spinner_TkelSelected(){
        spinkeluarga=(Spinner)findViewById(R.id.spin1);
        spinkeluarga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("AK1")) {
                    spinkeluarga.setEnabled(true);
                    spinkeluarga.setClickable(true);
                } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("AK2")) {
                    spinkeluarga.setEnabled(true);
                    spinkeluarga.setClickable(true);
                } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("AK3")) {
                    spinkeluarga.setEnabled(true);
                    spinkeluarga.setClickable(true);
                } else {
                    spinkeluarga.setEnabled(false);
                    spinkeluarga.setClickable(false);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
   }
   private void TunjanganKel(){
        spinkeluarga=(Spinner)findViewById(R.id.spin1);
        keluarga=(EditText)findViewById(R.id.edttkel);
        spinkeluarga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()){
                    case "AK1" :
                        tunjangan_keluarga=800000;
                        break;
                    case "AK2" :
                        tunjangan_keluarga=1200000;
                        break;
                    case "AK3" :
                        tunjangan_keluarga=1600000;
                        break;
                    case "AK4" :
                        tunjangan_keluarga=2000000;
                        break;
                    default:
                        tunjangan_keluarga= 0.0;
                }
                keluarga.setText("Rp."+tunjangan_keluarga);
                }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
   }
   private void transaksi() {
       output = (TextView) findViewById(R.id.txtoutput);


       double totalgaji= gaji +tunjangan_keluarga ;
       output.setText("ID Pegawai :" + idpegawai.getText().toString() +
               "\nNama Pegawai :" + nama.getText().toString() +
               "\nStatus Kepegawaian :" + spinstatus.getSelectedItem().toString() +
               "\nGaji Pokok :" + gaji_pokok.getText().toString() +
               "\nKategori Tunjangan :" + spinkeluarga.getSelectedItem().toString() +
               "\nTunjangan Keluarga :" + keluarga.getText().toString()+
               "\nGaji Total :"+String.format("%.0f",totalgaji));

   }
}
