package br.com.systemcore;

import br.com.systemcore.Auth.DTO.RegisterRequest;
import br.com.systemcore.Customer.DTO.CustomerResponse;
import br.com.systemcore.Customer.Models.Customer;
import br.com.systemcore.Customer.Repositories.CustomerRepository;
import br.com.systemcore.Customer.Services.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("John Doe");
        customer1.setEmail("john.doe@example.com");
        Date dateBirth = new Date();
        customer1.setBirthDate(dateBirth);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane Smith");
        customer2.setEmail("jane.smith@example.com");
        customer2.setBirthDate(dateBirth);

        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerResponse> customerResponses = customerService.listCustomers();

        Assertions.assertEquals(2, customerResponses.size());

        CustomerResponse response1 = customerResponses.get(0);
        Assertions.assertEquals("John Doe", response1.getName());
        Assertions.assertEquals("john.doe@example.com", response1.getEmail());

        CustomerResponse response2 = customerResponses.get(1);
        Assertions.assertEquals("Jane Smith", response2.getName());
        Assertions.assertEquals("jane.smith@example.com", response2.getEmail());

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testFindCustomerById() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        Date dateBirth = new Date();
        customer.setBirthDate(dateBirth);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.findCustomerById(1L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(1L, result.get().getId());
        Assertions.assertEquals("John Doe", result.get().getName());
        Assertions.assertEquals("john.doe@example.com", result.get().getEmail());
        Assertions.assertEquals(dateBirth, result.get().getBirthDate());

        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testRegisterCustomer() {
        RegisterRequest request = new RegisterRequest();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password123");
        Date dateBirth = new Date();

        request.setBirthDate(dateBirth);
        request.setPersonNrDocument("1234567890");
        request.setCompanyNrDocument("9876543210");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setName("John Doe");
        savedCustomer.setEmail("john.doe@example.com");
        savedCustomer.setBirthDate(dateBirth);
        savedCustomer.setPersonNrDocument("1234567890");
        savedCustomer.setCompanyNrDocument("9876543210");

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        Customer result = customerService.registerCustomer(request);

        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("John Doe", result.getName());
        Assertions.assertEquals("john.doe@example.com", result.getEmail());
        Assertions.assertEquals(dateBirth, result.getBirthDate());
        Assertions.assertEquals("1234567890", result.getPersonNrDocument());
        Assertions.assertEquals("9876543210", result.getCompanyNrDocument());

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        Date dateBirth = new Date();

        customer.setBirthDate(dateBirth);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.updateCustomer(customer);

        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("John Doe", result.getName());
        Assertions.assertEquals("john.doe@example.com", result.getEmail());
        Assertions.assertEquals(dateBirth, result.getBirthDate());

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        Date dateBirth = new Date();
        customer.setBirthDate(dateBirth);

        customerService.deleteCustomer(customer);

        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    public void testFindByEmail() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        Date dateBirth = new Date();
        customer.setBirthDate(dateBirth);

        when(customerRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.findByEmail("john.doe@example.com");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(1L, result.get().getId());
        Assertions.assertEquals("John Doe", result.get().getName());
        Assertions.assertEquals("john.doe@example.com", result.get().getEmail());
        Assertions.assertEquals(dateBirth, result.get().getBirthDate());

        verify(customerRepository, times(1)).findByEmail("john.doe@example.com");
    }
}

