package com.mike4christ.tisvdigital.chat;

import android.content.Context;
import com.mike4christ.tisvdigital.model.Chat;

public interface ChatContract {

    public interface Interactor {
        void getMessageFromFirebaseUser(String str, String str2);

        void sendMessageToFirebaseUser(Context context, Chat chat, String str);
    }

    public interface OnGetMessagesListener {
        void onGetMessagesFailure(String str);

        void onGetMessagesSuccess(Chat chat);
    }

    public interface OnSendMessageListener {
        void onSendMessageFailure(String str);

        void onSendMessageSuccess();
    }

    public interface Presenter {
        void getMessage(String str, String str2);

        void sendMessage(Context context, Chat chat, String str);
    }

    public interface View {
        void onGetMessagesFailure(String str);

        void onGetMessagesSuccess(Chat chat);

        void onSendMessageFailure(String str);

        void onSendMessageSuccess();
    }
}
