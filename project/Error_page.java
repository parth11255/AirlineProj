
    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
    
public class Error_page extends HttpServlet
{

     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {
				c_Parameters	param=new c_Parameters();
				PrintWriter pw=new PrintWriter(res.getWriter());
				
		
			try{
						res.setContentType("text/html");
						param.res=res;
						param.pw=pw;

			
						System.out.println(HttpUtils.getRequestURL(req));
						System.out.println(req.getRequestURI());

						String myMsg=req.getParameter("msg");
						String nextURL=req.getParameter("nextURL");
						String msgflag=req.getParameter("msgflag");

						if(msgflag==null)
						{
							msgflag="Error ";

						}else {msgflag="Message ";}
				
						System.out.println(myMsg);

	

						//	 myMsg=(String)hashTable.get("msg");
					   // 	String nextURL=(String)hashTable.get("nextURL");
						

						//System.out.println((String)hashTable.get("password"));
						//System.out.println((String)hashTable.get("login_id"));
						pw.println("<html>");
					/*	pw.println("<body");
						pw.println("<pre>"+myMsg);
						pw.println("<br><br>						<a href='"+nextURL+"'	 target='f3'><h2>go_Back</h2></a>");
						pw.println("										<a href='http://localhost:8080/inside_holidaysairways.html'	 target='f3'><h2>go_Home</h2></a>");
						pw.println("</pre>");
						pw.println("</html>");*/


						pw.println("<BODY BGCOLOR=#FFFFFF>");
						pw.println("	<TABLE bgcolor=#FFCC99 cellpadding=2 cellspacing=1 border=0 width=400>		  "); 
						pw.println("	<TR><TD valign=center align=center>																		  "); 
						pw.println("	<TABLE bgcolor=#FFFFCC cellpadding=5 cellspacing=0 border=0 width=100%>	  "); 
						pw.println("	<TR><td align=left><pre>"+msgflag+" : </pre><br></td><TD valign=center align=center>																		  "); 
																																												
						pw.println("<pre>");																															  
						pw.println("<FONT face=Verdana,Arial,Helvetica  color=#FF0033 size=3>	"+myMsg+"</FONT> ");
						pw.println("</pre>");
						pw.println("<TABLE border=0 width=394 cellpadding=0 cellspacing=0></TABLE >	"); 
						pw.println("</TD></TR></TABLE>																				"); 
						pw.println("</TD></TR></TABLE >																				"); 
						pw.println("<Table align=center cellspacing=0 Valign=center border=0 cellpadding=14><Th> <td> <input type=button value=' Back ' onclick=goBack()>	</td><td>		<input type=button value=' Abort '  onclick=Abort_action('Screen3')></td>		<td><input type=button value=' Home ' onclick=goHome() >  </td> </table>");


						pw.println("</BODY>");
						pw.println("<script>");
						pw.println("function goHome()");
						pw.println("{");

						pw.println("window.navigate('http://localhost:8080/inside_holidaysairways.html');");
						pw.println("}");
						pw.println("function goBack()");
						pw.println("{");

						pw.println("window.navigate('"+nextURL+"');");
						pw.println("}");
						pw.println("function Abort_action(form1)																		");
						pw.println("{																														");
						pw.println("	window.navigate('http://localhost:8080/inside_holidaysairways.html');		");
						pw.println("}																														");


						pw.println("</script>");
						pw.println("</HTML>");

	 }//catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
        //catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}
        catch(NumberFormatException e){ pw.print(e.getMessage()+"<html><body> Number Format Error...</body></html>");}

     }//doGet()

    public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doGet(req,res);

    }//doPost()

}//class Screen1

