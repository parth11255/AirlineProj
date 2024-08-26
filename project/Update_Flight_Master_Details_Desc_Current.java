
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
	import java.util.Calendar;
//	import java.text.DateFormat;
//	import java.text.ParseException;
    
public class Update_Flight_Master_Details_Desc_Current extends HttpServlet
{
			public void init(ServletConfig Config) throws ServletException                        
			{
					super.init(Config);											//register the init parameters
			}
    		 public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
			  {
						System.out.println("Line=>20=Update_Flight_Master_Details_Desc_Current");
						//var declaration
						HttpSession Session;
						Connection con;

						c_Parameters	param=new c_Parameters();
						con=param.m_getConnection();								//get Connection from c_Prameters class
						param.con=con;
						param.req=req;
						param.res=res;
						
						//get parameter from URL
						param.m_writeGetParameters(param);
						Hashtable hashTable=param.hashTable;
						PrintWriter pw=new PrintWriter(res.getWriter());
						param.pw=pw;
				try{		
						
						//Session tracking functio from c_Parameter
						 Session=req.getSession(false);
						 param.Session=Session;
						
						 //function from c_Parameter
						 param.check_Session_isValid_and_Copy_Session_hashTable(param );

						con.setAutoCommit(false);
						
						// if  call is for Delete Selected Flight for delete all entry ,url have paramter   "Delete_SelectedFlight=Yes;"	
						System.out.println((String)hashTable.get("Delete_SelectedFlight"));
						if((String)hashTable.get("Delete_SelectedFlight")!=null && ((String)hashTable.get("Delete_SelectedFlight")).equalsIgnoreCase("Yes"))
						{
											String Delete_Flight_No	=(String)hashTable.get("Flight_No");
											System.out.println("Delete_SeletedFlightNumber=>"+Delete_Flight_No);
											if(Delete_Flight_No!=null)
											{
														if(Delete_Flight_Master_Details_desc_Current_TableEntry(param,Delete_Flight_No))
														{
																con.commit();
																Session.invalidate();
																res.sendRedirect("/inside_holidaysairways.html");
																System.out.println("End here =>Update_F_M_D_C_table");					 
														}

														return;
											}//if(Delete_Flight_No!=null)
							}//if((String)Session_hashTable.get("Delete")!=null)
							
							

										
						// if  call is for Update Modified Fields do first delete all then make new entry ,url have paramter Dlete=Yes;	
						//System.out.println((String)hashTable.get("Delete"));
						if((String)hashTable.get("Delete")!=null)
						{
											String Delete_Flight_No	=(String)hashTable.get("Flight_No");
											System.out.println(Delete_Flight_No);
											if(Delete_Flight_No!=null)
											{
														Delete_Flight_Master_Details_desc_Current_TableEntry(param,Delete_Flight_No);

											}//if(Delete_Flight_No!=null)
							}//if((String)Session_hashTable.get("Delete")!=null)
												
						String sql;
				 		if((String)hashTable.get("Delete")==null)
						{//it  only need for Flight_Arrange URL because their we used separate vari.
								hashTable.put("Date_of_Departure",param.req.getParameter("Day")+"/"+param.req.getParameter("Month")+"/"+param.req.getParameter("Year")); 
								hashTable.put("Time_of_flight",(String)hashTable.get("Hour")+":"+(String)hashTable.get("Minute")+":"+(String)hashTable.get("Second")+" "+(String)hashTable.get("AMPM"));
						}
			//System.out.println("From=>Update_F_M_D_C_table");
						System.out.println("before Update_Flight_Master_Table");
						if(Update_Flight_Master_Table(param))
						{
System.out.println("Line=>92");
								if(	Update_Flight_Desc_Table(param))
								{
System.out.println("Line=>95");
									if(	Update_Flight_Details_Table(param))
									{
System.out.println("Line=>98");
											if(Update_Current_Details_Table(param))
											{
System.out.println("Line=>101");
											}else con.rollback();
						
									}else con.rollback();
							
								}else con.rollback();

						}else con.rollback();

					//Delete_con.commit();
						con.commit();
						Session.invalidate();
						con.close();
						String myMsg="Flight arranged successfully,You can check out by using Reports";
						String nextURL="/inside_holidaysairways.html";
						res.sendRedirect("http://localhost:8080/servlet/Error_page?msgflag=Message&msg="+myMsg+"&nextURL="+nextURL);

						System.out.println("End here 118 =>Update_F_M_D_C_table");					 
						  
		} catch(SQLException e){  }
		  catch(NumberFormatException e){String myMsg="Sorry,Your are Enter charactors insted of numbers for capacity Fields so login again & Try again";
																										String nextURL="/Admin_login.html";
																										param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																										return;
																 }
return;
 }//doPost

