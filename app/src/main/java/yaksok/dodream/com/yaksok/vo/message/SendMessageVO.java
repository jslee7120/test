package yaksok.dodream.com.yaksok.vo.message;

public class SendMessageVO {

    //givingUser”:string
    //         “receivingUser”:string
    //	“content”: string
    private String givingUser,receivingUser,content,regidate;

    public String getGivingUser() {
        return givingUser;
    }

    public void setGivingUser(String givingUser) {
        this.givingUser = givingUser;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(String receivingUser) {
        this.receivingUser = receivingUser;
    }

    public String getRegidate() {
        return regidate;
    }

    public void setRegidate(String regidate) {
        this.regidate = regidate;
    }
}
