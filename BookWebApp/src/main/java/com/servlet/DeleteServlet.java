package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteBook")
public class DeleteServlet extends HttpServlet {
	private static final String query="DELETE from book1 where id=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException {
		///get PrintWriter
		PrintWriter pw=resp.getWriter();
		///set content type
		resp.setContentType("text/html");
		///get the id of record
		int id=Integer.parseInt(req.getParameter("id"));
		
		
		///Load JDBC driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException cnf) {
		cnf.printStackTrace();
		}
		///generate the connection
		try(Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/BookData","root","suman1801");
			PreparedStatement ps=con.prepareStatement(query);){
			ps.setInt(1,id);
			int count=ps.executeUpdate();
			
			if(count==1) {
				pw.println("<h2> Records is Deleted Sucessfully</h2>");
			}else {
				pw.println("<h2> Records is not Deleted </h2>");
			}
			pw.println("<a href='home.html'>Home</a>");
			pw.println("<br>");
			pw.println("<a href='bookList'>Book List</a>");
	
            }catch(SQLException se) {
			se.printStackTrace();	
		pw.println("<h2>"+se.getMessage()+"</h2>");	
		}catch(Exception e) {
			e.printStackTrace();
			pw.println("<h2>"+e.getMessage()+"</h2>");
		}
		
	}
		
	@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
		}
}
