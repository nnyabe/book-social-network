package com.scoio.book.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
