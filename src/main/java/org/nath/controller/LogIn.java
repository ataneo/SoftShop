package org.nath.controller;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.nath.model.Authentication;
import org.nath.model.Employee;
import org.nath.model.Shop;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("/Login")
@Controller
@SessionAttributes("Shop")
public class LogIn {
	@RequestMapping(method = RequestMethod.POST)
    public ModelAndView createFromJson(@RequestParam("userName") String userName,@RequestParam("password") String password, HttpSession httpSession) {
		ModelAndView modelView = new ModelAndView();
		
		TypedQuery<Authentication> query =  Authentication.entityManager()
				.createQuery("from Authentication where userName = ? and password = ?", Authentication.class);
		query.setParameter(1, userName);
		query.setParameter(2, password);
		
		Authentication auth = null;
		try {
			auth = query.getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			modelView.addObject("error", "password not matching");
		}
		
		System.out.println(auth);
		
		if(auth==null)
			modelView.addObject("error", "password not matching");
		else{
			for(Employee emp : Employee.findAllEmployees()){
				if(emp.getAuthentication().equals(auth))
				{
					for(Shop shop:Shop.findAllShops())
						if(shop.getOwner().equals(emp)){
							modelView.addObject("Shop", shop);
							System.out.println(shop.getName());		
							modelView.setViewName("Project");
							return modelView;
						}								
				}
			}
		}
			
		
		modelView.addObject("userName", userName);
		modelView.setViewName("LogIn");
		return modelView;
	}
}
