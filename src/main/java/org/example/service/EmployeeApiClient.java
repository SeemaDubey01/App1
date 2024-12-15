package org.example.service;

import io.netty.handler.timeout.TimeoutException;
import org.example.domainObjects.Employee;
import org.example.domainObjects.Roles;
import org.example.domainObjects.ServiceEmployeeDTO;
import org.example.domainObjects.ServiceResponse;
import org.example.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        var serviceEmployeeDTO = dtoMapping(employeeDTO,role);
        var employeeResponse = webClientBuilder.baseUrl(BASE_URL)
                .build()
                .post()
                .uri("")
                .header("role", role)  // Adding role header
                .bodyValue(serviceEmployeeDTO)  // Sending the employee reponse data as body
                .retrieve()
                .bodyToMono(ServiceResponse.class)// Convert response to Employee object
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(10))
                        .filter(throwable -> throwable instanceof HttpServerErrorException ||
                                throwable instanceof WebClientRequestException && throwable.getCause() instanceof TimeoutException)
                )
                .block();  // Block to wait for the response (synchronous)

        return responseMapping(employeeResponse);
    }

    // Method to get an employee by ID
    @Retryable(maxAttempts = 3, value = { Exception.class }, backoff = @Backoff(delay = 2000))  // Retry 3 times with a 2-second delay
    public Employee getEmployeeById(Long id) {
        var employeeResponse =  webClientBuilder.baseUrl(BASE_URL)
                .build()
                .get()
                .uri("/{id}", id)  // URL with path parameter
                .retrieve()
                .bodyToMono(ServiceResponse.class)
                .retryWhen(Retry.backoff(5, Duration.ofSeconds(10))
                        .filter(throwable -> throwable instanceof HttpServerErrorException ||
                                throwable instanceof WebClientRequestException && throwable.getCause() instanceof TimeoutException)
                )
                .block();
        return responseMapping(employeeResponse);
    }

    // Method to update an employee
    @Retryable(maxAttempts = 3, value = { Exception.class }, backoff = @Backoff(delay = 2000))  // Retry 3 times with a 2-second delay
    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO,String role) {
        var serviceEmployeeDTO = dtoMapping(employeeDTO,role);
        var employeeResponse =   webClientBuilder.baseUrl(BASE_URL)
                .build()
                .put()
                .uri("/{id}", id)  // URL with path parameter
                .bodyValue(serviceEmployeeDTO)  // Sending the updated employee as the request body
                .retrieve()
                .bodyToMono(ServiceResponse.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(10))
                        .filter(throwable -> throwable instanceof HttpServerErrorException ||
                                throwable instanceof WebClientRequestException && throwable.getCause() instanceof TimeoutException)
                )
                .block();
        return responseMapping(employeeResponse);
    }

    // Method to delete an employee
    @Retryable(maxAttempts = 3, value = { Exception.class }, backoff = @Backoff(delay = 2000))  // Retry 3 times with a 2-second delay
    public String deleteEmployee(Long id) {
        return webClientBuilder.baseUrl(BASE_URL)
                .build()
                .delete()
                .uri("/{id}", id)  // URL with path parameter
                .retrieve()
                .bodyToMono(String.class)  // Expecting a String response
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(10))
                        .filter(throwable -> throwable instanceof HttpServerErrorException ||
                                throwable instanceof WebClientRequestException && throwable.getCause() instanceof TimeoutException)
                )
                .block();
    }

    // Method to delete an employee by role ID
    @Retryable(maxAttempts = 3, value = { Exception.class }, backoff = @Backoff(delay = 2000))  // Retry 3 times with a 2-second delay
    public String deleteEmployeeByRoleId(Long roleId) {
        return webClientBuilder.baseUrl(BASE_URL)
                .build()
                .delete()
                .uri("/role/{id}", roleId)  // URL with path parameter for role ID
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(10))
                        .filter(throwable -> throwable instanceof HttpServerErrorException ||
                                throwable instanceof WebClientRequestException && throwable.getCause() instanceof TimeoutException)
                )
                .block();
    }
    private ServiceEmployeeDTO dtoMapping(EmployeeDTO employeeDTO,String role){
      return new ServiceEmployeeDTO(employeeDTO.getFirstName()+" "+employeeDTO.getSurname(), Roles.valueOf(role).getId());
    }
    private Employee responseMapping(ServiceResponse response){
        var nameList = getFirstAndLastNames(response.getName());
        return new Employee(response.getId(),
                nameList.get(0),nameList.get(1), response.getRoleId());

    }

    private List<String> getFirstAndLastNames(String fullName){
        return  Arrays.stream(fullName.split(" "))
                .limit(2)  // Limit to only two parts
                .collect(Collectors.toList());

    }
}
