package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/web_db";
	private String id = "web";
	private String pw = "web";
	// 생성자
	
	// 메소드 g/s
	
	// 메소드
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
	
	public int insertUser(UserVo userVo) { //추가
		this.getConnection();
		try {
			String query = "";
			query += " insert into users ";
			query += " value(null, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			pstmt.executeUpdate();
			System.out.println("등록완료");
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		return 1;
	} // int insertUser(UserVo userVo)
	
	public UserVo selectUserByIdPw(UserVo userVo) {
		UserVo authUser = null; // 새로 담아서 넘겨줄값을 지정
		this.getConnection();
		try {
			String query = "";
			query += " select	no, ";
			query += "  		name ";
			query += " from users ";
			query += " where id = ? ";
			query += " and password = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				authUser = new UserVo(no,name); // 내보낼 값을 지정해준다
			}
			
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return authUser; // 값을 내보내준다
	} // UserVo selectUserByIdPw(UserVo userVo)
	
	public UserVo getUserByNo(int no) {
		UserVo authUserVo = null;
		this.getConnection();
		try {
			String query = "";
			query += " select	no, ";
			query += " 			id, ";
			query += " 			password, ";
			query += "  		name, ";
			query += "  		gender ";
			query += " from users ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int userNo = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				authUserVo = new UserVo(userNo, id, password, name, gender);
			}

		}catch(SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return authUserVo;
	} // getUserByNo(int no);
	
	public void updateUser(UserVo userVo) {
		this.getConnection();
		
		try {
			String query = "";
			query += " update users ";
			query += " set password = ?, ";
			query += " name = ?, ";
			query += " gender = ? ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getPassword());
			pstmt.setString(2, userVo.getName());
			pstmt.setString(3, userVo.getGender());
			pstmt.setInt(4, userVo.getNo());
			
			pstmt.executeUpdate();
			System.out.println("수정되었습니다");
		}catch(SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
	} // updateUser(UserVo userVo)
}
