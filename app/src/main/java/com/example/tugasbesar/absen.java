package com.example.tugasbesar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class absen extends AppCompatActivity  {
    private TimePicker timePicker;
    private TextView Output;
    private Button SetChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen);
        timePicker = findViewById(R.id.get_time);
        Output = findViewById(R.id.output);
        SetChange = findViewById(R.id.set_waktu);
        SetChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mencetak Output
                Output.setText(setTime());
                Toast.makeText(getApplicationContext(), "Berhasil Diubah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String setTime() {
        String waktu = "Waktu : " + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();
        return waktu;
    }
}



