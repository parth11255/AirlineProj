
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.*;
    
public class Screen3 extends HttpServlet
{
     public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {

			/*Session Tracking*/
			HttpSession Session=req.getSession(false);
			System.out.println("Line=>15=Screen3");
													try{
														
															String login_id=(String)Session.getValue("login_id");
															String password=(String)Session.getValue("password");


															if(login_id==null || password==null)
															{
																	String myMsg="Sorry,login_id session expired ,Try to login again";
																	String nextURL="/Admin_login.html";
																	res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																	return;
																	
															}
																//param.res.sendRedirect("/Choice_Modification_Deletion.html");
					
													}catch(Exception e){/*	param.res.setContentType("text/html");
																						e.printStackTrace(param.pw);
																						System.out.println("in catch");		*/
																										String myMsg="Sorry,Wrong login_id or may Password ,Try again";
																										String nextURL="/Admin_login.html";
																										res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																					  }	 


			c_Parameters	param=new c_Parameters();
			param.req=req;
			param.res=res;
			PrintWriter pw=new PrintWriter(res.getWriter());
			param.pw=pw;
	
			PreparedStatement st;
			String sql;
		
			param.m_writeGetParameters(param);
			Hashtable hashTable=param.hashTable;
			if(Session!=null)
			{
				Hashtable Session_hashTable=(Hashtable)Session.getValue("hashTable");
				if(Session_hashTable!=null)
				{
					hashTable=param.copy_hashTable_To_hashTable(Session_hashTable,hashTable );

				}else	System.out.println("Screen3:=>Session hashTable is Null");
			}else	System.out.println("Screen3:=>Session is Null");

	    try{
		
		    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		    Connection con   =DriverManager.getConnection("jdbc:odbc:A_pra");
	  	
			param.con=con;
      
			String Title       ="Search3";
		
			writeHead(param,Title);			
			/*System.out.println("******after head**********");*/
			
			writeBody(param);
			/*System.out.println("*****after body***********");*/
			
			writeScript(param);
			/*System.out.println("*****after script***********");*/
			if(Session!=null)
			{
						Session.putValue("hashTable",hashTable);
			}else	System.out.println("Screen2:=>Session is Null");


         }catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
          catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
          catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}

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
			Hashtable hashTable=param.hashTable;
			PreparedStatement st;
			String sql;
		
			pw.println("<BODY  bgcolor=''  link='#000000' vlink='#000000' text='#000000' marginwidth='0' marginheight='0' leftmargin='0' topmargin='0'> ");
//			pw.println("<br><br><br>");
			pw.println("<form name=Screen3 method=POST onckick='goHome()'>");
			
			pw.println("<TABLE bgcolor=#FFCC99 cellpadding=2 cellspacing=1 border=0 width=500 align=center>			");
			pw.println("<TR><TD valign=center align=center>																			");	
			pw.println("<TABLE bgcolor=#FFFFCC cellpadding=5 cellspacing=0 border=0 width=100%>		");
			pw.println("<tr align=center><td><font  color='#0033F' size=5>Arrange Flight Schedule</font></td></tr> ");
			pw.println("<TR>																																	");
			pw.println("<TD valign=center align=center>																						");
	
			pw.println("<Table  cellpadding='1' cellspacing='1' border='0' width=368 height=268 bgcolor='' align=center ><th align=left> ");			
			pw.println("<pre>");

