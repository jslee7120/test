package yaksok.dodream.com.yaksok;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok.service.UserService;
import yaksok.dodream.com.yaksok.vo.BodyVO;
import yaksok.dodream.com.yaksok.vo.UserVO;

public class SignUp extends AppCompatActivity {
    private ImageView sign_profile_imv;
    public static EditText sign_up_name_edt,sign_up_id_edt,sign_up_pw_edt,sign_up_re_pw_edt,sign_up_phone_number_edt,sign_authorization_number,sign_up_email_edt,sign_up_yourself_email;
    private Spinner sign_up_year_spin,sign_up_month_spin,sign_up_day_spin,sign_up_phone_conpany_spin,sign_up_phone_first_spin,sign_up_email_spin;
    private Button sign_up_check_id_btn,sign_up_check_pw_btn,sign_up_check_authorization_num_btn,sign_up_compelte_btn;
    private String age_range;// age_range 넘기기
    private boolean initializing = false;

    private final static int PICK_FROM_CAMERA = 1;
    private final static int PICK_FROM_ALBUM = 2;
    private final static int CROP_FROM_IMAGE = 3;
    
   
    private String id;
    private String  pw;


    private Intent intent;
    private Uri image_uri;
    private String url;
    static String SAMPLEIMG="ic_launcher.png";

    private SpinnerData spinnerData;

    private SharedPreferences loginInformation;

    private Retrofit retrofit;
    private UserService userService;
    private BodyVO bodyVO;
    public String user_email = "",yourself_user_email="";
    private String birthday = "";
    public String user_born_year = "", nowDate;
    int born_year, now_year,age;
    int month,day;
    public Button confirm_email_btn;






