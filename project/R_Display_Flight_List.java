/*
	Line:153=>Remaining to do source-intermidiate-destination type query &result only done source-destination
	Onclick of  go-next btn call funcation getloginScreen(formname) & 
																					called form1.action='http://localhost:8080/servlet/getLoginScreen'
    form 
*/


    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
    import java.util.*;
	import java.text.*;

public class R_Display_Flight_List extends HttpServlet
{

     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {
				
						System.out.println("Line=>23");
						HttpSession Session;
						Connection con;
						c_Parameters	param=new c_Parameters();
						con=param.m_getConnection();								//get Connection from c_Prameters class
						param.con=con;
						param.req=req;
						param.res=res;
						PrintWriter  pw=new PrintWriter(res.getWriter());
						param.pw=pw;
						//get parameter from URL
						param.m_writeGetParameters(param);
						Hashtable hashTable=param.hashTable;

    	//	try{

						Session=param.req.getSession(true);
						param.Session=Session;

															if(Session.isNew())
															{
																		Session.putValue("hashTable",hashTable);
															}
/*        			}catch(SQLException e){					
								
																param.res.setContentType("text/html");
																			e.printStackTrace(param.pw);
								System.out.println("in catch");String myMsg="Sorry,Wrong login_id or may Password ,Try again";
																										String nextURL="/inside_holidaysairways.html";
																										param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
															  }*/
					/*	Enumeration eParamNames=req.getParameterNames();*/
		
	
			String Source					=req.getParameter("LSource");
	        String Destination			=req.getParameter("LDestination");
			String Month					=req.getParameter("LMonth");
			String Day						=req.getParameter("LDay");
	        String MorningEvening	=req.getParameter("LMorningEvening");
	        String Category				=req.getParameter("LCategory");
	        String Adult						=req.getParameter("LAdult");
	        String Child						=req.getParameter("LChild");

            Integer Total_ticket=new Integer(Integer.parseInt(Adult)+Integer.parseInt(Child));

		    //c_Parameters	param=new c_Parameters();
			//Make all of first flight search Screen fields as hidden to refer 
			//param.writeGetParametersFromScreen1(param);
			
       try{


			System.out.println("reach55");

			String sql="SELECT DISTINCT T1.Flight_No, [Flight_Name], [Source], [Destination], [Duration_of_flight], Cost_of_ticket, [Date_of_Departure], [Time_of_flight] "
								 +"FROM Flight_Master AS T1, Flight_Desc AS T2, Flight_Details AS T3, Current_Details AS T4  "
								 +"WHERE T1.Source=? And T1.Destination =? And t1.flight_no=t2.flight_no And T2.Category=? And t2.flight_no=t3.flight_no And (T3.Date_of_Departure - date())<=30 ";
			/****and Available_Seat>=? set confirm= y/n ****/

			/*Total_ticket+ */

 			PreparedStatement st=con.prepareStatement(sql);

			//st.setInt(1,Total_ticket.intValue());
			st.setString(1,Source);
			st.setString(2,Destination);
			st.setString(3,Category);
			ResultSet rs       =st.executeQuery();
			
			param.rs=rs;
			String Title       ="Dispaly_Flight_List";
			writeHead(param,Title);			
			System.out.println("reach75");

			writeBody(param);//also include m_writeTable(param) Function for Display list  
											// At last their is function m_Make_all_them_hidden;	
//			System.out.println("reach79");

			writeScript(param);

         }//catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
          catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
          catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}
		   catch(ParseException e){System.out.println(e.getMessage());}
		  
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

    public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doGet(req,res);
    }//doPost()

