package com.mike4christ.tisvdigital.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mike4christ.tisvdigital.Constant;
import com.mike4christ.tisvdigital.R;
import com.mike4christ.tisvdigital.model.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    FirebaseUser auth;
    @BindView(R.id.prof_designation)
    TextView prof_designation;
    @BindView(R.id.prof_email)
    TextView prof_email;
    @BindView(R.id.prof_firstName)
    TextView prof_firstName;
    @BindView(R.id.prof_lastName)
    TextView prof_lastName;
    @BindView(R.id.prof_phoneNum)
    TextView prof_phoneNum;
    @BindView(R.id.profile_lay)
    LinearLayout profile_lay;
    @BindView(R.id.profile_photo)
    CircleImageView profile_photo;
    @BindView(R.id.progressBar_profile)
    ProgressBar progressBar_profile;
    DatabaseReference reference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, fragmentView);
        getUserProfile();
        return fragmentView;
    }

    private void getUserProfile() {
        progressBar_profile.setVisibility(View.VISIBLE);
        auth = FirebaseAuth.getInstance().getCurrentUser();
        if (auth != null) {
            reference = FirebaseDatabase.getInstance().getReference(Constant.ARG_USERS);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()) {
                        User user = dataSnapshotChild.getValue(User.class);
                        if (TextUtils.equals(user.getEmail(), FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                            prof_firstName.setText(user.getFirstname());
                            prof_lastName.setText(user.getLastname());
                            prof_designation.setText(user.getDesignation());
                            prof_email.setText(user.getEmail());
                            prof_phoneNum.setText(user.getPhone_num());
                            if (user.getLink().equals("Default")) {
                                ProfileFragment.this.profile_photo.setImageResource(R.drawable.man);
                            } else {
                                Glide.with(getContext()).load(user.getLink()).apply(new RequestOptions().fitCenter().circleCrop()).into(profile_photo);
                            }
                            ProfileFragment.this.progressBar_profile.setVisibility(View.GONE);
                        }
                    }
                    
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Snackbar.make(ProfileFragment.this.profile_lay, databaseError.getMessage(), Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                }
            });
            return;
        }
        Snackbar.make(this.profile_lay, "Null User, Try to Re-Login", Snackbar.LENGTH_SHORT).setAction((CharSequence) "Action", null).show();
    }
}
