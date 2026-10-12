package org.java.poc.EmployeeManagementSystem.service;

import org.java.poc.EmployeeManagementSystem.entity.Employee;
import org.java.poc.EmployeeManagementSystem.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    public void employeeObject(){
        employee = new Employee(1L, "Jacob", "Black",
                "jacob@gmail.com", "RAD", "Developer", 25000.0 );
    }

    @Test
    public void createEmployeeTest(){
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        Employee savedEmployee = employeeService.createEmployee(employee);
        assertEquals("Jacob", savedEmployee.getEmployeeFirstName());
    }

    @Test
    public void getAllEmployeesTest(){
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        List<Employee> employeeList = employeeService.getAllEmployees();
        assertEquals("Black",employeeList.getFirst().getEmployeeLastName());
    }

    @Test
    public void getEmployeeByIdTest(){
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        Employee actualEmployee = employeeService.getEmployeeById(1L);
        assertEquals("jacob@gmail.com",actualEmployee.getEmployeeEmail());
    }

    @Test
    public void updateEmployeeTest(){
        Employee upadteEmployee = new Employee(1L, "Edward", "Cullen",
                "edwar@gmail.com", "QA", "Tester", 15000.0 );

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(upadteEmployee);

        Employee actualEmployee  = employeeService.updateEmployee(1L, upadteEmployee);
        assertEquals("QA", actualEmployee.getEmployeeDepartment());
    }

    @Test
    public void deleteEmployeeTest(){
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        employeeService.deleteEmployee(1L);
        verify(employeeRepository, times(1)).delete(employee);
    }

}
