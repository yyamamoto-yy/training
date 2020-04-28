package jp.ne.anvil.enums;

/**
 * BeanNameのEnumクラス
 * @author k.nakamura
 * @version 1.0
 */
public enum BeanName {
    /**
     * ログインユーザ
     */
    LOGIN_USER("login_bean"),
    /**
     * 部屋一覧
     */
    ROOM_LIST("room_list"),
    /**
     * 部屋一覧検索条件
     */
    ROOM_SEARCH("room_search_bean"),
    /**
     * ユーザ一覧
     */
    USER_LIST("user_list"),
    /**
     * ユーザ一覧検索条件
     */
    USER_SEARCH("user_search_bean"),
    /**
     * ユーザ追加
     */
    USER_ADD("user_add_bean"),
    /**
     * ユーザ削除
     */
    USER_DELETE("user_delete_bean"),
    /**
     * ユーザパス変更
     */
    USER_UPDATE("user_update_bean"),
    /**
     * ユーザダウンロード
     */
    USER_DL("user_dl_bean"),
    /**
     * 社員寮一覧
     */
    DORMITORY_LIST("dormitory_list"),
    /**
     * 社員寮一覧
     */
    DORMITORY_SEARCH("dormitory_search_bean"),
    /**
     * 社員寮追加
     */
    DORMITORY_ADD("dormitory_add_bean"),
    /**
     * 社員寮削除
     */
    DORMITORY_DELETE("dormitory_delete_bean"),
    /**
    * 社員寮変更
    **/
    DORMITORY_UPDATE("dormitory_update_bean"),
    /**
     * 部屋追加用社員寮一覧取得
     **/
    DORM_LIST("dorm_list"),
     /**
      * 部屋変更
      */
    ROOM_ADD("room_add_bean"),
     /**
      * 部屋変更
      */
    ROOM_UPDATE("room_update_bean")

    ;

    /**
     * 名前
     */
    private String name;

    /**
     * コンストラクタ
     *
     * @param pName 名前
     */
    private BeanName(String pName) {
        name = pName;
    }

    /**
     * コード取得処理
     *
     * @return 名前
     */
    public String getName() {
        return name;
    }

}
