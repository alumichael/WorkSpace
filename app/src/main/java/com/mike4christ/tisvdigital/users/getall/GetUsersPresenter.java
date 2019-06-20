package com.mike4christ.tisvdigital.users.getall;

import com.mike4christ.tisvdigital.model.User;
import com.mike4christ.tisvdigital.users.getall.GetUsersContract.OnGetAllUsersListener;
import com.mike4christ.tisvdigital.users.getall.GetUsersContract.Presenter;
import com.mike4christ.tisvdigital.users.getall.GetUsersContract.View;
import java.util.List;

public class GetUsersPresenter implements GetUsersContract.Presenter, GetUsersContract.OnGetAllUsersListener {
    private GetUsersContract.View mView;
    private GetUsersInteractor mGetUsersInteractor;

    public GetUsersPresenter(GetUsersContract.View view) {
        this.mView = view;
        mGetUsersInteractor = new GetUsersInteractor(this);
    }

    @Override
    public void getAllUsers() {
        this.mGetUsersInteractor.getAllUsersFromFirebase();
    }

    @Override
    public void getChatUsers() {
        this.mGetUsersInteractor.getChatUsersFromFirebase();
    }

    @Override
    public void onGetAllUsersSuccess(List<User> users) {
        this.mView.onGetAllUsersSuccess(users);
    }

    @Override
    public void onGetAllUsersFailure(String message) {
        this.mView.onGetAllUsersFailure(message);
    }
}
