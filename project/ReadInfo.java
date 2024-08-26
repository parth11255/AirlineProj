import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import java.sql.*;

public class ReadInfo extends HttpServlet
{
	public void init(ServletConfig config) throws ServletException
	{	
		super.init(config);

	}
        public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		Hashtable hsParam=new Hashtable();
		Enumeration eHtmlFieldName;
		PrintWriter out=new PrintWriter(res.getWriter());
try{
		eHtmlFieldName=req.getParameterNames();
		while(eHtmlFieldName.hasMoreElements())
		{
			String strHtmlFieldName=(String)eHtmlFieldName.nextElement();
			hsParam.put(strHtmlFieldName,(String)req.getParameter(strHtmlFieldName));
		}

                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection con=DriverManager.getConnection("jdbc:odbc:A_pra");
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery("Select * from Flight_Master");
		ResultSetMetaData rsmd=rs.getMetaData();
		int numCols=rsmd.getColumnCount();

                System.out.println("yes");
		out.println("<table border=1 cellspacing=2 cellpadding=2>");
		while(rs.next())
		{
			out.println("<tr>");
			for(int i=1;i<=numCols;i++)
			{
				out.println("<td>"+rsmd.getColumnName(i)+"</td><td>"+rs.getString(i)+"</td></tr>");
			}
			out.println("</tr>");
                }
			out.println("</table>");
	}
catch(Exception e){e.printStackTrace(out);}
}

};




