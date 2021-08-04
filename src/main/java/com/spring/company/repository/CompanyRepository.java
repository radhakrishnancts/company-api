package com.spring.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.company.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

	@Query("from Company c where c.companyName = ?1")
	Company findByCompanyName(String companyName);
}