    public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doPost(req,res);

    }//doGet()

			public boolean Update_Flight_Master_Table(c_Parameters param)
			{
														
														ResultSet rs;
														Connection con=param.con;
														String sql;
														Hashtable hashTable=param.hashTable;
								try{																
			
														sql="INSERT INTO Flight_Master VALUES (?,?,?,?,?,?)";
														PreparedStatement Flight_Master_st=con.prepareStatement(sql);
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
											
											Flight_Master_st.setString(1,(String)hashTable.get("Flight_No"));
											Flight_Master_st.setString(2,(String)hashTable.get("Flight_Name"));
											Flight_Master_st.setString(3,(String)hashTable.get("Source"));
											Flight_Master_st.setString(4,(String)hashTable.get("Destination"));
											Flight_Master_st.setString(5,(String)hashTable.get("Total_capacity"));
											Flight_Master_st.setString(6,(String)hashTable.get("Duration_of_flight"));
			
											//Integer.parseInt(Total_capacity)+","+ Integer.parseInt(Duration_of_flight)";
			
											int Count=Flight_Master_st.executeUpdate();

											/*pw.print("yes "+count+" records updated");*/
						}catch(SQLException  e){ return false;}
return true;

			}//	public boolean Update_Flight_Master_Table(c_Parameters param)
			public boolean Update_Flight_Desc_Table(c_Parameters param)
			{
														
														PreparedStatement st;
														Connection con=param.con;
														String sql;
														Hashtable hashTable=param.hashTable;
																	System.out.println("try");					
								try{																
														sql="select C_Name from Category";
														PreparedStatement Category_st=con.prepareStatement(sql);
														ResultSet Category_rs=Category_st.executeQuery();
    													String Category;
													//	System.out.println("in try");		
									
															while(Category_rs.next())
															{	
																		Category=Category_rs.getString(1);
																		//System.out.println(Category);

																										sql="INSERT INTO Flight_Desc VALUES (?,?,?,?,?)";
																										st=con.prepareStatement(sql);
																									
																										//System.out.println("****LIST******");
																										//System.out.println((String)hashTable.get("Flight_No"));
																										//System.out.println((String)hashTable.get(Category));
																										//System.out.println((String)hashTable.get(Category+"_Cost_of_ticket"));
																										//System.out.println((String)hashTable.get(Category+"_Starting_SeatNo"));
																										//System.out.println((String)hashTable.get(Category+"_Ending_SeatNo"));
																										//System.out.println((String)hashTable.get("Duration_of_flight"));
																										//System.out.println("END LIST");
																										

																									st.setString(1,(String)hashTable.get("Flight_No"));
																									st.setString(2,(String)hashTable.get(Category));
																									st.setString(3,(String)hashTable.get(Category+"_Cost_of_ticket"));
																									st.setString(4,(String)hashTable.get(Category+"_Starting_SeatNo"));
																									st.setString(5,(String)hashTable.get(Category+"_Ending_SeatNo"));
																										
																									int Count=st.executeUpdate();
																									st.close();
																					System.out.println(Category);

															}//while
									}catch(SQLException  e){ return false;}

return true;
			}//public boolean Update_Flight_Desc_Table(param);

			public boolean Update_Flight_Details_Table(c_Parameters param) throws IOException
			{
														
														ResultSet rs;
														Connection con=param.con;
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
				 		if((String)hashTable.get("Delete")==null)
						{
											 Calendar cal= Calendar.getInstance();
											 cal.set(Integer.parseInt(param.req.getParameter("Year")),Integer.parseInt(param.req.getParameter("Month"))-1,Integer.parseInt(param.req.getParameter("Day")));
											 int Day_of_flight=cal.get(Calendar.DAY_OF_WEEK );
									
											  System.out.println("Line=>238="+Day_of_flight);
											  String charDay=null;
												switch(Day_of_flight)
												{
													case 0:System.out.println("Sun");charDay=("Sunday");break;
													case 1:System.out.println("Mon");charDay=	("Monday");break;
													case 2:System.out.println("Tue"); charDay= ("Tuesday");break;
													case 3:System.out.println("Wed"); charDay=("Wednesday");break;
													case 4:System.out.println("Thu");  charDay=("Thusday");break;
													case 5:System.out.println("Fri");    charDay=("Friday");break;
													case 6:System.out.println("Sat");   charDay=("Saturday");break;
												
												}
			
											  System.out.println("Line=>238="+charDay);
	
								 	      //((String)hashTable.get("Time_of_flight"));

											st.setString(1,(String)hashTable.get("Flight_No"));//[Flight_No],
											st.setString(2,charDay);// [Day_of_flight], 
											//st.setString(2,(String)hashTable.get("Day_of_flight"));
											st.setString(3,(String)hashTable.get("Time_of_flight"));//Time_of_flight FROM Flight_Details
											st.setString(4,(String)hashTable.get("Date_of_Departure"));//[Date_of_Departure]
								}else {
											System.out.println((String)hashTable.get("Flight_Date"));
											System.out.println((String)hashTable.get("Flight_Time"));

												st.setString(1,(String)hashTable.get("Flight_No"));//[Flight_No],
												st.setString(2, (String)hashTable.get("Day_of_flight"));
												st.setString(3,(String)hashTable.get("Flight_Time"));//Time_of_flight FROM Flight_Details
												st.setString(4,(String)hashTable.get("Flight_Date"));//[Date_of_Departure]
										 }

								//System.out.println("******after SetString373**********");			
											int count=st.executeUpdate();
								System.out.println("******after exeUpdate375**********");      

								}catch(Exception  e){ 		System.out.println("In Date catch");
																		String myMsg="Date Format is Illegal,please correct it";
																		String nextURL="/Admin_login.html";
																		param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msgflag=Message&msg="+myMsg+"&nextURL="+nextURL);
																		return false;
																		}
					return true;
					
			}//	public boolean Update_Flight_Details_Table(param)

			public boolean Update_Current_Details_Table(c_Parameters param) throws IOException
			{
														

														Connection con=param.con;
														String sql;
														Hashtable hashTable=param.hashTable;
														
					try{
														sql="select C_Name from Category";
														PreparedStatement Category_st=con.prepareStatement(sql);
														ResultSet Category_rs=Category_st.executeQuery();
    													String Category;
													//	System.out.println("in try");		
									
											while(Category_rs.next())
											{	
																	Category=Category_rs.getString(1);
																//	System.out.println(Category);

														sql="INSERT INTO Current_Details  VALUES (?,?,?)";
														PreparedStatement st=con.prepareStatement(sql);

														//System.out.println("******afetr sql**********");
														//System.out.println((String)hashTable.get("Flight_No"));
														//System.out.println((String)hashTable.get(Category));
														//System.out.println((String)hashTable.get("Total_capacity"));
														
														//System.out.println((String)hashTable.get("Date_of_Departure"));

														st.setString(1,(String)hashTable.get("Flight_No"));
														st.setString(2,(String)hashTable.get(Category));
													//calculte Capacity of each category
														int Start_Capacity=Integer.parseInt( (String)hashTable.get(Category+"_Starting_SeatNo"));
														int End_Capacity =Integer.parseInt( (String)hashTable.get(Category+"_Ending_SeatNo"));
														int Capacity	=	(End_Capacity-Start_Capacity )+1;
														
														st.setString(3,Integer.toString(Capacity));
														//st.setString(4,(String)hashTable.get("Date_of_Departure"));
														//System.out.println("******after SetString**********");			
														int count=st.executeUpdate();
												}
							}catch(SQLException  e){ return false;}
							catch(NumberFormatException  e){					String myMsg="Sorry,You have  Entered characters insted of numbers for capacity Fields so login again & Try again";
																										String nextURL="/Admin_login.html";
																										param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																										return false;				}
							catch(Exception  e){return false;}
return true;
			}//public boolean Update_Current_Details_Table(c_Parameters param)
			public boolean Delete_Flight_Master_Details_desc_Current_TableEntry(c_Parameters param,String Delete_Flight_No)
			{
														ResultSet rs;
														Connection con=param.con;
														//Here user separate connection call Delate_Con it set as Autocommit=false  //globe variable 
														String sql;
														PreparedStatement  st;
														Hashtable hashTable=param.hashTable;
														int Count;
													try{																
								
																	sql="Delete From Flight_Master where Flight_No='"+Delete_Flight_No+"'";
																	st=con .prepareStatement(sql);
																	Count=st.executeUpdate();
//																	System.out.println("Yes line=439");
																	st.close();

																	sql="Delete From Flight_Details where Flight_No='"+Delete_Flight_No+"'";
																	st=con.prepareStatement(sql);
																	Count=st.executeUpdate();
//																	System.out.println("Yes line=445");																	
																	st.close();
																	
																	sql="Delete From Flight_Desc where Flight_No='"+Delete_Flight_No+"'";
																	st=con.prepareStatement(sql);
																	Count=st.executeUpdate();
//																	System.out.println("Yes line=451");																																	
																	st.close();

																	sql="Delete From Current_Details where Flight_No='"+Delete_Flight_No+"'";
																	st=con.prepareStatement(sql);
																	Count=st.executeUpdate();
//																	System.out.println("Yes line=457");																																	
																	st.close();
			
											}catch(SQLException  e){ return false;}
								return true;
								}//Delete_Flight_Master_Details_desc_Current_TableEntry
}//class Display_FlightNumber_List

