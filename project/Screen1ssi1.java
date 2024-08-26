
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
    
public class Screen1ssi1 extends HttpServlet
{
	public void init(ServletConfig Config) throws ServletException                        
	{
			super.init(Config);
	}

     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {
			System.out.println("Line=>17=Screen1ssi1");
			c_Parameters	param=new c_Parameters();
			Connection con=param.m_getConnection();
		
			param.req=req;
			param.res=res;
			param.con=con;
			param.res.setContentType("text/html");
			PrintWriter pw=new PrintWriter(res.getWriter());
			param.pw=pw;
			try{
							con.setAutoCommit(false);
							param.m_writeGetParameters(param);
							Hashtable hashTable=param.hashTable;

							String Table=(String)hashTable.get("Table1");
							String SelectName=(String)hashTable.get("SelectName1");
							
							//use this fn call for ssi here no any use of Table param in function
							if(Table!=null && Table.equals("Plane") && SelectName.equals("Flight_No"))
									m_Display_html_options_in_listbox(param,Table);	
							
							con.commit();con.close();
				
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

		public void m_Display_html_options_in_listbox (c_Parameters param,String Table)throws ServletException,IOException
		{
				PrintWriter pw=param.pw;
				try{
							String sql="select Plane_no,Plane_name from  Plane";
							PreparedStatement st=param.con.prepareStatement(sql);
							ResultSet rs       =st.executeQuery();
						

							String P_no,P_name;
						
							while(rs.next())
							{	
									P_no=rs.getString(1);P_name=rs.getString(2);
									pw.println("<br><option id="+P_no+"   value="+P_name+ " >"+P_name+"</option>");
							}
							}catch(SQLException e){  try{param.con.rollback();param.con.close(); }catch(Exception ec){System.out.println("Connection problem");}	
																		pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");
																	}

		}//m_Display_html_options_in_listbox()

}//class Screen1

