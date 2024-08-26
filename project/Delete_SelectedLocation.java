
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
    
public class Delete_SelectedLocation extends HttpServlet
{
    		public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
			{
						Connection con;
						c_Parameters	param=new c_Parameters();
						con=param.m_getConnection();								//get Connection from c_Prameters class
						param.con=con;
						param.req=req;
						param.res=res;
													HttpSession Session=req.getSession(false);
													System.out.println("Yes-12***********");
													try{
															
															if(Session==null)
															{
																	System.out.println("Session is null so");
															}
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
																					/*param.res.setContentType("text/html");
																					e.printStackTrace(param.pw);
																					System.out.println("in catch");		*/
																					String myMsg="Sorry,Wrong login_id or may Password ,Try again";
																					String nextURL="/Admin_login.html";
																					res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																					
																		  }	 
					
			try{		
						//get parameter from URL
						param.m_writeGetParameters(param);
						Hashtable hashTable=param.hashTable;
						PrintWriter pw=new PrintWriter(res.getWriter());
						param.pw=pw;

									if(Session!=null)
									{
												Hashtable Session_hashTable=(Hashtable)Session.getValue("hashTable");
												if(Session_hashTable!=null)
												{
													hashTable=param.copy_hashTable_To_hashTable(hashTable,Session_hashTable );
									
												}else	System.out.println("Screen2:=>Session hashTable is Null");

									}else	System.out.println("Screen2:=>Session is Null");

				
						//Session tracking functio from c_Parameter
						 System.out.println("String login_id="+(String)Session.getValue("login_id"));
						 System.out.println("String password="+(String)Session.getValue("password"));

						con.setAutoCommit(false);
						// if  call is for Delete Selected Flight for delete all entry ,url have paramter   "Delete_SelectedFlight=Yes;"	
						System.out.println("35Table=>"+(String)hashTable.get("Table"));
						System.out.println("36Field_Atrribute=>"+(String)hashTable.get("Field_Atrribute"));
						System.out.println("37Field_Atrribute_ValueSelected=>"+(String)hashTable.get((String)hashTable.get("Field_Atrribute")));

						String Table=(String)hashTable.get("Table");
						String Field_Atrribute=(String)hashTable.get("Field_Atrribute");
						String Field_Atrribute_ValueSelected=(String)hashTable.get((String)hashTable.get("Field_Atrribute"));

						//String Delete_Flight_No=(String)hashTable.get("Table");
						Delete_TableEntry(param,Table,Field_Atrribute,Field_Atrribute_ValueSelected);
						
						System.out.println("after Delete");				
						con.commit();con.close();
						Session.invalidate();
						res.sendRedirect("/inside_holidaysairways.html");
						//System.out.println("Delete End here =>Update_F_M_D_C_table");			
						
						  
		}//catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
					 catch(SQLException e){  param.res.setContentType("text/html");	e.printStackTrace(param.pw);}
					  catch(NumberFormatException e){ param.pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}
					  catch(Exception e ){param.res.setContentType("text/html");	e.printStackTrace(param.pw);}

 }//doPost

    public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doPost(req,res);

    }//doGet()

				public void Delete_TableEntry(c_Parameters param,String Table,String Attribute,String Selected_Atrribute_Value)
				{
										ResultSet rs;
										Connection con=param.con;
										//Here user separate connection call Delate_Con it set as Autocommit=false  //globe variable 
										String sql;
										PreparedStatement  st;
										Hashtable hashTable=param.hashTable;
										int Count;
									try{																
													System.out.println(Table+"="+Attribute+"="+Selected_Atrribute_Value);
													sql="Delete From  "+Table +"  where "+Attribute+"='"+Selected_Atrribute_Value +"'";													
													System.out.println("SQl="+sql);
													st=con .prepareStatement(sql);
													Count=st.executeUpdate();
													System.out.println("Yes line=439");

											}catch(SQLException  e){ param.res.setContentType("text/html");
																		e.printStackTrace(param.pw);}
											System.out.println(Table);
	}//Delete_Flight_Master_Details_desc_Current_TableEntry

}//class Display_FlightNumber_List

