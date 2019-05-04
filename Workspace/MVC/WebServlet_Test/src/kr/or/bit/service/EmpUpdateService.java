package kr.or.bit.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.Empdao;

public class EmpUpdateService implements Action {


	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		  ActionForward	forward = null;
		  try {
			  
			  	  System.out.println("회원 수정까진 왔음");
				  request.setCharacterEncoding("UTF-8");
				  System.out.println(request.getParameter("empno")); 
				  
				  String ename = request.getParameter("ename");
			  	  String job = request.getParameter("job");
			  	  int empno = Integer.parseInt(request.getParameter("empno"));
			  	  int mgr = Integer.parseInt(request.getParameter("mgr"));
			  	  String hiredate = request.getParameter("hiredate");
			  	  int sal = Integer.parseInt(request.getParameter("sal"));
			      int comm = Integer.parseInt(request.getParameter("comm"));
			  	  int deptno = Integer.parseInt(request.getParameter("deptno"));
			  	  
			  	  
			  	  Empdao dao = new Empdao();
			  	  int result = dao.updateEmp(job, empno, mgr, hiredate, sal, comm, deptno, ename );
			  	  
			  	  String msg="";
			  	  String url="";
			  	  
			  	  if(result > 0) {
			  		  msg ="수정성공";
			  		  url ="EmpSearch.EMP";
			  	  }else { //-1 (제약, 컬럼길이 문제)
			  		  msg ="수정실패";
			  		  url ="/index.html";
			  	  }
			  	  request.setAttribute("board_msg", msg);
			  	  request.setAttribute("board_url", url);
			  	  
			  	  forward = new ActionForward();
			  	  forward.setRedirect(false); //forward 방식
			  	  forward.setPath("/WEB-INF/view/redirect.jsp");
			  	  
		  }catch (Exception e) {
			e.printStackTrace();
		  }	
		  
		
			return forward;
	}

}
