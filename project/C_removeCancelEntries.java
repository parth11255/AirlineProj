import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class  C_removeCancelEntries extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
			//var Declaration
			Hashtable hashTable; 
			c_Parameters param=new c_Parameters();
			PrintWriter pw= new PrintWriter(res.getWriter());
			param.pw=pw;
			
			param.res=res;
			param.req=req;
			Connection con=param.m_getConnection();
			
			param.con=con;
			
			//getParameters
			param.m_writeGetParameters(param);
			hashTable=param.hashTable;
			
			//check Session_id
			HttpSession  Session=req.getSession(false);

			if(Session==null)
			{
							String myMsg="Sorry ,Wrong login_id or may Password ,Try again";
							String nextURL="/C_checkLogin.html";
							param.res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
									
			}//Session==null

			param.Session=Session;

			try{
							con.setAutoCommit(false);
							Object ObjecthashTable=	 Session.getValue("hashTable");
							if(ObjecthashTable!=null)
							{
								Hashtable Session_hashTable=(Hashtable)ObjecthashTable;
								hashTable=param.copy_hashTable_To_hashTable(hashTable,Session_hashTable);
							}else{System.out.println("Session hashTable is null");}

				}catch(Exception e){	res.setContentType("text/html");
													e.printStackTrace(param.pw)	;
													System.out.println("in Catch");
													
													String myMsg="Sorry,Wrong login_id or may Password ,Try again";                    
													String nextURL="/frontpage.html";                                                                                                                 
													res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);     
													
												}//catch

			//Search for Condition
			{

				try{
								//if  they try to cancel all passenger then allow
								//if they are try to cancel some passenger with the registor passenger reservation then don't allow
								int Total_Passenger=(Integer.parseInt((String)hashTable.get("Total_Passenger")));

//System.out.println("Line=>65="+Total_Passenger);
System.out.println("login_id="+ (String)hashTable.get("login_id"));        
System.out.println("password="+(String)hashTable.get("password"));     

								String login_id		=	(String)hashTable.get("login_id");
								String password	=	(String)hashTable.get("password");
								boolean rmFromPsgMst_Flag=true;
								for(int i=1;i<=Total_Passenger	;i++)
								{
										System.out.println("Line=>72="+(String)hashTable.get("Pass_No"+i));
//										String temp=(String)hashTable.get("Pass_No"+i);
										if(	(String)hashTable.get("Pass_No"+i)==null )//&&  !((String)hashTable.get("Pass_No"+i)).equalsIgnoreCase("on") )//means ON
										{//one of them not marked means they try to cancel only some passenger
//stem.out.println("Line=>76");
												rmFromPsgMst_Flag=false;
												String sql="Select Pass_No From Registration_Details as t1 , Passenger_Master as t2,Passenger_Details as t3  where t1.User_id='"+login_id+"'  and t1.password='"+password+"'  and t1.Given_name =t3.Given_name and t1.Family_name=t3.Family_name and  t1.User_id=t2.User_id and t2.Res_No=t3.Res_No ";
												PreparedStatement st=con.prepareStatement(sql);
//stem.out.println("Line=>"+sql);
												ResultSet rs= st.executeQuery();
//stem.out.println("Line=>82");

												if(rs.next())
												{//check registor passenger in cancel list
//System.out.println((String)hashTable.get("Pass_No"+rs.getInt("Pass_No")));														
														if(	(String)hashTable.get("Pass_No"+rs.getInt("Pass_No"))!=null)//((String)hashTable.get("Pass_No"+rs.getInt("Pass_No"))	).equalsIgnoreCase("on"))
														{System.out.println("Yes  it is not allowed");
																con.rollback();
																con.close();
																String myMsg="Sorry, You can not cancel Ticket of a Credit Card holder .<br>If you want then cancel all passenger Ticket ";
																String nextURL="http://localhost:8080/C_checkLogin.html";                                                                                                                 
																res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
														return;
														}
														else{ System.out.println("Yes it is allowed"); }
												
												}else{ System.out.println("Inconsistant Database"); }

										}//if
//	System.out.println("Line=>96="+i);
								}//for
//System.out.println("Line=>88");
								//get again all information as per privious servlet C_Display_cancelDetails
								String sql="SELECT Distinct t4.Res_No,t5.Pass_No,t5.Given_name, t5.Family_name, t4.Flight_No, t1.Source, t1.Destination, t5.Seat_No, t4.Category, t5.Confirmed FROM Flight_Master AS T1, Flight_Details AS T2, Flight_Desc AS T3, Passenger_Master AS T4, Passenger_Details AS T5  WHERE T4.User_id=? And T4.Res_No=T5.Res_No And t4.Flight_No=t1.Flight_No";
								
								PreparedStatement st=con.prepareStatement(sql);

								st.setString(1,(String)hashTable.get("login_id") );
								ResultSet rs       =st.executeQuery();
//stem.out.println("Line=>100");
							//if they are Confirmed then remove from Passenger_Master
							//if they are not Confirmed then remove from Waiting_list

							//{//if they are Confirmed then remove from Passenger_Detail also from Passenger_Master if mrPsgMst_Flag=true

											if(rmFromPsgMst_Flag==true)//remove from passenger_Master
											{
//System.out.println("Line=>116");
												
													//user define function call
													rmEntryFromPssengerMasterTable(param);

											}//if
											int NoOfCancel=0,iNextPass_No=1;
											while(rs.next())	
											{	
												 hashTable.put("Flight_No",rs.getString("Flight_No"));											 
												 hashTable.put("Category",rs.getString("Category"));
												 hashTable.put("Source",rs.getString("Source"));
//System.out.println("Line=>127");
//System.out.println("Line=>"+rs.getString("Confirmed"));
//System.out.println("Line=>"+(String)hashTable.get("Pass_No"+iNextPass_No));
									String varForCheck=(String)hashTable.get("Pass_No"+iNextPass_No);
//System.out.println("Line=>"+varForCheck);
									if(		varForCheck!=null	 )
									{
System.out.println("Line=>138");
//												 if( (Integer.parseInt(rs.getString("Confirmed"))) ==1 )
												{//remove Form passenger_Details  if marked check & confirmed=yes
														sql="Delete From Passenger_Details where Res_no=? and Given_name=? and Family_name=? and Pass_No=? ";
//System.out.println("Line=>131");
														PreparedStatement temp_st=con.prepareStatement(sql);
														temp_st.setInt(1,rs.getInt("Res_no"));
														temp_st.setString(2,rs.getString("Given_name"));
														temp_st.setString(3,rs.getString("Family_name"));
														temp_st.setInt(4,rs.getInt("Pass_No") );
//System.out.println("Line=>138");

														int count=temp_st.executeUpdate();
//System.out.println("Line=>147");
														temp_st.close();
														NoOfCancel++;	
														hashTable.put("Seat_No"+NoOfCancel,rs.getString("Seat_No"));
//System.out.println("Line=>"+hashTable.get("Seat_No"+NoOfCancel));
												}//if
											}//if
												//else
												{	if((Integer.parseInt(rs.getString("Confirmed"))) !=1 && (String)hashTable.get("Pass_No"+iNextPass_No)!=null)
															{//if it not Confirmed then remove from Waiting_list & marked checked also From Passenger_Deatils
																					sql="Delete  from Waiting_list where  Flight_No=? and Category=? and Res_no=? and Pass_No=?";
																					PreparedStatement temp_st=con.prepareStatement(sql);
																					temp_st.setString(1,(String)hashTable.get("Flight_No") );
																					temp_st.setString(2,(String)hashTable.get("Category") );
																					temp_st.setString(3,rs.getString("Res_no") );
																					temp_st.setString(4,rs.getString("Pass_No") );



																					temp_st.executeUpdate();
																					temp_st.close();

																					sql="Update Waiting_list set Seat_No=Seat_No-1 where Flight_No=? and Category=?";
																					temp_st=con.prepareStatement(sql);
																					temp_st.setString(1,(String)hashTable.get("Flight_No") );
																					temp_st.setString(2,(String)hashTable.get("Category") );
																					temp_st.executeUpdate();
																					temp_st.close();
																}//if
														}//else
														//System.out.println("Line=>169="+iNextPass_No);
														iNextPass_No++;
											}//while
										//System.out.println("Line=>150="+NoOfCancel);

											//Find next Seat_no
							//												{
																				//no need
							//												}
											//Find Such a Res_no where Count(*)=totalCancel
											rs.close();
											//shift waiting list into passenger master by updating Confirmed=true assing seat_No= cancel passenger's seat number
											//and remove from waiting list and update others waiting number for that flight & category
											sql="Select Res_No,Pass_No from Waiting_list where Flight_No=? and Category=?  ORDER BY [waiting_no]";
											st=con.prepareStatement(sql);
//System.out.println("Flight_No="+(String)hashTable.get("Flight_No") );                    
//System.out.println("Category="+(String)hashTable.get("Category") );               
											st.setString(1,(String)hashTable.get("Flight_No") );
											st.setString(2,(String)hashTable.get("Category") );
											rs       =st.executeQuery();
//System.out.println("Line=>194=");
											int tempCount=1;
											while(rs.next() && NoOfCancel>=tempCount)
											{	
//System.out.println("Line=>195="+tempCount);

													sql="Update Passenger_Details set Confirmed=true ,Seat_No= ? where Res_no=? and Pass_No=?";
													PreparedStatement temp_st=con.prepareStatement(sql);
//System.out.println("Line=>  204="+(String)hashTable.get("Seat_No"+tempCount));	
////System.out.println("="+ rs.getString("Res_No"));   
////System.out.println("="+ rs.getInt("Pass_No"));       
													temp_st.setString(1,(String)hashTable.get("Seat_No"+tempCount));
													temp_st.setString(2,	rs.getString("Res_No"));
													temp_st.setInt(3,			rs.getInt("Pass_No"));

	//System.out.println("Line=>204="+tempCount);
												int count=temp_st.executeUpdate();
//System.out.println("Line=>206="+tempCount);

													temp_st.close();
													tempCount++;
											}//while
											rs.close();st.close();
//System.out.println("Line=>217=");
											//if not enough entries in waiting list then update current_Details table
											if(NoOfCancel >= tempCount )
											{
													sql="Update Current_Details set Available_Seats=Available_Seats+ "+(NoOfCancel -tempCount +1 )+"  where Flight_No=? and Category=?";
//System.out.println("Line=>222="+sql);
													PreparedStatement temp_st=con.prepareStatement(sql);
//													st.setInt(1,;
													temp_st.setString(1,(String)hashTable.get("Flight_No") );
													temp_st.setString(2,(String)hashTable.get("Category") );
//System.out.println("Line=>226=");
													temp_st.executeUpdate();
//System.out.println("Line=>228=");
													temp_st.close();

											}//if
//System.out.println("Line=>232=");
											sql="Delete * from Waiting_list where Flight_No=? and Category=? and Waiting_No<="+NoOfCancel;
											st=con.prepareStatement(sql);



//System.out.println("Line=>216="+sql);

											st.setString(1,(String)hashTable.get("Flight_No") );
											st.setString(2,(String)hashTable.get("Category") );
											st.executeUpdate();
											st.close();
////System.out.println("Line=>223=");
											sql="Update Waiting_list set Waiting_No=Waiting_No -"+NoOfCancel+"		where Flight_No=? and Category=?";
											st=con.prepareStatement(sql);
											st.setString(1,(String)hashTable.get("Flight_No") );
											st.setString(2,(String)hashTable.get("Category") );
////System.out.println("Line=>216="+sql);
											st.executeUpdate();
											st.close();
////System.out.println("Line=>231=");

con.commit();
Session.invalidate();

//confimation
			String myMsg="Your Tickets Are Canceled .<BR>You can collect your refund money From "+(String)hashTable.get("Source") +" Office according to refund rules.";
			String nextURL="/C_checkLogin.html";
			res.sendRedirect("http://localhost:8080/servlet/Error_page?msgflag=Message&msg="+myMsg+"&nextURL="+nextURL);


										}catch(Exception e){ try{param.con.rollback(); }catch(Exception ec){System.out.println("Connection problem");}	
																		param.res.setContentType("text/html");
																		e.printStackTrace(param.pw);
																		System.out.println("in main catch");		
																		}//catch
			}	//block

				//end 

				
			
	}//doPost()



