package jp.wako.demo.springbootmvc.presentation.shared.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UseCaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUseCaseException(final Model model, final UseCaseException exception) {
        return error(model, HttpStatus.BAD_REQUEST, exception);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDomainException(final Model model, final DomainException exception) {
        return error(model, HttpStatus.BAD_REQUEST, exception);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleOtherException(final Model model, final Exception exception) {
        return error(model, HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    private String error(final Model model, final HttpStatus httpStatus, final Exception exception) {
        // TODO: ログにスタックトレースを出力する
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("status", httpStatus.value());
        model.addAttribute("error", httpStatus.getReasonPhrase());
        model.addAttribute("exception", exception.getClass());
        model.addAttribute("message", exception.getMessage());
        model.addAttribute("trace", getStackTrace(exception));
        model.addAttribute("path", null);
        return "/error";
    }

    private String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

}
