package com.mike4christ.tisvdigital.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.mike4christ.tisvdigital.C0506R;

public class OneOnOneFragment_ViewBinding implements Unbinder {
    private OneOnOneFragment target;

    @UiThread
    public OneOnOneFragment_ViewBinding(OneOnOneFragment target, View source) {
        this.target = target;
        target.mRecyclerViewChat = (RecyclerView) Utils.findRequiredViewAsType(source, C0506R.id.recycler_view_chat, "field 'mRecyclerViewChat'", RecyclerView.class);
        target.mETxtMessage = (EditText) Utils.findRequiredViewAsType(source, C0506R.id.edit_text_message, "field 'mETxtMessage'", EditText.class);
        target.fab_send = (FloatingActionButton) Utils.findRequiredViewAsType(source, C0506R.id.fab_send, "field 'fab_send'", FloatingActionButton.class);
    }

    @CallSuper
    public void unbind() {
        OneOnOneFragment target = this.target;
        if (target != null) {
            this.target = null;
            target.mRecyclerViewChat = null;
            target.mETxtMessage = null;
            target.fab_send = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
