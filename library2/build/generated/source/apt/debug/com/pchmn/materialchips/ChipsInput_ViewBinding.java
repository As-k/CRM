// Generated code from Butter Knife. Do not modify!
package com.pchmn.materialchips;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChipsInput_ViewBinding implements Unbinder {
  private ChipsInput target;

  @UiThread
  public ChipsInput_ViewBinding(ChipsInput target) {
    this(target, target);
  }

  @UiThread
  public ChipsInput_ViewBinding(ChipsInput target, View source) {
    this.target = target;

    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.chips_recycler, "field 'mRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChipsInput target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;
  }
}
