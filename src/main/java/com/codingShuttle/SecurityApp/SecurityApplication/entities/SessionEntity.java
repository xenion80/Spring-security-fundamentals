package com.codingShuttle.SecurityApp.SecurityApplication.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",unique = true,nullable = false)
    private User user;

    @Column(unique = true,nullable = false,length = 1000)
    private String token;


}
