package com.mike4christ.tisvdigital.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.auth.FirebaseAuth;
import com.mike4christ.tisvdigital.C0506R;
import com.mike4christ.tisvdigital.Constant;
import com.mike4christ.tisvdigital.adapters.ChatRecyclerAdapter;
import com.mike4christ.tisvdigital.chat.ChatContract.View;
import com.mike4christ.tisvdigital.chat.ChatPresenter;
import com.mike4christ.tisvdigital.events.PushNotificationEvent;
import com.mike4christ.tisvdigital.model.Chat;
import com.mike4christ.tisvdigital.model.User;
import java.util.ArrayList;
import org.greenrobot.eventbus.Subscribe;

public class OneOnOneFragment extends Fragment implements View, OnClickListener {
    @BindView(2131296388)
    FloatingActionButton fab_send;
    private ChatPresenter mChatPresenter;
    private ChatRecyclerAdapter mChatRecyclerAdapter;
    @BindView(2131296365)
    EditText mETxtMessage;
    @BindView(2131296501)
    RecyclerView mRecyclerViewChat;
    User user = new User();

    public static OneOnOneFragment newInstance(String receiver_firstname, String receiver_lastname, String receiver_email, String receiver_link, String firebaseToken) {
        Bundle args = new Bundle();
        args.putString(Constant.ARG_RECEIVER_FIRST, receiver_firstname);
        args.putString(Constant.ARG_RECEIVER_LASTNAME, receiver_lastname);
        args.putString(Constant.ARG_RECEIVER_EMAIL, receiver_email);
        args.putString(Constant.ARG_RECEIVER_LINK, receiver_link);
        args.putString(Constant.ARG_FIREBASE_TOKEN, firebaseToken);
        OneOnOneFragment fragment = new OneOnOneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View fragmentView = inflater.inflate(C0506R.layout.fragment_one_on_one, container, false);
        ButterKnife.bind((Object) this, fragmentView);
        return fragmentView;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        this.fab_send.setOnClickListener(this);
        this.mChatPresenter = new ChatPresenter(this);
        ChatPresenter chatPresenter = this.mChatPresenter;
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Bundle arguments = getArguments();
        String str = Constant.ARG_RECEIVER_EMAIL;
        chatPresenter.getMessage(email, arguments.getString(str));
        Toast.makeText(getActivity(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), 0).show();
        Toast.makeText(getActivity(), getArguments().getString(str), 0).show();
    }

    private void sendMessage() {
        String message = this.mETxtMessage.getText().toString();
        String receiver_firstname = getArguments().getString(Constant.ARG_RECEIVER_FIRST);
        String receiver_email = getArguments().getString(Constant.ARG_RECEIVER_EMAIL);
        String senderEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String receiverFirebaseToken = getArguments().getString(Constant.ARG_FIREBASE_TOKEN);
        this.mChatPresenter.sendMessage(getActivity().getApplicationContext(), new Chat(receiver_firstname, senderEmail, receiver_email, message, System.currentTimeMillis()), receiverFirebaseToken);
    }

    public void onSendMessageSuccess() {
        this.mETxtMessage.setText("");
        Toast.makeText(getActivity(), "Message sent", 0).show();
    }

    public void onSendMessageFailure(String message) {
        Toast.makeText(getActivity(), message, 0).show();
    }

    public void onGetMessagesSuccess(Chat chat) {
        if (this.mChatRecyclerAdapter == null) {
            this.mChatRecyclerAdapter = new ChatRecyclerAdapter(new ArrayList());
            this.mRecyclerViewChat.setAdapter(this.mChatRecyclerAdapter);
        }
        this.mChatRecyclerAdapter.add(chat);
        this.mRecyclerViewChat.smoothScrollToPosition(this.mChatRecyclerAdapter.getItemCount() - 1);
    }

    public void onGetMessagesFailure(String message) {
        Toast.makeText(getActivity(), message, 0).show();
    }

    @Subscribe
    public void onPushNotificationEvent(PushNotificationEvent pushNotificationEvent) {
        ChatRecyclerAdapter chatRecyclerAdapter = this.mChatRecyclerAdapter;
        if (chatRecyclerAdapter == null || chatRecyclerAdapter.getItemCount() == 0) {
            this.mChatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getEmail(), pushNotificationEvent.getEmail());
        }
    }

    public void onClick(android.view.View view) {
        if (view.getId() == C0506R.id.fab_send) {
            validateUserInputs();
        }
    }

    private void validateUserInputs() {
        boolean isValid = true;
        if (this.mETxtMessage.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Empty Message typed", 1).show();
            isValid = false;
        }
        if (isValid) {
            sendMessage();
        }
    }
}
