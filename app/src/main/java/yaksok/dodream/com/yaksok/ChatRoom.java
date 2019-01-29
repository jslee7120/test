package yaksok.dodream.com.yaksok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import yaksok.dodream.com.yaksok.vo.message.SendMessageVO;

public class ChatRoom {
    public static String givingUser;
    public static String content;
    public static String recevingUser;
    public static String regidate;

    public static String getGivingUser() {
        return givingUser;
    }

    public static void setGivingUser(String givingUser) {
        ChatRoom.givingUser = givingUser;
    }

    public static String getContent() {
        return content;
    }

    public static void setContent(String content) {
        ChatRoom.content = content;
    }

    public static String getRecevingUser() {
        return recevingUser;
    }

    public static void setRecevingUser(String recevingUser) {
        ChatRoom.recevingUser = recevingUser;
    }

    public static String getRegidate() {
        return regidate;
    }

    public static void setRegidate(String regidate) {
        ChatRoom.regidate = regidate;
    }

  /*  SendMessageVO sendMessageVO = new SendMessageVO();
                        sendMessageVO.setGivingUser(bodyVO.getResult().get(i).getGivingUser());
                        sendMessageVO.setContent(bodyVO.getResult().get(i).getContent());
                        sendMessageVO.setReceivingUser(bodyVO.getResult().get(i).getReceivingUser());
                        sendMessageVO.setRegidate(bodyVO.getResult().get(i).getRegiDate().substring(11, 16));*/
}
