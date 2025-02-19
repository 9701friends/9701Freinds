package friends.aidelivery.store.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
public class Name {

    private static final int MAX_LENGTH = 20;

    @Column(name = "name" ,nullable = false,length = MAX_LENGTH)
    private String value;

    private void validate(String value){
        if(value == null || value.trim().isEmpty()){
            throw new NullPointerException("필수 입력값입니다.");
        }
        if(value.length() > MAX_LENGTH){
            throw new IllegalArgumentException("범위를 벗어났습니다.");
        }
    }

    public Name(String value){
        validate(value);
        this.value = value;
    }

    public Name update(final String value){
        return new Name(value);
    }

}
