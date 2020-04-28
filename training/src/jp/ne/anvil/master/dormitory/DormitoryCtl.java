package jp.ne.anvil.master.dormitory;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import jp.ne.anvil.enums.Action;
import jp.ne.anvil.enums.BeanName;
import jp.ne.anvil.enums.Message;
import jp.ne.anvil.login.LoginBean;
import jp.ne.anvil.master.CsvFileNameBean;

/**
 * 社員寮マスタ登録画面のコントローラ
 * @author y.yamamoto
 * @version 1.0
 */
@WebServlet("/MasterDormitory")
public class DormitoryCtl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(DormitoryCtl.class);
    /**
    * コンストラクタ
    */
    public DormitoryCtl() {
        super();
    }

    /**
     * doGet
     * @param request リクエスト
     * @param response レスポンス
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

     // セッションタイムアウト確認
      LoginBean loginBean = (LoginBean)session.getAttribute(BeanName.LOGIN_USER.getName());
      if (loginBean == null) {
          response.sendRedirect("/WEB-INF/views/timeout.html");
          return;
      }

      String action = request.getParameter("action");
        String forward = null;
        String msg = "　";

        if(Action.DORMITORY_SEARCH.getAction().equals(action)) {
         // 検索処理
            try {
                LOGGER.info(Message.I0021.getMessage(loginBean.getId()));
                DormitorySearchBean searchBean = new DormitorySearchBean();

                searchBean.setDormitoryId(request.getParameter("dId"));
                searchBean.setDormitoryName(request.getParameter("dName"));
                searchBean.setCreateDtFrom(request.getParameter("cDtFrom"));
                searchBean.setCreateDtTo(request.getParameter("cDtTo"));

                //UserModelクラスをnewして使えるようにする
                DormitoryModel dormitoryModel = new DormitoryModel();

                //UserModelクラスのgetUserメソッドを呼び出す
                List<DormitoryBean> listBean = dormitoryModel.getDormitory(searchBean);

                DormitoryAddBean addBean = new DormitoryAddBean();

                msg = "検索結果";
                request.setAttribute("msg", msg);
                request.setAttribute(BeanName.DORMITORY_LIST.getName(), listBean);
                request.setAttribute(BeanName.DORMITORY_SEARCH.getName(), searchBean);
                request.setAttribute(BeanName.DORMITORY_ADD.getName(), addBean);

                forward = "/WEB-INF/views/master/dormitory.jsp";

            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
        // 設定された遷移先へ移動

            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if(Action.DORMITORY_ADD.getAction().equals(action)) {

            // 追加処理
            try {

                DormitorySearchBean searchBean = new DormitorySearchBean();
                DormitoryAddBean addBean = new DormitoryAddBean();
                String aId =request.getParameter("aId");
                String aName =request.getParameter("aName");

                addBean.setAddId(aId);
                addBean.setAddName(aName);
                //idとnameが入力されているか、idが半角数字４文字か確認
                if (!StringUtils.isEmpty(aId) && !StringUtils.isEmpty(aName) && aId.matches("[0-9]{4}")) {

                    //UserAddModelクラスをnewして使えるようにする
                    DormitoryAddModel dormitoryAddModel = new DormitoryAddModel();

                    //UserAddModelクラスのaddUserメソッドを呼び出す
                    addBean = dormitoryAddModel.addDormitory(addBean);

                    //表示初期化
                    addBean.setAddId("");
                    addBean.setAddName("");

                    msg = addBean.getMsg();
                    LOGGER.info(Message.I0022.getMessage(loginBean.getId()));
                }else {

                    msg = "正しく入力されませんでした。";
                }

                request.setAttribute("msg", msg);
                request.setAttribute(BeanName.DORMITORY_SEARCH.getName(), searchBean);
                request.setAttribute(BeanName.DORMITORY_ADD.getName(), addBean);
                forward = "/WEB-INF/views/master/dormitory.jsp";

            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
        // 設定された遷移先へ移動
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if(Action.DORMITORY_DELETE.getAction().equals(action)) {

            // 削除処理
            try {

                DormitoryEditBean deleteBean = new DormitoryEditBean();
                String eId =request.getParameter("eId");
                String eName =request.getParameter("eName");

                deleteBean.setDeleteId(eId);

                //DormitoryDeleteModelクラスをnewして使えるようにする
                DormitoryDeleteModel dormitoryDeleteModel = new DormitoryDeleteModel();

                //DormitoryDeleteModelクラスのdeleteDormitoryメソッドを呼び出す
                deleteBean = dormitoryDeleteModel.deleteDormitory(deleteBean);

                //表示初期化
                DormitorySearchBean searchBean = new DormitorySearchBean();
                DormitoryAddBean addBean = new DormitoryAddBean();

                request.setAttribute("msg", "社員寮「" + eName + "」を削除しました。");
                request.setAttribute(BeanName.DORMITORY_SEARCH.getName(), searchBean);
                request.setAttribute(BeanName.DORMITORY_ADD.getName(), addBean);
                forward = "/WEB-INF/views/master/dormitory.jsp";
                LOGGER.info(Message.I0023.getMessage(loginBean.getId()));

            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
            // 設定された遷移先へ移動
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if(Action.DORMITORY_SELECT_DELETE.getAction().equals(action)) {

            // 削除処理
            try {

                DormitorySelectDeleteBean deleteBean = new DormitorySelectDeleteBean();

                if (request.getParameterValues("delId") != null) {

                    deleteBean.setDeleteId(request.getParameterValues("delId"));
                    //DormitoryDeleteModelクラスをnewして使えるようにする
                    DormitorySelectDeleteModel dormitoryDeleteModel = new DormitorySelectDeleteModel();

                    //DormitoryDeleteModelクラスのdeleteDormitoryメソッドを呼び出す
                    deleteBean = dormitoryDeleteModel.deleteDormitory(deleteBean);
                }else {
                    deleteBean.setMsg("削除する物件が選択されませんでした。");
                }

                msg=deleteBean.getMsg();

                //表示初期化
                DormitorySearchBean searchBean = new DormitorySearchBean();
                DormitoryAddBean addBean = new DormitoryAddBean();

                request.setAttribute("msg", msg);
                request.setAttribute(BeanName.DORMITORY_SEARCH.getName(), searchBean);
                request.setAttribute(BeanName.DORMITORY_ADD.getName(), addBean);
                forward = "/WEB-INF/views/master/dormitory.jsp";
                LOGGER.info(Message.I0023.getMessage(loginBean.getId()));

            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
            // 設定された遷移先へ移動
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if(Action.DORMITORY_UPDATE.getAction().equals(action)) {

            // 更新処理
            try {

                DormitoryEditBean updateBean = new DormitoryEditBean();
                String eId =request.getParameter("eId");
                String eName =request.getParameter("eName");
                String beforeName =request.getParameter("beforeName");

                updateBean.setUpdateId(eId);
                updateBean.setUpdateName(eName);
                updateBean.setBeforeName(beforeName);

                //DormitoryUpdateModelクラスをnewして使えるようにする
                DormitoryUpdateModel dormitoryUpdateModel = new DormitoryUpdateModel();

                //UserUpdateModelクラスのupdateUserメソッドを呼び出す
                updateBean = dormitoryUpdateModel.updateDormitory(updateBean);

                //表示初期化
                DormitorySearchBean searchBean = new DormitorySearchBean();
                DormitoryAddBean addBean = new DormitoryAddBean();

                msg = updateBean.getMsg();

                LOGGER.info(Message.I0024.getMessage(loginBean.getId()));

                request.setAttribute("msg", msg);
                request.setAttribute(BeanName.DORMITORY_SEARCH.getName(), searchBean);
                request.setAttribute(BeanName.DORMITORY_ADD.getName(), addBean);
                forward = "/WEB-INF/views/master/dormitory.jsp";

            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
            // 設定された遷移先へ移動
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

            //パスワード変更用の画面へ
        }else if ("dormitory_updateform".equals(action)){
            String eId =request.getParameter("eId");
            String eName =request.getParameter("eName");
            request.setAttribute("eId", eId);
            request.setAttribute("eName", eName);

            forward = "/WEB-INF/views/master/dormitoryUpdate.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if ("dormitory_update_cancel".equals(action)){

         // 初期表示
            DormitorySearchBean searchBean = new DormitorySearchBean();
            DormitoryAddBean addBean = new DormitoryAddBean();

            request.setAttribute("msg", "変更をキャンセルしました。");
            request.setAttribute(BeanName.DORMITORY_SEARCH.getName(), searchBean);
            request.setAttribute(BeanName.DORMITORY_ADD.getName(), addBean);

            forward = "/WEB-INF/views/master/dormitory.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if(Action.DORMITORY_DOWNLOAD.getAction().equals(action)) {
                // ダウンロード処理
               try {
                   DormitorySearchBean searchBean = new DormitorySearchBean();

                   searchBean.setDormitoryId(request.getParameter("dId"));
                   searchBean.setDormitoryName(request.getParameter("dName"));
                   searchBean.setCreateDtFrom(request.getParameter("cDtFrom"));
                   searchBean.setCreateDtTo(request.getParameter("cDtTo"));

                   //UserModelクラスをnewして使えるようにする
                   DormitoryModel dormitoryModel = new DormitoryModel();

                   //UserModelクラスのgetUserメソッドを呼び出す
                   List<DormitoryBean> listBean = dormitoryModel.getDormitory(searchBean);

                   if(listBean.size() != 0 && listBean != null) {
                       //ファイル名の取得
                       CsvFileNameBean csvName = new CsvFileNameBean();
                       csvName.setFileName("m_dormitory");
                       String fileName = csvName.getFileName();

                       //文字コードと出力するCSVファイル名を設定
                       response.setContentType("text/csv;charset=Shift-JIS");
                       response.setHeader("Content-disposition","attachment; filename="+fileName);

                       //ファイル書き込み
                       DormitoryDLModel dormitoryDLModel =  new DormitoryDLModel();
                       dormitoryDLModel.dlDormitory(listBean,fileName, response);

                       LOGGER.info(Message.I0025.getMessage(loginBean.getId()));

                   }else {
                       msg="出力するデータがありません。";
                       DormitoryAddBean addBean = new DormitoryAddBean();

                       request.setAttribute("msg", msg);
                       request.setAttribute(BeanName.DORMITORY_LIST.getName(), listBean);
                       request.setAttribute(BeanName.DORMITORY_SEARCH.getName(), searchBean);
                       request.setAttribute(BeanName.DORMITORY_ADD.getName(), addBean);

                       forward = "/WEB-INF/views/master/dormitory.jsp";
                       RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
                       dispatcher.forward(request, response);
                   }


               }catch(Exception e){
                   LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                   request.setAttribute("message", e.getMessage());
                   forward = "/WEB-INF/views/error.jsp";
                   RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
                   dispatcher.forward(request, response);
               }
           // 設定された遷移先へ移動



        }else{
                // 初期表示
                DormitorySearchBean searchBean = new DormitorySearchBean();
                DormitoryAddBean addBean = new DormitoryAddBean();

                request.setAttribute("msg",msg);
                request.setAttribute(BeanName.DORMITORY_SEARCH.getName(), searchBean);
                request.setAttribute(BeanName.DORMITORY_ADD.getName(), addBean);

                forward = "/WEB-INF/views/master/dormitory.jsp";
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
                dispatcher.forward(request, response);
        }
    }

    /**
     * doPost
     * @param request リクエスト
     * @param response レスポンス
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}