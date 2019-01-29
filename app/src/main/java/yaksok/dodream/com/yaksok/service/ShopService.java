package yaksok.dodream.com.yaksok.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import yaksok.dodream.com.yaksok.vo.FindFamilyVO;
import yaksok.dodream.com.yaksok.vo.ShopVO;

public interface ShopService {
    public static final String API_URL = "http://54.180.81.180:8080";
    @GET("/products")
    Call<ShopVO> getShopList();
}
