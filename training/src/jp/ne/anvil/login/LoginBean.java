package jp.ne.anvil.login;

/**
 * ログイン情報Bean
 * @author k.nakamura
 * @version 1.0
 */
public class LoginBean {

    private String id;
    private String errorMessage;
    private boolean result;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public boolean isResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
}
