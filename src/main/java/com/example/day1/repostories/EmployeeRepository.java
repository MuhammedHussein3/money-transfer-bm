package com.example.day1.repostories;

import com.example.day1.entites.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {



    @Query("""
SELECT e FROM Employee e
WHERE e.email = ?1
""")
     Employee findByEmail(String email);

    @Transactional(propagation = Propagation.REQUIRED)
    Employee save(Employee employee);
}
