package org.nath.controller;

import org.nath.model.Bill;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("/printBill")
@Controller
@SessionAttributes("bill")
public class PrintController {
	
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public ModelAndView printBill(@PathVariable("id") Long id){
		
		System.out.println(" printBill");
		ModelAndView modelView = new ModelAndView();
		modelView.addObject("bill", Bill.findBill(id));
		System.out.println(Bill.findBill(id));
		modelView.setViewName("redirect:/Shop_bill.jsp");	
		
		return modelView;	
	}
}
