package org.nath.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.nath.model.AccountTransaction;
import org.nath.model.Shop;
import org.nath.model.TranasctionType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/accounttransactions")
@Controller
public class AccountTransactionController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid AccountTransaction accountTransaction, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, accountTransaction);
            return "accounttransactions/create";
        }
        uiModel.asMap().clear();
        accountTransaction.persist();
        return "redirect:/accounttransactions/" + encodeUrlPathSegment(accountTransaction.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new AccountTransaction());
        return "accounttransactions/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("accounttransaction", AccountTransaction.findAccountTransaction(id));
        uiModel.addAttribute("itemId", id);
        return "accounttransactions/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("accounttransactions", AccountTransaction.findAccountTransactionEntries(firstResult, sizeNo));
            float nrOfPages = (float) AccountTransaction.countAccountTransactions() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("accounttransactions", AccountTransaction.findAllAccountTransactions());
        }
        return "accounttransactions/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid AccountTransaction accountTransaction, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, accountTransaction);
            return "accounttransactions/update";
        }
        uiModel.asMap().clear();
        accountTransaction.merge();
        return "redirect:/accounttransactions/" + encodeUrlPathSegment(accountTransaction.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, AccountTransaction.findAccountTransaction(id));
        return "accounttransactions/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        AccountTransaction accountTransaction = AccountTransaction.findAccountTransaction(id);
        accountTransaction.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/accounttransactions";
    }

	void populateEditForm(Model uiModel, AccountTransaction accountTransaction) {
        uiModel.addAttribute("accountTransaction", accountTransaction);
        uiModel.addAttribute("tranasctiontypes", Arrays.asList(TranasctionType.values()));
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
        AccountTransaction accountTransaction = AccountTransaction.findAccountTransaction(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (accountTransaction == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(accountTransaction.toJson(), headers, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/customer/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJsonForCustomer(@PathVariable("id") Long custId,HttpSession session) {
		
		Shop ashop = (Shop)session.getAttribute("Shop");
		Shop shop = Shop.findShop(ashop.getId());
		List<AccountTransaction> accountTransaction = new ArrayList<AccountTransaction>();
		for(AccountTransaction tran:shop.getAccount().getTransactions()){
			try {
				if(tran.getBill().getCustomer().getId()==custId){
					accountTransaction.add(tran);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		Set<AccountTransaction> set = new TreeSet<AccountTransaction>(new Comparator<AccountTransaction>() {
       	 public int compare(AccountTransaction obj1,AccountTransaction obj2){
       		 return (int) (obj1.getId() - obj2.getId());
       	 }
		});
       
       set.addAll(accountTransaction);
		
		
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
       
        return new ResponseEntity<String>(AccountTransaction.toJsonArray(set), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson(HttpSession session) {
		Shop ashop = (Shop)session.getAttribute("Shop");
		Shop shop = Shop.findShop(ashop.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Set<AccountTransaction> set = new TreeSet<AccountTransaction>(new Comparator<AccountTransaction>() {
        	 public int compare(AccountTransaction obj1,AccountTransaction obj2){
        		 return (int) (obj1.getId() - obj2.getId());
        	 }
		});
        
        set.addAll(shop.getAccount().getTransactions());
        return new ResponseEntity<String>(AccountTransaction.toJsonArray(set), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        AccountTransaction accountTransaction = AccountTransaction.fromJsonToAccountTransaction(json);
        accountTransaction.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (AccountTransaction accountTransaction: AccountTransaction.fromJsonArrayToAccountTransactions(json)) {
            accountTransaction.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        AccountTransaction accountTransaction = AccountTransaction.fromJsonToAccountTransaction(json);
        if (accountTransaction.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (AccountTransaction accountTransaction: AccountTransaction.fromJsonArrayToAccountTransactions(json)) {
            if (accountTransaction.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        AccountTransaction accountTransaction = AccountTransaction.findAccountTransaction(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (accountTransaction == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        accountTransaction.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
