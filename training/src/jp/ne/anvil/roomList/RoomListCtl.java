package jp.ne.anvil.roomList;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import jp.ne.anvil.enums.Action;
import jp.ne.anvil.enums.BeanName;
import jp.ne.anvil.enums.Message;
import jp.ne.anvil.login.LoginBean;
import jp.ne.anvil.login.LoginCtl;

/**
 * 部屋一覧画面のコントローラ
 * @author k.nakamura
 * @version 1.0
 */
@WebServlet("/room")
public class RoomListCtl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(LoginCtl.class);

    /**
    * コンストラクタ
    */
    public RoomListCtl() {
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
        LoginBean loginBean = (LoginBean) session.getAttribute(BeanName.LOGIN_USER.getName());
        if (loginBean == null) {
            response.sendRedirect("/WEB-INF/views/timeout.html");
            return;
        }

        String action = request.getParameter("action");
        String forward = null;

        if(Action.ROOM_SEARCH.getAction().equals(action)) {
            LOGGER.info(Message.I0002.getMessage(loginBean.getId()));
            // 検索処理
            try {
                RoomListSearthBean searchBean = new RoomListSearthBean();

                searchBean.setDormitoryName(request.getParameter("dormitory"));
                searchBean.setFloorPlan(new String[0]);
                if (request.getParameterValues("plan") != null) {
                    searchBean.setFloorPlan(request.getParameterValues("plan"));
                }
                searchBean.setSquareMeter(request.getParameter("meter"));
                searchBean.setOut(request.getParameter("out"));

                //RoomListModelクラスをnewして使えるようにする
                RoomListModel roomListModel = new RoomListModel();

                //RoomListModelクラスのgetRoomListメソッドを呼び出す
                List<RoomBean> listBean = roomListModel.getRoomList(searchBean);


                request.setAttribute(BeanName.ROOM_LIST.getName(), listBean);
                request.setAttribute(BeanName.ROOM_SEARCH.getName(), searchBean);
                forward = "/WEB-INF/views/roomList.jsp";
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
            RoomListSearthBean searchBean = new RoomListSearthBean();
            searchBean.setDormitoryName("");
            searchBean.setFloorPlan(new String[0]);
            searchBean.setSquareMeter("");

            request.setAttribute(BeanName.ROOM_SEARCH.getName(), searchBean);
            forward = "/WEB-INF/views/roomList.jsp";
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
