package com.SmartContactManager.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLinks {

    @Id
    private Long id;
    private String link;
    private String title;

    @ManyToOne // This establishes the relationship with Contact
    @JoinColumn(name = "contact_id") // This specifies the foreign key column
    private Contact contact; // Add this field to link back to Contact
}