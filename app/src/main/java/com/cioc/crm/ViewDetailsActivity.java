package com.cioc.crm;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

public class ViewDetailsActivity extends FragmentActivity {
    ImageView contactImage;
    TextView nameTv, companyTv, designationTv, cnoTv, emailTv;

    TabLayout tl;
    FloatingActionButton fabView, fabSchedule, fabTask, fabInfo;
    private boolean fabExpanded = false;
    private LinearLayout layoutFabSchedule;
    private LinearLayout layoutFabTask;
    private LinearLayout layoutFabInfo;
    Animation rotate_forward, rotate_Backward, fab_open, fab_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        Bundle b = getIntent().getExtras();
        int image = b.getInt("image");
        String name = b.getString("name");
        String company = b.getString("company");
        String designation = b.getString("designation");
        String cno = b.getString("cno");
        String email = b.getString("email");

        nameTv = findViewById(R.id.view_d_name);
        companyTv = findViewById(R.id.view_d_comapany);
        designationTv = findViewById(R.id.view_d_designation);
        cnoTv = findViewById(R.id.view_mob_no);
        emailTv = findViewById(R.id.view_email);

        fabView = findViewById(R.id.fab_view);
        fabSchedule = findViewById(R.id.fab_schedule);
        fabTask = findViewById(R.id.fab_task);
        fabInfo = findViewById(R.id.fab_info);

        layoutFabSchedule = (LinearLayout) this.findViewById(R.id.layoutFabSchedule);
        layoutFabTask = (LinearLayout) this.findViewById(R.id.layoutFabTask);
        layoutFabInfo = (LinearLayout) this.findViewById(R.id.layoutFabInfo);

        rotate_forward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotate_Backward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);
        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        nameTv.setText(name);
        companyTv.setText(company);
        designationTv.setText(designation);
        cnoTv.setText(cno);
        emailTv.setText(email);

        tl = findViewById(R.id.tl_view);
        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                switch (pos){
                    case 0:
                    {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.add(R.id.view_fg, new TimelineFragment(), "TimelineFragment");
                        ft.commit();
                        break;
                    }
                    case 1:
                    {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.add(R.id.view_fg, new ActiveFragment(), "ActiveFragment");
                        ft.commit();
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });
        closeSubMenusFab();

        fabSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.dialog_edit_update_company, null, false);
                AlertDialog.Builder adb = new AlertDialog.Builder(ViewDetailsActivity.this);
                adb.setView(view);
                adb.create().show();
            }
        });

        fabTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.dialog_edit_update_company, null, false);
                AlertDialog.Builder adb = new AlertDialog.Builder(ViewDetailsActivity.this);
                adb.setView(view);
                adb.create().show();
            }
        });

        fabInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.dialog_edit_update_company, null, false);
                AlertDialog.Builder adb = new AlertDialog.Builder(ViewDetailsActivity.this);
                adb.setView(view);
                adb.create().show();
            }
        });
    }

    //closes FAB submenus
    private void closeSubMenusFab(){
        layoutFabSchedule.setVisibility(View.GONE);
        layoutFabTask.setVisibility(View.GONE);
        layoutFabInfo.setVisibility(View.GONE);
        fabView.setImageResource(R.drawable.ic_add);
        fabSchedule.startAnimation(fab_close);
        fabTask.setAnimation(fab_close);
        fabInfo.setAnimation(fab_close);
        fabView.startAnimation(rotate_Backward);
//        fabView.startAnimation(fab_open);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabSchedule.setVisibility(View.VISIBLE);
        layoutFabTask.setVisibility(View.VISIBLE);
        layoutFabInfo.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
//        fab.setImageResource(R.drawable.ic_close);
        fabSchedule.startAnimation(fab_open);
        fabTask.setAnimation(fab_open);
        fabInfo.setAnimation(fab_open);
        fabView.startAnimation(rotate_forward);
        fabExpanded = true;
    }
}
