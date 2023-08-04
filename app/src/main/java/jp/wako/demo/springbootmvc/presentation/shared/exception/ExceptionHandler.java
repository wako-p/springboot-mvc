package jp.wako.demo.springbootmvc.presentation.shared.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UseCaseException.class)
    public String handleUseCaseException(UseCaseException exception) {
        return "redirect:/error";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DomainException.class)
    public String handleDomainException(DomainException exception) {
        return "redirect:/error";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public String handleOtherException(Exception exception) {
        return "redirect:/error";
    }

}
