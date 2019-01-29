package yaksok.dodream.com.yaksok;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok.adapter.FamilyFindAdapter;
import yaksok.dodream.com.yaksok.item.FamilyItem;
import yaksok.dodream.com.yaksok.service.DeleteService;
import yaksok.dodream.com.yaksok.service.UserService;
import yaksok.dodream.com.yaksok.vo.FamilyBodyVO;
import yaksok.dodream.com.yaksok.vo.FamilyDelVO;
import yaksok.dodream.com.yaksok.vo.FamilyVO;
import yaksok.dodream.com.yaksok.vo.BodyVO;
import yaksok.dodream.com.yaksok.vo.FindFamilyVO;

public class AddYourFmaily extends AppCompatActivity {
    public EditText fmaily_number_edt;
    public Button family_find_btn, family_find_skip_btn,complete_btn,deleteFamilyBtn;
    public ListView family_list_view;
    public Retrofit retrofit,retorofit2;
    public UserService userService;
    public DeleteService deleteService;
    public FamilyFindAdapter adapter;
    public ArrayList<FamilyItem> familyItems;
    public  AlertDialog.Builder dialog;
    public static FamilyVO familyVO;
    public String family_user_id = "";
    public boolean isAddedFamily = false;//김대표

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_your_fmailies);

        fmaily_number_edt = (EditText) findViewById(R.id.findFamily_edt);
        family_find_btn = (Button) findViewById(R.id.findFamily_btn);
        family_list_view = (ListView) findViewById(R.id.family_list);
        family_find_skip_btn = (Button) findViewById(R.id.family_skip_btn);
        complete_btn = (Button)findViewById(R.id.family_complete_btn);
      //  deleteFamilyBtn = (Button)findViewById(R.id.deleteFamily_btn);






         dialog = new AlertDialog.Builder(this);

        familyItems = new ArrayList<>();

        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);



        retorofit2 = new Retrofit.Builder()
                .baseUrl(deleteService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        deleteService = retrofit.create(DeleteService.class);



        adapter = new FamilyFindAdapter(this,familyItems,R.layout.family_list_item);


       alreadyConnectedFamily();


        family_find_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<FindFamilyVO> findFamilyVOCall = userService.getUserList(fmaily_number_edt.getText().toString(), "phoneNumber");
                findFamilyVOCall.enqueue(new Callback<FindFamilyVO>() {
                    @Override
                    public void onResponse(Call<FindFamilyVO> call, Response<FindFamilyVO> response) {
                        FindFamilyVO findFamilyVO = response.body();

                        if (findFamilyVO.getStatus().equals("200")) {
                            isAddedFamily = true;
//                           Log.d("!!!!!!!!!!!!!!!!",findFamilyVO.getResult().get(1).getNickName()+""+findFamilyVO.getResult().get(0).getUserId());
//                            Toast.makeText(getApplicationContext(),findFamilyVO.getResult().get(0).getProfileImagePath(),Toast.LENGTH_LONG).show();

                            for (int i = 0; i < findFamilyVO.getResult().size(); i++) {
                                adapter.addItem(findFamilyVO.getResult().get(i).getNickName());

                                family_list_view.setAdapter(adapter);
                                familyVO = new FamilyVO();

                                // userVO.setId(sign_up_id_edt.getText().toString());
                                //        userVO.setPhoneNumber(sign_up_phone_number_edt.getText().toString());
                                //        userVO.setPw(sign_up_pw_edt.getText().toString());
                                //        userVO.setNickname(sign_up_name_edt.getText().toString());
                                //        userVO.setProfileImagePath("");
                                //        userVO.setEmail(user_email);
                                //        userVO.setBirthday(birthday);
                                //        userVO.setAgeRange("10-19");
                                //        userVO.setUserType("G");
                                family_user_id = findFamilyVO.getResult().get(i).getUserId();
                            }


//                              if(family_list_view != null){
////                                  family_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                                      @Override
////                                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////
////
////                                                                        }


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

        family_list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                FamilyDelVO familyDelVO = new FamilyDelVO();
                familyDelVO.setUser_1(LoginActivity.userVO.getId());
                familyDelVO.setUser_2(String.valueOf(family_list_view.getItemAtPosition(position)));

                Call<FamilyBodyVO> delectionCall = deleteService.deleteBody(familyDelVO);

                delectionCall.enqueue(new Callback<FamilyBodyVO>() {
                    @Override
                    public void onResponse(Call<FamilyBodyVO> call, Response<FamilyBodyVO> response) {
                        FamilyBodyVO familyBodyVO = response.body();
                        if(familyBodyVO.getStatus().equals("201")){
                            Toast.makeText(getApplicationContext(),"삭제되었습니다.",Toast.LENGTH_LONG).show();
                            familyItems.remove(position);
                            adapter.notifyDataSetChanged();
                            adapter.notifyDataSetInvalidated();

                            // alreadyConnectedFamily();
                        }else if(familyBodyVO.getStatus().equals("500")){
                            Toast.makeText(getApplicationContext(),"서버 에러입니다.",Toast.LENGTH_LONG).show();

                        }

                    }


                    @Override
                    public void onFailure(Call<FamilyBodyVO> call, Throwable t) {

                    }
                });

                return false;
            }
        });

      /*  deleteFamilyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });*/
//        if(isAddedFamily){
//
//        }
//        else{
//            Toast.makeText(getApplicationContext(),"가족을 추가 해주시거나 선택해주십시오",Toast.LENGTH_LONG).show();
//        }

        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),LoginActivity.userVO.getId(),Toast.LENGTH_LONG).show();
                if(isAddedFamily){
                    startActivity(new Intent(getApplicationContext(),MainPageActivity.class));

                }
            }
        });
        family_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = ((FamilyItem)adapter.getItem(position)).getName();
                Toast.makeText(AddYourFmaily.this, name+"이름이 맞습니까? ", Toast.LENGTH_SHORT).show();
                dialog.setTitle("가족찾기");
                dialog.setMessage(name+"을 가족으로 등록 하시겠습니까?");
                dialog.setCancelable(false);
                String user_id = "";

