package yaksok.dodream.com.yaksok.js;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok.R;
import yaksok.dodream.com.yaksok.js.MedicineVOList;
import yaksok.dodream.com.yaksok.js.PillSearchItem;
import yaksok.dodream.com.yaksok.js.SearchListAdapter;
import yaksok.dodream.com.yaksok.service.UserService;

public class SearchPill extends AppCompatActivity {

    EditText et_PillSearch;
    Button bt_PillSearch;

    Retrofit retrofit;
    UserService userService;

    ListView lv_SearchList;
    SearchListAdapter adapter;

    String day, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpill);

        Intent intent = getIntent();

        et_PillSearch = (EditText)findViewById(R.id.et_PillSearch);
        bt_PillSearch = (Button)findViewById(R.id.bt_PillSearch);

        lv_SearchList = (ListView)findViewById(R.id.lv_SearchList);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        adapter = new SearchListAdapter();
        lv_SearchList.setAdapter(adapter);

        //레트로핏 설정
        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);

        et_PillSearch.setText(intent.getExtras().getString("PillName"));

        if(!et_PillSearch.getText().toString().equals("")) {
            Call<MedicineVOList> call = userService.getSearchPillList(et_PillSearch.getText().toString(), "search");
            System.out.println("@@@@@@@@@@@@@@@@@@@" + et_PillSearch.getText().toString());
            call.enqueue(new Callback<MedicineVOList>() {
                @Override
                public void onResponse(Call<MedicineVOList> call, Response<MedicineVOList> response) {
                    MedicineVOList mVO = response.body();
                    System.out.println("@@@@@@@@@@@@@@@@@@@" + mVO.getStatus());
                    if (mVO.getStatus().equals("200")) {
                        for (int i = 0; i < mVO.getResult().size(); i++) {
                            System.out.println("@@@@@@" + mVO.getResult().get(i).getMedicineNo());
                            adapter.addItem(mVO.getResult().get(i).getMedicineNo(), mVO.getResult().get(i).getName(), mVO.getResult().get(i).getElement());
                            adapter.notifyDataSetChanged();
                        }
                    } else if (mVO.getStatus().equals("204")) {
                    }
                }

                @Override
                public void onFailure(Call<MedicineVOList> call, Throwable t) {
                    System.out.println(t.fillInStackTrace().getMessage());
                }
            });
        }



        bt_PillSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Pill = et_PillSearch.getText().toString();
//                try {
//                    Pill = URLEncoder.encode(Pill,"UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                if(Pill.equals("")||Pill.equals(null)){
                    Toast.makeText(getApplicationContext(), "검색어를 입력하세요", Toast.LENGTH_LONG).show();
                }
                else {
                    Call<MedicineVOList> call = userService.getSearchPillList(Pill, "search");
                    System.out.println("@@@@@@@@@@@@@@@@@@@" + et_PillSearch.getText().toString());
                    call.enqueue(new Callback<MedicineVOList>() {
                        @Override
                        public void onResponse(Call<MedicineVOList> call, Response<MedicineVOList> response) {
                            MedicineVOList mVO = response.body();
                            System.out.println("@@@@@@@@@@@@@@@@@@@" + mVO.getStatus());
                            if (mVO.getStatus().equals("200")) {
                                for (int i = 0; i < mVO.getResult().size(); i++) {
                                    System.out.println("@@@@@@" + mVO.getResult().get(i).getMedicineNo());
                                    adapter.addItem(mVO.getResult().get(i).getMedicineNo(), mVO.getResult().get(i).getName(), mVO.getResult().get(i).getElement());
                                    adapter.notifyDataSetChanged();
                                }
                            } else if (mVO.getStatus().equals("204")) {
                            }
                        }

                        @Override
                        public void onFailure(Call<MedicineVOList> call, Throwable t) {
                            System.out.println(t.fillInStackTrace().getMessage());
                        }

                    });
                }

            }
        });

        lv_SearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                System.out.println("@@@@@@@@@@@@@"+ i+"@@" + l);

                final PillSearchItem item = (PillSearchItem) adapter.getItem(i);
                final int mNum = item.getMedicineNO();
                System.out.println("@@@NUm" + mNum);

                builder.setTitle("알림");
                builder.setMessage("약을 등록하시겠습니까?");
                builder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent resultIntent = new Intent();
                                String number = String.valueOf( item.getMedicineNO());
                                resultIntent.putExtra("result", item.getName());
                                resultIntent.putExtra("number", number);
                                setResult(RESULT_OK,resultIntent);
                                finish();
                            }
                        });
                builder.show();

            }
        });


    }
}