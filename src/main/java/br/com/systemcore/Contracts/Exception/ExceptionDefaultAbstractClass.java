package br.com.systemcore.Contracts.Exception;

import org.springframework.http.HttpStatus;

public class ExceptionDefaultAbstractClass extends RuntimeException implements IDefaultException {
    protected HttpStatus httpStatus = null;
    protected String customMessage;

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
    @Override
    public String getCustomMessage() {
        return this.customMessage;
    }


}
