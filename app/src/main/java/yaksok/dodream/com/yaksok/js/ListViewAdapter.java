package yaksok.dodream.com.yaksok.js;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import yaksok.dodream.com.yaksok.R;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<PillListItem> pillListItems = new ArrayList<PillListItem>() ;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return pillListItems.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pill_list_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView pill_img = (ImageView) convertView.findViewById(R.id.pill_img);
        TextView tvPillName = (TextView) convertView.findViewById(R.id.tvPillName) ;
        ImageView pill_gotoright = (ImageView) convertView.findViewById(R.id.pill_gotoright);



        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        PillListItem listItem = pillListItems.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        pill_img.setImageResource(R.drawable.pillimg);
        tvPillName.setText(listItem.getName());
        pill_gotoright.setImageResource(R.drawable.gotoright);



        return convertView;
    }


    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return pillListItems.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String Name) {
        PillListItem item = new PillListItem();

        //item.setPillimg(R.drawable.pillimg);
        item.setName(Name);
        //item.setGotoright(R.drawable.gotoright);


        pillListItems.add(item);
    }
}
