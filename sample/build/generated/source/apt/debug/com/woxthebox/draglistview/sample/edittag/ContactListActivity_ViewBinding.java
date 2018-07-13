// Generated code from Butter Knife. Do not modify!
package com.woxthebox.draglistview.sample.edittag;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.pchmn.materialchips.ChipsInput;
import com.woxthebox.draglistview.sample.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ContactListActivity_ViewBinding implements Unbinder {
  private ContactListActivity target;

  @UiThread
  public ContactListActivity_ViewBinding(ContactListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ContactListActivity_ViewBinding(ContactListActivity target, View source) {
    this.target = target;

    target.mChipsInput = Utils.findRequiredViewAsType(source, R.id.chips_input, "field 'mChipsInput'", ChipsInput.class);
    target.mChipListText = Utils.findRequiredViewAsType(source, R.id.chip_list, "field 'mChipListText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ContactListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mChipsInput = null;
    target.mChipListText = null;
  }
}
