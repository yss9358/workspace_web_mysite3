package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 유저가 실행되는지 확인
		System.out.println("UserController"); // /user 가 실행되는지
		
		// user에서 업무구분
		String action = request.getParameter("action");
		System.out.println(action); // action 값에 뭘 넣었는지
		
		if("joinform".equals(action)) {
			//회원가입폼
			System.out.println("user>joinForm");
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		} else if("join".equals(action)) {
			/* http://localhost:8080/mysite3/user?action=join&id=aaa&password=123&name=정우성&gender=male */
			//회원가입
			System.out.println("user>join");
			//꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//꺼낸 데이터를 Vo로 저장
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo);//꺼낸값 확인
			
			//Vo에 저장한 데이터를 dao의 메소드로 실행시켜서 db에 추가해야함.
			UserDao userDao = new UserDao();
			userDao.insertUser(userVo);
			System.out.println("userdao완");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
		} else if("loginform".equals(action)){
			System.out.println("UserLoginForm");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		} else if("login".equals(action)) {
			// action=login 이 적용되는지
			System.out.println("User>login");
			// parameter 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			// 여러값 -> Vo로 묶어서 보내기			
			UserVo userVo = new UserVo(id,password);
			UserDao userDao = new UserDao();
			// userVo 는 id,pw를 가지고있다
			// authUser 는 no, name을 가지고 있다.
			UserVo authUser = userDao.selectUserByIdPw(userVo);
			
			if(authUser!=null) { // null이 아니면 -> 데이터가 존재 -> session 에 authUser 추가 로그인성공
				// session에다가 request에서 주소값을 얻어서 넣는다.
				HttpSession session = request.getSession();
				// session 안에 no,name을 가지고 있는 authUser를 넣어주고 불러올때 필요한 authUser별명을 지어준다
				session.setAttribute("authUser", authUser);
				WebUtil.redirect(request, response, "/mysite3/main");
			} else{ // 로그인실패
				
				WebUtil.redirect(request, response, "/mysite3/user?action=loginform");
			}
		} else if("logout".equals(action)) {
			System.out.println("user>logout");
			
			HttpSession session = request.getSession();
			// session 에서 값을 제거하면 로그아웃
			// session.removeAttribute(""); 하면 특정 값만 제거 가능
			session.invalidate();
			WebUtil.redirect(request, response, "/mysite3/main");
		} else if("modifyform".equals(action)) {
			System.out.println("user>modifyform");
			
			HttpSession session = request.getSession();
			int no = ((UserVo)session.getAttribute("authUser")).getNo();
			System.out.println(no);
			
			UserDao userDao = new UserDao();
			UserVo authUserVo = userDao.getUserByNo(no);
			request.setAttribute("authUserVo", authUserVo);
			System.out.println(authUserVo);
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		} else if("modify".equals(action)) {
			System.out.println("user>modify");			
			
			UserVo userVo = new UserVo();
			
			HttpSession session = request.getSession();
			int no = ((UserVo)session.getAttribute("authUser")).getNo();
			System.out.println(no);
			
			userVo.setNo(no);
			userVo.setPassword(request.getParameter("password"));
			userVo.setName(request.getParameter("name"));
			userVo.setGender(request.getParameter("gender"));
			
			UserDao userDao = new UserDao();
			userDao.updateUser(userVo);
			System.out.println(userVo.toString());
			
			session.removeAttribute("authUser");
			session.setAttribute("authUser", userVo);
		
			
			
			WebUtil.redirect(request, response, "/mysite3/main");
		} else {
			System.out.println("action값을 다시 확인해주세요");
		}
		
		
		
		
	} //doGet

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	} //doPost

}
