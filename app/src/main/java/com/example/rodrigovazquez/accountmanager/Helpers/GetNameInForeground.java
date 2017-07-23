package com.example.rodrigovazquez.accountmanager.Helpers;

import com.example.rodrigovazquez.accountmanager.Views.SplashActivity;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

/**
 * Fetch token
 * com.example.rodrigovazquez.accountmanager
 * 72:27:D9:18:9C:77:28:C6:64:94:05:B8:3C:E7:13:EA:E7:01:54:0D
 */

public class GetNameInForeground extends AbstractGetNameTask {

    /**
     * @param mActivity
     * @param mEmail
     * @param mScope
     */
    public GetNameInForeground(SplashActivity mActivity, String mEmail, String mScope) {
        super(mActivity, mEmail, mScope);
    }

    /**
     * Obtenemos el token de autenticacion
     *
     * @return
     * @throws IOException
     */
    @Override
    protected String fetchToken() throws IOException {
        try {
            return GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
        } catch (GooglePlayServicesAvailabilityException ex) {
            onError(ex.getMessage(), ex);
        } catch (UserRecoverableAuthException ex) {
            mActivity.startActivityForResult(ex.getIntent(), mRequestCode);
        } catch (GoogleAuthException ex) {
            onError(ex.getMessage(), ex);
        }
        return null;
    }
}
