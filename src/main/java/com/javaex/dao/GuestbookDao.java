package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {

	//필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/web_db";
	private String id = "web";
	private String pw = "web";
	//생성자
	//메소드g/s
	//메소드
	protected void getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		} catch(ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
	} // getConnection();
	protected void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	} // close();
	
	public void guestadd(GuestbookVo guestbookVo) {
		this.getConnection();
		try {
			String query = "";
			query += " insert into guestbook ";
			query += " value(null, ?, ?, ?, date_format(now(),'%Y-%m-%d %H:%i:%s')) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestbookVo.getName());
			pstmt.setString(2, guestbookVo.getPassword());
			pstmt.setString(3, guestbookVo.getContent());
			pstmt.executeUpdate();
			
			/*System.out.println("등록되었습니다.");*/
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
	} // guestadd()
	
	public List<GuestbookVo> guestSelect() {
		this.getConnection();
		List<GuestbookVo> guestbookList = new ArrayList<GuestbookVo>();
		try {
			String query = "";
			query += " select	no, ";
			query += " 			name, ";
			query += " 			password, ";
			query += "   		content, ";
			query += " 			reg_date ";
			query += " from guestbook ";
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String reg_date = rs.getString("reg_date");
				
				GuestbookVo guestbookVo = new GuestbookVo(no, name, password, content, reg_date);
				// 리스트에 주소 추가
				guestbookList.add(guestbookVo);			
				}
			
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return guestbookList;
	}// guestSelect
	
	public void guestDelete(int no) {
		this.getConnection();
		try {
			String query = "";
			query += " delete from guestbook ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
	}
}
