package jp.ne.anvil.master.user;

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
 * ログインユーザマスタ登録画面のコントローラ
 * @author y.yamamoto
 * @version 1.0
 */
@WebServlet("/MasterUser")
public class UserCtl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(UserCtl.class);
    /**
    * コンストラクタ
    */
    public UserCtl() {
        super();
    }

    /**
     * doGet
     * @param request リクエスト
     * @param response レスポンス
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        String msg = "";

        if(Action.USER_SEARCH.getAction().equals(action)) {
            LOGGER.info(Message.I0011.getMessage(loginBean.getId()));
            // 検索処理
            try {
                UserSearchBean searchBean = new UserSearchBean();

                searchBean.setUserId(request.getParameter("uId"));
                searchBean.setCreateDtFrom(request.getParameter("cDtFrom"));
                searchBean.setCreateDtTo(request.getParameter("cDtTo"));

                //UserModelクラスをnewして使えるようにする
                UserModel userModel = new UserModel();

                //UserModelクラスのgetUserメソッドを呼び出す
                List<UserBean> listBean = userModel.getUser(searchBean);

                //追加項目初期化
                UserAddBean addBean = new UserAddBean();

                request.setAttribute("msg", "検索結果");
                request.setAttribute(BeanName.USER_LIST.getName(), listBean);
                request.setAttribute(BeanName.USER_SEARCH.getName(), searchBean);
                request.setAttribute(BeanName.USER_ADD.getName(), addBean);

                forward = "/WEB-INF/views/master/user.jsp";

            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
        // 設定された遷移先へ移動
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if(Action.USER_ADD.getAction().equals(action)) {

                // 追加処理
                try {

                    UserAddBean addBean = new UserAddBean();
                    String aId =request.getParameter("aId");
                    String aPass =request.getParameter("aPass");

                    addBean.setAddId(aId);
                    addBean.setAddPass(aPass);
                    //idとpassが入力されているか確認
                    if (!StringUtils.isEmpty(aId) && !StringUtils.isEmpty(aPass)) {

                        //UserAddModelクラスをnewして使えるようにする
                        UserAddModel userAddModel = new UserAddModel();

                        //UserAddModelクラスのaddUserメソッドを呼び出す
                        addBean = userAddModel.addUser(addBean);

                        //表示初期化
                        UserSearchBean searchBean = new UserSearchBean();

                        addBean.setAddId("");
                        addBean.setAddPass("");

                        LOGGER.info(Message.I0012.getMessage(loginBean.getId()));

                        request.setAttribute("msg", "ユーザを追加しました。");
                        request.setAttribute(BeanName.USER_SEARCH.getName(), searchBean);
                        request.setAttribute(BeanName.USER_ADD.getName(), addBean);
                        forward = "/WEB-INF/views/master/user.jsp";
                    }else {
                      //表示初期化
                        UserSearchBean searchBean = new UserSearchBean();

                        LOGGER.info(Message.I0013.getMessage(loginBean.getId()));

                        request.setAttribute("msg", "正しく入力されませんでした。");
                        request.setAttribute(BeanName.USER_SEARCH.getName(), searchBean);
                        request.setAttribute(BeanName.USER_ADD.getName(), addBean);
                        forward = "/WEB-INF/views/master/user.jsp";
                    }

                }catch(Exception e){
                    LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                    request.setAttribute("message", e.getMessage());
                    forward = "/WEB-INF/views/error.jsp";
                }
            // 設定された遷移先へ移動
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
                dispatcher.forward(request, response);

        }else if(Action.USER_DELETE.getAction().equals(action)) {

            // 削除処理
            try {

                UserEditBean editBean = new UserEditBean();
                String eId =request.getParameter("eId");

                editBean.setDeleteId(eId);

                //"admin"ではないか確認
                if(!eId.equals("admin")) {
                    //UserDeleteModelクラスをnewして使えるようにする
                    UserDeleteModel userDeleteModel = new UserDeleteModel();

                    //UserDeleteModelクラスのdeleteUserメソッドを呼び出す
                    editBean = userDeleteModel.deleteUser(editBean);

                    //表示初期化
                    UserSearchBean searchBean = new UserSearchBean();
                    UserAddBean addBean = new UserAddBean();

                    LOGGER.info(Message.I0014.getMessage(loginBean.getId()));

                    request.setAttribute("msg", "ユーザ「" + eId + "」を削除しました。");
                    request.setAttribute(BeanName.USER_SEARCH.getName(), searchBean);
                    request.setAttribute(BeanName.USER_ADD.getName(), addBean);
                    forward = "/WEB-INF/views/master/user.jsp";

                }else {
                    //表示初期化
                    UserSearchBean searchBean = new UserSearchBean();
                    UserAddBean addBean = new UserAddBean();

                    request.setAttribute("msg", "ユーザ「admin」は削除できません。");
                    request.setAttribute(BeanName.USER_SEARCH.getName(), searchBean);
                    request.setAttribute(BeanName.USER_ADD.getName(), addBean);
                    forward = "/WEB-INF/views/master/user.jsp";
               }

            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
            // 設定された遷移先へ移動
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if(Action.USER_UPDATE.getAction().equals(action)) {

            // 更新処理
            try {

                UserEditBean editBean = new UserEditBean();
                String eId =request.getParameter("eId");
                String ePass =request.getParameter("ePass");

                editBean.setUpdateId(eId);
                editBean.setUpdatePass(ePass);

                //UserUpdateModelクラスをnewして使えるようにする
                UserUpdateModel userUpdateModel = new UserUpdateModel();

                //UserUpdateModelクラスのupdateUserメソッドを呼び出す
                editBean = userUpdateModel.updateUser(editBean);

                //表示初期化
                UserSearchBean searchBean = new UserSearchBean();
                UserAddBean addBean = new UserAddBean();

                LOGGER.info(Message.I0015.getMessage(loginBean.getId()));

                request.setAttribute("msg", "ユーザ「" + eId + "」のパスワードを変更しました。");
                request.setAttribute(BeanName.USER_SEARCH.getName(), searchBean);
                request.setAttribute(BeanName.USER_ADD.getName(), addBean);
                forward = "/WEB-INF/views/master/user.jsp";


            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
            // 設定された遷移先へ移動
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

            //パスワード変更用の画面へ
        }else if ("user_updateform".equals(action)){
            String eId =request.getParameter("eId");
            request.setAttribute("eId", eId);


            forward = "/WEB-INF/views/master/userUpdate.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if ("user_update_cancel".equals(action)){


         // 初期表示
            UserSearchBean searchBean = new UserSearchBean();
            UserAddBean addBean = new UserAddBean();

            request.setAttribute("msg", "変更をキャンセルしました。");
            request.setAttribute(BeanName.USER_SEARCH.getName(), searchBean);
            request.setAttribute(BeanName.USER_ADD.getName(), addBean);

            forward = "/WEB-INF/views/master/user.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);

        }else if(Action.USER_DOWNLOAD.getAction().equals(action)) {

                // ダウンロード処理
            try {
                UserSearchBean searchBean = new UserSearchBean();

                searchBean.setUserId(request.getParameter("uId"));
                searchBean.setCreateDtFrom(request.getParameter("cDtFrom"));
                searchBean.setCreateDtTo(request.getParameter("cDtTo"));

                //UserModelクラスをnewして使えるようにする
                UserModel userModel = new UserModel();

                //UserModelクラスのgetUserメソッドを呼び出す
                List<UserBean> listBean = userModel.getUser(searchBean);

                if(listBean.size() != 0 && listBean != null) {

                    //ファイル名の取得
                    CsvFileNameBean csvName = new CsvFileNameBean();
                    csvName.setFileName("m_login_user");
                    String fileName = csvName.getFileName();

                    //文字コードと出力するCSVファイル名を設定
                    response.setContentType("text/csv;charset=Shift-JIS");
                    response.setHeader("Content-disposition","attachment; filename="+fileName);

                    //ファイル書き込み
                    UserDLModel userDLModel =  new UserDLModel();
                    userDLModel.dlUser(listBean,fileName, response);

                    msg="検索結果をファイル出力しました。";

                    LOGGER.info(Message.I0016.getMessage(loginBean.getId()));

                }else {
                    msg="出力するデータがありません。";


                //追加項目初期化
                UserAddBean addBean = new UserAddBean();

                request.setAttribute("msg", msg);
                request.setAttribute(BeanName.USER_LIST.getName(), listBean);
                request.setAttribute(BeanName.USER_SEARCH.getName(), searchBean);
                request.setAttribute(BeanName.USER_ADD.getName(), addBean);

                forward = "/WEB-INF/views/master/user.jsp";
                // 設定された遷移先へ移動
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
                dispatcher.forward(request, response);
                }
            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
                // 設定された遷移先へ移動
                RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
                dispatcher.forward(request, response);
            }

        }else if ("user_add_ajax".equals(action)){




               forward = "/WEB-INF/views/master/userAddAjax.html";
               RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
               dispatcher.forward(request, response);

        }else{
            // 初期表示
            UserSearchBean searchBean = new UserSearchBean();
            UserAddBean addBean = new UserAddBean();

            request.setAttribute("msg", "　");
            request.setAttribute(BeanName.USER_SEARCH.getName(), searchBean);
            request.setAttribute(BeanName.USER_ADD.getName(), addBean);

            forward = "/WEB-INF/views/master/user.jsp";
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