<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div data-dojo-type="dijit.layout.ContentPane" title="Settings"
					extractContent="false" preventCache="false" preload="false"
					refreshOnShow="false" doLayout="true" closable="false"
					splitter="false" maxSize="Infinity">
					<span data-dojo-type="dijit.layout.TabContainer" tabStrip="false"
						persist="false" controllerWidget="dijit.layout.TabController"
						style="min-width: 1em; min-height: 1em; width: 100%; height: 100%;"><div
							data-dojo-type="dijit.layout.ContentPane"
							title="Account Settings" extractContent="false"
							preventCache="false" preload="false" refreshOnShow="false"
							doLayout="true" closable="false" splitter="false"
							maxSize="Infinity">
							<fieldset
								style="border: 2px groove threedface; margin: 2px; padding: 0.75em; text-align: center;">
								<legend style="font-weight: bold;"> Change The Password</legend>
								<form action="/SoftShop/authentications">
									<table style="width: 100%;">
										<tbody style="width: 100%;">
											<tr>
												<td></td>
												<td><label style="font-weight: bold;"> Your
														Username :</label></td>
												<td><input type="text"
													data-dojo-type="dijit.form.ValidationTextBox"
													name="userName" value="koushik" readOnly="readOnly"
													intermediateChanges="false" trim="false" uppercase="false"
													lowercase="false" propercase="false" required="required"
													style="width: 100%;"></input></td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td><label style="font-weight: bold;"> Your Old
														Password :</label></td>
												<td style="text-align: left;"><input type="password"
													data-dojo-type="dijit.form.ValidationTextBox"
													name="passwordOld" intermediateChanges="false" trim="false"
													uppercase="false" lowercase="false" propercase="false"
													required="required" state="Incomplete" style="width: 100%;"></input>
												</td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td><label style="font-weight: bold;"> Create A
														New Password :</label></td>
												<td><input type="password"
													data-dojo-type="dijit.form.ValidationTextBox"
													name="password" intermediateChanges="false" trim="false"
													uppercase="false" lowercase="false" propercase="false"
													required="required" state="Incomplete" style="width: 100%;"></input>
												</td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td style="font-weight: bold;"><label> Confirm
														your Password :</label></td>
												<td><input type="password"
													data-dojo-type="dijit.form.ValidationTextBox"
													name="passwordConfirm" intermediateChanges="false"
													trim="false" uppercase="false" lowercase="false"
													propercase="false" required="required" state="Incomplete"
													style="width: 100%;"></input></td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td></td>
												<td style="text-align: left;"><input type="button"
													data-dojo-type="dijit.form.Button"
													intermediateChanges="false" label="Update"
													iconClass="dijitNoIcon"></input></td>
												<td></td>
											</tr>
										</tbody>
									</table>
								</form>
							</fieldset>
						</div>
						<div data-dojo-type="dijit.layout.ContentPane" title="Log Up"
							extractContent="false" preventCache="false" preload="false"
							refreshOnShow="false" doLayout="true" selected="selected"
							closable="false" splitter="false" maxSize="Infinity">
							<fieldset
								style="border: 2px groove threedface; margin: 2px; padding: 0.75em; text-align: center;">
								<legend style="font-weight: bold;"> Employee Log Up</legend>
								<form action="/SoftShop/employees">
									<table style="width: 100%; text-align: right;">
										<tbody style="width: 100%;">
											<tr>
												<td style="width: 25%;"></td>
												<td style="width: 25%;"><label
													style="font-weight: bold;"> Name Of The Employee :</label>
												</td>
												<td style="width: 25% ];"><input type="text"
													data-dojo-type="dijit.form.ValidationTextBox" name="name"
													value="KOUSHIK BISWAS" intermediateChanges="false"
													trim="false" uppercase="true" lowercase="false"
													propercase="false" required="required" style="width: 100%;"></input>
												</td>
												<td style="width: 25%;"></td>
											</tr>
										</tbody>
									</table>
								</form>
								<form action="/SoftShop/addresses">
									<table style="width: 100%; text-align: right;">
										<tbody style="width: 100%;">
											<tr>
												<td style="width: 25%;"></td>
												<td style="width: 25%;"><label
													style="font-weight: bold;"> Address :</label></td>
												<td style="width: 25%;"><input type="text"
													data-dojo-type="dijit.form.ValidationTextBox" name="street"
													intermediateChanges="false" trim="false" uppercase="true"
													lowercase="false" propercase="false" required="required"
													state="Incomplete" style="width: 100%;"></input></td>
												<td style="width: 25%;"></td>
											</tr>
											<tr>
												<td></td>
												<td><label style="font-weight: bold;"> P.O. :</label></td>
												<td><input type="text"
													data-dojo-type="dijit.form.ValidationTextBox"
													name="postOffice" intermediateChanges="false" trim="false"
													uppercase="true" lowercase="false" propercase="false"
													required="required" state="Incomplete" style="width: 100%;"></input>
												</td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td><label style="font-weight: bold;"> P.S. :</label></td>
												<td><input type="text"
													data-dojo-type="dijit.form.ValidationTextBox"
													intermediateChanges="false" trim="false" uppercase="true"
													lowercase="false" propercase="false" required="required"
													state="Incomplete" style="width: 100%;"></input></td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td><label style="font-weight: bold;"> Dist :</label></td>
												<td><input type="text"
													data-dojo-type="dijit.form.ValidationTextBox"
													name="district" intermediateChanges="false" trim="false"
													uppercase="true" lowercase="false" propercase="false"
													required="required" state="Incomplete" style="width: 100%;"></input>
												</td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td><label style="font-weight: bold;"> State :</label>
												</td>
												<td><input type="text"
													data-dojo-type="dijit.form.ValidationTextBox"
													name="currentState" intermediateChanges="false"
													trim="false" uppercase="true" lowercase="false"
													propercase="false" required="required" state="Incomplete"
													style="width: 100%;"></input></td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td><label style="font-weight: bold;"> Pin :</label></td>
												<td><input type="text"
													data-dojo-type="dijit.form.ValidationTextBox" name="pin"
													intermediateChanges="false" trim="false" uppercase="false"
													lowercase="false" propercase="false" required="required"
													regExp="^[0-9]{6}$" state="Incomplete" style="width: 100%;"></input>
												</td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td><label style="font-weight: bold;"> E-Mail :</label>
												</td>
												<td><input type="text"
													data-dojo-type="dijit.form.ValidationTextBox"
													name="emailId" intermediateChanges="false" trim="false"
													uppercase="false" lowercase="false" propercase="false"
													style="width: 100%;"></input></td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td><label style="font-weight: bold;"> Mobile
														No:</label></td>
												<td><input type="text"
													data-dojo-type="dijit.form.ValidationTextBox"
													name="shopPhone" intermediateChanges="false" trim="false"
													uppercase="false" lowercase="false" propercase="false"
													required="required" regExp="^[0-9]{10}$" state="Incomplete"
													style="width: 100%;"></input></td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td><label style="font-weight: bold;"> Phone No
														:</label></td>
												<td><input type="text"
													data-dojo-type="dijit.form.ValidationTextBox"
													name="phoneNumber" intermediateChanges="false" trim="false"
													uppercase="false" lowercase="false" propercase="false"
													regExp="^[0-9]{10}$" style="width: 100%;"></input></td>
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
								<form action="/SoftShop/authentications">
									<table style="width: 100%; text-align: right;">
										<tbody style="width: 100%;">
											<tr>
												<td style="width: 25%;"></td>
												<td style="width: 25%;"></td>
												<td style="width: 25%;"></td>
												<td style="width: 25%;"></td>
											</tr>
											<tr>
												<td></td>
												<td><label style="font-weight: bold;"> Choose
														your Username :</label></td>
												<td><input type="text"
													data-dojo-type="dijit.form.ValidationTextBox"
													name="userName" intermediateChanges="false" trim="false"
													uppercase="false" lowercase="false" propercase="false"
													required="required" state="Incomplete" style="width: 100%;"></input>
												</td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td><label style="font-weight: bold;"> Create a
														Password :</label></td>
												<td><input type="password"
													data-dojo-type="dijit.form.ValidationTextBox"
													name="password" intermediateChanges="false" trim="false"
													uppercase="false" lowercase="false" propercase="false"
													required="required" state="Incomplete" style="width: 100%;"></input>
												</td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td style="font-weight: bold;"><label> Confirm
														your Password :</label></td>
												<td><input type="password"
													data-dojo-type="dijit.form.ValidationTextBox"
													intermediateChanges="false" trim="false" uppercase="false"
													lowercase="false" propercase="false" required="required"
													state="Incomplete" style="width: 100%;"></input></td>
												<td></td>
											</tr>
											<tr>
												<td></td>
												<td></td>
												<td style="text-align: left;"><input type="button"
													data-dojo-type="dijit.form.Button"
													intermediateChanges="false" label="Log Up"
													iconClass="dijitNoIcon"></input></td>
												<td></td>
											</tr>
										</tbody>
									</table>
								</form>
							</fieldset>
						</div> </span>
				</div>