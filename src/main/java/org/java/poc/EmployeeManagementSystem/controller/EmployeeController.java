package org.java.poc.EmployeeManagementSystem.controller;

import jakarta.validation.Valid;
import org.java.poc.EmployeeManagementSystem.entity.Employee;
import org.java.poc.EmployeeManagementSystem.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid Employee employee){
        Employee savedEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<Employee>(savedEmployee,HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return ResponseEntity.ok().body(employeeService.getAllEmployees());
    }

    @GetMapping("/{employeeID}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeID){
        return ResponseEntity.ok().body(employeeService.getEmployeeById(employeeID));
    }

    @PutMapping("/update/{employeeID}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable (value = "employeeID") Long employeeID, @RequestBody @Valid Employee employee)
    {
        return ResponseEntity.ok().body(employeeService.updateEmployee(employeeID, employee));
    }

    @DeleteMapping("/delete/{employeeID}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable (value = "employeeID") Long employeeID) {
        employeeService.deleteEmployee(employeeID);
        return ResponseEntity.noContent().build();
    }

}

