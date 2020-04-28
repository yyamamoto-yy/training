package jp.ne.anvil.enums;

/**
 * メッセージのEnumクラス
 * @author k.nakamura
 * @version 1.0
 */
public enum Message {
    I0000("{1}がログイン成功"),
    I0001("{1}がログイン失敗"),
    I0002("{1}が部屋を検索"),
    I0011("{1}がユーザを検索"),
    I0012("{1}がユーザを登録"),
    I0013("{1}がユーザ登録に失敗"),
    I0014("{1}がユーザを削除"),
    I0015("{1}がパスワードを変更"),
    I0016("{1}がユーザ検索結果をダウンロード"),
    I0021("{1}が社員寮を検索"),
    I0022("{1}が社員寮を登録"),
    I0023("{1}が社員寮を削除"),
    I0024("{1}が社員寮を変更"),
    I0025("{1}が社員寮検索結果をダウンロード"),
    I0031("{1}が部屋を検索"),
    I0032("{1}が部屋を登録"),
    I0033("{1}が部屋を削除"),
    I0034("{1}が部屋を変更"),
    I0035("{1}が部屋検索結果をダウンロード"),
    I0036("{1}が重複する部屋を登録しようとしました。"),
    E0000("ログイン処理で例外発生{1}"),
    ;

    /**
     * メッセージ
     */
    private String message;

    /**
     * コンストラクタ
     *
     * @param pMessage メッセージ
     */
    private Message(String pMessage) {
        message = pMessage;
    }

    /**
     * メッセージ取得処理
     *
     * @return メッセージ
     */
    public String getMessage() {
        return message;
    }

    /**
     * メッセージ取得処理
     *
     * @param args メッセージパラメータ
     * @return メッセージ
     */
    public String getMessage(String... args) {
        return createMessage(message, args);
    }

    /**
     * メッセージ作成処理
     *
     * @param args メッセージパラメータ(可変長引数)
     * @return メッセージ
     */
    private static String createMessage(String pMessage, String... args) {
        int loopCnt = args.length;
        for (int i = 0; i < loopCnt; i++) {
            String pattern = "{" + (i + 1) +"}";
            pMessage = pMessage.replace(pattern, args[i]);
        }
        return pMessage;
    }
}
