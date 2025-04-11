package org.example.vrback.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table(name = "tokens")
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Token {
    @Id
    private int id;
    private int token;
    private int activationCount;
}
