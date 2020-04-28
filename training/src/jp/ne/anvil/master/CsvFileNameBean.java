package jp.ne.anvil.master;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * csvファイル名Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class CsvFileNameBean {
    private String fileName;

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String masterName) {
        //日付を取得
        Date d = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmsssss");
        String date = dt.format(d);
        // 出力ファイル名を設定
        this.fileName = masterName + "_" + date + ".csv";
    }


}
