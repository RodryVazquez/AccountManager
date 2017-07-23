package com.example.rodrigovazquez.accountmanager.Views;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rodrigovazquez.accountmanager.Helpers.AbstractGetNameTask;
import com.example.rodrigovazquez.accountmanager.Helpers.ConnectionHelper;
import com.example.rodrigovazquez.accountmanager.Helpers.GetNameInForeground;
import com.example.rodrigovazquez.accountmanager.R;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.drive.events.ProgressEvent;

public class SplashActivity extends AppCompatActivity {
    
    Context mContext = SplashActivity.this;
    AccountManager accountManager;
    String token;
    int serverCode;
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";

    ProgressBar progressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar)findViewById(R.id.waitProgress);
        syncGoogleAccount();
    }

    /**
     * 
     * @return
     */
    private String[] getAccountNames(){
        accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = accounts[i].name;
        }
        return names;
    }

    /**
     *
     * @param activity
     * @param email
     * @param scope
     * @return
     */
    private AbstractGetNameTask getTask(SplashActivity activity, String email, String scope){
        return new GetNameInForeground(activity, email, scope);
    }

    /**
     *
     */
    public void syncGoogleAccount(){
        if(ConnectionHelper.isConnected(this)){
            String[] accounts = getAccountNames();
            if(accounts.length > 0){
                getTask(SplashActivity.this, accounts[0], SCOPE).execute();
            }else{
                setProgressVisibility(false);
                Toast.makeText(mContext, "No Google Account Sync!", Toast.LENGTH_SHORT).show();
            }
        }else{
            setProgressVisibility(false);
            Toast.makeText(mContext, "Not Network Service !!!", Toast.LENGTH_SHORT).show();   
        }
    }

    public void setProgressVisibility(boolean flag){
        progressBar.setVisibility(flag ? View.VISIBLE : View.GONE);
    }
}
