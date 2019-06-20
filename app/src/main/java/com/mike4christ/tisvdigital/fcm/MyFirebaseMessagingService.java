package com.mike4christ.tisvdigital.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mike4christ.tisvdigital.C0506R;
import com.mike4christ.tisvdigital.ChatActivity;
import com.mike4christ.tisvdigital.Constant;
import com.mike4christ.tisvdigital.TisvChatApp;
import com.mike4christ.tisvdigital.events.PushNotificationEvent;
import com.mike4christ.tisvdigital.users.getall.SharedPrefUtil;
import org.greenrobot.eventbus.EventBus;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    public void onNewToken(String token) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Refreshed token: ");
        stringBuilder.append(token);
        Log.d(TAG, stringBuilder.toString());
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getApplicationContext());
        String str = Constant.ARG_FIREBASE_TOKEN;
        sharedPrefUtil.saveString(str, token);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseDatabase.getInstance().getReference().child(Constant.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getEmail()).child(str).setValue(token);
        }
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("From: ");
        stringBuilder.append(remoteMessage.getFrom());
        String stringBuilder2 = stringBuilder.toString();
        String str = TAG;
        Log.d(str, stringBuilder2);
        if (remoteMessage.getData().size() > 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Message data payload: ");
            stringBuilder.append(remoteMessage.getData());
            Log.d(str, stringBuilder.toString());
            stringBuilder2 = (String) remoteMessage.getData().get(Constant.POST_TILE);
            String message = (String) remoteMessage.getData().get("text");
            String firstname = (String) remoteMessage.getData().get("firstname");
            String lastname = (String) remoteMessage.getData().get("lastname");
            String email = (String) remoteMessage.getData().get("email");
            String link = (String) remoteMessage.getData().get("link");
            String fcmToken = (String) remoteMessage.getData().get("fcm_token");
            if (TisvChatApp.isChatActivityOpen()) {
                EventBus eventBus = EventBus.getDefault();
                PushNotificationEvent pushNotificationEvent = r1;
                PushNotificationEvent pushNotificationEvent2 = new PushNotificationEvent(stringBuilder2, message, firstname, lastname, email, link, fcmToken);
                eventBus.post(pushNotificationEvent);
                return;
            }
            sendNotification(stringBuilder2, message, firstname, lastname, email, link, fcmToken);
        }
    }

    private void sendNotification(String title, String message, String receiver_firstname, String receiver_lastname, String receiverEmail, String receiverLink, String firebaseToken) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(Constant.ARG_RECEIVER_FIRST, receiver_firstname);
        intent.putExtra(Constant.ARG_RECEIVER_LASTNAME, receiver_lastname);
        intent.putExtra(Constant.ARG_RECEIVER_EMAIL, receiverEmail);
        intent.putExtra(Constant.ARG_RECEIVER_LINK, receiverLink);
        intent.putExtra(Constant.ARG_FIREBASE_TOKEN, firebaseToken);
        intent.addFlags(67108864);
        ((NotificationManager) getSystemService("notification")).notify(0, new Builder(this).setSmallIcon(C0506R.drawable.ic_chat_black_24dp).setContentTitle(title).setContentText(message).setAutoCancel(true).setSound(RingtoneManager.getDefaultUri(2)).setContentIntent(PendingIntent.getActivity(this, 0, intent, 1073741824)).build());
    }
}
