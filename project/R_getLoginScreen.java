
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
    import java.util.*;
	import java.text.*;
    
public class R_getLoginScreen extends HttpServlet
{

     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {

			
			Enumeration eParamNames=req.getParameterNames();

			c_Parameters	param=new c_Parameters();
			//Make all of first flight search Screen fields as hidden to refer 
			//param.writeGetParametersFromScreen1(param);
			

			param.req=req;
			param.res=res;
			PrintWriter  pw=new PrintWriter(res.getWriter());
			param.pw=pw;
		
		
      try{

    		Connection con=param.m_getConnection();
          
			//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		    //Connection con   =DriverManager.getConnection("jdbc:odbc:A_pra");
		  	
				param.con=con;

				String Title       ="getLoginScreen";
				writeHead(param,Title);			

				writeBody(param);	//also include m_writeTable(param) Function for Display list  
													// At last their is function m_Make_all_them_hidden;	
													//				param.m_Make_all_them_hidden(param);
				writeScript(param);

         }//catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
      //    catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
      //    catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}
	 catch(ParseException e){System.out.println(e.getMessage());}
		  /*
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
*/
     }//doPost

			public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
			{
				doGet(req,res);
			}//doPost()
	
				public void writeHead(c_Parameters param,String Title)throws ServletException,IOException
				{

						param.res.setContentType("text/html");
						
						param.pw.println("<html><pre>");		
						param.pw.println("<Title>"+Title+"</Title>");
							
				}//writeHead()

				public void writeBody(c_Parameters param)throws ServletException,IOException,ParseException
				{
						HttpServletRequest req=param.req;
						HttpServletResponse res=param.res;
						Connection con=param.con;		
						ResultSet rs=param.rs;
						PrintWriter pw=param.pw;

						PreparedStatement st;
						String sql;
						String FileName="c:/prashant/project/login.html";

				//Start Html Page Here
						PrintWriter out=param.m_ReadFile(param,FileName);
						pw.println(out);
				//Make privious fields as hidden
			 			
						pw.println("</pre>");
						pw.println("<center><br><br><input type=button value=' Next ' name='Next' onclick='getLoginScreen(Display_Flight_List)'></center>");
						//pw.println("<center><br><br><input type=button value=' Next ' name='Next' onclick='getPassengerMasterScreen(Display_Flight_List)'></center>");
						param.m_Make_all_them_hidden(param);
					
				}//writeBody


    		public void writeScript(c_Parameters param)throws ServletException,IOException
			{
					PrintWriter pw=param.pw;
				
							pw.println("<script>");

					//function for Validate the fields
							pw.println("function getPassengerMasterScreen(form1)");
							pw.println("{");
							//pw.println("	if(form1.elements[0].value==form1.elements[1].value)    ");
							//pw.println("	{");
							//pw.println("		alert('Source & Destination should not be Same');");
							//pw.println("     }");
							//pw.println("	else");
							//pw.println("			{");
							pw.println("				form1.action='http://localhost:8080/servlet/getPassengerMasterScreen';	");
							pw.println("				form1.submit();");
							//pw.println("			}");
							pw.println("}");

							

							pw.println("function getLoginScreen(form1)");
							pw.println("{");
							//pw.println("	if(form1.elements[0].value==form1.elements[1].value)    ");
							//pw.println("	{");
							//pw.println("		alert('Source & Destination should not be Same');");
							//pw.println("     }");
							//pw.println("	else");
							//pw.println("			{");
							pw.println("				form1.action='http://localhost:8080/servlet/R_getLoginScreen';	");
							pw.println("				form1.submit();");
							//pw.println("			}");
							pw.println("}");

							pw.println("</script>");


							pw.println("</html>");
		   }//writeScript()

			
	}


