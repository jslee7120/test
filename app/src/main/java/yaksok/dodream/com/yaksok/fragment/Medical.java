package yaksok.dodream.com.yaksok.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import yaksok.dodream.com.yaksok.R;
import yaksok.dodream.com.yaksok.adapter.ShopList;
import yaksok.dodream.com.yaksok.service.ShopService;
import yaksok.dodream.com.yaksok.vo.ShopVO;


public class Medical extends Fragment {

    private Retrofit retrofit;
    private ShopService shopService;
    private ShopList shopList;
    private ListView medical_product_list;
    public Medical() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medical,container,false);
        medical_product_list = (ListView)view.findViewById(R.id.medical_menu_list);
        shopList = new ShopList();
        retrofit = new Retrofit.Builder()
                .baseUrl(shopService.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        shopService = retrofit.create(ShopService.class);

        System.out.print("@@@@@@@@@@@@@@@@@@@@@");

        Call<ShopVO> shopVOCall = shopService.getShopList();
        shopVOCall.enqueue(new Callback<ShopVO>() {
            @Override
            public void onResponse(Call<ShopVO> call, Response<ShopVO> response) {
                //200 : OK
                //204 : OK, but list is null
                //500 : Server Error

                ShopVO shopVO = response.body();


                assert shopVO != null;
                if(shopVO.getStatus().equals("200")){
                    for(int i = 0; i < shopVO.getResult().size(); i++){
                        if(shopVO.getResult().get(i).getProductCategoryry().equals("제품구분 : 의약외품")){
                            shopList.addItem(shopVO.getResult().get(i).getImageUri(),shopVO.getResult().get(i).getProductName(),
                                    shopVO.getResult().get(i).getProductCategoryry(),shopVO.getResult().get(i).getProductEffectCategory());
                            medical_product_list.setAdapter(shopList);
                        }
                        else{

                        }
                    }
                }
                else if(shopVO.getStatus().equals("204")){
                    Toast.makeText(getContext(),"ok but list is null",Toast.LENGTH_SHORT).show();
                }
                else if(shopVO.getStatus().equals("500")){
                    Toast.makeText(getContext(),"sever is error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShopVO> call, Throwable t) {

            }
        });
        return view;
    }

}
