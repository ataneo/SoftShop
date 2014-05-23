dojo.provide("softShop.AddStock");

dojo.require('dijit._Widget');
dojo.require('dijit._Templated');

dojo.declare('softShop.AddStock', [ dijit._Widget, dijit._Templated ], {
	widgetsInTemplate : true,
	templatePath : dojo.moduleUrl('softShop', 'AddStock.html'),
	rate : 0,
	priceRules : [],
	area : 1,
	postCreate : function() {
		window.currentAddStock = this;

		this.Amount.setValue(0);
		var self = this;
		this.connect(this.cancel, "onClick", this.calculateBeforeDestroy);

		this.connect(this.length, "onBlur", this.calculateArea);
		this.connect(this.width, "onBlur", this.calculateArea);

		this.connect(this.count, "onBlur", this.countChange);
		this.connect(this.bothSide, "onChange", this.countChange);

		this.connect(this.item_filter, "onChange", function(newItem) {
			saveItemInSession(newItem);
			// self.roll_filter.reset();
			self.category_filter.reset();
			self.brand_filter.reset();
			// numberOfUnit_text.reset();
			// totalCost_input.reset();
			// costPerUnit_input.reset();

			self.item_filter.store.get(newItem).then(function(item) {
				self.current_item = item;
				self.hasRoll = item.hasRoll == "NAN" ? true : false;
				self.width.setDisabled(item.hasRoll == "NAN" ? true : false);
				self.length.setDisabled(item.hasRoll == "NAN" ? true : false);
				// self.unit.innerHTML=item.unit;
			});
		});

		this.connect(this.category_filter, "onChange", function(newCategory) {
			self.getRule(newCategory);
			self.getSellingPrice(newCategory, self.brand_filter.getValue());
		});

		this.connect(this.brand_filter, "onChange", function(newBrand) {
			self.getSellingPrice(self.category_filter.getValue(), newBrand);
		});
	},

	countChange : function() {
		this.getPrice(this.count.getValue(),
				this.bothSide.getValue() == "on" ? true : false);
		this.calculateAmount();
	},

	getPrice : function(count, isDoubleSided) {
		var grade = this.getGrade();
		var sellingPrice = this.prices[grade];
		var self = this;
		var mached = false;
		this.priceRules.forEach(function(priceRule) {
			if (count >= priceRule.fromRange && count <= priceRule.toRange) {
				mached = true;
				var expression = isDoubleSided ? priceRule.doubleSidedprice
						: priceRule.price;
				try {
					R = sellingPrice;
					C = count;
					//alert(R);
					//alert(C);
					dojo.eval("VALUE=" + expression);
					//alert(expression);
					self.rate = VALUE;
					//alert(self.rate);
				} catch (e) {
					console.error(e);
				}
			}
		});

		if (!mached) {
			this.rate = sellingPrice;
		}
	},

	getSellingPrice : function(category, brand) {
		if (category == "" || brand == "")
			return;

		var self = this;
		dojo.xhrGet({
			url : "PriceList/getPrice",
			handleAs : "json",
			content : {
				category : category,
				brand : brand
			},
			handle : function(response) {
				console.info(dojo.toJson(response));

				self.prices = {};

				response.forEach(function(price) {
					self.prices[price.customerGrade.grade] = price.price;
				});
				console.info(dojo.toJson(self.prices));
			}
		});
	},

	getGrade : function() {
		try {
			return dojo.query("[name=customer_Grade]:checked",
					customerForm2.domNode)[0].value;
		} catch (e) {
			alert("Please select a customer");
		}
		;
	},

	getRule : function(newCategory) {
		//alert(newCategory);
		var self = this;
		dojo.xhrGet({
			url : "priceranges/listJsonForCategory/" + newCategory,
			handleAs : "json",
			handle : function(response) {
				console.info(dojo.toJson(response));
				self.priceRules = response;
			}
		});
	},

	calculateArea : function() {
		try {
			var length = this.length.getValue();
			var width = this.width.getValue();
			this.area = length * width;
			this.unit.setValue(this.area);
			this.calculateAmount();
		} catch (e) {
			console.warn(e);
			this.area = 1;
		}
	},

	calculateAmount : function() {
		try {
			this.Amount.setValue(this.rate * this.count.getValue() * this.area
					* (this.bothSide.getValue() == "on" ? 2 : 1));
			this.calculateGrandTotal();
		} catch (e) {
			console.warn(e);
			// this.Amount = 0;
		}
	},

	calculateGrandTotal : function() {
		try {
			var totalAmt = 0;
			dojo.query(".magicRow", dojo.byId("billTab")).forEach(
					function(magicRow) {
						try {
							totalAmt += dijit.byId(magicRow.id).Amount
									.getValue();
						} catch (e) {
							console.warn(e);
						}
					});
			grandTotal.setValue(totalAmt);
		} catch (e) {
			console.warn(e);
		}

	},

	calculateBeforeDestroy : function() {
		this.Amount.setValue(0);
		this.calculateGrandTotal();
		this.destroy();
	},

	disableDropDown : function() {
		this.item_filter.setDisabled(true);
		this.category_filter.setDisabled(true);
		this.brand_filter.setDisabled(true);
	},

	destroy : function() {
		window.currentAddStock = null;
		delete window.currentAddStock;
		dojo.destroy(this.unit);
		this.inherited(arguments);
	}
});
