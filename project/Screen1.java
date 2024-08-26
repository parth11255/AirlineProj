
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
    
	public class Screen1 extends HttpServlet
	{
		private	Connection con;
		public void init(ServletConfig Config) throws ServletException                        
		{
				super.init(Config);
				c_Parameters	param=new c_Parameters();
		//param.hashTable.clear();//make empty hashTable

		}

     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {
		System.out.println("Line=>25=Screen1");
		//variables
		HttpSession Session=null;
		/*Session Tracking*/
										 try{
														Session=req.getSession(false);
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
					
												}catch(Exception e){					
								
																/*					param.res.setContentType("text/html");
																					e.printStackTrace(param.pw);
																					System.out.println("in catch");		*/				
																										String myMsg="Sorry,Wrong login_id or may Password ,Try again";
																										String nextURL="/Admin_login.html";
																										res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																					  }	 
								/*End session*/

			c_Parameters	param=new c_Parameters();
			con=param.m_getConnection();//get Connection from c_Prameters class
			param.req=req;
			param.res=res;
			param.con=con;
			param.res.setContentType("text/html");
			PrintWriter pw=new PrintWriter(res.getWriter());
			param.pw=pw;
			System.out.println("Line=>60");

			param.m_writeGetParameters(param);
			Hashtable hashTable=param.hashTable;

			String Table=(String)hashTable.get("Table1");
			String SelectName=(String)hashTable.get("SelectName1");

			//System.out.println(Table);
			//System.out.println(SelectName);
			
			//use this fn call for ssi here no any use of Table param in function
			 if(Table!=null && Table.equals("Plane") && SelectName.equals("Flight_No"))
						m_Display_html_options_in_listbox(param,Table);	

			Table=(String)hashTable.get("Table2");
			SelectName=(String)hashTable.get("SelectName2");

			 if(Table!=null && Table.equals("Location") && SelectName.equals("Location"))
						Display_Location(param);
	
			  if(Session!=null)
			  {
						Session.putValue("hashTable",hashTable);

			  }	else	{System.out.println("Session is null");}

     }//doGet()

    public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doGet(req,res);

    }//doPost()

	public void Display_Location(c_Parameters param)throws ServletException,IOException
	{
				PrintWriter pw=param.pw;
				try{		
							String sql="select P_no,P_name from  Location";
							PreparedStatement st=param.con.prepareStatement(sql);
							ResultSet rs       =st.executeQuery();
		
				
						String Location;
					
						while(rs.next())
						{	
								Location=rs.getString(1);
								pw.println("<br><option name="+Location+" value="+Location+">"+Location+"</option>");
						}

				}catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}

		}//Display_Location()

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
/*
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
	}//writeScript()
*/
}//class Screen1

