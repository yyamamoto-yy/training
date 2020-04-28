package jp.ne.anvil.login;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import jp.ne.anvil.enums.Action;
import jp.ne.anvil.enums.Message;

/**
 * ログイン画面のコントローラ
 * @author k.nakamura
 * @version 1.0
 */
@WebServlet("/todo")
public class LoginCtl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(LoginCtl.class);

    /**
    * コンストラクタ
    */
    public LoginCtl() {
        super();
    }

    /**
     * doGet
     * @param request リクエスト
     * @param response レスポンス
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        request.setCharacterEncoding("Shift_JIS");

        String action = request.getParameter("action");
        String forward = null;

        if(Action.LOGIN.getAction().equals(action)) {
            // ログイン処理
            try {
                String id = request.getParameter("id");
                String pass = request.getParameter("pass");

                LoginModel loginModel = new LoginModel();
                LoginBean loginBean = loginModel.getUserInfo(id, pass);

                if (loginBean.isResult()) {
                    LOGGER.info(Message.I0000.getMessage(id));
                    // ログイン成功
                    session.setAttribute("login_bean", loginBean);
                    forward = "./menu";
                }else {
                    LOGGER.info(Message.I0001.getMessage(id));
                    // ログイン失敗
                    request.setAttribute("id", id);
                    request.setAttribute("message", loginBean.getErrorMessage());
                    forward = "/WEB-INF/views/login.jsp";
                }
            }catch(Exception e){
                LOGGER.error(Message.E0000.getMessage(e.getMessage()));
                request.setAttribute("message", e.getMessage());
                forward = "/WEB-INF/views/error.jsp";
            }
            // 設定された遷移先へ移動
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request, response);
        }else{
            // 初期表示
            forward = "/WEB-INF/views/login.jsp";
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
