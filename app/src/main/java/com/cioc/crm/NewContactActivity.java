package com.cioc.crm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

public class NewContactActivity extends Activity {

    EditText newFullName, newEmail, newMobNo, newCompany, newEmailDuplicate, newMobNoDuplicate, newDesignation, newNotes, newLinkedin, newFb;
    TextView newDp, newDpAttach;
    Button saveNewContact;
    Switch genderSwitch;
    ImageView switchProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        newFullName = findViewById(R.id.contacts_full_name);
        newEmail = findViewById(R.id.contacts_email);
        newMobNo = findViewById(R.id.contacts_mobile);
        newCompany = findViewById(R.id.contacts_company);
        newEmailDuplicate = findViewById(R.id.contacts_email_secondary);
        newMobNoDuplicate = findViewById(R.id.contacts_mobile_secondary);
        newDesignation = findViewById(R.id.contacts_designation);
        newNotes = findViewById(R.id.contacts_notes);
        newLinkedin = findViewById(R.id.contacts_linkedin);
        newFb = findViewById(R.id.contacts_facebook);
        newDp = findViewById(R.id.contact_profile_photo);
        newDpAttach = findViewById(R.id.dp_attached);
        saveNewContact = findViewById(R.id.save_newContacts);
        genderSwitch = findViewById(R.id.gender_sw);
        switchProfile = findViewById(R.id.switch_profile);

        genderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    switchProfile.setImageResource(R.drawable.male);
                }
                else switchProfile.setImageResource(R.drawable.woman);
            }
        });


        newDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), 111);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111){
            if (resultCode == RESULT_OK){
                Uri uri = data.getData();
                try {
                    Bitmap bit = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    newDpAttach.setVisibility(View.VISIBLE);
                    newDpAttach.setText("Attached");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
