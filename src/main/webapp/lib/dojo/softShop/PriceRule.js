dojo.provide("softShop.PriceRule");

dojo.require('dijit._Widget');
dojo.require('dijit._Templated');

dojo.declare('softShop.PriceRule', [dijit._Widget,dijit._Templated], {
	widgetsInTemplate: true,
	templatePath: dojo.moduleUrl('softShop','PriceRule.html'),
	fromRange:0,
	toRange:0,
	price:0,
	doubleSidedprice:0,
	
	postCreate: function(){
		this.connect(this.cancel,"onClick",this.destroy);
		
		
	},
	
	singleSidedClicked:function(){
		this.checkExpression(this.singleSided);
	},
	
	doubleSidedClicked:function(){
		this.checkExpression(this.doubleSided);
	},
	
	
	checkExpression:function(widget){
		var str = widget.getValue();
		VALUE = null;
		R=12;
		C=7;
		try{
		dojo.eval("VALUE="+str);
		}catch (e) {
			dojo.removeClass(widget.domNode,"correct");
			dojo.addClass(widget.domNode,"incorrect");		
			alert(e);
			//dojo.attr(widget.domNode.parentNode,"title",e);
		}
		if(VALUE && (dojo.number.parse(VALUE))){
			dojo.removeClass(widget.domNode,"incorrect");
			dojo.addClass(widget.domNode,"correct");			
		}
		else
		{
			dojo.removeClass(widget.domNode,"correct");
			dojo.addClass(widget.domNode,"incorrect");			
		}		
	},
	
	
	destroy:function(){
		this.inherited(arguments);
	}
});
