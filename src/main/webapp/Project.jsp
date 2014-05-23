<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="org.nath.model.Shop"%>  
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>SOFT BUSINESS MANAGEMENT</title>
<style>
@import "themes/claro/document.css";

@import "themes/claro/claro.css";

@import "lib/dojo/dojox/grid/resources/claroGrid.css";

@import "lib/dojo/dojox/grid/resources/Grid.css"

@import "lib/dojo/dojox/widget/Toaster/Toaster.css"



html,body {
	height: 100%;
	width: 100%;
}

.claro .correct{
background-color:#D4FF55;
}

.claro .incorrect{
background-color: #FF7F55;
}


.dijitToasterContent {
	padding:1em;
	padding-top:0.25em;
	background:#73c74a;
}
.dijitToasterMessage{ 
	color:#fff;
}
.dijitToasterWarning{ }
.dijitToasterError,
.dijitToasterFatal{
	font-weight:bold;
	color:#fff;
}
.dijitToasterWarning .dijitToasterContent{
	background:#d4d943;
} 
.dijitToasterError .dijitToasterContent{
	background:#c46600;
}
 
.dijitToasterClip {
	position: absolute;
	z-index: 5000;
	overflow: hidden;
}
.dijitToasterContainer {
	display: block;
	position: absolute;
	width: 17.5em;
	margin: 0px;
	font:0.75em;
}

.dijitTextBox {
width: 10em;
}

</style>
<script type="text/javascript" src="lib/dojo/dojo/dojo.js"
	data-dojo-config="'async':false,'locale':'en-us','parseOnLoad':true,'packages':[{'name':'gridx','location':'../gridx'},{'name':'softShop','location':'../softShop'}]"></script>
