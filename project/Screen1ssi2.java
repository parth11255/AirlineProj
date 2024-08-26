
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
    
public class Screen1ssi2 extends HttpServlet
{
	String flagFire;

	public void init(ServletConfig Config) throws ServletException                        
	{
			super.init(Config);
			flagFire=null;
	}

     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {
			System.out.println("Line=>20=Screen1ssi2");
			
			Connection con;
			c_Parameters	param=new c_Parameters();
			con=param.m_getConnection();

			param.req=req;
			param.res=res;
			param.con=con;
			param.res.setContentType("text/html");
			PrintWriter pw=new PrintWriter(res.getWriter());
			param.pw=pw;
			try{			

									param.m_writeGetParameters(param);
									Hashtable hashTable=param.hashTable;

									String Table=(String)hashTable.get("Table2");
									String SelectName=(String)hashTable.get("SelectName2");
									String LSource=(String)hashTable.get("LSource");
										
if(flagFire==null) 
{
	flagFire="FirstSsi";

}else flagFire=null;
//note if flagfire <> null then 1st ssi call else 2nd ssi call
									System.out.println("flagFire	 :"+flagFire);

									if(( Table!=null )&& Table.equals("Location") && SelectName.equals("Location"))
												Display_Location(param);
							
									
									con.close();

			}catch(Exception e){ try{param.con.rollback();param.con.close(); }catch(Exception ec){System.out.println("Connection problem");}	
												    param.res.setContentType("text/html");
													e.printStackTrace(param.pw);
													System.out.println("in main catch");
												}

		 
     }//doGet()

    public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doGet(req,res);

    }//doPost()

  	public void Display_Location(c_Parameters param)throws ServletException,IOException
	{
				PrintWriter pw=param.pw;
				Hashtable hashTable=param.hashTable;

				String		Table=(String)hashTable.get("Table2");
				String		SelectName=(String)hashTable.get("SelectName2");
				String		LSource=(String)hashTable.get("LSource");


				try{		
							
							String	sql="select Location_Name from  Location";

							if(LSource!=null && (! LSource.equals("Source")) && flagFire==null)
							{//when not 1st time called or when not call by selecting 1st option
							//fire for Second time call that time only for second ssi call it true

									 sql="SELECT DISTINCT   [Destination]  "
									 +" FROM Flight_Master AS T1 "
									 +" WHERE T1.Source='"+LSource+"'";

									 System.out.println("Destination Sql");
								
							}

							PreparedStatement st=param.con.prepareStatement(sql);
							ResultSet rs       =st.executeQuery();
		

							String Location;
					
							while(rs.next())
							{	
									Location=rs.getString(1);
									if(LSource!=null && (! LSource.equals("Source")) && Location.equals(LSource) &&  flagFire!=null )
									{//when not 1st time called or when not call by selecting 1st option
										pw.println("<br><option  id="+Location+"   value="+Location+"  Selected>"+Location+"</option>");
									}else{
											pw.println("<br><option  id="+Location+"   value="+Location+" >"+Location+"</option>");
										}
						}
					 rs.close();

				}catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}

		}//Display_Location()

}//class Screen1

