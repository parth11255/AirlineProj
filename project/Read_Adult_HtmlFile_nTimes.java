/*call from:=R_Display_total_Number_of_Passenger_ScreensFrames.java		 =>by ServletContext
Read Adult passenger html file and add it to R_Display_total_Number_of_Passenger_ScreensFrames*/

    import javax.servlet.http.*;
    import javax.servlet.*;
    import java.io.*;
    import java.sql.*;
	import java.util.Hashtable;
    
public class Read_Adult_HtmlFile_nTimes extends HttpServlet
{

	public void init(ServletConfig Config) throws ServletException                        
	{
			super.init(Config);

	//param.hashTable.clear();//make empty hashTable
			
	}

     public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
     {
						System.out.println("u rein:=>R_Display_total_Number_of_Passenger_ScreensFrames");
						PrintWriter out=new PrintWriter(res.getWriter());
		
						//Session tracking functio from c_Parameter

						 //Read file
						 BufferedReader in =new BufferedReader(new FileReader("/R_Adult_passenger_Detail.html"));
						for(;;)
							{
								String line =in.readLine();
								if(line==null)
									break;
								out.println(line);
							}
							in.close();
							out.flush();

     }//doGet()

    public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
    {
		doGet(req,res);

    }//doPost()

}//class Screen1

