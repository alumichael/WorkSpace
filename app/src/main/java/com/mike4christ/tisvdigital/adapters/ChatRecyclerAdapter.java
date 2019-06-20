package com.mike4christ.tisvdigital.adapters;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import com.mike4christ.tisvdigital.R;
import com.mike4christ.tisvdigital.model.Chat;
import java.util.List;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;
    private List<Chat> mChats;



    public ChatRecyclerAdapter(List<Chat> chats) {
        mChats = chats;
    }

    public void add(Chat chat) {
        mChats.add(chat);
        notifyItemInserted(mChats.size() - 1);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == 1) {
            return new MyChatViewHolder(layoutInflater.inflate(R.layout.item_chat_mine, parent, false));
        }
        if (viewType != 2) {
            return null;
        }
        return new OtherChatViewHolder(layoutInflater.inflate(R.layout.item_chat_other, parent, false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (TextUtils.equals( mChats.get(position).senderEmail, FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            configureMyChatViewHolder((MyChatViewHolder) holder, position);
        } else {
            configureOtherChatViewHolder((OtherChatViewHolder) holder, position);
        }
    }

    private void configureMyChatViewHolder(MyChatViewHolder myChatViewHolder, int position) {
        myChatViewHolder.txtChatMessage.setText( mChats.get(position).message);
    }

    private void configureOtherChatViewHolder(OtherChatViewHolder otherChatViewHolder, int position) {
        otherChatViewHolder.txtChatMessage.setText(mChats.get(position).message);
    }

    public int getItemCount() {
        List list = mChats;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public int getItemViewType(int position) {
        if (TextUtils.equals(((Chat) mChats.get(position)).senderEmail, FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            return 1;
        }
        return 2;
    }


    private static class MyChatViewHolder extends RecyclerView.ViewHolder {
        private TextView txtChatMessage;

        public MyChatViewHolder(View itemView) {
            super(itemView);
            txtChatMessage = (TextView) itemView.findViewById(R.id.text_view_chat_message);
        }
    }

    private static class OtherChatViewHolder extends RecyclerView.ViewHolder {
        private TextView txtChatMessage;

        public OtherChatViewHolder(View itemView) {
            super(itemView);
            txtChatMessage = (TextView) itemView.findViewById(R.id.text_view_chat_message);
        }
    }
}
