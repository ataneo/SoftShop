<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div data-dojo-type="dijit.layout.ContentPane" title="Stock"
					extractContent="false" preventCache="false" preload="false"
					refreshOnShow="false" doLayout="true" closable="false"
					splitter="false" maxSize="Infinity">
					<span data-dojo-type="dijit.layout.AccordionContainer"
						duration="200" persist="false"
						style="min-width: 1em; min-height: 1em; width: 100%; text-align: center; height: 90%;"><div
							data-dojo-type="dijit.layout.ContentPane" onShow="initStockGrid();"
							title="View Or Entry Stock" extractContent="false"
							preventCache="false" preload="false" refreshOnShow="false"
							doLayout="true" selected="selected" closable="false"
							splitter="false" maxSize="Infinity">
							
							<div id="stockGrid" style="height:300px;width:100%"></div>
							
							<fieldset
								style="border: 2px groove threedface; margin: 2px; padding: 0.75em;">
								<legend style="font-weight: bold;"> Entry Stock</legend>
							    <form method="post" data-dojo-type="dojox.form.Manager" jsId="myForm" action="/SoftShop/AddStock">
							    <script type="dojo/method" event="onSubmit">
									// do not submit this form
									var values = this.gatherFormValues();
									var totalUnit = dojo.byId("totalUnit_label").innerHTML;
									dojo.mixin(values,{totalUnit:totalUnit});
									
									console.info(dojo.toJson(values));
									dojo.xhrPost({
										url:"AddStock/",
										content:values,
										handle:function(response){
											window.stockGrid.render();
											item_filter.reset();
											numberOfUnit_text.reset();
											totalCost_input.reset();
											costPerUnit_input.reset();											
										}
									});	
									return false;
									
								</script>	
								<table class="stockEntryTab" style="width: 100%; text-align: center;">
									<tbody>
										<tr>
											<td><label> Select A Item</label></td>
											<td><label> Select A Category</label></td>
											<td><label> Select A Brand</label></td>
											<td><label> Roll</label></td>
											<td><label> Number of units</label></td>
											<td><label> Unit</label></td>
											<td><label> Cost / Unit</label></td>
											<td><label> Total Cost</label></td>
										</tr>
										<tr>
											<td>
												
													<select data-dojo-type="dijit.form.FilteringSelect" name="item" jsId="item_filter"
														intermediateChanges="false" trim="false"
														uppercase="false" lowercase="false" propercase="false"
														 pageSize="Infinity" store="itemStore"  searchAttr="name"
														>														
													</select>
												</td>
											<td>
												<select data-dojo-type="dijit.form.FilteringSelect" jsId="category_filter"
														intermediateChanges="false" trim="false" name="category"
														pageSize="Infinity" store="categoryStore" searchAttr="name" onChange="saveCategoryInSession(arguments[0]);"
														>														
													</select>
												</td>
											<td>
												<select data-dojo-type="dijit.form.FilteringSelect" jsId="brand_filter"
														intermediateChanges="false" trim="false" name="brand"
														pageSize="Infinity" store="brandStore" searchAttr="brandName"
														>
													</select>
												</td>
											<td>
												<select
												data-dojo-type="dijit.form.FilteringSelect"  name="roll"
												intermediateChanges="false" jsId="roll_filter"
												store="rollStore" searchAttr="firstDimention"
												pageSize="Infinity">													
											</select></td>
											<td><input type="text" jsId="numberOfUnit_text" name="numberOfUnit"
												data-dojo-type="dijit.form.ValidationTextBox" 
												intermediateChanges="false" trim="false" uppercase="false"
												lowercase="false" propercase="false" required="required"
												state="Error" ></input></td>
											<td width="20px"><label id="totalUnit_label"> </label> <label id="unit_label"></label></td>
											<td><input type="text" jsId="costPerUnit_input" name="costPerUnit"
												data-dojo-type="dijit.form.CurrencyTextBox"
												intermediateChanges="false" trim="false" uppercase="false" disabled="disabled"
												lowercase="false" propercase="false" required="required"
												currency="Rs. "
												regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
												state="Incomplete" ></input></td>
											<td><input type="text" jsId="totalCost_input" name="totalCost"
												data-dojo-type="dijit.form.CurrencyTextBox"
												intermediateChanges="false" trim="false" uppercase="false"
												lowercase="false" propercase="false" required="required" disabled="disabled"
												currency="Rs. "
												regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
												state="Incomplete" ></input></td>
											<td style="width: 126px; height: 27px;"><input 
												type="submit" data-dojo-type="dijit.form.Button"
												intermediateChanges="false" label="Save"
												iconClass="dijitNoIcon"></input></td>
										</tr>
									</tbody>
								</table>
								</form>
							</fieldset>
						</div>
						<div jsId="addItemPane" data-dojo-type="dijit.layout.ContentPane"
							title="Add Or Edit Product" extractContent="false"
							preventCache="false" preload="false" refreshOnShow="false"
							doLayout="true" closable="false" splitter="false" onShow=" initAddItem();"
							maxSize="Infinity">
							<table style="width: 100%;">
								<tbody>
									<tr style=" width: 100%; ">
										<td style="width: 50%;">
											<fieldset
												style="border: 2px groove threedface; margin: 2px; padding: 0.75em; text-align: center;">
												<legend> Item</legend>
												<table style="border: none; width: 100%; height: 100%;">
													<tbody>
														<tr>
															<td colspan="5">
																<div id="item1Grid" style="height:150px;width:100%">
															</td>
														</tr>
														<!-- tr style="font-weight: bold; text-decoration: underline;">
															<td style="width: 25%;"></td>
															<td style="width: 25%;"></td>
															<td style="width: 25%;"></td>
															<td style="width: 10%;"></td>
															<td style="width: 15%;"></td>
														</tr>
														<tr style="font-weight: bold;">
															<td><label> Sl. No.</label></td>
															<td><label> Item</label></td>
															<td><label> Unit</label></td>
															<td><label> Roll</label></td>
															<td style="width: 25%;"></td>
														</tr>
														<tr>
															<td><label> 1</label></td>
															<td><label> Flex</label></td>
															<td><label> Sq Ft</label></td>
															<td><input type="checkbox"
																data-dojo-type="dijit.form.CheckBox"
																intermediateChanges="false" iconClass="dijitNoIcon"></input>
															</td>
															<td style="width: 25%;"></td>
														</tr>
														<tr>
															<td><label> 2</label></td>
															<td><label> Digital Media</label></td>
															<td><label> Pices</label></td>
															<td><input type="checkbox"
																data-dojo-type="dijit.form.CheckBox"
																intermediateChanges="false" iconClass="dijitNoIcon"></input>
															</td>
															<td style="width: 25%;"></td>
														</tr-->
														<tr>
															<td><label> Add A New Item</label></td>
															<td><input jsId="item_name" type="text"
																data-dojo-type="dijit.form.ValidationTextBox"
																intermediateChanges="false" trim="false"
																uppercase="false" lowercase="false" propercase="false"
																required="required" state="Incomplete"
																style="width: 150px;" name="name"></input></td>
															<td><input jsId="item_unit" type="text"
																data-dojo-type="dijit.form.ValidationTextBox"
																intermediateChanges="false" trim="false"
																uppercase="false" lowercase="false" propercase="false"
																required="required" state="Incomplete"
																style="width: 100px;" name="unit"></input></td>
															<td><input jsId="item_hasRoll" type="checkbox"
																data-dojo-type="dijit.form.CheckBox"
																intermediateChanges="false" iconClass="dijitNoIcon" name="hasRoll"></input>
															</td>
															<td style="width: 25%;"><input type="button"
																data-dojo-type="dijit.form.Button"
																intermediateChanges="false" label="Save"
																iconClass="dijitNoIcon" onClick="addNewItem();">
																</input>

															</td>
														</tr>
														<tr>
															<td></td>
															<td></td>
															<td></td>
															<td></td>
															<td style="width: 25%;"></td>
														</tr>
													</tbody>
												</table>
												
											</fieldset></td>
										<td class="item_disable" style="width: 50%;">
											<fieldset
												style="border: 2px groove threedface; margin: 2px; padding: 0.75em;">
												<legend> Brand</legend>
												<table style="width: 100%; height: 100%;">
													<tbody>
														<tr>
															<td colspan="3">
																<div id="brandGrid" style="height: 150px;width:100%">
															</td>
														</tr>
														<tr>
															<td><label> Add A New Brand :</label></td>
															<td><input type="text" jsId="brand_name"
																data-dojo-type="dijit.form.ValidationTextBox"
																intermediateChanges="false" trim="false"
																uppercase="false" lowercase="false" propercase="false"
																required="required" state="Incomplete"
																style="width: 150px;"></input></td>
															<td><input type="button"
																data-dojo-type="dijit.form.Button"
																intermediateChanges="false" label="Save"
																iconClass="dijitNoIcon" onClick="addNewBrand();"></input></td>
														</tr>
														<tr>
															<td></td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
												</table>
											</fieldset></td>
									</tr>
									<tr class="item_disable" style="width: 100%;">
										<td>
											<fieldset
												style="border: 2px groove threedface; margin: 2px; padding: 0.75em;">
												<legend> Category</legend>
												<table style="width: 100%; height: 100%;">
													<tbody>
														<tr>
															<td colspan="4">
																<div id="categoryGrid" style="height: 150px;width:100%">
															</td>
														</tr>
														<!-- tr style="font-weight: bold; text-decoration: underline;">
															<td style="width: 25%;"></td>
															<td style="width: 25%;"></td>
															<td style="width: 25%;"></td>
															<td style="width: 25%;"></td>
														</tr>
														<tr style="font-weight: bold;">
															<td><label> Sl. No.</label></td>
															<td><label> Category</label></td>
															<td><label> capacity</label></td>
															<td></td>
														</tr>
														<tr>
															<td><label> 1</label></td>
															<td><label> F/L</label></td>
															<td><label> 150 Ft</label></td>
															<td></td>
														</tr>
														<tr>
															<td><label> 2</label></td>
															<td><label> B/L</label></td>
															<td><label> 150 Ft</label></td>
															<td></td>
														</tr-->
														<tr>
															<td><label> Add A New Category :</label></td>
															<td><input type="text" jsId="category_name"
																data-dojo-type="dijit.form.ValidationTextBox"
																intermediateChanges="false" trim="false"
																uppercase="false" lowercase="false" propercase="false"
																required="required" state="Incomplete"
																style="width: 150px;"></input></td>
															<td><input type="text" jsId="category_capacity"
																data-dojo-type="dijit.form.ValidationTextBox"
																intermediateChanges="false" trim="false"
																uppercase="false" lowercase="false" propercase="false"
																required="required" state="Incomplete"
																style="width: 50px;"></input></td>
															<td><input type="button"
																data-dojo-type="dijit.form.Button" onClick="addNewCategory();"
																intermediateChanges="false" label="Save"
																iconClass="dijitNoIcon"></input></td>
														</tr>
														<tr>
															<td></td>
															<td></td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
												</table>
											</fieldset></td>
										<td class="item_disable">
											<fieldset
												style="border: 2px groove threedface; margin: 2px; padding: 0.75em;">
												<legend> Roll</legend>
												<table style="width: 100%; height: 100%;">
													<tbody>
														<tr>
															<td colspan="3">
																<div id="rollGrid" style="height: 150px;width:100%">
															</td>
														</tr>
														<!-- tr style="font-weight: bold; text-decoration: underline;">
															<td style="width: 30%;"></td>
															<td></td>
															<td style="width: 30%;"></td>
														</tr>
														<tr style="font-weight: bold;">
															<td><label> Sl. No.</label></td>
															<td><label> Roll</label></td>
															<td></td>
														</tr>
														<tr>
															<td><label> 1</label></td>
															<td><label> 3 Ft</label></td>
															<td></td>
														</tr>
														<tr>
															<td><label> 2</label></td>
															<td><label> 4 Ft</label></td>
															<td></td>
														</tr-->
														<tr>
															<td><label> Add A New Roll :</label></td>
															<td><input type="text" jsId="roll_size"
																data-dojo-type="dijit.form.ValidationTextBox"
																intermediateChanges="false" trim="false"
																uppercase="false" lowercase="false" propercase="false"
																required="required" state="Incomplete"
																style="width: 100px;"></input></td>
															<td><input type="text" jsId="roll_length"
																data-dojo-type="dijit.form.ValidationTextBox"
																intermediateChanges="false" trim="false"
																uppercase="false" lowercase="false" propercase="false"
																required="required" state="Incomplete"
																style="width: 100px;"></input></td>
															<td><input type="button"
																data-dojo-type="dijit.form.Button" onClick="addNewRoll();"
																intermediateChanges="false" label="Save"
																iconClass="dijitNoIcon"></input></td>
														</tr>
														<tr>
															<td></td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
												</table>
											</fieldset></td>
									</tr>
								</tbody>
							</table>
						</div> </span>
				</div>