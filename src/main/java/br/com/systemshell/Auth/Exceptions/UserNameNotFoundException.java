package br.com.systemshell.Auth.Exceptions;

import br.com.systemshell.Contracts.Exception.ExceptionDefaultAbstractClass;
import br.com.systemshell.Contracts.Exception.IDefaultException;
import org.springframework.http.HttpStatus;

public class UserNameNotFoundException extends ExceptionDefaultAbstractClass implements IDefaultException {

    public UserNameNotFoundException(String customMessage) {
        super();
        this.httpStatus = HttpStatus.NOT_FOUND;
        this.customMessage = customMessage;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
