package com.mike4christ.tisvdigital.fcm;

import android.util.Log;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class FcmNotificationBuilder {

    private static final String APPLICATION_JSON = "application/json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String AUTH_KEY = "key=AAAAta0Qxyg:APA91bHo-fDDNHtQfHsMAr-CvC4afYgxB7Wb5TL-Ir5G8PgSlsN5iTTX0-cir-sdUgelpb_RohR2pfCR5y9jMxE54Uj87ge3GAHJvmKHlc9EBUiaq0WbGq9LfOVwFLZwliZYHayzcmM3";
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
    private static final String SERVER_API_KEY = "AAAAta0Qxyg:APA91bHo-fDDNHtQfHsMAr-CvC4afYgxB7Wb5TL-Ir5G8PgSlsN5iTTX0-cir-sdUgelpb_RohR2pfCR5y9jMxE54Uj87ge3GAHJvmKHlc9EBUiaq0WbGq9LfOVwFLZwliZYHayzcmM3";
    private static final String TAG = "FcmNotificationBuilder";
    private String mEmail;
    private String mFirebaseToken;
    private String mFirstname;
    private String mLastname;
    private String mLink;
    private String mMessage;
    private String mReceiverFirebaseToken;
    private String mTitle;


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


        Request request = new Request.Builder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .addHeader(AUTHORIZATION, AUTH_KEY)
                .url(FCM_URL)
                .post(requestBody)
                .build();

        Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onGetAllUsersFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "onResponse: " + response.body().string());
            }
        });

    }

    private JSONObject getValidJsonBody() throws JSONException {
        JSONObject jsonObjectBody = new JSONObject();
        jsonObjectBody.put(KEY_TO, mReceiverFirebaseToken);
        //Data
        JSONObject jsonObjectData = new JSONObject();
        jsonObjectData.put("title", mTitle);
        jsonObjectData.put(KEY_TEXT, mMessage);
        jsonObjectData.put("email", mEmail);
        jsonObjectData.put(KEY_LINK, mLink);
        jsonObjectData.put(KEY_FCM_TOKEN, mFirebaseToken);
        jsonObjectBody.put(KEY_DATA, jsonObjectData);
        return jsonObjectBody;
    }
}
