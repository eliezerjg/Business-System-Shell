package br.com.systemshell.Customer.Services;

import br.com.systemshell.Auth.DTO.RegisterRequest;
import br.com.systemshell.Auth.Exceptions.UserNotFoundException;
import br.com.systemshell.Customer.DTO.CustomerResponse;
import br.com.systemshell.Customer.DTO.UpdatedCustomerResponse;
import br.com.systemshell.Customer.Models.Customer;
import br.com.systemshell.Customer.Repositories.CustomerRepository;
import br.com.systemshell.Customer.Repositories.ICustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements ICustomer {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public List<CustomerResponse> listCustomers() {
        List<Customer> clientes  = customerRepository.findAll();
        List<CustomerResponse> customerResponsesList =
                clientes.stream()
                        .map(cliente -> new CustomerResponse(cliente))
                        .collect(Collectors.toList());
        return customerResponsesList;
    }
    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer registerCustomer(RegisterRequest requestRegistro) {
        Customer customer = new Customer();
        customer.setName(requestRegistro.getName());
        customer.setPassword(requestRegistro.getPassword());
        customer.setEmail(requestRegistro.getEmail());
        customer.setBirthDate(requestRegistro.getBirthDate());
        customer.setPersonNrDocument(requestRegistro.getPersonNrDocument());
        customer.setCompanyNrDocument(requestRegistro.getCompanyNrDocument());
        return customerRepository.save(customer);
    }

    @Override
    public UpdatedCustomerResponse updateCustomer(Long id, Customer updatedCustomer) {
        Customer customer = this.findCustomerById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
        customer.setName(updatedCustomer.getName());
        customer.setBirthDate(updatedCustomer.getBirthDate());
        customer.setEmail(updatedCustomer.getEmail());
        UpdatedCustomerResponse lastUpdatedCustomer = new UpdatedCustomerResponse(customerRepository.save(updatedCustomer));
        return lastUpdatedCustomer;
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = this.findCustomerById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
        customerRepository.delete(customer);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
