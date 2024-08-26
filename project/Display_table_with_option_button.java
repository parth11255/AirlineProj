import java.sql.*;

public class Display_table_with_option_button
{
   private ResultSet rs;
   int iIndex;

   //constructor
   public Display_table_with_option_button(ResultSet rs) 
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
      sfOut.append("<table border=1>");
      for(iIndex=1;iIndex<=iColTotal;iIndex++)
      {
         sfOut.append("<th><b>"+rsmt.getColumnLabel(iIndex)+"</b>");

      }//for(iIndex=1;iIndex<=iColTotal;iIndex++)

      while(rs.next()!=false)
      {
			
			sfOut.append("<tr>");
            for(iIndex=1;iIndex<=iColTotal;iIndex++)
            {     Object o=rs.getObject(iIndex);
               if(o!=null)
               {
					//print option button for each record
					String tempVar=o.toString();
		
					if(iIndex==1) sfOut.append(" <TD> <input type=radio name=SelectedFlight value='"+tempVar+"' >"+tempVar +"</td>");
					else
                     sfOut.append(" <td>"+tempVar+"</td>");
               }
               else
                     sfOut.append(" <td>&nbsp</td>");

            }//for(iIndex=1;iIndex<=iColTotal;iIndex++)
            sfOut.append("</tr>");

      }//while(rs.next()!=false)

      sfOut.append("</table >");

 }catch(SQLException e){  sfOut.append("Error ...."+e.getMessage());}

      return sfOut.toString();

   }// public printtable()

}//public class htmlresultset

