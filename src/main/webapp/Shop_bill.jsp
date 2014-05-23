<%@page import="org.nath.model.BillProduct"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="org.nath.model.Bill"%>
<%@ page import="org.nath.util.EnglishNumberToWords"%> 
<% Bill bill = (Bill)session.getAttribute("bill");%>
<html>
<head>
<title>Shop_Bill</title>
</head>

<body>
 <div style="border: 3px solid black; width: 800px; height: 500px; vertical-align: top;">
  <table style="border-collapse: collapse; table-layout: fixed; width: 100%; height: 100%;" border="2">
    <tbody>
      <tr>
        <td colspan="2" style="width: 100% height: 20%;">
        <img src="heder_bill.jpg" width="100%" height="100" border="0" alt=""> 
		</td>
      </tr>
      <tr>
        <td style="width: 40%;">
          <table border="0" style="border-collapse: collapse; table-layout: auto; width: 100%; height: 100%; ">
            <tbody>
              <tr><td>
              <table style="">
              	<colgroup>
              	<col style="text-align: right; width: 30%; white-space: nowrap;">
              	<col style="text-align: left;">
              	</colgroup>
              	<tr>
              		<td>              		
              			Bill No :
              		</td>
              		<td  >
              			${bill.id}
              		</td>
              	</tr>
              	<tr>
              		<td >
              			Cust I.D:
              		</td>
              		<td  >
              			${bill.customer.id}
              		</td>
              	</tr>
              	<tr>
              		<td >
              			Name :
              		</td>
              		<td  >
              			${bill.customer.name}
              		</td>
              	</tr>
              	<tr>
              		<td >
              			Address:
              		</td>
              		<td  >
              			${bill.customer.address.postOffice.postOffice} ,&nbsp; ${bill.customer.address.policeStation.policeStation}
              		</td>
              	</tr>
              	<tr>
              		<td >
              			Phone :
              		</td>
              		<td  >
              			${bill.customer.address.mobileNumber}
              		</td>
              	</tr>
              	<tr>
              		<td >
              			Date :
              		</td>
              		<td  >
              			${bill.billDate}
              		</td>
              	</tr>
              </table>
              
              
              	
                
				
				
				
				</td>
              </tr>
              <tr>
                <td colspan="2" style="border: 1px solid black;">
                  <div>Job Details :</div>
				</td>
	             </tr>
            </tbody>
          </table>
        </td>
        <td style="width: 60%;">
         <table style="border-collapse: collapse; table-layout: fixed; width: 100%; height: 100%;" border="1">
           <colgroup>
             <col></col>
             <col></col>
             <col></col>
             <col></col>
           </colgroup>
           <tbody>
             <tr style=" text-align: center; font-size: 15pt;">
               <td style="width: 10%; height: 10%;">
                 Sl.</td>
               <td style="width: 50%;">
                 Particulars</td>
               <td style="width: 15%;">
               Count</td>
               <td style="width: 25%;">
                 Price</td>
             </tr>
             <tr>
               <td colspan="4" style="width: 100%;">
                 <table style="border-collapse: collapse; table-layout: fixed; width: 100%; height: 100%;" border="0" align="top">
<colgroup>
<col style="border: 1px solid black;"></col>
<col style="border: 1px solid black;"></col>
<col style="border: 1px solid black;"></col>
<col style="border: 1px solid black;"></col>
</colgroup>
<tbody>

<%
	for(int i=0;i<bill.getBillProduts().size();i++){
		BillProduct billProduct = bill.getBillProduts().get(i);
%>


<tr valign="top">
<td style="width: 10%; text-align: center;">
  <%=(i+1)%></td>
<td style="width: 50%;">
  <%=billProduct.getItem().getName()%>,<%=billProduct.getCategory().getName()%></td>
<td style="width: 15%; text-align: center;">
<%=billProduct.getCount()%></td>
<td style="width: 25%; text-align: right;">
  <%=billProduct.getAmount()%></td>
</tr>

<%} %>
</tbody>
</table>
</td>
             </tr>
             <tr>
               <td style="height: 15%;" colspan="2">
                 </td>
               <td style="text-align: center; font-size: 1em;">
                 Total</td>
               <td style="text-align: right; font-size: 1em; ">
                 ${bill.amount}</td>
             </tr>
           </tbody>
         </table>
       </td>
      </tr>
      <tr>
        <td colspan="2" style="width: 100%; height: 15%;">
        <div style="">
         <label>
         Rupees in words :</label>
         <label><%=EnglishNumberToWords.convert(Math.round(bill.getAmount())).toUpperCase()%></label>
         <label>
           ONLY.</label>
       </div>
          <div style="text-align: center;">
           <table style="border-collapse:collapse;table-layout:fixed;width:100%;height:auto;">
             <tbody>
               <tr>
					<td style="width:33%;">
				    <label>Delivery on :</label>
					<label>${bill.delivaryDate}</label>
					</td>
					<td style="width:33%;text-align: center;">
						< label>Advance :</label>
						<label>${bill.paidAmount}</label>
					</td>
					<td style="width:33%;text-align: center;">
						<label>Balance :</label>
						
						<label>${bill.customer.balance}</label>
					</td>
				</tr>
				<tr>
					<td style="height: 2em;"></td>
					<td></td>
					<td></td>
               </tr>
               <tr>
                 <td>
                   <label>Due Date :</label>
					<label>${bill.duePromiseDate}</label>
				 </td>
                 <td style="border: 2px solid black;"><center><label>***** Visit Again *****</label></center></td>
                 <td style="text-align: center;">
					<label>Signature</label>
				</td>
              </tr>
             </table>
           </div>
			<div>N.B. : "The delivery may be delayed due to technical problem or power failure."
			</div>
         </td>
        </tr>
      </tbody>
	</table>
  </div>
</body>
</html>