		try{
			String Flight_No=(String)hashTable.get("Flight_No");

			pw.println("Flight Number           <input name='Flight_No' value="+Flight_No+ " size=7 disabled ><br>");

			pw.print("Date of Departure       ");
			
			m_Display_Day_Month_Year(param);

//			pw.print("Day of Flight		 ");
//			m_Display_Weeks_with_listbox(param);

			pw.println("Time of Flight          <input name='Hour' size=2 value='12' onchange=''><input name='Minute' size=2 value='00' onchange=''><input name='Second' size=3 value='00' onchange=''>hr:min:sec<br>");
			pw.print("				AM <input type=radio name='AMPM' value='AM' checked=true>  ");
			pw.print("    PM <input type=radio name='AMPM' value='PM'>");
			pw.println("</pre>");
			pw.println("</th></table>");





			pw.println("<Table align=center cellspacing=0 Valign=center border=0 cellpadding=14><Th> <td> <input type=button value=' Back ' onclick=Back_action('Screen3')>	</td><td>		<input type=button value=' Abort '  onclick=Abort_action('Screen3')></td>		<td><input type='button' value='  Add  ' onclick='Validate(Screen3)'  >  </td> </table>");
			pw.println("</td>");
			pw.println("<TABLE border=0 width=394 cellpadding=0 cellspacing=0></TABLE ></TD></TR></TABLE></TD></TR></TABLE ></table>");

			param.m_Make_all_them_hidden(param);
			pw.println("</form>");
			pw.println("</body>");

   	  } //catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
	    catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}
   }//writeHead()


   	public void writeScript(c_Parameters param)throws ServletException,IOException
	{
		PrintWriter pw=param.pw;
	
				pw.println("<script>");
				//function for Validate the fields
				pw.println("function Validate(form1)");
				pw.println("{");
				pw.println("	if(form1.elements[0].value=='' || form1.elements[1].value=='' || form1.elements[2].value==''  || form1.elements[3].value=='' || form1.elements[4].value<=0  )");
				pw.println("	{");
				pw.println("		alert('Value should not be empty');");
				pw.println("	}else");//ending seat number must be smaller than capacity 
/*										//start<end &&start>0
				pw.println("		if( form1.elements[3].value<=0 || form1.elements[3].value>form1.elements[4].value || form1.elements[4].value>="+(String)param.hashTable.get("Total_capacity")+")");
				pw.println("		{");
				pw.println("			alert('Enter Proper Seat information');");
				pw.println("		}");
				pw.println("		else");*/
				pw.println("			{");
				pw.println("					if( Validate_Time(form1))");
				pw.println("					{");
				pw.println("							Update_Flight_Master_Details_Desc_Current();");
				pw.println("					}");
				pw.println("			}");
				pw.println("}");

				//function for Validate TIme fields
				pw.println("function Validate_Time(form1)");
				pw.println("{");
				pw.println("	if(form1.elements[4].value=='' || form1.elements[5].value=='' || form1.elements[6].value=='' )");
				pw.println("	{");
				pw.println("		alert('Value should not be empty');return false;");
				pw.println("	}else");//ending seat number must be smaller than capacity 
										//start<end &&start>0
				pw.println("		if( form1.elements[4].value<=0 || form1.elements[4].value>12 )");
				pw.println("		{");
				pw.println("			alert('Hour : Enter Proper Time information');form1.elements[4].focus=true;return false;");
				pw.println("		}");
				pw.println("		else if( form1.elements[5].value<0 || form1.elements[5].value>59) ");
				pw.println("			{");
				pw.println("				alert('Minute : Enter Proper Time information');form1.elements[5].focus=true;return false;");
				pw.println("			}");
				pw.println("		else if(form1.elements[6].value <0 || form1.elements[6].value>59 )");
				pw.println("			{");
				pw.println("				alert('Second : Enter Proper Time information');form1.elements[6].focus=true;return false;");
				pw.println("			}else {return true;}");
				pw.println("}");


				//function for add Record in Flight_Desc
/*				pw.println("function addRecord()");
				pw.println("{");
				pw.println("line168  Screen2.action='http://localhost:8080/servlet/AddRecordScreen2';");
				pw.println("  Screen2.submit();");
				pw.println("}");
*/
				pw.println("function Update_Flight_Master_Details_Desc_Current()");
				pw.println("{");
				pw.println("  Screen3.action='http://localhost:8080/servlet/Update_Flight_Master_Details_Desc_Current';");
				pw.println("  Screen3.submit();");
				pw.println("}");

				pw.println("function goHome()");
				pw.println("{");
			
				pw.println(" window.navigate('http://localhost:8080/inside_holidaysairways.html');");
				pw.println("  ");
				pw.println("}");

				pw.println("function Back_action(form1)																			");
				pw.println("{																														");
				pw.println("		history.back();	  ");  
				pw.println("}																														"); 
				pw.println("function Abort_action(form1)																		");
				pw.println("{																														");
				pw.println("	window.navigate('http://localhost:8080/inside_holidaysairways.html');		");
				pw.println("}																														");

				pw.println("</script>");																								  
				pw.println("</html>");																									  
																																					  
	}//writeScript()																														


	public void m_Display_Day_Month_Year(c_Parameters param )
	{
		PrintWriter pw =param.pw;
		//Day
		
		pw.print("<select name=Day size=1>"); 
		
		for(int i=1;i<=31;i++)
		{
			pw.println("<option name="+i+" id="+i+" value="+i+">"+i+"</option>");
		}//for
		
		pw.print("</select>Day");

		//Month
		
		pw.print("<select name=Month size=1>"); 
		
		for(int i=1;i<=12;i++)
		{
			pw.println("<option name="+i+" id="+i+" value="+i+">"+i+"</option>");
		}//for
		
		pw.print("</select>Month");
		
		//Year
				
		pw.print("<select name=Year size=1>"); 
		
		for(int i=2001;i<=2010;i++)
		{
			pw.println("<option name="+i+" id="+i+" value="+i+">"+i+"</option>");
		}//for
		
		pw.println("</select>Year<br>");
		
		
	}//m_Display_Day_Month_Year(c_Parameters param )

	public void m_Display_Weeks_with_listbox(c_Parameters param)
	{
		PrintWriter pw =param.pw;
		pw.print("<select name=Day_of_flight size=1>"); 
			pw.println("<option name=Sunday id=Sunday value=Sunday>Sunday</option>");
			pw.println("<option name=Monday id=Monday value=Monday>Monday</option>");
			pw.println("<option name=Thuesday id=Thuesday value=Thuesday>Thuesday</option>");
			pw.println("<option name=Wednesday id=Wednesday value=Wednesday>Wednesday</option>");
			pw.println("<option name=Thusday id=Thusday value=Thusday>Thusday</option>");
			pw.println("<option name=Friday id=Friday value=Friday>Friday</option>");
			pw.println("<option name=Saturday id=Saturday value=Saturday>Saturday</option>");
       pw.println("</select><br>");
		
	}


}//class Display_FlightNumber_List


