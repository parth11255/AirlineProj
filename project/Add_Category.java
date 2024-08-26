
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
	import java.util.Calendar;
	import java.text.DateFormat;
	import java.text.ParseException;
    
	public class Add_Category extends HttpServlet
	{
			public void init(ServletConfig Config) throws ServletException                        
			{
					super.init(Config);											//register the init parameters
			}
    		public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
			{
				//		var declaration
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

						String sql;
				 	
						//System.out.println("From=>Update_F_M_D_C_table");
						System.out.println("Add_plane:=>Update_Plane_Table(");
						Update_Category_Table(param);
		
						//Delete_con.commit();
						con.commit();
						Session.invalidate();
						res.sendRedirect("/inside_holidaysairways.html");
						System.out.println("End here =>Update_F_M_D_C_table");					 
						  
		}catch(SQLException e){  param.res.setContentType("text/html");	e.printStackTrace(param.pw);}
		  catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}

 }//doPost

    public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doPost(req,res);

    }//doGet()

	public void Update_Category_Table(c_Parameters param)
	{
														
														ResultSet rs;
														Connection con=param.con;
														String sql;
														Hashtable hashTable=param.hashTable;
								try{																
			
														sql="INSERT INTO Category VALUES (?,?)";
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
											
											Flight_Master_st.setString(1,(String)hashTable.get("Category_No"));
											Flight_Master_st.setString(2,(String)hashTable.get("Category_Name"));

											int Count=Flight_Master_st.executeUpdate();


						}catch(SQLException  e){ param.res.setContentType("text/html");
																											e.printStackTrace(param.pw);}

			}//	public void Update_Flight_Master_Table(c_Parameters param)
			

}//class Display_FlightNumber_List

