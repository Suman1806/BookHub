package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/editBook")
public class EditServlet extends HttpServlet {
	private static final String query="UPDATE book1 set Book_Name=?,Book_Edition=?,Book_Price=? where id=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException {
		///get PrintWriter
		PrintWriter pw=resp.getWriter();
		///set content type
		resp.setContentType("text/html");
		///get the id of record
		int id=Integer.parseInt(req.getParameter("id"));
		//edit data
		String bookName=req.getParameter("bookName");
		String bookEdition=req.getParameter("bookEdition");
		float bookPrice=Float.parseFloat(req.getParameter("bookPrice"));
		///Load JDBC driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException cnf) {
		cnf.printStackTrace();
		}
		///generate the connection
		try(Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/BookData","root","suman1801");
			PreparedStatement ps=con.prepareStatement(query);){
			ps.setString(1, bookName);
			ps.setString(2, bookEdition);
			ps.setFloat(3, bookPrice);
			ps.setInt(4,id);
			int count=ps.executeUpdate();
			if(count==1) {
				pw.println("<h2> Records is Edited Sucessfully</h2>");
			}else {
				pw.println("<h2> Records is not Edited</h2>");
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
