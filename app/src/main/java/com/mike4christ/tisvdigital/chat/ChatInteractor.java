package com.mike4christ.tisvdigital.chat;

import android.content.Context;
import android.util.Log;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mike4christ.tisvdigital.Constant;
import com.mike4christ.tisvdigital.chat.ChatContract.Interactor;
import com.mike4christ.tisvdigital.chat.ChatContract.OnGetMessagesListener;
import com.mike4christ.tisvdigital.chat.ChatContract.OnSendMessageListener;
import com.mike4christ.tisvdigital.fcm.FcmNotificationBuilder;
import com.mike4christ.tisvdigital.model.Chat;
import com.mike4christ.tisvdigital.users.getall.SharedPrefUtil;

public class ChatInteractor implements Interactor {
    private static final String TAG = "ChatInteractor";
    private OnGetMessagesListener mOnGetMessagesListener;
    private OnSendMessageListener mOnSendMessageListener;

    public ChatInteractor(OnSendMessageListener onSendMessageListener) {
        this.mOnSendMessageListener = onSendMessageListener;
    }

    public ChatInteractor(OnGetMessagesListener onGetMessagesListener) {
        this.mOnGetMessagesListener = onGetMessagesListener;
    }

    public ChatInteractor(OnSendMessageListener onSendMessageListener, OnGetMessagesListener onGetMessagesListener) {
        this.mOnSendMessageListener = onSendMessageListener;
        this.mOnGetMessagesListener = onGetMessagesListener;
    }

    public void sendMessageToFirebaseUser(Context context, Chat chat, String receiverFirebaseToken) {

        String senderEmail= chat.senderEmail.replace(".",",");
        String receiverEmail= chat.receiverEmail.replace(".",",");
        String room_type_1=senderEmail +"_"+receiverEmail;
        String room_type_2=receiverEmail +"_"+senderEmail;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final String str2 = room_type_1;
        final DatabaseReference databaseReference2 = databaseReference;
        final Chat chat3 = chat;
        final String str3 = room_type_2;
        final Context context2 = context;
        final String str4 = receiverFirebaseToken;
        databaseReference.child(Constant.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean hasChild = dataSnapshot.hasChild(str2);
                String str = " exists";
                String str2 = "sendMessageToFirebaseUser: ";
                String str3 = Constant.ARG_CHAT_ROOMS;
                String str4 = ChatInteractor.TAG;
                StringBuilder stringBuilder;
                if (hasChild) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str2);
                    stringBuilder.append(str2);
                    stringBuilder.append(str);
                    Log.e(str4, stringBuilder.toString());
                    databaseReference2.child(str3).child(str2).child(String.valueOf(chat3.timestamp)).setValue(chat3);
                } else if (dataSnapshot.hasChild(str3)) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str2);
                    stringBuilder.append(str3);
                    stringBuilder.append(str);
                    Log.e(str4, stringBuilder.toString());
                    databaseReference2.child(str3).child(str3).child(String.valueOf(chat3.timestamp)).setValue(chat3);
                } else {
                    Log.e(str4, "sendMessageToFirebaseUser: success");
                    databaseReference2.child(str3).child(str2).child(String.valueOf(chat3.timestamp)).setValue(chat3);
                    ChatInteractor.this.getMessageFromFirebaseUser(chat3.senderEmail, chat3.receiverEmail);
                }
                ChatInteractor.this.sendPushNotificationToReceiver(chat3.message, chat3.senderEmail, new SharedPrefUtil(context2).getString(Constant.ARG_FIREBASE_TOKEN), str4);
                ChatInteractor.this.mOnSendMessageListener.onSendMessageSuccess();
            }

            public void onCancelled(DatabaseError databaseError) {
                OnSendMessageListener access$100 = ChatInteractor.this.mOnSendMessageListener;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to send message: ");
                stringBuilder.append(databaseError.getMessage());
                access$100.onSendMessageFailure(stringBuilder.toString());
            }
        });
    }

    private void sendPushNotificationToReceiver(String message, String email, String firebaseToken, String receiverFirebaseToken) {
        FcmNotificationBuilder.initialize().title("tisv.digital").message(message).email(email).firebaseToken(firebaseToken).receiverFirebaseToken(receiverFirebaseToken).send();
    }

    public void getMessageFromFirebaseUser(String senderEmail, String receiverEmail) {
        CharSequence charSequence = ",";
        CharSequence charSequence2 = ".";
        senderEmail = senderEmail.replace(charSequence2, charSequence);
        receiverEmail = receiverEmail.replace(charSequence2, charSequence);
        StringBuilder room_type_1 = new StringBuilder();
        room_type_1.append(senderEmail);
        String str = "_";
        room_type_1.append(str);
        room_type_1.append(receiverEmail);
        room_type_1 = room_type_1.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(receiverEmail);
        stringBuilder.append(str);
        stringBuilder.append(senderEmail);
        str = stringBuilder.toString();
        FirebaseDatabase.getInstance().getReference().child(Constant.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {

            /* renamed from: com.mike4christ.tisvdigital.chat.ChatInteractor$2$1 */
            class C08101 implements ChildEventListener {
                C08101() {
                }

                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatInteractor.this.mOnGetMessagesListener.onGetMessagesSuccess((Chat) dataSnapshot.getValue(Chat.class));
                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                public void onCancelled(DatabaseError databaseError) {
                    OnGetMessagesListener access$200 = ChatInteractor.this.mOnGetMessagesListener;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to get message: ");
                    stringBuilder.append(databaseError.getMessage());
                    access$200.onGetMessagesFailure(stringBuilder.toString());
                }
            }

            /* renamed from: com.mike4christ.tisvdigital.chat.ChatInteractor$2$2 */
            class C08112 implements ChildEventListener {
                C08112() {
                }

                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatInteractor.this.mOnGetMessagesListener.onGetMessagesSuccess((Chat) dataSnapshot.getValue(Chat.class));
                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                public void onCancelled(DatabaseError databaseError) {
                    OnGetMessagesListener access$200 = ChatInteractor.this.mOnGetMessagesListener;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to get message: ");
                    stringBuilder.append(databaseError.getMessage());
                    access$200.onGetMessagesFailure(stringBuilder.toString());
                }
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean hasChild = dataSnapshot.hasChild(room_type_1);
                String str = Constant.ARG_CHAT_ROOMS;
                String str2 = " exists";
                String str3 = "getMessageFromFirebaseUser: ";
                String str4 = ChatInteractor.TAG;
                StringBuilder stringBuilder;
                if (hasChild) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str3);
                    stringBuilder.append(room_type_1);
                    stringBuilder.append(str2);
                    Log.e(str4, stringBuilder.toString());
                    FirebaseDatabase.getInstance().getReference().child(str).child(room_type_1).addChildEventListener(new C08101());
                } else if (dataSnapshot.hasChild(str)) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(str3);
                    stringBuilder.append(str);
                    stringBuilder.append(str2);
                    Log.e(str4, stringBuilder.toString());
                    FirebaseDatabase.getInstance().getReference().child(str).child(str).addChildEventListener(new C08112());
                } else {
                    Log.e(str4, "getMessageFromFirebaseUser: no such room available");
                }
            }

            public void onCancelled(DatabaseError databaseError) {
                OnGetMessagesListener access$200 = ChatInteractor.this.mOnGetMessagesListener;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to get message: ");
                stringBuilder.append(databaseError.getMessage());
                access$200.onGetMessagesFailure(stringBuilder.toString());
            }
        });
    }
}
