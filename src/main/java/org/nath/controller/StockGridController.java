package org.nath.controller;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.nath.model.Brand;
import org.nath.model.Category;
import org.nath.model.Item;
import org.nath.model.Roll;
import org.nath.model.Shop;
import org.nath.model.StockCount;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONContext;
import flexjson.JSONSerializer;


@Controller
@RequestMapping("/StockGrid")
public class StockGridController {
	
	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> listJson(HttpSession session) {
		Shop shop = (Shop)session.getAttribute("Shop");
		shop = Shop.findShop(shop.getId());
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        //List<Item> result = Item.findAllItems();
        Set<Item> set=shop.getItems();
        
        int count = 1;
        int i=0;
        System.out.println(set);
        String row="";
        String finalString = "[";
        
        try{
        for(Iterator<Item> itemIterator = set.iterator();itemIterator.hasNext();i++){
        	Item item = itemIterator.next();
        	System.out.println("*********"+item.getName());
        	Set<Brand> brandSet = item.getBrands();
        	Set<Category> categorySet = item.getCategories();
        	for(Iterator<Category> categoryIterator = categorySet.iterator();categoryIterator.hasNext(); ){
        		Category category = categoryIterator.next();  
        		Set <StockCount> stockCounts = category.getStockCount();        		
        		Set<Roll> rollSet = category.getRolls();
        		for(Iterator<Brand> brandIterator = brandSet.iterator();brandIterator.hasNext();){
        			Brand brand = brandIterator.next();
        			if(rollSet.size()>0){
	        			for(Iterator<Roll>rollIterator=rollSet.iterator();rollIterator.hasNext();){
	        				Roll roll = rollIterator.next();
	        				
	        				Iterator<StockCount>ite = stockCounts.iterator();
	        				StockCount stockCount= new StockCount();
	        				stockCount.setBrand(brand);
	        				stockCount.setRoll(roll);
	        				
	        				
        					
	        				StockCount dbStockCount1= null;
	    					StockCount dbStockCount= null;
	    					while(ite.hasNext()){
	    						dbStockCount1 = ite.next();
	    						if(dbStockCount1.equals(stockCount)){
	    							dbStockCount = dbStockCount1;
	    							System.out.println("StockCount found "+ dbStockCount.toString());
	    							break;
	    						}    							
	    					}
	    					if(dbStockCount==null)
	    					{
	    						System.out.println("StockCount not found ");
	    					}
	    					
        					Long stockCoLong;
    						try {
    							stockCoLong = dbStockCount.getStockCount();
    						} catch (Exception e1) {
    							stockCoLong = new Long(0);
    						}      					
        					       				      				
	        				row = "{item:'"+item.getName()+"'";
	        				row = row + ",category:'"+category.getName()+"'";
	        				row = row + ",brand:'"+brand.getBrandName()+"'";
	        				row = row + ",roll:'"+roll.getFirstDimention()+"'";
	        				row = row + ",length:'"+roll.getSecondDimention()+"'";
	        				row = row + ",stock:'"+stockCoLong+" "+item.getUnit()+"'";
	        				row = row + ",progress:"+stockCoLong.longValue();
	        				row = row + ",max:"+category.getCapacity()+"}";
	        				System.out.println(row);
	        				finalString = finalString+ (finalString.equals("[")?"":",") + row ;
	        				count++;
	        			}
        			}else{
        				        				
        				StockCount stockCount= new StockCount();
        				stockCount.setBrand(brand);
        				stockCount.setRoll(null);
        				System.out.println("****************************************************");
        				System.out.println("Trying to find out StockCount in Category : "+category.getName()+ " Brand : "+brand.getBrandName());
        				Iterator<StockCount>ite = stockCounts.iterator();
        				StockCount dbStockCount1= null;
    					StockCount dbStockCount= null;
    					while(ite.hasNext()){
    						dbStockCount1 = ite.next();
    						if(dbStockCount1.equals(stockCount)){
    							dbStockCount = dbStockCount1;
    							System.out.println("StockCount found "+ dbStockCount.toString());
    							break;
    						}    							
    					}
    					if(dbStockCount==null)
    					{
    						System.out.println("StockCount not found ");
    					}
    					
    					Long stockCoLong;
						try {
							stockCoLong = dbStockCount.getStockCount();
						} catch (Exception e1) {
							System.out.println("Got exception " + e1);
							stockCoLong = new Long(0);
						}      					
    					
        				
    					row = "{item:'"+item.getName()+"'";
        				row = row + ",category:'"+category.getName()+"'";
        				row = row + ",brand:'"+brand.getBrandName()+"'";
        				row = row + ",roll:''";
        				row = row + ",length:''";
        				row = row + ",stock:'"+stockCoLong+" "+item.getUnit()+"'";
        				row = row + ",progress:"+stockCoLong.longValue();
        				row = row + ",max:"+category.getCapacity()+"}";
        				
        				System.out.println(row);
        				finalString = finalString+ (finalString.equals("[")?"":",") + row ;
        				count++;
        				
        			}
        		}
        	}
        }
        }catch (Exception e) {
			e.printStackTrace();
		}
        
        finalString = finalString + "]";
        
        System.out.println(finalString);
        
        System.out.println(count);
        System.out.println(i);
        
                 
        return new ResponseEntity<String>(finalString, headers, HttpStatus.OK);
    }

}
