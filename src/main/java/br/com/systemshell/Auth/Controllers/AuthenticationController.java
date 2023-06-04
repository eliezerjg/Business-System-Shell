package br.com.systemshell.Auth.Controllers;

import br.com.systemshell.Auth.DTO.AuthenticationRequest;
import br.com.systemshell.Auth.DTO.AuthenticationResponse;
import br.com.systemshell.Auth.DTO.DefaultResponse;
import br.com.systemshell.Auth.DTO.RegisterRequest;
import br.com.systemshell.Contracts.Exception.ExceptionDefaultAbstractClass;
import br.com.systemshell.Auth.Exceptions.UserNameNotFoundException;
import br.com.systemshell.Auth.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@ControllerAdvice
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest request) {
        AuthenticationResponse auth = null;
        auth = service.register(request);
        return ResponseEntity.ok(auth);
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest request) throws UserNameNotFoundException {
        AuthenticationResponse auth = null;
        auth = service.authenticate(request);
        return ResponseEntity.ok(auth);
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

