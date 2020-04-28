package jp.ne.anvil.master.dormitory;

/**
 * 社員寮編集Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class DormitoryEditBean {
    private String deleteId = "";

    private String deleteName = "";

    private String updateId = "";

    private String updateName = "";

    private String beforeName = "";

    private String msg = "";


    public String getDeleteId() {
        return deleteId;
    }
    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }

    public String getDeleteName() {
        return deleteName;
    }
    public void setDeleteName(String deleteName) {
        this.deleteName = deleteName;
    }

    public String getUpdateId() {
        return updateId;
    }
    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getBeforeName() {
        return beforeName;
    }
    public void setBeforeName(String beforeName) {
        this.beforeName = beforeName;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }



}
