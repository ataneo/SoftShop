package org.nath.controller;



import java.math.BigInteger;

import javax.servlet.http.HttpSession;

import org.nath.model.Address;
import org.nath.model.CurrentState;
import org.nath.model.District;
import org.nath.model.Employee;
import org.nath.model.PoliceStation;
import org.nath.model.PostOffice;
import org.nath.model.Shop;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/EmployeeResigtration")
@Controller
public class EmployeeEntry {
	@RequestMapping(method = RequestMethod.POST)
	
    public ResponseEntity<String> createFromJson(HttpSession session,@RequestParam("customer_Name") String customer_Name,
    		@RequestParam("customer_Street") String customer_Street,
    		@RequestParam("customer_PostOffice") String customer_PostOffice,
    		@RequestParam("customer_PoliceStation") String customer_PoliceStation,
    		@RequestParam("customer_District") String customer_District,
    		@RequestParam("customer_CurrentState") String customer_CurrentState,
    		@RequestParam("customer_Pin") String customer_Pin,
    		@RequestParam("customer_EmailId") String customer_EmailId,
    		@RequestParam("customer_Mobile") String customer_Mobile,
    		@RequestParam("customer_Reco") String customer_Reco,
			@RequestParam("customer_Grade") String customer_Grade ){
        
		try{
		Employee customer = new Employee();
		customer.setName(customer_Name);

		Address customerAddress = new Address();
		customerAddress.setStreet(customer_Street);
		customerAddress.setPostOffice(PostOffice.find(customer_PostOffice));
		customerAddress.setPoliceStation(PoliceStation.find(customer_PoliceStation));
		customerAddress.setDistrict(District.find(customer_District));
		customerAddress.setCurrentState(CurrentState.find(customer_CurrentState));
		customerAddress.setPin(Integer.parseInt(customer_Pin));
		customerAddress.setEmailId(customer_EmailId);
		customerAddress.setMobileNumber(new BigInteger(customer_Mobile));
		
		customerAddress.persist();
		customer.setAddress(customerAddress);
		
		Shop shop = (Shop)session.getAttribute("Shop");
		shop = Shop.findShop(shop.getId());
		    
        shop.getEmployees().add(customer);
        customer.persist();
        shop.persist();        
        
		}catch(Exception e)
		{
			e.printStackTrace();
		}
        
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

}
