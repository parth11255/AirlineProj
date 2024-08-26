import java.sql.*;

public class htmlresultset_For_Modification
{
   private ResultSet rs;
   int iIndex;

   //constructor
   public htmlresultset_For_Modification(ResultSet rs) 
   {
      this.rs=rs;

   }//constructor

   //printtable
   public String printtable() throws SQLException
   {
      StringBuffer sfOut =new StringBuffer();
      ResultSetMetaData rsmt= rs.getMetaData();
      int iColTotal =rsmt.getColumnCount();
      
 try{
//sfOut.append("<TABLE align=left cellpadding=0 cellspacing=0 border=0 width=568>");
//sfOut.append("<TR>");
//sfOut.append("<TD align=top valign=left>");
//sfOut.append("<TABLE align=left cellpadding=0 cellspacing=0 border=0 width=400>");
//sfOut.append("<TR>");
//sfOut.append("<TD align=top valign=left>");
sfOut.append("<TABLE bgcolor=#FFCC99 cellpadding=2 cellspacing=1 border=0 width=400>");
sfOut.append("<TR>");
sfOut.append("<TD valign=center align=center>");
sfOut.append("<TABLE bgcolor=#FFFFCC cellpadding=5 cellspacing=0 border=0 width=100%>");
sfOut.append("<TR>");
sfOut.append("<TD valign=center align=center>");
sfOut.append("<TABLE border=0 width=394 cellpadding=0 cellspacing=0>");
//sfOut.append("<TR>");
  /*    for(iIndex=1;iIndex<=iColTotal;iIndex++)
      {
         sfOut.append("<th><b>"+rsmt.getColumnLabel(iIndex)+"</b>");

      }//for(iIndex=1;iIndex<=iColTotal;iIndex++)*/

if(  rsmt.getColumnLabel(2).equalsIgnoreCase("Category"))
{
		For_Category_Table(sfOut,rsmt,iColTotal);
}
 else{
						while(rs.next()!=false)
					   {
							for(iIndex=1;iIndex<=iColTotal;iIndex++)
							{			Object o=rs.getObject(iIndex);

									   if(o!=null)
									   {
											sfOut.append("<tr>");
											 sfOut.append("<td>"+rsmt.getColumnLabel(iIndex)+"</td>");
											 if(iIndex==1)
											 {
															sfOut.append("<td><input name="+rsmt.getColumnLabel(iIndex)+"  value="+o.toString()+" disabled></td>");
											}
											else {		
															sfOut.append("<td><input name="+rsmt.getColumnLabel(iIndex)+"  value="+o.toString()+"></td>");
													  }
									   }
									   else
							 sfOut.append("<td>&nbsp</td>");
							 sfOut.append("</tr>");

							}//for(iIndex=1;iIndex<=iColTotal;iIndex++)
				//            sfOut.append("</tr>");

					  }//while(rs.next()!=false)
		}//else

      sfOut.append("</TABLE >");
	  sfOut.append("</TD>");
	  sfOut.append("</TR>");
      sfOut.append("</TABLE >");
  	  sfOut.append("</TD>");
	  sfOut.append("</TR>");
       sfOut.append("</TABLE >");
  //	  sfOut.append("</TD>");
//	  sfOut.append("</TR>");
  //     sfOut.append("</TABLE >");
  // 	  sfOut.append("</TD>");
///	  sfOut.append("</TR>");
  //     sfOut.append("</TABLE >");



 }catch(SQLException e){  sfOut.append("Error ...."+e.getMessage());}

      return sfOut.toString();

   }// public printtable()
 public String For_Category_Table(StringBuffer sfOut,ResultSetMetaData rsmt,int iColTotal)
 {
								 c_Parameters param=new c_Parameters();
								 Connection con=param.m_getConnection();
		try{
														String sql="select C_Name from Category";
														PreparedStatement Category_st=con.prepareStatement(sql);
														ResultSet Category_rs=Category_st.executeQuery();
    													String Category;
														System.out.println("in try");		
									

													while(rs.next()!=false && Category_rs.next()!=false)
												   {
														Category=Category_rs.getString(1);
														System.out.println(Category);
									
														for(iIndex=1;iIndex<=iColTotal;iIndex++)
														{			Object o=rs.getObject(iIndex);

																   if(o!=null)
																   {
																		sfOut.append("<tr>");
																		 sfOut.append("<td>"+rsmt.getColumnLabel(iIndex)+"</td>");
																		 if(iIndex==1)
																		 {
																						sfOut.append("<td><input name="+rsmt.getColumnLabel(iIndex)+"  value="+o.toString()+" disabled></td>");
																		}
																		else {	
																							if(rsmt.getColumnLabel(iIndex).equalsIgnoreCase("Category"))
																							{
																								sfOut.append("<td><input name="+Category+"  value="+o.toString()+"></td>");
																							}
																							else
																									{
																										sfOut.append("<td><input name="+Category+"_"+rsmt.getColumnLabel(iIndex)+"  value="+o.toString()+"></td>");
																									}
																				  }
																   }
																   else
														 sfOut.append("<td>&nbsp</td>");
														 sfOut.append("</tr>");

														}//for(iIndex=1;iIndex<=iColTotal;iIndex++)
											//            sfOut.append("</tr>");

												  }//while(rs.next()!=false)
		}catch(Exception e){}
					        return sfOut.toString();
 }//function

}//public class htmlresultset

