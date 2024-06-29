package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
	private static final String query="SELECT Book_Name,Book_Edition,Book_Price from book1 where id=?";
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
			
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            rs.next();
            pw.println("<form action='editBook?id="+id+"' method='post'>");
            pw.println("<table align='center'>");
            pw.println("<tr>");
            pw.println("<td>Book Name</td>");
            pw.println("<td><input typr='text' name='bookName' value='"+ rs.getString(1)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Book Edition</td>");
            pw.println("<td><input typr='text' name='bookEdition' value='"+ rs.getString(2)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Book Price</td>");
            pw.println("<td><input typr='text' name='bookPrice' value='"+ rs.getFloat(3)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td><input type='submit' value='Edit'></td>");
            pw.println("<td><input type='reset' value='Cancel'></td>");
            pw.println("</tr>");
            pw.println("</table>");
            pw.println("</form>");
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
