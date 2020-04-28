package jp.ne.anvil.master.dormitory;

/**
 * 社員寮検索条件Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class DormitorySearchBean {
    private String dormitoryId = "";

    private String dormitoryName = "";

    private String createDtFrom = "";

    private String createDtTo = "";

    public String getDormitoryId() {
        return dormitoryId;
    }
    public void setDormitoryId(String dormitoryId) {
        this.dormitoryId = dormitoryId;
    }

    public String getDormitoryName() {
        return dormitoryName;
    }
    public void setDormitoryName(String dormitoryName) {
        this.dormitoryName = dormitoryName;
    }

    public String getCreateDtFrom() {
        return createDtFrom;
    }
    public void setCreateDtFrom(String createDtFrom) {
        this.createDtFrom = createDtFrom;
    }

    public String getCreateDtTo() {
        return createDtTo;
    }
    public void setCreateDtTo(String createDtTo) {
        this.createDtTo = createDtTo;
    }
}
