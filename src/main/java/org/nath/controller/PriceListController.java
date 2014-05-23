package org.nath.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.nath.model.Brand;
import org.nath.model.Category;
import org.nath.model.CustomerGrade;
import org.nath.model.Item;
import org.nath.model.PriceRange;
import org.nath.model.SellingPrice;
import org.nath.util.PriceRangeForm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/PriceList")

public class PriceListController {
	
	@RequestMapping(value="/addPriceRanges" ,method = RequestMethod.POST,headers = "Accept=application/json")
	@Transactional
	public ModelAndView addPriceRanges(@ModelAttribute("priceRangeForm") PriceRangeForm priceRangeForm,@RequestParam("category") long acategory){
		Category category = Category.findCategory(acategory);
		category.getPriceRange().clear();
		try {
			for(PriceRange i:priceRangeForm.getPriceRanges()){
				System.out.println(i.toJson());
				category.getPriceRange().add(i);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		category.persist();
				
		return null;
	}
	
	@RequestMapping(value="/getPrice" ,method = RequestMethod.GET,headers = "Accept=application/json")
	@Transactional
	public ResponseEntity<String> getPrice(@RequestParam("category") long acategory,
			@RequestParam("brand") long abrand){
			Category category = Category.findCategory(acategory);
						
			List<SellingPrice>sellingPrices = new ArrayList<SellingPrice>();
			
			for(SellingPrice price:category.getSellingPrice()){
				if(price.getBrand().getId().equals(abrand)){
					sellingPrices.add(price);
				}
			}
			
			
			HttpHeaders headers = new HttpHeaders();
		    headers.add("Content-Type", "application/json; charset=utf-8");
		
			return new ResponseEntity<String>(SellingPrice.toJsonArray(sellingPrices), headers, HttpStatus.OK);		
	}
	
	
	@RequestMapping(value="/addPrice" ,method = RequestMethod.POST,headers = "Accept=application/json")
	@Transactional
	public ResponseEntity<String> addPrice(@RequestParam("category") long acategory,
			@RequestParam("brand") long abrand,
			@RequestParam("gradeA") double gradeA,
			@RequestParam("gradeB") double gradeB,
			@RequestParam("gradeC") double gradeC,
			@RequestParam("gradeD") double gradeD,
			HttpSession session
			){
		
		Category category = Category.findCategory(acategory);
		Brand brand = Brand.findBrand(abrand);
		
	
		//updating addPrice Rate
		Set<SellingPrice> sellingPrices = category.getSellingPrice();
		
		for(SellingPrice price:sellingPrices){
			if(price.getBrand().getId().equals(abrand)){
				sellingPrices.remove(price);
			}
		}
			
		SellingPrice sellingPriceA= new SellingPrice();
		sellingPriceA.setBrand(brand);
		sellingPriceA.setCustomerGrade(CustomerGrade.find("A"));
		sellingPriceA.setEntryDate(new Date());
		sellingPriceA.setPrice(gradeA);
		
		
		System.out.println(gradeA);
		
		SellingPrice sellingPriceB= new SellingPrice();
		sellingPriceB.setBrand(brand);
		sellingPriceB.setCustomerGrade(CustomerGrade.find("B"));
		sellingPriceB.setEntryDate(new Date());
		sellingPriceB.setPrice(gradeB);
		
		
		System.out.println(gradeB);
		
		SellingPrice sellingPriceC= new SellingPrice();
		sellingPriceC.setBrand(brand);
		sellingPriceC.setCustomerGrade(CustomerGrade.find("C"));
		sellingPriceC.setEntryDate(new Date());
		sellingPriceC.setPrice(gradeC);
		
		
		System.out.println(gradeC);
		
		SellingPrice sellingPriceD= new SellingPrice();
		sellingPriceD.setBrand(brand);
		sellingPriceD.setCustomerGrade(CustomerGrade.find("D"));
		sellingPriceD.setEntryDate(new Date());
		sellingPriceD.setPrice(gradeD);
		sellingPriceD.persist();
		System.out.println(gradeD);
		
		
		
		sellingPrices.add(sellingPriceA);
		sellingPrices.add(sellingPriceB);
		sellingPrices.add(sellingPriceC);
		sellingPrices.add(sellingPriceD);
		
		category.persist();
		
		//updating sellingPrices ends
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
		
		return new ResponseEntity<String>(headers, HttpStatus.OK);		
	}
	
}
