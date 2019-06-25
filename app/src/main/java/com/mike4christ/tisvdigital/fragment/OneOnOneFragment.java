package com.mike4christ.tisvdigital.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import com.mike4christ.tisvdigital.Constant;
import com.mike4christ.tisvdigital.R;
import com.mike4christ.tisvdigital.adapters.ChatRecyclerAdapter;
import com.mike4christ.tisvdigital.chat.ChatContract;

import com.mike4christ.tisvdigital.chat.ChatPresenter;
import com.mike4christ.tisvdigital.events.PushNotificationEvent;
import com.mike4christ.tisvdigital.model.Chat;
import com.mike4christ.tisvdigital.model.User;



import java.util.ArrayList;

public class OneOnOneFragment extends Fragment implements ChatContract.View ,View.OnClickListener{
    @BindView(R.id.fab_send)
    FloatingActionButton fab_send;
    private ChatPresenter mChatPresenter;
    private ChatRecyclerAdapter mChatRecyclerAdapter;
    @BindView(R.id.edit_text_message)
    EditText mETxtMessage;
    @BindView(R.id.recycler_view_chat)
    RecyclerView mRecyclerViewChat;



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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_one_on_one, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            init();

        }catch (Exception e){
            Toast.makeText(getActivity(), "Try to SignIn again"+e.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("Problem",e.getMessage());

        }
    }

    private void init() {
        fab_send.setOnClickListener(this);

        this.mChatPresenter = new ChatPresenter(this);
        ChatPresenter chatPresenter = this.mChatPresenter;
        chatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                getArguments().getString(Constant.ARG_RECEIVER_EMAIL));
    }

    private void sendMessage() {
        String message = mETxtMessage.getText().toString();
        String receiver_firstname = getArguments().getString(Constant.ARG_RECEIVER_FIRST);
        String receiver_email = getArguments().getString(Constant.ARG_RECEIVER_EMAIL);
        String senderEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String receiverFirebaseToken = getArguments().getString(Constant.ARG_FIREBASE_TOKEN);


        Chat chat = new Chat(receiver_firstname,
                senderEmail,
                receiver_email,
                message,
                System.currentTimeMillis());
        mChatPresenter.sendMessage(getActivity().getApplicationContext(),
                chat,
                receiverFirebaseToken);


    }
    @Override
    public void onSendMessageSuccess() {
        mETxtMessage.setText("");
        Toast.makeText(getActivity(), "Message sent", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onSendMessageFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onGetMessagesSuccess(Chat chat) {
        if (mChatRecyclerAdapter == null) {
            mChatRecyclerAdapter = new ChatRecyclerAdapter(getContext(),new ArrayList<Chat>());
            mRecyclerViewChat.setAdapter(mChatRecyclerAdapter);
        }
        mChatRecyclerAdapter.add(chat);
        mRecyclerViewChat.smoothScrollToPosition(mChatRecyclerAdapter.getItemCount() - 1);
    }

    @Override
    public void onGetMessagesFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }


    public void onPushNotificationEvent(PushNotificationEvent pushNotificationEvent) {
        ChatRecyclerAdapter chatRecyclerAdapter = mChatRecyclerAdapter;
        if (chatRecyclerAdapter == null || chatRecyclerAdapter.getItemCount() == 0) {
            mChatPresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getEmail(), pushNotificationEvent.getEmail());
        }
    }


    @Override
    public void onClick(android.view.View view) {
        if (view.getId() == R.id.fab_send) {
            validateUserInputs();
        }
    }

    private void validateUserInputs() {
        boolean isValid = true;
        if (mETxtMessage.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Empty Message typed", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if (isValid) {
            sendMessage();
        }
    }
}