public void rmEntryFromPssengerMasterTable(c_Parameters param)
{
								Hashtable hashTable =param.hashTable;
								Connection con=param.con;
//System.out.println("Line=>in function");
								String sql="SELECT Distinct t4.Res_No,t5.Pass_No,t5.Given_name, t5.Family_name, t4.Flight_No, t1.Source, t1.Destination, t5.Seat_No, t4.Category, t5.Confirmed FROM Flight_Master AS T1, Flight_Details AS T2, Flight_Desc AS T3, Passenger_Master AS T4, Passenger_Details AS T5  WHERE T4.User_id=? And T4.Res_No=T5.Res_No And t4.Flight_No=t1.Flight_No";
					try{
								PreparedStatement st=con.prepareStatement(sql);

								st.setString(1,(String)hashTable.get("login_id") );
								ResultSet rs       =st.executeQuery();
//System.out.println("Line=>232 in function");
								//if they are Confirmed then remove from Passenger_Master
								//if they are not Confirmed then remove from Waiting_list

								{//if they are Confirmed then remove from Passenger_Master
								if(rs.next())
								{
									sql="Delete From Passenger_Master where Res_no=? and Flight_No=? and Category=? and User_id=?";
									PreparedStatement temp_st=con.prepareStatement(sql);
//									System.out.println("="+rs.getString("Res_no"));                     
//									System.out.println("="+rs.getString("Flight_No"));                  
//									System.out.println("="+rs.getString("Category"));                   
//									System.out.println("="+(String)hashTable.get("login_id"));      

									temp_st.setString(1,rs.getString("Res_no"));
									temp_st.setString(2,rs.getString("Flight_No"));
									temp_st.setString(3,rs.getString("Category"));
									temp_st.setString(4,(String)hashTable.get("login_id"));
//System.out.println("Line=>244");									
									int count=temp_st.executeUpdate();
									temp_st.close();
								}
									rs.close();

							}//block
				}catch(Exception e){	try{param.con.rollback(); }catch(Exception ec){System.out.println("Connection problem");}	
																param.res.setContentType("text/html");
																		e.printStackTrace(param.pw);
																		System.out.println("in function catch");		
												}
}//function

}//C_removeCancaledEnteries