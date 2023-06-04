package br.com.systemshell.Auth.Exceptions;

import br.com.systemshell.Contracts.Exception.ExceptionDefaultAbstractClass;
import br.com.systemshell.Contracts.Exception.IDefaultException;
import org.springframework.http.HttpStatus;

public class PasswordNotMatchException extends ExceptionDefaultAbstractClass implements IDefaultException {
    public PasswordNotMatchException(String customMessage) {
        super();
        this.httpStatus = HttpStatus.UNAUTHORIZED;
        this.customMessage = customMessage;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
