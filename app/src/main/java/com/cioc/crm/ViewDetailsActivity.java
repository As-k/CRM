package com.cioc.crm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.irshulx.Editor;
import com.github.irshulx.EditorListener;
import com.github.irshulx.models.EditorTextStyle;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class ViewDetailsActivity extends FragmentActivity {
    ImageView contactImage;
    TextView nameTv, companyTv, designationTv, cnoTv, emailTv;

    TabLayout tl;
    FloatingActionButton fabView, fabSchedule, fabTask, fabMeeting, fabNotes;
    private boolean fabExpanded = false;
    private LinearLayout layoutFabSchedule;
    private LinearLayout layoutFabTask;
    private LinearLayout layoutFabMeeting;
    private LinearLayout layoutFabNotes;
    Animation rotate_forward, rotate_Backward, fab_open, fab_close;

    int c_yr, c_month, c_day, c_hr, c_min;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        Bundle b = getIntent().getExtras();
        int image = b.getInt("image");
        final String name = b.getString("name");
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
        fabMeeting = findViewById(R.id.fab_meeting);
        fabNotes = findViewById(R.id.fab_notes);

        layoutFabSchedule = (LinearLayout) this.findViewById(R.id.layoutFabSchedule);
        layoutFabTask = (LinearLayout) this.findViewById(R.id.layoutFabTask);
        layoutFabMeeting = (LinearLayout) this.findViewById(R.id.layoutFabMeeting);
        layoutFabNotes = (LinearLayout) this.findViewById(R.id.layoutFabNotes);

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
                        ft.add(R.id.view_fg, new InfoFragment(), "ActiveFragment");
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

        Calendar c = Calendar.getInstance();
        c_yr = c.get(Calendar.YEAR);
        c_month = c.get(Calendar.MONTH);
        c_day = c.get(Calendar.DAY_OF_MONTH);
        c_hr = c.get(Calendar.HOUR_OF_DAY);
        c_min = c.get(Calendar.MINUTE);

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
            public void onClick(View view) {
                final EditText scheduleDate, scheduleTime, scheduleInternalPeople, scheduleOS, scheduleLocation, scheduleEventDetails;
                Button scheduleAddIP, scheduleAddOS, scheduleCancel, scheduleSave;
                View v = getLayoutInflater().inflate(R.layout.layout_schedule_style, null, false);

                scheduleDate = v.findViewById(R.id.schedule_date);
                scheduleTime = v.findViewById(R.id.schedule_time);
                scheduleOS = v.findViewById(R.id.schedule_other_stakeholders);
                scheduleInternalPeople = v.findViewById(R.id.schedule_internal_people);
                scheduleLocation = v.findViewById(R.id.schedule_loction);
                scheduleEventDetails = v.findViewById(R.id.schedule_event_details);

                scheduleAddOS = v.findViewById(R.id.add_schedule_other_stakeholders);
                scheduleAddIP = v.findViewById(R.id.add_schedule_internal_people);
                scheduleCancel = v.findViewById(R.id.schedule_cancel);
                scheduleSave = v.findViewById(R.id.schedule_save);

                scheduleDate.setFocusableInTouchMode(false);
                scheduleTime.setFocusableInTouchMode(false);
                scheduleDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog dpd = new DatePickerDialog(ViewDetailsActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                scheduleDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                            }
                        },c_yr,c_month,c_day);
                        DatePicker dp = dpd.getDatePicker();
//                dp.setMinDate(System.currentTimeMillis()-10*24*60*60*1000);
//                dp.setMaxDate(System.currentTimeMillis());
                        dpd.show();
                    }
                });

                scheduleTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerDialog tpd = new TimePickerDialog(ViewDetailsActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (hourOfDay > 12) {
                                    scheduleTime.setText((hourOfDay-12) + ":" + minute+" PM");
                                } else {
                                    scheduleTime.setText(hourOfDay + ":" + minute+" AM");
                                }
                            }
                        }, c_hr, c_min,false);
                        tpd.show();
                    }
                });
                AlertDialog.Builder adb = new AlertDialog.Builder(ViewDetailsActivity.this);
                adb.setView(v);
                adb.setCancelable(false);
                final AlertDialog ad = adb.create();
                scheduleCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ad.dismiss();
                    }
                });
                ad.show();
            }
        });

        fabTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText taskDate, taskOtherStake, taskDetails;
                Button scheduleAddIP, scheduleAddOS, taskCancel, scheduleSave;
                View v = getLayoutInflater().inflate(R.layout.layout_task_style, null, false);
                taskDate = v.findViewById(R.id.task_date);
                taskOtherStake = v.findViewById(R.id.task_other_stakeholders);
                taskDetails = v.findViewById(R.id.task_details);

                scheduleAddOS = v.findViewById(R.id.add_task_other_stakeholders);
                taskCancel= v.findViewById(R.id.task_cancel);
                scheduleSave = v.findViewById(R.id.task_save);

                taskDate.setFocusableInTouchMode(false);
                taskDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog dpd = new DatePickerDialog(ViewDetailsActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                taskDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                            }
                        },c_yr,c_month,c_day);
                        DatePicker dp = dpd.getDatePicker();
