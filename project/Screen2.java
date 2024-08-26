
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.*;
    
	public class Screen2 extends HttpServlet
	{

			private	Connection con;
			public void init(ServletConfig Config) throws ServletException                        
			{
					super.init(Config);									//register the init parameters
			}

			 public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
			 {
					
					/*check for session id*/
															HttpSession Session=req.getSession(false);
															System.out.println("Yes-107***********");
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

					PrintWriter pw=new PrintWriter(res.getWriter());
					c_Parameters	param=new c_Parameters();	//I use separate class for passing the parameters from one fun to other
					con=param.m_getConnection();			//get Connection from c_Prameters class
												param.req=req;
												param.res=res;
												param.pw=pw;
											
					PreparedStatement st;
					String sql;
				
					param.m_writeGetParameters(param);				// in this fuction all parameters store in hashTable
					Hashtable hashTable=param.hashTable;
					if(Session!=null)
					{
						Hashtable Session_hashTable=(Hashtable)Session.getValue("hashTable");
						if(Session_hashTable!=null)
						{
							hashTable=param.copy_hashTable_To_hashTable(hashTable,Session_hashTable );
						}else	System.out.println("Screen2:=>Session hashTable is Null");
					}else	System.out.println("Screen2:=>Session is Null");

				try{
				
								param.con=con;
								sql="Select Flight_No From Flight_Master where Flight_No=?";
								st=con.prepareStatement(sql);
								
								System.out.println((String)hashTable.get("Flight_No"));
								st.setString(1,(String)hashTable.get("Flight_No"));//sets filght number.
								ResultSet	rs=st.executeQuery();
								System.out.println(HttpUtils.getRequestURL(req));

								if(rs.next()) //Duplicate 
								{
										res.sendRedirect("/Errorpage.html");//encodeRedirectURL
										System.out.println("Line=>84");
								}
								else{
												System.out.println("Line=>86");
												String Title       ="Search2";

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


									   }//else
				}//catch()
				  catch(SQLException e){ res.setContentType("text/html");
														  e.printStackTrace(pw);
														  //res.sendRedirect("http://locdalhost:8080/Screen1.shtml");
														}

				  catch(NumberFormatException e){ res.sendRedirect("http://location:8080/Screen1.shtml");pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}

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
		
			pw.println("<BODY> ");
//			pw.println("<br><br><br>");
			pw.println("<form name=Screen2 method=POST action='http://localhost:8080/servlet/Screen3' >");

			pw.println("<TABLE bgcolor=#FFCC99 cellpadding=2 cellspacing=1 border=0 width=500>			");
			pw.println("<TR><TD valign=center align=center>																			");	
			pw.println("<TABLE bgcolor=#FFFFCC cellpadding=5 cellspacing=0 border=0 width=100%>		");
			pw.println("<tr align=center><td><font  color='#0033F' size=5>Arrange Flight Schedule</font></td></tr> ");
			pw.println("<TR>																																	");
			pw.println("<TD valign=center align=center>																						");


			pw.println("<Table  cellpadding='1' cellspacing='1' border='0' width=368 height=268   align=center><th align=left> ");			
			pw.println("<pre>");		

		try{
			
			String Flight_No=(String)hashTable.get("Flight_No");
			pw.println("Flight Number           <input name='Flight_No' value="+Flight_No+" size=7 disabled><br>"); 
				//Function for list all Category in listbox			
			Display_Caterory(param);		

			//pw.println("<center><input type='button' value='add' onclick='Validate(Screen2)'>       <input type=submit value=' Next '></center>");

			pw.println("<Table align=center cellspacing=0  Valign=center border=0 cellpadding=40>");
			pw.println("<Tr align=left> ");
			pw.println("<td align=left> <input type=button value=' Back ' onclick= Back_action('Screen2')>	</td>");
			pw.println("<td align=center><input type=button value=' Abort ' onclick=Abort_action('Screen2')></td>  ");
			pw.println("<td align=right > <input type=button  value=' Next '  onclick='Validate(Screen2)'></td></tr></center>");
			pw.println("	</Table>");

			pw.println("</td>");
			pw.println("<TABLE border=0 width=394 cellpadding=0 cellspacing=0></TABLE ></TD></TR></TABLE></TD></TR></TABLE ></table>");
			param.m_Make_all_them_hidden(param);
			pw.println("</form>");
			pw.println("</pre>");
			pw.println("</body>");

   	  } //catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
	    catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}
   }//writeHead()



	public void Display_Caterory(c_Parameters param)throws ServletException,IOException
		{
			String sql;		
			ResultSet rs=param.rs;
			PrintWriter pw=param.pw;
			PreparedStatement st;

			Connection con=param.con;
			try{
				sql="select C_Name from Category";
 				st=con.prepareStatement(sql);
				rs=st.executeQuery();
							String Category;
				
							while(rs.next())
							{	
									Category=rs.getString(1);
//									<TABLE cellpadding="1" cellspacing="1" border="0" width="568" bgcolor=#FFCC99>
									pw.println("<Table border=1 align=center cellpadding=1 cellspacing=1 border=0 width=400 >");
									pw.println("<tr>");
									pw.println("<br><td>Category </td><td><input  name="+Category+" value="+Category+" readonly></td>");
									pw.println("</tr>");												
									pw.println("<tr>");
									pw.println("<td>Cost of Ticket </td><td> <input name='"+Category+"_Cost_of_ticket' size=6></td>");
									pw.println("</tr>");												
									pw.println("<tr>");
									pw.println("<td>Starting Seat Number </td><td>   <input name='"+Category+"_Starting_SeatNo' size=3></td>");
									pw.println("</tr>");												
									pw.println("<tr>");
									pw.println("<td>Ending Seat Number </td><td>     <input name='"+Category+"_Ending_SeatNo' size=3></td>");
									pw.println("</tr>");												
									pw.println("</Table >");

							}
				}catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}

		}//Display_Category()

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
										//start<end &&start>0
				pw.println("		if(	 form1.First_Cost_of_ticket.value<=0 ||	"
								+	"			form1.Economic_Cost_of_ticket.value<=0 || "
								+	"			form1.Bussiness_Cost_of_ticket.value<=0 ) ");
				pw.println("		{");						
																				pw.println("			alert('Enter Proper Ticket Cost information');");
				pw.println("	    	} else");
    		   
			   			   pw.println("		if(	!(	parseInt(form1.First_Ending_SeatNo.value)			||				parseInt(form1.First_Starting_SeatNo.value )|| "              
    										+	"	parseInt(form1.Economic_Ending_SeatNo.value)			||				parseInt(form1.Economic_Starting_SeatNo.value  )|| "    								
											+	"	parseInt(form1.Bussiness_Ending_SeatNo.value)		||				parseInt(form1.Bussiness_Starting_SeatNo.value ) ) )");	
     			pw.println("		{");						
																				pw.println("			alert('Please do not enter characters in place of Numbers');");

			    pw.println("	    	} else");

			   pw.println("		if( 				form1.First_Starting_SeatNo.value<=0 ||"
											+ "			form1.First_Starting_SeatNo.value>parseInt(form1.Total_capacity.value)|| "
											+	"			form1.Economic_Starting_SeatNo.value<=0 || "
											+	"			form1.Economic_Starting_SeatNo.value<=parseInt(form1.First_Ending_SeatNo.value) || 			"
											+	"			form1.Bussiness_Starting_SeatNo.value<=parseInt(form1.Economic_Ending_SeatNo.value ))");
				pw.println("		{");						
																				pw.println("			alert('Starting Seat number should not be less than one or less than privious category ending number also not greater than Total capacity');");
