
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
	import java.util.Calendar;
	import java.text.DateFormat;
	import java.text.ParseException;
    
public class Display_Modification_Fields extends HttpServlet
{
	private	Connection con;
	private	String Flight_No;
	HttpSession Session;
	public void init(ServletConfig Config) throws ServletException                        
	{
			super.init(Config);									//register the init parameters
			c_Parameters	param=new c_Parameters();
			con=param.m_getConnection();			//get Connection from c_Prameters class
		
	}
  public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
   {


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
																//param.res.sendRedirect("/Choice_Modification_Deletion.html");
					
										}catch(Exception e){	/*	param.res.setContentType("text/html");
																				e.printStackTrace(param.pw);
																				System.out.println("in catch");		*/
																				String myMsg="Sorry,Wrong login_id or may Password ,Try again";
																				String nextURL="/Admin_login.html";
																				res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
																		  }	 

			PrintWriter pw=new PrintWriter(res.getWriter());
			c_Parameters	param=new c_Parameters();	//I use separate class for passing the parameters from one fun to other
										param.req=req;
										param.res=res;
										param.pw=pw;
									
			PreparedStatement st;
			String sql;
		
			param.m_writeGetParameters(param);				// in this fuction all parameters store in hashTable
			Hashtable hashTable=param.hashTable;

	    try{
					Flight_No=(String)hashTable.get("Flight_No");
					System.out.println(req.getParameter("Flight_No")+"*****"+(String)hashTable.get("Flight_No"));

					//System.out.println("before 			Update_Flight_Master_Table(param) **********");
					pw.println("<Html><Body><Form name=Display_Modification_Fields method=GET>");
					pw.println("<br><TABLE><TR><B><u>Flight Master</U></b><TR><TR></TR></TABLE>");
					Display_Flight_Master_Table(param);
					
					//System.out.println("******before 			Update_Flight_Desc_Table(param) **********");
					pw.println("<br><TABLE><TR><B><u>Flight  Description </U></b><TR><TR></TR></TABLE>");
					Display_Flight_Desc_Table(param);
					
					//System.out.println("******before 			Update_Flight_Details_Table(param) **********");
		    		pw.println("<br><TABLE><TR><B><u>Flight Deatil</U></b><TR><TR></TR></TABLE>");
					Display_Flight_Details_Table(param);
		    		pw.println("<br><TABLE><TR><B><u><input type=button value=Save onclick=Validate(Display_Modification_Fields)></U></b><TR><TR></TR></TABLE>");
					pw.println("</Form ></Body>");
					writeScript(param);
					hashTable.put("Delete","Yes"); 
					Session.putValue("hashTable",hashTable);
	
         }catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}

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
					}catch(SQLException ignore){}
			}

		  }//finally

     }//doPost

    public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doPost(req,res);

    }//doGet()


