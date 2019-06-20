package com.mike4christ.tisvdigital.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.mike4christ.tisvdigital.C0506R;
import com.wang.avi.AVLoadingIndicatorView;

public class RegistrationInitialFragment_ViewBinding implements Unbinder {
    private RegistrationInitialFragment target;

    @UiThread
    public RegistrationInitialFragment_ViewBinding(RegistrationInitialFragment target, View source) {
        this.target = target;
        target.emailEditxt = (EditText) Utils.findRequiredViewAsType(source, C0506R.id.email_editxt, "field 'emailEditxt'", EditText.class);
        target.passwordEditxt = (EditText) Utils.findRequiredViewAsType(source, C0506R.id.password_editxt, "field 'passwordEditxt'", EditText.class);
        target.firstNameEditxt = (EditText) Utils.findRequiredViewAsType(source, C0506R.id.firstname_editxt, "field 'firstNameEditxt'", EditText.class);
        target.lastnmameEditxt = (EditText) Utils.findRequiredViewAsType(source, C0506R.id.lastname_editxt, "field 'lastnmameEditxt'", EditText.class);
        target.designationEditxt = (EditText) Utils.findRequiredViewAsType(source, C0506R.id.designation_editxt, "field 'designationEditxt'", EditText.class);
        target.phoneEditxt = (EditText) Utils.findRequiredViewAsType(source, C0506R.id.phone_num_editxt, "field 'phoneEditxt'", EditText.class);
        target.progressBar = (AVLoadingIndicatorView) Utils.findRequiredViewAsType(source, C0506R.id.avi1, "field 'progressBar'", AVLoadingIndicatorView.class);
        target.inputLayoutEmail = (TextInputLayout) Utils.findRequiredViewAsType(source, C0506R.id.inputLayoutEmail, "field 'inputLayoutEmail'", TextInputLayout.class);
        target.inputLayoutPassword = (TextInputLayout) Utils.findRequiredViewAsType(source, C0506R.id.inputLayoutPassword, "field 'inputLayoutPassword'", TextInputLayout.class);
        target.inputLayoutFirstname = (TextInputLayout) Utils.findRequiredViewAsType(source, C0506R.id.inputLayoutFirstname, "field 'inputLayoutFirstname'", TextInputLayout.class);
        target.inputLayoutDesignation = (TextInputLayout) Utils.findRequiredViewAsType(source, C0506R.id.inputLayoutDesignation_stack, "field 'inputLayoutDesignation'", TextInputLayout.class);
        target.inputLayoutLastname = (TextInputLayout) Utils.findRequiredViewAsType(source, C0506R.id.inputLayoutLastname, "field 'inputLayoutLastname'", TextInputLayout.class);
        target.inputLayoutPhonenum = (TextInputLayout) Utils.findRequiredViewAsType(source, C0506R.id.inputLayoutPhonenum, "field 'inputLayoutPhonenum'", TextInputLayout.class);
        target.addPicFrameRelativelayout = (RelativeLayout) Utils.findRequiredViewAsType(source, C0506R.id.add_photo_relative_layout, "field 'addPicFrameRelativelayout'", RelativeLayout.class);
        target.regBtn = (Button) Utils.findRequiredViewAsType(source, C0506R.id.register_btn, "field 'regBtn'", Button.class);
    }

    @CallSuper
    public void unbind() {
        RegistrationInitialFragment target = this.target;
        if (target != null) {
            this.target = null;
            target.emailEditxt = null;
            target.passwordEditxt = null;
            target.firstNameEditxt = null;
            target.lastnmameEditxt = null;
            target.designationEditxt = null;
            target.phoneEditxt = null;
            target.progressBar = null;
            target.inputLayoutEmail = null;
            target.inputLayoutPassword = null;
            target.inputLayoutFirstname = null;
            target.inputLayoutDesignation = null;
            target.inputLayoutLastname = null;
            target.inputLayoutPhonenum = null;
            target.addPicFrameRelativelayout = null;
            target.regBtn = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
