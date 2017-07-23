package com.example.rodrigovazquez.accountmanager.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Helper para validad la conexion a internet
 */

public class ConnectionHelper {

    /**
     * Metodo para verificar la conexion a internet
     * @param mContext contexto del que es llamado
     * @return 1 o 0
     */
    public static boolean isConnected(Context mContext) {

        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            Log.e("ConnectionHelper", "Available");
            return true;
        }
        Log.e("ConnectionHelper", "Not available");
        return false;
    }
}
