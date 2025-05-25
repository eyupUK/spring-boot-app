package com.eyup.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignInModel {
    @NotBlank(message = "Email required!")
    private String email;

    @NotBlank(message = "Password required!")
    private String password;


    @Override
    public String toString() {
        return "SignInModel{" +
                "username='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
