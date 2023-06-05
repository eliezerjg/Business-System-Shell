package br.com.systemshell.Customer.DTO;

import br.com.systemshell.Customer.Models.Customer;
import lombok.Data;

import java.util.Date;
@Data
public class UpdatedCustomerResponse {
    String email;
    Date birthDate;
    String name;

    public UpdatedCustomerResponse(Customer customer){
        this.setEmail(customer.getEmail());
        this.setBirthDate(customer.getBirthDate());
        this.setName(customer.getName());
    }

}
