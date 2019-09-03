package com.hines.james.multitenancywithspring.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultitenancyDataSource {
    private String name;
    private String url;
    private String username;
    private String password;
}
