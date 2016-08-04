package com.example.pc_.permisos;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permisos, REQUEST_CODE);
            }
        } else {
            readSMS();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readSMS();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readSMS();
            } else {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Permisos invalidos")
                        .setMessage("No se pueden leer los mensajes por que hacen falta permisos")
                        .setPositiveButton("Autorizar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(permissions, REQUEST_CODE);
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        }
    }

    private void readSMS() {
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            //TODO
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String columnName = cursor.getColumnName(i);
                Log.e("Column", columnName);
            }

            for (int i = 0; i < cursor.getCount(); i++) {
                String address =
                        cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String body =
                        cursor.getString(cursor.getColumnIndexOrThrow("body"));
                txtSMS.append("\n" + address + " : " + body);
                cursor.moveToNext();
            }

        } else {
            cursor.close();
        }
    }
}
