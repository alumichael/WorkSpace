package com.mike4christ.tisvdigital;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.mike4christ.tisvdigital.fragment.ProfileEditFragment;
import com.mike4christ.tisvdigital.fragment.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind((Activity) this);
        setSupportActionBar(this.toolBar);
        this.toolBar.setTitleTextColor(-1);
        this.toolBar.setLogo((int) C0506R.drawable.icon);
        this.toolBar.setTitle((CharSequence) "Profile");
        initialFragment();
    }

    private void initialFragment() {
        ProfileFragment profileFragment = new ProfileFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(C0506R.id.fragment_profile, profileFragment);
        fragmentTransaction.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0506R.menu.profile, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != C0506R.id.edit_profile) {
            return super.onOptionsItemSelected(item);
        }
        item.setVisible(false);
        ProfileEditFragment profileEditFragment = new ProfileEditFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(C0506R.id.fragment_profile, profileEditFragment);
        fragmentTransaction.commit();
        return true;
    }

    public void onBackPressed() {
        startActivity(new Intent(this, Dashboard.class));
        super.onBackPressed();
    }
}
