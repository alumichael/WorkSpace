package com.mike4christ.tisvdigital;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.wang.avi.AVLoadingIndicatorView;

public class ForgetPasswordActivity extends AppCompatActivity implements OnClickListener {
    @BindView(2131296303)
    FrameLayout activity_forgot;
    private FirebaseAuth auth;
    @BindView(2131296320)
    TextView btn_back;
    @BindView(2131296368)
    EditText email_editxt_forget;
    @BindView(2131296398)
    Button forget_pass_btn;
    NetworkInfo networkInfo;
    @BindView(2131296313)
    AVLoadingIndicatorView progresBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0506R.layout.activity_forgot_password);
        ButterKnife.bind((Activity) this);
        this.networkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        this.forget_pass_btn.setOnClickListener(this);
        this.btn_back.setOnClickListener(this);
        this.auth = FirebaseAuth.getInstance();
    }

    public void onClick(View view) {
        if (view.getId() == C0506R.id.btn_back) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (view.getId() == C0506R.id.forget_pass_btn) {
            NetworkInfo networkInfo = this.networkInfo;
            if (networkInfo == null || !networkInfo.isAvailable() || !this.networkInfo.isConnected()) {
                Snackbar.make(this.activity_forgot, (CharSequence) "Error Network Connection, Check and Try again", -1).show();
            } else if (this.email_editxt_forget.getText().toString().isEmpty()) {
                Snackbar.make(this.activity_forgot, (CharSequence) "Empty Field", -1).show();
            } else {
                resetPassword(this.email_editxt_forget.getText().toString());
            }
        }
    }

    private void resetPassword(final String email) {
        this.forget_pass_btn.setVisibility(4);
        this.progresBar.setVisibility(0);
        this.auth.sendPasswordResetEmail(email).addOnCompleteListener((Activity) this, new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ForgetPasswordActivity.this.progresBar.setVisibility(4);
                    View snackBar = ForgetPasswordActivity.this.activity_forgot;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("We have sent password to email: ");
                    stringBuilder.append(email);
                    Snackbar.make(snackBar, stringBuilder.toString(), -1).show();
                    return;
                }
                ForgetPasswordActivity.this.forget_pass_btn.setVisibility(0);
                ForgetPasswordActivity.this.progresBar.setVisibility(4);
                Snackbar.make(ForgetPasswordActivity.this.activity_forgot, (CharSequence) "Failed to send password", -1).show();
            }
        });
    }
}
