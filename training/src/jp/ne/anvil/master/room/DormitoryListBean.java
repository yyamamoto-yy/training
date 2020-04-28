package jp.ne.anvil.master.room;

/**
 * 社員寮一覧取得Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class DormitoryListBean {
    private String dormitory_id;
    private String dormitory_name;


    public String getDormitory_id() {
        return dormitory_id;
    }
    public void setDormitory_id(String dormitory_id) {
        this.dormitory_id = dormitory_id;
    }

    public String getDormitory_name() {
        return dormitory_name;
    }
    public void setDormitory_name(String dormitory_name) {
        this.dormitory_name = dormitory_name;
    }

}
