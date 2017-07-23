package com.example.rodrigovazquez.accountmanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodrigovazquez.accountmanager.Helpers.AbstractGetNameTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Home
 */
public class MainActivity extends AppCompatActivity {

    private TextView name, email, gender;
    private ImageView imageProfile;
    String textName, textEmail, textGender, textBirthday, userImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageProfile = (ImageView) findViewById(R.id.profileImage);
        name = (TextView) findViewById(R.id.txtNameValue);
        email = (TextView) findViewById(R.id.txtEmailValue);
        gender = (TextView) findViewById(R.id.txtGenderValue);

        //Obtenemos el email del usuario
        //atravez del intent que inicia la actividad
        Intent intent = getIntent();
        textEmail = intent.getStringExtra("email_id");
        System.out.println("Email " + textEmail);
        email.setText(textEmail);

        /**
         * Obtenemos la informacion de la cuenta de google
         */

        try {
            System.out.println("On Home Page***"
                    + AbstractGetNameTask.GOOGLE_USER_DATA);
            JSONObject profileData = new JSONObject(
                    AbstractGetNameTask.GOOGLE_USER_DATA);

            if (profileData.has("picture")) {
                userImageUrl = profileData.getString("picture");
                new GetImageFromUrl().execute(userImageUrl);
            }
            if (profileData.has("name")) {
                textName = profileData.getString("name");
                name.setText(textName);
            }
            if (profileData.has("gender")) {
                textGender = profileData.getString("gender");
                gender.setText(textGender);
            }
            if (profileData.has("birthday")) {
                textBirthday = profileData.getString("birthday");
                email.setText(textBirthday);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            imageProfile.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }


}
