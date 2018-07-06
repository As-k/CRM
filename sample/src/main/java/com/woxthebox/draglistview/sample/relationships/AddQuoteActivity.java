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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
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
    ImageView quoteDownload;
    RelativeLayout quoteDownloadLayout;
    Spinner typeDropdown;
    Button addQuote, editProduct;
    TextView productTxt, codeType, code, taxRate;
    LinearLayout llProduct, llProductList;
    TextInputLayout textInputLayout;
    String[] items = new String[]{"Onetime", "Request", "day" , "Hour", "Monthly", "Yearly", "User"};
    List<ProductMeta> productMetaList;
    ServerUrl serverUrl;
    public AsyncHttpClient client;
    ArrayList desc;
    ListView quoteList;
    JSONArray jsonArray = new JSONArray();
    int total = 0;
    String pk;
    boolean editQuote = false;

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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pk = bundle.getString("pk");
            editQuote = bundle.getBoolean("edit");
            if (bundle.getBoolean("download")) {
                quoteDownloadLayout.setVisibility(View.VISIBLE);
            }
            String data = bundle.getString("data");
            try {
                JSONArray dataJson = new JSONArray(data);
                for (int i = 0; i < dataJson.length(); i++) {
                    JSONObject object = dataJson.getJSONObject(i);
                    String currency = object.getString("currency");
                    String type = object.getString("type");
                    String tax = object.getString("tax");
                    String desc = object.getString("desc");
                    String rate = object.getString("rate");
                    String quantity = object.getString("quantity");
                    String taxCode = object.getString("taxCode");
                    String total = object.getString("total");
                    String totalTax = object.getString("totalTax");
                    String subtotal = object.getString("subtotal");
                    jsonArray.put(object);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            QuoteList list = new QuoteList();
            quoteList.setAdapter(list);
            list.notifyDataSetChanged();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.layout_style_spinner, items);
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

    }

    public void init(){
        quoteDownloadLayout = findViewById(R.id.download_quote_layout);
        quoteDownloadLayout.setVisibility(View.GONE);
        quoteDownload = findViewById(R.id.download_quote);
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
        String rateStr = rate.getText().toString().trim();
        String qualityStr = quality.getText().toString().trim();
        String currencyStr = currency.getText().toString().trim();
        String descStr = description.getText().toString().trim();
        String productStr = productTxt.getText().toString().trim();
        String codeStr = code.getText().toString().trim();
        String taxCode[] = codeStr.split("Code : ");
        String codeTypeStr = codeType.getText().toString().trim();
        String taxRateStr = taxRate.getText().toString().trim();
        String taxrate[] = taxRateStr.split("Tax Rate : ");
        String typeStr = typeDropdown.getSelectedItem().toString().toLowerCase();

        if (productClass.getText().equals("")||rateStr.isEmpty()||qualityStr.isEmpty()||descStr.isEmpty()){
            return;
        }
        int total =  Integer.parseInt(rateStr) * Integer.parseInt(qualityStr);
        int totalTax = total * Integer.parseInt(rateStr)/100;
        int subTotal = total + totalTax;

//        data = "{\"data \":[{\"currency\":\""+currenyStr+"\",\"type\":\"onetime\",\"tax\":18,\"desc\":\"desc 1\",\"rate\":250,\"quantity\":6,\"taxCode\":998313,\"total\":1500,\"totalTax\":270,\"subtotal\":1770,\"$$hashKey\":\"object:148\"}]}";
        try {
            JSONObject dataJson = new JSONObject();
            dataJson.put("currency", currencyStr);
            dataJson.put("type", typeStr);
            dataJson.put("tax", taxrate[1]);
            dataJson.put("desc", descStr);
            dataJson.put("rate", rateStr);
            dataJson.put("quantity", qualityStr);
            dataJson.put("taxCode", taxCode[1]);
            dataJson.put("total", ""+total);
            dataJson.put("totalTax", ""+totalTax);
            dataJson.put("subtotal", ""+subTotal);
//            dataJson.put("$$hashKey", ""+currencyStr);
            jsonArray.put(dataJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        QuoteList list = new QuoteList();
        quoteList.setAdapter(list);
        list.notifyDataSetChanged();
        textInputLayout.setVisibility(View.VISIBLE);
        productClass.setVisibility(View.VISIBLE);
        productClass.setText("");
        rate.setText("");
        quality.setText("");
        description.setText("");
        llProduct.setVisibility(View.GONE);
        llProductList.setVisibility(View.GONE);
    }


    class QuoteList extends BaseAdapter{

        @Override
        public int getCount() {
            return jsonArray == null ? 0 : jsonArray.length();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            TextView descriptionList, typeList, priceList, taxRateList, hsnSACList, quantityList, totalTaxList, subTotalList;
            ImageView deleteQuote, editQuote;

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
            editQuote = v.findViewById(R.id.edit_quote);

            try {
                JSONObject obj = jsonArray.getJSONObject(position);
                obj.getString("currency");
                String taxrate[] = obj.getString("tax").split("Tax Rate : ");
                taxRateList.setText(obj.getString("tax"));
                String typestr[] = obj.getString("taxCode").split("Code : ");
                hsnSACList.setText(obj.getString("taxCode"));
                descriptionList.setText(obj.getString("desc"));
                priceList.setText(obj.getString("rate"));
                quantityList.setText(obj.getString("quantity"));
                typeList.setText(obj.getString("type"));
                total = total + Integer.parseInt(obj.getString("total"));
                totalTaxList.setText(obj.getString("totalTax"));
                subTotalList.setText(obj.getString("subtotal"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            deleteQuote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jsonArray.remove(position);
                    QuoteList list = new QuoteList();
                    quoteList.setAdapter(list);
                    list.notifyDataSetChanged();
                }
            });

            editQuote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        JSONObject obj = jsonArray.getJSONObject(position);
                        currency.setText(obj.getString("currency"));
                        client.get(ServerUrl.url+"/api/clientRelationships/productMeta/?code="+obj.getString("taxCode"), new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                                super.onSuccess(statusCode, headers, response);
                                for (int i=0; i<response.length(); i++){
                                    try {
                                        JSONObject object = response.getJSONObject(i);
                                        String description1 = object.getString("description");
                                        productClass.setText(description1);
                                        textInputLayout.setVisibility(View.GONE);
                                        productClass.setVisibility(View.GONE);
                                        llProduct.setVisibility(View.VISIBLE);
                                        llProductList.setVisibility(View.VISIBLE);
                                        productTxt.setText(description1);
                                        codeType.setText("Code Type : "+object.getString("typ"));
                                        code.setText("Code : "+object.getString("code"));
                                        taxRate.setText("Tax Rate : "+object.getString("taxRate"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                            }
                        });
                        description.setText(obj.getString("desc"));
                        rate.setText(obj.getString("rate"));
                        quality.setText(obj.getString("quantity"));
                        String type = obj.getString("type");
                        for (int i=0; i<items.length; i++){
                            if (items[i].toLowerCase().equals(type)){
                                typeDropdown.setSelection(i);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.remove(position);
                    QuoteList list = new QuoteList();
                    quoteList.setAdapter(list);
                    list.notifyDataSetChanged();
                }
            });
            return v;
        }
    }

    public void exitQuote(View v) {
        RequestParams params = new RequestParams();
        params.put("data", jsonArray);
        params.put("deal", ActiveDealsActivity.dealPk);
        params.put("value", total);

        if (!editQuote) {
            client.post(ServerUrl.url + "/api/clientRelationships/contract/", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(AddQuoteActivity.this, "successfully posted", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(AddQuoteActivity.this, "post failure", Toast.LENGTH_SHORT).show();
                }
            });
            finish();
        } else {
            client.patch(ServerUrl.url + "/api/clientRelationships/contract/"+pk+"/", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(AddQuoteActivity.this, "patched success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(AddQuoteActivity.this, "patch failure", Toast.LENGTH_SHORT).show();
                }
            });
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();



    }
}
