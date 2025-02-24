package friends.aidelivery.store.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Column(name = "rating", precision = 2, scale = 1)
    private BigDecimal value;

    @Column(name = "rating_count")
    private Integer quantity = 0;

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

    private void validate(BigDecimal value, Integer quantity) {
        if (quantity < 1) {
            return;
        }
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
     * 주어진 변경 사항을 반영하여 새로운 평점과 리뷰 수를 기반으로 업데이트된 Rating 객체를 반환합니다.
     *
     * @param quantityChange 리뷰의 수가 변경된 양 (생성: 1, 수정: 0, 삭제: -1)
     * @param oldRating      수정 전의 기존 평점
     * @param newRating      새로 추가된 평점 (혹은 수정된 평점)
     * @return 업데이트된 평균 평점과 리뷰 수를 포함한 새로운 Rating 객체
     */
    public Rating update(int quantityChange, BigDecimal oldRating, BigDecimal newRating) {

        BigDecimal totalRating = this.value.multiply(BigDecimal.valueOf(this.quantity));

        BigDecimal newTotalRating = totalRating.add(newRating).subtract(oldRating);
        int newQuantity = this.quantity + quantityChange;

        BigDecimal newAverage = newTotalRating.divide(BigDecimal.valueOf(newQuantity), 2,
            RoundingMode.HALF_UP);

        return new Rating(newAverage, newQuantity);
    }

}
