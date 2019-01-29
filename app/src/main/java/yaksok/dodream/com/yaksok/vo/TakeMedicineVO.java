package yaksok.dodream.com.yaksok.vo;

public class TakeMedicineVO {
  /*  BODY{“givingUser”:string,   //누가?
          “medicineNo”:string*/
    public static String givingUser;
    public static String medicineNO;

    public static String getGivingUser() {
        return givingUser;
    }

    public static void setGivingUser(String givingUser) {
        TakeMedicineVO.givingUser = givingUser;
    }

    public static String getMedicineNO() {
        return medicineNO;
    }

    public static void setMedicineNO(String medicineNO) {
        TakeMedicineVO.medicineNO = medicineNO;
    }
}
