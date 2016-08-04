package com.example.pc_.permisos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnSMS;
    TextView txtSMS;
    String[] permisos = {Manifest.permission.READ_SMS};
    int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSMS = (Button) findViewById(R.id.main_btn_sms);
        txtSMS = (TextView) findViewById(R.id.main_txt_sms);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //Si la version del SDK del telefono es mayor o igual que M has lo que este adentro
            if (checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {

            }
        }

    }
}
