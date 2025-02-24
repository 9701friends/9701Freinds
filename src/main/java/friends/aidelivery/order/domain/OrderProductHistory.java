package friends.aidelivery.order.domain;

import friends.aidelivery.product.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_order_product_history")
@Comment("주문 완료시 생성되는 주문내역 조회용 테이블")
@Entity
public class OrderProductHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_product_history_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_history_id", nullable = false)
    private OrderHistory orderHistory;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_content")
    private String productContent;

    @Column(name = "product_price")
    private Long productPrice;

    @Column(name = "product_quantity")
    private int quantity;

    private OrderProductHistory(
        final OrderHistory orderHistory,
        final String productName,
        final String productContent,
        final Long productPrice,
        int quantity) {
        this.orderHistory = orderHistory;
        this.productName = productName;
        this.productContent = productContent;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public static OrderProductHistory create(
        final OrderHistory orderHistory,
        final OrderProduct orderProduct) {
        final Product product = orderProduct.getProduct();
        return new OrderProductHistory(
            orderHistory,
            product.getName().getValue(),
            product.getContent().getValue(),
            product.getPrice().getValue(),
            orderProduct.getQuantity());
    }
}
