package yaksok.dodream.com.yaksok.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import yaksok.dodream.com.yaksok.R;
import yaksok.dodream.com.yaksok.item.ConnectedFamily;
import yaksok.dodream.com.yaksok.item.ShopItem;

public class ShopList extends BaseAdapter{
    private ArrayList<ShopItem> shopItems = new ArrayList<>();
    Bitmap bitmap;
    @Override
    public int getCount() {
        return shopItems.size();
    }

    @Override
    public Object getItem(int position) {
        return shopItems.get(position);
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
           assert layoutInflater != null;
           convertView = layoutInflater.inflate(R.layout.shoplistitem,parent,false);
       }


        ImageView imageView = (ImageView)convertView.findViewById(R.id.product_img);
        TextView title = (TextView)convertView.findViewById(R.id.product_title);
        TextView detial1 = (TextView)convertView.findViewById(R.id.product_detail);
        TextView detail2 = (TextView)convertView.findViewById(R.id.product_detail2);

        final ShopItem shopItem = shopItems.get(position);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(shopItem.getImg());
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setDoInput(true);//서버로부터 응답 수신
                    httpURLConnection.connect();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);


                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start(); //thread 실행

        try{
            thread.join(); // 메인 쓰레드는 별도의 쓰레드가 작업을 완료할 때 까지 대기해야한다. join()을 호출하여 별도의 작업 Thread가 종료 될 떄까지 메인 thread를 기다리게 한다.

            imageView.setImageBitmap(bitmap);
            //Log.d("@@@@@@@@@@@@@@",bitmap.toString());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        title.setText(shopItem.getTitle());
        detial1.setText(shopItem.getDetail1());
        detail2.setText(shopItem.getDetail2());


        return convertView;
    }

    public void addItem(String img,String title,String detail1, String detial2){
       ShopItem shopItem = new ShopItem();
       shopItem.setImg(img);
       shopItem.setTitle(title);
       shopItem.setDetail1(detail1);
       shopItem.setDetail2(detial2);

        shopItems.add(shopItem);
    }
}
