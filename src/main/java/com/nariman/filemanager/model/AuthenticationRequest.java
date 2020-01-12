package com.nariman.filemanager.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class AuthenticationRequest implements Serializable {
    private String username;
    private String password;
}
