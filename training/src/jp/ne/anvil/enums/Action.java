package jp.ne.anvil.enums;

/**
 * アクションのEnumクラス
 * @author k.nakamura
 * @version 1.0
 */
public enum Action {
    /**
     * ログイン
     */
    LOGIN("login"),
    /**
     * 部屋検索
     */
    ROOM_SEARCH("room_search"),
    /**
     * マスタ選択
     */
    MASTER_SELECT("select"),
    /**
     * ユーザ検索
     */
    USER_SEARCH("user_search"),
    /**
     * ユーザ追加
     */
    USER_ADD("user_add"),
    /**
     * ユーザ削除
     */
    USER_DELETE("user_delete"),
    /**
     * ユーザパス変更
     */
    USER_UPDATE("user_update"),
    /**
     * ユーザダウンロード
     */
    USER_DOWNLOAD("user_download"),
    /**
     * 社員寮検索
     */
    DORMITORY_SEARCH("dormitory_search"),
    /**
     * 社員寮追加
     */
    DORMITORY_ADD("dormitory_add"),
    /**
     * 社員寮削除
     */
    DORMITORY_DELETE("dormitory_delete"),
    /**
     * 社員寮一括削除
     */
    DORMITORY_SELECT_DELETE("dormitory_select_delete"),
    /**
     * 社員寮変更
     */
    DORMITORY_UPDATE("dormitory_update"),
    /**
     * 社員寮ダウンロード
     */
    DORMITORY_DOWNLOAD("dormitory_download"),
    /**
     * 部屋追加フォームへ
     */
    ROOM_ADD("room_add"),
    /**
     * 部屋追加
     */
    ROOM_ADDITION("room_addition"),
    /**
     * 部屋削除
     */
    ROOM_DELETE("room_delete"),
    /**
     * 部屋一括削除
     */
    ROOM_SELECT_DELETE("room_select_delete"),
    /**
     * 部屋情報変更
     */
    ROOM_UPDATE("room_update"),
    /**
     * 部屋ダウンロード
     */
    ROOM_DOWNLOAD("room_download"),
    ;

    /**
     * キー
     */
    private String action;

    /**
     * コンストラクタ
     *
     * @param pKey キー
     */
    private Action(String pAction) {
        action = pAction;
    }

    /**
     * コード取得処理
     *
     * @return コード
     */
    public String getAction() {
        return action;
    }

}
