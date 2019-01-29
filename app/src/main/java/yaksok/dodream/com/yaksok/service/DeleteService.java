package yaksok.dodream.com.yaksok.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;
import yaksok.dodream.com.yaksok.vo.FamilyBodyVO;
import yaksok.dodream.com.yaksok.vo.FamilyDelVO;
import yaksok.dodream.com.yaksok.vo.message.MessageBodyVO;

public interface DeleteService {
    public static String API_URL = "http://54.180.81.180:8080";

    @HTTP(method = "DELETE",path = "/families?",hasBody = true)
    Call<FamilyBodyVO> deleteBody(@Body FamilyDelVO familyDelVO);


}
