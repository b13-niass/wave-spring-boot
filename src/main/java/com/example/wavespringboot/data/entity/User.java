package com.example.wavespringboot.data.entity;

import com.example.wavespringboot.enums.ChannelEnum;
import com.example.wavespringboot.enums.EtatCompteEnum;
import com.example.wavespringboot.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String adresse;
    private String fileCni;
    private String cni;
    private LocalDate dateNaissance;
    private String codeVerification;

    @Column(unique = true)
    private String telephone;

    @Enumerated(EnumType.STRING)
    private ChannelEnum channel;

    @Column(unique = true)
    private String email;

    private String password;

    private int nbrConnection = 0;

    @Enumerated(EnumType.STRING)
    private EtatCompteEnum etatCompte;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @ManyToOne
    private Pays pays;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet;

    @OneToMany(mappedBy = "user")
    private List<Favoris> favoris;

    @OneToMany(mappedBy = "sender")
    private List<Transaction> sentTransactions;

    @OneToMany(mappedBy = "receiver")
    private List<Transaction> receivedTransactions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return this.telephone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return etatCompte == EtatCompteEnum.ACTIF;
    }
}

