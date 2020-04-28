package jp.ne.anvil.master.dormitory;

/**
 * 社員寮追加Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class DormitoryAddBean {

    private String addId = "";

    private String addName = "";

    private String msg;

    public String getAddId() {
        return addId;
    }
    public void setAddId(String addId) {
        this.addId = addId;
    }

    public String getAddName() {
        return addName;
    }
    public void setAddName(String addName) {
        this.addName = addName;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }


}
