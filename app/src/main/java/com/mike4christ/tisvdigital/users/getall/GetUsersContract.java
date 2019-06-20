package com.mike4christ.tisvdigital.users.getall;

import com.mike4christ.tisvdigital.model.User;
import java.util.List;

public interface GetUsersContract {

    public interface Interactor {
        void getAllUsersFromFirebase();

        void getChatUsersFromFirebase();
    }

    public interface OnGetAllUsersListener {
        void onGetAllUsersFailure(String str);

        void onGetAllUsersSuccess(List<User> list);
    }

    public interface OnGetChatUsersListener {
        void onGetChatUsersFailure(String str);

        void onGetChatUsersSuccess(List<User> list);
    }

    public interface Presenter {
        void getAllUsers();

        void getChatUsers();
    }

    public interface View {
        void onGetAllUsersFailure(String str);

        void onGetAllUsersSuccess(List<User> list);

        void onGetChatUsersFailure(String str);

        void onGetChatUsersSuccess(List<User> list);
    }
}
