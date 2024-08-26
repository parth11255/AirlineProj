/*check whether login & password is correct for old passenger if yes then Display_all_paassenger_detail hatl page otherwise error error page with nexrRUI=R_login.html*/

    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
    
public class V_check_login extends HttpServlet
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
						param.m_writeGetParameters(param);//writes parameters to hash tables
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
															String myMsg="Wrong login_id or may Password ,Try again";
															String nextURL="/inside_holidaysairways.html";                                                                                                                 
															param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);     
																						                                                                                                                                                                          
							}else{
													System.out.println("IN ELSE");
													String sql="select User_id,password from  Registration_Details where User_id='"+login_id+"'  and password='"+password+ "'	";
													PreparedStatement st=param.con.prepareStatement(sql);
																							//								st.setString(1,login_id);
																							//								st.setString(2,password);
													System.out.println("Yes-93***********");
		
													ResultSet rs       =st.executeQuery();
													System.out.println(rs);			
											
													if(!rs.next())
													{	
															System.out.println("Yes-100***********");
															String myMsg="Sorry, Wrong login_id or Password ,Try again";
															String nextURL="/V_checkLogin.html";
															param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
													}
													else
													{
													System.out.println("Yes-107***********");
													HttpSession Session=param.req.getSession(true);

//															if(Session.isNew())
//															{
																System.out.println("is new now");
																Session.putValue("login_id",(String)hashTable.get("login_id"));
																Session.putValue("password",(String)hashTable.get("password"));
																Session.putValue("hashTable",hashTable);
//															}
																												System.out.println("BEFORE CALL");
																param.res.sendRedirect("http://localhost:8080/servlet/V_Display__Reservation_Details");
																													System.out.println("AFTER CALL");
													}//esle
											}//else
								}catch(SQLException e){					
								
																					/*				param.res.setContentType("text/html");
																																							e.printStackTrace(param.pw);
								System.out.println("in catch");*/		String myMsg="Sorry,Wrong login_id or Password ,Try again";
																										String nextURL="/inside_holidaysairways.html";
																										param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																		  }

		}//Display_Location()

}//class Screen1

