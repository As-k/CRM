package com.cioc.crm;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewContactFragment extends Fragment {

    EditText new_full_name, new_email, new_mob_no, new_company, new_email_duplicate, new_mob_no_duplicate, new_designation, new_notes, new_linkedin, new_fb;
    TextView new_dp, new_dp_attach;
    Button saveNewContact;


    public NewContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_contact, container, false);

        new_full_name = v.findViewById(R.id.contacts_full_name);
        new_email = v.findViewById(R.id.contacts_email);
        new_mob_no = v.findViewById(R.id.contacts_mobile);
        new_company = v.findViewById(R.id.contacts_company);
        new_email_duplicate = v.findViewById(R.id.contacts_email_secondary);
        new_mob_no_duplicate = v.findViewById(R.id.contacts_mobile_secondary);
        new_designation = v.findViewById(R.id.contacts_designation);
        new_notes = v.findViewById(R.id.contacts_notes);
        new_linkedin = v.findViewById(R.id.contacts_linkedin);
        new_fb = v.findViewById(R.id.contacts_facebook);
        new_dp = v.findViewById(R.id.contact_profile_photo);
        new_dp_attach = v.findViewById(R.id.dp_attached);
        saveNewContact = v.findViewById(R.id.save_newContacts);

        new_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), 111);
            }
        });



        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111){
            if (resultCode == getActivity().RESULT_OK){
                Uri uri = data.getData();
                try {
                    Bitmap bit = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                    new_dp_attach.setVisibility(View.VISIBLE);
                    new_dp_attach.setText("Attached");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
