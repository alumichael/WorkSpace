package com.mike4christ.tisvdigital.adapters;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.mike4christ.tisvdigital.R;
import com.mike4christ.tisvdigital.model.User;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.List;

public class UserListingRecyclerAdapter extends RecyclerView.Adapter<UserListingRecyclerAdapter.ViewHolder> {

    private List<User> mUsers;



    public UserListingRecyclerAdapter(List<User> users) {
        this.mUsers = users;
    }

    public void add(User user) {
        mUsers.add(user);
        notifyItemInserted(this.mUsers.size() - 1);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_hall_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mUsers.get(position);

        if (user.email != null) {
            holder.firstName.setText(user.firstname);
            holder.lastName.setText(user.lastname);
            holder.email.setText(user.email);
            Log.d("Check Database: -----", user.link);
            if (user.link.equals("Default")) {
                holder.users_photo.setImageResource(R.drawable.man);
            } else {
                holder.userPhoto(user.getLink());
            }
        }
    }



    public int getItemCount() {
        if (mUsers != null) {
            return mUsers.size();
        }
        return 0;
    }

    public User getUser(int position) {
        return mUsers.get(position);
    }

     static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.email)
        TextView email;
        @BindView(R.id.firstname)
        TextView firstName;
        @BindView(R.id.lastname)
        TextView lastName;
        @BindView(R.id.user_layout)
        LinearLayout userLayout;
        @BindView(R.id.users_photo)
        CircleImageView users_photo;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind( this, itemView);
        }

        public void userPhoto(String url) {
            CircleImageView circleImageView = this.users_photo;
            if (circleImageView != null) {
                Glide.with(circleImageView.getContext()).load(url).apply(new RequestOptions().fitCenter().override(50, 50).circleCrop()).into(this.users_photo);
            }
        }
    }
}
