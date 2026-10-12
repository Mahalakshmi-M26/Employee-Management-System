package org.java.poc.EmployeeManagementSystem.controller;

import org.java.poc.EmployeeManagementSystem.entity.Employee;
import org.java.poc.EmployeeManagementSystem.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void employeeObject(){
        employeeRepository.deleteAll();
        employee = new Employee(null, "Jacob", "Black",
                "jacob@gmail.com", "RAD", "Developer", 25000.0 );
        employee = employeeRepository.save(employee);
    }

    @Test
     public void createEmployeeTest() throws Exception {
            employeeRepository.deleteAll();
            String employeeJson = "{\"employeeFirstName\": \"Jacob\",\"employeeLastName\": \"Black\"," +
                    "\"employeeEmail\": \"jacob@gmail.com\",\"employeeDepartment\": \"RAD\",\"employeeJobTitle\": \"Developer\"," +
                    "\"employeeSalary\": 25000.0}";

            mockMvc.perform(post("/api/employees/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(employeeJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.employeeFirstName").value("Jacob"));
    }

    @Test
    void getAllEmployeesTest() throws Exception {
        mockMvc.perform(get("/api/employees/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    public void getEmployeeByIdTest() throws Exception {
        mockMvc.perform(get("/api/employees/{employeeID}", employee.getEmployeeID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeEmail").value("jacob@gmail.com"));
    }

    @Test
    public void updateEmployeeTest() throws Exception {
        String updateEmployeeJson = "{\"employeeFirstName\": \"Edward\",\"employeeLastName\": \"Cullen\"," +
                "\"employeeEmail\": \"edwar@gmail.com\",\"employeeDepartment\": \"RA\",\"employeeJobTitle\": \"Tester\"," +
                "\"employeeSalary\": 15000.0}";

        mockMvc.perform(put("/api/employees/update/{employeeID}", employee.getEmployeeID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEmployeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeDepartment").value("RA"));
    }

    @Test
    public void deleteEmployeeTest() throws Exception{
        mockMvc.perform(delete("/api/employees/delete/{employeeID}", employee.getEmployeeID()))
                .andExpect(status().isNoContent());
    }

}
