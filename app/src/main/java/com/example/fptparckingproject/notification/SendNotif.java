package com.example.fptparckingproject.notification;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendNotif {
    public void sendMessage(final String title, final String body, final String token) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONObject notification = new JSONObject();
                    notification.put("body", body);
                    notification.put("title", title);

                    JSONObject data = new JSONObject();
                    root.put("notification", notification);
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
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private String postToFCM(String bodyString) throws IOException {
        API_Service api_service = new API_Service();
        OkHttpClient mClient = new OkHttpClient();
        RequestBody body = RequestBody.create(api_service.getJSON(), bodyString);
        Request request = new Request.Builder()
                .url(api_service.getFCM_MESSAGE_URL())
                .post(body)
                .addHeader("Authorization", api_service.getSERVER_KEY())
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
}
