package com.mike4christ.tisvdigital;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.mike4christ.tisvdigital.fragment.Welcome1Fragment;
import com.mike4christ.tisvdigital.fragment.WelcomeSlide2Fragment;
import com.mike4christ.tisvdigital.fragment.WelcomeSlide3Fragment;
import java.util.ArrayList;
import java.util.List;


public class WelcomeSlideActivity extends BaseActivity implements View.OnClickListener {

    private TextView[] dots;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;
    @BindView(R.id.get_started_btn)
    Button getStartedBtn;
    private int[] layouts;
    @BindView(R.id.txt_login)
    TextView loginTxt;
    private MyViewPagerAdapter myViewPagerAdapter;
    private UserPreferences userPreferences;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_welcome_slide);
        ButterKnife.bind(this);
        userPreferences = new UserPreferences(this);
        layouts = new int[]{R.layout.fragment_welcome1, R.layout.fragment_welcome_slide2, R.layout.fragment_welcome_slide3};
        addBottomDots(0);
        changeStatusBarColor();
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        myViewPagerAdapter.addFragment(new Welcome1Fragment());
        myViewPagerAdapter.addFragment(new WelcomeSlide2Fragment());
        myViewPagerAdapter.addFragment(new WelcomeSlide3Fragment());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        getStartedBtn.setOnClickListener(this);
        loginTxt.setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.get_started_btn) {
            startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
        } else if (id == R.id.txt_login) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }

    private void addBottomDots(int currentPage) {
        this.dots = new TextView[this.layouts.length];
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
        this.dotsLayout.removeAllViews();
        int i = 0;
        while (true) {
            TextView[] textViewArr = this.dots;
            if (i >= textViewArr.length) {
                break;
            }
            textViewArr[i] = new TextView(this);
            this.dots[i].setText(Html.fromHtml("&#9642"));
            this.dots[i].setTextSize(35.0f);
            this.dots[i].setTextColor(colorsInactive[currentPage]);
            this.dotsLayout.addView(this.dots[i]);
            i++;
        }
        if (dots.length > 0) {
            dots[currentPage].setTextColor(colorsActive[currentPage]);
        }
    }


    //	viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };





    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }



    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();

        MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return (Fragment) this.mFragmentList.get(position);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        void addFragment(Fragment fragment) {
            this.mFragmentList.add(fragment);
        }
    }



}
