package br.com.systemcore.Contracts.Exception;

import org.springframework.http.HttpStatus;

public interface IDefaultException {
    public HttpStatus getHttpStatus();
    public String getCustomMessage();
}
