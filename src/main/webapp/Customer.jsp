<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div data-dojo-type="dijit.layout.ContentPane" title="Customer"
					extractContent="false" preventCache="false" preload="false"
					refreshOnShow="false" doLayout="true" closable="false"
					splitter="false" maxSize="Infinity">
					<span data-dojo-type="dijit.layout.AccordionContainer" jsId="customerAccordian"
						duration="200" persist="false"
						style="min-width: 1em; min-height: 1em; width: 100%; height: 100%; text-align: center;">
						<div
							data-dojo-type="dijit.layout.ContentPane" onShow="window.initCustomerGrid();"
							title="Customer Details" extractContent="false" jsId="customerContentPane"
							preventCache="false" preload="false" refreshOnShow="false"
							doLayout="true" selected="selected" closable="false"
							splitter="false" maxSize="Infinity">
							<div id="customerGrid" style="height:400px;width:100%"></div>
						</div>
						<div data-dojo-type="dijit.layout.ContentPane"
							title="Add A New Customer" extractContent="false"
							preventCache="false" preload="false" refreshOnShow="false"
							doLayout="true" closable="false" splitter="false"
							maxSize="Infinity">
								
							<form method="post" data-dojo-type="dojox.form.Manager" jsId="customerForm" action="/SoftShop/CustomeResigtration">
							<script type="dojo/method" event="onSubmit">
									// do not submit this form
									var values = this.gatherFormValues();
									dojo.xhrPost({
										url:"CustomeResigtration/",
										content:values,
										handle:function(response){
											customerForm.reset();										
										}
									});	
									return false;
									
							</script>	
								<table style="width: 100%;">
									<tbody>
										<tr>
											<td style="width: 25%;"></td>
											<td style="width: 25%;"></td>
											<td style="width: 25%;"></td>
											<td style="width: 25%;"></td>
										</tr>
										<tr>
											<td></td>
											<td><label style="font-weight: bold;"> Customer
													Name :</label></td>
											<td><input type="text"
												data-dojo-type="dijit.form.ValidationTextBox" name="customer_Name"
												value="" intermediateChanges="false"
												trim="false" uppercase="true" lowercase="false"
												propercase="false" required="required" style="width: 100%;"></input>
											</td>
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
								<table style="width: 100%;">
									<tbody>
										<tr>
											<td style="width: 25%;"></td>
											<td style="width: 25%;"></td>
											<td style="width: 25%;"></td>
											<td style="width: 25%;"></td>
										</tr>
										<tr>
           <td style="width: 1%;"></td>
           <td>
            <label style="font-weight: bold;">
              Address :</label>
          </td>
           <td>
             <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="customer_Street" autocomplete="false" onChange=""  uppercase="true" lowercase="false" propercase="false" required="required" state="Incomplete" style="width: 100%;"></input>
           </td>
           <td style="width: 1%;"></td>
         </tr>
         <tr>
											<td></td>
											<td><label style="font-weight: bold;"> Post
													Office :</label></td>
											<td><select data-dojo-type="dijit.form.ComboBox"
												name="customer_PostOffice" store="postofficesStore" searchAttr="postOffice"
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
												name="customer_PoliceStation" store="policeStationStore" searchAttr="policeStation"
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
												name="customer_District" store="districtStore" searchAttr="district" autocomplete="false"
												onChange=""  uppercase="false" lowercase="false"
												propercase="false" pageSize="Infinity" style="width: 100%;">													
											</select></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td><label style="font-weight: bold;"> State :</label></td>
											<td><select data-dojo-type="dijit.form.ComboBox"
												name="customer_CurrentState"   store="currentStateStore" searchAttr="currentState"
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
								             <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="customer_Pin" autocomplete="false" onChange=""  uppercase="false" lowercase="false" propercase="false" required="required" regExp="^[0-9]{6}$" state="Incomplete" style="width: 100%;"></input>
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
								             <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="customer_EmailId" autocomplete="false" onChange=""  uppercase="false" lowercase="false" propercase="false" style="width: 100%;"></input>
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
								            <input type="text" data-dojo-type="dijit.form.ValidationTextBox" name="customer_Mobile" autocomplete="false" onChange=""  uppercase="false" lowercase="false" propercase="false" required="required" regExp="^[0-9]{10}$" state="Error" style="width: 100%;"></input>
								          </td>
								           <td></td>
								         </tr>
										<tr>
											<td></td>
											<td><label style="font-weight: bold;">
													Recommended by :</label></td>
											<td><input type="text"
												data-dojo-type="dijit.form.ValidationTextBox" name="customer_Reco"
												value="SELF" intermediateChanges="false" trim="false"
												uppercase="true" lowercase="false" propercase="false"
												required="required" style="width: 100%;"></input></td>
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
							
							
								<table style="width: 100%;">
									<tbody>
										<tr>
											<td style="width: 25%;"></td>
											<td style="width: 25%;"><label
												style="font-weight: bold;"> Grade of the Customer :</label>
											</td>
											<td>
												<table style="width: 100%; text-align: center;">
													<tbody>
														<tr>
															<td><label> A</label></td>
															<td><label> B</label></td>
															<td><label> C</label></td>
															<td><label> D</label></td>
														</tr>
														<tr>
															<td><input showlabel="true" type="radio"
																data-dojo-type="dijit.form.RadioButton" value="A"
																intermediateChanges="false" name=customer_Grade iconClass="dijitNoIcon"></input>
															</td>
															<td><input showlabel="true" type="radio"
																data-dojo-type="dijit.form.RadioButton" name=customer_Grade
																intermediateChanges="false" iconClass="dijitNoIcon" value="B"></input>
															</td>
															<td><input showlabel="true" type="radio" name=customer_Grade
																data-dojo-type="dijit.form.RadioButton" value="C"
																intermediateChanges="false" iconClass="dijitNoIcon"></input>
															</td>
															<td><input showlabel="true" type="radio" name=customer_Grade
																data-dojo-type="dijit.form.RadioButton" value="D"
																intermediateChanges="false" iconClass="dijitNoIcon"></input>
															</td>
														</tr>
													</tbody>
												</table></td>
											<td style="width: 25%;"></td>
										</tr>
										<tr>
											<td></td>
											<td><input type="submit"
												data-dojo-type="dijit.form.Button"
												intermediateChanges="false" label="Add"
												iconClass="dijitNoIcon"></input></td>
											<td><input type="reset"
												data-dojo-type="dijit.form.Button"
												intermediateChanges="false" label="Reset"
												iconClass="dijitNoIcon"></input></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td></td>
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
							</form>
							
						</div> </span>
				</div>