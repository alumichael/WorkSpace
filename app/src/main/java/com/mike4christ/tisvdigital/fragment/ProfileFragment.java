package com.mike4christ.tisvdigital.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mike4christ.tisvdigital.C0506R;
import com.mike4christ.tisvdigital.Constant;
import com.mike4christ.tisvdigital.model.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    FirebaseUser auth;
    @BindView(2131296485)
    TextView prof_designation;
    @BindView(2131296486)
    TextView prof_email;
    @BindView(2131296487)
    TextView prof_firstName;
    @BindView(2131296488)
    TextView prof_lastName;
    @BindView(2131296489)
    TextView prof_phoneNum;
    @BindView(2131296491)
    LinearLayout profile_lay;
    @BindView(2131296493)
    CircleImageView profile_photo;
    @BindView(2131296495)
    ProgressBar progressBar_profile;
    DatabaseReference reference;

    /* renamed from: com.mike4christ.tisvdigital.fragment.ProfileFragment$1 */
    class C08171 implements ValueEventListener {
        C08171() {
        }

        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()) {
                User user = (User) dataSnapshotChild.getValue(User.class);
                if (TextUtils.equals(user.getEmail(), FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                    ProfileFragment.this.prof_firstName.setText(user.getFirstname());
                    ProfileFragment.this.prof_lastName.setText(user.getLastname());
                    ProfileFragment.this.prof_designation.setText(user.getDesignation());
                    ProfileFragment.this.prof_email.setText(user.getEmail());
                    ProfileFragment.this.prof_phoneNum.setText(user.getPhone_num());
                    if (user.getLink().equals("Default")) {
                        ProfileFragment.this.profile_photo.setImageResource(C0506R.drawable.man);
                    } else {
                        Glide.with(ProfileFragment.this.getContext()).load(user.getLink()).apply(new RequestOptions().fitCenter().circleCrop()).into(ProfileFragment.this.profile_photo);
                    }
                    ProfileFragment.this.progressBar_profile.setVisibility(8);
                }
            }
        }

        public void onCancelled(@NonNull DatabaseError databaseError) {
            Snackbar.make(ProfileFragment.this.profile_lay, databaseError.getMessage(), 0).setAction((CharSequence) "Action", null).show();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(C0506R.layout.fragment_profile, container, false);
        ButterKnife.bind((Object) this, fragmentView);
        getUserProfile();
        return fragmentView;
    }

    private void getUserProfile() {
        this.progressBar_profile.setVisibility(0);
        this.auth = FirebaseAuth.getInstance().getCurrentUser();
        if (this.auth != null) {
            this.reference = FirebaseDatabase.getInstance().getReference(Constant.ARG_USERS);
            this.reference.addListenerForSingleValueEvent(new C08171());
            return;
        }
        Snackbar.make(this.profile_lay, (CharSequence) "Null User, Try to Re-Login", 0).setAction((CharSequence) "Action", null).show();
    }
}
