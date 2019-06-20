package com.mike4christ.tisvdigital.fragment;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mike4christ.tisvdigital.ChatActivity;
import com.mike4christ.tisvdigital.ItemClickSupport;
import com.mike4christ.tisvdigital.ItemClickSupport.OnItemClickListener;
import com.mike4christ.tisvdigital.R;
import com.mike4christ.tisvdigital.adapters.UserListingRecyclerAdapter;
import com.mike4christ.tisvdigital.chat.ChatContract;
import com.mike4christ.tisvdigital.model.User;
import com.mike4christ.tisvdigital.users.getall.GetUsersPresenter;
import java.util.List;

public class ChatFragment extends Fragment implements View, OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String ARG_TYPE = "type";
    public static final String TYPE_ALL = "type_all";
    public static final String TYPE_CHATS = "type_chats";
    private GetUsersPresenter mGetUsersPresenter;
    private RecyclerView mRecyclerViewAllUserListing;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private UserListingRecyclerAdapter mUserListingRecyclerAdapter;
    
    

    public static ChatFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        android.view.View fragmentView = inflater.inflate(R.layout.fragment_chat, container, false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(android.view.View view) {
        mSwipeRefreshLayout =  view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerViewAllUserListing = view.findViewById(R.id.my_recycler_view);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mGetUsersPresenter = new GetUsersPresenter(this);
        getUsers();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
               mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        ItemClickSupport.addTo(mRecyclerViewAllUserListing).setOnItemClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public void onRefresh() {
        getUsers();
    }

    private void getUsers() {
        mGetUsersPresenter.getAllUsers();
    }

    public void onItemClicked(RecyclerView recyclerView, int position, android.view.View v) {
        ChatActivity.startActivity(getActivity(), mUserListingRecyclerAdapter.getUser(position).firstname, mUserListingRecyclerAdapter.getUser(position).lastname, mUserListingRecyclerAdapter.getUser(position).email, mUserListingRecyclerAdapter.getUser(position).link, mUserListingRecyclerAdapter.getUser(position).firebaseToken);
    }

    public void onGetAllUsersSuccess(List<User> users) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                
                   mSwipeRefreshLayout.setRefreshing(false);
                
            }
        });
        if (users.size() == 0) {
            mSwipeRefreshLayout.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "User is empty", Toast.LENGTH_LONG).show();
            return;
        }
        mUserListingRecyclerAdapter = new UserListingRecyclerAdapter(users);
        mRecyclerViewAllUserListing.setAdapter(mUserListingRecyclerAdapter);
        mUserListingRecyclerAdapter.notifyDataSetChanged();
    }

    public void onGetAllUsersFailure(String message) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        Context activity = getActivity();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error: ");
        stringBuilder.append(message);
        Toast.makeText(activity, stringBuilder.toString(), Toast.LENGTH_LONG).show();
    }

    public void onGetChatUsersSuccess(List<User> list) {
    }

    public void onGetChatUsersFailure(String message) {
    }
}
