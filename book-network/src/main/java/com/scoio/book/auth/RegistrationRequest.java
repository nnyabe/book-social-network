package com.scoio.book.auth;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotNull
    @NotEmpty(message= "First Name cannot be empty.")
    @NotBlank(message= "First Name cannot be blank")
    private String firstname;

    @NotNull
    @NotEmpty(message= "Last Name cannot be empty.")
    @NotBlank(message= "Last Name cannot be blank")
    private String lastname;

    @NotNull
    @NotEmpty(message= "Email cannot be empty.")
    @NotBlank(message= "Email Name cannot be blank")
    @Email(message = "Invalid Email")
    private String email;

    @NotNull
    @NotEmpty(message= "Password cannot be empty.")
    @NotBlank(message= "Password cannot be blank")
    @Size(min = 8, message = "Password should be more than 8 characters.")
    private String password;
}
