package friends.aidelivery.store.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class Address {

    private static final int MAX_LENGTH = 100;

    @Column(name = "address", nullable = false, length = MAX_LENGTH)
    private String value;


    private void validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new NullPointerException("필수 입력 값입니다.");
        }
    }

    public Address(String value) {
        validate(value);
        this.value = value;
    }

    public void update(String value) {
        validate(value);
        this.value = value;
    }

}
