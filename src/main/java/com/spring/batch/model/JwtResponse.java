package com.spring.batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Data
@Builder
@NoArgsConstructor(force = true)
@JsonIgnoreProperties(ignoreUnknown = true)

@AllArgsConstructor
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;


}