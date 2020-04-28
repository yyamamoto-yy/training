package jp.ne.anvil.master.user;

/**
 * ユーザー一覧Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class UserBean {
    private String user_id;//ユーザー名

    private String create_dt;

    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getCreate_dt() {
        return create_dt;
    }
    public void setCreate_dt(String create_dt) {
        this.create_dt = create_dt;
    }

}
