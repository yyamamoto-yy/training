package jp.ne.anvil.master.dormitory;

/**
 * 社員寮編集Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class DormitorySelectDeleteBean {

    private String[] deleteId = new String[0];

    private String msg = "";

    public String[] getDeleteId() {
        return deleteId;
    }
    public void setDeleteId(String[] strings) {
        this.deleteId = strings;
    }


    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }



}
