package com.mike4christ.tisvdigital.chat;

import android.content.Context;
import com.mike4christ.tisvdigital.chat.ChatContract.OnGetMessagesListener;
import com.mike4christ.tisvdigital.chat.ChatContract.OnSendMessageListener;
import com.mike4christ.tisvdigital.chat.ChatContract.Presenter;
import com.mike4christ.tisvdigital.chat.ChatContract.View;
import com.mike4christ.tisvdigital.model.Chat;

public class ChatPresenter implements Presenter, OnSendMessageListener, OnGetMessagesListener {
    private ChatInteractor mChatInteractor = new ChatInteractor(this, this);
    private View mView;

    public ChatPresenter(View view) {
        this.mView = view;
    }

    public void sendMessage(Context context, Chat chat, String receiverFirebaseToken) {
        this.mChatInteractor.sendMessageToFirebaseUser(context, chat, receiverFirebaseToken);
    }

    public void getMessage(String senderEmail, String receiverEmail) {
        this.mChatInteractor.getMessageFromFirebaseUser(senderEmail, receiverEmail);
    }

    public void onSendMessageSuccess() {
        this.mView.onSendMessageSuccess();
    }

    public void onSendMessageFailure(String message) {
        this.mView.onSendMessageFailure(message);
    }

    public void onGetMessagesSuccess(Chat chat) {
        this.mView.onGetMessagesSuccess(chat);
    }

    public void onGetMessagesFailure(String message) {
        this.mView.onGetMessagesFailure(message);
    }
}
