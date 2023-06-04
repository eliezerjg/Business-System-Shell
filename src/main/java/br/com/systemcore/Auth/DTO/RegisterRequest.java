package br.com.systemcore.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String user;
    private String password;
    private String email;
    private Date birthDate;
    private String personNrDocument;
    private String companyNrDocument;
 }
