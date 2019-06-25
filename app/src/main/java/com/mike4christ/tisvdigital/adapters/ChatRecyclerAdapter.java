package com.mike4christ.tisvdigital.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import com.mike4christ.tisvdigital.R;
import com.mike4christ.tisvdigital.UserPreferences;
import com.mike4christ.tisvdigital.model.Chat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;
    private List<Chat> mChats;

    Context context;

    public ChatRecyclerAdapter(Context context,List<Chat> chats) {
        this.context = context;
        this.mChats = chats;

    }

    public void add(Chat chat) {
        mChats.add(chat);
        notifyItemInserted(mChats.size() - 1);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ME:
                View viewChatMine = layoutInflater.inflate(R.layout.item_chat_mine, parent, false);
                viewHolder = new MyChatViewHolder(viewChatMine);
                break;
            case VIEW_TYPE_OTHER:
                View viewChatOther = layoutInflater.inflate(R.layout.item_chat_other, parent, false);
                viewHolder = new OtherChatViewHolder(viewChatOther);
                break;
        }
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (TextUtils.equals(mChats.get(position).senderEmail,
                FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            configureMyChatViewHolder((MyChatViewHolder) holder, position);
        } else {
            configureOtherChatViewHolder((OtherChatViewHolder) holder, position);
        }
    }

    private void configureMyChatViewHolder(MyChatViewHolder myChatViewHolder, int position) {
        Chat chat = mChats.get(position);
        UserPreferences userPreferences=new UserPreferences(context);

        myChatViewHolder.txtChatMessage.setText( chat.message);
        if(userPreferences.isSentSuccess()){
            myChatViewHolder.img_sent.setVisibility(View.VISIBLE);
            userPreferences.setSentSuccess(false);
        }
        myChatViewHolder.time_sent.setVisibility(View.VISIBLE);

        long millis = chat.timestamp;
        @SuppressLint("DefaultLocale") String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        myChatViewHolder.time_sent.setText(hms);



    }

    private void configureOtherChatViewHolder(OtherChatViewHolder otherChatViewHolder, int position) {
        Chat chat = mChats.get(position);

        otherChatViewHolder.txtChatMessage.setText(chat.message);
        otherChatViewHolder.time_get.setVisibility(View.VISIBLE);

        long millis = chat.timestamp;
        @SuppressLint("DefaultLocale") String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        otherChatViewHolder.time_get.setText(hms);
    }

    public int getItemCount() {
        if (mChats != null) {
            return mChats.size();
        }
        return 0;
    }

    public int getItemViewType(int position) {
        if (TextUtils.equals(mChats.get(position).senderEmail,
                FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_OTHER;
        }
    }


    private static class MyChatViewHolder extends RecyclerView.ViewHolder {
        private TextView txtChatMessage;
        private TextView time_sent;
        private ImageView img_sent;

        public MyChatViewHolder(View itemView) {
            super(itemView);
            txtChatMessage = itemView.findViewById(R.id.text_view_chat_message);
            time_sent = itemView.findViewById(R.id.time_sent);
            img_sent = itemView.findViewById(R.id.img_sent);
        }
    }

    private static class OtherChatViewHolder extends RecyclerView.ViewHolder {
        private TextView txtChatMessage;
        private TextView time_get;

        public OtherChatViewHolder(View itemView) {
            super(itemView);
            txtChatMessage = itemView.findViewById(R.id.text_view_chat_message);
            time_get = itemView.findViewById(R.id.time_get);
        }
    }
}
