
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
    
public class C_Display__Reservation_Details extends HttpServlet
{

	public void init(ServletConfig Config) throws ServletException                        
	{
			super.init(Config);

	}

     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {
						System.out.println("C_Display__Reservation_Details");
	//					var declaration
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
																				hashTable=param.copy_hashTable_To_hashTable(hashTable,Session_hashTable );

																	}else {System.out.println("C_parameters:=>Session_hashTable is null"); }
									}catch(Exception e){/*	param.res.setContentType("text/html");
																		e.printStackTrace(param.pw);
																		System.out.println("in catch");		*/
																		String myMsg="Error:c_parameter()=>Sorry,Wrong login_id or may Password ,Try again";
																		String nextURL="/frontpage.html";
																		res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																		return;
													} //catch


						//Display Total Adult Screens
						//ServletContext context=getServletContext();
						String login_id=(String)hashTable.get("login_id");
						String password=(String)hashTable.get("password");
						String newuser=(String)hashTable.get("newuser");
		System.out.println("login_id="+ (String)hashTable.get("login_id"));        
		System.out.println("password="+(String)hashTable.get("password"));     	
		System.out.println("newuser="+(String)hashTable.get("newuser"));       	
						  
						  System.out.println("reach55");
					try{
								String sql="SELECT Distinct t4.Res_No,t5.Pass_No,t5.Given_name, t5.Family_name, t4.Flight_No, t1.Source, t1.Destination, t5.Seat_No, t4.Category, t5.Confirmed FROM Flight_Master AS T1, Flight_Details AS T2, Flight_Desc AS T3, Passenger_Master AS T4, Passenger_Details AS T5  WHERE T4.User_id=? And T4.Res_No=T5.Res_No And t4.Flight_No=t1.Flight_No";
								PreparedStatement st=con.prepareStatement(sql);

	//						    System.out.println((String)hashTable.get("LCategory") );       
	//							System.out.println((String)hashTable.get("SelectedFlight") );//((String)hashTable.get("LChild")) ); 
								st.setString(1,(String)hashTable.get("login_id") );
	//							System.out.println(sql);
								ResultSet rs       =st.executeQuery();
/*
								//long Cost_of_ticket;  declare at top
								if( rs.next() )
								{
										Cost_of_ticket=Long.parseLong( (String)rs.getString("Cost_of_ticket") );
//										System.out.println("Cost_of_ticket="+Cost_of_ticket);

								}else Cost_of_ticket=12000;
						}
						 catch(Exception e){}
*/	
//					    System.out.println("R_Display_total..=>"+(String)hashTable.get("LChild"));
  						pw.println("<html>");
						pw.println("<body>");
						pw.println("<form name=C_Display__Reservation_Details  method=POST>");
						pw.println("						<title>Check</title>");
						pw.println("						</head>");

						pw.println("						<body>");
						pw.println("						<input type=button name='Delete' value='Cancelation' onclick=FunCancel(C_Display__Reservation_Details)>");
						pw.println("						<table bgcolor=#FFFFCC cellpadding=5 cellspacing=0 border=1 width=100%>");

						pw.println("						  <tr align=center bgcolor=#FFCC99>");
						pw.println("							<td width=><b>Check</b></td>");
						pw.println("							<td width=> <b> Resvation No</b></td>");
						pw.println("							<td width=> <b>Passenger_Name </b></td>");
						pw.println("							<td width=> <b>Flight Number</b></td>");
						pw.println("							<td width=> <b>Flight_Source</b></td>");
						pw.println("							<td width=> <b>Flight_Destination<b></td>");
						pw.println("							<td width=> <b>Seat Number</b></td>");
						pw.println("							<td width=> <b>Category</b></td>");
						pw.println("							<td width=> <b>Confirmed</b></td>");
						pw.println("						  </tr>");
						int Total_Passenger=0;
						while(rs.next())
							{	
								Total_Passenger++;
																  //Read file
									   BufferedReader in =new BufferedReader(new FileReader("c:/JavaWebServer2.0/public_html/C_Display__Reservation_Details.html"));
										for(;;)
											{
											
												String line =in.readLine();
												String new_string=null;
												int start_index;
												if(line==null)
													break;
												System.out.println("reach133");
												start_index=line.indexOf("#chName1");
												
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat("Pass_No"+rs.getString("Pass_No"));
															String temp=line.substring(start_index+8);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//															System.out.println(line);
												}
												System.out.println("reach149");
												start_index=line.indexOf("#Reservation_No");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat(rs.getString("Res_No"));
															String temp=line.substring(start_index+15);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//															System.out.println(line);
												}
												System.out.println("reach162");
												start_index=line.indexOf("#PassengerName");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);
															new_string=new_string.concat(rs.getString("Given_name")+ " "+rs.getString("Family_name"));
															String temp=line.substring(start_index+14);
															if(temp!=null)
																	new_string=new_string.concat(temp);
															line=new String(new_string);
												}
												start_index=line.indexOf("#FlightNumber");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat(rs.getString("Flight_No"));
															String temp=line.substring(start_index+13);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//													System.out.println(line);
											}
												start_index=line.indexOf("#FlightSource");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat(rs.getString("Source"));
															String temp=line.substring(start_index+13);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//															System.out.println(line);
											}
												start_index=line.indexOf("#FlightDestination");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat(rs.getString("Destination"));
															String temp=line.substring(start_index+18);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//															System.out.println(line);
											}

												start_index=line.indexOf("#SeatNumber");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat(rs.getString("Seat_No"));
															String temp=line.substring(start_index+11);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//															System.out.println(line);
											}
												start_index=line.indexOf("#Category");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat(rs.getString("Category"));
															String temp=line.substring(start_index+9);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//															System.out.println(line);
											}


												start_index=line.indexOf("#Confirmed");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat(rs.getBoolean("Confirmed")?"Yes":"No");
															String temp=line.substring(start_index+11);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//															System.out.println(line);
											}
															
															pw.println(line);
											}
											in.close();

							}//Adult for loop
							hashTable.put("Total_Passenger",new Integer(Total_Passenger).toString());
		
						pw.println("						</table >");
						pw.println("</form>");
						pw.println("</body>");
						pw.println("<script>");
						pw.println("function FunCancel(form1) { form1.action='http://localhost:8080/servlet/C_removeCancelEntries' ; form1.submit();}");
						pw.println("</script>");
						pw.println("</html>");
						Session.putValue("hashTable",hashTable);
			//		 res.sendRedirect("/R_all_passenger_detail.html");

						//System.out.println((String)hashTable.get("password"));
						//System.out.println((String)hashTable.get("login_id"));
						

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
					}catch(Exception ignore){}
			}

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
												

													String sql="select User_id,Password from  Registration_Details where User_id='"+login_id+"'  and Password='"+password+ "'	";
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
														  System.out.println("R_Display_total..=>after login id");
    													   return true;

													}
																
														//	param.res.sendRedirect("http://localhost:8080/servlets/R_Display_total_Number_of_Passenger_ScreensFrames");
								
											}//else
								}catch(SQLException e){					
								
																					/*				param.res.setContentType("text/html");
																																							e.printStackTrace(param.pw);
								System.out.println("in catch");*/		String myMsg="Sorry,Wrong login_id or may Password ,Try again";
																										String nextURL="/inside_holidaysairways.html";
																										param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																		  }
																		  return true;

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

