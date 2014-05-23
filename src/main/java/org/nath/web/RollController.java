package org.nath.web;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.nath.model.Category;
import org.nath.model.Item;
import org.nath.model.Roll;
import org.nath.model.Shop;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

@RequestMapping("/rolls")
@Controller
public class RollController {

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Roll roll, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, roll);
            return "rolls/create";
        }
        uiModel.asMap().clear();
        roll.persist();
        return "redirect:/rolls/" + encodeUrlPathSegment(roll.getId().toString(), httpServletRequest);
    }
	
	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson(HttpSession session) {
		Long categoryId  = ((Long)session.getAttribute("categoryID"));
		Category category = Category.findCategory(categoryId);
		
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Set<Roll> result = category.getRolls();
        return new ResponseEntity<String>(Roll.toJsonArray(result), headers, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/category/{category}",headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJsonForCategory(HttpSession session,@PathVariable("category") Long id) {		
		Category category = Category.findCategory(id);		
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        Set<Roll> result = category.getRolls();
        return new ResponseEntity<String>(Roll.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json,HttpSession session) {
		Long categoryId  = ((Long)session.getAttribute("categoryID"));
		Category category = Category.findCategory(categoryId);
		Roll roll = Roll.fromJsonToRoll(json);
		category.getRolls().add(roll);
		roll.persist();
        category.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Roll());
        return "rolls/create";
    }
	
	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
		Roll roll = Roll.findRoll(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (roll == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(roll.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("roll", Roll.findRoll(id));
        uiModel.addAttribute("itemId", id);
        return "rolls/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("rolls", Roll.findRollEntries(firstResult, sizeNo));
            float nrOfPages = (float) Roll.countRolls() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("rolls", Roll.findAllRolls());
        }
        return "rolls/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Roll roll, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, roll);
            return "rolls/update";
        }
        uiModel.asMap().clear();
        roll.merge();
        return "redirect:/rolls/" + encodeUrlPathSegment(roll.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Roll.findRoll(id));
        return "rolls/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Roll roll = Roll.findRoll(id);
        roll.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/rolls";
    }

	void populateEditForm(Model uiModel, Roll roll) {
        uiModel.addAttribute("roll", roll);
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
}