 /*   private SQLiteDatabase db;
    private static final String DATABASE_NAME = "myeongjun.db";
    private static final String TABLE_NAME = "HELLO_TABLE";
    private static final String ID = "id";
    private static final String PW = "pw";
    private boolean databaseCreated = false;
    private boolean tableCreated = false;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        retrofit = new Retrofit.Builder()
                .baseUrl(userService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);



    final long now = System.currentTimeMillis();
    Date date = new Date(now);
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
     nowDate = simpleDateFormat.format(date);

     confirm_email_btn = (Button)findViewById(R.id.confirm_email_btn);


//        final MyDatabaseOpenHelper DBHELPER = new MyDatabaseOpenHelper(getApplicationContext(), "YakSok.db", null, 1);

        sign_up_name_edt = (EditText)findViewById(R.id.sign_name_edt);

        sign_up_id_edt = (EditText) findViewById(R.id.sign_id_edt);


        sign_up_pw_edt = (EditText)findViewById(R.id.sign_pw_edt);
        sign_up_re_pw_edt = (EditText)findViewById(R.id.sign_re_pw_edt);

        sign_up_check_id_btn = (Button)findViewById(R.id.sign_check_id_btn);
        sign_up_check_pw_btn = (Button)findViewById(R.id.sign_up_check_pw_btn);

        sign_up_year_spin = (Spinner)findViewById(R.id.sign_year_spin);
        String[] year_spin = getResources().getStringArray(R.array.year);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,year_spin);
        sign_up_year_spin.setAdapter(arrayAdapter);

        sign_up_year_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_born_year =  String.valueOf(sign_up_year_spin.getItemAtPosition(position));
                born_year = Integer.parseInt(user_born_year.substring(0,4));
                now_year  = Integer.parseInt(nowDate.substring(0,4));
                age = now_year - born_year +1;
                System.out.println(age);

                if(age>=90){
                    age_range = "90-99";
                    System.out.println(age_range);
                }
                else if(age>=80){
                    age_range = "80-89";
                    System.out.println(age_range);

                }
                else if(age>=70){
                    age_range = "70-79";
                    System.out.println(age_range);  }
                else if(age>=60){
                    age_range = "60-69";
                    System.out.println(age_range);
                }
                else if(age>=50){
                    age_range = "50-59";
                    System.out.println(age_range);
                }
                else if(age>=40){
                    age_range = "40-49";
                    System.out.println(age_range);
                }
                else if(age>=30){
                    age_range = "30-39";
                    System.out.println(age_range);
                }
                else if(age>=20){
                    age_range = "20-29";
                    System.out.println(age_range);
                }else if(age>=10){
                    age_range = "1-10";
                    System.out.println(age_range);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sign_up_month_spin = (Spinner)findViewById(R.id.sign_month_spin);
        String[] month_spin = getResources().getStringArray(R.array.month);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,month_spin);
        sign_up_month_spin.setAdapter(monthAdapter);

        sign_up_month_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               month = Integer.parseInt(sign_up_month_spin.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sign_up_day_spin = (Spinner)findViewById(R.id.sign_day_spin);
        String[] day_spin = getResources().getStringArray(R.array.days);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,day_spin);
        sign_up_day_spin.setAdapter(dayAdapter);

        sign_up_day_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               day = Integer.parseInt(sign_up_day_spin.getItemAtPosition(position).toString());

               birthday = String.valueOf(month)+"-"+String.valueOf(day);
                Toast.makeText(getApplicationContext(),birthday,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sign_up_phone_conpany_spin = (Spinner)findViewById(R.id.sign_up_phone_company_spin);
        String[] phone_company = getResources().getStringArray(R.array.phone_company);
        ArrayAdapter<String> phoneCompanyAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,phone_company);
        sign_up_phone_conpany_spin.setAdapter(phoneCompanyAdapter);


//
//        sign_up_email_spin = (Spinner)findViewById(R.id.sign_choose_email_spin);
//        String[] kindOfEmails = getResources().getStringArray(R.array.email_example);
//        ArrayAdapter<String> emailAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,kindOfEmails);
//        sign_up_email_spin.setAdapter(emailAdapter);
//
//        sign_up_check_authorization_num_btn = (Button)findViewById(R.id.sign_up_get_autho_btn);
//        sign_authorization_number = (EditText)findViewById(R.id.sign_authorization_number);
//
//

        sign_up_yourself_email = (EditText)findViewById(R.id.sign_email_yourself_edt);
//
//       sign_up_email_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//           @Override
//           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                           user_email = sign_up_email_edt.getText().toString()+"@"+sign_up_email_spin.getItemAtPosition(position);
//                           System.out.println("@@@@@@@@@"+user_email);
//
//
//           }
//
//           @Override
//           public void onNothingSelected(AdapterView<?> parent) {
//
//           }
//       });

        confirm_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_email = sign_up_yourself_email.getText().toString();
                if(checkEmail(user_email)){
                    Toast.makeText(getApplicationContext(),"사용할 수 있는 이메일 입니다.",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"다시 입력하세요.",Toast.LENGTH_LONG).show();
                    sign_up_yourself_email.setText("");
                }


            }
        });




        //회원 가입 완료 버튼
        sign_up_compelte_btn = (Button) findViewById(R.id.sign_up_complete_btn);




        sign_up_check_id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sign_up_id_edt.getText().toString().length()<=6){
                    sign_up_id_edt.setText("");
                    sign_up_id_edt.setFocusable(true);
                    Toast.makeText(getApplicationContext(),"6글자 이상으로 아이디를 입력해주세요.",Toast.LENGTH_LONG).show();
                }
               else{
                    Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_LONG).show();
                }
            }
        });

        sign_up_check_pw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sign_up_pw_edt.getText().toString().length()<=6){
                    sign_up_pw_edt.setText("");
                    sign_up_pw_edt.setFocusable(true);
                    Toast.makeText(getApplicationContext(),"6글자 이상으로 비밀번호를 입력해주세요.",Toast.LENGTH_LONG).show();
                }
                else if(!hasSpecialCharacter(sign_up_pw_edt.getText().toString())){
                    sign_up_pw_edt.setText("");
                    sign_up_pw_edt.setFocusable(true);
                    Toast.makeText(getApplicationContext(),"!,#,* 와 같은 특수 문자를 추가해주세요",Toast.LENGTH_LONG).show();
                }
                else if (!sign_up_pw_edt.getText().toString().equals(sign_up_re_pw_edt.getText().toString())){
                    sign_up_pw_edt.setText("");
                    sign_up_re_pw_edt.setText("");
                    sign_up_pw_edt.setFocusable(true);
                    Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"비밀번호가 적절합니다.",Toast.LENGTH_LONG).show();
                }

            }
        });




        sign_up_phone_number_edt = (EditText)findViewById(R.id.sign_phone_number_edt);//전화번호 보내줘야 함


//        sign_up_email_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

              /*  sgn_profile_imv = (ImageView) findViewById(R.id.sign_profile_imv);
                 sign_profile_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUp.this);
                alertDialog.setTitle("프로필 사진 선택");

                alertDialog.setPositiveButton("사진 찍기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        takePicture();

                    }
                }).setNegativeButton("앨범에서 선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, PICK_FROM_ALBUM);

                    }
                }).setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                AlertDialog alertDialog1 = alertDialog.create();
                alertDialog1.show();
            }
        });*/
        sign_up_check_authorization_num_btn = (Button)findViewById(R.id.sign_up_get_autho_btn);
        sign_up_check_authorization_num_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.sign_authorization_layout);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });



        sign_up_compelte_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DBHELPER.insert(sign_up_id_edt.getText().toString(),sign_up_pw_edt.getText().toString() );
