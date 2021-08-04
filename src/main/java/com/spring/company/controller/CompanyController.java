package com.spring.company.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.spring.company.model.AuthRequest;
import com.spring.company.repository.CompanyRepository;
import com.spring.company.response.Company;
import com.spring.company.util.JwtUtil;
import com.spring.stock.service.Stocks;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	CompanyRepository crepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception{
		System.out.println("***********authenticate");
		try{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
		}catch(Exception e){
			throw new Exception("invalid username and password");
		}
		return jwtUtil.generateToken(authRequest.getUsername());
	}

	@GetMapping("/test")
	public String testCompanyApi(){
		System.out.println("inside testCompanyApi ");
		return "company-api";
	}
	
	@PostMapping("/register")
	public String createCompany(@RequestBody Company company) throws Exception{
		try{
			com.spring.company.model.Company c = prepareModel(company);
			
			crepository.save(c);

		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return "Record Saved";
	}

	private com.spring.company.model.Company prepareModel(Company company) {
		com.spring.company.model.Company c = new com.spring.company.model.Company();
		c.setCompanyName(company.getCompanyName());
		c.setCompanyCEO(company.getCompanyCEO());
		c.setTurnOver(company.getTurnOver());
		c.setWebsite(company.getWebsite());
		c.setStockExchange(company.getStockExchange());
		return c;
	}

	@GetMapping("/info/{companycode}")
	public Company getCompanyInfo(@PathVariable("companycode") String companycode ) throws Exception{
		Company c = null;
		try{
			Optional<com.spring.company.model.Company> company = crepository.findById(new Long(companycode));
			//String result = restTemplate.getForObject("http://localhost:8080/stock-api/stock/test", String.class);
			//System.out.println("result:"+result);
			List<Stocks> stocks = restTemplate.getForObject("http://localhost:8080/stock-api/stock/get?companycode="+companycode, List.class);
			c = prepareResponse(company.get());
			c.setStocks(stocks);
			if(c == null) throw new Exception("Record not found");
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return c;
	}
	
	private Company prepareResponse(com.spring.company.model.Company company) {

		Company c = new Company();
		c.setCompanyCode(company.getCompanyCode());
		c.setCompanyName(company.getCompanyName());
		c.setCompanyCEO(company.getCompanyCEO());
		c.setTurnOver(company.getTurnOver());
		c.setWebsite(company.getWebsite());
		c.setStockExchange(company.getStockExchange());
		return c;
	}

	@GetMapping("/getall")
	public List<Company> getAll() throws Exception{
		List<com.spring.company.model.Company> clist = crepository.findAll();
		List<Company> companyList = prepareResponseList(clist);
		if(companyList == null || companyList.isEmpty()) throw new Exception("No Record Available");
		return companyList;
	}
	
	private List<Company> prepareResponseList(List<com.spring.company.model.Company> clist) {
		// TODO Auto-generated method stub
		List<Company> cl = new ArrayList<Company>();
		for(com.spring.company.model.Company company:clist){
			List<Stocks> stocks = restTemplate.getForObject("http://localhost:8080/stock-api/stock/get?companycode="+company.getCompanyCode(), List.class);
			Company c = new Company();
			c.setCompanyCode(company.getCompanyCode());
			c.setCompanyName(company.getCompanyName());
			c.setCompanyCEO(company.getCompanyCEO());
			c.setTurnOver(company.getTurnOver());
			c.setWebsite(company.getWebsite());
			c.setStockExchange(company.getStockExchange());
			c.setStocks(stocks);
			cl.add(c);
		}
		
		return cl;
	}

	@DeleteMapping("/delete/{companycode}")
	public String deleteCompany(@PathVariable("companycode") String companycode) throws Exception{
		try{
			crepository.deleteById(new Long(companycode));
			restTemplate.delete("http://localhost:8080/stock-api/stock/delete/"+companycode);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return "Record Deleted";
	}
			
}
