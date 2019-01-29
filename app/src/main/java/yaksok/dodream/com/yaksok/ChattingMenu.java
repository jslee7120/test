package yaksok.dodream.com.yaksok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok.adapter.FamilyConnectedAdapter;
import yaksok.dodream.com.yaksok.item.ConnectedFamily;
import yaksok.dodream.com.yaksok.service.MessageService;
import yaksok.dodream.com.yaksok.service.UserService;
import yaksok.dodream.com.yaksok.vo.FindFamilyVO;

public class ChattingMenu extends AppCompatActivity {
    public ListView listView;
    public ArrayList<ConnectedFamily> familyArrayList;
    public FamilyConnectedAdapter familyConnectedAdapter;
    public Retrofit retrofit,retrofit_message;
    public UserService userService;
    public MessageService messageService;
    public String user2_id;
    public Intent connected_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_menu);
       // overridePendingTransition(R.anim.rignt_anim,R.anim.left_in_anim);


        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        userService = retrofit.create(UserService.class);

        Call<FindFamilyVO> findFamilyVOCall = userService.getConnectedFamilyInfo(LoginActivity.userVO.getId());
        findFamilyVOCall.enqueue(new Callback<FindFamilyVO>() {
            @Override
            public void onResponse(Call<FindFamilyVO> call, Response<FindFamilyVO> response) {
                FindFamilyVO findFamilyVO = response.body();
                Toast.makeText(getApplicationContext(),findFamilyVO.getStatus(),Toast.LENGTH_SHORT).show();

                if (findFamilyVO.getStatus().equals("200")) {

                        for(int i = 0; i < findFamilyVO.getResult().size();i++){
                        familyConnectedAdapter.addItem(findFamilyVO.getResult().get(i).getNickName());
                        listView.setAdapter(familyConnectedAdapter);
                    }
                } else if (findFamilyVO.getStatus().equals("204")) {
                    Toast.makeText(getApplicationContext(), "상대의 계정이 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                } else if (findFamilyVO.getStatus().equals("400")) {
                    Toast.makeText(getApplicationContext(), "잘못된 요청입니다.", Toast.LENGTH_LONG).show();
                } else if (findFamilyVO.getStatus().equals("500")) {
                    Toast.makeText(getApplicationContext(), "서버 오루 입니다..", Toast.LENGTH_LONG).show();
                }

            }


            @Override
            public void onFailure(Call<FindFamilyVO> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


        listView = (ListView)findViewById(R.id.family_connected_listview);
        familyConnectedAdapter = new FamilyConnectedAdapter();
        familyArrayList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                Intent intent = new Intent(getApplicationContext(),ChattingRoom.class);
//                intent.putExtra("user2id",)
               // Toast.makeText(getApplicationContext(),"눌림",Toast.LENGTH_SHORT).show();
                Call<FindFamilyVO> findFamilyVOCall = userService.getConnectedFamilyInfo(LoginActivity.userVO.getId());
                findFamilyVOCall.enqueue(new Callback<FindFamilyVO>() {
                    @Override
                    public void onResponse(Call<FindFamilyVO> call, Response<FindFamilyVO> response) {
                        FindFamilyVO findFamilyVO = response.body();
                        Toast.makeText(getApplicationContext(),findFamilyVO.getStatus(),Toast.LENGTH_SHORT).show();

                        if (findFamilyVO.getStatus().equals("200")) {

                              Intent intent = new Intent(getApplicationContext(),ChattingRoom.class);
                              intent.putExtra("user2",findFamilyVO.getResult().get(position).getUserId());
                              intent.putExtra("name",findFamilyVO.getResult().get(position).getNickName());
                              startActivity(intent);


                        } else if (findFamilyVO.getStatus().equals("204")) {
                            Toast.makeText(getApplicationContext(), "상대의 계정이 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                        } else if (findFamilyVO.getStatus().equals("400")) {
                            Toast.makeText(getApplicationContext(), "잘못된 요청입니다.", Toast.LENGTH_LONG).show();
                        } else if (findFamilyVO.getStatus().equals("500")) {
                            Toast.makeText(getApplicationContext(), "서버 오루 입니다..", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FindFamilyVO> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });

    }

}
