package br.com.systemshell.Customer.DTO;

import br.com.systemshell.Customer.Models.Customer;
import br.com.systemshell.Customer.Models.Role;
import lombok.Data;

@Data
public class CustomerResponse {
    String email;
    String personNrDocument;
    String companyNrDocument;
    String name;
    Role role;
    public CustomerResponse(Customer customer){
        this.setEmail(customer.getEmail());
        this.setPersonNrDocument(customer.getPersonNrDocument());
        this.setCompanyNrDocument(customer.getCompanyNrDocument());
        this.setName(customer.getName());
        this.setRole(customer.getRole());
    }

}
