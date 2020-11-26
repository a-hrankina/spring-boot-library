package com.ahrankina.library.jsfui.util;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.UnexpectedRollbackException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;
import java.util.logging.Level;

@Log
@Aspect
@Component
public class AspectMessageHandler {

    @Around("execution(* com.ahrankina.library.jsfui.controller.*.deleteAction(..))")
    public void deleteConstraint(ProceedingJoinPoint jp) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");

        try {
            jp.proceed();
            context.addMessage(null, new FacesMessage(null, bundle.getString("deleted")));
            RequestContext.getCurrentInstance().update("info");
        } catch (Throwable throwable) {

            if (throwable instanceof UnexpectedRollbackException) {
                log.log(Level.WARNING, throwable.getMessage());
                throwable.printStackTrace();

                if (((UnexpectedRollbackException) throwable).getMostSpecificCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
                    context.addMessage(null, new FacesMessage(null, bundle.getString("constraint_delete_record")));
                }
            }
        }
        RequestContext.getCurrentInstance().update("info");
    }


    @Around("execution(* com.ahrankina.library.dao.*.save(..))")
    public void addNewSprValue(ProceedingJoinPoint jp) {

        try {
            Object obj = jp.proceed();

            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");

            context.addMessage(null, new FacesMessage(null, bundle.getString("added") + ": \"" + obj.toString() + "\""));

            RequestContext.getCurrentInstance().update("info");
        } catch (Throwable throwable) {
            log.log(Level.WARNING, throwable.getMessage());
            throwable.printStackTrace();

        }

    }

}

