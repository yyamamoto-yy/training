package jp.ne.anvil.master.dormitory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * 社員寮ダウンロードのモデル
 * @author y.yamamoto
 * @version 1.0
 */
public class DormitoryDLModel {


    /**
    * コンストラクタ
    */
    public DormitoryDLModel() {
        super();
    }

    /**
     * 社員寮ー一覧をダウンロード
     * @param pListBean 検索条件
     * @throws Exception
     */
    public void dlDormitory(List<DormitoryBean> pListBean, String pFileName ,HttpServletResponse response) throws Exception {

        try {

            if(pListBean.size() != 0 && pListBean != null) {
                PrintWriter p = new PrintWriter(
                        new OutputStreamWriter(response.getOutputStream(),
                                "Shift_JIS"));

                // ヘッダーを指定する
                p.write("\"物件ID\"");
                p.write(",");
                p.write("\"物件名\"");
                p.write(",");
                p.write("\"更新日時\"");
                p.write("\r\n");

                // 内容をセットする
                for(int i = 0; i < pListBean.size(); i++){
                    p.write('"' + pListBean.get(i).getDormitory_id() + '"');
                    p.write(",");
                    p.write('"' + pListBean.get(i).getDormitory_name() + '"');
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
