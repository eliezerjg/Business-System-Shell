package br.com.systemshell.Contracts.Exception;

import org.springframework.http.HttpStatus;

public interface IDefaultException {
    public HttpStatus getHttpStatus();
    public String getCustomMessage();
}
