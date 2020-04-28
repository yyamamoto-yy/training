package jp.ne.anvil.master.user;

/**
 * ユーザー検索条件Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class UserSearchBean {
    private String userId = "";

    private String createDtFrom = "";

    private String createDtTo = "";

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
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
