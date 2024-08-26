import java.sql.*;

public class htmlresultset
{
   private ResultSet rs;
   int iIndex;

   //constructor
   public htmlresultset(ResultSet rs) 
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
      sfOut.append("<table border=1  bgcolor=#FFFFCC cellpadding=5 cellspacing=0 border=1 width=100%>");

      for(iIndex=1;iIndex<=iColTotal;iIndex++)
      {
         sfOut.append("<th align=center bgcolor=#FFCC99 ><b>"+rsmt.getColumnLabel(iIndex)+"</b>");

      }//for(iIndex=1;iIndex<=iColTotal;iIndex++)

      while(rs.next()!=false)
      {

            sfOut.append("<tr align=center>");
            for(iIndex=1;iIndex<=iColTotal;iIndex++)
            {     Object o=rs.getObject(iIndex);
               if(o!=null)
               {
                     sfOut.append("<td>"+o.toString()+"</td>");
               }
               else
                     sfOut.append("<td>&nbsp</td>");

            }//for(iIndex=1;iIndex<=iColTotal;iIndex++)
            sfOut.append("</tr>");

      }//while(rs.next()!=false)

      sfOut.append("</table >");

 }catch(SQLException e){  sfOut.append("Error ...."+e.getMessage());}

      return sfOut.toString();

   }// public printtable()

}//public class htmlresultset

