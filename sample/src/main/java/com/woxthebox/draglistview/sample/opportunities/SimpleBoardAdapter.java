package com.woxthebox.draglistview.sample.opportunities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woxthebox.draglistview.sample.R;
import com.woxthebox.draglistview.sample.relationships.RelationshipActivity;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**Created by Utkarsh on 13/09/18*/

public class SimpleBoardAdapter extends BoardAdapter {
    int header_resource;
    int item_resource;
    int column_footer;
   public static Item opp;

//    public SimpleBoardAdapter instance;
    Context mContext;

    public SimpleBoardAdapter(Context context, ArrayList<SimpleColumn> data, List<Item> itemList) {
        super(context);
        this.mContext = context;
        this.itemList = itemList;

        this.columns = (ArrayList) data;
        this.header_resource = R.layout.column_header_button;
        this.item_resource = R.layout.column_itemopp;
        this.column_footer = R.layout.column_footer;
    }

    public SimpleBoardAdapter() {
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public int getItemCount(int column_position) {
        return columns.get(column_position).objects.size();
    }

    @Override
    public Object createHeaderObject(int column_position) {
        return columns.get(column_position).header_object;

    }

    @Override
    public Object createFooterObject(int column_position) {
        return "Footer " + String.valueOf(column_position);
    }

    @Override
    public Object createItemObject(int column_position, int item_position) {
        return columns.get(column_position).objects.get(item_position);
    }

    @Override
    public boolean isColumnLocked(int column_position) {
        return false;
    }

    @Override
    public boolean isItemLocked(int column_position) {
        return false;
    }

    @Override
    public View createItemView(final Context context, Object header_object, Object item_object, int column_position, int item_position) {
        opp = itemList.get(item_position);
        View item = View.inflate(context, item_resource, null);
            TextView pk=item.findViewById(R.id.pk);
            pk.setText(opp.getPk());
            pk.setVisibility(GONE);
            TextView companyName = item.findViewById(R.id.comp_name);
            companyName.setText(opp.getName());
            TextView dealName = item.findViewById(R.id.emp_title);
            dealName.setText(opp.getCompanyName());
            TextView contactName = item.findViewById(R.id.name);
            contactName.setText(opp.getContactName());
            // textView.setText(columns.get(column_position).objects.get(item_position).toString());
            contactName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, StepView.class);
                    intent.putExtra("dealPk", opp.getPk());
                    mContext.startActivity(intent);
                }
            });

        return  item;

    }

    @Override
    public int maxItemCount(int column_position) {
        return 10;
    }

    @Override
    public void createColumns() {
        super.createColumns();
    }

    @Override
    public View createHeaderView(Context context, Object header_object, int column_position) {

        View view = View.inflate(context, header_resource, null);
        RelativeLayout relativeLayout = view.findViewById(R.id.header_card_relative);
        ImageView imageView=view.findViewById(R.id.categ_images);


            if (columns.get(column_position).header_object.toString().equals("Contacting")) {
                relativeLayout.setBackgroundColor(Color.rgb(22, 160, 133));
                imageView.setBackgroundResource(R.drawable.ic_local_phone_black_24dp);
        }

         if (columns.get(column_position).header_object.toString().equals("Demo/POC")) {
                relativeLayout.setBackgroundColor(Color.rgb(195, 118, 18));
                imageView.setBackgroundResource(R.drawable.ic_tv_black_24dp);
            }


            if (columns.get(column_position).header_object.toString().equals("Requirements")) {
                relativeLayout.setBackgroundColor(Color.rgb(255, 180, 36));
                imageView.setBackgroundResource(R.drawable.ic_menu_black_24dp);
            }


            if (columns.get(column_position).header_object.toString().equals("Proposal")) {
                relativeLayout.setBackgroundColor(Color.rgb(41, 128, 185));
                imageView.setBackgroundResource(R.drawable.pdf_white);
            }


            if (columns.get(column_position).header_object.toString().equals("Negotiation")) {
                relativeLayout.setBackgroundColor(Color.rgb(39, 174, 96));
                imageView.setBackgroundResource(R.drawable.ic_negotiation);
            }


            if (columns.get(column_position).header_object.toString().equals("Conclusion")) {
                relativeLayout.setBackgroundColor(Color.rgb(121, 95, 153));
                imageView.setBackgroundResource(R.drawable.ic_conclusion);
            }

        TextView textView = view.findViewById(R.id.categ);
        textView.setText(columns.get(column_position).header_object.toString());
        return view;
    }

    @Override
    public View createFooterView(Context context, Object footer_object, int column_position) {
        //View column = View.inflate(context, column_footer, null);
        //TextView textView = column.findViewById(R.id.add_card);
       /* TextView textView = (TextView)footer.findViewById(R.id.textView);
        textView.setText((String)footer_object);*/
        return null;
    }

    public static class SimpleColumn extends Column {
        public String title;
        public SimpleColumn(String title, ArrayList<Object> items) {
            super();
            this.title = title;

            this.header_object = new Item(title);
            this.objects = items;
        }
    }
}
