package com.mike4christ.tisvdigital.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.mike4christ.tisvdigital.C0506R;
import com.wang.avi.AVLoadingIndicatorView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditFragment_ViewBinding implements Unbinder {
    private ProfileEditFragment target;

    @UiThread
    public ProfileEditFragment_ViewBinding(ProfileEditFragment target, View source) {
        this.target = target;
        target.firstname_editxt = (EditText) Utils.findRequiredViewAsType(source, C0506R.id.firstname_editxt, "field 'firstname_editxt'", EditText.class);
        target.lastname_editxt = (EditText) Utils.findRequiredViewAsType(source, C0506R.id.lastname_editxt, "field 'lastname_editxt'", EditText.class);
        target.designation_editxt = (EditText) Utils.findRequiredViewAsType(source, C0506R.id.designation_editxt, "field 'designation_editxt'", EditText.class);
        target.phone_num_editxt = (EditText) Utils.findRequiredViewAsType(source, C0506R.id.phone_num_editxt, "field 'phone_num_editxt'", EditText.class);
        target.avi1 = (AVLoadingIndicatorView) Utils.findRequiredViewAsType(source, C0506R.id.avi1, "field 'avi1'", AVLoadingIndicatorView.class);
        target.inputLayoutFirstnameP = (TextInputLayout) Utils.findRequiredViewAsType(source, C0506R.id.inputLayoutFirstnameP, "field 'inputLayoutFirstnameP'", TextInputLayout.class);
        target.inputLayoutLastnameP = (TextInputLayout) Utils.findRequiredViewAsType(source, C0506R.id.inputLayoutLastnameP, "field 'inputLayoutLastnameP'", TextInputLayout.class);
        target.inputLayoutDesignationP = (TextInputLayout) Utils.findRequiredViewAsType(source, C0506R.id.inputLayoutDesignationP, "field 'inputLayoutDesignationP'", TextInputLayout.class);
        target.inputLayoutPhone_NumP = (TextInputLayout) Utils.findRequiredViewAsType(source, C0506R.id.inputLayoutPhone_NumP, "field 'inputLayoutPhone_NumP'", TextInputLayout.class);
        target.pick_photo = (CircleImageView) Utils.findRequiredViewAsType(source, C0506R.id.pick_photo, "field 'pick_photo'", CircleImageView.class);
        target.update_btn = (Button) Utils.findRequiredViewAsType(source, C0506R.id.update_btn, "field 'update_btn'", Button.class);
    }

    @CallSuper
    public void unbind() {
        ProfileEditFragment target = this.target;
        if (target != null) {
            this.target = null;
            target.firstname_editxt = null;
            target.lastname_editxt = null;
            target.designation_editxt = null;
            target.phone_num_editxt = null;
            target.avi1 = null;
            target.inputLayoutFirstnameP = null;
            target.inputLayoutLastnameP = null;
            target.inputLayoutDesignationP = null;
            target.inputLayoutPhone_NumP = null;
            target.pick_photo = null;
            target.update_btn = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
