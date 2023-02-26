package com.automationfraternity.advice;

import com.automationfraternity.exceptions.DoctorNotFoundException;
import com.automationfraternity.exceptions.PatientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(DoctorNotFoundException.class)
    public final ResponseEntity<CustomErrorDetails> handleDoctorNotFound(DoctorNotFoundException exception, WebRequest request){
        CustomErrorDetails errorDetails = new CustomErrorDetails(new Date(), exception.getMessage(), request.getDescription(false) );
        return new ResponseEntity<CustomErrorDetails>(errorDetails,HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PatientNotFoundException.class)
    public final ResponseEntity<CustomErrorDetails> handlePatientNotFound(Exception exception, WebRequest request){
        CustomErrorDetails errorDetails = new CustomErrorDetails(new Date(), exception.getMessage() + " Error while processing your request", request.getDescription(false) );
        return new ResponseEntity<CustomErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
    }

}
