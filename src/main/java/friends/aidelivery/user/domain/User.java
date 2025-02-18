package friends.aidelivery.user.domain;

import friends.aidelivery.user.application.dto.request.UserCreateRequest;
import friends.aidelivery.user.domain.enums.UserRoleEnum;
import friends.aidelivery.user.domain.vo.Address;
import friends.aidelivery.user.domain.vo.Email;
import friends.aidelivery.user.domain.vo.Name;
import friends.aidelivery.user.domain.vo.Nickname;
import friends.aidelivery.user.domain.vo.Password;
import friends.aidelivery.user.domain.vo.Phone;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Table(name = "p_user")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_Id")
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private Email email;

    @Embedded
    private Nickname nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = false)
    private String password;

    @Embedded
    private Address address;

    @Embedded
    private Phone phone;

    private User(Name name, Email email, Nickname nickname, UserRoleEnum role,
        String encryptedPassword, Address address, Phone phone) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.password = encryptedPassword;
        this.address = address;
        this.phone = phone;
    }

    public static User createUser(UserCreateRequest userCreateRequest,
        PasswordEncoder passwordEncoder) {

        return new User(
            new Name(userCreateRequest.name()),
            new Email(userCreateRequest.email()),
            new Nickname(userCreateRequest.nickname()),
            userCreateRequest.role(),
            Password.encrypt(userCreateRequest.password(), passwordEncoder).getValue(),
            new Address(userCreateRequest.address()), new Phone(userCreateRequest.phone())
        );
    }

}