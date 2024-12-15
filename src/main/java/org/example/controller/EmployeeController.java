package org.example.controller;

import org.example.domainObjects.Employee;
import org.example.dto.EmployeeDTO;
import org.example.exception.BadRequestException;
import org.example.exception.ResourceNotFoundException;
import org.example.service.EmployeeApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeApiClient employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDTO employeeDTO, @RequestHeader("Role") String role) {
        validateRole(role);
        return ResponseEntity.ok(employeeService.createEmployee(employeeDTO,role));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(employeeService.getEmployeeById(id));
        }catch(WebClientResponseException.NotFound e){
             throw new ResourceNotFoundException("Employee not found for the id "+id);
        }
        }


    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO,@RequestHeader("Role") String role) {
        validateRole(role);
        try {
            return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO, role));
        }catch(WebClientResponseException.NotFound e){
            throw new ResourceNotFoundException("Employee not found for the id "+id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Employee deleted successfully");
        }catch(WebClientResponseException.NotFound e){
            throw new ResourceNotFoundException("Employee not found for the id "+id);
        }
    }

    private void validateRole(String role) {
        if (role.trim().isEmpty()) {
            throw new BadRequestException("Role cannot be null or empty.");
        }

        if (role.length() < 3 || role.length() > 50) {
            throw new BadRequestException("Role length must be between 3 and 50 characters.");
        }
        if (!role.matches("ADMIN|USER|MANAGER")) {
          throw new BadRequestException("Invalid role");
        }
    }

}
