<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo" %>

<% 
	UserVo authUser = (UserVo)session.getAttribute("authUser");
%>

		<!-- header -->
		<div id="header" class="clearfix">
			<h1>
				<a href="/mysite3/main">MySite</a>
			</h1>
			<%if(authUser!=null){%>
				<!-- session에 값이 있으면-->	
				<ul>
					<li><%=authUser.getName()%>님 안녕하세요^^</li>
					<li><a href="/mysite3/user?action=logout" class="btn_s">로그아웃</a></li>
					<li><a href="/mysite3/user?action=modifyform&no=<%=authUser.getNo()%>" class="btn_s">회원정보수정</a></li>
				</ul>
			<%} else { %>
				<!-- session에 값이 없으면  -->
				<ul>
					<li><a href="/mysite3/user?action=loginform" class="btn_s">로그인</a></li>
					<li><a href="/mysite3/user?action=joinform" class="btn_s">회원가입</a></li>
				</ul>
			<%} %>
		</div>
		<!-- //header -->
		<!-- nav -->
		<div id="nav">
			<ul class="clearfix">
				<li><a href="">입사지원서</a></li>
				<li><a href="">게시판</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="/mysite3/gbc?action=list">방명록</a></li>
			</ul>
		</div>
		<!-- //nav -->