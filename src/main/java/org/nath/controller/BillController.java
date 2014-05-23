package org.nath.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.nath.model.Account;
import org.nath.model.AccountTransaction;
import org.nath.model.Bill;
import org.nath.model.BillProduct;
import org.nath.model.Category;
import org.nath.model.Customer;
import org.nath.model.PriceRange;
import org.nath.model.Roll;
import org.nath.model.Shop;
import org.nath.model.StockCount;
import org.nath.model.TranasctionType;
import org.nath.util.Aroll;
import org.nath.util.PriceRangeForm;
import org.nath.util.RollForm;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/bills")
@Controller
public class BillController {
	
	@RequestMapping(value="/addBill" ,method = RequestMethod.POST,headers = "Accept=application/json")
	@Transactional
	public ResponseEntity<String> addPriceRanges(@ModelAttribute("bill") Bill bill,HttpSession session,BindingResult result,@RequestParam("due") double due){
		
		for(ObjectError err:result.getAllErrors()){
			System.out.println("Errors **********");
			System.out.println(err.getObjectName());			
		}
		
		
		//bill.getCustomer().setBalance(bill.getCustomer().getBalance()-due);
		bill.getCustomer().setBalance(-due);		
		
		Shop ashop = (Shop)session.getAttribute("Shop");
		Shop shop = Shop.findShop(ashop.getId());
		
		AccountTransaction trans = new AccountTransaction();
		trans.setAmount(BigDecimal.valueOf(bill.getPaidAmount()));
		trans.setDescription("Payments towards Bill");
		trans.setBill(bill);
		trans.setDebitOrCredit(TranasctionType.CREDIT);
		trans.setTxDate(new Date());
		trans.persist();
		shop.getAccount().getTransactions().add(trans);
		
		
		bill.setBillDate(new Date());
		shop.getBills().add(bill);
		System.out.println(bill.getJobDescription());
		//System.out.println(bill.getCustomer().getId());
		shop.persist();	
		
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<String>(bill.toJson(), headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateBill/{id}" ,method = RequestMethod.POST,headers = "Accept=application/json")
	@Transactional
	public ResponseEntity<String> updateBill(@PathVariable("id") Long id,@ModelAttribute("bill") Bill bill,@ModelAttribute("rollForm") RollForm rollForm,HttpSession session,BindingResult result){
		
		for(ObjectError err:result.getAllErrors()){
			System.out.println("Errors **********");
			System.out.println(err.getObjectName());			
		}
		System.out.println(id);
		Bill updateBill = Bill.findBill(id);
		
		System.out.println(updateBill.toJsonAll());
		
		System.out.println(bill.toJson());
		
		double nowPayingAmt =0.0;
		try {
			nowPayingAmt = bill.getPaidAmount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		updateBill.setDuePromiseDate(bill.getDuePromiseDate());
		//updateBill.setPaidAmount(updateBill.getPaidAmount()+nowPayingAmt);
		
		
		
		
		
		
		Shop shop = Shop.findShop(((Shop)session.getAttribute("Shop")).getId());
		Set<AccountTransaction> transactions =	shop.getAccount().getTransactions();	
		
		AccountTransaction trans = new AccountTransaction();
		trans.setAmount(BigDecimal.valueOf(nowPayingAmt));
		trans.setBill(updateBill);
		trans.setDebitOrCredit(TranasctionType.CREDIT);
		trans.setTxDate(new Date());
		trans.setDescription("Payments towards Bill");
		
		transactions.add(trans);
		
		
		System.out.println(updateBill.getCustomer().getBalance()+nowPayingAmt);
		
		updateBill.getCustomer().setBalance(updateBill.getCustomer().getBalance()+nowPayingAmt);
		updateBill.persist();
		
		shop.persist();
		
		for(int i =0 ;i<rollForm.getRolls().size();i++){
			Aroll roll = rollForm.getRolls().get(i);
			if(roll!=null && !"".equals(roll.getRoll().trim()))
			{
				Roll aroll = Roll.findRoll(Long.parseLong(roll.getRoll().trim()));
				BillProduct billProduct = updateBill.getBillProduts().get(i);
				billProduct.setRoll(new Integer(aroll.getFirstDimention()).toString());
				billProduct.persist();
				
				StockCount stockCount= new StockCount();
				stockCount.setBrand(billProduct.getBrand());
				stockCount.setRoll(aroll);
				
				Iterator<StockCount>ite = billProduct.getCategory().getStockCount().iterator();
				while(ite.hasNext()){
					StockCount dbStockCount1 = ite.next();
					if(dbStockCount1.equals(stockCount)){
						dbStockCount1.setStockCount(Math.round(dbStockCount1.getStockCount()-((Double.parseDouble(billProduct.getArea())*(Double.parseDouble(billProduct.getCount()))))));
						break;
					}    							
				}				
			}
		}	
		
		
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<String>(updateBill.toJson(), headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Bill bill, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, bill);
            return "bills/create";
        }
        uiModel.asMap().clear();
        bill.persist();
        return "redirect:/bills/" + encodeUrlPathSegment(bill.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Bill());
        return "bills/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("bill", Bill.findBill(id));
        uiModel.addAttribute("itemId", id);
        return "bills/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("bills", Bill.findBillEntries(firstResult, sizeNo));
            float nrOfPages = (float) Bill.countBills() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("bills", Bill.findAllBills());
        }
        addDateTimeFormatPatterns(uiModel);
        return "bills/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Bill bill, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, bill);
            return "bills/update";
        }
        uiModel.asMap().clear();
        bill.merge();
        return "redirect:/bills/" + encodeUrlPathSegment(bill.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Bill.findBill(id));
        return "bills/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Bill bill = Bill.findBill(id);
        bill.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/bills";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("bill_billdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("bill_delivarydate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("bill_duepromisedate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, Bill bill) {
        uiModel.addAttribute("bill", bill);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("billproducts", BillProduct.findAllBillProducts());
        uiModel.addAttribute("customers", Customer.findAllCustomers());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        Bill bill = Bill.findBill(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (bill == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(bill.toJsonAll(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Bill> result = Bill.findAllBills();
        return new ResponseEntity<String>(Bill.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        Bill bill = Bill.fromJsonToBill(json);
        bill.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (Bill bill: Bill.fromJsonArrayToBills(json)) {
            bill.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Bill bill = Bill.fromJsonToBill(json);
        if (bill.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (Bill bill: Bill.fromJsonArrayToBills(json)) {
            if (bill.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        Bill bill = Bill.findBill(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (bill == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        bill.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