<script type="text/javascript">
	require([ "dijit/dijit", "dojo/parser","dijit/layout/BorderContainer",
			"dijit/layout/ContentPane", "dijit/layout/AccordionContainer",
			"dijit/layout/TabContainer", "dijit/form/ValidationTextBox",
			"dijit/form/RadioButton", "dijit/form/Button","dojox/widget/Toaster",
			"dijit/form/ComboBox", "dijit/TitlePane", "dijit/form/CheckBox",
			"dojox/io/xhrScriptPlugin", "dojox/grid/DataGrid","softShop/DateTextBox",
			"gridx/core/model/cache/Async", "dojox/data/CsvStore","dijit/Dialog",
			"dijit/form/TimeTextBox", "dojo/data/ItemFileReadStore",
			"dijit/ProgressBar", "dijit/form/CurrencyTextBox","dijit/form/Textarea",
			"dijit/form/FilteringSelect","dojo/data/ObjectStore","softShop/AddStock","softShop/AddStock2","softShop/PriceRule",
			"dojo/store/JsonRest","dojox/form/Manager","dijit/form/DateTextBox"]);
	
	dojo.ready(function(){
		
		
		openBill = function(billId){
			updateBillsearchByCustID.reset();
			updateBillPopUp.show();
			billStore.get(billId).then(function(bill){
				currentBillId = bill.id;
				if(bill.customer)
					updateBillsearchByCustID.setValue(bill.customer.id);
				else
					updateBillsearchByCustID.setValue(1);
				
				if(bill.billDate)
					dojo.byId("dateTimeLabel").innerHTML = new Date(bill.billDate);
				customerForm3.disable(); 

												
				dojo.query(".magicRow","billTabUpdate").forEach(function(priceRuleWid,i){
				try{
					var widget = dijit.byId(dojo.attr(priceRuleWid,"widgetid"));
					widget.destroy();
					}catch(e){}						
				});	

				dojo.query("[widgetid]","billTabUpdate").forEach(function(priceRuleWid,i){
				try{
					var widget = dijit.byId(dojo.attr(priceRuleWid,"widgetid"));
					widget.reset();
					widget.setDisabled(false);
					}catch(e){}						
				});
				
				
						bill.billProduts.forEach(function(billProdut){
								var newRow = new softShop.AddStock2(billProdut);
								dojo.place(newRow.domNode,'pivot','before');
							});	
						
						saveUpdateBill.setDisabled(false);
						billUpdateTotal.setValue(bill.amount);
						
						//updateBillDue.setValue(bill.amount - bill.paidAmount);
						updateBillDeliveryDate.setValue(new Date(bill.delivaryDate));	
													
				});						
		}
		
		
		printBill =function(){
			window.open("/SoftShop/printBill/"+currentBillId,"BillPrint","width=700,height=500,toolbar=yes,location=yes,directories=no,menubar=yes,scrollbars=yes,copyhistory=yes,resizable=yes,left=10,top=10");			
		};
		
		getPrice = function(category,brand){
			if(category==""||brand=="")
				return;
			
			dojo.xhrGet({
				url:"PriceList/getPrice",
				handleAs:"json",
				content:{category:category,brand:brand},
				handle:function(response){
					console.info(dojo.toJson(response));
					gradeA.setValue(0);
					gradeB.setValue(0);
					gradeC.setValue(0);
					gradeD.setValue(0);
					
					response.forEach(function(price){
						var customerGrade = price.customerGrade.grade;
						var priceValue = price.price;
						switch(customerGrade){
						case "A":gradeA.setValue(priceValue);break;
						case "B":gradeB.setValue(priceValue);break;
						case "C":gradeC.setValue(priceValue);break;
						case "D":gradeD.setValue(priceValue);break;
						}
					});
				}
			});				
		};
		
		
		
		updateBillSubmit= function(form){
			try{
				
				dojo.query("[widgetid]",form).forEach(function(input,i){
					try{
					var widget = dijit.byId(dojo.attr(input,"widgetid"));
					widget.setDisabled(false);
					}catch(e){}
				});
				 
				
				dojo.query(".magicRow",form).forEach(function(priceRuleWid,i){
					dojo.query("input",priceRuleWid).forEach(function(input){
						delete input.disabled;
						if(dojo.attr(input,"name")){
							dojo.attr(input,"name","rolls["+i+"]."+dojo.attr(input,"name"));
						}
					});									
				});		
				
				}catch (e) {
					console.warn(e);
				}
											//var values = this.gatherFormValues();
											console.info(dojo.toJson(dojo.formToObject(form),true));
											dojo.xhrPost({
												url:"bills/updateBill/"+currentBillId,
												content:dojo.mixin(dojo.formToObject(form),{
													}),
												handleAs:"json",
												handle:function(response){
													//self.reset(response);
													currentBillId = response.id;
													dojo.query("[widgetid]",form).forEach(function(input,i){
														try{
														var widget = dijit.byId(dojo.attr(input,"widgetid"));
														widget.setDisabled(true);
														jobDetails.setDisabled(true);
														}catch(e){}
													});
													updatePrintButton.setDisabled(false);
													newBill.setDisabled(false);
												}
											});	
											return false;
		};
		
		
		addBillSubmit= function(form){
			try{
				
				dojo.query("[widgetid]",form).forEach(function(input,i){
					try{
					var widget = dijit.byId(dojo.attr(input,"widgetid"));
					widget.setDisabled(false);
					}catch(e){}
				});
				 
				
				dojo.query(".magicRow",form).forEach(function(priceRuleWid,i){
					dojo.query("input",priceRuleWid).forEach(function(input){
						delete input.disabled;
						if(dojo.attr(input,"name")){
							dojo.attr(input,"name","billProduts["+i+"]."+dojo.attr(input,"name"));
						}
					});									
				});		
				
				}catch (e) {
					console.warn(e);
				}
											//var values = this.gatherFormValues();
											console.info(dojo.toJson(dojo.formToObject(form),true));
											dojo.xhrPost({
												url:"bills/addBill",
												content:dojo.mixin(dojo.formToObject(form),{
													customer:customerIdCombo.getValue(),
													jobDescription:jobDetails.getValue()
													}),
												handleAs:"json",
												handle:function(response){
													//self.reset(response);
													currentBillId = response.id;
													billNo.setValue(response.id);
													dojo.query("[widgetid]",form).forEach(function(input,i){
														try{
														var widget = dijit.byId(dojo.attr(input,"widgetid"));
														widget.setDisabled(true);
														jobDetails.setDisabled(true);
														}catch(e){}
													});
													printButton.setDisabled(false);
													newBill.setDisabled(false);
												}
											});	
											return false;
		};
		
		resetBill = function(){
			customerForm2.reset();
			dojo.query("[widgetid]","addBillForm").forEach(function(input,i){
				try{
				var widget = dijit.byId(dojo.attr(input,"widgetid"));
				widget.setDisabled(false);
				jobDetails.setDisabled(true);
				}catch(e){}
			});
			printButton.setDisabled(false);
			newBill.setDisabled(false);
			jobDetails.setDisabled(false);
			dojo.query(".magicRow","addBillForm").forEach(function(priceRuleWid,i){
				try{
					var widget = dijit.byId(dojo.attr(priceRuleWid,"widgetid"));
					widget.destroy();
					}catch(e){}						
			});	
			
			var newRow = new softShop.AddStock({});
			dojo.place(newRow.domNode,'addButton','before');
			
		};
		
		priceListSubmit  = function(form){
			try{
				
				
				dojo.query(".priceRule",form).forEach(function(priceRuleWid,i){
					dojo.query("input",priceRuleWid).forEach(function(input){
						if(dojo.attr(input,"name")){
							dojo.attr(input,"name","priceRanges["+i+"]."+dojo.attr(input,"name"));
						}
					});									
				});		
				
				}catch (e) {
					console.warn(e);
				}
											//var values = this.gatherFormValues();
											console.info(dojo.toJson(dojo.formToObject(form),true));
											dojo.xhrPost({
												url:"PriceList/addPriceRanges",
												content:dojo.mixin(dojo.formToObject(form),{category:category_filter_price.getValue()}),
												handle:function(response){
													//self.reset();	
													dojo.publish("message",{message: "Saved", type: "message", duration: 500});
												}
											});	
											return false;
		};
		
		
		calcGrandTotal= function(){
			
			try{
			totalGrandTotal.setValue(grandTotal.getValue()-customerBalance.getValue());
			}catch(e){ console.warn(e); }
			
			try{
				
				Due.setValue(totalGrandTotal.getValue()-advance.getValue());
				
			}catch(e){
				console.warn(e);
			}
		};
		
		window.renderCustomerAccountGrid = function(customerId){
			
			customerIdStore.get(customerId).then(function(customer){
				customerAccountContainer.setAttribute("title","Customer Account for "+customer.name); ;
			});
			
			dojo.empty(customerAccountContainer.domNode);
			var newDiv = dojo.create("div",{width:"100%"},customerAccountContainer.domNode);
			
			window.customerAccountGridGrid = new dojox.grid.DataGrid({
				store: dojo.data.ObjectStore({objectStore: new dojo.store.JsonRest({target:"/SoftShop/accounttransactions/customer/"+customerId})}),
		        structure: [
					{name:"Date",width:"20%", field:"txDate",formatter: function(txDate){
						var date = new Date(txDate);
					    return date.toLocaleFormat();
					    }},
		            {name:"Bill No",width:"10%", fields:["bill","txDate"],formatter:function(fields){
		            	var bill = fields[0];
		            	var txDate = fields[1];
		            	if(bill==null || txDate > bill.billDate)
		            		return "";
	                    return bill?("<a href='#' onclick='openBill("+bill.id+");'>"+bill.id+" (Rs. "+bill.amount+")</a>"):"";
	                    }},	                
		            {name:"Debit", fields: ["debitOrCredit", "amount","bill","txDate"],formatter: function(fields){
		            	var bill = fields[2];
		            	var txDate = fields[3];
		            	if(!(bill==null || txDate > bill.billDate))
		            		return bill.amount;
	                    return fields[0]=="DEBIT"?fields[1]:"";
                    }},
		            {name:"Credit", fields: ["debitOrCredit", "amount"],formatter: function(fields){
	                    return fields[0]=="CREDIT"?fields[1]:"";
                    }},
                    {name:"Total Bill Amt", fields: ["debitOrCredit", "amount"],formatter: function(fields){
	                    return fields[0]=="CREDIT"?fields[1]:"";
                    }},
                    {name:"Total Credit", fields: ["debitOrCredit", "amount"],formatter: function(fields){
	                    return fields[0]=="CREDIT"?fields[1]:"";
                    }},
                    {name:"Total Due", fields: ["debitOrCredit", "amount"],formatter: function(fields){
	                    return fields[0]=="CREDIT"?fields[1]:"";
                    }}
                    
		        ]
		    }, newDiv); // make sure you have a target HTML element with this id
		    window.customerAccountGridGrid.startup();		    
		}
		
		 window.initShopAccountGrid = function(){
				
				if(window.shopAccountGrid)
				{
					window.shopAccountGrid.render();
					return;
				}
				window.shopAccountGrid = new dojox.grid.DataGrid({
			        store: dojo.data.ObjectStore({objectStore: window.shopAccountGridStore}),
			        structure: [
			            {name:"No", field:"id",width:"4em;"},
			            {name:"Bill No", field:"bill",formatter:function(bill){			            	
		                    return bill?"<a href='#'>"+bill.id+" (Rs. "+bill.amount+")</a>":"";
		                    }},
		                {name:"Customer", field:"bill",formatter:function(bill){			            	
			                    return bill&&bill.customer?"<a href='#'>"+bill.customer.id+"("+bill.customer.name?bill.customer.name:""+")</a>":"";
			                }},
			            {name:"Date", field:"txDate",formatter: function(txDate){
			            	var date = new Date(txDate);
		                    return date.getDay()+"/"+date.getMonth()+"/"+date.getFullYear()+" "+date.getHours()+":"+date.getMinutes();
		                    }},
			            {name:"description", field:"description",width:"30%"},
			            {name:"Debit", fields: ["debitOrCredit", "amount"],formatter: function(fields){
		                    return fields[0]=="DEBIT"?fields[1]:"";
	                    }},
			            {name:"Credit", fields: ["debitOrCredit", "amount"],formatter: function(fields){
		                    return fields[0]=="CREDIT"?fields[1]:"";
	                    }}		            
			        ]
			    }, "ShopAccountGrid"); // make sure you have a target HTML element with this id
			    window.shopAccountGrid.startup();		    
			}
			
		 window.initStockGrid = function(){
			
			if(window.stockGrid)
			{
				window.stockGrid.render();
				return;
			}
			window.stockGrid = new dojox.grid.DataGrid({
		        store: dojo.data.ObjectStore({objectStore: window.stockGridStore}),
		        structure: [
		            {name:"Item", field:"item",width:"15%"},
		            {name:"Category", field:"category"},
		            {name:"Brand", field:"brand"},
		            {name:"Roll", field:"roll"},
		            {name:"Length", field:"length"},
		            {name:"Stock", field:"stock",width:"15%"},
		            {name:"Capacity", field:"max"},
		            {name:"Stock Bar", fields: ["progress", "max","roll","length"],width:"150px",
		            	formatter: function(fields){
		                    var progress = fields[0],
		                    max = fields[1],roll=fields[2],length=fields[3];
		                    try{
		                    	if(!roll=="")
		                    		max = max * roll * length;
		                    }catch(e){ }
		                    
		                    return new dijit.ProgressBar({
		                    	value:progress,
		                    	maximum:max,
		                    	indeterminate:false,
		                    	style:"width: 100%"
		                    });
		                }
		            }
		        ]
		    }, "stockGrid"); // make sure you have a target HTML element with this id
		    window.stockGrid.startup();		    
		}	
		 
		 window.initCustomerGrid = function(){
				
				if(window.customerGrid)
				{
					window.customerGrid.render();
					return;
				}
				window.customerGrid = new dojox.grid.DataGrid({
			        store: dojo.data.ObjectStore({objectStore: window.customerIdStore}),
			        structure: [
					            {name:"ID", field:"id",width:"4em"},
					            {name:"Name", field:"name",width:"10em"},
					            {name:"Street", field:"address",get:function(colIndex,item){
					            		return item.address.street;
					            	}},
					            {name:"Post Office", field:"address",width:"10em",get:function(colIndex,item){
					            	console.info(dojo.toJson(item,true));
				            		return item.address.postOffice.postOffice;
				            	}},
					            {name:"Police Station", field:"address",get:function(colIndex,item){
				            		return item.address.policeStation.policeStation;
				            	}},
					            {name:"District", field:"address",get:function(colIndex,item){
				            		return item.address.district.district;
				            	}},
					            {name:"State", field:"address",width:"10em",get:function(colIndex,item){
				            		return item.address.currentState.currentState;
				            	}},
					            {name:"Pin",  field:"address",get:function(colIndex,item){
				            		return item.address.pin;
				            	}},
					            {name:"E-Mail :", field:"address",width:"20em",get:function(colIndex,item){
				            		return item.address.emailId;
				            	}},
					            {name:"Mobile No :",  field:"address",width:"10em",get:function(colIndex,item){
				            		return item.address.mobileNumber;
				            	}}
					        ]
			    }, "customerGrid"); // make sure you have a target HTML element with this id
			    window.customerGrid.startup();		    
			    dojo.connect(customerGrid,"onSelected",function(index){  mainTabContainer.selectChild(accountPane); accountAccordian.selectChild(customerAccountContainer); renderCustomerAccountGrid(customerGrid.getItem(index).id); })
			};
		
		
		dojo.connect(numberOfUnit_text,"onBlur",function(){			
			roll_filter.store.get(roll_filter.getValue()).then(function(roll){			
				
				var totalUnit = callStock(current_item.hasRoll,roll.firstDimention,roll.secondDimention,numberOfUnit_text.getValue(),null,null,400).totalUnit;
				dojo.byId("totalUnit_label").innerHTML = totalUnit;
				totalCost_input.setDisabled(false);
				costPerUnit_input.setDisabled(false);				
			});			
		});
		
		dojo.connect(costPerUnit_input,"onBlur",function(){					
			roll_filter.store.get(roll_filter.getValue()).then(function(roll){				
				var totalCost = callStock(current_item.hasRoll,roll.firstDimention,roll.secondDimention,numberOfUnit_text.getValue(),totalCost_input.getValue(),costPerUnit_input.getValue(),null).totalCost;
				totalCost_input.setValue(totalCost);
			});
			
		});
		
		dojo.connect(totalCost_input,"onBlur",function(){					
			roll_filter.store.get(roll_filter.getValue()).then(function(roll){				
				var cosetPerUnit = callStock(current_item.hasRoll,roll.firstDimention,roll.secondDimention,numberOfUnit_text.getValue(),null,null,totalCost_input.getValue()).cost;
				costPerUnit_input.setValue(cosetPerUnit);
			});			
		});
		
		
		dojo.connect(roll_filter,"onChange",function(newRoll){
			//saveBrandInSession(newBrand);
			numberOfUnit_text.reset();
			totalCost_input.reset();
			costPerUnit_input.reset();
			dojo.byId("totalUnit_label").innerHTML = "";
			
			
			/*this.store.get(newCategory).then(function(category){
				current_category = category;				
			});*/
			
		});
		
		dojo.connect(category_filter,"onChange",function(newCategory){
			saveCategoryInSession(newCategory);
			roll_filter.reset();
			brand_filter.reset();
			numberOfUnit_text.reset();
			totalCost_input.reset();
			costPerUnit_input.reset();
			dojo.byId("totalUnit_label").innerHTML = "";
			
			
			this.store.get(newCategory).then(function(category){
				current_category = category;				
			});
			
		});
		
		
		dojo.connect(item_filter,"onChange",function(newItem){
			saveItemInSession(newItem);
			roll_filter.reset();
			category_filter.reset();
			brand_filter.reset();
			numberOfUnit_text.reset();
			totalCost_input.reset();
			costPerUnit_input.reset();			
			
			this.store.get(newItem).then(function(item){
				current_item = item;				
				roll_filter.setDisabled(item.hasRoll=="NAN"?true:false);				
				dojo.byId("unit_label").innerHTML=item.unit;
			});
			
		});
		
	
		function callStock(hasRoll, rollSize, rollLanth, numberOfUnit, totalUnit, cost, totalCost)
		{
			if(hasRoll=="NAN")
				hasRoll = false;
		  console.debug(dojo.toJson(arguments,true));
		  if(numberOfUnit!=null){
		                                                       
		                         totalUnit=hasRoll?rollSize * rollLanth * numberOfUnit:numberOfUnit;
		                       
		                       if(cost==null&&totalCost==null){
		                                               alert("Please Give Cost / Total Cost");
		                       }
		                       else {
		                               (cost!=null)?totalCost=totalUnit * cost:cost=totalCost / totalUnit;

		                              var obj = {
		                               totalUnit:totalUnit,
		                               cost:cost,
		                               totalCost:totalCost
		                               };

		                               return(obj);
		                       }
		                       
		         }
		         else alert("Please Give Unit");
		 
		}
		
		 

		
		
		//sets the item in session
		
		saveItemInSession = function(itemId){
			    	dojo.xhrPost({
			    		url:"/SoftShop/items/saveInSession/"+itemId,
			    		sync:true
			    	});			    	
		}
		
		
		saveCategoryInSession = function(categoryId){
			//setting the category in session
	    	dojo.xhrPost({
	    		url:"/SoftShop/categorys/saveInSession/"+categoryId,
	    		sync:true
	    	});
		}
		
		
		window.initAddItem= function(){
			
			dojo.query(".item_disable").style("visibility","hidden");
		
			//code start for displaying item1Grid
			if(!window.item1Grid){
				//window.itemStore = new dojo.store.JsonRest({target:"/SoftShop/items/"});
				window.item1Grid = new dojox.grid.DataGrid({
			        store: dojo.data.ObjectStore({objectStore: window.itemStore}),
			        structure: [
			            {name:"ID", field:"id"},
			            {name:"Name", field:"name"},
			            {name:"Unit", field:"unit"},
			            {name:"Has Roll", field:"hasRoll"}
			        ]
			    }, "item1Grid"); // make sure you have a target HTML element with this id
			    window.item1Grid.startup();
			    if(!window.addNewItem){
					window.addNewItem = function(){
				    	var newData = {
				    					name:item_name.value,
				    					unit:item_unit.value,
				    					hasRoll:item_hasRoll.getValue()?"YES":"NAN"
				    				};
				    	window.itemStore.add(newData).then(function(){
				    		window.item1Grid.render();
				    		item_name.reset();
				    		item_unit.reset();
				    		item_hasRoll.reset();
				    	});
				    };    
				}
			    
			    window.item1Grid.on("RowClick", function(evt){
			    	var idx = evt.rowIndex,item = this.getItem(idx); 	
			    	
			    	
			    	//  get a value out of the item
			        
			        var itemId = this.store.getValue(item, "id");
			        hasRoll = this.store.getValue(item, "hasRoll");
			        
			      	//setting the item in session
			    	saveItemInSession(itemId);
			      	
			        dojo.query(".item_disable").style("visibility","visible");
			        dojo.style(dojo.query(".item_disable")[2],"visibility","hidden");
			        
			        
			        (function(){
			        	
			        	//creating brand
			        	if(!window.brandGrid){
				        	//window.brandStore = new dojo.store.JsonRest({target:"/SoftShop/brands/"});
							window.brandGrid = new dojox.grid.DataGrid({
						        store: dojo.data.ObjectStore({objectStore: window.brandStore}),
						        structure: [
						            {name:"ID", field:"id"},
						            {name:"Name", field:"brandName"}
						        ]
						    }, "brandGrid"); // make sure you have a target HTML element with this id
						    window.brandGrid.startup();
			        	}else{
			        		window.brandGrid.render();
			        	}
					    
			        	if(!window.addNewBrand){
						    window.addNewBrand = function(){
						    	var newData = {
						    				brandName:brand_name.value				    					
						    				};
						    	window.brandStore.add(newData).then(function(){
						    		window.brandGrid.render();
						    		brand_name.reset();				    		
						    	});
						    };
			        	}
					    
					    //end brand
					    
					  //creating Category
					  if(!window.categoryGrid){
			        	//window.categoryStore = new dojo.store.JsonRest({target:"/SoftShop/categorys/"});
						window.categoryGrid = new dojox.grid.DataGrid({
					        store: dojo.data.ObjectStore({objectStore: window.categoryStore}),
					        structure: [
					            {name:"ID", field:"id"},
					            {name:"Name", field:"name"},
					            {name:"Capacity", field:"capacity"}
					            
					        ]
					    }, "categoryGrid"); // make sure you have a target HTML element with this id
					    window.categoryGrid.startup();
					    window.categoryGrid.on("RowClick", function(evt){
					    	var idx = evt.rowIndex,
					    	category = this.getItem(idx);
					    	
					    	 var categoryId = this.store.getValue(category, "id");
					    	 
					    	 alert(hasRoll);
					    	 if(hasRoll!="NAN")
						        	dojo.style(dojo.query(".item_disable")[2],"visibility","visible");
					    	 
					    	 saveCategoryInSession(categoryId);
					    	 
					        (function(){
					        	
					        	//creating roll
					        	if(!window.rollGrid){
						        	//window.rollStore = new dojo.store.JsonRest({target:"/SoftShop/rolls/"});
									window.rollGrid = new dojox.grid.DataGrid({
								        store: dojo.data.ObjectStore({objectStore: window.rollStore}),
								        structure: [
								            {name:"ID", field:"id"},
								            {name:"Roll Size", field:"firstDimention"},
								            {name:"Length", field:"secondDimention"}
								        ]
								    }, "rollGrid"); // make sure you have a target HTML element with this id
								    window.rollGrid.startup();
					        	}else{
					        		window.rollGrid.render();
					        	}
							    
					        	if(!window.addNewRoll){
								    window.addNewRoll = function(){
								    	var newData = {
									    				firstDimention:roll_size.value,					    				
									    				secondDimention:roll_length.value		    					
								    				};
								    	window.rollStore.add(newData).then(function(){
								    		window.rollGrid.render();
								    		roll_size.reset();
								    		roll_length.reset();
								    	});
								    };
					        	}
							    
							    //end roll
							   })();
					    });
					    
					    window.addNewCategory = function(){
					    	var newData = {
					    					name:category_name.value,
					    					capacity:category_capacity.value,
					    				};
					    	window.categoryStore.add(newData).then(function(){
					    		window.categoryGrid.render();
					    		category_name.reset();
					    		category_capacity.reset();
					    	});
					    };
			        }else{
			        	window.categoryGrid.render();
			        }				    
					    
					    
					    
					    //end Category
			        		        	
			        
			        	
			        	
			        })();
			             
			        //  do something with the value.
			    }, true);
			}else{
				window.item1Grid.render();
			}		       
		}
		
	});
