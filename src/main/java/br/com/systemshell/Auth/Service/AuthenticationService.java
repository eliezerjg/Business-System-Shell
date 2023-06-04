package br.com.systemshell.Auth.Service;

import br.com.systemshell.Auth.DTO.AuthenticationRequest;
import br.com.systemshell.Auth.DTO.AuthenticationResponse;
import br.com.systemshell.Auth.DTO.RegisterRequest;
import br.com.systemshell.Auth.Exceptions.PasswordNotMatchException;
import br.com.systemshell.Auth.Exceptions.UserNameNotFoundException;
import br.com.systemshell.Customer.Models.Customer;
import br.com.systemshell.Customer.Models.Role;
import br.com.systemshell.Customer.Repositories.CustomerRepository;
import br.com.systemshell.Security.Configurations.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Customer user = new Customer();
        user.setName(request.getUser());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setPersonNrDocument(request.getPersonNrDocument());
        user.setBirthDate(request.getBirthDate());
        user.setEmail(request.getEmail());

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Customer user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNameNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException("Usuário ou senha incorreta");
        }

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
