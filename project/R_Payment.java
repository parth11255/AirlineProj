/*call from:=R_login.html		 =>finction go_Nexr(form1)
check whether login & password is correct for old passenger if yes then Display_all_paassenger_detail hatl page otherwise error error page with nexrRUI=R_login.html
Note=>Please don't fix more than one token in one line in R_Adult_passenger_Deatils.html*/
/* alppied setAutocommit(false) on line 107 and con.commit() on line 319*/

	import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
    
public class R_Payment extends HttpServlet
{

	public void init(ServletConfig Config) throws ServletException                        
	{
			super.init(Config);

	//param.hashTable.clear();//make empty hashTable
			
	}

     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {

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
//    					System.out.println("u rein:=>R_Display_total_Number_of_Passenger_ScreensFrames");
						PrintWriter pw=new PrintWriter(res.getWriter());
						param.pw=pw;
		try{
						

						//Session tracking functio from c_Parameter                                                                                                                                                             
						 Session=req.getSession(false);                                                                                                                                                                                
						if(Session==null)
						 {
//														System.out.println("Yes-100***********");
														String myMsg="Sorry,Wrong login_id or may Password ,Try again";
														String nextURL="/R_Search_Flight_for_Booking.shtml";
														param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
									
						 }
						 param.Session=Session;
						 //function from copy Session_hashTable to hashtable 
						 //param.check_Session_isValid_For_User_and_Copy_Session_hashTable(param );
						 	try{
																	//copy all variable of session in param
																	Object ObjecthashTable =Session.getValue("hashTable");
																	if(ObjecthashTable !=null)
																	{
																				Hashtable Session_hashTable=(Hashtable) ObjecthashTable;
																				hashTable=param.copy_hashTable_To_hashTable(Session_hashTable,hashTable );

																	}else {System.out.println("C_parameters:=>Session_hashTable is null"); }
									}catch(Exception e){/*	param.res.setContentType("text/html");
																		e.printStackTrace(param.pw);
																		System.out.println("in catch");		*/
																		try{if(param.con!=null){param.con.rollback(); param.con.close();}}catch(Exception c){System.out.println("Connection problem");}		
																		System.out.println("Line=>86=R_Payment");
																		String myMsg="Sorry,Wrong login_id or may Password ,Try again";
																		String nextURL="/frontpage.html";
																		res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																		return;
																	} //catch
/*check whether user is old or new*/
{
							String login_id=		(String)hashTable.get("login_id");              
							String password=	(String)hashTable.get("password");           
							String newuser=		(String)hashTable.get("newuser");             
	                                                                                                                            
			System.out.println("login_id="+ (String)hashTable.get("login_id"));          
			System.out.println("password="+(String)hashTable.get("password"));    
			System.out.println("newuser="+(String)hashTable.get("newuser"));       	

			if(newuser==null)
			{
					hashTable.put("User_id",(Object)login_id);
					hashTable.put("password",(Object)password);
			}
                                                                                                                           
}//end of checking new or old user

/*		 Here Enter Master & Passenger Details in Passenger_Master & Passenger_Details Table with validation if  invalid then redirect that page		
			Find Master passenger is old or new
			take user-id from hashtable and place in passenger_detail
			then place other passenger detail if exist
		
*/
															int Res_No=1;
															try//Find next Res_No from Database
															{
																
																			//take last(Current for this passenger) Res_No.
																		
																			String sql="SELECT max(Res_No)"
																					   +" FROM Passenger_Master ";
																			PreparedStatement st=con.prepareStatement(sql);
																			ResultSet rs       =st.executeQuery();
																			
																			if(rs.next())
																			{
																				Res_No=Integer.parseInt(rs.getString(1))+1;
																				System.out.println(Res_No);
																			} else Res_No=1;
															con.close();
															}catch(Exception e){try{if(param.con!=null){param.con.rollback(); param.con.close();}}catch(Exception ec){System.out.println("Connection problem");}		e.getMessage();}
		hashTable.put("Res_No",new Integer(Res_No).toString());
		try{
					//Privious con is now closed at 113 line
							con=param.m_getConnection();
							param.con=con;
							con.setAutoCommit(false);									
							String sql="INSERT INTO Passenger_Master VALUES (?,date(),?,?,?)";
							PreparedStatement st       =con.prepareStatement(sql);
//			 for tracing
//				System.out.println("***********from108************");	
//				System.out.println((String)hashTable.get("Res_No"));
//				System.out.println(	(String)hashTable.get("SelectedFlight"));
//				System.out.println(	(String)hashTable.get("User_id"));

		

							st.setString(1,(String)hashTable.get("Res_No"));
							//2nd parameter palced directly in sql string
							st.setString(2,(String)hashTable.get("SelectedFlight"));
							st.setString(3,(String)hashTable.get("LCategory"));
							st.setString(4,(String)hashTable.get("User_id"));

							int count=st.executeUpdate();		
							st.close();
																	//System.out.println("after sql");	
//Enter record in passenger_Details table
				{
						

				  		    //Total Adult Screens
						   System.out.println("R_Display_total..=>Adult="+(String)hashTable.get("LAdult"));
						   System.out.println("R_Display_total..=>Child="+(String)hashTable.get("LAdult"));
    					   int  TotalAdult=Integer.parseInt( ((String)hashTable.get("LAdult")) );
						   int  TotalChild=Integer.parseInt( ((String)hashTable.get("LChild")) );
						  //For:= Enter Adult Record in Passenger_Details
						  int Pass_no=1;
						  for(int i=1;i<=TotalAdult;i++)
						  {	
									sql="INSERT INTO Passenger_Details VALUES (?,?,?,?,?,false,0)";

									st      =con.prepareStatement(sql);

									// for tracing
									System.out.println("***********from141************");	
									System.out.println("GivenName_Adult_passenger	:"+(String)hashTable.get	("GivenName_Adult_passenger"+i));        
									System.out.println("FamilyName_Adult_passenger	:"+	(String)hashTable.get("FamilyName_Adult_passenger"+i));    
									System.out.println("age	:"+	(String)hashTable.get("age"));            								
									System.out.println("Res_No	:"+(String)hashTable.get("Res_No"));                

									st.setString(1,new Integer(Pass_no++).toString());
									st.setString(2,(String)hashTable.get("Title_Adult_passenger"+i)+" "+(String)hashTable.get("GivenName_Adult_passenger"+i));
									st.setString(3,(String)hashTable.get("FamilyName_Adult_passenger"+i));
									st.setString(4,"0");
									st.setString(5,(String)hashTable.get("Res_No"));

													
									System.out.println(" sql=>"+sql);	
									count=st.executeUpdate();		
									System.out.println("after sql");	
									st.close();

							}//for
						  //For:= Enter Child Record in Passenger_Details
						  for(int i=1;i<=TotalChild;i++)
						  {	
									sql="INSERT INTO Passenger_Details VALUES (?,?,?,?,?,false,0)";

									st      =con.prepareStatement(sql);

									// for tracing
									System.out.println("***********from141************");	
								
									st.setString(1,new Integer(Pass_no++).toString());
									st.setString(2,(String)hashTable.get("Title_Child_passenger"+i)+" "+(String)hashTable.get("GivenName_Child_passenger"+i));
									System.out.println((String)hashTable.get	("GivenName_Child_passenger"+i));        
									st.setString(3,(String)hashTable.get("FamilyName_Child_passenger"+i));
									System.out.println(	(String)hashTable.get("FamilyName_Child_passenger"+i));    
									st.setString(4,(String)hashTable.get("age"+i));


									st.setString(5,(String)hashTable.get("Res_No"));
									System.out.println(	(String)hashTable.get("Res_No"));                
													
									System.out.println(" sql=>"+sql);	
									count=st.executeUpdate();		
									System.out.println("after sql");	
									st.close();

							}//for


///		  do=>1. if seat numbers are avilable then (use Current_Deatils)
//				  2. set confirm=true and  assign new seatnumber=(lastseatnumber_flight_no)+1
//				  3. else update waiting list
				  
				  {//check for seat avilable
									sql="select Available_Seats From Current_Details where Flight_No=? and Category=?";
									st      =con.prepareStatement(sql);
									// for tracing
									System.out.println("***********from166************");	
									System.out.println((String)hashTable.get	("SelectedFlight"));        
									System.out.println(	(String)hashTable.get("LCategory"));    
								
									st.setString(1,(String)hashTable.get("SelectedFlight"));
									st.setString(2,(String)hashTable.get("LCategory"));
									ResultSet rs=st.executeQuery();		
				
									if(rs.next())
									{//Caculate number of avilable seats for that category of flight
									
											int Available_Seats=rs.getInt("Available_Seats");

											if(Available_Seats>=(TotalAdult+TotalChild))
											{//Yes avilable then set confimed=true and assign seatnumber and decrement avilables_seats(Current_Details)
																	
																			//select next seat_no 	from (Flight_Desc)
																			sql="Select Starting_SeatNo,Ending_SeatNo from Flight_Desc	where Flight_No=? and Category=?";
																			st      =con.prepareStatement(sql);
																			// for tracing
																			System.out.println("***********from186************");	
																			st.setString(1,(String)hashTable.get("SelectedFlight"));
																			st.setString(2,(String)hashTable.get("LCategory"));
																			System.out.println((String)hashTable.get	("SelectedFlight"));        
																			System.out.println(	(String)hashTable.get("LCategory"));    

																			rs=st.executeQuery();		
																			if(rs.next())
																			{
																						  //calculation of next seat number				
																						  int NextSeat_no=((rs.getInt("Ending_SeatNo")-rs.getInt("Starting_SeatNo")+1)-Available_Seats)+1;
  																						  System.out.println("NextSeatNumber"+NextSeat_no);    

																						  for(int i=1;i<=(TotalAdult+TotalChild);i++)
																						  {//Effect ocuure here ,set confimed=true and assign seatnumber(key use Pass_no+Res_No)
													
																									sql="Update  Passenger_Details set Confirmed=true,Seat_no= "+NextSeat_no+"  where Res_No=?  and Pass_No="+i;
																									st      =con.prepareStatement(sql);
																									st.setString(1,(String)hashTable.get("Res_No"));
																									System.out.println(	(String)hashTable.get("Res_No"));                
																									NextSeat_no=NextSeat_no+1;//ment for next seatnumber assing next passenger
																										
																									count=st.executeUpdate();		
																									st.close();
																									hashTable.put("Confirmation",(Object)"true");
																							}//for

																						//decrement avilables_seats(Current_Details)
																						{
																									sql="Update  Current_Details  set Available_Seats=Available_Seats-"+(TotalAdult+TotalChild)+" where Flight_No=? and Category=?";
																									st      =con.prepareStatement(sql);
																									// for tracing
																									System.out.println("***********from217************");	
																									System.out.println((String)hashTable.get	("SelectedFlight"));        
																									System.out.println(	(String)hashTable.get("LCategory"));    
																								
																									st.setString(1,(String)hashTable.get("SelectedFlight"));
																									st.setString(2,(String)hashTable.get("LCategory"));
																									count =st.executeUpdate();		
																						}//end decrement avilables_seats(Current_Details)
																		
																		}//if(rs.next())

											}//if(Available_Seats>=(TotalAdult+TotalChild))

											else//Enter all passenger in waiting list
											{

																  for(int i=1;i<=TotalAdult+TotalChild;i++)
																  {	
																		sql="INSERT INTO Waiting_list VALUES (?,?,?,?,?,?)";

																		st      =con.prepareStatement(sql);
																		// for tracing
																		System.out.println("***********from239************");	
																

																		st.setString(1,(String)hashTable.get("Res_No"));
																		st.setString(2,new Integer(i).toString());//Pass_No
																		st.setString(3,(String)hashTable.get("SelectedFlight"));
																		//get Date_of_Departurefrom Flight_Details
																		{
																								sql="select Date_of_Departure from Flight_Details where Flight_No=?";
																								PreparedStatement temp_st      =con.prepareStatement(sql);
																								// for tracing
																								System.out.println("***********from247************");	
																								System.out.println((String)hashTable.get("SelectedFlight"));
																								
																								temp_st.setString(1,(String)hashTable.get("SelectedFlight"));
																								ResultSet temp_rs=temp_st.executeQuery();		

																								if(temp_rs.next())
																								{
																										hashTable.put("Date_of_Departure",(Object)temp_rs.getDate("Date_of_Departure"));        
																										System.out.println(" sql=>"+sql);	
																										temp_st.close();
																								}

																		}
																		st.setDate(4,(Date)hashTable.get("Date_of_Departure"));      
																		System.out.println((Date)hashTable.get("Date_of_Departure"));        
																		st.setString(5,(String)hashTable.get("LCategory"));

																		int NextWaiting_no=1;
																							//Fing NextWaiting Number
																							{
//Problem => not working max function in query	
//String sql="SELECT max(Res_No)" +"FROM Passenger_Master ";
//			sql="select max(Waiting_No)"+" from Waiting_list ";
//tracing 
																								sql="select max( Waiting_No ) from Waiting_list where Flight_No=? and Category=? and Date_of_Departure=?";
																								//sql="SELECT max([Waiting_No]) FROM Waiting_list WHERE Flight_No='SQ1200' And [Category]='First'";

																								PreparedStatement temp_st      =con.prepareStatement(sql);
																								// for tracing
																								System.out.println("***********from253************");	
//																								System.out.println((String)hashTable.get("SelectedFlight"));
//																								System.out.println((Date)hashTable.get("Date_of_Departure"));																								
//																								System.out.println((String)hashTable.get	("LCategory"));        
//
																								temp_st.setString(1,(String)hashTable.get("SelectedFlight"));
																								
																								temp_st.setString(2,(String)hashTable.get("LCategory"));

																								temp_st.setDate	 (3,(Date)hashTable.get("Date_of_Departure"));        

																								ResultSet temp_rs=temp_st.executeQuery();		

																								if(temp_rs.next())
																								{
																										NextWaiting_no=temp_rs.getInt(1)+1;
																								}
																										temp_st.close();																								

																							}//End Find NextWaing Number

																		st.setString(6,new Integer(NextWaiting_no).toString());//Waiting_No
														
																		System.out.println(" sql=>"+sql);	
																		count=st.executeUpdate();		
																		st.close();
																	}//for
													
											}//End  Enter all passenger in waiting list
										}//if														   
		

							}//end of check for seat avilable

							}//End block for enter passenger_Deatil
					con.commit();con.close();									
					param.Session.invalidate();

//																			String myMsg="Flight arranged successfully,You can check out by using Reports";
//																			String nextURL="/inside_holidaysairways.html";
//																			res.sendRedirect("http://localhost:8080/servlet/Error_page?msgflag=Message&msg="+myMsg+"&nextURL="+nextURL);
							
							{
									  //Read file
									   BufferedReader in =new BufferedReader(new FileReader("c:/JavaWebServer2.0/public_html/TicketReceive.html"));
										for(;;)
											{
												String line =in.readLine();
												String new_string=null;
												int start_index;
												if(line==null)
													break;
												start_index=line.indexOf("#Place");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat((String)hashTable.get("LSource"));
															String temp=line.substring(start_index+6);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
															System.out.println(line);
												}
												start_index=line.indexOf("#YesNo");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															if((String)hashTable.get("Confirmation")!=null)
															{
																new_string=new_string.concat(" ");
															}
															else
															{
																	new_string=new_string.concat("not ");
															}
															String temp=line.substring(start_index+6);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
															System.out.println(line);
												}
															pw.println(line);
											}//for

											in.close();

							}//Adult for loop
					
				}
						catch(SQLException e){ 	try{if(param.con!=null){param.con.rollback(); param.con.close();}}catch(Exception ec){try{if(param.con!=null){param.con.rollback(); param.con.close();}}catch(Exception ec1){System.out.println("Connection problem");}		System.out.println("Connection problem");}		
																		
																		param.res.setContentType("text/html");
																		e.printStackTrace(param.pw);
        																System.out.println("in catch");
																		/*String myMsg="Error:R_Newuser()=>please ,Fill  all values properly";
																					String nextURL="/R_person_detail_for_registration.html";
																					res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);*/
																					return;
																	}

       }//catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
