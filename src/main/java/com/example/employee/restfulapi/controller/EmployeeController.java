package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/employees")
public class EmployeeController {
    //在此处完成Employee API
    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Employee getEmployee(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    @RequestMapping(value = "/page/{page}/pageSize/{pageSize}", method = RequestMethod.GET)
    public Page<Employee> getEmployees(@PathVariable int page, @PathVariable int pageSize) {
        return employeeRepository.findAll(new PageRequest(page, pageSize));
    }

    @RequestMapping(value = "/male", method = RequestMethod.GET)
    public List<Employee> getMaleEmployees() {
        return employeeRepository.findByGender("male");
    }

    @RequestMapping(method = RequestMethod.POST)
    public Employee saveEmployee(Employee employee) throws Exception {
        if (employee.getName() == null || employee.getAge() == null || employee.getGender() == null || employee.getSalary() == null || employee.getCompanyId() == null) {
            throw new Exception("Invalid Employee!");
        }
        return employeeRepository.save(employee);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Employee updateEmployee(@PathVariable Long id, @ModelAttribute Employee employee) throws Exception {
        if (getEmployee(id) == null) {
            throw new Exception("Employee not found by id: " + id);
        }
        employeeRepository.updateById(id, employee.getName(), employee.getAge(), employee.getGender(), employee.getSalary(), employee.getCompanyId());
        return employeeRepository.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteEmployee(@PathVariable Long id) throws Exception {
        if (getEmployee(id) == null) {
            throw new Exception("Employee not found by id: " + id);
        }
        employeeRepository.deleteById(id);
        return "success";
    }
}
