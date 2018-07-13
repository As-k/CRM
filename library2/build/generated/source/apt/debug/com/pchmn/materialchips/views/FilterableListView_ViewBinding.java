// Generated code from Butter Knife. Do not modify!
package com.pchmn.materialchips.views;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.pchmn.materialchips.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FilterableListView_ViewBinding implements Unbinder {
  private FilterableListView target;

  @UiThread
  public FilterableListView_ViewBinding(FilterableListView target) {
    this(target, target);
  }

  @UiThread
  public FilterableListView_ViewBinding(FilterableListView target, View source) {
    this.target = target;

    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.recycler_view, "field 'mRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FilterableListView target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;
  }
}
