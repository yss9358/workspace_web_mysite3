package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("list".equals(action)) {
			GuestbookDao geustbookDao = new GuestbookDao();
			List<GuestbookVo> guestbookList = geustbookDao.guestSelect();
			System.out.println(guestbookList.toString());
			request.setAttribute("guestbookList", guestbookList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addlist.jsp");
		} else if("add".equals(action)) {
			/*System.out.println("book-add");*/
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			GuestbookVo guestbookVo = new GuestbookVo(name,password,content);
			/*System.out.println("값꺼내기");*/
			GuestbookDao guestbookDao = new GuestbookDao();
			guestbookDao.guestadd(guestbookVo);
			/*System.out.println("등록완료");*/
			WebUtil.redirect(request, response, "http://localhost:8080/mysite3/gbc?action=list");
		} else if("deleteform".equals(action)) {
			System.out.println("book-deleteform");
			GuestbookDao geustbookDao = new GuestbookDao();
			List<GuestbookVo> guestbookList = geustbookDao.guestSelect();
			System.out.println(guestbookList.toString());
			request.setAttribute("guestbookList", guestbookList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		} else if("delete".equals(action)) {
			System.out.println("delete");
			int no = Integer.parseInt(request.getParameter("no"));
			GuestbookDao guestbookDao = new GuestbookDao();
			guestbookDao.guestDelete(no);
			WebUtil.redirect(request, response, "/mysite3/gbc?action=list");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
