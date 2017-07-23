package com.example.rodrigovazquez.accountmanager.Helpers;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rodrigovazquez.accountmanager.MainActivity;
import com.example.rodrigovazquez.accountmanager.Views.SplashActivity;
import com.google.android.gms.auth.GoogleAuthUtil;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by RodryVazquez on 22/07/17.
 */

public abstract class AbstractGetNameTask extends AsyncTask<Void, Void, Void> {

    private static final String token_url = "https://www.googleapis.com/auth/v1/userinfo?access_token=";

    private static final String TAG = "TokenInfoTask";
    public static String GOOGLE_USER_DATA = "No_data";

    protected SplashActivity mActivity;

    protected String mScope;
    protected String mEmail;
    protected int mRequestCode;

    /**
     * @param mActivity
     * @param mEmail
     * @param mScope
     */
    AbstractGetNameTask(SplashActivity mActivity, String mEmail, String mScope) {
        this.mActivity = mActivity;
        this.mEmail = mEmail;
        this.mScope = mScope;
    }

    /**
     * @param voids
     * @return
     */
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            fetchNameFromProfileServer();
        } catch (IOException ex) {
            onError("Following error ocurred please try again " + ex.getMessage(), ex);
        } catch (JSONException ex) {
            onError("Bad response: " + ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * @param message
     * @param e
     */
    protected void onError(String message, Exception e) {
        if (e != null) {
            Log.e(TAG, "Exception:", e);
        }
    }

    /**
     * @return
     * @throws IOException
     */
    protected abstract String fetchToken() throws IOException;

    /**
     *
     */
    private void fetchNameFromProfileServer() throws IOException, JSONException {
        String token = fetchToken();
        URL url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        int sc = urlConnection.getResponseCode();
        if (sc == 200) {

            InputStream in = urlConnection.getInputStream();
            GOOGLE_USER_DATA = readResponse(in);
            in.close();

            Intent intent = new Intent(mActivity, MainActivity.class);
            intent.putExtra("email_id", mEmail);
            mActivity.startActivity(intent);
            mActivity.finish();

        } else if (sc == 401) {
            GoogleAuthUtil.invalidateToken(mActivity, token);
            onError("Server auth error, please try again.", null);
        } else {
            onError("Server returned the following error code: " + sc, null);
        }
    }

    /**
     * @param is
     * @return
     * @throws IOException
     */
    private static String readResponse(InputStream is) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[2048];
        int len = 0;
        while ((len = is.read(data, 0, data.length)) >= 0) {
            byteArrayOutputStream.write(data, 0, len);
        }
        return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
    }
}