//                switch (LoginActivity.userType){
//                    case "G":
//                        user_id = SignUp.userVO.getId();
//                        break;
//                    case "K":
//                        user_id = String.valueOf(Kakao_User_Info.id);
//                        break;
//                    case "N":
//                        user_id = Naver_User_Info.getNaver_id();
//                        break;
//
//                }
                final String finalUser_id = user_id;
                dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fmaily_number_edt.setText("");

                      FamilyVO familyVO = new FamilyVO();
                      familyVO.setUser_1(LoginActivity.userVO.getId());
                      familyVO.setUser_2(family_user_id);
                        //code
                        //201 : OK
                        //403 : 삽입시 중복
                        //500 : Server Error
                        Call<BodyVO> call = userService.postRegisterFamily(familyVO);
                        call.enqueue(new Callback<BodyVO>() {
                            @Override
                            public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
                                BodyVO bodyVO = response.body();
                                assert bodyVO != null;
                                switch (bodyVO.getStatus()) {
                                    case "201":
                                        Toast.makeText(getApplicationContext(), "가족 추가가 되었습니다.", Toast.LENGTH_LONG).show();
                                        break;
                                    case "403":
                                        Toast.makeText(getApplicationContext(), "삽입시 중복이 됩니다.", Toast.LENGTH_LONG).show();
                                        break;
                                    case "500":
                                        Toast.makeText(getApplicationContext(), "서버 에러", Toast.LENGTH_LONG).show();
                                        break;
                                }

                            }

                            @Override
                            public void onFailure(Call<BodyVO> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fmaily_number_edt.setText("");
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();

        getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.


        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.activity_customize_actionbar, null);

        actionBar.setCustomView(actionbar);

        //액션바 양쪽 공백 없애기
        Toolbar parent = (Toolbar) actionbar.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_setting:
                Toast.makeText(getApplicationContext(), "눌림", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SettingPage.class);
                startActivity(intent);
                return true;
//                startActivity(new Intent(getApplicationContext(),SettingPage.class));
//                return  true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void alreadyConnectedFamily() {

        userService = retrofit.create(UserService.class);

        Call<FindFamilyVO> findFamilyVOCall = userService.getConnectedFamilyInfo(LoginActivity.userVO.getId());
        findFamilyVOCall.enqueue(new Callback<FindFamilyVO>() {
            @Override
            public void onResponse(Call<FindFamilyVO> call, Response<FindFamilyVO> response) {
                FindFamilyVO findFamilyVO = response.body();
                Toast.makeText(getApplicationContext(),findFamilyVO.getStatus(),Toast.LENGTH_SHORT).show();

                if (findFamilyVO.getStatus().equals("200")) {

                    for(int i = 0; i < findFamilyVO.getResult().size();i++){
                        adapter.addItem(findFamilyVO.getResult().get(i).getNickName());
                        family_list_view.setAdapter(adapter);
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

    }


}




        //===전화번호를 사용하여 유저의 정보의 리스트를 가지고온다=====
//HTTP GET   users/{item} ? itemTyp
//ex) /01027250856 ? itemType=phoneNumber
//
//Host:  http://54.180.81.180:8080/users/010?itemType=phoneNumber
//
//request path variable  -  item
//request query paramter - itemType
//
//response
//BODY{
//	“status” : “code”
//	“result” : “UserVO” List
//
//}
//
//code
//200 : OK
//204: 계정 존재하지않음(null반환)
//400: 잘못된 요청(itemType)
//500 : Server Error








