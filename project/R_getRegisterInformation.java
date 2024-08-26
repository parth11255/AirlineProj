
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
	import java.util.Calendar;
	import java.text.DateFormat;
	import java.text.ParseException;
   // R_getRegisterInformation
public class R_getRegisterInformation extends HttpServlet
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
    					System.out.println("u rein:=>R_getRegisterInformation");
						PrintWriter pw=new PrintWriter(res.getWriter());
						param.pw=pw;
				try{		
						//Session tracking functio from c_Parameter
						 Session=req.getSession(false);
						 if(Session==null)
						 {
														res.sendRedirect("/frontpage.html");Session.invalidate();return;
						 }
						 param.Session=Session;
						 //function from c_Parameter
						 param.check_Session_isValid_For_User_and_Copy_Session_hashTable(param );
						 Session.putValue("hashTable",hashTable);
						 res.sendRedirect("/R_all_passenger_detail.html");
						//System.out.println("End here =>Update_F_M_D_C_table");					 
						  
			}//catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
					//catch(SQLException e){  param.res.setContentType("text/html");	e.printStackTrace(param.pw);}
					  catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}

 }//doPost

    public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doPost(req,res);

    }//doGet()

}//class Display_FlightNumber_List

