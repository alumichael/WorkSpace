package com.mike4christ.tisvdigital.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mike4christ.tisvdigital.Constant;
import com.mike4christ.tisvdigital.Dashboard;
import com.mike4christ.tisvdigital.R;
import com.mike4christ.tisvdigital.UserPreferences;
import com.mike4christ.tisvdigital.model.User;
import com.mike4christ.tisvdigital.users.getall.SharedPrefUtil;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.regex.Pattern;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class RegistrationInitialFragment extends Fragment implements View.OnClickListener {
    int PICK_IMAGE_REQUEST = 1;
    DatabaseReference Userdatabase;
    @BindView(R.id.add_photo_relative_layout)
    RelativeLayout addPicFrameRelativelayout;
    private ConnectivityManager connectivityManager;
    public String designation;
    @BindView(R.id.designation_editxt)
    EditText designationEditxt;
    public String email;
    @BindView(R.id.email_editxt)
    EditText emailEditxt;
    public String emailEncode;
    FirebaseAuth firebaseAuth;
    @BindView(R.id.firstname_editxt)
    EditText firstNameEditxt;
    public String firstname;
    FirebaseUser fuser;
    public String id;
    Uri img_uri;
    @BindView(R.id.inputLayoutDesignation_stack)
    TextInputLayout inputLayoutDesignation;
    @BindView(R.id.inputLayoutEmail)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.inputLayoutFirstname)
    TextInputLayout inputLayoutFirstname;
    @BindView(R.id.inputLayoutLastname)
    TextInputLayout inputLayoutLastname;
    @BindView(R.id.inputLayoutPassword)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.inputLayoutPhonenum)
    TextInputLayout inputLayoutPhonenum;
    public String lastname;
    @BindView(R.id.lastname_editxt)
    EditText lastnmameEditxt;
    public String link;
    private NetworkInfo networkInfo;
    public String password;
    @BindView(R.id.password_editxt)
    EditText passwordEditxt;
    @BindView(R.id.phone_num_editxt)
    EditText phoneEditxt;
    public String phone_num;
    @BindView(R.id.avi1)
    AVLoadingIndicatorView progressBar;
    @BindView(R.id.register_btn)
    Button regBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration_initial, container, false);
        ButterKnife.bind(this, view);
        Context context = getContext();
        getContext();
        connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        setViewActions();
        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), this.PICK_IMAGE_REQUEST);
    }

    private void validateUserInputs() {
        NetworkInfo networkInfo = this.networkInfo;
        if (networkInfo != null && networkInfo.isAvailable() && this.networkInfo.isConnected()) {
            boolean isValid = true;
            if (emailEditxt.getText().toString().isEmpty()) {
                inputLayoutEmail.setError("Email is required!");
                isValid = false;
            } else if (isValidEmailAddress(emailEditxt.getText().toString())) {
                inputLayoutEmail.setErrorEnabled(false);
            } else {
                inputLayoutEmail.setError("Valid Email is required!");
                isValid = false;
            }

            if (firstNameEditxt.getText().toString().isEmpty()) {
                inputLayoutFirstname.setError("First Name is required");

                isValid = false;

            } else {
                inputLayoutFirstname.setErrorEnabled(false);
            }
            if (lastnmameEditxt.getText().toString().isEmpty()) {
                inputLayoutLastname.setError("Last Name is required");
                isValid = false;
            } else {
                inputLayoutLastname.setErrorEnabled(false);
            }
            if (designationEditxt.getText().toString().isEmpty()) {
                inputLayoutDesignation.setError("Designation is required");
                isValid = false;
            } else {
                inputLayoutDesignation.setErrorEnabled(false);
            }
            if (phoneEditxt.getText().toString().isEmpty()) {
                inputLayoutPhonenum.setError("Phone number is required");
                isValid = false;
            } else if (this.phoneEditxt.getText().toString().length() < 11) {
                inputLayoutPassword.setError("Your Phone Number must not be less than 11 in length");
                isValid = false;
            } else {
                inputLayoutPhonenum.setErrorEnabled(false);
            }
            if (passwordEditxt.getText().toString().isEmpty()) {
                inputLayoutPassword.setError("Password is required");
                isValid = false;
            } else if (passwordEditxt.getText().toString().length() < 6) {
                inputLayoutPassword.setError("Your Password must be greater than 5 in length");
                isValid = false;
            } else {
                inputLayoutPassword.setErrorEnabled(false);
            }
            if (isValid) {
                initFragment();
            }
            return;
        }
        showMessage("No Internet connection discovered!");
    }

    private void showMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }

    private void setViewActions() {
        regBtn.setOnClickListener(this);
    }

    private void initFragment() {
        regBtn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        firstname = firstNameEditxt.getText().toString();
        lastname = lastnmameEditxt.getText().toString();
        designation = designationEditxt.getText().toString();
        phone_num = phoneEditxt.getText().toString();
        email = emailEditxt.getText().toString().toLowerCase();
        password = passwordEditxt.getText().toString();
        emailEncode = email.replace('.', ',');
        link = "Default";
        Userdatabase = FirebaseDatabase.getInstance().getReference(Constant.ARG_USERS).child(emailEncode);
        Log.d("UserEmail", Userdatabase.toString());
        Userdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String value = (String) dataSnapshot.getValue(String.class);
                    Toast.makeText(getContext(), "The Email you use already Exist !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    email.replace(".", ",");
                    final User user = new User(firstname, lastname, designation, phone_num, email, password, link, new SharedPrefUtil(getContext()).getString(Constant.ARG_FIREBASE_TOKEN));
                    Userdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                Userdatabase.setValue(user);
                                Toast.makeText(getContext(), "Registration Completed", Toast.LENGTH_LONG).show();
                                if (firebaseAuth.getCurrentUser() != null) {
                                    progressBar.setVisibility(View.GONE);
                                }

                                startActivity(new Intent(getContext(), Dashboard.class));

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            regBtn.setVisibility(View.VISIBLE);
                        }

                    });
                    new UserPreferences(getContext()).setUserLogged(true);
                    return;


                }
                 regBtn.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "The Email you use already Exist !", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.add_photo_relative_layout) {
            chooseImage();
        } else if (id == R.id.register_btn) {
            validateUserInputs();
        }
    }

    private static boolean isValidEmailAddress(String email) {
        if (email == null || Pattern.compile("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z0-9-]{1,8}))?$").matcher(email).matches()) {
            return true;
        }
        return false;
    }
}
