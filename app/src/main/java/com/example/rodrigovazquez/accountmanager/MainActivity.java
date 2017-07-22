package com.example.rodrigovazquez.accountmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView name, email, gender;
    private ImageView imageProfile;
    String textName, textEmail, textGender, textBirthday, userImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageProfile = (ImageView)findViewById(R.id.profileImage);
        name = (TextView)findViewById(R.id.txtNameValue);
        email = (TextView)findViewById(R.id.txtEmailValue);
        gender = (TextView)findViewById(R.id.txtGenderValue);

        //Obtenemos el email del usuario
        //atravez del intent que inicia la actividad
        Intent intent = getIntent();
        textEmail = intent.getStringExtra("email_id");
        System.out.println("Email " + textEmail);
        email.setText(textEmail);
    }
}
