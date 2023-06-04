package br.com.systemcore.Customer.Repositories;

import br.com.systemcore.Customer.DTO.CustomerResponse;
import br.com.systemcore.Customer.Models.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomer {
    Optional<Customer> findByEmail(String email);
    public void deleteCustomer(Customer cliente);

    List<CustomerResponse> listCustomers();

    Optional<Customer> findCustomerById(Long id);

    Customer registerCustomer(Customer customer);

    public Customer updateCustomer(Customer cliente);
}
