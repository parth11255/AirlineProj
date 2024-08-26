
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
    
public class Display_Reports extends HttpServlet
{

     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {
						System.out.println("Dispaly_Reports");
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
						PrintWriter pw=new PrintWriter(res.getWriter());
						param.pw=pw;

						//Session tracking functio from c_Parameter
						 Session=req.getSession(false);
						 if(Session==null)
						 {
//														System.out.println("Yes-100***********");
														String myMsg="Sorry ,Wrong login_id or Password ,Try again";
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
																		String myMsg="Wrong login_id or Password ,Try again";
																		String nextURL="/frontpage.html";
																		res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																		return;
													} //catch
						String sql=null;

						if(Integer.parseInt((String)hashTable.get("Report_No"))==1)
						{
							System.out.println("Report_No	:"+(String)hashTable.get("Report_No"));
							sql="SELECT	 [t1.Flight_No] AS  [Flight Number],	[Source],	 [Destination],	 [Day_of_Flight]	AS ['Day'], format([Time_of_flight],'hh:mm:ss AMPM') AS ['Time'],	format([Date_of_Departure],'dd-mmm- yyyy') AS ['Date']	,Total_capacity as [Capacity] ,Duration_of_Flight as [Duration]	FROM	 Flight_Master AS t1, Flight_Details AS t2	WHERE t1.Flight_No=t2.Flight_No";
						}
						else 	if(Integer.parseInt((String)hashTable.get("Report_No"))==2)
								{
											sql="SELECT [t1.Flight_No] AS [Flight Number], [Category], [t1.Flight_Name] AS [Plane Name], [Starting_SeatNo] AS [Start Seat Number ], [Ending_SeatNo] AS [End Seat Number] FROM Flight_Master AS t1, Flight_Details AS t2, Flight_Desc AS t3 WHERE t1.Flight_No=t2.Flight_No and t1.Flight_No=t3.Flight_No";

								}

								else 	if(Integer.parseInt((String)hashTable.get("Report_No"))==3)
										{
													sql="SELECT [t1.Flight_No] AS [Flight Number], [t1.Flight_Name] AS [Plane Name] FROM Flight_Master AS t1";

										}
										else 	if(Integer.parseInt((String)hashTable.get("Report_No"))==4)
												{
															sql="SELECT [t1.Flight_No] AS [Flight Number], format([Time_of_flight],'hh:mm:ss AMPM') AS ['Time'], format([Date_of_Departure],'dd-mmm- yyyy') AS ['Date'], t3.Category, [Cost_of_ticket] AS Cost, [Available_Seats] AS [Vacant Seats] FROM Flight_Master AS t1, Flight_Details AS t2, Flight_Desc AS t3, Current_Details AS t4 WHERE t1.Flight_No=t2.Flight_No and t2.Flight_No=t3.Flight_No and t3.Flight_No=t4.Flight_No and t3.Category=t4.category";

												}
												else 	if(Integer.parseInt((String)hashTable.get("Report_No"))==5)
														{
																	sql="SELECT Distinct t3.Res_No, t2.Given_name AS Name, t2.Family_Name AS Surname, [t3.Flight_No] AS [Flight Number], format([Date_of_Departure],'dd-mmm- yyyy') AS ['Date'] FROM passenger_master AS t1, passenger_Details AS t2, waiting_list AS t3 WHERE t1.Res_No=t2.Res_No and t2.Res_No=t3.Res_No";
														}
														else 	if(Integer.parseInt((String)hashTable.get("Report_No"))==6)
																{
																			sql="Select * from Registration_Details";
																}
														


											System.out.println("sql	 :"+sql);

						//Display Total Adult Screens
						//ServletContext context=getServletContext();
						  
								System.out.println("Line=>72");
					try{
								PreparedStatement st=con.prepareStatement(sql);
														
								ResultSet rs       =st.executeQuery();
							
								System.out.println("Line=>81");															
					
								htmlresultset obj=null;
								if(rs!=null)
								{
									obj=new htmlresultset(rs);

								}else System.out.println("rs is null");


								pw.println("<html>");
								pw.println("<body >");
								pw.println("<form name=Display_Reports  method=POST>");
								pw.println("<title>Reports</title>");
								pw.println("						<body>");
								pw.println("						<input  type=button name='Print' value='Print' onclick=FunPrint(Display_Reports)>");

								pw.print("<br><center>"+obj.printtable()+"</center>");
						
								pw.println("						</table >");

								pw.println("						<input  type=button name='Print' value='Print' onclick=FunPrint(Display_Reports)>");
								pw.println("</form>");
								pw.println("</body>");
						
								writeScript(param);
						
								pw.println("</html>");
						
								con.close();
	 }//catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
      catch(SQLException e){ pw.println(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
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

	public void writeScript(c_Parameters param)throws ServletException,IOException
	{
				PrintWriter pw=param.pw;
	
				pw.println("<script>");
				pw.println("function FunPrint(form1)");
				pw.println("{");
				pw.println("	window.print();");
				pw.println("}");
				pw.println("</script>");
				pw.println("</html>");

	}//writeScript()
}//class Screen1

