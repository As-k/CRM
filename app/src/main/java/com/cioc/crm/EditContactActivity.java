package com.cioc.crm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class EditContactActivity extends Activity {
    EditText editFullName, editEmail, editMobNo, editCompany, editEmailDuplicate, editMobNoDuplicate, editDesignation, editNotes, editLinkedin, editFb;
    TextView editDp, editDpAttach;
    Button saveEditContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        editFullName = findViewById(R.id.edit_full_name);
        editEmail = findViewById(R.id.edit_email);
        editMobNo = findViewById(R.id.edit_mobile);
        editCompany = findViewById(R.id.edit_company);
        editEmailDuplicate = findViewById(R.id.edit_email_secondary);
        editMobNoDuplicate = findViewById(R.id.edit_mobile_secondary);
        editDesignation = findViewById(R.id.edit_designation);
        editNotes = findViewById(R.id.edit_notes);
        editLinkedin = findViewById(R.id.edit_linkedin);
        editFb = findViewById(R.id.edit_facebook);
        editDp = findViewById(R.id.edit_profile_photo);
        editDpAttach = findViewById(R.id.edit_dp_attached);
        saveEditContact = findViewById(R.id.edit_save_newContacts);

        editDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), 112);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 112){
            if (resultCode == RESULT_OK){
                Uri uri = data.getData();
                try {
                    Bitmap bit = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    editDpAttach.setVisibility(View.VISIBLE);
                    editDpAttach.setText("Attached");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
