package jp.ne.anvil.api;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import jp.ne.anvil.enums.BeanName;
import jp.ne.anvil.enums.Message;
import jp.ne.anvil.login.LoginBean;
/**
 * Servlet implementation class getInfomation
 */
@WebServlet("/userAddAjax")
public class UserAddAjax extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * ロガー
     */
    private static final Logger LOGGER = Logger.getLogger(UserAddAjax.class);


    public UserAddAjax() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 処理開始ログ
        LOGGER.info("通信開始");

        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        LoginBean loginBean = (LoginBean)session.getAttribute(BeanName.LOGIN_USER.getName());

        String id = request.getParameter("id");
        String pass = request.getParameter("pass");
        String msg ="";
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs= null;
        Connection conn = null;
        JSONArray jsonarray = new JSONArray();
        JSONObject jsonobject = new JSONObject();
        int i = 0;
        try{
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");

            conn = ds.getConnection();
            conn.setAutoCommit(false);

            StringBuilder sb = new StringBuilder()

                    .append("SELECT " )
                    .append("   COUNT(*)  cnt")
                    .append(" FROM " )
                    .append("   m_login_user " )
                    .append(" WHERE " )
                    .append("   id = ? ")
                    ;

            ps = conn.prepareStatement(sb.toString());

            ps.setString(1, id );

            rs = ps.executeQuery();

            int a = 0;
            while(rs.next()) {
                a = rs.getInt("cnt");
            }

            if(a == 0) {

                StringBuilder sb2 = new StringBuilder()
                        .append("INSERT INTO ")
                        .append("m_login_user")
                        .append(" (id,password,create_dt) ")
                        .append(" VALUES ")
                        .append("( ? ")
                        .append(", ? ")
                        .append(",sysdate)")
                        ;

                ps2 = conn.prepareStatement(sb2.toString());

                ps2.setString(1, id );
                ps2.setString(2, pass );

                i = ps2.executeUpdate();

                //コミット
                conn.commit();

                msg = "ユーザー「"+ id + "」を登録しました。";
                jsonobject.put("result",0);
                jsonobject.put("msg",msg);
                jsonarray.put(jsonobject);

                LOGGER.info(Message.I0012.getMessage(loginBean.getId()));
            }else {
                conn.rollback();
                msg = "すでに使用されています。";
                jsonobject.put("result",1);
                jsonobject.put("msg",msg);
                jsonarray.put(jsonobject);
            }
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(jsonarray);


        }catch(Exception e){

            msg = "登録に失敗しました。";
            jsonobject.put("result",2);
            jsonobject.put("msg",msg);
            jsonobject.put("err", e);
            jsonarray.put(jsonobject);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(jsonarray);
            LOGGER.warn("例外が発生",e);

            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

        }finally{
            try {
                if(rs != null)rs.close();
                if(ps != null)ps.close();
                if(ps2 != null)ps2.close();
                if(conn != null)conn.close();
            }catch(Exception e){
                LOGGER.warn("例外が発生",e);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}