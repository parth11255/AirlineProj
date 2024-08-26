	
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.*;
    
public class AddRecordScreen2 extends HttpServlet
{
  /**wrote separate file c_Prameters.java*******/

     public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {

			Screen2 objScreen2=new Screen2();
			c_Parameters	param=new c_Parameters();
			param.req=req;
			param.res=res;
			PrintWriter pw=new PrintWriter(res.getWriter());
			param.pw=pw;
		
			PreparedStatement st;
			String sql;
		
			param.m_writeGetParameters(param);
			Hashtable hashTable=param.hashTable;

		   try{
			
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				Connection con   =DriverManager.getConnection("jdbc:odbc:A_pra");
		  		
				param.con=con;
				
				sql="INSERT INTO Flight_Desc VALUES (?,?,?,?,?)";
 				st=con.prepareStatement(sql);
/*
System.out.println("****LIST******");
System.out.println((String)hashTable.get("Flight_No"));
System.out.println((String)hashTable.get("Flight_Name"));
System.out.println((String)hashTable.get("Source"));
System.out.println((String)hashTable.get("Destination"));
System.out.println((String)hashTable.get("Total_capacity"));
System.out.println((String)hashTable.get("Duration_of_flight"));
System.out.println("END LIST");
*/

			st.setString(1,(String)hashTable.get("Flight_No"));
			st.setString(2,(String)hashTable.get("Category"));
			st.setString(3,(String)hashTable.get("Cost_of_ticket"));
			st.setString(4,(String)hashTable.get("Starting_SeatNo"));
			st.setString(5,(String)hashTable.get("Ending_SeatNo"));
				
			int Count=st.executeUpdate();

			String Title       ="AddRecordScreen2";
			ResultSet rs=st.getResultSet();			
			param.rs=rs;
						
			objScreen2.writeHead(param,Title);			
/*System.out.println("******after head**********");*/
			objScreen2.writeBody(param);
/*System.out.println("*****after body***********");*/
			objScreen2.writeScript(param);
/*System.out.println("*****after script***********");*/

		} catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
		  catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
		  catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}

			 }//doPost
		}//class Display_FlightNumber_List



/**********to****************/

/*****old from**********
          int count;
          res.setContentType("text/html");
          

          //it return the value attribute of select cnt. which is our FlightName
          String Flight_No       =req.getParameter("Flight_No");
          String Category        =req.getParameter("Category");
          String Cost_of_ticket  =req.getParameter("Cost_of_ticket");
          String Starting_SeatNo =req.getParameter("Starting_SeatNo");
          String Ending_SeatNo   =req.getParameter("Ending_SeatNo");

//System.out.print(Flight_No);
//System.out.print(Category);
      try{
             Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//             Connection con     =DriverManager.getConnection("jdbc:odbc:pra","ssi","ssi");
               Connection con =DriverManager.getConnection("jdbc:odbc:A_pra");
               Statement st       =con.createStatement();
               count=st.executeUpdate("INSERT INTO Flight_Desc VALUES ('"+ Flight_No + "','"+ Category+"',"+ Integer.parseInt(Cost_of_ticket)+","+ Integer.parseInt(Starting_SeatNo)+"," +Integer.parseInt(Ending_SeatNo)+")");
*/
/*              pw.print("yes "+count+" records updated");*//*
pw.print("<html>");
pw.println("<BODY bgcolor='#FFFFCC' link='#000000' vlink='#000000' text='#000000' marginwidth='0' marginheight='0' leftmargin='0' topmargin='0'> ");
pw.println("<pre><br>");
pw.println("<form name=Screen2 method=POST action='http://localhost:8080/servlet/Screen3'>");
pw.println("Flight Number           <input name='Flight_No' value="+Flight_No+" size=7><br>");
//pw.println("<br><br><table border=2>");
//pw.println("<td>");
pw.println("Category                <input name='Category' size=20><br>");
pw.println("Cost of Ticket          <input name='Cost_of_ticket' size=20><br>");
pw.println("Starting Seat Number    <input name='Starting_SeatNo' size=3><br>");
pw.println("Ending Seat Number      <input name='Ending_SeatNo' size=3><br><br><br><br>");
//pw.println("</td>");
//pw.println("</table>");


pw.println("<center><input type='button' value='add' onclick='addRecord()'>       <input type=submit></center>");
pw.println("</form>");
pw.println("</pre>");
pw.println("</body>");
pw.println("<script>");
pw.println("function addRecord()");
pw.println("{");
pw.println("  Screen2.action='http://localhost:8080/servlet/AddRecordScreen2';");
pw.println("  Screen2.submit();");
pw.println("}");
pw.println("</script>");

pw.println("</html>");


         }catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
          catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
          catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}

     }//doPost
}//class Display_FlightNumber_List


*/
/* ssi tag not allowed in clint side scripting
pw.print("<servlet code=give_category_info_me><param name='p_no' value='P1'></servlet>");
*/
/* so use applet
<applet code='applet_call_from_search_add_flight' height=200 width=100>

</applet>


*************old to************/