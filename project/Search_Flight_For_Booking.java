/* call From here=	form1.action='http://localhost:8080/servlet/Display_Flight_List   at  lineno=207*/
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.*;
	
    
public class Search_Flight_For_Booking extends HttpServlet
{

//parameter class & m_getConnection method

     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {
	  
					c_Parameters	param=new c_Parameters();
					param.req=req;
					param.res=res;
					PrintWriter pw=new PrintWriter(res.getWriter());
					param.pw=pw;
					System.out.println("****************");

	try{
				 Connection con=param.m_getConnection();
				param.con=con;
			     //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		         //Connection con   =DriverManager.getConnection("jdbc:odbc:A_pra");
		  		String Title="Search_Flight_For_Booking.java";
			

				writeHead(param,Title);			
				writeBody(param);
     			writeScript(param);

         }//catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
         // catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
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
					}catch(SQLException ignore){}
			}


		  }//finally

     }//doPost

    public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doGet(req,res);
    
	}//doPost()

/*******************HTML FUNCTIONS**********************/


	public void writeHead(c_Parameters param,String Title)throws ServletException,IOException
	{

			param.res.setContentType("text/html");
			
			param.pw.println("<html><pre>");		
			param.pw.println("<Title>"+Title+"</Title>");
				
	}//writeHead()

	public void writeBody(c_Parameters param)throws ServletException,IOException
	{
			HttpServletRequest req=param.req;
			HttpServletResponse res=param.res;
			Connection con=param.con;		
			ResultSet rs=param.rs;
			PrintWriter pw=param.pw;

			PreparedStatement st;
			String sql;
					
	         pw.println("<BODY bgcolor='#FFFFCC' link='#000000' vlink='#000000' text='#000000' marginwidth='0' marginheight='0' leftmargin='0' topmargin='0'>"); 
			 pw.println("<form name=Search_Flight_For_Booking method=GET >");
             pw.println("<table align=center bgColor=#ffcc99 border=0 cellPadding=2 cellSpacing=1 width=568><td><pre>");
             pw.println("<center><b><u><font color='#000000'> Flight Search</font></b></u></center>");
			
			try
			{

			   //City Source
					sql="select * from Location";
					st=con.prepareStatement(sql);
					rs=st.executeQuery();
		           	param.rs=rs;
					pw.print("From           ");

					pw.print("<select name=LSource >");
					for(int i=0;rs.next();i++)
					{
						String Location=rs.getString("Location_Name") ;
						pw.println("<option value='"+ Location +"' id='"+ Location +"'>" + Location +"</option>");
					}
					pw.println("</select>");

			   //City Destination
					pw.print("TO             ");

					sql="select * from Location";
					st=con.prepareStatement(sql);
					rs      =st.executeQuery();

					pw.println("<select name=LDestination size=1>");
					for(int i=0;rs.next();i++)
					{
						String Location=rs.getString("Location_Name") ;
						pw.println("<option value='"+ Location +"' id="+Location+">" + Location +"</option>");
					}
					pw.println("</select>");

					//Dispaly  Leaving date & time
					pw.print("Outbound Date  ");
			   //Lmonth list
					pw.print("</body>");    
					pw.print("<script>");

					pw.print("document.write('<select  name=LMonth Value= size=1 >');");
					pw.println("for(i=1;i<=12;i++)");
					pw.println("{");
					   pw.println("document.writeln('<option value='+i+' id='+i+' >'+ i +'</option>');");
					pw.println("}");
					pw.print("document.write('</select>');");
					pw.print("</script>");
					pw.print("<body>");    


				//Lday list
					pw.print("<select  name='LDay' size=1>");
					for(int i=1;i<=31;i++)
					{
					   pw.println("<option value="+ i +" id="+i+">" + i +"</option>");
					}
					pw.print("</select>");

					pw.print("<select name='LMorningEvening' size=1>");

					pw.println("<option value=Morning id=Morning>Morning</option>");
					pw.println("<option value=Evening id=Evening>Evening</option>");
					pw.println("</select>");
                                  
					  pw.println("Travel Class   <select  name='LCategory' size=1>");
//function call to display category from Category_table
					  param.m_Display_Category(param);

					  pw.println("</select>");

					pw.println("Number Of Traveller ");
					pw.println("            Adult<select  name='LAdult' size=1>");
					for(int i=1;i<=5;i++)
					{
					   pw.println("<option value="+ i +" id="+i+">" + i +"</option>");
					}
					pw.println("</select>");
					pw.println("            Child<select  name='LChild' size=1>");
					for(int i=0;i<=4;i++)
					{
					   pw.println("<option value="+ i +" id="+i+">" + i +"</option>");
					}
					pw.println("</select>");
					pw.println("</pre></td></table>");
					
					pw.println("<center><input type=button value=ok onclick='Validate(Search_Flight_For_Booking)'></center>");

					pw.println("</form>");
					pw.println("</pre>");
					pw.println("</body>");
				

			} catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
			  catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}
		
	}


	public void writeScript(c_Parameters param)throws ServletException,IOException
	{
		PrintWriter pw=param.pw;
	
				pw.println("<script>");

//function for Validate the fields
				pw.println("function Validate(form1)");
				pw.println("{");
				pw.println("	if(form1.elements[0].value==form1.elements[1].value)    ");
				pw.println("	{");
				pw.println("		alert('Source & Destination should not be Same');");
				pw.println("     }");
				pw.println("	else");
				pw.println("			{");
				pw.println("				form1.action='http://localhost:8080/servlet/Display_Flight_List';	");
				pw.println("				form1.submit();");
				pw.println("			}");
				pw.println("}");

				pw.println("</script>");
				pw.println("</html>");
	}//writeScript()


}//class Display_FlightNumber_List

		



/* ssi tag not allowed in clint side scripting
pw.print("<servlet code=give_category_info_me><param name='p_no' value='P1'></servlet>");
*/
/* so use applet
<applet code='applet_call_from_search_add_flight' height=200 width=100>

</applet>
*/

/* show this
             if(Hp_no!=null && Hp_name!=null)
                     rs =st.executeQuery("select c_name,Capacity,T_Cost_adult ,T_cost_child from plane_category_capacity,category,plane where Upper(category.c_no)=Upper(plane_category_capacity.c_no) and Upper(plane.p_no)=Upper(plane_category_capacity.p_no) and Upper(plane.p_name)=Upper('"+Hp_name.toUpperCase()+"')");
             else
                     rs =st.executeQuery("select C_name,Capacity,T_Cost_adult ,T_cost_child from plane_category_capacity,category where Upper(category.c_no)=Upper(plane_category_capacity.c_no) and Upper(p_no)=Upper('p1')");
*/
