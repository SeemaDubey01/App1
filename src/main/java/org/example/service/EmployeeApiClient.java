package org.example.service;

import org.example.domainObjects.Employee;
import org.example.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmployeeApiClient {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public EmployeeApiClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    private static final String BASE_URL = "http://localhost:8080/api/employees"; // Update base URL as needed

    // Method to create an employee
    public Employee createEmployee(EmployeeDTO employeeDTO, String role) {
        return webClientBuilder.baseUrl(BASE_URL)
                .build()
                .post()
                .uri("")
                .header("role", role)  // Adding role header
                .bodyValue(employeeDTO)  // Sending the employee data as body
                .retrieve()
                .bodyToMono(Employee.class)  // Convert response to Employee object
                .block();  // Block to wait for the response (synchronous)
    }

    // Method to get an employee by ID
    public Employee getEmployeeById(Long id) {
        return webClientBuilder.baseUrl(BASE_URL)
                .build()
                .get()
                .uri("/{id}", id)  // URL with path parameter
                .retrieve()
                .bodyToMono(Employee.class)
                .block();
    }

    // Method to update an employee
    public Employee updateEmployee(Long id, Employee employee) {
        return webClientBuilder.baseUrl(BASE_URL)
                .build()
                .put()
                .uri("/{id}", id)  // URL with path parameter
                .bodyValue(employee)  // Sending the updated employee as the request body
                .retrieve()
                .bodyToMono(Employee.class)
                .block();
    }

    // Method to delete an employee
    public String deleteEmployee(Long id) {
        return webClientBuilder.baseUrl(BASE_URL)
                .build()
                .delete()
                .uri("/{id}", id)  // URL with path parameter
                .retrieve()
                .bodyToMono(String.class)  // Expecting a String response
                .block();
    }

    // Method to delete an employee by role ID
    public String deleteEmployeeByRoleId(Long roleId) {
        return webClientBuilder.baseUrl(BASE_URL)
                .build()
                .delete()
                .uri("/role/{id}", roleId)  // URL with path parameter for role ID
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
