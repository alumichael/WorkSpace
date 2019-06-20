package com.mike4christ.tisvdigital.fcm;

import android.util.Log;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class FcmNotificationBuilder {
    private static final String APPLICATION_JSON = "application/json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String AUTH_KEY = "key=YOUR_SERVER_API_KEY";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String KEY_DATA = "data";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_FCM_TOKEN = "fcm_token";
    private static final String KEY_FirstNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_LINK = "link";
    private static final String KEY_NOTIFICATION = "notification";
    private static final String KEY_TEXT = "text";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TO = "to";
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String SERVER_API_KEY = "YOUR_SERVER_API_KEY";
    private static final String TAG = "FcmNotificationBuilder";
    private String mEmail;
    private String mFirebaseToken;
    private String mFirstname;
    private String mLastname;
    private String mLink;
    private String mMessage;
    private String mReceiverFirebaseToken;
    private String mTitle;

    /* renamed from: com.mike4christ.tisvdigital.fcm.FcmNotificationBuilder$1 */
    class C08131 implements Callback {
        C08131() {
        }

        public void onFailure(Call call, IOException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onGetAllUsersFailure: ");
            stringBuilder.append(e.getMessage());
            Log.e(FcmNotificationBuilder.TAG, stringBuilder.toString());
        }

        public void onResponse(Call call, Response response) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onResponse: ");
            stringBuilder.append(response.body().string());
            Log.e(FcmNotificationBuilder.TAG, stringBuilder.toString());
        }
    }

    private FcmNotificationBuilder() {
    }

    public static FcmNotificationBuilder initialize() {
        return new FcmNotificationBuilder();
    }

    public FcmNotificationBuilder title(String title) {
        this.mTitle = title;
        return this;
    }

    public FcmNotificationBuilder message(String message) {
        this.mMessage = message;
        return this;
    }

    public FcmNotificationBuilder firstname(String firstname) {
        this.mFirstname = firstname;
        return this;
    }

    public FcmNotificationBuilder lastname(String lastname) {
        this.mLastname = lastname;
        return this;
    }

    public FcmNotificationBuilder email(String email) {
        this.mEmail = email;
        return this;
    }

    public FcmNotificationBuilder link(String link) {
        this.mLink = link;
        return this;
    }

    public FcmNotificationBuilder firebaseToken(String firebaseToken) {
        this.mFirebaseToken = firebaseToken;
        return this;
    }

    public FcmNotificationBuilder receiverFirebaseToken(String receiverFirebaseToken) {
        this.mReceiverFirebaseToken = receiverFirebaseToken;
        return this;
    }

    public void send() {
        RequestBody requestBody = null;
        try {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, getValidJsonBody().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new OkHttpClient().newCall(new Builder().addHeader(CONTENT_TYPE, APPLICATION_JSON).addHeader(AUTHORIZATION, AUTH_KEY).url(FCM_URL).post(requestBody).build()).enqueue(new C08131());
    }

    private JSONObject getValidJsonBody() throws JSONException {
        JSONObject jsonObjectBody = new JSONObject();
        jsonObjectBody.put(KEY_TO, this.mReceiverFirebaseToken);
        JSONObject jsonObjectData = new JSONObject();
        jsonObjectData.put("title", this.mTitle);
        jsonObjectData.put(KEY_TEXT, this.mMessage);
        jsonObjectData.put("email", this.mEmail);
        jsonObjectData.put(KEY_LINK, this.mLink);
        jsonObjectData.put(KEY_FCM_TOKEN, this.mFirebaseToken);
        jsonObjectBody.put(KEY_DATA, jsonObjectData);
        return jsonObjectBody;
    }
}
