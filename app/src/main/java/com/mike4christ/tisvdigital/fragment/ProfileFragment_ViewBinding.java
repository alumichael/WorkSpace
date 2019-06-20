package com.mike4christ.tisvdigital.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.mike4christ.tisvdigital.C0506R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment_ViewBinding implements Unbinder {
    private ProfileFragment target;

    @UiThread
    public ProfileFragment_ViewBinding(ProfileFragment target, View source) {
        this.target = target;
        target.progressBar_profile = (ProgressBar) Utils.findRequiredViewAsType(source, C0506R.id.progressBar_profile, "field 'progressBar_profile'", ProgressBar.class);
        target.profile_photo = (CircleImageView) Utils.findRequiredViewAsType(source, C0506R.id.profile_photo, "field 'profile_photo'", CircleImageView.class);
        target.prof_firstName = (TextView) Utils.findRequiredViewAsType(source, C0506R.id.prof_firstName, "field 'prof_firstName'", TextView.class);
        target.prof_lastName = (TextView) Utils.findRequiredViewAsType(source, C0506R.id.prof_lastName, "field 'prof_lastName'", TextView.class);
        target.prof_designation = (TextView) Utils.findRequiredViewAsType(source, C0506R.id.prof_designation, "field 'prof_designation'", TextView.class);
        target.prof_email = (TextView) Utils.findRequiredViewAsType(source, C0506R.id.prof_email, "field 'prof_email'", TextView.class);
        target.prof_phoneNum = (TextView) Utils.findRequiredViewAsType(source, C0506R.id.prof_phoneNum, "field 'prof_phoneNum'", TextView.class);
        target.profile_lay = (LinearLayout) Utils.findRequiredViewAsType(source, C0506R.id.profile_lay, "field 'profile_lay'", LinearLayout.class);
    }

    @CallSuper
    public void unbind() {
        ProfileFragment target = this.target;
        if (target != null) {
            this.target = null;
            target.progressBar_profile = null;
            target.profile_photo = null;
            target.prof_firstName = null;
            target.prof_lastName = null;
            target.prof_designation = null;
            target.prof_email = null;
            target.prof_phoneNum = null;
            target.profile_lay = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
