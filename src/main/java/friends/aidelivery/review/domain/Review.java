package friends.aidelivery.review.domain;

import friends.aidelivery.review.domain.vo.Rating;
import friends.aidelivery.review.domain.vo.ReviewContent;
import friends.aidelivery.review.domain.vo.ReviewTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_review")
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "review_id")
    private UUID id;

    // todo order 연관관계 설정
    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    // todo user 연관관계 설정
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Embedded
    private ReviewContent content;

    @Embedded
    private Rating rating;

    @Embedded
    private ReviewTime reviewTime;

    public Review(final UUID orderId, final UUID userId, final ReviewContent content,
        final Rating rating,
        final ReviewTime reviewTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.content = content;
        this.rating = rating;
        this.reviewTime = reviewTime;
    }

    public Review(final UUID orderId, final UUID userId, final String content,
        final Integer rating,
        final ReviewTime reviewTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.content = new ReviewContent(content);
        this.rating = new Rating(rating);
        this.reviewTime = reviewTime;
    }


}
