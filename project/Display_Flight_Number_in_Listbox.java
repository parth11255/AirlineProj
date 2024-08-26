
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class Display_Flight_Number_in_Listbox	 extends HttpServlet
{
     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {
          res.setContentType("text/html");
		 c_Parameters param=new c_Parameters();


          PrintWriter pw=new PrintWriter(res.getWriter());

		  String SelectFlightNumber=req.getParameter("SelectFlightNumber");//value=true
		  System.out.println(SelectFlightNumber);

       try{
             Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			 Connection con=param.m_getConnection();
             //Connection con =DriverManager.getConnection("jdbc:odbc:pra","ssi","ssi");
             Statement st=con.createStatement();

			 if(SelectFlightNumber!=null)
			 {
							 st.executeQuery("select Flight_No,Flight_Name from Flight_Master");
							 ResultSet rs =st.getResultSet();

							//Start Html Page Here

							
							 pw.println("<select name='Flight_No'>");
							   while(rs.next())
							   {
									String Flight_No= rs.getString("Flight_No") ;
									pw.println("<option value='" +Flight_No+ "'>" + Flight_No+ " </option>");
							   }
							pw.print("</select> ");
							//Flight Name  <input name='FlightName' size=15><br>Category Available        Capacity        Ticket Cost<pre><input type='checkbox' name='CategoryAvailable1'>Economy              <input name='Capacity1' size=5>         <input name='TicketCost1' size=5><input type='checkbox' name='CategoryAvailable2'>First                <input name='Capacity2' size=5>         <input name='TicketCost2' size=5><input type='checkbox' name='CategoryAvailable3'>Bussiness            <input name='Capacity3' size=5>         <input name='TicketCost3' size=5>Leaveing Date<table border=2><td><pre>Date        Month<input  name='LMonth' size=5>Day<input  name='LDay' size=5>Year<input  name='LYear' size=5>Time        Hour<input  name='LHour' size=5>Minute<input  name='LMinute' size=5>        <input  Type='radio' name='LAMPM' value='AM'>A.M.        <input  Type='radio' name='LAMPM' value='PM'>P.M.</td></table><table border=1><td><pre>Source City             Destination City  ");
							//pw.println("<pre><input name='SourceCity' size=15>          <input name=DestinationCity size=15></td></table>Return Date<table border=1><td><pre>Date        Month<input  name='RMonth' size=5>Day<input  name='RDay' size=5>Year<input  name='RYear' size=5>Time        Hour<input  name='RHour' size=5>Minute<input  name='RMinute' size=5>        <input  Type='radio' name='RAMPM' value='AM'>A.M.        <input  Type='radio' name='RAMPM' value='PM'>P.M.</td></table></pre></td></table></center><center><input type='submit'></center></form></body></html>");
							//</pre></td></table></center><center><input type='submit'></center></form></body></html>");
				}//if
         }catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
          catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
          catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}

     }//doPost
}//class Display_FlightNumber_List
