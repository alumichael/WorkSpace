package com.mike4christ.tisvdigital.chat;

import android.content.Context;
import com.mike4christ.tisvdigital.chat.ChatContract.OnGetMessagesListener;
import com.mike4christ.tisvdigital.chat.ChatContract.OnSendMessageListener;
import com.mike4christ.tisvdigital.chat.ChatContract.Presenter;
import com.mike4christ.tisvdigital.chat.ChatContract.View;
import com.mike4christ.tisvdigital.model.Chat;

public class ChatPresenter implements ChatContract.Presenter, ChatContract.OnSendMessageListener, ChatContract.OnGetMessagesListener {


    private ChatContract.View mView;
    private ChatInteractor mChatInteractor;

    public ChatPresenter(ChatContract.View view) {
        this.mView = view;
        mChatInteractor = new ChatInteractor(this, this);
    }


    public void sendMessage(Context context, Chat chat, String receiverFirebaseToken) {
        mChatInteractor.sendMessageToFirebaseUser(context, chat, receiverFirebaseToken);
    }

    public void getMessage(String senderEmail, String receiverEmail) {
        mChatInteractor.getMessageFromFirebaseUser(senderEmail, receiverEmail);
    }

    @Override
    public void onSendMessageSuccess() {
        mView.onSendMessageSuccess();
    }

    @Override
    public void onSendMessageFailure(String message) {
        mView.onSendMessageFailure(message);
    }

    @Override
    public void onGetMessagesSuccess(Chat chat) {
        mView.onGetMessagesSuccess(chat);
    }

    @Override
    public void onGetMessagesFailure(String message) {
        mView.onGetMessagesFailure(message);
    }
}
