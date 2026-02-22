public interface DiscountPolicy {
    double discountAmount(double subtotal, int lineCount);
}