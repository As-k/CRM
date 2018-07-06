package com.woxthebox.draglistview.sample.opportunities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.irshulx.Editor;
import com.github.irshulx.models.EditorTextStyle;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.contacts.MeetingActivity;

import java.util.Calendar;

import me.originqiu.library.EditTag;

public class NewOpportunityActivity extends AppCompatActivity {

    EditText oppoName, oppoCompany, oppoCloseDate, oppoValuation;
    EditTag editTagViewInternal, editTagViewCustomer;
    Spinner oppoCurrency, oppoState;
    AutoCompleteTextView editTagInternal, editTagCustomer;
    TextView minConfidence, maxConfidence;
    SeekBar confidenceSeekBar;
    String[] currencies = new String[]{"INR", "USD"};
    String[] states = {"Contacting","Demo / POC","Requirements","Proposal","Negotiation","Won / Lost"};
    int c_yr, c_month, c_day;

    Editor oppoRequirementEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_opportunity);

        getSupportActionBar().hide();

        init();
        setUpEditor();

        ArrayAdapter<String> oppoCurrencyAdapter = new ArrayAdapter<String>(this, R.layout.layout_style_spinner, currencies);
        oppoCurrency.setAdapter(oppoCurrencyAdapter);
        ArrayAdapter<String> oppoStateAdapter = new ArrayAdapter<String>(this, R.layout.layout_style_spinner, states);
        oppoState.setAdapter(oppoStateAdapter);

        confidenceSeekBar.setProgress(0);
        confidenceSeekBar.setMax(100);
        confidenceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                minConfidence.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        oppoCloseDate.setFocusableInTouchMode(false);
        oppoCloseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(NewOpportunityActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        oppoCloseDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },c_yr,c_month,c_day);
                DatePicker dp = dpd.getDatePicker();
                dpd.show();
            }
        });


    }

    public void init(){
        Calendar c = Calendar.getInstance();
        c_yr = c.get(Calendar.YEAR);
        c_month = c.get(Calendar.MONTH);
        c_day = c.get(Calendar.DAY_OF_MONTH);

        oppoName = findViewById(R.id.oppo_name);
        oppoCompany = findViewById(R.id.oppo_company);
        editTagViewInternal = findViewById(R.id.oppo_tag_view_internal);
        editTagViewCustomer = findViewById(R.id.oppo_tag_view_customer);
        oppoCloseDate = findViewById(R.id.oppo_close_date);
        oppoValuation = findViewById(R.id.oppo_valuation);
        oppoCurrency = findViewById(R.id.currency_spinner);
        oppoState = findViewById(R.id.state_spinner);
        minConfidence = findViewById(R.id.min_confidence);
        maxConfidence = findViewById(R.id.max_confidence);
        confidenceSeekBar = findViewById(R.id.confidence_seekBar);

        oppoRequirementEditor = (Editor) findViewById(R.id.oppo_requirement_editor);


    }


    public void setUpEditor() {
        findViewById(R.id.action_h1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.updateTextStyle(EditorTextStyle.H1);
            }
        });

        findViewById(R.id.action_h2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.updateTextStyle(EditorTextStyle.H2);
            }
        });

        findViewById(R.id.action_h3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.updateTextStyle(EditorTextStyle.H3);
            }
        });

//        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.updateTextStyle(EditorTextStyle.BOLD);
//            }
//        });

        findViewById(R.id.action_Italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.updateTextStyle(EditorTextStyle.ITALIC);
            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.updateTextStyle(EditorTextStyle.INDENT);
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.updateTextStyle(EditorTextStyle.OUTDENT);
            }
        });

        findViewById(R.id.action_bulleted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.insertList(false);
            }
        });

        findViewById(R.id.action_unordered_numbered).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.insertList(true);
            }
        });

        findViewById(R.id.action_hr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.insertDivider();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.openImagePicker();
            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.insertLink();
            }
        });

//        findViewById(R.id.action_map).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editor.insertMap();
//            }
//        });

        findViewById(R.id.action_erase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppoRequirementEditor.clearAllContents();
            }
        });
        //editor.dividerBackground=R.drawable.divider_background_dark;
        //editor.setFontFace(R.string.fontFamily__serif);
//        Map<Integer, String> headingTypeface = getHeadingTypeface();
//        Map<Integer, String> contentTypeface = getContentface();
//        editor.setHeadingTypeface(headingTypeface);
//        editor.setContentTypeface(contentTypeface);
//        editor.setDividerLayout(R.layout.tmpl_divider_layout);
//        editor.setEditorImageLayout(R.layout.tmpl_image_view);
//        editor.setListItemLayout(R.layout.tmpl_list_item);
//        //editor.StartEditor();
//        editor.setEditorListener(new EditorListener() {
//            @Override
//            public void onTextChanged(EditText editText, Editable text) {
//                // Toast.makeText(EditorTestActivity.this, text, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onUpload(Bitmap image, String uuid) {
//                Toast.makeText(MeetingActivity.this, uuid, Toast.LENGTH_LONG).show();
//                editor.onImageUploadComplete("http://www.videogamesblogger.com/wp-content/uploads/2015/08/metal-gear-solid-5-the-phantom-pain-cheats-640x325.jpg", uuid);
//                // editor.onImageUploadFailed(uuid);
//            }
//        });
        oppoRequirementEditor.render();  // this method must be called to start the editor
//        findViewById(R.id.btnRender).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*
//                Retrieve the content as serialized, you could also say getContentAsHTML();
//                */
//                String text = editor.getContentAsSerialized();
//                Intent intent = new Intent(getApplicationContext(), RenderTestActivity.class);
//                intent.putExtra("content", text);
//                startActivity(intent);
//            }
//        });
    }

}
