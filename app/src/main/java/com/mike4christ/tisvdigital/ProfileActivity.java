package com.mike4christ.tisvdigital;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.mike4christ.tisvdigital.fragment.ProfileEditFragment;
import com.mike4christ.tisvdigital.fragment.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind( this);
        setSupportActionBar(toolBar);
        toolBar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolBar.setLogo(R.drawable.icon);
        toolBar.setTitle("Profile");
        initialFragment();
    }

    private void initialFragment() {
        ProfileFragment profileFragment = new ProfileFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_profile, profileFragment);
        fragmentTransaction.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.edit_profile) {
            return super.onOptionsItemSelected(item);
        }
        item.setVisible(false);
        ProfileEditFragment profileEditFragment = new ProfileEditFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_profile, profileEditFragment);
        fragmentTransaction.commit();
        return true;
    }

    public void onBackPressed() {
        startActivity(new Intent(this, Dashboard.class));
        super.onBackPressed();
    }
}
