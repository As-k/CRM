package com.woxthebox.draglistview.sample.relationships;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.ServerUrl;
import com.woxthebox.draglistview.sample.opportunities.OppEventAdapter;
import com.woxthebox.draglistview.sample.opportunities.QuoteList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AddQuoteActivity extends AppCompatActivity {

    EditText rate, quality, currency, description;
    AutoCompleteTextView productClass;
    Spinner typeDropdown;
    Button addQuote, editProduct;
    TextView productTxt, codeType, code, taxRate;
    LinearLayout llProduct, llProductList;
    TextInputLayout textInputLayout;

    String[] qu1 = {"Other Photography & Videography and their processing services n.e.c.", "Information technology (IT) design and development services", "Information technology (IT) design and development services"};
    String[] qu2 = {"Onetime", "Request", "Request"};
    String[] qu3 = {"500", "1000", "1000"};
    String[] qu4 = {"18", "18", "18"};
    String[] qu5 = {"435547", "53474", "53474"};
    String[] qu6 = {"2", "4", "4"};
    String[] qu7 = {"6456", "5654", "5654"};
    String[] qu8 = {"33665", "34663", "34663"};
    List<ProductMeta> productMetaList;
    ServerUrl serverUrl;
    public AsyncHttpClient client;
    ArrayList desc;
    ListView quoteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);

        getSupportActionBar().hide();
        serverUrl = new ServerUrl(this);
        client = serverUrl.getHTTPClient();
        desc = new ArrayList();
        productMetaList = new ArrayList();
        init();

        final String[] items = new String[]{"Onetime", "Request", "day" , "Hour", "Monthly", "Yearly", "User"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        typeDropdown.setAdapter(adapter);

        productClass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString().trim();

                if (str.equals("")){
                    textInputLayout.setVisibility(View.VISIBLE);
                    productClass.setVisibility(View.VISIBLE);
                    llProduct.setVisibility(View.GONE);
                    llProductList.setVisibility(View.GONE);
                    productMetaList.clear();
                }

                client.get(ServerUrl.url+"/api/clientRelationships/productMeta/?description__contains="+str, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        desc.clear();
                        for (int i=0; i<response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                ProductMeta meta = new ProductMeta(object);
                                String description1 = object.getString("description");
                                desc.add(description1);
                                if (productClass.getText().toString().equals(description1)) {
                                    textInputLayout.setVisibility(View.GONE);
                                    productClass.setVisibility(View.GONE);
                                    llProduct.setVisibility(View.VISIBLE);
                                    llProductList.setVisibility(View.VISIBLE);
                                    productTxt.setText(description1);
                                    codeType.setText("Code Type : "+object.getString("typ"));
                                    code.setText("Code : "+object.getString("code"));
                                    taxRate.setText("Tax Rate : "+object.getString("taxRate"));
                                    productMetaList.add(meta);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(AddQuoteActivity.this, android.R.layout.simple_dropdown_item_1line, desc);
                                productClass.setAdapter(adapter1);
                            }
                        },500);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        QuoteList list = new QuoteList();
        quoteList.setAdapter(list);

    }

    public void init(){
        rate = findViewById(R.id.rate);
        productClass = findViewById(R.id.product_service);
        textInputLayout = findViewById(R.id.til_product_service);
        quality = findViewById(R.id.quantity);
        currency = findViewById(R.id.currency);
        description = findViewById(R.id.description);
        typeDropdown = findViewById(R.id.type_spinner);
        quoteList = findViewById(R.id.quote_list);
        addQuote = findViewById(R.id.add_quote_btn);
        editProduct = findViewById(R.id.product_class_btn);
        llProduct = findViewById(R.id.ll_product);
        llProduct.setVisibility(View.GONE);
        llProductList = findViewById(R.id.ll_product_list);
        llProductList.setVisibility(View.GONE);
        productTxt = findViewById(R.id.product_txt);
        code = findViewById(R.id.product_code);
        codeType = findViewById(R.id.product_code_type);
        taxRate = findViewById(R.id.product_tax_rate);

        editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInputLayout.setVisibility(View.VISIBLE);
                productClass.setVisibility(View.VISIBLE);
                productClass.setText("");
                llProduct.setVisibility(View.GONE);
                llProductList.setVisibility(View.GONE);
            }
        });

    }

    public void addQuote(View v){

    }


    class QuoteList extends BaseAdapter{

        @Override
        public int getCount() {
            return qu1.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView descriptionList, typeList, priceList, taxRateList, hsnSACList, quantityList, totalTaxList, subTotalList;
            ImageView deleteQuote;

            View v = getLayoutInflater().inflate(R.layout.layout_quote_list, parent, false);
            descriptionList = v.findViewById(R.id.description_list);
            typeList = v.findViewById(R.id.type_list);
            priceList = v.findViewById(R.id.price_list);
            taxRateList = v.findViewById(R.id.tax_rate_list);
            hsnSACList = v.findViewById(R.id.hsn_sac_list);
            quantityList = v.findViewById(R.id.quantity_list);
            totalTaxList = v.findViewById(R.id.total_list);
            subTotalList = v.findViewById(R.id.sub_total_list);
            deleteQuote = v.findViewById(R.id.del_quote);

            descriptionList.setText(qu1[position]);//(String)map.get(keys[0]
            typeList.setText(qu2[position]);
            priceList.setText(qu3[position]);
            taxRateList.setText(qu4[position]);
            hsnSACList.setText(qu5[position]);
            quantityList.setText(qu6[position]);
            totalTaxList.setText(qu7[position]);
            subTotalList.setText(qu8[position]);

            deleteQuote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AddQuoteActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                }
            });

            return v;
        }
    }
}
