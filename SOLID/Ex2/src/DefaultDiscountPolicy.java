import java.util.*;

public class DefaultDiscountPolicy implements DiscountPolicy {

    @Override
    public double discountAmount(double subtotal, int lineCount) {
        return 0.0;
    }
}