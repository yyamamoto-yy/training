package jp.ne.anvil.master.user;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * ユーザーダウンロードのモデル
 * @author y.yamamoto
 * @version 1.0
 */
public class UserDLModel {


    /**
    * コンストラクタ
    */
    public UserDLModel() {
        super();
    }

    /**
     * ユーザー一覧をダウンロード
     * @param pListBean 検索条件
     * @throws Exception
     */
    public void dlUser(List<UserBean> pListBean , String pFileName ,HttpServletResponse response) throws Exception {

        try {
            if(pListBean.size() != 0 && pListBean != null) {

                PrintWriter p = new PrintWriter(
                        new OutputStreamWriter(response.getOutputStream(),
                                "Shift_JIS"));

                // ヘッダーを指定する
                p.write("\"ユーザID\"");
                p.write(",");
                p.write("\"更新日時\"");
                p.write("\r\n");

                // 内容をセットする
                for(int i = 0; i < pListBean.size(); i++){
                    p.write('"' + pListBean.get(i).getUser_id() + '"');
                    p.write(",");
                    p.write('"' + pListBean.get(i).getCreate_dt() + '"');
                    p.write("\r\n");    // 改行
                }

                // ファイルに書き出し閉じる
                p.close();

            }
        } catch (IOException ex) {

            ex.printStackTrace();
        }

    }
}
