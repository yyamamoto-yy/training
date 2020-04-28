package jp.ne.anvil.menu;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.ne.anvil.enums.BeanName;
import jp.ne.anvil.login.LoginBean;

/**
 * メニュー画面のコントローラ
 * @author k.nakamura
 * @version 1.0
 */
@WebServlet("/menu")
public class MenuCtl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
    * コンストラクタ
    */
    public MenuCtl() {
        super();
    }

    /**
     * doGet
     * @param request リクエスト
     * @param response レスポンス
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // セッションタイムアウト確認
        LoginBean loginBean = (LoginBean) session.getAttribute(BeanName.LOGIN_USER.getName());
        if (loginBean == null) {
            response.sendRedirect("/WEB-INF/views/timeout.html");
            return;
        }

        //String action = request.getParameter("action");
        String forward = null;

        // 初期表示
        forward = "/WEB-INF/views/menu.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
        dispatcher.forward(request, response);
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
