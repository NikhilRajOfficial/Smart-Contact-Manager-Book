package com.SmartContactManager.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "Username is required")
    @Size(min=3 , message = "Minimum 3 characters is required")
    private String name;

    @NotBlank(message = "Email is required !")
    @Email(message = "Invalid Email Address")
    private String email;

    @NotBlank( message="Password is required")
    @Size(min= 6 ,message = "Minimum 6 characters is required")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Size(min= 10 , max= 12 ,  message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "This is required")
    private String about;




}
