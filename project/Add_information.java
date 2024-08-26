
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
	import java.util.Calendar;
	import java.text.DateFormat;
	import java.text.ParseException;
    
public class Add_information extends HttpServlet
{


	private	Connection con;
	HttpSession Session;
	public void init(ServletConfig Config) throws ServletException                        
	{
			super.init(Config);														//register the init parameters
			c_Parameters	param=new c_Parameters();
			con=param.m_getConnection();								//get Connection from c_Prameters class
	}
  public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
   {
			c_Parameters	param=new c_Parameters();
			param.req=req;
			param.res=res;
			PrintWriter pw=new PrintWriter(res.getWriter());
			param.pw=pw;
			param.con=con;
	
			 Hashtable Session_hashTable=null;				
			 Session=req.getSession(false);

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
																					
		    							}catch(Exception e){/*	param.res.setContentType("text/html");
																				e.printStackTrace(param.pw);
																				System.out.println("in catch");		*/
																				String myMsg="Sorry,Wrong login_id or may Password ,Try again";
																				String nextURL="/Admin_login.html";
																				res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																		  }	 //catch
									
			   try{
								con.setAutoCommit(false);
								String sql;
								param.m_writeGetParameters(param);
								Hashtable hashTable=param.hashTable;
								//hashTable=param.copy_hashTable_To_hashTable(Session_hashTable,hashTable );

	 

	System.out.println(HttpUtils.getRequestURL(req));
			Update_Category_Table(param);
		
	con.commit();
	Session.invalidate();
   res.sendRedirect("/inside_holidaysairways.html");

/*          
  //it return the value attribute of select cnt. which is our FlightName
  String Flight_No       =req.getParameter("Flight_No");
  String Date_of_Departure=req.getParameter("Date_of_Departure");
  String Day_of_flight   =req.getParameter("Day_of_flight");
  String Hour            =req.getParameter("Hour");
  String Minute          =req.getParameter("Minute");
  String Second          =req.getParameter("Second");
  String AMPM            =req.getParameter("AMPM");
*/


         }//catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
         catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
          catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}

     }//doPost

    public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doPost(req,res);

    }//doGet()


