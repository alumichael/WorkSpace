package com.mike4christ.tisvdigital.users.getall;

import android.text.TextUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mike4christ.tisvdigital.Constant;
import com.mike4christ.tisvdigital.model.User;
import com.mike4christ.tisvdigital.users.getall.GetUsersContract.Interactor;
import com.mike4christ.tisvdigital.users.getall.GetUsersContract.OnGetAllUsersListener;
import java.util.ArrayList;
import java.util.List;

public class GetUsersInteractor implements Interactor {
    private static final String TAG = "GetUsersInteractor";
    private OnGetAllUsersListener mOnGetAllUsersListener;

    /* renamed from: com.mike4christ.tisvdigital.users.getall.GetUsersInteractor$1 */
    class C08211 implements ValueEventListener {
        C08211() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            List<User> users = new ArrayList();
            for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()) {
                User user = (User) dataSnapshotChild.getValue(User.class);
                if (!TextUtils.equals(user.email, FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                    users.add(user);
                }
            }
            GetUsersInteractor.this.mOnGetAllUsersListener.onGetAllUsersSuccess(users);
        }

        public void onCancelled(DatabaseError databaseError) {
            GetUsersInteractor.this.mOnGetAllUsersListener.onGetAllUsersFailure(databaseError.getMessage());
        }
    }

    public GetUsersInteractor(OnGetAllUsersListener onGetAllUsersListener) {
        this.mOnGetAllUsersListener = onGetAllUsersListener;
    }

    public void getAllUsersFromFirebase() {
        FirebaseDatabase.getInstance().getReference().child(Constant.ARG_USERS).addListenerForSingleValueEvent(new C08211());
    }

    public void getChatUsersFromFirebase() {
    }
}
