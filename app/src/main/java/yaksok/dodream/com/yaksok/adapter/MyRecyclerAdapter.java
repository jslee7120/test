package yaksok.dodream.com.yaksok.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import yaksok.dodream.com.yaksok.ChattingRoom;
import yaksok.dodream.com.yaksok.LoginActivity;
import yaksok.dodream.com.yaksok.R;
import yaksok.dodream.com.yaksok.item.ConnectedFamily;
import yaksok.dodream.com.yaksok.item.RecyclerItem;
import yaksok.dodream.com.yaksok.vo.message.ChatMessage;
import yaksok.dodream.com.yaksok.vo.message.SendMessageVO;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private ArrayList<SendMessageVO> albumList;
    private int itemLayout;
    public ChattingRoom chattingRoom;
    public String getTime;

    /**
     * 생성자
     * @param items
     * @param itemLayout
     */
    public MyRecyclerAdapter(ArrayList<SendMessageVO> items , int itemLayout){

        this.albumList = items;
        this.itemLayout = itemLayout;
//        long now = System.currentTimeMillis();
//        Date date = new Date(now);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        getTime = sdf.format(date);


    }

    /**
     * 레이아웃을 만들어서 Holer에 저장
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout,viewGroup,false);

        return new ViewHolder(view);
    }

    /**
     * listView getView 를 대체
     * 넘겨 받은 데이터를 화면에 출력하는 역할
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        MyRecyclerAdapter.ViewHolder viewHolder1 = viewHolder;


        SendMessageVO item = albumList.get(position);
//        viewHolder.user2.setText(item.getGivingUser());
//        viewHolder.textTitle.setText(item.getReceivingUser());
//        viewHolder.user1.setText(item.getContent());

        if(!albumList.get(position).getGivingUser().equals("")){
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            //layoutParams3.addRule(RelativeLayout.CENTER_IN_PARENT);



            //layoutParams.topMargin = 700;//in my case

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);//in my case
            layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_START);
            layoutParams3.addRule(RelativeLayout.START_OF,R.id.user_context);
            layoutParams3.addRule(RelativeLayout.CENTER_IN_PARENT);
            viewHolder1.textTitle.setLayoutParams(layoutParams);
            viewHolder1.textTitle.setText(albumList.get(position).getContent());
            viewHolder1.textTitle.setBackgroundResource(R.drawable.rightbubble);
            viewHolder1.user2.setText(item.getGivingUser());
            viewHolder1.user2.setVisibility(View.INVISIBLE);
            viewHolder1.user1.setText(item.getReceivingUser());
            viewHolder1.user1.setVisibility(View.INVISIBLE);
            viewHolder1.user1.setLayoutParams(layoutParams2);
            viewHolder1.time.setText(albumList.get(position).getRegidate());
            viewHolder1.time.setLayoutParams(layoutParams3);


            Log.d("givingtimeadatper",albumList.get(position).getRegidate());
            // viewHolder1.giving_time.setLayoutParams(layoutParams3);


        }

        if(albumList.get(position).getReceivingUser().equals(LoginActivity.userVO.getId())){

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

           // layoutParams3.addRule(RelativeLayout.CENTER_IN_PARENT);

            //layoutParams.topMargin = 700;//in my case
            layoutParams.addRule(RelativeLayout.RIGHT_OF,R.id.user2_txt);//in my case
            layoutParams2.addRule(RelativeLayout.BELOW,R.id.user_context);
            layoutParams3.addRule(RelativeLayout.END_OF,R.id.user_context);
            layoutParams3.addRule(RelativeLayout.CENTER_IN_PARENT);
            layoutParams4.addRule(RelativeLayout.BELOW,R.id.user_context);
            viewHolder1.textTitle.setLayoutParams(layoutParams);
            viewHolder1.textTitle.setText(albumList.get(position).getContent());
            viewHolder1.textTitle.setBackgroundResource(R.drawable.leftbubble);
            viewHolder1.user1.setText(item.getReceivingUser());
            viewHolder1.user1.setVisibility(View.INVISIBLE);
            viewHolder1.user2.setLayoutParams(layoutParams2);
            viewHolder1.user2.setText(ChattingRoom.connectedName);
           // viewHolder1.user2.setLayoutParams(layoutParams4);
            viewHolder1.user2.setVisibility(View.VISIBLE);
            viewHolder1.user2.setTextSize(13);
            viewHolder1.time.setText(albumList.get(position).getRegidate());
            viewHolder1.time.setLayoutParams(layoutParams3);

           // viewHolder1.time.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);



      }

      setFadeAnimation(viewHolder.itemView);


    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    /**
     * 뷰 재활용을 위한 viewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView user1, textTitle,user2,time;


        public ViewHolder(View itemView){
            super(itemView);

            user1 = (TextView) itemView.findViewById(R.id.user1_txt);
            user2 = (TextView) itemView.findViewById(R.id.user2_txt);
            textTitle = (TextView) itemView.findViewById(R.id.user_context);
            time = (TextView) itemView.findViewById(R.id.receiving_time);


        }

    }
    public void addItem(String givinguser,String context, String receivinguser){
       SendMessageVO messageVO = new SendMessageVO();
       messageVO.setGivingUser(givinguser);
       messageVO.setContent(context);
       messageVO.setReceivingUser(receivinguser);

       albumList.add(messageVO);
    }
    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }



}



