package com.example.fptparkingproject.notification;

import android.os.AsyncTask;

import com.example.fptparkingproject.constant.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendNotif {
    Constant constant = new Constant();

    public void sendMessage(final String title, final String body,final String image, final String token, final String sendToken, final String code, final String time) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject data = new JSONObject();
                    data.put(constant.JSON_KEY_BODY, body);
                    data.put(constant.JSON_KEY_TITLE, title);
                    data.put(constant.JSON_KEY_SENDTOKEN, sendToken);
                    data.put(constant.JSON_KEY_TOKEN, token);
                    data.put(constant.JSON_KEY_CODE, code);
                    data.put(constant.JSON_KEY_TIME, time);
                    data.put(constant.JSON_KEY_IMAGE, image);
                    JSONObject root = new JSONObject();
                    root.put(constant.JSON_KEY_DATA, data);
                    root.put(constant.JSON_KEY_TO, token);

                    String result = postToFCM(root.toString());
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    private String postToFCM(String bodyString) throws IOException {
        Notif notif = new Notif();
        OkHttpClient mClient = new OkHttpClient();
        RequestBody body = RequestBody.create(notif.getJSON(), bodyString);
        Request request = new Request.Builder()
                .url(notif.getFCM_MESSAGE_URL())
                .post(body)
                .addHeader(notif.getHEADER(), notif.getSERVER_KEY())
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
}
