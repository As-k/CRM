// Generated code from Butter Knife. Do not modify!
package com.pchmn.materialchips.views;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.pchmn.materialchips.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailedChipView_ViewBinding implements Unbinder {
  private DetailedChipView target;

  @UiThread
  public DetailedChipView_ViewBinding(DetailedChipView target) {
    this(target, target);
  }

  @UiThread
  public DetailedChipView_ViewBinding(DetailedChipView target, View source) {
    this.target = target;

    target.mContentLayout = Utils.findRequiredViewAsType(source, R.id.content, "field 'mContentLayout'", RelativeLayout.class);
    target.mAvatarIconImageView = Utils.findRequiredViewAsType(source, R.id.avatar_icon, "field 'mAvatarIconImageView'", CircleImageView.class);
    target.mNameTextView = Utils.findRequiredViewAsType(source, R.id.name, "field 'mNameTextView'", TextView.class);
    target.mInfoTextView = Utils.findRequiredViewAsType(source, R.id.info, "field 'mInfoTextView'", TextView.class);
    target.mDeleteButton = Utils.findRequiredViewAsType(source, R.id.delete_button, "field 'mDeleteButton'", ImageButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailedChipView target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mContentLayout = null;
    target.mAvatarIconImageView = null;
    target.mNameTextView = null;
    target.mInfoTextView = null;
    target.mDeleteButton = null;
  }
}
