package br.com.systemcore.Auth.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DefaultResponse {
    private String title;
    private String message;
    private boolean error;
}
