
	import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
	import java.util.Calendar;
	import java.text.DateFormat;
	import java.text.ParseException;

public class Display_XXXNumber_in_Listbox	 extends HttpServlet
{
     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {
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
					
					// if  call is for Delete Selected Flight for delete all entry ,url have paramter   "Delete_SelectedFlight=Yes;"	
					System.out.println("Table=>"+(String)hashTable.get("Table"));
					System.out.println("Field_Atrribute=>"+(String)hashTable.get("Field_Atrribute"));
					
					String Table=(String)hashTable.get("Table");
					String Field_Atrribute=(String)hashTable.get("Field_Atrribute");
   
		            //Connection con =DriverManager.getConnection("jdbc:odbc:pra","ssi","ssi");
					Statement st=con.createStatement();
					
					if(Table!=null || Field_Atrribute!=null)
					{
							 st.executeQuery("select  "+Field_Atrribute+"  from  "+Table);
							 ResultSet rs =st.getResultSet();
							 //Start Html Page Here
		
							 pw.println("<select name='"+Field_Atrribute+"'>");
	  					     while(rs.next())
							 {
										String Name= rs.getString(Field_Atrribute) ;
										pw.println("<option value='" +Name+ "'>" + Name+ " </option>");
							  }
							 pw.print("</select> ");

					   }//if
					
					else
							{System.out.println("Display_XXXNumber...=>parameters are empty");}

         }//catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
          catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
          catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}

     }//doPost

}//class Display_FlightNumber_List
