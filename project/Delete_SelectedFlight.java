
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
	import java.util.Calendar;
	import java.text.DateFormat;
	import java.text.ParseException;
    
public class Delete_SelectedFlight extends HttpServlet
{

/**wrote separate file c_Prameters.java*******/
			public void init(ServletConfig Config) throws ServletException                        
			{
					super.init(Config);											//register the init parameters
			}
    		 public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
			  {
				//			var declaration
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

											String Delete_Flight_No	=(String)hashTable.get("Flight_No");
											System.out.println("Delete_SeletedFlightNumber=>"+Delete_Flight_No);
											if(Delete_Flight_No!=null)
											{
														Delete_Flight_Master_Details_desc_Current_TableEntry(param,Delete_Flight_No);
																														//	}catch(SQLException  e){ param.res.setContentType("text/html");
																								//									e.printStackTrace(param.pw);}
																			//Delete_con.commit();
														con.commit();
														Session.invalidate();
														res.sendRedirect("/inside_holidaysairways.html");
														System.out.println("End here =>Update_F_M_D_C_table");					 

														return;
											}//if(Delete_Flight_No!=null)
							System.out.println("End here =>Update_F_M_D_C_table");					 
						  
		}//catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
					 catch(SQLException e){  param.res.setContentType("text/html");	e.printStackTrace(param.pw);}
					  catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}

 }//doPost

    public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doPost(req,res);

    }//doGet()

								public void Delete_Flight_Master_Details_desc_Current_TableEntry(c_Parameters param,String Delete_Flight_No)
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
			
											}catch(SQLException  e){ param.res.setContentType("text/html");
																						e.printStackTrace(param.pw);}

					}//Delete_Flight_Master_Details_desc_Current_TableEntry

}//class Display_FlightNumber_List