//        catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
          catch(NumberFormatException e){ try{if(param.con!=null){param.con.rollback(); param.con.close();}}catch(Exception ec){System.out.println("Connection problem");}		
		  pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}

  		  finally
		  {

			if(param.rs!=null)
			{
				try{
					param.rs.close();
					}catch(SQLException ignore){}
			}//if
			
			if(param.con!=null)
			{
				try{
					param.con.close();
					}catch(SQLException ignore){}
			}//if
	
		  }//finally
  
	 }//doGet()

    public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doGet(req,res);

    }//doPost()

	public boolean check_for_right_Passenger(c_Parameters param,String login_id,String password)throws ServletException,IOException
	{
				PrintWriter pw=param.pw;
				Hashtable hashTable=param.hashTable;
				
				try{		
							
//								System.out.println( password.length() );System.out.print(login_id.length());

							
								if(login_id==null || password==null || login_id.length()==0 || password.length()==0)
								{
													return false;																						                                                                                                                                                                          
								}else{
												

													String sql="select login_id,password from  Login where login_id='"+login_id+"'  and password='"+password+ "'	";
													PreparedStatement st=param.con.prepareStatement(sql);
																							//								st.setString(1,login_id);
																							//								st.setString(2,password);
//													System.out.println("Yes-93***********");

													ResultSet rs       =st.executeQuery();
//													System.out.println(rs);			
											
													if(!rs.next())
													{	
															return false;
													}
													else
													{
														  //System.out.println("R_Display_total..=>after login id");
    													   return true;

													}
																
														//	param.res.sendRedirect("http://localhost:8080/servlets/R_Display_total_Number_of_Passenger_ScreensFrames");
								
											}//else
								}catch(SQLException e){				try{if(param.con!=null){param.con.rollback(); param.con.close();}}catch(Exception ec){System.out.println("Connection problem");}			
								
																					/*				param.res.setContentType("text/html");
																																							e.printStackTrace(param.pw);
								System.out.println("in catch");*/		String myMsg="Sorry,Wrong login_id or may Password ,Try again";
																										String nextURL="/inside_holidaysairways.html";
																										param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																		  }
																		  return true;

		}//Display_Location()

}//class Screen1

