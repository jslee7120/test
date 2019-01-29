package yaksok.dodream.com.yaksok.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok.R;
import yaksok.dodream.com.yaksok.item.ConnectedFamily;
import yaksok.dodream.com.yaksok.item.FamilyItem;

public class FamilyConnectedAdapter extends BaseAdapter{
    private ArrayList<ConnectedFamily> familyItems = new ArrayList<>();
    @Override
    public int getCount() {
        return familyItems.size();
    }

    @Override
    public Object getItem(int position) {
        return familyItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final int pos = position;
       final Context context = parent.getContext();

       if(convertView == null){
           LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = layoutInflater.inflate(R.layout.familyconnectedofuser,parent,false);
       }

        TextView name = (TextView)convertView.findViewById(R.id.user_name);
        //ImageView user_pic  = (ImageView)convertView.findViewById(R.id.user_pic);

       ConnectedFamily familyItem = familyItems.get(position);
       name.setText(familyItem.getName());

        return convertView;
    }

    public void addItem(String name){
        ConnectedFamily familyItem = new ConnectedFamily();

        familyItem.setName(name);
        familyItem.setUser_pic(R.drawable.user_pic);
        familyItem.setGoToRight(R.drawable.gotoright);

        familyItems.add(familyItem);
    }
}
