package friends.aidelivery.user.domain;

import friends.aidelivery.user.domain.vo.Address;
import friends.aidelivery.user.domain.vo.Email;
import friends.aidelivery.user.domain.vo.Name;
import friends.aidelivery.user.domain.vo.Nickname;
import friends.aidelivery.user.domain.vo.Password;
import friends.aidelivery.user.domain.vo.Phone;
import friends.aidelivery.user.domain.enums.UserRoleEnum;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_user")
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

    @Embedded
    private Password password;

    @Embedded
    private Address address;

    @Embedded
    private Phone phone;
}