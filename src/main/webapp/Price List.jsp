<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<div data-dojo-type="dijit.layout.ContentPane" title="Price List"
	extractContent="false" preventCache="false" preload="false"
	refreshOnShow="false" doLayout="true" closable="false" splitter="false"
	maxSize="Infinity">
	<form method="post" data-dojo-type="dojox.form.Manager" action="/SoftShop/PriceList">
							<script type="dojo/method" event="onSubmit">
									// do not submit this form
									var values = this.gatherFormValues();
									var self=this;
									dojo.xhrPost({
										url:"PriceList/addPrice",
										content:values,
										handle:function(response){
											//self.reset();		
											dojo.publish("message",{message: "Saved", type: "message", duration: 500});								
										}
									});	
									return false;
									
							</script>	
	<table style="width: 100%; text-align: center;">
		<tr>
			<td><b>Item</b></td>
			<td><b>Category</b></td>
			<td><b>Brand</b></td>
			<td><b>Grade-A</b></td>
			<td><b>Grade-B</b></td>
			<td><b>Grade-C</b></td>
			<td><b>Grade-D</b></td>
			<td></td>
		</tr>
		<tr>
			<td style="width: 12em;"><select
				data-dojo-type="dijit.form.FilteringSelect" name="item"
				jsId="item_filter_price" intermediateChanges="false" trim="false"
				uppercase="false" lowercase="false" propercase="false"
				pageSize="Infinity" store="itemStore" searchAttr="name">
					<script type="dojo/method" event="onChange">
					saveItemInSession(this.value);
					category_filter_price.reset();
					brand_filter_price.reset();
			
					gradeA.reset();
					gradeB.reset();
					gradeC.reset();
					gradeD.reset();
				</script>
			</select></td>
			<td style="width: 12em;"><select
				data-dojo-type="dijit.form.FilteringSelect"
				jsId="category_filter_price" intermediateChanges="false"
				trim="false" name="category" pageSize="Infinity"
				store="categoryStore" searchAttr="name"
				>
				<script type="dojo/method" event="onChange">
					saveCategoryInSession(this.getValue());
					dojo.query(".priceRule","addPriceRangeTab").forEach(function(priceRuleWid){
						dijit.byId(priceRuleWid.id).destroy();													
					});
					dojo.xhrGet({
										url:"priceranges/listJsonForCategory/"+this.getValue(),
										handleAs:"json",
										handle:function(response){
											console.info(dojo.toJson(response));
											response.forEach(function(priceRange){
												var newRow = new softShop.PriceRule(priceRange);
												dojo.place(newRow.domNode,'addButtonTr','before');
											});											 									
										}
									});	
							
					getPrice(this.getValue(),brand_filter_price.getValue());
				</script>
			</select></td>
			<td style="width: 12em;"><select
				data-dojo-type="dijit.form.FilteringSelect"
				jsId="brand_filter_price" intermediateChanges="false" trim="false"
				name="brand" pageSize="Infinity" store="brandStore"
				searchAttr="brandName">
				<script type="dojo/method" event="onChange">
						getPrice(category_filter_price.getValue(),this.getValue());					
				</script>
			</select></td>
			<td><input type="text" jsId="gradeA" name="gradeA"
				data-dojo-type="dijit.form.CurrencyTextBox"
				intermediateChanges="false" trim="false" uppercase="false"
				lowercase="false" propercase="false" required="required"
				currency="Rs. "
				regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
				style="width: 6em;"></input></td>
			<td><input type="text" jsId="gradeB" name="gradeB"
				data-dojo-type="dijit.form.CurrencyTextBox"
				intermediateChanges="false" trim="false" uppercase="false"
				lowercase="false" propercase="false" required="required"
				currency="Rs. "
				regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
				style="width: 6em;"></input></td>
			<td><input type="text" jsId="gradeC" name="gradeC"
				data-dojo-type="dijit.form.CurrencyTextBox"
				intermediateChanges="false" trim="false" uppercase="false"
				lowercase="false" propercase="false" required="required"
				currency="Rs. "
				regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
				style="width: 6em;"></input></td>
			<td><input type="text" jsId="gradeD" name="gradeD"
				data-dojo-type="dijit.form.CurrencyTextBox"
				intermediateChanges="false" trim="false" uppercase="false"
				lowercase="false" propercase="false" required="required"
				currency="Rs. "
				regExp="((?:(?:(?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|)))))|(?:\((?:Rs\.[\s\xa0])?((?:(?:(?:(?:0|[1-9]\d{0,2}(?:[,]\d{3})*)|(?:\d+))(?:\.\d{2}|))|(?:(?:\.\d{2}|))))\))))"
				style="width: 6em;"></input></td>
			<td><input type="submit" data-dojo-type="dijit.form.Button"
				intermediateChanges="false" label="Save" iconClass="dijitNoIcon"></input>
			</td>
		</tr>
	</table>
	</form>
	
	
	<fieldset
						style="border: 2px groove threedface; margin: 2px; padding: 0.75em;">
						<legend style="font-weight: bold;"> Apply Price rules</legend>
	<form id="addPriceRangeTab" method="post" action="/SoftShop/PriceList" onsubmit="arguments[0].preventDefault(); priceListSubmit(this);">
							
	<table style="width: 100%; text-align: center;">
		<tr>
			<td><b>From</b></td>
			<td><b>To</b></td>
			<td><b>Single Sided</b></td>
			<td><b>Double Sided</b></td>
			<td></td>
		</tr>
		<!-- Rules -->
		<tr data-dojo-type="softShop.PriceRule"></tr>
		<tr id="addButtonTr">
			<td><input type="button" data-dojo-type="dijit.form.Button"
				intermediateChanges="false" label="Add new Ranges" onClick="var newRow = new softShop.PriceRule({});dojo.place(newRow.domNode,'addButtonTr','before');"
				iconClass="dijitNoIcon"></input></td>
			<td><b>I</b> = infinity</td>
			<td><b>R</b> = Rate , <b>C</b> = Count <i>(use with
					precaution)</i></td>
			<td>Valid characters : <b>+ - * / ( ) ? : < > <= >= == != &&
					||</b></td>
			<td><input type="submit" data-dojo-type="dijit.form.Button"
				intermediateChanges="false" label="Save" iconClass="dijitNoIcon"></input></td>
		</tr>

	</table>
	</form>
	</fieldset>

</div>