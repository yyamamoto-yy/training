package jp.ne.anvil.master.user;

/**
 * ユーザ編集Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class UserEditBean {
    private String deleteId;

    private String updateId;

    private String updatePass;


    public String getDeleteId() {
        return deleteId;
    }
    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }

    public String getUpdateId() {
        return updateId;
    }
    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdatePass() {
        return updatePass;
    }
    public void setUpdatePass(String updatePass) {
        this.updatePass = updatePass;
    }





}
