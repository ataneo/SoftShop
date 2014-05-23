<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>Untitled</title>
<script type="text/javascript" src="lib/dojo/dojo/dojo.js" data-dojo-config="'async':false,'parseOnLoad':true"></script>
<script type="text/javascript">
require([
  "dijit/form/Button",
  "dijit/form/ValidationTextBox",
  "dojox/form/Manager",
  "dojo/store/JsonRest","dijit/form/ComboBox"
]);

dojo.ready(function(){
	copyAddress = function(onOrfalse){
		var array = ["street","postOffice","policeStation","district","currentState","pin"];
		for(var item in array){
			query = "[name=shop_"+array[item]+"]";
			query1 = "[name=owner_"+array[item]+"]";
			ele = dojo.query(query);
			value = "";
			try{value = ele.attr("value")[0];}catch (e) {
				ele.attr("value","atanu");	
			}
			
			dojo.query(query1)[0].value = (onOrfalse=="on"?value:"");
				
		}
	};
});

</script>
<style>@import "themes/claro/document.css";@import "themes/claro/claro.css";@import "app.css";
</style>
</head>
<body class="claro">
	<div data-dojo-type="dojo.store.JsonRest" jsId="postofficesStore" target="/SoftShop/postoffices/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="policeStationStore" target="/SoftShop/policestations/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="districtStore" target="/SoftShop/districts/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="currentStateStore" target="/SoftShop/currentstates/"></div>
 <form method="post" data-dojo-type="dojox.form.Manager" jsId="myForm" action="/SoftShop/ShopResigtration">
 <script type="dojo/method" event="onSubmit">
	// do not submit this form
	var values = this.gatherFormValues();
	alert(dojo.toJson(values));
	if(values.repeat_password!=values.password)
	{
		alert("Repeat password does not match");
		return false;
	}
	else
		return true;
