package com.se192491.assignment02.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orchids")
public class Orchid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orchid_id")
    private int orchidID;
    @Column(name = "is_natural")
    private boolean isNatural;
    @Column(name = "orchid_description")
    private String orchidDescription;
    @Column(name = "orchid_name")
    private String orchidName;
    @Column(name = "orchid_url")
    private String orchidUrl;
    @Column(name = "price")
    private int price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