/*******************HTML FUNCTIONS**********************/
/*********************no HTML FUNCTION ALL INVOKE FROM SCREEN3SEVLET()*/

			public void Update_Category_Table(c_Parameters param)
			{
														
														ResultSet rs;
														//Connection con=param.con;
														String sql;
														Hashtable hashTable=param.hashTable;
								try{																
			
														sql="INSERT INTO Category VALUES (?,?)";
														PreparedStatement Flight_Master_st=con.prepareStatement(sql);
											
											System.out.println("****LIST******");
											System.out.println((String)hashTable.get("C_no"));                
											System.out.println((String)hashTable.get("C_name"));          
											System.out.println((String)hashTable.get("T_cost_adult"));  
											System.out.println((String)hashTable.get("T_cost_child"));   
											
											System.out.println("END LIST");
											
											Flight_Master_st.setString(1,(String)hashTable.get("C_no"));
											Flight_Master_st.setString(2,(String)hashTable.get("C_name"));
											
			
											//Integer.parseInt(Total_capacity)+","+ Integer.parseInt(Duration_of_flight)";
			
											int Count=Flight_Master_st.executeUpdate();
											System.out.println(Count);
											/*pw.print("yes "+count+" records updated");*/
						}catch(SQLException  e){ param.res.setContentType("text/html");
																											e.printStackTrace(param.pw);}

			}//	public void Update_Flight_Master_Table(c_Parameters param)

			public void Update_Flight_Desc_Table(c_Parameters param)
			{
														
														PreparedStatement st;
														//Connection con=param.con;
														String sql;
														Hashtable hashTable=param.hashTable;
																	System.out.println("try");					
								try{																
														sql="select C_Name from Category";
														PreparedStatement Category_st=con.prepareStatement(sql);
														ResultSet Category_rs=Category_st.executeQuery();
    													String Category;
														System.out.println("in try");		
									
															while(Category_rs.next())
															{	
																		Category=Category_rs.getString(1);
																		System.out.println(Category);

																										sql="INSERT INTO Flight_Desc VALUES (?,?,?,?,?)";
																										st=con.prepareStatement(sql);
																									
																										System.out.println("****LIST******");
																										System.out.println((String)hashTable.get("Flight_No"));
																										System.out.println((String)hashTable.get(Category));
																										System.out.println((String)hashTable.get(Category+"_Cost_of_ticket"));
																										System.out.println((String)hashTable.get(Category+"_Starting_SeatNo"));
																										System.out.println((String)hashTable.get(Category+"_Ending_SeatNo"));
																										//System.out.println((String)hashTable.get("Duration_of_flight"));
																										System.out.println("END LIST");
																										

																									st.setString(1,(String)hashTable.get("Flight_No"));
																									st.setString(2,(String)hashTable.get(Category));
																									st.setString(3,(String)hashTable.get(Category+"_Cost_of_ticket"));
																									st.setString(4,(String)hashTable.get(Category+"_Starting_SeatNo"));
																									st.setString(5,(String)hashTable.get(Category+"_Ending_SeatNo"));
																										
																									int Count=st.executeUpdate();
																									st.close();
																					System.out.println(Category);

															}//while
									}catch(SQLException  e){ param.res.setContentType("text/html");
																											e.printStackTrace(param.pw);}


			}//public void Update_Flight_Desc_Table(param);
			public void Update_Flight_Details_Table(c_Parameters param)
			{
														
														ResultSet rs;
														//Connection con=param.con;
														String sql;
														Hashtable hashTable=param.hashTable;

							try{		
											System.out.println("******before sql**********");

											sql="INSERT INTO Flight_Details VALUES (?,?,?,?)";					
											PreparedStatement st       =con.prepareStatement(sql);
											System.out.println("******afetr sql**********");
											System.out.println((String)hashTable.get("Flight_No"));
											System.out.println((String)hashTable.get("Day_of_flight"));
											System.out.println((String)hashTable.get("Time_of_flight"));
											System.out.println((String)hashTable.get("Date_of_Departure"));
								/*
											java.util.Date dt=null;
									try{
											 DateFormat dtf=DateFormat.getDateInstance();
											dt=dtf.parse(((String)hashTable.get("")).substring(0,8));	//error format of Date_of_Departure is geting proper

										}catch(ParseException e){System.out.println(e.getMessage());}
											 Calendar cal= Calendar.getInstance();
											 cal.setTime(dt);
 											int Day_of_flight=cal.DAY_OF_WEEK ;
								*/

											//((String)hashTable.get("Time_of_flight"));

											st.setString(1,(String)hashTable.get("Flight_No"));
											st.setString(2,"Monday");
											//st.setString(2,(String)hashTable.get("Day_of_flight"));
											st.setString(3,(String)hashTable.get("Time_of_flight"));
											st.setString(4,(String)hashTable.get("Date_of_Departure"));

								System.out.println("******after SetString**********");			
											int count=st.executeUpdate();
								System.out.println("******after exeUpdate**********");      

								}catch(SQLException  e){ param.res.setContentType("text/html");
																		e.printStackTrace(param.pw);}

					
			}//	public void Update_Flight_Details_Table(param)

			public void Update_Current_Details_Table(c_Parameters param) throws IOException
			{
														

														//Connection con=param.con;
														String sql;
														Hashtable hashTable=param.hashTable;
														
					try{
														sql="select C_Name from Category";
														PreparedStatement Category_st=con.prepareStatement(sql);
														ResultSet Category_rs=Category_st.executeQuery();
    													String Category;
														System.out.println("in try");		
									
											while(Category_rs.next())
											{	
																	Category=Category_rs.getString(1);
																	System.out.println(Category);

														sql="INSERT INTO Current_Details  VALUES (?,?,?)";
														PreparedStatement st=con.prepareStatement(sql);

														System.out.println("******afetr sql**********");
														System.out.println((String)hashTable.get("Flight_No"));
														System.out.println((String)hashTable.get(Category));
														System.out.println((String)hashTable.get("Total_capacity"));
														
														//System.out.println((String)hashTable.get("Date_of_Departure"));

														st.setString(1,(String)hashTable.get("Flight_No"));
														st.setString(2,(String)hashTable.get(Category));
													//calculte Capacity of each category
														int Start_Capacity=Integer.parseInt( (String)hashTable.get(Category+"_Starting_SeatNo"));
														int End_Capacity =Integer.parseInt( (String)hashTable.get(Category+"_Ending_SeatNo"));
														 int Capacity	=	(End_Capacity-Start_Capacity )+1;
														
														st.setString(3,Integer.toString(Capacity));
														//st.setString(4,(String)hashTable.get("Date_of_Departure"));
														System.out.println("******after SetString**********");			
														int count=st.executeUpdate();
												}
							}catch(SQLException  e){ param.res.setContentType("text/html");
														e.printStackTrace(param.pw);}
							catch(NumberFormatException  e){ String myMsg="Sorry,Your are Enter charactors insted of numbers for capacity Fields so login again & Try again";
																										String nextURL="/Admin_login.html";
																										param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																										Session.invalidate();				}
								catch(Exception  e){System.out.println(e.getMessage());}



			}//public void Update_Current_Details_Table(c_Parameters param)
								public void Delete_Flight_Master_Details_desc_Current_TableEntry(c_Parameters param,String Delete_Flight_No)
								{
														ResultSet rs;
														//Here user separate connection call Delate_Con it set as Autocommit=false  //globe variable 
														String sql;
														PreparedStatement  st;
														Hashtable hashTable=param.hashTable;
														int Count;
													try{																
								
																	sql="Delete From Flight_Master where Flight_No='"+Delete_Flight_No+"'";
																	st=con .prepareStatement(sql);
																	Count=st.executeUpdate();
																	System.out.println("Yes line=439");
																	st.close();

																	sql="Delete From Flight_Details where Flight_No='"+Delete_Flight_No+"'";
																	st=con.prepareStatement(sql);
																	Count=st.executeUpdate();
																	System.out.println("Yes line=445");																	
																	st.close();
																	
																	sql="Delete From Flight_Desc where Flight_No='"+Delete_Flight_No+"'";
																	st=con.prepareStatement(sql);
																	Count=st.executeUpdate();
																	System.out.println("Yes line=451");																																	
																	st.close();

																	sql="Delete From Current_Details where Flight_No='"+Delete_Flight_No+"'";
																	st=con.prepareStatement(sql);
																	Count=st.executeUpdate();
																	System.out.println("Yes line=457");																																	
																	st.close();
			
											}catch(SQLException  e){ param.res.setContentType("text/html");
																						e.printStackTrace(param.pw);}

					}//Delete_Flight_Master_Details_desc_Current_TableEntry

}//class Display_FlightNumber_List

