package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/companies")
public class CompanyController {
    //在此处完成Company API
    @Autowired
    CompanyRepository companyRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Company> getCompanys() {
        return companyRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Company getCompany(@PathVariable Long id) {
        return companyRepository.findById(id);
    }

    @RequestMapping(value = "/{id}/employees", method = RequestMethod.GET)
    public List<Employee> getEmployees(@PathVariable Long id) {
        return companyRepository.findEmployeesByCompanyId(id);
    }

    @RequestMapping(value = "/page/{page}/pageSize/{pageSize}", method = RequestMethod.GET)
    public Page<Company> getCompanys(@PathVariable int page, @PathVariable int pageSize) {
        return companyRepository.findAll(new PageRequest(page, pageSize));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Company saveCompany(Company company) throws Exception {
        if (company.getCompanyName() == null || company.getEmployeesNumber() == null) {
            throw new Exception("Invalid Company!");
        }
        return companyRepository.save(company);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Company updateCompany(@PathVariable Long id, @ModelAttribute Company company) throws Exception {
        if (getCompany(id) == null) {
            throw new Exception("Company not found by id: " + id);
        }
       companyRepository.updateById(id, company.getCompanyName(), company.getEmployeesNumber());
       return companyRepository.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteCompany(@PathVariable Long id) throws Exception {
        if (getCompany(id) == null) {
            throw new Exception("Company not found by id: " + id);
        }
        companyRepository.deleteById(id);
        return "success";
    }
}
