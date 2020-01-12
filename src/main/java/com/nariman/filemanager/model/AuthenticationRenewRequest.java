package com.nariman.filemanager.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationRenewRequest implements Serializable {
    private String jwt;
}
