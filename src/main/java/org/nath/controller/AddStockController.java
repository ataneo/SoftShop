package org.nath.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.nath.model.AccountTransaction;
import org.nath.model.Brand;
import org.nath.model.Category;
import org.nath.model.Item;
import org.nath.model.Price;
import org.nath.model.Roll;
import org.nath.model.Shop;
import org.nath.model.StockCount;
import org.nath.model.TranasctionType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/AddStock")
public class AddStockController {
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addStock(@RequestParam("item") long aitem,
			@RequestParam("category") long acategory,
			@RequestParam("brand") long abrand,
			@RequestParam("roll") String aroll,
			@RequestParam("numberOfUnit") long anumberOfUnit,
			@RequestParam("totalUnit") long atotalUnit,
			@RequestParam("costPerUnit") Double acostPerUnit,
			@RequestParam("totalCost") BigDecimal atotalCost,
			HttpSession session
			){
		
		Item item = Item.findItem(aitem);
		Category category = Category.findCategory(acategory);
		Brand brand = Brand.findBrand(abrand);
		
		
		Roll roll=null;
		try {
			roll = Roll.findRoll(Long.parseLong(aroll));
		} catch (NumberFormatException e1) {
		}
		
		System.out.println("Roll"+roll);	
		
		
		
		//updating stock count
		Set <StockCount> stockCounts = category.getStockCount();
		
		System.out.println("Stock counts "+stockCounts);
		
		StockCount stockCount= new StockCount();
		stockCount.setBrand(brand);
		stockCount.setRoll(roll);
		stockCount.setStockCount(atotalUnit);
		
		if(stockCounts.contains(stockCount))
		{
			Iterator<StockCount>ite = stockCounts.iterator();
			StockCount dbStockCount= null;
			while(ite.hasNext()){
				dbStockCount = ite.next();
				if(dbStockCount.equals(stockCount))
					break;
			}
			dbStockCount.setStockCount(dbStockCount.getStockCount()+atotalUnit);
			dbStockCount.merge();
		}
		else{
			stockCounts.add(stockCount);
			stockCount.persist();
		}
		
		category.persist();
		//updating stock count ends
		
		//creating transaction and updating account balance..
		Shop shop = (Shop)session.getAttribute("Shop");
		shop = Shop.findShop(shop.getId());
		
		AccountTransaction tx = new AccountTransaction();
		tx.setAmount(atotalCost);
		tx.setDebitOrCredit(TranasctionType.DEBIT);
		tx.setDescription("Item "+item.getName()+" Category "+category.getName()+" Brand "+brand.getBrandName()+" Roll "+(roll==null?"null":roll.getFirstDimention()));
		tx.setTxDate(new Date());
		shop.getAccount().setBalance(new BigDecimal(shop.getAccount().getBalance().doubleValue()-atotalCost.doubleValue()));
		
		tx.persist();
		
		
		shop.getAccount().getTransactions().add(tx);
		
		shop.persist();
		//end creating transaction and updating account balance..
		
		System.out.println("updating purchase price");
		//updating purchase price
				Set <Price> prices = category.getPurchingPrice();
				TreeSet <Price> treeSet = new TreeSet<Price>(new Comparator<Price>(){
					@Override
					public int compare(Price arg0, Price arg1) {
						try{
						return arg0.getEntryDate().compareTo(arg1.getEntryDate());
						}catch (Exception e) {
							return -1;
						}
					}					
				});
				
				Price price= new Price();
				price.setBrand(brand);
				price.setEntryDate(new Date());
				price.setRoll(roll);
				price.setPrice(acostPerUnit);
				
				
				for(Iterator <Price>ii=prices.iterator();ii.hasNext();)
				{
					Price tempPrice = ii.next();
					if(tempPrice.equals(price))
						treeSet.add(tempPrice);
					
				}
				
				System.out.println("prices "+prices);
				
				System.out.println("treeSet "+treeSet);
				
				Price latestPrice = null;
				
				if(treeSet.size()>0)
					latestPrice = treeSet.first();
				
				System.out.println("Latest purchace price  "+ latestPrice);
				
				//if price is updated add a new price
				if( prices.size()==0 || latestPrice==null ||latestPrice.getEntryDate()==null || (latestPrice.getPrice().doubleValue() != acostPerUnit.doubleValue()))
				{
					prices.add(price);
					price.persist();
					category.persist();
				}				
				//end updating purchase price	
				
				System.out.println("end updating purchase price	");
			
		return null;
		
		
	}
	
}
