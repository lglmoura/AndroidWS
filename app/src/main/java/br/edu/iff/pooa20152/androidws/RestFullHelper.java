package br.edu.iff.pooa20152.androidws;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by lglmoura on 08/04/16.
 */
public class RestFullHelper {

    public static String POST = "POST";
    public static String PUT = "PUT";
    public static String DELETAR = "DELETE";
    public static String GET = "GET";
    public final int TIMEOUT_MILLIS = 15000;
    private final String TAG = "Http";
    public boolean LOG_ON = false;
    private String contentType;
    private String charsetToEncode;


    public JSONObject doGet(String url) throws IOException {
        return doGet(url, "UTF-8");
    }

    public JSONObject doGet(String url, String charset) {

        JSONObject jObj = null;


        if (LOG_ON) {
            Log.d(TAG, ">> Http.doGet: " + url);
        }


        HttpURLConnection conn = null;
        String s = null;
        String json = null;
        try {
            conn = (HttpURLConnection) getConnection(url, GET);
            conn.connect();

            InputStream in = null;

            int status = conn.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d(TAG, "Error code: " + status);
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }


            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            json = buffering(reader);

            if (LOG_ON) {
                Log.d(TAG, "<< Http.doGet: " + json);
            } else {
                System.out.println(">> Http.doGet:json " + json);
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
            ;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {

            Log.e("JSON Parser", "Error parsing data TEST " + e.toString());
        }

        if (LOG_ON) {
            Log.d(TAG, "<< Http.doGet: " + s);
        }

        return jObj;
    }

    public String buffering(BufferedReader reader){
        StringBuilder sb = new StringBuilder();
        try {

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }


        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return sb.toString();
    }

    public HttpURLConnection getConnection(String endPoint, String method) {
        return getConnection(endPoint, method, null);
    }

    public HttpURLConnection getConnection(String endPoint, String method, String contentType) {

        System.out.println("ENDPOINT " + endPoint + " METHOD " + method);

        HttpURLConnection conn = null;

        try {
            URL url = new URL(endPoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (contentType != null)
                conn.setRequestProperty("Content-Type", contentType);
            else
                conn.setRequestProperty("Content-Type", "text/plain");

            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setReadTimeout(TIMEOUT_MILLIS);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return conn;
    }



}