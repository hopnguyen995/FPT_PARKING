package com.example.fptparkingproject.notification;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendNotif {
    private int success;

    public int sendMessage(final String title, final String body, final String token, final String tagToken,final String code,final String time) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject data = new JSONObject();
                    data.put("body", body);
                    data.put("title", title);
                    data.put("sendtoken", tagToken);
                    data.put("token",token);
                    data.put("code",code);
                    data.put("time",time);
                    JSONObject root = new JSONObject();
                    root.put("data", data);
                    root.put("to", token);

                    String result = postToFCM(root.toString());
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    success = resultJson.getInt("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
        return success;
    }

    private String postToFCM(String bodyString) throws IOException {
        Notif notif = new Notif();
        OkHttpClient mClient = new OkHttpClient();
        RequestBody body = RequestBody.create(notif.getJSON(), bodyString);
        Request request = new Request.Builder()
                .url(notif.getFCM_MESSAGE_URL())
                .post(body)
                .addHeader("Authorization", notif.getSERVER_KEY())
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
}
