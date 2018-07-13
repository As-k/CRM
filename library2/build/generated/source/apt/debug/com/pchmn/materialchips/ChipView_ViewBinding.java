// Generated code from Butter Knife. Do not modify!
package com.pchmn.materialchips;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChipView_ViewBinding implements Unbinder {
  private ChipView target;

  @UiThread
  public ChipView_ViewBinding(ChipView target) {
    this(target, target);
  }

  @UiThread
  public ChipView_ViewBinding(ChipView target, View source) {
    this.target = target;

    target.mContentLayout = Utils.findRequiredViewAsType(source, R.id.content, "field 'mContentLayout'", LinearLayout.class);
    target.mAvatarIconImageView = Utils.findRequiredViewAsType(source, R.id.icon, "field 'mAvatarIconImageView'", CircleImageView.class);
    target.mLabelTextView = Utils.findRequiredViewAsType(source, R.id.label, "field 'mLabelTextView'", TextView.class);
    target.mDeleteButton = Utils.findRequiredViewAsType(source, R.id.delete_button, "field 'mDeleteButton'", ImageButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChipView target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mContentLayout = null;
    target.mAvatarIconImageView = null;
    target.mLabelTextView = null;
    target.mDeleteButton = null;
  }
}
