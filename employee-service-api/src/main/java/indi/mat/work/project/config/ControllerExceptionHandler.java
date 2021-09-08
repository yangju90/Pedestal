package indi.mat.work.project.config;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.github.pagehelper.PageException;
import indi.mat.work.project.exception.EmployeeException;
import indi.mat.work.project.exception.ErrorInputException;
import indi.mat.work.project.response.Response;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(EmployeeException.class)
    public Response<Void> handleBusinessException(EmployeeException e) {
        return Response.FAIL(e.getMessage());
    }


    @ExceptionHandler(PageException.class)
    public Response<Void> handlePageException2(PageException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Response.FAIL(e.getMessage());
    }

    @ExceptionHandler(ErrorInputException.class)
    public Response<Void> handleBadRequestException(ErrorInputException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return Response.FAIL(e.getMessage());
    }


    @ExceptionHandler(ResponseStatusException.class)
    public Response<Void> handleResponseStatusException(ResponseStatusException e, HttpServletResponse response) {
        response.setStatus(e.getStatus().value());
        return Response.FAIL(e.getReason());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Void> handleThrowable(Throwable e) {
        logger.error(e.getMessage(), e);
        return Response.FAIL(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Void> handleConstraintViolationException(ConstraintViolationException e) {
        Set<String> messages = new LinkedHashSet<>();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            PathImpl path = (PathImpl)(constraintViolation.getPropertyPath());
            String name = path.getLeafNode().getName();
            if("<list element>".equals(name)) {
                name = path.getLeafNode().getParent().asString();
            }
            messages.add(name + " " + constraintViolation.getMessage());
        }
        return Response.FAIL(messages.isEmpty() ? e.getMessage() : String.join(" | ", messages));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String  typeName = e.getRequiredType().getTypeName();
        StringBuilder fieldName = new StringBuilder(convertMessage(e.getName(),typeName));
        return Response.FAIL(fieldName.toString());
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn(ex.getMessage());
        String message = status.getReasonPhrase();
        if(ex instanceof MethodArgumentNotValidException) {
            Set<String> messages = new LinkedHashSet<>();
            for (ObjectError error : ((MethodArgumentNotValidException)ex).getBindingResult().getAllErrors()) {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                messages.add(fieldName + " " + errorMessage);
            }
            if(!messages.isEmpty()) {
                message = String.join(" | ", messages);
            }
        }else if(ex instanceof HttpMessageNotReadableException){
            StringBuilder stringBuilder = new StringBuilder();
            Throwable convertException = ex.getCause();
            if(convertException instanceof MismatchedInputException){
                MismatchedInputException convertExceptionTmp = (MismatchedInputException)convertException;
                List<JsonMappingException.Reference> linkedList  = convertExceptionTmp.getPath();
                String  typeName = convertExceptionTmp.getTargetType().getTypeName();
                for (JsonMappingException.Reference reference: linkedList ) {
                    stringBuilder.append(convertMessage(reference.getFieldName(),typeName)+"|");
                }
            }else if(convertException instanceof JsonMappingException){
                JsonMappingException convertExceptionTmp = (JsonMappingException)convertException;
                List<JsonMappingException.Reference> linkedList  = convertExceptionTmp.getPath();
                for (JsonMappingException.Reference reference: linkedList ) {
                    stringBuilder.append(reference.getFieldName()+" is not valid"+"|");
                }
            }
            if(stringBuilder.length() != 0){
                message = stringBuilder.substring(0,stringBuilder.length()-1);
            }

        }
        return super.handleExceptionInternal(ex, Response.FAIL(message), headers, status, request);
    }

    /**
     * 格式化提示消息
     * @param fieldName
     * @param typeName
     * @return
     */
    private String convertMessage(String fieldName,String typeName){
        StringBuilder sb = new StringBuilder(fieldName);
        sb.append(" must be ");
        if(long.class.getTypeName().equals(typeName) || Long.class.getTypeName().equals(typeName)){
             sb.append("integer max value:"+ Long.MAX_VALUE);
        }else if(int.class.getTypeName().equals(typeName) || Integer.class.getTypeName().equals(typeName)){
            sb.append("integer max value:"+ Integer.MAX_VALUE);
        }else if(float.class.getTypeName().equals(typeName) || Float.class.getTypeName().equals(typeName)){
            sb.append("float max value:"+ Float.MAX_VALUE);
        }else if(double.class.getTypeName().equals(typeName) || Double.class.getTypeName().equals(typeName)) {
            sb.append("double max value:" + Double.MAX_VALUE);
        }else if(boolean.class.getTypeName().equals(typeName) || Boolean.class.getTypeName().equals(typeName)) {
            sb.append("true or false");
        }else if(BigDecimal.class.getTypeName().equals(typeName)) {
            sb.append("number max value:"+Double.MAX_VALUE);
        }else{
            sb.append(typeName);
        }
        return sb.toString();
    }
}
