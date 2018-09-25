package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.dao.CustomerDAO;
import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	//be kell injekt�lnunk a Customer Service-t
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		
		//visszakapni a customer service-b�l
		List<Customer> theCustomers = customerService.getCustomers();
		
		//hozz�adni a spring mvc modelhez
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		//elmentj�k a vev�t service-�nket haszn�lva
		customerService.saveCustomer(theCustomer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		//visszakapni a customer-t a servicet�l
		Customer theCustomer = customerService.getCustomer(theId);
		//customert be kell �ll�tanunk mint model attrib. hogy el�re felt�lts�k a formot
		theModel.addAttribute("customer", theCustomer);
		//elk�ldeni formunkra
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		//vev� t�rl�se
		customerService.deleteCustomer(theId);
		
		
		return "redirect:/customer/list"; 
	}
	
	 @PostMapping("/search")
	    public String searchCustomers(@RequestParam("theSearchName") String theSearchName,
	                                    Model theModel) {

	        //Serviceb�l vev�k keres�se
	        List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
	                
	        // add the customers to the model
	        theModel.addAttribute("customers", theCustomers);

	        return "list-customers";        
	    }
}
