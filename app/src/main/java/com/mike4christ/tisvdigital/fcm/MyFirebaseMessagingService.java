package com.mike4christ.tisvdigital.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mike4christ.tisvdigital.ChatActivity;
import com.mike4christ.tisvdigital.Constant;
import com.mike4christ.tisvdigital.R;
import com.mike4christ.tisvdigital.TisvChatApp;
import com.mike4christ.tisvdigital.events.PushNotificationEvent;
import com.mike4christ.tisvdigital.users.getall.SharedPrefUtil;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onNewToken(String token) {
        String string_token = "Refreshed token: " + token;
        Log.d(TAG, string_token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(final String token) {
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getApplicationContext());
        String str = Constant.ARG_FIREBASE_TOKEN;
        sharedPrefUtil.saveString(str, token);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseDatabase.getInstance().getReference().child(Constant.ARG_USERS)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                    .child(str)
                    .setValue(token);
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            String title = remoteMessage.getData().get(Constant.POST_TILE);
            String message = remoteMessage.getData().get("text");
            String firstname = remoteMessage.getData().get("firstname");
            String lastname =  remoteMessage.getData().get("lastname");
            String email = remoteMessage.getData().get("email");
            String link = remoteMessage.getData().get("link");
            String fcmToken = remoteMessage.getData().get("fcm_token");

            if (!TisvChatApp.isChatActivityOpen()) {

                sendNotification(title,
                        message,
                        firstname,
                        lastname,
                        email,
                        link,
                        fcmToken);

            }else {
                new PushNotificationEvent(title,
                        message,
                        firstname,
                        lastname,
                        email,
                        link,
                        fcmToken);
            }


        }
    }

    private void sendNotification(String title, String message, String receiver_firstname, String receiver_lastname, String receiverEmail, String receiverLink, String firebaseToken) {

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(Constant.ARG_RECEIVER_FIRST, receiver_firstname);
        intent.putExtra(Constant.ARG_RECEIVER_LASTNAME, receiver_lastname);
        intent.putExtra(Constant.ARG_RECEIVER_EMAIL, receiverEmail);
        intent.putExtra(Constant.ARG_RECEIVER_LINK, receiverLink);
        intent.putExtra(Constant.ARG_FIREBASE_TOKEN, firebaseToken);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"channel_id")
                .setSmallIcon(R.drawable.ic_chat_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());


    }
}
