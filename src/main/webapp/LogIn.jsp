<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>Untitled</title>
<script type="text/javascript" src="lib/dojo/dojo/dojo.js" data-dojo-config="'async':true,'parseOnLoad':true,'packages':[{'name':'maqetta','location':'../../maqetta'},{'name':'gridx','location':'../gridx'},{'name':'clipart','location':'../../clipart'},{'name':'shapes','location':'../../shapes'},{'name':'jquery-ui','location':'../../jquery-ui'},{'name':'yui','location':'../../yui'}]"></script>
<script type="text/javascript">
require([
  "dijit/dijit",
  "dojo/parser",
  "maqetta/space",
  "maqetta/AppStates",
  "dijit/layout/BorderContainer",
  "dijit/layout/ContentPane",
  "dijit/form/Button",
  "dijit/Dialog",
  "dijit/form/ValidationTextBox",
  "dijit/form/CheckBox",
  "dijit/layout/TabContainer",
  "dijit/TitlePane"
]);
</script>
<style>@import "themes/claro/document.css";@import "themes/claro/claro.css";@import "app.css";
</style>
</head>
<body class="claro" dvFlowLayout="true" data-davinci-ws="collapse" dvStates="{'_show:dijit_Dialog_10':{'origin':true}}" id="myapp">
 <span data-dojo-type="dijit.TitlePane" id="logIn" title="Log In" extractContent="false" preventCache="false" preload="false" refreshOnShow="false" duration="200" open="true" style="min-width: 1em; position: absolute; z-index: 900; left: 6px; top: 6px; width: 200px;" class="logIn">
   <form method="post" action="/SoftShop/Login">
   
   <table style="width: 100%; text-align: left; font-weight: bold;">
     <tbody>
       <tr>
         <td style="width: 1%;"></td>
         <td>
           <label>
             Username</label>
         </td>
         <td style="width: 1%;"></td>
       </tr>
       <tr>
         <td></td>
         <td>
           <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="userName" intermediateChanges="false" trim="false" uppercase="false" lowercase="false" propercase="false" required="required" state="Incomplete" style="width: 100%;" value=${userName}></input>
         </td>
         <td></td>
       </tr>
       <tr>
         <td></td>
         <td>
           <label>
             Password</label>
         </td>
         <td></td>
       </tr>
       <tr>
         <td></td>
         <td>
           <input type="password" data-dojo-type="dijit.form.ValidationTextBox" name="password" intermediateChanges="false" trim="false" uppercase="false" lowercase="false" propercase="false" required="required" state="Incomplete" style="width: 100%;"></input>
         </td>
         <td></td>
       </tr>
       <tr>
         <td style="color: red;" colspan="3">${error}</td>
       </tr>
       <tr>
         <td></td>
         <td>
           <input type="checkbox" data-dojo-type="dijit.form.CheckBox" intermediateChanges="false" iconClass="dijitNoIcon"></input>
           <label style="font-weight: normal;">
             Stay Logged In</label>
         </td>
         <td></td>
       </tr>
       <tr>
         <td></td>
         <td>
           <input type="submit" data-dojo-type="dijit.form.Button" intermediateChanges="false" label="Log In" iconClass="dijitNoIcon"></input>
         </td>
         <td></td>
       </tr>
     </tbody>
   </table>
 </form>
 
 </span><img src="01.png" style="height: 99%; width: 99%; min-height: 600px; min-width: 800px;"></img>
 </body>

</html>
    