

import	javax.servlet.http.*;
import	javax.servlet.*;
import	java.io.*;
import	java.sql.*;
import	java.util.*;
    
class c_Parameters
{

		HttpServletRequest			req;
		HttpServletResponse		res;
		HttpSession						Session;
		ResultSet						rs;
		Connection						con;
		Hashtable						hashTable;
		PrintWriter						pw;
	

	public Connection m_getConnection()
    {
			
			Connection			con=null;
			try{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					con =DriverManager.getConnection("jdbc:odbc:A_pra");
				}
				catch(ClassNotFoundException e){pw.print(e.getMessage()+"<html><body>Class error... </body></html>");}
				catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}

	        return con;

    }//m_getConnection()

	public Hashtable copy_hashTable_To_hashTable(Hashtable Source,Hashtable Destination)
	{
		if(Source!=null)
		{
				Enumeration eKeyNames=Source.keys();
				while( eKeyNames.hasMoreElements() )
				{
					String strNames=(String)eKeyNames.nextElement();
					Destination.put(strNames,Source.get(strNames));
				}//while
		}//if
		else 
				System.out.println("C_parameter:=> Source or Destination is null");

		return Destination;

	}//copy_hashTable_To_hashTable

	public void m_writeGetParameters(c_Parameters param)
	{
				HttpServletRequest		req=param.req;
				Enumeration			eParamNames=req.getParameterNames();
				Hashtable			hashTable=new Hashtable();

				while( eParamNames.hasMoreElements() )
				{
					String	Names=(String)eParamNames.nextElement();

					hashTable.put( Names, req.getParameter(Names) ); 
					System.out.println(Names+"="+req.getParameter(Names));
				}
				param.hashTable=hashTable;
				
				/*
			  //it return the value attribute of select cnt. which is our FlightName
			  String Flight_No       =req.getParameter("Flight_No");
			  String Flight_Name     =req.getParameter("Flight_Name");
			  String Source          =req.getParameter("Source");
			  String Destination     =req.getParameter("Destination");
			  String Total_capacity  =req.getParameter("Total_capacity");
			  String Duration_of_flight=req.getParameter("Duration_of_flight");
				*/
	}//m_writeGetParameters

	public void m_Make_all_them_hidden(c_Parameters param)
	{
					Enumeration		eParamNames=param.req.getParameterNames();
					PrintWriter		pw=param.pw;

					pw.println("<!			Hidden fields starts			-->"); 
					while( eParamNames.hasMoreElements() )
					{
						String Names=(String)eParamNames.nextElement();

						pw.println("<input type=hidden name="+Names+"  value="+req.getParameter(Names)+">"); 
						//System.out.println(Names+"="+req.getParameter(Names));
					}//while
					pw.println("<!			Hidden fields Ends			-->"); 

	}//m_Make_all_them_hidden(param);


	public void m_Display_Category(c_Parameters param) throws ServletException,IOException
	{
			String				sql;		
			ResultSet			rs=param.rs;
			PrintWriter			pw=param.pw;
			PreparedStatement		st;
			Connection			con=param.con;

			try{
				sql="select C_Name from Category";
 				st=con.prepareStatement(sql);
				rs=st.executeQuery();

							String Category;
				
							while( rs.next() )
							{	
									Category=rs.getString(1);
									pw.println("<br><option name="+Category+" value="+Category+">"+Category+"</option>");
							}
			    }catch(SQLException e){  pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}

	}//Display_Category()


	public PrintWriter m_ReadFile(c_Parameters param,String FileName)throws ServletException, IOException
	{
					ResultSet	rs=param.rs;
					PrintWriter	out=new PrintWriter(param.res.getWriter());

						//StringBuffer Buffer=new StringBuffer();
					//for ex: FileName="c:/prashant/project/test.html"
					//System.out.println("File name=>"+FileName);
					try
					{

						BufferedReader in=new BufferedReader(new InputStreamReader(	new  FileInputStream("c:/prashant/project/login.html") ) );
					
						while(true)
						{
							String strBuffer=in.readLine();

							if(strBuffer==null) 
								break;
							//System.out.println(strBuffer);
							out.println(strBuffer);

						}//while

					}catch(FileNotFoundException e){System.out.println(e.getMessage());}//catch(SQLException e){pw.print(e.getMessage()+"<html><body>SQL Error</body>"+e.getSQLState()+"</html>");}

					return out;		
				
	}//m_ReadFile

	public void check_Session_isValid_and_Copy_Session_hashTable(c_Parameters param ) throws IOException
	{
			HttpServletRequest		req=param.req;
			HttpServletResponse	res=param.res;
			PrintWriter			pw=param.pw;
			HttpSession			Session=param.Session;
			ResultSet			rs=param.rs;
			Connection			con=param.con;
			Hashtable			hashTable=param.hashTable;



		//check isValid
		try{
			if(Session==null)
			{
				System.out.println("c_parameters=>175");
				String	myMsg="Sorry,login_id session expired ,Try to login again";
				String	nextURL="/Admin_login.html";

				res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);

				return;
			}//if

			String	login_id=(String)Session.getValue("login_id");
			String	password=(String)Session.getValue("password");


			if( login_id==null || password==null || login_id.length()==0 || password.length() ==0 )
			{
				String myMsg="Sorry,login_id session expired ,Try to login again";
				String nextURL="/Admin_login.html";

				res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
				return;
			}//if

			//copy all variable of session in param
			Object ObjecthashTable =Session.getValue("hashTable");

			if(ObjecthashTable !=null)
			{
					Hashtable Session_hashTable=(Hashtable) ObjecthashTable;
					hashTable=param.copy_hashTable_To_hashTable(Session_hashTable,hashTable );

			}else {System.out.println("C_parameters:=>Session_hashTable is null"); }


		System.out.println(login_id);
		System.out.println(password);
		}//try.								
		catch(Exception e){	
					param.res.setContentType("text/html");
					e.printStackTrace(param.pw);
					System.out.println("in catch");		
					/*String myMsg="Sorry,Wrong login_id or may Password ,Try again";
					String nextURL="/Admin_login.html";
					res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);*/
							
	     } //catch
	
	}//public void check_Session_isValid(param)

	public void check_Session_isValid_For_User_and_Copy_Session_hashTable(c_Parameters param ) throws IOException
	{
			HttpServletRequest		req=param.req;
			HttpServletResponse	res=param.res;
			PrintWriter			pw=param.pw;
			HttpSession			Session=param.Session;
			ResultSet			rs=param.rs;
			Connection			con=param.con;
			Hashtable			hashTable=param.hashTable;

				//check isValid
				try{							
						if(Session==null)
						{
										System.out.println("c_parameters=>160");
										String myMsg="Sorry,login_id session expired ,Try to login again";
										String nextURL="/frontpage.html";
										res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
										return;

						}//if

					//String login_id=(String)Session.getValue("login_id");
					//String password=(String)Session.getValue("password");
					//if(login_id==null || password==null || login_id.length()==0|| password.length()==0)
					//{
					//		String myMsg="Sorry,login_id session expired ,Try to login again";
					//		String nextURL="/Admin_login.html";
					//		res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
					//		return;
					//}
					//copy all variable of session in param


						Object ObjecthashTable =Session.getValue("hashTable");
						if(ObjecthashTable !=null)
						{
								Hashtable Session_hashTable=(Hashtable) ObjecthashTable;
								hashTable=param.copy_hashTable_To_hashTable(Session_hashTable,hashTable );
						}else {System.out.println("C_parameters:=>Session_hashTable is null"); }

					//System.out.println((String)Session_hashTable.get("Flight_No"));
					//System.out.println((String)Session_hashTable.get("Flight_Name"));
							
				}catch(Exception e){/*	param.res.setContentType("text/html");
						e.printStackTrace(param.pw);
						System.out.println("in catch");		*/
						String myMsg="Sorry,Wrong login_id or may Password ,Try again";
						String nextURL="/frontpage.html";
						res.sendRedirect("http://localhost:8080/servlet/Error_page?msg="+myMsg+"&nextURL="+nextURL);
						return;
						} //catch

	}//public void check_Session_isValid(param)

	public void Delete_TableEntry(c_Parameters param,String Table,String Delete_Flight_No)
	{
				ResultSet			rs;
				Connection			con=param.con;
				//Here user separate connection call Delate_Con it set as Autocommit=false  //globe variable 
				String				sql;
				PreparedStatement		st;
				Hashtable			hashTable=param.hashTable;
				int					Count;

				try{																
	
							sql="Delete From "+Table +"  where Flight_No='"+Delete_Flight_No+"'";
							st=con .prepareStatement(sql);
							Count=st.executeUpdate();
//				System.out.println("Yes line=439");
							st.close();

				}catch(SQLException  e){ param.res.setContentType("text/html");
									e.printStackTrace(param.pw);}
	}//	public void Delete_TableEntry(c_Parameters param,String Table,String Delete_Flight_No)

}//c_Parameters

