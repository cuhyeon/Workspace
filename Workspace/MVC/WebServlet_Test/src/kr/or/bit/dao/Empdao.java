package kr.or.bit.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import kr.or.bit.dto.Empdto;
import kr.or.bit.utils.SingletonHelper;


public class Empdao {
	
	DataSource ds = null;
	public Empdao() throws NamingException {
		//conn = SingletonHelper.getConnection("oracle");
		 Context context = new InitialContext(); //이름기반 검색
		 ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle"); ///jdbc/oracle pool 검색
	}
	
	//EMP 직원 리스트 (한건)
	public Empdto getEmpListByEmpno(String id) {
		return null;
	}
	
	//EMP 직원 리스트 (여러건)
	public List<Empdto> getEmpList() throws SQLException{

		
		PreparedStatement pstmt = null;
		String sql = "select EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO from emp";
		//POOL 연결 객체 얻어오기
		Connection conn = ds.getConnection();
		//
		pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		List<Empdto> emplist = new ArrayList<>();
		//[] 
		while(rs.next()) {
			Empdto m = new Empdto();
			m.setEname(rs.getString("ENAME"));
			m.setJob(rs.getString("JOB"));
			m.setHiredate(rs.getString("HIREDATE"));
			m.setSal(rs.getInt("SAL"));
			m.setComm(rs.getInt("COMM"));
			m.setMgr(rs.getInt("MGR"));
			m.setEmpno(rs.getInt("EMPNO"));
			m.setDeptno(rs.getInt("DEPTNO"));
			emplist.add(m);
		}
		
		SingletonHelper.close(rs);
		SingletonHelper.close(pstmt);
		conn.close();
		return emplist;
	}
	
	// List 요청에대한 서비스 
	
	
	
	
	//EMP 직원 INSERT
	public int insertEmp(String ename, String job, int empno, int mgr, String hiredate, int sal, int comm, int deptno)  {
		int resultrow=0;
		PreparedStatement pstmt = null;
		Connection conn = null;
		try { 
			
				String sql = "insert into EMP(ENAME,JOB,EMPNO,MGR,HIREDATE,SAL,COMM,DEPTNO) values(?,?,?,?,?,?,?,?)";
				conn = ds.getConnection();
				

			   pstmt = conn.prepareStatement(sql);
			   pstmt.setString(1, ename);
			   pstmt.setString(2, job);
			   pstmt.setInt(3, empno);
			   pstmt.setInt(4, mgr);
			   pstmt.setString(5, hiredate);
			   pstmt.setInt(6, sal);
			   pstmt.setInt(7, comm);
			   pstmt.setInt(8, deptno);
			   

			   
			   resultrow = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("Insert :" + e.getMessage());
			//executeUpdate(); 예외발생  (제약 위반, 컬럼 길이)
			resultrow = -1;
		}finally {
			SingletonHelper.close(pstmt);
			//POINT //반환하기
			try {
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			} 
		}
 
		return resultrow;
	}
	
	
	
	public int updateEmp(String job, int empno, int mgr, String hiredate, int sal, int comm, int deptno, String ename) {
		//update memo set email=? , content=? where id=?
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result=0;
		
		
		try { 
			
			   String sql = "update EMP set JOB=?, EMPNO=?, MGR=?, HIREDATE=?, COMM=?, SAL=?, DEPTNO=? where Ename=?";
			   conn = ds.getConnection();			

			   pstmt = conn.prepareStatement(sql);
			   pstmt.setString(1, job);
			   pstmt.setInt(2, empno);
			   pstmt.setInt(3, mgr);
			   pstmt.setString(4, hiredate);
			   pstmt.setInt(5, sal);
			   pstmt.setInt(6, comm);
			   pstmt.setInt(7, deptno);
			   pstmt.setString(8, ename);
			   
			   result = pstmt.executeUpdate();
			   
			
		}catch(Exception e1) {
			System.out.println("Insert :" + e1.getMessage());
			//executeUpdate(); 예외발생  (제약 위반, 컬럼 길이)
			result = -1;
		}finally {
			SingletonHelper.close(pstmt);
			try {
				conn.close();
			} catch (Exception e2) {
				
				e2.printStackTrace();
			} 
		}
 
		return result;
		
		
	}

	
	
}
