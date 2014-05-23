<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div data-dojo-type="dijit.layout.ContentPane" title="Account" jsId="accountPane"
					extractContent="false" preventCache="false" preload="false"
					refreshOnShow="false" doLayout="true" closable="false"
					splitter="false" maxSize="Infinity">
					<span data-dojo-type="dijit.layout.AccordionContainer"
						duration="200" persist="false" jsId="accountAccordian"
						style="min-width: 1em; min-height: 1em; width: 100%; height: 100%; text-align: center;"><div
							data-dojo-type="dijit.layout.ContentPane" jsId="customerAccountContainer"
							title="Customer Account" extractContent="false"
							preventCache="false" preload="false" refreshOnShow="false"
							doLayout="true" closable="false" splitter="false" onShow=" try{if(customerAccountGridGrid)customerAccountGridGrid.render();}catch(e){}"
							maxSize="Infinity">	
							<h3 id="custName"></h3>					
							
						</div>
						<div data-dojo-type="dijit.layout.ContentPane"
							title="Employee Account" extractContent="false"
							preventCache="false" preload="false" refreshOnShow="false"
							doLayout="true" closable="false" splitter="false"
							maxSize="Infinity">
							
						</div>
						<div data-dojo-type="dijit.layout.ContentPane"
							title="Shop Account" extractContent="false" preventCache="false"
							preload="false" refreshOnShow="false" doLayout="true" onShow="initShopAccountGrid();"
							selected="selected" closable="false" splitter="false"
							maxSize="Infinity">
							
							<% 
								SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								String startDatestring = formatter.format(new Date());
								System.out.print(startDatestring);
								
							%>
							<div>
							<label for="startDateId" style="text-align: left;">Start Date</label>
							<input id="startDateId" type="text" name="startDate" jsId="startDate" value="<%=startDatestring%>"
									data-dojo-type="softShop.DateTextBox"></input>
							<label for="endDateId" style="text-align: left;">End Date</label>
							<input id="endDateId" type="text" name="endDate" jsId="endDate" value="<%=startDatestring%>"
									data-dojo-type="softShop.DateTextBox"></input></div>
							
							<div id="ShopAccountGrid" style="height:80%"></div>
							
						</div> 
						<span title="Permanent Entry" data-dojo-type="dijit.layout.ContentPane"
							extractContent="false" preventCache="false"
							preload="false" refreshOnShow="false" doLayout="true"
							selected="selected" closable="false" splitter="false"
							maxSize="Infinity"><span
								data-dojo-type="dijit.layout.TabContainer" tabStrip="false"
								persist="false" controllerWidget="dijit.layout.TabController"
								style="min-width: 1em; min-height: 1em; width: 100%; text-align: center; font-weight: bold; height: 100px;"><div
										data-dojo-type="dijit.layout.ContentPane"
										title="Entry Expenditure" extractContent="false"
										preventCache="false" preload="false" refreshOnShow="false"
										doLayout="true" selected="selected" closable="false"
										splitter="false" maxSize="Infinity">
										<table style="width: 100%; text-align: center;">
											<tbody>
												<tr style="font-weight: bold;">
													<td><label> Date</label></td>
													<td><label> Time</label></td>
													<td><label> Detiles</label></td>
													<td><label> Amount</label></td>
													<td></td>
												</tr>
												<tr>
													<td><label> 06/26/2012</label></td>
													<td><label> 11:45</label></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														intermediateChanges="false" trim="false" uppercase="false"
														lowercase="false" propercase="false" required="required"
														state="Incomplete" style="width: 90%;"></input></td>
													<td><input type="text"
														data-dojo-type="dijit.form.CurrencyTextBox"
														intermediateChanges="false" trim="false" uppercase="false"
														lowercase="false" propercase="false" required="required"
														currency="Rs. "
														regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
														state="Error" style="width: 10em;"></input></td>
													<td style="text-align: left;"><input type="button"
														data-dojo-type="dijit.form.Button"
														intermediateChanges="false" label="Entry"
														iconClass="dijitNoIcon"></input></td>
												</tr>
											</tbody>
										</table>
									</div>
									<div data-dojo-type="dijit.layout.ContentPane"
										title="Pay to Employee" extractContent="false"
										preventCache="false" preload="false" refreshOnShow="false"
										doLayout="true" closable="false" splitter="false"
										maxSize="Infinity">
										<table style="width: 100%; text-align: center;">
											<tbody>
												<tr style="font-weight: bold;">
													<td><label> Date</label></td>
													<td><label> Time</label></td>
													<td><label> Employee ID</label></td>
													<td><label> Employee Name</label></td>
													<td><label> Detiles</label></td>
													<td><label> Amount</label></td>
													<td></td>
												</tr>
												<tr>
													<td><label> 06/26/2012</label></td>
													<td><label> 11:45</label></td>
													<td><select
														data-dojo-type="dijit.form.FilteringSelect"
														value="XXX001E" intermediateChanges="false" trim="false"
														uppercase="false" lowercase="false" propercase="false"
														required="required" pageSize="Infinity">
															<option value="XXX001E">XXX001E</option>
															<option value="XXX002E">XXX002E</option>
															<option value="XXX003E">XXX003E</option>
													</select></td>
													<td><select
														data-dojo-type="dijit.form.FilteringSelect"
														value="KOUSHIK" intermediateChanges="false" trim="false"
														uppercase="false" lowercase="false" propercase="false"
														required="required" pageSize="Infinity">
															<option value="KOUSHIK">KOUSHIK</option>
															<option value="TIKLU">TIKLU</option>
													</select></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														intermediateChanges="false" trim="false" uppercase="false"
														lowercase="false" propercase="false" required="required"
														state="Incomplete" style="width: 90%;"></input></td>
													<td><input type="text"
														data-dojo-type="dijit.form.CurrencyTextBox"
														intermediateChanges="false" trim="false" uppercase="false"
														lowercase="false" propercase="false" required="required"
														currency="Rs. "
														regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
														state="Error" style="width: 10em;"></input></td>
													<td style="text-align: left;"><input type="button"
														data-dojo-type="dijit.form.Button"
														intermediateChanges="false" label="Entry"
														iconClass="dijitNoIcon"></input></td>
												</tr>
											</tbody>
										</table>
									</div>
									<div data-dojo-type="dijit.layout.ContentPane"
										title="Pay to Customer" extractContent="false"
										preventCache="false" preload="false" refreshOnShow="false"
										doLayout="true" closable="false" splitter="false"
										maxSize="Infinity">
									<form method="post" data-dojo-type="dojox.form.Manager"
										jsId="customerForm4" action="/SoftShop/CustomeResigtration">	
										<table style="width: 100%; text-align: center;">
											<tbody>
												<tr style="font-weight: bold;">
													<td><label> Date</label></td>
													<td><label> Time</label></td>
													<td><label> Customer ID</label></td>
													<td><label> Customer Name</label></td>
													<td><label> Detiles</label></td>
													<td><label> Amount</label></td>
													<td></td>
												</tr>
												<tr>
													<td><label> 06/26/2012</label>
													</td>
													<td><label> 11:45</label></td>
													<td><select id="searchByCustIDforAcc"
																data-dojo-type="dijit.form.ComboBox" name="customer_Id"
																jsId="customerIdCombo2" store="customerIdStore" searchAttr="id"
																autocomplete="false" onChange="" uppercase="false"
																lowercase="false" propercase="false" pageSize="Infinity"
																style="width: 10em;">
															
																<script type="dojo/method" event="onChange">
																this.store.get(this.value).then(function(customer){
																	account_Customer_Name.setValue(customer.name);
																																										
																});									
																</script>	
															</td>
													<td><select
														data-dojo-type="dijit.form.FilteringSelect" jsId="account_Customer_Name" name="account_Customer_Name"
														value="" intermediateChanges="false" trim="false"
														uppercase="false" lowercase="false" propercase="false"
														required="required" pageSize="Infinity">
														</select></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														intermediateChanges="false" trim="false" uppercase="false"
														lowercase="false" propercase="false" required="required"
														state="Incomplete" style="width: 90%;"></input></td>
													<td><input type="text"
														data-dojo-type="dijit.form.CurrencyTextBox"
														intermediateChanges="false" trim="false" uppercase="false"
														lowercase="false" propercase="false" required="required"
														currency="Rs. "
														regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
														state="Error" style="width: 10em;"></input></td>
													<td style="text-align: left;"><input type="button"
														data-dojo-type="dijit.form.Button"
														intermediateChanges="false" label="Entry"
														iconClass="dijitNoIcon"></input></td>
												</tr>
											</tbody>
										</table>
									</form>	
									</div> </span> </span>
						
						</span>
				</div>