package br.com.systemshell.Customer.Repositories;

import br.com.systemshell.Customer.DTO.CustomerResponse;
import br.com.systemshell.Customer.Models.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomer {
    Optional<Customer> findByEmail(String email);

    public void deleteCustomer(Long id);

    List<CustomerResponse> listCustomers();

    Optional<Customer> findCustomerById(Long id);

    Customer registerCustomer(Customer customer);

}
