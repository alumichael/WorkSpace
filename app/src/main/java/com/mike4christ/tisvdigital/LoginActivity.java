package com.mike4christ.tisvdigital;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mike4christ.tisvdigital.model.User;
import com.mike4christ.tisvdigital.users.getall.SharedPrefUtil;
import com.wang.avi.AVLoadingIndicatorView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    FirebaseUser authUser;
    private ProgressDialog dlg;
    @BindView(R.id.email_editxt)
    EditText emailEditxt;
    FirebaseAuth firebaseAuth;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.password_editxt)
    EditText passwordEditxt;
    @BindView(R.id.progressBar)
    AVLoadingIndicatorView progressView;
    DatabaseReference reference;
    @BindView(R.id.txt_register)
    TextView txtRegister;
    @BindView(R.id.txt_forget_pass)
    TextView txt_forget_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        UserPreferences userPreferences = new UserPreferences(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            userPreferences.setUserLogged(true);
            startActivity(new Intent(this, Dashboard.class));
        }
        txtRegister.setOnClickListener(this);
        txt_forget_pass.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.login_btn) {
            String userEmail = emailEditxt.getText().toString().toLowerCase();
            final String userPassword = passwordEditxt.getText().toString();
            NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
                if (!userEmail.isEmpty()) {
                    if (!userPassword.isEmpty()) {
                        loginBtn.setVisibility(View.GONE);
                        progressView.setVisibility(View.VISIBLE);
                        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressView.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, "Successfully Log in !", Toast.LENGTH_LONG).show();
                                    updateFirebaseToken((task.getResult()).getUser().getEmail(), new SharedPrefUtil(LoginActivity.this).getString(Constant.ARG_FIREBASE_TOKEN, null));
                                    setUserData();
                                    Context context = LoginActivity.this;
                                    context.startActivity(new Intent(context, Dashboard.class));
                                    return;
                                }
                                progressView.setVisibility(View.GONE);
                                loginBtn.setVisibility(View.VISIBLE);
                                if (userPassword.length() < 6) {
                                    Toast.makeText(LoginActivity.this, "Password length must be over 6", Toast.LENGTH_LONG).show();
                                }
                                Toast.makeText(LoginActivity.this, "Check your Internet or try to input Valid data", Toast.LENGTH_LONG).show();
                            }
                        });
                        new UserPreferences(getApplicationContext()).setUserLogged(true);
                        return;
                    }
                }
                showMessage("All fields are required!");
                return;
            }
            showMessage("Kindly connect to the Internet.");
        } else if (id == R.id.txt_forget_pass) {
            startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
        } else if (id == R.id.txt_register) {
            startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
        }
    }

    private void setUserData() {
        authUser = FirebaseAuth.getInstance().getCurrentUser();
        if (authUser != null) {
            reference = FirebaseDatabase.getInstance().getReference(Constant.ARG_USERS);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()) {
                        User user = dataSnapshotChild.getValue(User.class);
                        if (TextUtils.equals(user.getEmail(), FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                            User user2 = new User(user.getFirstname(), user.getLastname(), user.getDesignation(), user.getPhone_num(), user.getEmail(), user.getPassword(), user.getLink(), new SharedPrefUtil(LoginActivity.this).getString(user.getFirebaseToken()));
                            new UserPreferences(getApplicationContext()).saveString(Constant.ARG_SENDER_FIRSTNAME, user2.getFirstname());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            return;
        }
        Toast.makeText(this, "Null User Data", Toast.LENGTH_LONG).show();
    }

    private void updateFirebaseToken(String email, String token) {
        FirebaseDatabase.getInstance().getReference().child(Constant.ARG_USERS).child(email.replace(".", ",")).child(Constant.ARG_FIREBASE_TOKEN).setValue(token);
    }

    public void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
