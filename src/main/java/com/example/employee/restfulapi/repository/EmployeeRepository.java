package com.example.employee.restfulapi.repository;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAll();

    Employee findById(Long id);

    Page<Employee> findAll(Pageable pageable);

    List<Employee> findByGender(String gender);

    Employee save(Employee employee);

    @Modifying
    @Transactional
    @Query("update Employee u set u.name = ?2, u.age = ?3, u.gender = ?4, u.salary = ?5, u.companyId = ?6 where u.id = ?1")
    int updateById(Long id, String name, Integer age, String gender, Integer salary, Long companyId);

    @Transactional
    void deleteById(Long id);
}