</script>	
 <fieldset style="border: 2px groove threedface; margin: 2px; padding: 0.75em; text-align: center; position: absolute; z-index: 900; width: 350px; left: 3em; top: 1em;">
   <legend style="font-weight: bold; font-size: 1.5em;">
     Shop Registration</legend>
   <div id="shop">
     <table style="width: 100%; text-align: right;">
       <tbody style="width: 100%;">
         <tr>
           <td style="width: 1%;"></td>
           <td>
            <label style="font-weight: bold;">
              Shop Name :</label>
          </td>
           <td>
             <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="shopName" autocomplete="false" onChange=""  uppercase="true" lowercase="false" propercase="false" required="required" state="Incomplete" style="width: 100%;"></input>
           </td>
           <td style="width: 1%;"></td>
         </tr>
       </tbody>
     </table>
   </div>
   <div id="shopAddress" action="/SoftShop/addresses">
     <table style="width: 100%; text-align: right;">
     <tbody style="width: 100%;">
         <tr>
           <td style="width: 1%;"></td>
           <td>
            <label style="font-weight: bold;">
              Shop Address :</label>
          </td>
           <td>
             <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="shop_street" autocomplete="false" onChange=""  uppercase="true" lowercase="false" propercase="false" required="required" state="Incomplete" style="width: 100%;"></input>
           </td>
           <td style="width: 1%;"></td>
         </tr>
         <tr>
											<td></td>
											<td><label style="font-weight: bold;"> Post
													Office :</label></td>
											<td><select data-dojo-type="dijit.form.ComboBox"
												name="shop_postOffice" store="postofficesStore" searchAttr="postOffice"
												autocomplete="false" onChange=""  uppercase="false"
												lowercase="false" propercase="false" pageSize="Infinity"
												style="width: 100%;">
											</select></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td><label style="font-weight: bold;"> Police
													Station :</label></td>
											<td><select data-dojo-type="dijit.form.ComboBox"
												name="shop_policeStation" store="policeStationStore" searchAttr="policeStation"
												autocomplete="false" onChange=""  uppercase="false"
												lowercase="false" propercase="false" pageSize="Infinity"
												style="width: 100%;">
											</select></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td><label style="font-weight: bold;"> District
													:</label></td>
											<td><select data-dojo-type="dijit.form.ComboBox"
												name="shop_district" store="districtStore" searchAttr="district" autocomplete="false"
												onChange=""  uppercase="false" lowercase="false"
												propercase="false" pageSize="Infinity" style="width: 100%;">													
											</select></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td><label style="font-weight: bold;"> State :</label></td>
											<td><select data-dojo-type="dijit.form.ComboBox"
												name="shop_currentState"   store="currentStateStore" searchAttr="currentState"
												autocomplete="false" onChange=""  uppercase="false"
												lowercase="false" propercase="false" pageSize="Infinity"
												style="width: 100%;">
											</select></td>
											<td></td>
										</tr>
         <tr>
           <td></td>
           <td>
             <label style="font-weight: bold;">
               Pin :</label>
           </td>
           <td>
             <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="shop_pin" autocomplete="false" onChange=""  uppercase="false" lowercase="false" propercase="false" required="required" regExp="^[0-9]{6}$" state="Incomplete" style="width: 100%;"></input>
           </td>
           <td></td>
         </tr>
         <tr>
           <td></td>
           <td>
             <label style="font-weight: bold;">
               E-Mail :</label>
           </td>
           <td>
             <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="shop_emailId" autocomplete="false" onChange=""  uppercase="false" lowercase="false" propercase="false" style="width: 100%;"></input>
           </td>
           <td></td>
         </tr>
         <tr>
           <td></td>
           <td>
             <label style="font-weight: bold;">
               Phone No :</label>
           </td>
           <td>
            <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="shop_mobileNumber" autocomplete="false" onChange=""  uppercase="false" lowercase="false" propercase="false" required="required" regExp="^[0-9]{10}$" state="Error" style="width: 100%;"></input>
          </td>
           <td></td>
         </tr>
         <tr>
           <td></td>
           <td style="height: 1em;"></td>
           <td></td>
           <td></td>
         </tr>
         <tr>
           <td></td>
           <td></td>
           <td></td>
           <td></td>
         </tr>
       </tbody>
     </table>
     </div>
     <label for="checkbox">Copy Address</label>
     <input id="checkbox" onChange="copyAddress(this.get('value'));" type="checkbox" data-dojo-type="dijit.form.CheckBox" intermediateChanges="false" iconClass="dijitNoIcon"></input>
   <div id="owner">
     <table style="width: 100%; text-align: right;">
       <tbody style="width: 100%;">
         <tr>
           <td style="width: 1%;"></td>
           <td>
            <label style="font-weight: bold;">
              Shop Owner Name :</label>
          </td>
           <td>
             <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="owner_name" autocomplete="false" onChange=""  uppercase="true" lowercase="false" propercase="false" required="required" state="Incomplete" style="width: 100%;"></input>
           </td>
           <td style="width: 1%;"></td>
         </tr>
       </tbody>
     </table>
   </div>
   <div id="ownerAddress" action="/SoftShop/addresses">
     <table style="width: 100%; text-align: right;">
       <tbody style="width: 100%;">
         <tr>
           <td style="width: 1%;"></td>
           <td>
            <label style="font-weight: bold;">
              Shop owner Address :</label>
          </td>
           <td>
             <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="owner_street" autocomplete="false" onChange=""  uppercase="true" lowercase="false" propercase="false" required="required" state="Incomplete" style="width: 100%;"></input>
           </td>
           <td style="width: 1%;"></td>
         </tr>
         <tr>
											<td></td>
											<td><label style="font-weight: bold;"> Post
													Office :</label></td>
											<td><select data-dojo-type="dijit.form.ComboBox"
												name="owner_postOffice" store="postofficesStore" searchAttr="postOffice"
												autocomplete="false" onChange=""  uppercase="false"
												lowercase="false" propercase="false" pageSize="Infinity"
												style="width: 100%;">
											</select></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td><label style="font-weight: bold;"> Police
													Station :</label></td>
											<td><select data-dojo-type="dijit.form.ComboBox"
												name="owner_policeStation" store="policeStationStore" searchAttr="policeStation"
												autocomplete="false" onChange=""  uppercase="false"
												lowercase="false" propercase="false" pageSize="Infinity"
												style="width: 100%;">
											</select></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td><label style="font-weight: bold;"> District
													:</label></td>
											<td><select data-dojo-type="dijit.form.ComboBox"
												name="owner_district" store="districtStore" searchAttr="district" autocomplete="false"
												onChange=""  uppercase="false" lowercase="false"
												propercase="false" pageSize="Infinity" style="width: 100%;">													
											</select></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td><label style="font-weight: bold;"> State :</label></td>
											<td><select data-dojo-type="dijit.form.ComboBox"
												name="owner_currentState"   store="currentStateStore" searchAttr="currentState"
												autocomplete="false" onChange=""  uppercase="false"
												lowercase="false" propercase="false" pageSize="Infinity"
												style="width: 100%;">
											</select></td>
											<td></td>
										</tr>
         
         <tr>
           <td></td>
           <td>
             <label style="font-weight: bold;">
               Pin :</label>
           </td>
           <td>
             <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="owner_pin" autocomplete="false" onChange=""  uppercase="false" lowercase="false" propercase="false" required="required" regExp="^[0-9]{6}$" state="Incomplete" style="width: 100%;"></input>
           </td>
           <td></td>
         </tr>
         <tr>
           <td></td>
           <td>
             <label style="font-weight: bold;">
               E-Mail :</label>
           </td>
           <td>
             <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="owner_emailId" autocomplete="false" onChange=""  uppercase="false" lowercase="false" propercase="false" style="width: 100%;"></input>
           </td>
           <td></td>
         </tr>
         <tr>
           <td></td>
           <td>
            <label style="font-weight: bold;">
              Mobile No :</label>
          </td>
           <td>
            <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="owner_mobileNumber" autocomplete="false" onChange=""  uppercase="false" lowercase="false" propercase="false" required="required" regExp="^[0-9]{10}$" state="Error" style="width: 100%;"></input>
          </td>
           <td></td>
         </tr>
         <tr>
           <td></td>
           <td style="height: 1em;"></td>
           <td></td>
           <td></td>
         </tr>
         <tr>
           <td></td>
           <td></td>
           <td></td>
           <td></td>
         </tr>
       </tbody>
     </table>
   </div>
   <div id="authentications" action="/SoftShop/Project.jsp">
     <table style="width: 100%; text-align: right;">
       <tbody style="width: 100%;">
         <tr>
           <td style="width: 1%;"></td>
           <td>
             <label style="font-weight: bold;">
               
               Admin Password :</label>
           </td>
           <td>
            <input type="password" data-dojo-type="dijit.form.ValidationTextBox" autocomplete="false" onChange=""  uppercase="false" lowercase="false" propercase="false" required="required" state="Incomplete" style="width: 100%;" name="admin_password"></input>
          </td>
           <td style="width: 1%;"></td>
         </tr>
         <tr>
           <td></td>
           <td>
             <label style="font-weight: bold;">
               
               Choose your Username :</label>
           </td>
           <td>
             <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="userName" autocomplete="false" onChange=""  uppercase="false" lowercase="false" propercase="false" required="required" state="Incomplete" style="width: 100%;"></input>
           </td>
           <td></td>
         </tr>
         <tr>
           <td></td>
           <td>
             <label style="font-weight: bold;">
               
               Create a Password :</label>
           </td>
           <td>
             <input type="password" data-dojo-type="dijit.form.ValidationTextBox" name="password" autocomplete="false" onChange=""  uppercase="false" lowercase="false" propercase="false" required="required" state="Incomplete" style="width: 100%;"></input>
           </td>
           <td></td>
         </tr>
         <tr>
           <td></td>
           <td style="font-weight: bold;">
             <label>
               
               Confirm your Password :</label>
           </td>
           <td>
             <input type="password" data-dojo-type="dijit.form.ValidationTextBox" name="repeat_password" autocomplete="false" onChange=""  uppercase="false" lowercase="false" propercase="false" required="required" state="Incomplete" style="width: 100%;"></input>
           </td>
           <td></td>
         </tr>
         <tr>
           <td></td>
           <td></td>
           <td style="text-align: left;">
             <button id="button" type="submit" data-dojo-type="dijit.form.Button" autocomplete="false" label="Log Up" iconClass="dijitNoIcon"></button>		      
           </td>
           <td></td>
         </tr>
       </tbody>
     </table>
   </div>
 </fieldset>
 </form>
 <img src="01.png" style="height: 99%; width: 99%; min-height: 600px; min-width: 800px;"></img>
</body>
</html>
