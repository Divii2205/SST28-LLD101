/**
 * The Strategy interface. By keeping this independent, we easily satisfy the
 * Open/Closed Principle. If you decide to add a FixedWindowStrategy tomorrow,
 * you don't need to touch the existing RequestHandlerService logic at all.
 */
public interface RateLimitingStrategy {
    boolean allowRequest();
}