</script>
</head>
<body class="claro" dvFlowLayout="true" data-davinci-ws="collapse"
	id="myapp">
	
	<div data-dojo-type="dojo.store.JsonRest" jsId="itemStore" target="/SoftShop/items/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="brandStore" target="/SoftShop/brands/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="categoryStore" target="/SoftShop/categorys/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="rollStore" target="/SoftShop/rolls/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="rollStoreWithCategory" target="/SoftShop/rolls/"></div>	
	<div data-dojo-type="dojo.store.JsonRest" jsId="stockGridStore" target="/SoftShop/StockGrid/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="postofficesStore" target="/SoftShop/postoffices/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="policeStationStore" target="/SoftShop/policestations/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="districtStore" target="/SoftShop/districts/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="currentStateStore" target="/SoftShop/currentstates/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="customerIdStore" target="/SoftShop/customers/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="customerPhoneStore" target="/SoftShop/addresses/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="billStore" target="/SoftShop/bills/"></div>
	<div data-dojo-type="dojo.store.JsonRest" jsId="shopAccountGridStore" target="/SoftShop/accounttransactions/"></div>
	
	
	<div data-dojo-type="dojox.widget.Toaster" messageTopic="message"></div>
	
	
	
	
	
	<div data-dojo-type="dijit.layout.BorderContainer" design="headline"
		persist="false" gutters="true"
		style="z-index: 0; width: 100%; height: 100%; min-width: 800px; min-height: 650px;">
		<div data-dojo-type="dijit.layout.ContentPane" extractContent="false" preventCache="false" preload="false" refreshOnShow="false" region="top" splitter="true" maxSize="Infinity" doLayout="false">
		 <div style="background-image: url('03.png'); text-align: center;">
		   <label style="font-family: cursive; font-size: 3em; color: #ffffff; font-weight: bold;">
		     <c:out value="${model.errors}"/>
		     ${Shop.name}</label>
		 </div>
		</div>
		<div data-dojo-type="dijit.layout.ContentPane" extractContent="false"
			preventCache="false" preload="false" refreshOnShow="false"
			region="center" splitter="false" maxSize="Infinity" doLayout="false">
			<span data-dojo-type="dijit.layout.TabContainer" tabPosition="left-h"
				tabStrip="false" persist="false" jsId="mainTabContainer"
				controllerWidget="dijit.layout.TabController"
				style="min-width: 1em; min-height: 1em; width: 100%; height: 100%;">
				<jsp:include page="Home.jsp"></jsp:include>
				<jsp:include page="Billing.jsp"></jsp:include>
				<jsp:include page="Sells.jsp"></jsp:include>
				<jsp:include page="Customer.jsp"></jsp:include>
				<jsp:include page="Price List.jsp"></jsp:include>
				<jsp:include page="Stock.jsp"></jsp:include>
				<jsp:include page="Account.jsp"></jsp:include>
				<jsp:include page="Settings.jsp"></jsp:include>			
			    <jsp:include page="Log Out.jsp"></jsp:include>	
		    </span>
		</div>

	</div>
</body>
</html>
    