//                Toast.makeText(getApplicationContext(), DBHELPER.getResult()+id, Toast.LENGTH_LONG).show();


                ToServer();

              /*  loginInformation = getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor editor = loginInformation.edit();
                editor.putString("id",sign_up_id_edt.getText().toString());
                editor.putString("pw",sign_up_pw_edt.getText().toString());
                editor.putBoolean("auto",true);
                editor.apply();//이 부분이 있어야 저장이



                startActivity(new Intent(getApplicationContext(),MainPageActivity.class));
*/






            }
        });

    }/*
    	“id” : “…” NOT NULL
	“phoneNumber” : ”…“ NOT NULL
	“pw” : ”…“ NOT NULL
	“nickname” : ”…“ NOT NULL
	“profileImagePath” : “…”
	“email” : “…”
 	“birthday” : “…”
	“ageRange” : “…”
	“userType”:”…”	  NOT NULL   char형

*/

    //code
    //201 : OK
    //400 : 잘못된요청(userType)
    //403 : 아이디중복
    //409 : 입력된 핸드폰번호로 가입된 계정 존재
    //500 : Server Error

    private void ToServer() {

        LoginActivity.userVO = new UserVO();
        System.out.println("@@@@" +sign_up_id_edt.getText().toString() + sign_up_phone_number_edt.getText().toString() + sign_up_pw_edt.getText().toString() + sign_up_name_edt.getText().toString()) ;
        LoginActivity.userVO.setId(sign_up_id_edt.getText().toString());
        LoginActivity.userVO.setPhoneNumber(sign_up_phone_number_edt.getText().toString());
        LoginActivity.userVO.setPw(sign_up_pw_edt.getText().toString());
        LoginActivity.userVO.setNickname(sign_up_name_edt.getText().toString());
        LoginActivity.userVO.setProfileImagePath("");
        LoginActivity.userVO.setEmail(user_email);
        LoginActivity.userVO.setBirthday(birthday);
        LoginActivity.userVO.setAgeRange("10-19");
        LoginActivity.userVO.setUserType("G");
        System.out.println(LoginActivity.userVO);
        Call<BodyVO> call = userService.postSignUp(LoginActivity.userVO);

       call.enqueue(new Callback<BodyVO>() {
           @Override
           public void onResponse(Call<BodyVO> call, Response<BodyVO> response) {
               BodyVO bodyVO = response.body();
               Log.d("server","server");
               Log.d("server_before","sssssssss");

               Toast.makeText(getApplicationContext(),bodyVO.getStatus(),Toast.LENGTH_LONG).show();
               if(bodyVO.getStatus().equals("201")){
                   startActivity(new Intent(getApplicationContext(),AddYourFmaily.class));
                   Toast.makeText(getApplicationContext(),"가입 성공 되었습니다.",Toast.LENGTH_LONG).show();

                   finish();
               }
               else if (bodyVO.getStatus().equals("400")) {
                   Toast.makeText(getApplicationContext(), "잘못된 요청", Toast.LENGTH_SHORT).show();
               }
               else if (bodyVO.getStatus().equals("403")) {
                   Toast.makeText(getApplicationContext(), "아이디 중복", Toast.LENGTH_SHORT).show();
               }else if (bodyVO.getStatus().equals("409")) {
                   Toast.makeText(getApplicationContext(), "입력된 핸드폰 계정 아이디 존재", Toast.LENGTH_SHORT).show();

               } else if (bodyVO.getStatus().equals("500")) {
                   Toast.makeText(getApplicationContext(), "서버 오류", Toast.LENGTH_SHORT).show();
               }


           }

           @Override
           public void onFailure(Call<BodyVO> call, Throwable t) {
            Toast.makeText(getApplicationContext(),"이상있음",Toast.LENGTH_LONG).show();
            Log.d("ttttttttt",t.getMessage());
           }
       });
    }

//    private void takePicture() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        File file = new File(Environment.getExternalStorageDirectory(),SAMPLEIMG);
//
//
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
//        startActivityForResult(intent,PICK_FROM_CAMERA);
//
//    }


//    Bitmap loadPicture(){
//
////사진찍은 것을 로드 해오는데 사이즈를 조절하도록하자!!일단 파일을 가져오고
//
//        File file=new File(Environment.getExternalStorageDirectory(),SAMPLEIMG);
//
////현재사진찍은 것을 조절하구이해서 조절하는 클래스를 만들자.
//
//        BitmapFactory.Options options=new BitmapFactory.Options();
//
////이제 사이즈를 설정한다.
//
//        options.inSampleSize=4;
//
////그후에 사진 조정한것을 다시 돌려보낸다.
//
//        return BitmapFactory.decodeFile(file.getAbsolutePath(),options);
//
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        if(resultCode != RESULT_OK){
//            return ;
//        }
//
//        switch (requestCode){
//
//            case PICK_FROM_CAMERA:{
//                sign_profile_imv.setImageBitmap(loadPicture());
//                break;
//            }
//            case PICK_FROM_ALBUM:{
//                sign_profile_imv.setImageURI(data.getData());
//                sign_profile_imv.setBackground(new ShapeDrawable(new OvalShape()));
//                sign_profile_imv.setClipToOutline(true);
//                break;
//
//            }
//
//
//
//        }
//
//
//    }

    public static boolean hasSpecialCharacter(String string){
        if(TextUtils.isEmpty(string)){
            return false;
        }
        else{
            for(int i = 0; i < string.length(); i++){
                if(!Character.isLetterOrDigit(string.charAt(i))){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidPw(String pw){
        boolean returnValue = false;

        String regex = "^[a-zA-Z0-9!@.#$%^&*?_~]{7,12}$";


        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(pw);

        if (m.matches()) {
            returnValue = true;

        }

        return returnValue;


    }
    public static boolean checkEmail(String email){
        boolean returnValue = false;

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) return true;
        else return false;

    }



}
