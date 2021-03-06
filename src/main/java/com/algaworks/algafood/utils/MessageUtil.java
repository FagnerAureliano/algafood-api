package com.algaworks.algafood.utils;

import org.springframework.context.support.ResourceBundleMessageSource;

public class MessageUtil {

    private ResourceBundleMessageSource messageSource;

    public MessageUtil(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, String... args) {
        return messageSource.getMessage(code, args, null);
    }

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, null);
    }
}
