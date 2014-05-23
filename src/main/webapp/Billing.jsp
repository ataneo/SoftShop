<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<div data-dojo-type="dijit.layout.ContentPane" title="Billing"
	extractContent="false" preventCache="false" preload="false"
	refreshOnShow="false" doLayout="true" selected="selected"
	closable="false" splitter="false" maxSize="Infinity">
	<span data-dojo-type="dijit.layout.TabContainer" tabStrip="false"
		persist="false" controllerWidget="dijit.layout.TabController"
		style="min-width: 1em; min-height: 1em; width: 100%; height: 100%;">
		<div data-dojo-type="dijit.layout.ContentPane" title="New Bill"
			extractContent="false" selected preventCache="false" preload="false"
			refreshOnShow="false" doLayout="true" closable="false"
			splitter="false" maxSize="Infinity">
			<div data-dojo-type="dijit.layout.BorderContainer" persist="false"
				gutters="true"
				style="min-width: 1em; min-height: 1em; z-index: 0; height: 100%; width: 922px;">
				<div data-dojo-type="dijit.layout.ContentPane"
					extractContent="false" preventCache="false" preload="false"
					refreshOnShow="false" doLayout="true" closable="false" region="top"
					splitter="true" maxSize="Infinity" style="text-align: center;">
					<form method="post" data-dojo-type="dojox.form.Manager"
						jsId="customerForm2" action="/SoftShop/CustomeResigtration">
						<script type="dojo/method" event="onSubmit">
									// do not submit this form
									var values = this.gatherFormValues();
									dojo.xhrPost({
										url:"CustomeResigtration/",
										content:values,
										handle:function(response){
											customerForm2.reset();										
										}
									});	
									return false;
									
							</script>
						<script type="dojo/method" event="onReset">
								this.enable();
								addCustButton.enable();
								return true;						
							</script>
						<table width="100%">
							<tbody>
								<tr>
									<td align="left" width="20%" style="font-weight: bold;"><label>Bill No :</label><br>
										<input type="text"
											data-dojo-type="dijit.form.ValidationTextBox" jsId="billNo"
											name="billNo" autocomplete="false" onChange=""
											uppercase="false" lowercase="false" propercase="false"
											state="Incomplete" disabled="disabled"
											style="width: 100%;"></input>
										<br> <br> <br>
										<label for="searchByCustID"	style="font-weight: bold;">Search By CustomerID</label><br>
										<select id="searchByCustID"
										data-dojo-type="dijit.form.ComboBox" name="customer_Id"
										jsId="customerIdCombo" store="customerIdStore" searchAttr="id"
										autocomplete="false" onChange="" uppercase="false"
										lowercase="false" propercase="false" pageSize="Infinity"
										style="width: 10em;">
											<script type="dojo/method" event="onBlur">
								this.store.get(this.value).then(function(customer){
								var formValues = {
									customer_Name:			customer.name,
									customer_Street:		customer.address.street,
									customer_PostOffice:	customer.address.postOffice.postOffice,
									customer_PoliceStation:	customer.address.policeStation.policeStation,
									customer_District:		customer.address.district.district,
									customer_CurrentState:	customer.address.currentState.currentState,
									customer_Pin:			customer.address.pin,
									customer_EmailId:		customer.address.emailId,
									customer_Mobile:		customer.address.mobileNumber,
									customer_Reco:			customer.reference,
									customer_Grade:			customer.grade.grade
								};
								customerForm2.setFormValues(formValues);
								customerForm2.disable(); 
								//addCustButton.disable();
								customerBalance.setValue(customer.balance);

																	
								});									
							</script>
									</select> <br> <br> <br> <label for="searchByCustPhone"
										style="font-weight: bold;">Search By Customer Name</label><br>
										<select id="searchByCustPhone"
										data-dojo-type="dijit.form.ComboBox" name="customer_Id"
										store="customerPhoneStore" searchAttr="mobileNumber"
										autocomplete="false" onChange="" uppercase="false"
										lowercase="false" propercase="false" pageSize="Infinity"
										style="width: 10em;"></td>
									<td width="50%">
										<table style="width: 100%;">

											<colgroup>
												<col></col>
												<col
													style="color: green; text-align: right; elevation: 45deg;"></col>
												<col></col>
												<col></col>
											</colgroup>
											<tbody>
												<tr>
													<td style="width: 10%;"></td>
													<td style="width: 40%;"></td>
													<td style="width: 40%;"></td>
													<td style="width: 10%;"></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;">Customer
															Name :</label></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_Name" value="" intermediateChanges="false"
														trim="false" uppercase="true" lowercase="false"
														propercase="false" required="required"
														style="width: 100%;"></input></td>
													<td></td>
												</tr>
												<tr>

													<td></td>
													<td><label style="font-weight: bold;"> Address
															:</label></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_Street" autocomplete="false" onChange=""
														uppercase="true" lowercase="false" propercase="false"
														required="required" state="Incomplete"
														style="width: 100%;"></input></td>
													<td style="width: 1%;"></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;"> Post
															Office :</label></td>
													<td><select data-dojo-type="dijit.form.ComboBox"
														name="customer_PostOffice" store="postofficesStore"
														searchAttr="postOffice" autocomplete="false" onChange=""
														uppercase="false" lowercase="false" propercase="false"
														pageSize="Infinity" style="width: 100%;">
													</select></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;"> Police
															Station :</label></td>
													<td><select data-dojo-type="dijit.form.ComboBox"
														name="customer_PoliceStation" store="policeStationStore"
														searchAttr="policeStation" autocomplete="false"
														onChange="" uppercase="false" lowercase="false"
														propercase="false" pageSize="Infinity"
														style="width: 100%;">
													</select></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;">
															District :</label></td>
													<td><select data-dojo-type="dijit.form.ComboBox"
														name="customer_District" store="districtStore"
														searchAttr="district" autocomplete="false" onChange=""
														uppercase="false" lowercase="false" propercase="false"
														pageSize="Infinity" style="width: 100%;">
													</select></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;"> State :</label></td>
													<td><select data-dojo-type="dijit.form.ComboBox"
														name="customer_CurrentState" store="currentStateStore"
														searchAttr="currentState" autocomplete="false" onChange=""
														uppercase="false" lowercase="false" propercase="false"
														pageSize="Infinity" style="width: 100%;">
													</select></td>
													<td></td>
												</tr>

												<tr>
													<td></td>
													<td><label style="font-weight: bold;"> Pin :</label></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_Pin" autocomplete="false" onChange=""
														uppercase="false" lowercase="false" propercase="false"
														required="required" regExp="^[0-9]{6}$" state="Incomplete"
														style="width: 100%;"></input></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;"> E-Mail
															:</label></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_EmailId" autocomplete="false" onChange=""
														uppercase="false" lowercase="false" propercase="false"
														style="width: 100%;"></input></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;"> Mobile
															No :</label></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_Mobile" autocomplete="false" onChange=""
														uppercase="false" lowercase="false" propercase="false"
														required="required" regExp="^[0-9]{10}$" state="Error"
														style="width: 100%;"></input></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;">
															Recommended by :</label></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_Reco" value="" intermediateChanges="false"
														trim="false" uppercase="true" lowercase="false"
														propercase="false" required="required"
														style="width: 100%;"></input></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>

												<tr>
													<td style="width: 25%;"></td>
													<td style="width: 25%;"><label
														style="font-weight: bold;"> Grade of the Customer
															:</label></td>
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
																		intermediateChanges="false" name=customer_Grade
																		iconClass="dijitNoIcon"></input></td>
																	<td><input showlabel="true" type="radio"
																		data-dojo-type="dijit.form.RadioButton"
																		name=customer_Grade intermediateChanges="false"
																		iconClass="dijitNoIcon" value="B"></input></td>
																	<td><input showlabel="true" type="radio"
																		name=customer_Grade
																		data-dojo-type="dijit.form.RadioButton" value="C"
																		intermediateChanges="false" iconClass="dijitNoIcon"></input>
																	</td>
																	<td><input showlabel="true" type="radio"
																		name=customer_Grade
																		data-dojo-type="dijit.form.RadioButton" value="D"
																		intermediateChanges="false" iconClass="dijitNoIcon"></input>
																	</td>
																</tr>
															</tbody>
														</table>
													</td>
													<td style="width: 25%;"></td>
												</tr>
												<tr>
													<td></td>
													<td><input type="submit" jsId="addCustButton"
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
									</td>
									<td width="30%" valign="top"><label>Job Details :</label>
										<textarea type="text" data-dojo-type="dijit.form.Textarea" jsId="jobDetails"
											style="width: 100%; height: 100%;"></textarea></td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				<div data-dojo-type="dijit.layout.ContentPane"
					extractContent="false" preventCache="false" preload="false"
					refreshOnShow="false" doLayout="true" closable="false"
					region="center" splitter="false" maxSize="Infinity"
					style="text-align: center;">
					<form id="addBillForm" method="post" action="/SoftShop/PriceList" onsubmit="arguments[0].preventDefault(); addBillSubmit(this);">
						
					<table id="billTab" style="width: 100%; text-align: center;">
						<tbody>
							<tr style="font-weight: bold;">
								<td><label> Item</label></td>
								<td><label> Category</label></td>
								<td><label> Brand</label></td>
								<td><label> Length</label></td>
								<td><label> Width</label></td>
								<td><label> Area</label></td>
								<td><label> Count</label></td>
								<td><label> Both side</label></td>
								<td><label> Amount (Rs)</label></td>
								<td></td>
							</tr>
							<!-- add a row -->
							<tr data-dojo-type="softShop.AddStock"></tr>
							<tr id="addButton">
								<td><input type="button" data-dojo-type="dijit.form.Button"
									intermediateChanges="false" label="Add New Item"
									iconClass="dijitNoIcon"
									onClick="if(window.currentAddStock)window.currentAddStock.disableDropDown();var newRow = new softShop.AddStock({});dojo.place(newRow.domNode,'addButton','before');">

									</input></td>
								<td style="text-align: left;"></td>
								<td></td>
								<td><B>Customer Balance</B></td>
								<td><input type="text" jsId="customerBalance"
									data-dojo-type="dijit.form.CurrencyTextBox" disabled="disabled"
									readOnly="readOnly" intermediateChanges="false" trim="false"
									uppercase="false" lowercase="false" propercase="false"
									required="required" currency="Rs. "
									regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
									style="width: 9em;"></input></td>
								<td><B>Grand Total</B></td>
								<td><input type="text" jsId="totalGrandTotal"
									data-dojo-type="dijit.form.CurrencyTextBox" disabled="disabled"
									readOnly="readOnly" intermediateChanges="false" trim="false"
									uppercase="false" lowercase="false" propercase="false"
									required="required" currency="Rs. "
									regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
									style="width: 9em;"></input></td>
								<td style="text-align: right;"><label
									style="font-weight: bold;"> Total</label></td>
								<td><input type="text" jsId="grandTotal" name="amount" onChange="calcGrandTotal();"
									data-dojo-type="dijit.form.CurrencyTextBox" disabled="disabled"
									readOnly="readOnly" intermediateChanges="false" trim="false"
									uppercase="false" lowercase="false" propercase="false"
									required="required" currency="Rs. "
									regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
									style="width: 9em;"></input></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td style="text-align: right;"><label
									style="font-weight: bold;"> Advance</label></td>
								<td><input type="text" onChange="calcGrandTotal();" jsId="advance" name="paidAmount" 
									name="Advance" data-dojo-type="dijit.form.CurrencyTextBox"
									intermediateChanges="false" trim="false" uppercase="false"
									lowercase="false" propercase="false" required="required"
									currency="Rs. " value="0"
									regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
									state="Incomplete" style="width: 9em;"></input></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td><label style="font-weight: bold;"> Delivery
										Date &amp; time :</label></td>
								<td style="text-align: left;"><input type="text" name="delivaryDate"
									data-dojo-type="softShop.DateTextBox"></input></td>
								<td style="text-align: left;"><input type="text" name="delivaryTime"
									data-dojo-type="dijit.form.TimeTextBox"
									intermediateChanges="false" trim="false" uppercase="false"
									lowercase="false" propercase="false"></input></td>
								<td><input type="submit" data-dojo-type="dijit.form.Button"
									intermediateChanges="false" label="Save"
									iconClass="dijitNoIcon"></input></td>
								<td><input type="button" data-dojo-type="dijit.form.Button" jsId="printButton"
									intermediateChanges="false" label="Print" onclick="printBill();"
									iconClass="dijitNoIcon"></input></td>
								<td><input type="reset" data-dojo-type="dijit.form.Button" jsId="newBill"
									intermediateChanges="false" label="New Bill" onclick="resetBill();"
									iconClass="dijitNoIcon"></input></td>
								<td></td>
								<td style="text-align: right;"><label
									style="font-weight: bold;">Net Due</label></td>
								<td><input type="text"
									data-dojo-type="dijit.form.CurrencyTextBox" jsId="Due" name="due"
									disabled="disabled" readOnly="readOnly"
									intermediateChanges="false" trim="false" uppercase="false"
									lowercase="false" propercase="false" required="required"
									currency="Rs. "
									regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
									style="width: 9em;"></input></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
					</form>
				</div>
			</div>
		</div>
		<div data-dojo-type="dijit.layout.ContentPane" title="Update Bill"
			extractContent="false" preventCache="false" preload="false"
			refreshOnShow="false" doLayout="true" selected="selected"
			closable="false" splitter="false" maxSize="Infinity">
			<div data-dojo-type="dijit.layout.BorderContainer" persist="false"
				gutters="true"
				style="min-width: 1em; min-height: 1em; z-index: 0; height: 100%;">
				<div data-dojo-type="dijit.layout.ContentPane"
					extractContent="false" preventCache="false" preload="false"
					refreshOnShow="false" doLayout="true" closable="false" region="top"
					splitter="true" maxSize="Infinity" style="text-align: center;">
				<form method="post" data-dojo-type="dojox.form.Manager"
						jsId="customerForm3" action="/SoftShop/CustomeResigtration">
							
					<table style="width: 100%;">

						<tbody>
							<tr>
								<td style="width: 0s%;"></td>
								<td style="width: 30%;"><label style="font-weight: bold;">
										Bill No :</label></td>
								<td style="width: 30%;"><select
									data-dojo-type="dijit.form.FilteringSelect" store="billStore" searchAttr="id"
									intermediateChanges="false" trim="false" uppercase="false" jsId="updateBillNO"
									lowercase="false" propercase="false" required="required" autocomplete="false"
									pageSize="Infinity">
									<script type="dojo/method" event="onChange">								
								openBill(this.getValue());
							</script>									
								</select></td>
								<td style="width: 20%;"></td>
							</tr>
							<tr>
								<td></td>
								<td><label style="font-weight: bold;"> Date / Time
										:</label></td>
								<td><label style="width: 100%;" id="dateTimeLabel"></label></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td><label style="font-weight: bold;"> Customer ID
										:</label></td>
								<td><select id="updateBillsearchByCustID"
										data-dojo-type="dijit.form.ComboBox" name="customer_Id"
										jsId="updateBillsearchByCustID" store="customerIdStore" searchAttr="id"
										autocomplete="false" onChange="" uppercase="false"
										lowercase="false" propercase="false" pageSize="Infinity"
										style="width: 10em;">
											<script type="dojo/method" event="onChange">
								this.store.get(this.value).then(function(customer){
								//updateBillAdvance.setValue(customer.balance);
								updateBillDue.setValue(customer.balance);
								updateBillPopUp.titleNode.innerHTML=customer.name;
								var formValues = {
									customer_Name:			customer.name,
									customer_Street:		customer.address.street,
									customer_PostOffice:	customer.address.postOffice.postOffice,
									customer_PoliceStation:	customer.address.policeStation.policeStation,
									customer_District:		customer.address.district.district,
									customer_CurrentState:	customer.address.currentState.currentState,
									customer_Pin:			customer.address.pin,
									customer_EmailId:		customer.address.emailId,
									customer_Mobile:		customer.address.mobileNumber,
									customer_Reco:			customer.reference,
									customer_Grade:			customer.grade.grade
								};
								customerForm3.setFormValues(formValues);
								customerForm3.disable(); 
								addCustButton.disable();
																	
								});									
							</script></td>
								<td></td>
							</tr>
							
												<tr>
													<td style="width: 10%;"></td>
													<td style="width: 40%;"></td>
													<td style="width: 40%;"></td>
													<td style="width: 10%;"></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;">Customer
															Name :</label></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_Name" value="" intermediateChanges="false"
														trim="false" uppercase="true" lowercase="false"
														propercase="false" required="required"
														style="width: 100%;"></input></td>
													<td></td>
												</tr>
												<tr>

													<td></td>
													<td><label style="font-weight: bold;"> Address
															:</label></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_Street" autocomplete="false" onChange=""
														uppercase="true" lowercase="false" propercase="false"
														required="required" state="Incomplete"
														style="width: 100%;"></input></td>
													<td style="width: 1%;"></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;"> Post
															Office :</label></td>
													<td><select data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_PostOffice" store="postofficesStore"
														searchAttr="postOffice" autocomplete="false" onChange=""
														uppercase="false" lowercase="false" propercase="false"
														pageSize="Infinity" style="width: 100%;">
													</select></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;"> Police
															Station :</label></td>
													<td><select data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_PoliceStation" store="policeStationStore"
														searchAttr="policeStation" autocomplete="false"
														onChange="" uppercase="false" lowercase="false"
														propercase="false" pageSize="Infinity"
														style="width: 100%;">
													</select></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;">
															District :</label></td>
													<td><select data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_District" store="districtStore"
														searchAttr="district" autocomplete="false" onChange=""
														uppercase="false" lowercase="false" propercase="false"
														pageSize="Infinity" style="width: 100%;">
													</select></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;"> State :</label></td>
													<td><select data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_CurrentState" store="currentStateStore"
														searchAttr="currentState" autocomplete="false" onChange=""
														uppercase="false" lowercase="false" propercase="false"
														pageSize="Infinity" style="width: 100%;">
													</select></td>
													<td></td>
												</tr>

												<tr>
													<td></td>
													<td><label style="font-weight: bold;"> Pin :</label></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_Pin" autocomplete="false" onChange=""
														uppercase="false" lowercase="false" propercase="false"
														required="required" regExp="^[0-9]{6}$" state="Incomplete"
														style="width: 100%;"></input></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;"> E-Mail
															:</label></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_EmailId" autocomplete="false" onChange=""
														uppercase="false" lowercase="false" propercase="false"
														style="width: 100%;"></input></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;"> Mobile
															No :</label></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_Mobile" autocomplete="false" onChange=""
														uppercase="false" lowercase="false" propercase="false"
														required="required" regExp="^[0-9]{10}$" state="Error"
														style="width: 100%;"></input></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td><label style="font-weight: bold;">
															Recommended by :</label></td>
													<td><input type="text"
														data-dojo-type="dijit.form.ValidationTextBox"
														name="customer_Reco" value="" intermediateChanges="false"
														trim="false" uppercase="true" lowercase="false"
														propercase="false" required="required"
														style="width: 100%;"></input></td>
													<td></td>
												</tr>
												<tr>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>

												<tr>
													<td style="width: 25%;"></td>
													<td style="width: 25%;"><label
														style="font-weight: bold;"> Grade of the Customer
															:</label></td>
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
																		intermediateChanges="false" name=customer_Grade
																		iconClass="dijitNoIcon"></input></td>
																	<td><input showlabel="true" type="radio"
																		data-dojo-type="dijit.form.RadioButton"
																		name=customer_Grade intermediateChanges="false"
																		iconClass="dijitNoIcon" value="B"></input></td>
																	<td><input showlabel="true" type="radio"
																		name=customer_Grade
																		data-dojo-type="dijit.form.RadioButton" value="C"
																		intermediateChanges="false" iconClass="dijitNoIcon"></input>
																	</td>
																	<td><input showlabel="true" type="radio"
																		name=customer_Grade
																		data-dojo-type="dijit.form.RadioButton" value="D"
																		intermediateChanges="false" iconClass="dijitNoIcon"></input>
																	</td>
																</tr>
															</tbody>
														</table>
													</td>
													<td style="width: 25%;"></td>
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
												<tr>
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
											</tbody>
										</table>
				</form>
				</div>
				<div data-dojo-type="dijit.layout.ContentPane"
					extractContent="false" preventCache="false" preload="false"
					refreshOnShow="false" doLayout="true" closable="false"
					region="center" splitter="false" maxSize="Infinity"
					style="text-align: center;">
					<div data-dojo-type="dijit.Dialog" jsId="updateBillPopUp" title="customer_ID,customer_Name" style="width:80%;">
					<form id="updateBillForm" action="/SoftShop" onsubmit="arguments[0].preventDefault(); updateBillSubmit(this);">
					<table id="billTabUpdate" style="width: 100%; text-align: center;">
						<tbody>
							<tr style="font-weight: bold;">
								<td><label> Item</label></td>
								<td><label> Category</label></td>
								<td><label> Brand</label></td>
								<td><label> Length</label></td>
								<td><label> Width</label></td>
								<td><label> Area</label></td>
								<td><label> Count</label></td>
								<td><label> Both side</label></td>
								<td><label> Amount (Rs)</label></td>
								<td><label> Using Roll</label></td>
								<td></td>
							</tr>
							
							<tr id="pivot">
								<td></td>
								<td style="text-align: left;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td style="text-align: right;"><label
									style="font-weight: bold;"> Total</label></td>
								<td><input type="text"
									data-dojo-type="dijit.form.CurrencyTextBox" disabled="disabled" jsId="billUpdateTotal"
									readOnly="readOnly" intermediateChanges="false" trim="false"
									uppercase="false" lowercase="false" propercase="false"
									required="required" currency="Rs. "
									regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
									style="width: 9em;"></input></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td style="text-align: left;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td style="text-align: right;"><!-- label
									style="font-weight: bold;">Previous Advance</label--></td>
								<td><!-- input type="text"
									data-dojo-type="dijit.form.CurrencyTextBox" disabled="disabled" jsId="updateBillAdvance"
									readOnly="readOnly" intermediateChanges="false" trim="false"
									uppercase="false" lowercase="false" propercase="false" value="0.00"
									required="required" currency="Rs. "
									regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
									style="width: 9em;"></input--></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td style="text-align: left;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td style="text-align: right;"><label
									style="font-weight: bold;">Balance</label></td>
								<td><input type="text"
									data-dojo-type="dijit.form.CurrencyTextBox" disabled="disabled" jsId="updateBillDue"
									readOnly="readOnly" intermediateChanges="false" trim="false"
									uppercase="false" lowercase="false" propercase="false"
									required="required" currency="Rs. " 
									regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
									style="width: 9em;"></input></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td><label style="font-weight: bold;"> Delivery
										Date &amp; Time :</label></td>
								<td style="text-align: left;"><input type="text" name="delivaryDate"
									data-dojo-type="softShop.DateTextBox" jsId="updateBillDeliveryDate"></input></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td style="text-align: right;"><label
									style="font-weight: bold;"> Paid</label></td>
								<td><input type="text" 
									data-dojo-type="dijit.form.CurrencyTextBox"
									intermediateChanges="false" trim="false" uppercase="false"
									lowercase="false" propercase="false" required="required" name="paidAmount"
									currency="Rs. " onChange="updateBalance.setValue(this.getValue()+updateBillDue.getValue());"
									regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
									state="Incomplete" style="width: 9em;"/></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td><label style="font-weight: bold;"> Paid Date :</label></td>
								<td style="text-align: left;"><input type="text" name="duePromiseDate"
									data-dojo-type="softShop.DateTextBox"></input></td>
								<td><input type="submit" data-dojo-type="dijit.form.Button" jsId="saveUpdateBill"
									intermediateChanges="false" label="Save" oniconClass="dijitNoIcon"></input></td>
								<td><input type="button" data-dojo-type="dijit.form.Button" jsId="updatePrintButton"
									intermediateChanges="false" label="Print" onclick="printBill();"
									iconClass="dijitNoIcon"></input></td>
								<td></td>
								<td></td>
								<td style="text-align: right; font-weight: bold;">Net Balance</td>
								<td><input type="text" value="balance" jsId="updateBalance"
									data-dojo-type="dijit.form.CurrencyTextBox" disabled="disabled"
									intermediateChanges="false" trim="false" uppercase="false"
									lowercase="false" propercase="false" required="required"
									currency="Rs. "
									regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
									state="Incomplete" style="width: 9em;"></input></td>
								<td></td>
							</tr>
						</tbody>
					</table>
					</form>
					</div>
				</div>
			</div>
		</div>
	</span>
</div>