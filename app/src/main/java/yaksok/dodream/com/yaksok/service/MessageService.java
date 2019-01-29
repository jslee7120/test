package yaksok.dodream.com.yaksok.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import yaksok.dodream.com.yaksok.vo.BodyVO;
import yaksok.dodream.com.yaksok.vo.UserVO;
import yaksok.dodream.com.yaksok.vo.message.MessageBodyVO;
import yaksok.dodream.com.yaksok.vo.message.MessageResultBodyVO;
import yaksok.dodream.com.yaksok.vo.message.MessageVO;
import yaksok.dodream.com.yaksok.vo.message.SendMessageVO;

public interface MessageService {
    public static final String API_URL = "http://54.180.81.180:8080";

    @GET("/messages/{user1}/{user2}")
    Call<MessageBodyVO> getTheChatting(@Path("user1") String userId, @Path("user2") String user2);

    @POST("/messages")
    Call<MessageResultBodyVO>sendAmeesage(@Body SendMessageVO messageVO);
    //===두아이디를 사용해 채팅메세지를 최신순으로 가져온다. (정렬순은 얘기해주면 바꿔드림)=====
    //HTTP GET /messages/{user1}/{user2}
    //Host:  http://54.180.81.180:8080/messages/audwns1029/gam7325
    //
    //request path variable  -  userId
    //
    //response
    //BODY{
    //    "result": [MessageVOList],
    //    "status": “code”
    //}
    //
    //code
    //200 : OK
    //204 : 값없음(null반환)
    //500 : Server Error
    //


    //===메세지를 보낸다.=====
    //HTTP POST /messages
    //Host:  http://54.180.81.180:8080
    //
    //request
    //BODY{“givingUser”:string
    //         “receivingUser”:string
    //	“content”: string
    //}
    //
    //response
    //BODY{
    //	“status” : “code”
    //}
    //
    //code
    //201 : OK
    //400: FCM error
    //500 : Server Error
}