/*******************HTML FUNCTIONS**********************/
/** NOte =:> I take it from Screen2 it same as before only in AddRecord () Http request is changed**/
public void writeScript(c_Parameters param)throws ServletException,IOException
	{
		PrintWriter pw=param.pw;
	
				pw.println("<script>");
				//function for Validate the fields
				pw.println("function Validate(form1)");
				pw.println("{");
				pw.println("	if(form1.elements[0].value=='' || form1.elements[1].value=='' || form1.elements[2].value==''  || form1.elements[3].value=='' || form1.elements[4].value<=0  )");
				pw.println("	{");
				pw.println("		alert('Value should not be empty');");
				pw.println("	}else");//ending seat number must be smaller than capacity 
										//start<end &&start>0
				pw.println("		if(	 form1.First_Cost_of_ticket.value<=0 ||	"
								+	"			form1.Economic_Cost_of_ticket.value<=0 || "
								+	"			form1.Bussiness_Cost_of_ticket.value<=0 ) ");
				pw.println("		{");						
																				pw.println("			alert('Enter Proper Ticket Cost information');");
			   pw.println("	    	} else");
				pw.println("		if( 	form1.First_Starting_SeatNo.value<=0 ||"
											+ "			form1.First_Starting_SeatNo.value>parseInt(form1.Total_capacity.value)|| "
											+	"			form1.Economic_Starting_SeatNo.value<=0 || "
											+	"			form1.Economic_Starting_SeatNo.value<=parseInt(form1.First_Ending_SeatNo.value) || 			"
											+	"			form1.Bussiness_Starting_SeatNo.value<=parseInt(form1.Economic_Ending_SeatNo.value ))");
				pw.println("		{");						
																				pw.println("alert('Starting Seat number should not be less than one OR less than privious category ending number Also not greater than Total capacity');");
//																				alert(form1.First_Starting_SeatNo.value+"="+form1.Total_capacity.value);
//																				alert(form1.Economic_Starting_SeatNo.value);
//																				alert(form1.Economic_Starting_SeatNo.value+"="+form1.First_Ending_SeatNo.value);
//																				alert(form1.Bussiness_Starting_SeatNo.value+"="+form1.Economic_Ending_SeatNo.value);

			   pw.println("	   	} else");
																		
				pw.println("		if(				form1.First_Ending_SeatNo.value>			parseInt(form1.Total_capacity.value)	||"
    										+	"			form1.Economic_Ending_SeatNo.value>	parseInt(form1.Total_capacity.value) ||"									
											+	"			form1.Bussiness_Ending_SeatNo.value>parseInt(form1.Total_capacity.value )) ");
     			pw.println("		{");						
																				pw.println("			alert(form1.First_Ending_SeatNo.value+form1.Total_capacity.value+'Starting Seat number should not be  greater than Total capacity');");
			   pw.println("	    	} else");
			
															
				pw.println("		if(	!(	parseInt(form1.First_Ending_SeatNo.value)					||				parseInt(form1.First_Starting_SeatNo.value )		|| "              
    										+	"	parseInt(form1.Economic_Ending_SeatNo.value)		||	parseInt(form1.Economic_Starting_SeatNo.value  )		|| "    								
											+	"	parseInt(form1.Bussiness_Ending_SeatNo.value)		||	parseInt(form1.Bussiness_Starting_SeatNo.value )	) )");	
     			pw.println("		{");						
																				pw.println("			alert('Please do not enter characters in place of Numbers');");

			   pw.println("	    	} else");
								
				pw.println("		if(				form1.First_Ending_SeatNo.value<				parseInt(form1.First_Starting_SeatNo.value )|| "
											+	"			form1.Economic_Ending_SeatNo.value<		parseInt(form1.Economic_Starting_SeatNo.value )|| "
											+	"			form1.Bussiness_Ending_SeatNo.value<	parseInt(form1.Bussiness_Starting_SeatNo.value ))");				
										pw.println("		{");						
																					pw.println("			alert('Ending Seat Number should not be less  than its Staring number ');");
																					pw.println("		}");
																					pw.println("		else");
																					pw.println("			{");
																					pw.println("				addRecord(form1);");
																					pw.println("			}");
																					pw.println("}");
//function for add Record in Flight_Desc
				pw.println("function addRecord(form1)");
				pw.println("{");
				pw.println("  form1.action='http://localhost:8080/servlet/Update_Flight_Master_Details_Desc_Current?Delete=Yes'	");
				pw.println("  form1.submit();");
				pw.println("}");


				pw.println("</script>");
				pw.println("</html>");
	}//writeScript()


