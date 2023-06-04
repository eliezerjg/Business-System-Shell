package br.com.systemshell;

import br.com.systemshell.Auth.DTO.AuthenticationRequest;
import br.com.systemshell.Auth.DTO.AuthenticationResponse;
import br.com.systemshell.Auth.DTO.RegisterRequest;
import br.com.systemshell.Auth.Exceptions.PasswordNotMatchException;
import br.com.systemshell.Auth.Exceptions.UserNameNotFoundException;
import br.com.systemshell.Auth.Service.AuthenticationService;
import br.com.systemshell.Customer.Models.Customer;
import br.com.systemshell.Customer.Models.Role;
import br.com.systemshell.Customer.Repositories.CustomerRepository;
import br.com.systemshell.Security.Configurations.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        RegisterRequest request = new RegisterRequest();
        request.setUser("john.doe");
        request.setPassword("password123");
        request.setPersonNrDocument("1234567890");
        Date dateBirth = new Date();
        request.setBirthDate(dateBirth);
        request.setEmail("john.doe@example.com");

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(repository.save(any(Customer.class))).thenReturn(null);
        when(jwtService.generateToken(any(Customer.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.register(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("jwtToken", response.getToken());

        verify(repository, times(1)).save(customerArgumentCaptor.capture());
        Customer savedCustomer = customerArgumentCaptor.getValue();

        Assertions.assertEquals("john.doe", savedCustomer.getName());
        Assertions.assertEquals("encodedPassword", savedCustomer.getPassword());
        Assertions.assertEquals(Role.USER, savedCustomer.getRole());
        Assertions.assertEquals("1234567890", savedCustomer.getPersonNrDocument());
        Assertions.assertEquals(dateBirth, savedCustomer.getBirthDate());
        Assertions.assertEquals("john.doe@example.com", savedCustomer.getEmail());

        verify(jwtService, times(1)).generateToken(savedCustomer);
    }

    @Test
    public void testAuthenticate() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("john.doe@example.com");
        request.setPassword("password123");

        Customer customer = new Customer();
        customer.setEmail("john.doe@example.com");
        customer.setPassword("encodedPassword");

        when(repository.findByEmail(request.getEmail())).thenReturn(Optional.of(customer));
        when(passwordEncoder.matches(request.getPassword(), customer.getPassword())).thenReturn(true);
        when(jwtService.generateToken(customer)).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.authenticate(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("jwtToken", response.getToken());

        verify(jwtService, times(1)).generateToken(customer);
    }

    @Test
    public void testAuthenticate_UserNameNotFoundException() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("john.doe@example.com");
        request.setPassword("password123");

        when(repository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNameNotFoundException.class, () -> authenticationService.authenticate(request));

        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtService, never()).generateToken(any(Customer.class));
    }

    @Test
    public void testAuthenticate_PasswordNotMatchException() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("john.doe@example.com");
        request.setPassword("password123");

        Customer customer = new Customer();
        customer.setEmail("john.doe@example.com");
        customer.setPassword("encodedPassword");

        when(repository.findByEmail(request.getEmail())).thenReturn(Optional.of(customer));
        when(passwordEncoder.matches(request.getPassword(), customer.getPassword())).thenReturn(false);

        Assertions.assertThrows(PasswordNotMatchException.class, () -> authenticationService.authenticate(request));

        verify(jwtService, never()).generateToken(any(Customer.class));
    }
}
