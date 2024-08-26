
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.*;

public class R_AddRecord_PassengerMasterScreen extends HttpServlet
{

/**wrote separate file c_Prameters.java*******/
public static int PassCount=0;
     public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {


			c_Parameters	param=new c_Parameters();

			param.req=req;
			param.res=res;
			PrintWriter pw=new PrintWriter(res.getWriter());
			param.pw=pw;
	
			Hashtable hashTable;
			PreparedStatement st;
			String sql;

			param.m_writeGetParameters(param);
			hashTable=param.hashTable;	



	    try{
		
		    Connection	con=param.m_getConnection();
			param.con=con;

				sql="INSERT INTO Passenger_Master VALUES (?,date(),?,?,?,?,?)";

			st       =con.prepareStatement(sql);
/* for tracing
System.out.println("***********from************");	
System.out.println((String)hashTable.get("Res_No"));
System.out.println(	(String)hashTable.get("Flight_No"));
System.out.println((String)hashTable.get("Address"));
System.out.println((String)hashTable.get("State"));
System.out.println((String)hashTable.get("Phone_No"));
System.out.println((String)hashTable.get("E-mail_Address"));
*/

			st.setString(1,(String)hashTable.get("Res_No"));
			st.setString(2,(String)hashTable.get("Flight_No"));
			st.setString(3,(String)hashTable.get("Address"));
			st.setString(4,(String)hashTable.get("State"));
			st.setString(5,(String)hashTable.get("Phone_No"));
			st.setString(6,(String)hashTable.get("E-mail_Address"));

            int count=st.executeUpdate();
			String Title       ="AddRecord_PassengerMasterScreen";
			
			//invoke same function from Screen3
			//res.sendRedirect("http://localhost:8080/servlets/Search_Flight_For_Booking");
			Screen3 objScreen3 =new Screen3();
		
			writeHead(param,Title);			

			writeBody(param);

			//writeScript(param);


         }//catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
          catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
          catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}

  		  finally
		  {
			if(param.rs!=null)
			{
				try{
					param.rs.close();
					}catch(SQLException ignore){}
			}
			if(param.con!=null)
			{
				try{
					param.con.close();
					}catch(SQLException ignore){}
			}


		  }//finally

     }//doPost

    public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doPost(req,res);

    }//doGet()


/*******************HTML FUNCTIONS**********************/

			public void writeHead(c_Parameters param,String Title)throws ServletException,IOException
				{
	
			
						param.res.setContentType("text/html");

			
						param.pw.println("<html><pre>");		
						param.pw.println("<Title>"+Title+"</Title>");
							
				}//writeHead()



				public void writeBody(c_Parameters param)throws ServletException,IOException
				{
						HttpServletRequest req=param.req;
						HttpServletResponse res=param.res;
						Connection con=param.con;		
						ResultSet rs=param.rs;
						PrintWriter pw=param.pw;
						java.util.Date Today=new java.util.Date();

						String Flight_No=(String)param.hashTable.get("SelectedFlight");			
						String Res_No=null;

			try

			{
				//take last(Current for this passenger) Res_No.
				
					String sql="SELECT max(Res_No)"
							   +"FROM Passenger_Master ";
					PreparedStatement st=con.prepareStatement(sql);
					rs       =st.executeQuery();
					
					if(rs.next());
					{
						Res_No=rs.getString(1);
						System.out.println(Res_No);
					}

			}catch(Exception e){e.getMessage();}
					

						pw.println("<BODY bgcolor='#FFFFCC' link='#000000' vlink='#000000' text='#000000' marginwidth='0' marginheight='0' leftmargin='0' topmargin='0'> ");
						pw.println("<form name=AddRecord_PassengerMasterScreen method=POST action='http://localhost:8080/servlet/AddRecord_PassengerMasterScreen '>");
			            pw.println("<table align=center><td><pre>");
						pw.println("<center><b><u><font color='#000000'> Passenger Detail </font></b></u></center>");
			
						PassCount=PassCount+1;
System.out.println(PassCount);
						pw.println("<center><font color='grey'> Person Detail</font></center>");

						pw.println("<br>");
						pw.println("<br>Enter Detail for Passenger Number "+PassCount+"		<input name='Pass_No' size=3>{auto}");
						pw.println("");
						pw.println("");
						pw.println("<br>Name 		<input name='Name' size=30>");
						pw.println("");	
						pw.println("<br>Age  		<input name='Age' size=3>");
						pw.println("");
						pw.println("<br>Gender		<select name='Gender' size=1><option>Male</option><option>Female</option></select>");
						pw.println("");
						pw.println("<pre>");
						pw.println("<br><center><font color='grey'> Credit Card Detail</font></center>");
						pw.println("");
						pw.println("<br>Passport Number	<input name='Passport_No' size=8>");
						pw.println("");
						pw.println("<br>Visa Type	<select name='Visa_Type' size=1>");
						Display_VisaTypes(param);
						pw.println("</select>");
						pw.println("");
						pw.println("");
						pw.println("<center>");				
						//pw.println("<input type=button value=' Next ' onclick=Validate(getPassengerMasterScreen)>");
						pw.println("<input type=submit value='go Next' >");
						pw.println("</center>");


						pw.println("</pre></td></table>");
					

						pw.println("</form>");
						
						pw.println("</body>");
						pw.println("</html>");


				}//writeBody();

		public void Display_VisaTypes(c_Parameters param)throws ServletException,IOException
		{
			String sql;		
			ResultSet rs=param.rs;
			PrintWriter pw=param.pw;
			PreparedStatement st;

			Connection con=param.con;
			try{
				sql="select V_Name from VisaDetail";
 				st=con.prepareStatement(sql);
				rs=st.executeQuery();
							String VisaName;
				
							while(rs.next())
							{	
									VisaName=rs.getString(1);
									pw.println("<br><option name="+VisaName+" value="+VisaName+">"+VisaName+"</option>");
							}
				}catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}

		}//Display_State()



}//class Display_FlightNumber_List

