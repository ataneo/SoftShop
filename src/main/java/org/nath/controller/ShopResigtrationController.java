package org.nath.controller;



import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.nath.model.Account;
import org.nath.model.Address;
import org.nath.model.Authentication;
import org.nath.model.CurrentState;
import org.nath.model.District;
import org.nath.model.Employee;
import org.nath.model.PoliceStation;
import org.nath.model.PostOffice;
import org.nath.model.Shop;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("/ShopResigtration")
@Controller
public class ShopResigtrationController {
	@RequestMapping(method = RequestMethod.POST)
	@Transactional
    public ModelAndView createFromJson(@RequestParam("shopName") String shopName,
    		@RequestParam("shop_street") String shop_street,
    		@RequestParam("shop_postOffice") String shop_postOffice,
    		@RequestParam("shop_policeStation") String shop_policeStation,
    		@RequestParam("shop_district") String shop_district,
    		@RequestParam("shop_currentState") String shop_currentState,
    		@RequestParam("shop_pin") String shop_pin,
    		@RequestParam("shop_emailId") String shop_emailId,
    		@RequestParam("shop_mobileNumber") String shop_mobileNumber,
    		@RequestParam("owner_name") String owner_name,
    		@RequestParam("owner_street") String owner_street,
    		@RequestParam("owner_postOffice") String owner_postOffice,
    		@RequestParam("owner_policeStation") String owner_policeStation,
    		@RequestParam("owner_district") String owner_district,
    		@RequestParam("owner_currentState") String owner_currentState,
    		@RequestParam("owner_pin") String owner_pin,
    		@RequestParam("owner_emailId") String owner_emailId,
    		@RequestParam("owner_mobileNumber") String owner_mobileNumber,
    		@RequestParam("admin_password") String admin_password,
    		@RequestParam("userName") String userName,
    		@RequestParam("password") String password,
    		@RequestParam("repeat_password") String repeat_password) {
		
		
		System.out.println("Hello");
        
		try{
		Shop shop = new Shop();
		shop.setName(shopName);

		Address shopAddress = new Address();
		shopAddress.setStreet(shop_street);
		shopAddress.setPostOffice(PostOffice.find(shop_postOffice));
		shopAddress.setPoliceStation(PoliceStation.find(shop_policeStation));
		shopAddress.setDistrict(District.find(shop_district));
		shopAddress.setCurrentState(CurrentState.find(shop_currentState));
		shopAddress.setPin(Integer.parseInt(shop_pin));
		shopAddress.setEmailId(shop_emailId);
		shopAddress.setMobileNumber(new BigInteger(shop_mobileNumber));
		
		shopAddress.persist();
		shop.setAddress(shopAddress);
		
		
		Employee owner = new Employee();
		owner.setName(owner_name);
		
		Address ownerAddress = new Address();
		ownerAddress.setStreet(owner_street);
		ownerAddress.setPostOffice(PostOffice.find(owner_postOffice));
		ownerAddress.setPoliceStation(PoliceStation.find(owner_policeStation));
		ownerAddress.setDistrict(District.find(owner_district));
		ownerAddress.setCurrentState(CurrentState.find(owner_currentState));
		ownerAddress.setPin(Integer.parseInt(owner_pin));
		ownerAddress.setEmailId(owner_emailId);
		ownerAddress.setMobileNumber(new BigInteger(owner_mobileNumber));
		
		owner.setAddress(ownerAddress);
		ownerAddress.persist();
		
		owner.persist();
		
		Authentication auth = new Authentication();
		auth.setUserName(userName);
		auth.setPassword(password);
		auth.persist();
		if(!password.equals(repeat_password))
			throw new RuntimeException("repeat password does not match");
		if(!"admin".equals(admin_password))
			throw new RuntimeException("Admin authentication not matching");
		
		owner.setAuthentication(auth);
			
		shop.setOwner(owner);
		
        Account account = new Account();
        account.setBalance(new BigDecimal(0));
        account.setDescription("Account created for "+shopName);
        
        account.persist();
        
        shop.setAccount(account);
            
        shop.persist();
        shop.flush();
        
		}catch(Exception e)
		{
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e.toString());
			return new ModelAndView("redirect:/Registration.jsp");
		}
        
        return new ModelAndView("redirect:/LogIn.jsp");
    }

}
