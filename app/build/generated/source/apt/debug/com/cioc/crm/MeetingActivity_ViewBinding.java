// Generated code from Butter Knife. Do not modify!
package com.cioc.crm;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.pchmn.materialchips.ChipsInput;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MeetingActivity_ViewBinding implements Unbinder {
  private MeetingActivity target;

  @UiThread
  public MeetingActivity_ViewBinding(MeetingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MeetingActivity_ViewBinding(MeetingActivity target, View source) {
    this.target = target;

    target.mChipsInputIP = Utils.findRequiredViewAsType(source, R.id.chips_input_ip, "field 'mChipsInputIP'", ChipsInput.class);
    target.mChipsInputCRM = Utils.findRequiredViewAsType(source, R.id.chips_input_crm, "field 'mChipsInputCRM'", ChipsInput.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MeetingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mChipsInputIP = null;
    target.mChipsInputCRM = null;
  }
}
