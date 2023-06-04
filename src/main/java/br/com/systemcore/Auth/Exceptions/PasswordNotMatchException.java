package br.com.systemcore.Auth.Exceptions;

import br.com.systemcore.Contracts.Exception.ExceptionDefaultAbstractClass;
import br.com.systemcore.Contracts.Exception.IDefaultException;
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