//																				alert(form1.First_Starting_SeatNo.value+"="+form1.Total_capacity.value);
//																				alert(form1.Economic_Starting_SeatNo.value);
//																				alert(form1.Economic_Starting_SeatNo.value+"="+form1.First_Ending_SeatNo.value);
//																				alert(form1.Bussiness_Starting_SeatNo.value+"="+form1.Economic_Ending_SeatNo.value);
			   pw.println("	    	} else");
			
															
				pw.println("		if(				form1.First_Ending_SeatNo.value>parseInt(form1.Total_capacity.value)	||"
   										+	"			form1.Economic_Ending_SeatNo.value>parseInt(form1.Total_capacity.value) ||"									
										+	"			form1.Bussiness_Ending_SeatNo.value>parseInt(form1.Total_capacity.value )) ");
     			pw.println("		{");						
																				pw.println("			alert(form1.First_Ending_SeatNo.value+form1.Total_capacity.value+'Starting Seat number should not be  greater than Total capacity');");
			   pw.println("	    	} else");
			   
											
				pw.println("		if(				form1.First_Ending_SeatNo.value<parseInt(form1.First_Starting_SeatNo.value )|| "
											+	"			form1.Economic_Ending_SeatNo.value<parseInt(form1.Economic_Starting_SeatNo.value )|| "
											+	"			form1.Bussiness_Ending_SeatNo.value<parseInt(form1.Bussiness_Starting_SeatNo.value ))");				
										pw.println("		{");						
																					pw.println("			alert('Ending Seat Number should not be less  than its Staring number ');");
																					pw.println("		}");
																					pw.println("		else");
																					pw.println("			{");
																					pw.println("				addRecord();");
																					pw.println("			}");
																					pw.println("}");
//function for add Record in Flight_Desc
				pw.println("function addRecord()");
				pw.println("{");
				pw.println("  Screen2.action='http://localhost:8080/servlet/Screen3';");
				pw.println("  Screen2.submit();");
				pw.println("}");
				pw.println("function goHome()");
				pw.println("{");

				pw.println("window.navigate('http://localhost:8080/inside_holidaysairways.html');");
				pw.println("}");

				pw.println("function Back_action(form1)");
				pw.println("{");

				pw.println("history.back();");
				pw.println("}");
				pw.println("function Abort_action(form1)																		");
				pw.println("{																														");
				pw.println("	window.navigate('http://localhost:8080/inside_holidaysairways.html');		");
				pw.println("}																														");



				pw.println("</script>");
				pw.println("</html>");
	}//writeScript()



}//class Display_FlightNumber_List


																																																																																																																/* ssi tag not allowed in clint side scripting
																																																																																																																pw.print("<servlet code=give_category_info_me><param name='p_no' value='P1'></servlet>");
																																																																																																																*/
																																																																																																																/* so use applet
																																																																																																																<applet code='applet_call_from_search_add_flight' height=200 width=100>

																																																																																																																</applet>
																																																																																																																*/

