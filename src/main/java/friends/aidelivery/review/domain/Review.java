package friends.aidelivery.review.domain;

import friends.aidelivery.order.domain.Order;
import friends.aidelivery.review.domain.vo.Rating;
import friends.aidelivery.review.domain.vo.ReviewContent;
import friends.aidelivery.review.domain.vo.ReviewTime;
import friends.aidelivery.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Embedded
    private ReviewContent content;

    @Embedded
    private Rating rating;

    @Embedded
    private ReviewTime reviewTime;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Review(final Order order, final User user, final String content,
        final Integer rating,
        final LocalDateTime reviewTime) {
        this.order = order;
        this.user = user;
        this.content = new ReviewContent(content);
        this.rating = new Rating(rating);
        this.reviewTime = new ReviewTime(reviewTime, order.getCompletionTime());
    }

    public void update(final String content, final Integer rating, final LocalDateTime reviewTime) {
        this.content = new ReviewContent(content);
        this.rating = new Rating(rating);
        this.reviewTime = new ReviewTime(reviewTime, order.getCompletionTime());
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}
