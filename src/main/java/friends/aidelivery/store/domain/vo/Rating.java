package friends.aidelivery.store.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Rating {

    private static final BigDecimal MAX_VALUE = new BigDecimal("5.0");
    private static final BigDecimal MIN_VALUE = new BigDecimal("1.0");

    @Column(name = "rating")
    private BigDecimal value;

    @Column(name ="rating_count")
    private Integer quantity = 0;

    private void validate(BigDecimal value, Integer quantity) {
        if (value == null) {
            throw new NullPointerException("평점은 필수 입력값입니다.");
        }
        if (value.compareTo(MIN_VALUE) < 0 || value.compareTo(MAX_VALUE) > 0) {
            throw new IllegalArgumentException("평점 범위를 벗어났습니다.");
        }
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("수량은 0 이상이어야 합니다.");
        }
    }

    /**
     * 새로운 Rating 객체를 생성
     *
     * @param value    새로 계산된 평균 평점
     * @param quantity 현재까지의 리뷰 개수
     */
    public Rating(BigDecimal value, Integer quantity) {
        validate(value, quantity);
        this.value = value;
        this.quantity = quantity;
    }

    /**
     * 새로운 평점을 반영하여 업데이트된 Rating 객체를 반환
     *
     * @param newRating 새롭게 추가된 평점
     * @return 업데이트된 평점 정보를 포함한 새로운 Rating 객체
     */
    public Rating update(BigDecimal newRating) {
        // 기존 평점 총합 + 새 평점
        BigDecimal totalRatingSum = this.value.multiply(BigDecimal.valueOf(this.quantity))
            .add(newRating);

        int newQuantity = this.quantity + 1;
        BigDecimal newAverage = totalRatingSum.divide(BigDecimal.valueOf(newQuantity), 2, BigDecimal.ROUND_HALF_UP);

        return new Rating(newAverage, newQuantity);
    }
}
