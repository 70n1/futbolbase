package com.el70n1.futbolbase;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by el70n on 06/12/2016.
 */

public final class Conexiones {
    public String base_url = "http://www.ffcv.es/ncompeticiones/";
    public String base_script = "server.php?action=";
    private static final String newLine  = System.getProperty("line.separator");

    public JSONArray getResultados(String cmp, String jor, String tmp){
        String action = "getResultados";
        String myurl = base_url + base_script + action + "&cmp=" + cmp + "&jor=" + jor + "&tmp=" +tmp;
        return getJsonArray(getUrl(myurl));

    }

    public JSONArray getClasificacion(String cmp, String jor, String tmp) {
        String action = "getClasificacion";
        String myurl = base_url + base_script + action + "&cmp=" + cmp + "&jor=" + jor + "&tmp=" +tmp;
        return getJsonArray(getUrl(myurl));
    }

    public String getUrl (String myurl) {
        InputStream is = null;
        String salida = "";
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            //Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            salida = readStream(is);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (ProtocolException e1) {
            e1.printStackTrace();
            Log.e("GETUrl Error protocolo", "Error http " + e1.toString());
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            Log.e("GETUrl URL Mal formada", "Error http " + e1.toString());
        } catch (IOException e1) {
            e1.printStackTrace();
            Log.e("GETUrl IOException", "Error http " + e1.toString());
        } finally {
            if (is != null) {
                //is.close();
            }
        }
        return salida;
    }

    public JSONObject getJson(String texto){
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(texto);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // return JSON String
        return jObj;
    }


    public JSONArray getJsonArray(String texto){
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(texto);
        } catch (JSONException e) {
            Log.e("JSON Array Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jArray;
    }

    /**
    public String getResultados(String cmp,String jor, String tmp) throws IOException {




    }
 **/
    @NonNull
    private static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine + newLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
