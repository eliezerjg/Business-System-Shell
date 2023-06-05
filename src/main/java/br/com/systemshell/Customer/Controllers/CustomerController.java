package br.com.systemshell.Customer.Controllers;

import br.com.systemshell.Auth.DTO.DefaultResponse;
import br.com.systemshell.Contracts.Exception.ExceptionDefaultAbstractClass;
import br.com.systemshell.Customer.DTO.UpdatedCustomerResponse;
import br.com.systemshell.Customer.Models.Customer;
import br.com.systemshell.Customer.Services.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@ControllerAdvice
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @PutMapping("/{id}")
    public ResponseEntity<UpdatedCustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
       return ResponseEntity.ok(customerServiceImpl.updateCustomer(id, updatedCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Long id) {
        customerServiceImpl.deleteCustomer(id);
        return ResponseEntity.ok("");
    }

    @ExceptionHandler(ExceptionDefaultAbstractClass.class)
    public ResponseEntity handleException(ExceptionDefaultAbstractClass e) {

        DefaultResponse response = DefaultResponse.builder()
                .title("Erro")
                .message(e.getCustomMessage())
                .error(true)
                .build();
        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }


}