/*******************HTML FUNCTIONS**********************/

			public void writeHead(c_Parameters param,String Title)throws ServletException,IOException
				{

						param.res.setContentType("text/html");
						
						param.pw.println("<html><pre>");		
						param.pw.println("<Title>"+Title+"</Title>");
							
				}//writeHead()

				public void writeBody(c_Parameters param)throws ServletException,IOException,ParseException
				{
						HttpServletRequest req=param.req;
						HttpServletResponse res=param.res;
						Connection con=param.con;		
						ResultSet rs=param.rs;
						PrintWriter pw=param.pw;

						PreparedStatement st;
						String sql;
								
						//Start Html Page Here
						 pw.println("<BODY bgcolor='' link='#000000' vlink='#000000' text='#000000' marginwidth='0' marginheight='0' leftmargin='0' topmargin='0'>"); 
						 pw.println("<form name='Display_Flight_List' method=GET action='http://localhost:8080/servlet/search_add_flight'>");
			
				
						 pw.println("<center><b><u><font color='#000000'> Flight List</font></b></u></center>");
						 System.out.println("Line=>161");
				
						//Print the all records of rs in table format (query is written in servlet's body)
						if(	m_writeTable(param))
						{//if true then 1st search fails
								
							
						}
						
			
						/****ramain*****************************************
						String sql="SELECT [Destination]"+
					   "FROM Flight_Master AS T1, Flight_Desc AS T2, Flight_Details AS T3, Current_Details AS T4"+
					   "WHERE T1.Source=? And t1.flight_no=t2.flight_no And T2.Category=? And t2.flight_no=t3.flight_no And (T3.Date_of_Departure-date())<=30";
					   	 
						PreparedStatement st=con.prepareStatement(sql);
						st.setString(1,Source);
						st.setString(2,Destination);
						st.setString(2,Category);
						ResultSet rs       =st.executeQuery();
						param.rs=rs;

		******************************************************/


		
			 			
						pw.println("</pre>");

						pw.println("<Table align=center cellspacing=0  Valign=center border=0 cellpadding=40>");
						pw.println("<Tr align=left> ");
						pw.println("<td align=left> <input type=button value=' Back ' onclick= Back_action('Display_Flight_List')>	</td>");
						pw.println("<td align=center><input type=button value=' Abort ' onclick=Abort_action('Display_Flight_List')></td>  ");
						pw.println("<td align=right > <input type=button value=' Next ' name='Next' onclick='getLoginScreen(Display_Flight_List)'></td></tr></center>");
						pw.println("	</Table>");

						pw.println("<center><br><br></center>");
						//pw.println("<center><br><br><input type=button value='go Next' name='Next' onclick='getPassengerMasterScreen(Display_Flight_List)'></center>");

						//Make privious fields as hidden
						param.m_Make_all_them_hidden(param);
						pw.println("</form>");


						pw.println("</body>");
					
					
				}//writeBody

				public boolean m_writeTable(c_Parameters param)throws ServletException,IOException,ParseException
				{
						ResultSet rs=param.rs;
						PrintWriter pw=param.pw;
						boolean flag=true;
					
					try
					{

						while(rs.next())
						{
								flag=false;
								String Flight_No=rs.getString(1);
								String Flight_Name=rs.getString(2);
								String Source=rs.getString(3);
								String Destination=rs.getString(4);
								String Duration_of_flight=rs.getString(5);
								String Cost_of_ticket=rs.getString(6);

								
								Calendar cal_Date = Calendar.getInstance();	
								java.sql.Date DepartureDate =rs.getDate(7) ;//     throws SQLException
								cal_Date.setTime(DepartureDate);	

								Calendar cal_Time= Calendar.getInstance();	
//								System.out.println("Line=>223="+rs.getString(8));
								java.sql.Time Time_of_flight=rs.getTime(8);
								System.out.println("Line=>223="+Time_of_flight.toString());
								cal_Time.setTime(Time_of_flight);	
/*set(int year,
                      int month,
                      int date,
                      int hour,
                      int minute)
*/					 
								String D_Date=cal_Date.get(Calendar.MONTH)+1 +" /  "+cal_Date.get(Calendar.DATE)+" / "+cal_Date.get(Calendar.YEAR);                             
								String D_Time=cal_Time.get(Calendar.HOUR)+":"+cal_Time.get(Calendar.MINUTE);	                                                                                          
								String D_AMPM=null;
								if(cal_Time.get(Calendar.AM_PM)==1)
								{
									D_AMPM="PM";
								}else  D_AMPM="AM";

								System.out.println("D_AMPM "+D_AMPM);
								System.out.println("D_DATE "+D_Date);
								System.out.println("D_Time "+D_Time);
								System.out.println("Date "+cal_Date.getTime());
								System.out.println("Time "+cal_Time.getTime());

								System.out.println("My Time" +Time_of_flight );
								
								Calendar rightnow = Calendar.getInstance();	
								rightnow.set(cal_Date.get(Calendar.YEAR),cal_Date.get(Calendar.MONTH),cal_Date.get(Calendar.DATE),cal_Time.get(Calendar.HOUR),cal_Time.get(Calendar.MINUTE));	
								
								System.out.println("Line=>236");
								System.out.println("Line=>237"+DepartureDate.toString());

								rightnow.add(Calendar.HOUR,Integer.parseInt(Duration_of_flight));
								System.out.println(Calendar.HOUR);
//								java.sql.Date  Update_Date=(java.sql.Date)rightnow.getTime();	
								String A_Date=rightnow.get(Calendar.MONTH)+1 +" /  "+rightnow.get(Calendar.DATE)+" / "+rightnow.get(Calendar.YEAR);
//								String A_Time=Update_Date.getHours()+":"+Update_Date.getMinutes();						//DepartureDate.getHour()+":"+DepartureDate.getMinute();
								String A_Time=rightnow.get(Calendar.HOUR)+":"+rightnow.get(Calendar.MINUTE);	
								String A_AMPM=null;
								if(rightnow.get(Calendar.AM_PM)==1)
								{
									A_AMPM="PM";

								}else  A_AMPM="AM";

								System.out.println("A_AMPM"+A_AMPM);
								System.out.println("A_DATE"+A_Date);
								System.out.println("A_Time"+A_Time);
								System.out.println("Time"+rightnow.getTime());



								pw.println("<TABLE bgcolor='#FFCC99' cellpadding='2' cellspacing='1' border='0' width='568'>");
								pw.println("<TR>");
								pw.println("<TD valign=center align=center>");
								pw.println("<TABLE align=left border='0' cellpadding='5' cellspacing='0' bgcolor='FFFFCC' width='564'>");
								pw.println("<TR>");
								pw.println("<TD width='40' align=center rowspan='0'><INPUT type='radio' name=SelectedFlight  value="+Flight_No+"></TD>");
								pw.println("<TD>");
								pw.println("<TABLE cellspacing='0' cellpadding='0' border='0' width='100%'>");
								pw.println("<TR>");
								pw.println("<TD>");
								pw.println("<TABLE border='0' cellpadding='0' cellspacing='1' width='100%'>");
								pw.println("<TR>");
								pw.println("<TD><IMG src='/images/space.gif' alt='space' width='110' height='1'></TD>");
								pw.println("<TD><IMG src='/images/space.gif' alt='space' width='155' height='1'></TD>");
								pw.println("<TD><IMG src='/images/space.gif' alt='space' width='110' height='1'></TD>");
								pw.println("<TD><IMG src='/images/space.gif' alt='space' width='110' height='1'></TD>");
								pw.println("</TR>");
								pw.println("<TR>");
								pw.println("<TD valign=top align=left><FONT face='Arial,Helvetica,sans-serif' color='#000000' size='3'>"+Flight_No+"</FONT></TD>");
								pw.println("<TD valign='bottom' align=left colspan='2'><FONT face='Arial,Helvetica,sans-serif' color='#000000' size='3'><IMG src='/images/space.gif' alt='space' width='5' height='1'>Boeing 777-200</FONT></TD>");
								pw.println("<TD valign='bottom' align='right'><FONT face='Arial,Helvetica,sans-serif' color='#000000' size='3'>Stops 0</FONT></TD>");
								pw.println("</TR>");
								pw.println("<TR>");
								pw.println("<TD valign='bottom' align=left><FONT face='Arial,Helvetica,sans-serif' color='#000000' size='3'><IMG src='/images/space.gif' alt='space' width='10' height='1'><B>Depart</B></FONT></TD>");
								pw.println("<TD valign=top align=left><FONT face='Arial,Helvetica,sans-serif' color='#000000' size='3'><IMG src='/images/space.gif' alt='space' width='5' height='1'>"+Source+"</FONT></TD>");
								pw.println("<TD valign=top align=left><FONT face='Arial,Helvetica,sans-serif' color='#000000' size='3'><IMG src='/images/space.gif' alt='space' width='5' height='1'>"+D_Date+"</FONT></TD>");
								pw.println("<TD valign=top align=right><FONT face='Arial,Helvetica,sans-serif' color='#000000' size='3'><IMG src='/images/space.gif' alt='space' width='5' height='1'>"+D_Time+" "+D_AMPM+"</FONT></TD>");
								pw.println("</TR>");
								pw.println("<TR>");
								pw.println("<TD valign='bottom' align=left><FONT face='Arial,Helvetica,sans-serif' color='#000000' size='3'><IMG src='/images/space.gif' alt='space' width='10' height='1'><B>Arrive</B></FONT></TD>");
								pw.println("<TD valign=top align=left><FONT face='Arial,Helvetica,sans-serif' color='#000000' size='3'><IMG src='/images/space.gif' alt='space' width='5' height='1'>"+Destination+"</FONT></TD>");
								pw.println("<TD valign=top align=left><FONT face='Arial,Helvetica,sans-serif' color='#000000' size='3'><IMG src='/images/space.gif' alt='space' width='5' height='1'>"+A_Date+"</FONT></TD>");
								pw.println("<TD valign=top align=right><FONT face='Arial,Helvetica,sans-serif' color='#000000' size='3'><IMG src='/images/space.gif' alt='space' width='5' height='1'>"+A_Time+" "+A_AMPM+"</FONT></TD>");
								pw.println("</TR>");
								pw.println("</TABLE>");
								pw.println("</TABLE>");
								pw.println("</TABLE>");
								pw.println("</TABLE>");
						}
			
					}catch(SQLException e){pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
					 catch(NumberFormatException e){pw.print(e.getMessage()+"<html><body>Number Format error</body></html>");}
			    	 //catch(ParseException e){System.out.println(e.getMessage());}
	
					return flag;
	
					
				}//m_writeTable
			public void writeScript(c_Parameters param)throws ServletException,IOException
			{
					PrintWriter pw=param.pw;
				
							pw.println("<script>");

					//function for Validate the fields
							pw.println("function getPassengerMasterScreen(form1)");
							pw.println("{");
							//pw.println("	if(form1.elements[0].value==form1.elements[1].value)    ");
							//pw.println("	{");
							//pw.println("		alert('Source & Destination should not be Same');");
							//pw.println("     }");
							//pw.println("	else");
							//pw.println("			{");
							pw.println("				form1.action='http://localhost:8080/servlet/getPassengerMasterScreen';	");
							pw.println("				form1.submit();");
							//pw.println("			}");
							pw.println("}");

							

							pw.println("function getLoginScreen(form1)");
							pw.println("{");
//							pw.println("	if(form1.elements[0].)    ");
//							pw.println("	{");///
//							pw.println("		alert('Source & Destination should not be Same');");
//							pw.println("     }");
//							pw.println("	else");
							pw.println("			{");
							pw.println("				form1.action='http://localhost:8080/servlet/R_login' ;	");
							pw.println("				form1.submit();");
							pw.println("			}");
							pw.println("}");
							pw.println("function Back_action(form1)");
							pw.println("{");

							pw.println("history.back();");
							pw.println("}");
							pw.println("function Abort_action(form1)																		");
							pw.println("{																														");
							pw.println("	window.navigate('http://localhost:8080/inside_holidaysairways.html');		");
							pw.println("}		");
							pw.println("</script>");


							pw.println("</html>");
		   }//writeScript()



}//class Display_FlightNumber_List