/*********************End Html******************************/
/* other common function also writen in Screen3*/
/*	
	
	public void m_Display_Day_Month_Year(c_Parameters param )
	{
		PrintWriter pw =param.pw;
		//Day
		
		pw.print("<select name=Day size=1>"); 
		
		for(int i=1;i<=31;i++)
		{
			pw.println("<option name="+i+" id="+i+" value="+i+">"+i+"</option>");
		}//for
		
		pw.print("</select>Day");

		//Month
		
		pw.print("<select name=Month size=1>"); 
		
		for(int i=1;i<=12;i++)
		{
			pw.println("<option name="+i+" id="+i+" value="+i+">"+i+"</option>");
		}//for
		
		pw.print("</select>Month");
		
		//Year
				
		pw.print("<select name=Year size=1>"); 
		
		for(int i=2000;i<=2010;i++)
		{
			pw.println("<option name="+i+" id="+i+" value="+i+">"+i+"</option>");
		}//for
		
		pw.println("</select>Year<br>");
		
		
	}//m_Display_Day_Month_Year(c_Parameters param )
	
	public void m_Display_Weeks_with_listbox(c_Parameters param)
	{
		PrintWriter pw =param.pw;
		pw.print("<select name=Day_of_flight size=1>"); 
			pw.println("<option name=Sunday id=Sunday value=Sunday>Sunday</option>");
			pw.println("<option name=Monday id=Monday value=Monday>Monday</option>");
			pw.println("<option name=Thuesday id=Thuesday value=Thuesday>Thuesday</option>");
			pw.println("<option name=Wednesday id=Wednesday value=Wednesday>Wednesday</option>");
			pw.println("<option name=Thusday id=Thusday value=Thusday>Thusday</option>");
			pw.println("<option name=Friday id=Friday value=Friday>Friday</option>");
			pw.println("<option name=Saturday id=Saturday value=Saturday>Saturday</option>");
       pw.println("</select><br>");
		
	}
*/

			public void Display_Flight_Master_Table(c_Parameters param)
			{
																PrintWriter pw=param.pw;
								try{
														ResultSet rs;
														//Connection con=param.con;
														String sql;
														Hashtable hashTable=param.hashTable;
														System.out.println("Yes******138");
														sql="SELECT [Flight_No], [Flight_Name], [Source], [Destination], [Total_capacity], [Duration_of_flight] FROM Flight_Master where Flight_No='"+Flight_No+"'";

														PreparedStatement Flight_Master_st=con.prepareStatement(sql);
																												System.out.println("Yes******143");
														ResultSet Flight_Master_rs=Flight_Master_st.executeQuery();
														System.out.println("Yes******145");
														htmlresultset_For_Modification  obj=new htmlresultset_For_Modification(Flight_Master_rs);
														pw.println(obj.printtable());

									}catch(SQLException  e){ param.res.setContentType("text/html");
																											e.printStackTrace(param.pw);}


			}//	public void Update_Flight_Master_Table(c_Parameters param)
			public void Display_Flight_Desc_Table(c_Parameters param)
			{
													PrintWriter pw=param.pw;
					try{
														ResultSet rs;
														//Connection con=param.con;
														String sql;
														Hashtable hashTable=param.hashTable;
														sql="SELECT [Flight_No], [Category], [Cost_of_ticket], [Starting_SeatNo], [Ending_SeatNo] FROM Flight_Desc where Flight_No='"+Flight_No+"'";


														PreparedStatement Flight_Desc_st=con.prepareStatement(sql);
														ResultSet Flight_Desc_rs=Flight_Desc_st.executeQuery();

														htmlresultset_For_Modification obj =new htmlresultset_For_Modification(Flight_Desc_rs);
														pw.println(obj.printtable());


									}catch(SQLException  e){ param.res.setContentType("text/html");
																											e.printStackTrace(param.pw);}


			}//public void Update_Flight_Desc_Table(param);
			public void Display_Flight_Details_Table(c_Parameters param)
			{
													PrintWriter pw=param.pw;	
				try{								
														ResultSet rs;
														//Connection con=param.con;
														String sql;
														Hashtable hashTable=param.hashTable;
														sql="SELECT [Flight_No], [Day_of_flight], format([Date_of_Departure],'dd/mm/yy') AS Flight_Date, format([Time_of_flight],'hh:mm:ss AMPM') AS Flight_Time FROM Flight_Details WHERE Flight_No='"+Flight_No+"'";


														PreparedStatement Flight_Details_st=con.prepareStatement(sql);
														ResultSet Flight_Details_rs=Flight_Details_st.executeQuery();

														htmlresultset_For_Modification obj =new htmlresultset_For_Modification(Flight_Details_rs);
														pw.println(obj.printtable());


									}catch(SQLException  e){ param.res.setContentType("text/html");
																											e.printStackTrace(param.pw);}

			}//	public void Update_Flight_Details_Table(param)

			public void Display_Current_Details_Table(c_Parameters param)
			{
													PrintWriter pw=param.pw;	

											try{
														ResultSet rs;
														//Connection con=param.con;
														String sql;
														Hashtable hashTable=param.hashTable;
														sql="SELECT [Flight_No], [Category], [Availabe_Seats] FROM Current_Details  WHERE Flight_No='"+Flight_No+"'";


														PreparedStatement   Current_Details_st=con.prepareStatement(sql);
														ResultSet Current_Details_rs=Current_Details_st.executeQuery();

														htmlresultset_For_Modification obj =new htmlresultset_For_Modification(Current_Details_rs);
														pw.println(obj.printtable());


									}catch(SQLException  e){ param.res.setContentType("text/html");
																											e.printStackTrace(param.pw);}


			}//public void Update_Current_Details_Table(c_Parameters param)


}//class Display_FlightNumber_List

