package com.woxthebox.draglistview.sample.opportunities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woxthebox.draglistview.sample.R;

/**
 * Created by admin on 29/06/18.
 */

public class QuoteList extends RecyclerView.Adapter<QuoteList.MyHolder> {
    String[] qu1 = {"Other Photography & Videography and their processing services n.e.c.", "Information technology (IT) design and development services", "Information technology (IT) design and development services"};
    String[] qu2 = {"Onetime", "Request", "Request"};
    String[] qu3 = {"500", "1000", "1000"};
    String[] qu4 = {"18", "18", "18"};
    String[] qu5 = {"435547", "53474", "53474"};
    String[] qu6 = {"2", "4", "4"};
    String[] qu7 = {"6456", "5654", "5654"};
    String[] qu8 = {"33665", "34663", "34663"};
    Context context;

    public QuoteList(Context context ) {
        this.context = context;

    }
    @NonNull
    @Override
    public QuoteList.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.layout_quote_list, parent, false);
        QuoteList.MyHolder myHolder = new QuoteList.MyHolder(v);
        return myHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull QuoteList.MyHolder holder, int position) {
//        HashMap map = (HashMap) arrayList.get(position);
        holder.descriptionList.setText(qu1[position]);//(String)map.get(keys[0]
        holder.typeList.setText(qu2[position]);
        holder.priceList.setText(qu3[position]);
        holder.taxRateList.setText(qu4[position]);
        holder.hsnSACList.setText(qu5[position]);
        holder.quantityList.setText(qu6[position]);
        holder.totalTaxList.setText(qu7[position]);
        holder.subTotalList.setText(qu8[position]);
    }

    @Override
    public int getItemCount() {
        return qu1.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView descriptionList, typeList, priceList, taxRateList, hsnSACList, quantityList, totalTaxList, subTotalList;

        public MyHolder(View v) {
            super(v);
//                View v = getLayoutInflater().inflate(R.layout.layout_quote_list, parent, false);
//
            descriptionList = v.findViewById(R.id.description_list);
            typeList = v.findViewById(R.id.type_list);
            priceList = v.findViewById(R.id.price_list);
            taxRateList = v.findViewById(R.id.tax_rate_list);
            hsnSACList = v.findViewById(R.id.hsn_sac_list);
            quantityList = v.findViewById(R.id.quantity_list);
            totalTaxList = v.findViewById(R.id.total_list);
            subTotalList = v.findViewById(R.id.sub_total_list);


        }
    }
}