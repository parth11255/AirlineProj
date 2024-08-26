/*check whether login & password is correct for old passenger if yes then Display_all_paassenger_detail hatl page otherwise error error page with nexrRUI=R_login.html*/

    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
    
public class R_check_old_login extends HttpServlet
{

	public void init(ServletConfig Config) throws ServletException                        
	{
			super.init(Config);

	//param.hashTable.clear();//make empty hashTable
			
	}

     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {
				Connection con;
				c_Parameters	param=new c_Parameters();
				con=param.m_getConnection();//get Connection from c_Prameters class
				param.req=req;
				param.res=res;
				param.con=con;
				PrintWriter pw=new PrintWriter(res.getWriter());
						
		
			try{
						param.res.setContentType("text/html");
						
						param.pw=pw;
						param.m_writeGetParameters(param);
						Hashtable hashTable=param.hashTable;

						System.out.println((String)hashTable.get("password"));
						System.out.println((String)hashTable.get("login_id"));


						String login_id=(String)hashTable.get("login_id");
						String password=(String)hashTable.get("password");
						
						check_for_right_Passenger(param,login_id,password);
						System.out.println("AFTER FUNCTION");

	 }//catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
        //catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
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
					}catch(Exception ignore){}
			}

		  }//finally

     }//doGet()

    public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doGet(req,res);

    }//doPost()

	public void check_for_right_Passenger (c_Parameters param,String login_id,String password)throws ServletException,IOException
		{
				PrintWriter pw=param.pw;
				Hashtable hashTable=param.hashTable;
				
				try{		
							
							System.out.println( password.length() );System.out.print(login_id.length());

													System.out.println("BEFORE IF");
							if(login_id==null || password==null || login_id.length()==0 || password.length()==0)
							{
															String myMsg="Sorry,Wrong login_id or may Password ,Try again";
															String nextURL="/inside_holidaysairways.html";                                                                                                                 
															param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);     
																						                                                                                                                                                                          
							}else{
																				System.out.println("IN ELSE");
													String sql="select login_id,password from  Login where login_id='"+login_id+"'  and password='"+password+ "'	";
													PreparedStatement st=param.con.prepareStatement(sql);
																							//								st.setString(1,login_id);
																							//								st.setString(2,password);
												System.out.println("Yes-93***********");

													ResultSet rs       =st.executeQuery();
													System.out.println(rs);			
											
													if(!rs.next())
													{	
															System.out.println("Yes-100***********");
															String myMsg="Sorry2,Wrong login_id or may Password ,Try again";
															String nextURL="/R_login.html";
															param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
													}
													else
													{
													System.out.println("Yes-107***********");
															HttpSession Session=param.req.getSession(true);

															if(Session.isNew())
															{
																Session.putValue("login_id",(String)hashTable.get("login_id"));
																Session.putValue("password",(String)hashTable.get("password"));
															}
																												System.out.println("BEFORE CALL");
																param.res.sendRedirect("http://localhost:8080/servlets/R_Display_total_Number_of_Passenger_ScreensFrames");
																													System.out.println("AFTER CALL");
													}//esle
											}//else
								}catch(SQLException e){					
								
																					/*				param.res.setContentType("text/html");
																																							e.printStackTrace(param.pw);
								System.out.println("in catch");*/		String myMsg="Sorry,Wrong login_id or may Password ,Try again";
																										String nextURL="/inside_holidaysairways.html";
																										param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																		  }

		}//Display_Location()

/*
		public void m_Display_html_options_in_listbox (c_Parameters param,String Table)throws ServletException,IOException
		{
				PrintWriter pw=param.pw;
				try{
							String sql="select P_no,P_name from  Plane";
							PreparedStatement st=param.con.prepareStatement(sql);
							ResultSet rs       =st.executeQuery();
						
						
							String P_no,P_name;
						
										while(rs.next())
										{	
												P_no=rs.getString(1);P_name=rs.getString(2);
												pw.println("<br><option name="+P_name+" value="+P_no+">"+P_no+"</option>");
										}
							}catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}

		}//m_Display_html_options_in_listbox()

	public void writeScript(c_Parameters param)throws ServletException,IOException
	{
		PrintWriter pw=param.pw;
	
				pw.println("<script>");
				pw.println("function Validate(form1)");
				pw.println("{");
				pw.println("	if(form1.elements[0].value=='' || form1.elements[1].value=='' || form1.elements[2].value==''  || form1.elements[3].value=='' || form1.elements[4].value<=0 || form1.elements[5].value<=0 )");
				pw.println("	{");
				pw.println("		alert('Value should not be empty');");
				pw.println("	}else");
				pw.println("		if( form1.Source.value==form1.Destination.value )");
				pw.println("		{");
				pw.println("			alert('Source and Destination should not be same');");
				pw.println("		}");
				pw.println("		else");
				pw.println("			{");
				pw.println("				form1.action='http://localhost:8080/servlet/Screen2';");
				pw.println("				form1.submit();");
				pw.println("			}");
				pw.println("}");
				pw.println("</script>");
				pw.println("</html>");
/*
			//	rs.beforeFirst();
				String sql="select L_Name from Location";
 				PreparedStatement st=param.con.prepareStatement(sql);
				param.rs       =st.executeQuery();
				
		
				pw.println("function Display_DestinationLocation()");
				pw.println("{");
								
										while(rs.next())
										{	
												Location=rs.getString(1);
												pw.println("if(Source.value!="+Location+")");
												pw.println(" document.write('<br><option name="+Location+" value="+Location+">"+Location+"</option>');");
										}
				pw.println("}");

			}catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
			*/
//	}//writeScript()
}//class Screen1

