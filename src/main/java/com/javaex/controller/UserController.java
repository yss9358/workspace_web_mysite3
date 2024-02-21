package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		} else {
			System.out.println("action값을 다시 확인해주세요");
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
