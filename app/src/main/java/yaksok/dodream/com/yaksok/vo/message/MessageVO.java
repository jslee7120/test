package yaksok.dodream.com.yaksok.vo.message;

public class MessageVO {
    private int messageNo;
    private int givingUserMyMedicineNo;
    private String givingUser;//사용자
    private String receivingUser;//상대방
    private String content;
    private String regiDate;

    public int getMessageNo() {
        return messageNo;
    }

    public void setMessageNo(int messageNo) {
        this.messageNo = messageNo;
    }

    public int getGivingUserMyMedicineNo() {
        return givingUserMyMedicineNo;
    }

    public void setGivingUserMyMedicineNo(int givingUserMyMedicineNo) {
        this.givingUserMyMedicineNo = givingUserMyMedicineNo;
    }

    public String getGivingUser() {
        return givingUser;
    }

    public void setGivingUser(String givingUser) {
        this.givingUser = givingUser;
    }

    public String getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(String receivingUser) {
        this.receivingUser = receivingUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRegiDate() {
        return regiDate;
    }

    public void setRegiDate(String regiDate) {
        this.regiDate = regiDate;
    }
}
