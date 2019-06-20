package com.mike4christ.tisvdigital.users.getall;

import com.mike4christ.tisvdigital.model.User;
import com.mike4christ.tisvdigital.users.getall.GetUsersContract.OnGetAllUsersListener;
import com.mike4christ.tisvdigital.users.getall.GetUsersContract.Presenter;
import com.mike4christ.tisvdigital.users.getall.GetUsersContract.View;
import java.util.List;

public class GetUsersPresenter implements Presenter, OnGetAllUsersListener {
    private GetUsersInteractor mGetUsersInteractor = new GetUsersInteractor(this);
    private View mView;

    public GetUsersPresenter(View view) {
        this.mView = view;
    }

    public void getAllUsers() {
        this.mGetUsersInteractor.getAllUsersFromFirebase();
    }

    public void getChatUsers() {
        this.mGetUsersInteractor.getChatUsersFromFirebase();
    }

    public void onGetAllUsersSuccess(List<User> users) {
        this.mView.onGetAllUsersSuccess(users);
    }

    public void onGetAllUsersFailure(String message) {
        this.mView.onGetAllUsersFailure(message);
    }
}
