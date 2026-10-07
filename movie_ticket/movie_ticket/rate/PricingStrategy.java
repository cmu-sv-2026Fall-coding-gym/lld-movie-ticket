package movie_ticket.rate;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal getPrice();
}
