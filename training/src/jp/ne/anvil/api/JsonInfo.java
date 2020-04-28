package jp.ne.anvil.api;

/**
 * 社員寮一覧Bean
 * @author y.yamamoto
 * @version 1.0
 */
public class JsonInfo {

        private String[] names;
        private int count;

        public JsonInfo(String[] names){
            this.names = names;
            this.count = names.length;
        }

}
