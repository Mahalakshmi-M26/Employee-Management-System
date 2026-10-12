package org.java.poc.EmployeeManagementSystem.service;

import jakarta.persistence.EntityNotFoundException;
import org.java.poc.EmployeeManagementSystem.entity.Employee;
import org.java.poc.EmployeeManagementSystem.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long employeeID){
        return employeeRepository.findById(employeeID).orElseThrow(() ->
                new EntityNotFoundException("Employee with the ID:"+employeeID+ " is NOT FOUND"));
    }

    public Employee updateEmployee(Long employeeID, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(employeeID).orElseThrow(() ->
                new EntityNotFoundException("Employee with the ID:"+employeeID+ " is NOT FOUND"));
        existingEmployee.setEmployeeFirstName(employee.getEmployeeFirstName());
        existingEmployee.setEmployeeLastName(employee.getEmployeeLastName());
        existingEmployee.setEmployeeEmail(employee.getEmployeeEmail());
        existingEmployee.setEmployeeDepartment(employee.getEmployeeDepartment());
        existingEmployee.setEmployeeJobTitle(employee.getEmployeeJobTitle());
        existingEmployee.setEmployeeSalary(employee.getEmployeeSalary());

        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(Long employeeID)  {
        Employee employee = employeeRepository.findById(employeeID).orElseThrow(() ->
                new EntityNotFoundException("Employee with the ID:"+employeeID+ " is NOT FOUND"));
        employeeRepository.delete(employee);
    }
}
