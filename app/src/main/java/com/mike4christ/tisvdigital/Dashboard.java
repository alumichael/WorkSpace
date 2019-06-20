package com.mike4christ.tisvdigital;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mike4christ.tisvdigital.fragment.ChatFragment;
import com.mike4christ.tisvdigital.fragment.WebFragment;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    FirebaseUser authUser;
    @BindView(R.id.dashboard_layout)
    CoordinatorLayout dashboard_layout;
    private TabLayout mTabLayoutUserListing;
    private ViewPager mViewPagerUserListing;
    NetworkInfo networkInfo;
    private int[] tabIcons = new int[]{R.drawable.ic_web_black_24dp, R.drawable.ic_chat_black_24dp};
    @BindView(R.id.tab)
    TabLayout tablayout;
    @BindView(R.id.toolbar)
    Toolbar toolBar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;


 

    class UserListingPagerAdapter extends FragmentPagerAdapter {
        private final Fragment[] sFragments = new Fragment[]{ChatFragment.newInstance(ChatFragment.TYPE_ALL)};
        private final String[] sTitles = new String[]{"All Users"};

        public UserListingPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return sFragments[position];
        }

        public int getCount() {
            return sFragments.length;
        }

        public String getPageTitle(int position) {
            return sTitles[position];
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Fragment getItem(int position) {
            return (Fragment) mFragmentList.get(position);
        }

        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position) {
            return  mFragmentTitleList.get(position);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        networkInfo = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        authUser = FirebaseAuth.getInstance().getCurrentUser();
        if (authUser != null) {
            ButterKnife.bind( this);
            setSupportActionBar(toolBar);
            toolBar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
            toolBar.setLogo(R.drawable.icon);
            toolBar.setTitle(R.string.app_name);
            setupViewPager(viewPager);
            tablayout.setupWithViewPager(viewPager);
            setupTabIcons();

        }else {
            new UserPreferences(this).setUserLogged(false);
            Toast.makeText(this, "Null User data,please try to login again", 1).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WebFragment(), "Channel");
        adapter.addFragment(new ChatFragment(), "Chat Room");
        viewPager.setAdapter(adapter);
        int a = viewPager.getCurrentItem();
        if (a == 1) {
            init();
        }
        if (a == 0) {


            if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
                Snackbar.make(dashboard_layout,  "Hi, you are Welcome", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            } else {
                Snackbar.make(dashboard_layout,"Try to Connect to Internet", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
    }

    private void setupTabIcons() {
        tablayout.getTabAt(0).setIcon(tabIcons[0]);
        tablayout.getTabAt(1).setIcon(tabIcons[1]);
        tablayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), Mode.SRC_IN);
        tablayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), Mode.SRC_IN);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.colorWhite), Mode.SRC_IN);
                int position = tab.getPosition();
                Dashboard dashboard;
                if (position == 0) {
                    dashboard = Dashboard.this;
                    dashboard.setTitle(dashboard.getString(R.string.web));
                } else if (position == 1) {
                    dashboard = Dashboard.this;
                    dashboard.setTitle(dashboard.getString(R.string.chat));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Dashboard.this.tablayout.getTabAt(0).getIcon().clearColorFilter();
                tab.getIcon().clearColorFilter();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.colorWhite), Mode.SRC_IN);
                int position = tab.getPosition();
                Dashboard dashboard;
                if (position == 0) {
                    dashboard = Dashboard.this;
                    dashboard.setTitle(dashboard.getString(R.string.web));
                } else if (position == 1) {
                    dashboard = Dashboard.this;
                    dashboard.setTitle(dashboard.getString(R.string.chat));
                }
            }
        });
    }

    private void init() {
        mViewPagerUserListing.setAdapter(new UserListingPagerAdapter(getSupportFragmentManager()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.log_out) {
            logout();
            return true;
        } else if (itemId != R.id.profile) {
            return super.onOptionsItemSelected(item);
        } else {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.logout)
                .setMessage("Are you sure ?")
                .setPositiveButton(R.string.logout, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            FirebaseAuth.getInstance().signOut();
                            UserPreferences userPreferences = new UserPreferences(Dashboard.this);
                            userPreferences.setUserLogged(false);
                            Context context = Dashboard.this;
                            context.startActivity(new Intent(context, LoginActivity.class));
                            Toast.makeText(Dashboard.this, "Successfully logged out!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(Dashboard.this, "No user logged in yet!", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void onBackPressed() {
        try {
            if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
                WebFragment fragment = (WebFragment) getSupportFragmentManager().findFragmentByTag("webView");
                if (fragment.mywebView.canGoBack()) {
                    fragment.mywebView.goBack();
                } else {
                    super.onBackPressed();
                }
            } else {
                Snackbar.make(dashboard_layout, "Check your Internet", Snackbar.LENGTH_LONG).setAction( "Action", null).show();
            }
        } catch (Exception e) {
            super.onBackPressed();
            finish();
        }
    }
}
