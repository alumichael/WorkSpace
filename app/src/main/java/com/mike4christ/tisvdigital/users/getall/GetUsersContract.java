package com.mike4christ.tisvdigital.users.getall;

import com.mike4christ.tisvdigital.model.User;
import java.util.List;

public interface GetUsersContract {

    interface View {

        void onGetChatUsersSuccess(List<User> users);
        void onGetChatUsersFailure(String message);

        void onGetAllUsersFailure(String message);

        void onGetAllUsersSuccess(List<User> users);


    }

    interface Presenter {
        void getAllUsers();

        void getChatUsers();
    }

    interface Interactor {
        void getAllUsersFromFirebase();

        void getChatUsersFromFirebase();
    }

    interface OnGetAllUsersListener {
        void onGetAllUsersFailure(String message);

        void onGetAllUsersSuccess(List<User> users);
    }

     interface OnGetChatUsersListener {
        void onGetChatUsersFailure(String message);

        void onGetChatUsersSuccess(List<User> users);
    }



}
