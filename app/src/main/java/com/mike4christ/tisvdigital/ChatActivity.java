package com.mike4christ.tisvdigital;


import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.mike4christ.tisvdigital.fragment.OneOnOneFragment;

public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolBar;

    public static void startActivity(Context context, String receiver_firstname, String receiver_lastname, String receiver_email, String receiver_link, String firebaseToken) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(Constant.ARG_RECEIVER_FIRST, receiver_firstname);
        intent.putExtra(Constant.ARG_RECEIVER_LASTNAME, receiver_lastname);
        intent.putExtra(Constant.ARG_RECEIVER_EMAIL, receiver_email);
        intent.putExtra(Constant.ARG_RECEIVER_LINK, receiver_link);
        intent.putExtra(Constant.ARG_FIREBASE_TOKEN, firebaseToken);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        String str = Constant.ARG_RECEIVER_LASTNAME;
        String string = extras.getString(str);
        Bundle extras2 = getIntent().getExtras();
        String str2 = Constant.ARG_RECEIVER_EMAIL;
        applyToolbar(string, extras2.getString(str2));
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content_chat, OneOnOneFragment.newInstance(getIntent().getExtras().getString(Constant.ARG_RECEIVER_FIRST), getIntent().getExtras().getString(str), getIntent().getExtras().getString(str2), getIntent().getExtras().getString(Constant.ARG_RECEIVER_LINK), getIntent().getExtras().getString(Constant.ARG_FIREBASE_TOKEN)), OneOnOneFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void applyToolbar(String title, String subtitle) {
        setSupportActionBar(this.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subtitle);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        if (VERSION.SDK_INT >= 21) {
            this.toolBar.setElevation(10.0f);
        }
    }

    protected void onResume() {
        super.onResume();
        TisvChatApp.setChatActivityOpen(true);
    }

    protected void onPause() {
        super.onPause();
        TisvChatApp.setChatActivityOpen(false);
    }
}
