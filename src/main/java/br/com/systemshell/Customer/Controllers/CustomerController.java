package br.com.systemshell.Customer.Controllers;

import br.com.systemshell.Customer.DTO.UpdatedCustomerResponse;
import br.com.systemshell.Customer.Models.Customer;
import br.com.systemshell.Customer.Services.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @PutMapping("/{id}")
    public ResponseEntity<UpdatedCustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        Optional<Customer> customerOptional = customerServiceImpl.findCustomerById(id);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setName(updatedCustomer.getName());
            customer.setBirthDate(updatedCustomer.getBirthDate());
            customer.setEmail(updatedCustomer.getEmail());
            return ResponseEntity.ok(new UpdatedCustomerResponse(customerOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> customer = customerServiceImpl.findCustomerById(id);
        if (customer.isPresent()) {
            customerServiceImpl.deleteCustomer(customer.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