//                dp.setMinDate(System.currentTimeMillis()-10*24*60*60*1000);
//                dp.setMaxDate(System.currentTimeMillis());
                        dpd.show();
                    }
                });

                AlertDialog.Builder adb = new AlertDialog.Builder(ViewDetailsActivity.this);
                adb.setView(v);
                adb.setCancelable(false);
                final AlertDialog ad = adb.create();
                taskCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ad.dismiss();
                    }
                });
                ad.show();
            }
        });

        fabMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final EditText meetingDate, meetingTime, meetingInternalPeople, meetingCRM, meetingDuration, meetingPlace;
//                Button meetingAddIP, meetingAddCRM, meetingCancel, meetingSave;
//                View v = getLayoutInflater().inflate(R.layout.layout_meeting_style, null, false);
//                getWindow().setSoftInputMode(
//
//                        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
//
//                );
//                editor = (Editor) v.findViewById(R.id.editor);
////                setUpEditor();
//                meetingDate = v.findViewById(R.id.meeting_date);
//                meetingTime = v.findViewById(R.id.meeting_time);
//                meetingInternalPeople = v.findViewById(R.id.meeting_internal_people);
//                meetingCRM = v.findViewById(R.id.meeting_within_crm);
//                meetingDuration = v.findViewById(R.id.meeting_duration);
//                meetingPlace = v.findViewById(R.id.meeting_place);
//
//                meetingAddIP = v.findViewById(R.id.add_meeting_internal_people);
//                meetingAddCRM = v.findViewById(R.id.add_meeting_within_crm);
//                meetingCancel = v.findViewById(R.id.meeting_cancel);
//                meetingSave = v.findViewById(R.id.meeting_save);
//
//                meetingDate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        DatePickerDialog dpd = new DatePickerDialog(ViewDetailsActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                                meetingDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
//                            }
//                        },c_yr,c_month,c_day);
//                        DatePicker dp = dpd.getDatePicker();
////                dp.setMinDate(System.currentTimeMillis()-10*24*60*60*1000);
////                dp.setMaxDate(System.currentTimeMillis());
//                        dpd.show();
//                    }
//                });
//
//                meetingTime.setOnClickListener(new View.OnClickListener() {
//                   @Override
//                   public void onClick(View v) {
//                       TimePickerDialog tpd = new TimePickerDialog(ViewDetailsActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new TimePickerDialog.OnTimeSetListener() {
//                           @Override
//                           public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                               if (hourOfDay > 12) {
//                                   meetingTime.setText((hourOfDay-12) + ":" + minute+" PM");
//                               } else {
//                                   meetingTime.setText(hourOfDay + ":" + minute+" AM");
//                               }
//                           }
//                       }, c_hr, c_min,false);
//                       tpd.show();
//                   }
//                });
//
//
//
//                AlertDialog.Builder adb = new AlertDialog.Builder(ViewDetailsActivity.this);
//                adb.setView(v);
//                adb.setCancelable(false);
//                final AlertDialog ad = adb.create();
//                meetingCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ad.dismiss();
//                    }
//                });
//                ad.show();
                startActivity(new Intent(ViewDetailsActivity.this, MeetingActivity.class));
            }
        });

        fabNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView attachFile;
                final EditText noteDetails;
                Button noteCancel, noteSend;
                View v = getLayoutInflater().inflate(R.layout.layout_note_style, null, false);
                attachFile = v.findViewById(R.id.note_attach_file);
                noteDetails = v.findViewById(R.id.note_details);
                noteCancel = v.findViewById(R.id.note_cancel);
                noteSend = v.findViewById(R.id.note_send);

                attachFile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("*/*");
                        startActivityForResult(intent, 101);
                    }
                });

                AlertDialog.Builder adb = new AlertDialog.Builder(ViewDetailsActivity.this);
                adb.setView(v);
                adb.setCancelable(false);
                final AlertDialog ad = adb.create();
                noteCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ad.dismiss();
                    }
                });
                ad.show();
            }
        });
    }

    //closes FAB submenus
    private void closeSubMenusFab(){
        layoutFabSchedule.setVisibility(View.GONE);
        layoutFabTask.setVisibility(View.GONE);
        layoutFabMeeting.setVisibility(View.GONE);
        layoutFabNotes.setVisibility(View.GONE);
        fabView.setImageResource(R.drawable.ic_add);
        fabSchedule.startAnimation(fab_close);
        fabTask.setAnimation(fab_close);
        fabMeeting.setAnimation(fab_close);
        fabNotes.setAnimation(fab_close);
        fabView.startAnimation(rotate_Backward);
//        fabView.startAnimation(fab_open);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabSchedule.setVisibility(View.VISIBLE);
        layoutFabTask.setVisibility(View.VISIBLE);
        layoutFabMeeting.setVisibility(View.VISIBLE);
        layoutFabNotes.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
//        fab.setImageResource(R.drawable.ic_close);
        fabSchedule.startAnimation(fab_open);
        fabTask.setAnimation(fab_open);
        fabMeeting.setAnimation(fab_open);
        fabNotes.setAnimation(fab_open);
        fabView.startAnimation(rotate_forward);
        fabExpanded = true;
    }


}

