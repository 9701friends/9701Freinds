package friends.aidelivery.user.domain;

import friends.aidelivery.admin.application.dto.request.AdminUserUpdateRequest;
import friends.aidelivery.common.domain.TimeStamp;
import friends.aidelivery.user.application.dto.request.UserInfoRequestDto;
import friends.aidelivery.user.domain.enums.UserRoleEnum;
import friends.aidelivery.user.domain.vo.Address;
import friends.aidelivery.user.domain.vo.Email;
import friends.aidelivery.user.domain.vo.Name;
import friends.aidelivery.user.domain.vo.Nickname;
import friends.aidelivery.user.domain.vo.Password;
import friends.aidelivery.user.domain.vo.Phone;
import friends.aidelivery.user.exception.UserUnauthorizedException;
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

@Entity
@Getter
@Table(name = "p_user")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeStamp {

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

    @Enumerated(EnumType.STRING)
    private Password password;

    @Embedded
    private Address address;

    @Embedded
    private Phone phone;

    @Column(nullable = false)
    private Boolean isDeleted;

    private User(Name name, Email email, Nickname nickname, UserRoleEnum role,
        Password password, Address address, Phone phone) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.isDeleted = false;
    }

    public void updateUser(UserInfoRequestDto userInfoRequestDto, Password password) {
        this.name = new Name(userInfoRequestDto.name());
        this.email = new Email(userInfoRequestDto.email());
        this.nickname = new Nickname(userInfoRequestDto.nickname());
        this.role = userInfoRequestDto.role();
        this.password = password;
        this.address = new Address(userInfoRequestDto.address());
        this.phone = new Phone(userInfoRequestDto.phone());
    }

    public void updateUserByAdmin(AdminUserUpdateRequest request) {
        this.address = new Address(request.address());
        this.role = request.role();
        this.phone = new Phone(request.phone());
    }

    public void updateUserRole(UserRoleEnum role) {
        this.role = role;
    }

    public static User createUser(UserInfoRequestDto userCreateRequest,
        Password password) {

        if (!userCreateRequest.role().equals(UserRoleEnum.CUSTOMER)) {
            throw new UserUnauthorizedException();
        }

        return new User(
            new Name(userCreateRequest.name()),
            new Email(userCreateRequest.email()),
            new Nickname(userCreateRequest.nickname()),
            userCreateRequest.role(),
            password,
            new Address(userCreateRequest.address()), new Phone(userCreateRequest.phone())
        );
    }

    public void softDeleteUser() {
        this.isDeleted = true;
    }
}