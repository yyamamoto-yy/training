package jp.ne.anvil.master.dormitory;

/**
 * 社員寮一覧Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class DormitoryBean {
    private String dormitory_id = "";

    private String dormitory_name = "";

    private String create_dt = "";

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


    public String getCreate_dt() {
        return create_dt;
    }
    public void setCreate_dt(String create_dt) {
        this.create_dt = create_dt;
    }

}
