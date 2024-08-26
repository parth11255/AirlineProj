/*call from:=R_login.html		 =>finction go_Nexr(form1)
check whether login & password is correct for old passenger if yes then Display_all_paassenger_detail hatl page otherwise error error page with nexrRUI=R_login.html
Note=>Please don't fix more than one token in one line in R_Adult_passenger_Deatils.html*/

    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
    
public class R_Newuser extends HttpServlet
{

	public void init(ServletConfig Config) throws ServletException                        
	{
			super.init(Config);

	//param.hashTable.clear();//make empty hashTable
			
	}

     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {

	//					var declaration
						long Cost_of_ticket=0;
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
						String login_id=(String)hashTable.get("login_id");
						String password=(String)hashTable.get("password");
//						String newuser=(String)hashTable.get("newuser");
						String newuser="true";
						hashTable.put("newuser",(Object)newuser);
	
				
						//Session tracking functio from c_Parameter
						 Session=req.getSession(false);
						 if(Session==null)
						 {
														System.out.println("Yes-100***********");
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
																		e.printStackTrace(param.pw);*/
																		System.out.println("in catch=73");		
																		String myMsg="Sorry,Wrong login_id or may Password ,Try again";
																		String nextURL="/frontpage.html";
																		res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																		return;
													} //catch

/*		 Here Enter all Registration Details in Registration_Details Table with validation if  invalid then redirect that page		

		//check whether that user_id is unique in database
		//combine some fields=>Title+GivenName,Contact_phone=country+area+local number,
													Alternative_phone=country+area+local number		
													Fax=country+area+local number

*/						try{
									String sql="Select * from Registration_Details where User_id=? ";

									PreparedStatement st1=con.prepareStatement(sql);
									System.out.println("First sql=>"+sql);	
									System.out.println("User id	 :"+ (String)hashTable.get("User_id") );       
									System.out.println( "PassWord	:"+(String)hashTable.get("Password") );   
									st1.setString(1,(String)hashTable.get("User_id") );


									System.out.println("First sql=>"+sql);
									ResultSet rs1       =st1.executeQuery();

									if( rs1.next() )
									{
										//Duplicate User_id redirect page
										System.out.println("Duplicate");
										String myMsg="Sorry, login_id or may Password allready exist,Try again";
																					String nextURL="/R_person_detail_for_registration.html";
																					res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																					return;

									}else
									{//Store in database
																								System.out.println("not Duplicate");
													sql="Insert into Registration_Details values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

													st1=con.prepareStatement(sql);
System.out.println( (String)hashTable.get("User_id") );                         
System.out.println( (String)hashTable.get("Password") );    
System.out.println( (String)hashTable.get("Title") );    
System.out.println( (String)hashTable.get("Given_name"));                 
System.out.println( (String)hashTable.get("Family_name"));                
System.out.println( (String)hashTable.get("Comp_name"));                
System.out.println( (String)hashTable.get("Street_address"));            
System.out.println( (String)hashTable.get("City"));                                  
System.out.println( (String)hashTable.get("State"));                               
System.out.println( (String)hashTable.get("Postal_Zip_code"));          
System.out.println( (String)hashTable.get("tel_home_ccode"));
System.out.println( (String)hashTable.get("tel_home_area"));
System.out.println( (String)hashTable.get("tel_home"));
System.out.println( (String)hashTable.get("tel_work_ccode"));
System.out.println( (String)hashTable.get("tel_work_area"));
System.out.println( (String)hashTable.get("tel_work"));
//System.out.println( (String)hashTable.get(Alternative_phone));  

System.out.println( (String)hashTable.get("Email"));                      

													st1.setString(1,(String)hashTable.get("User_id") );
													st1.setString(2,(String)hashTable.get("Password") );
													st1.setString(3,(String)hashTable.get("Title")+" "+(String)hashTable.get("Given_name"));
													st1.setString(4,(String)hashTable.get("Family_name"));
													st1.setString(5,(String)hashTable.get("Comp_name"));
													st1.setString(6,(String)hashTable.get("Street_address"));
													st1.setString(7,(String)hashTable.get("City"));
													st1.setString(8,(String)hashTable.get("State"));
													st1.setString(9,(String)hashTable.get("Postal_Zip_code"));
													st1.setString(10,(String)hashTable.get("Country"));
													String Contact_phone=(String)hashTable.get("tel_home_ccode")+"-"+(String)hashTable.get("tel_home_area")+"-"+(String)hashTable.get("tel_home");
													st1.setString(11,Contact_phone);

													String Alternative_phone =(String)hashTable.get("tel_work_ccode")+"-"+(String)hashTable.get("tel_work_area")+"-"+(String)hashTable.get("tel_work");
													st1.setString(12,Alternative_phone);
													
													String Fax_no =(String)hashTable.get("fax_no_ccode")+"-"+(String)hashTable.get("fax_no_area")+"-"+(String)hashTable.get("fax_no");
System.out.println( Fax_no);                          
													st1.setString(13,Fax_no);
													st1.setString(14,(String)hashTable.get("Email"));

													System.out.println(sql);
													   int count=st1.executeUpdate();
													System.out.println("after sql");	
												

									}//else
						}
						catch(SQLException e){ /*
										param.res.setContentType("text/html");
									e.printStackTrace(param.pw);*/
								System.out.println("in catch	:R_Newuser()=>167");
					
												String myMsg="please ,Fill  all values properly";
												String nextURL="/R_person_detail_for_registration.html";
												res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
												return;
																	}

//end here 
						 
						 
//						 System.out.println("R_Diaplay_total...=>"+login_id);
//						 System.out.println("R_Display_total..=>"+password);
						
//						  System.out.println("R_Display_total..=>after check_for _right_passenger");

						 //Display Total Adult Screens
						 //ServletContext context=getServletContext();
//						  System.out.println("R_Display_total..Adult=>"+(String)hashTable.get("LAdult"));
// 						  System.out.println("R_Display_total..child=>"+(String)hashTable.get("LChild"));
    					  int  TotalAdult=Integer.parseInt( ((String)hashTable.get("LAdult")) );
						  int  TotalChild=Integer.parseInt( ((String)hashTable.get("LChild")) );
						  
						  System.out.println("reach55");
					try{
						String  sql="SELECT Cost_of_ticket "
											 +" FROM  Flight_Desc AS T2"
											 +" WHERE T2.Category=? And t2.flight_no=? ";
						/****and Available_Seat>=? set confirm= y/n ****/

						/*Total_ticket+ */

						PreparedStatement st=con.prepareStatement(sql);

						//st.setInt(1,Total_ticket.intValue());

//						System.out.println((String)hashTable.get("LCategory") );       
//						System.out.println((String)hashTable.get("SelectedFlight") );//((String)hashTable.get("LChild")) ); 
						st.setString(1,(String)hashTable.get("LCategory") );
						st.setString(2,(String)hashTable.get("SelectedFlight") );
//						System.out.println(sql);
						ResultSet rs       =st.executeQuery();
						//long Cost_of_ticket;  declare at top
						if( rs.next() )
						{
							Cost_of_ticket=Long.parseLong( (String)rs.getString("Cost_of_ticket") );
//							System.out.println("Cost_of_ticket="+Cost_of_ticket);

						}else Cost_of_ticket=12000;

						
					}
				catch(Exception e){}

//					   System.out.println("R_Display_total..=>"+(String)hashTable.get("LChild"));
  						pw.println("<html>");
						pw.println("<body>");
						pw.println("<form name=R_Display_total_Number_of_Passenger_ScreensFrames  method=GET>");

						for(int i=1;i<=TotalAdult;i++)
							{
																  //Read file
									   BufferedReader in =new BufferedReader(new FileReader("c:/JavaWebServer2.0/public_html/R_Adult_passeneger_Detail.html"));
										for(;;)
											{
												String line =in.readLine();
												String new_string=null;
												int start_index;
												if(line==null)
													break;
												start_index=line.indexOf("#1");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat(new Integer(i).toString());
															String temp=line.substring(start_index+2);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
															System.out.println(line);
												}
												start_index=line.indexOf("#2");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);
															new_string=new_string.concat("Adult");
															String temp=line.substring(start_index+2);
															if(temp!=null)
																	new_string=new_string.concat(temp);
															line=new String(new_string);
												}
												start_index=line.indexOf("#name1");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat("Title_Adult_passenger"+new Integer(i).toString());
															String temp=line.substring(start_index+6);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//													System.out.println(line);
											}
												start_index=line.indexOf("#name2");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat("GivenName_Adult_passenger"+new Integer(i).toString());
															String temp=line.substring(start_index+6);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//															System.out.println(line);
											}
												start_index=line.indexOf("#name3");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat("FamilyName_Adult_passenger"+new Integer(i).toString());
															String temp=line.substring(start_index+6);
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
						  for(int i=1;i<=TotalChild;i++)
							{
																  //Read file
										 BufferedReader in =new BufferedReader(new FileReader("c:/JavaWebServer2.0/public_html/R_Child_passenger_Details.html"));
										for(;;)
											{
												String line =in.readLine();
												String new_string=null;
												int start_index;

												if(line==null)
													break;
												start_index=line.indexOf("#1");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat(new Integer(i).toString());
															String temp=line.substring(start_index+2);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//															System.out.println(line);
												}
												start_index=line.indexOf("#2");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);
															new_string=new_string.concat("child");
															String temp=line.substring(start_index+2);
															if(temp!=null)
																	new_string=new_string.concat(temp);
															line=new String(new_string);
												}
												
												start_index=line.indexOf("#name1");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat("Title_Child_passenger"+new Integer(i).toString());
															String temp=line.substring(start_index+6);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//															System.out.println(line);
											}
												start_index=line.indexOf("#name2");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat("GivenName_Child_passenger"+new Integer(i).toString());
															String temp=line.substring(start_index+6);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//															System.out.println(line);
											}
												start_index=line.indexOf("#name3");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat("FamilyName_Child_passenger"+new Integer(i).toString());
															String temp=line.substring(start_index+6);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//															System.out.println(line);
											}
												start_index=line.indexOf("#age");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat("age"+new Integer(i).toString());
															String temp=line.substring(start_index+4);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
															System.out.println(line);
												}


												pw.println(line);
											}
											in.close();

							}//Child for loop
				
					//Display fare details				
							{						
								BufferedReader in =new BufferedReader(new FileReader("c:/JavaWebServer2.0/public_html/R_Fare_for_Category.html"));
								for(;;)
								{
												String line =in.readLine();
												String new_string=null;
												int start_index;

												if(line==null)
													break;
												start_index=line.indexOf("#1");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat((String)hashTable.get("LCategory"));
															String temp=line.substring(start_index+2);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//													System.out.println(line);
												}
												start_index=line.indexOf("#2");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);
															new_string=new_string.concat("Adult");
															String temp=line.substring(start_index+2);
															if(temp!=null)
																	new_string=new_string.concat(temp);
															line=new String(new_string);
												}
												
												start_index=line.indexOf("#3");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat(new Long(Cost_of_ticket).toString());
															String temp=line.substring(start_index+2);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//															System.out.println(line);
											}
												start_index=line.indexOf("#4");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat(new Long	(Cost_of_ticket*TotalAdult).toString());
															String temp=line.substring(start_index+2);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//													System.out.println(line);
											}
												start_index=line.indexOf("#5");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat("Child");
															String temp=line.substring(start_index+2);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//													System.out.println(line);
											}
												start_index=line.indexOf("#6");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat(new Long( Cost_of_ticket/2).toString());
															String temp=line.substring(start_index+2);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//													System.out.println(line);
											}
												start_index=line.indexOf("#7");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat(new Long((Cost_of_ticket/2)*TotalChild).toString());
															String temp=line.substring(start_index+2);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//													System.out.println(line);
											}
												start_index=line.indexOf("#8");
												if(start_index!=-1)
												{
															new_string=line.substring(0,start_index);//System.out.println(new_string);
															new_string=new_string.concat(new Long	(( ( ( Cost_of_ticket/2 )*TotalChild )+( (Cost_of_ticket*TotalAdult ) )+2800)	 ).toString());
															String temp=line.substring(start_index+2);
															if(temp!=null)
															{
																	new_string=new_string.concat(temp);
															}
															line=new String(new_string);
//													System.out.println(line);
											}

												pw.println(line);
							}//for
							in.close();
					}//fare loop

					//for Creadit card detail
							{						
								BufferedReader in =new BufferedReader(new FileReader("c:/JavaWebServer2.0/public_html/R_Credit_Card_Details.html"));
								for(;;)
								{
												String line =in.readLine();
												if(line==null)
													break;
												pw.println(line);
								}
											in.close();
//							System.out.println("out for");
							}

						pw.println("<input type=button value=Pay onclick=pay(R_Display_total_Number_of_Passenger_ScreensFrames)>");	
						 //int TotalChild=hashTable.get("LChild");
						pw.println("</form>");
						pw.println("</body>");
						pw.println("<script>");                                                                                                                             
						pw.println("function pay(form1) { form1.action='http://localhost:8080/servlet/R_Payment' ; form1.submit();}");
						
						pw.println("</script>");                                                                                                                             
						pw.println("</html>");
						Session.putValue("hashTable",hashTable);
			//		 res.sendRedirect("/R_all_passenger_detail.html");

						//System.out.println((String)hashTable.get("password"));
						//System.out.println((String)hashTable.get("login_id"));
						

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
								}catch(SQLException e){					
								
																					/*				param.res.setContentType("text/html");
																																							e.printStackTrace(param.pw);*/
								System.out.println("in catch=621")	;	String myMsg="Sorry,Wrong login_id or may Password ,Try again";
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

