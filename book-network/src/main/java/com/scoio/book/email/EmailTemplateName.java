package com.scoio.book.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account");
    private final String name;


}
