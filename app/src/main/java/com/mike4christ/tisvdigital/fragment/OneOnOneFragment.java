package com.mike4christ.tisvdigital.fragment;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

    @BindView(R.id.img_sent)
    ImageView img_sent;
    @BindView(R.id.img_sent_fail)
    ImageView img_sent_fail;


    /*@BindView(R.id.chatting_bg_image)
    ImageView chatting_bg_image;*/
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;






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

        if(!(isNetworkConnected())) {
            mETxtMessage.setText("");
            img_sent.setVisibility(View.GONE);
            img_sent_fail.setVisibility(View.VISIBLE);

            Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_LONG).show();
        }


        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            init();
            if(!(isNetworkConnected())) {
                mETxtMessage.setText("");
                img_sent.setVisibility(View.GONE);
                img_sent_fail.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_LONG).show();
            }

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

            try {

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
            }catch (Exception e){
                mETxtMessage.setText("");
                img_sent.setVisibility(View.GONE);
                img_sent_fail.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_LONG).show();


            }


        }


    public  boolean isNetworkConnected() {
        Context context = getContext();
        final ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                final NetworkInfo ni = cm.getActiveNetworkInfo();

                if (ni != null) {
                    return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
                }
            } else {
                final Network n = cm.getActiveNetwork();

                if (n != null) {
                    final NetworkCapabilities nc = cm.getNetworkCapabilities(n);

                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                }
            }
        }

        return false;
    }


    @Override
    public void onSendMessageSuccess() {
        mETxtMessage.setText("");
        img_sent_fail.setVisibility(View.GONE);
        img_sent.setVisibility(View.VISIBLE);
       //Toast.makeText(getActivity(), "Message sent", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onSendMessageFailure(String message) {
        mETxtMessage.setText("");
        img_sent.setVisibility(View.GONE);
        img_sent_fail.setVisibility(View.VISIBLE);
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
         if(!isNetworkConnected()){
            mETxtMessage.setText("");
            img_sent.setVisibility(View.GONE);
            img_sent_fail.setVisibility(View.VISIBLE);

            Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if (isValid) {
            if(isNetworkConnected()) {
                sendMessage();
            }else{
                mETxtMessage.setText("");
                img_sent.setVisibility(View.GONE);
                img_sent_fail.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity(), "Please Check your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }
    }
}
