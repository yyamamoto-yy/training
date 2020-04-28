package jp.ne.anvil.api;

/**
 * 社員寮一覧Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class DormitoryInfoBean {
    private int result;
    private int count;
    private String error;

    public int getResult() {
        return result;
    }
    public void setResult(int result) {
        this.result = result;
    }

    public int getcount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

}
