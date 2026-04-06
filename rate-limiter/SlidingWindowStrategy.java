import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowStrategy implements RateLimitingStrategy {
    private int maxRequests;
    private long windowSizeInMillis;
    // The data structure maintaining the history of request timestamps
    private Queue<Long> requestTimestamps;

    public SlidingWindowStrategy(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.requestTimestamps = new LinkedList<>();
    }

    @Override
    public synchronized boolean allowRequest() {
        long currentTime = System.currentTimeMillis();

        // 1. Evict timestamps that are outside the sliding window.
        // If a request was made exactly 1 minute ago, and our window is 1 minute,
        // it is obsolete and doesn't count against our current threshold.
        while (!requestTimestamps.isEmpty() && currentTime - requestTimestamps.peek() > windowSizeInMillis) {
            requestTimestamps.poll(); 
        }

        // 2. Check if the queue size exceeds our limit
        if (requestTimestamps.size() < maxRequests) {
            // We have capacity. Push the new timestamp and allow the request.
            requestTimestamps.add(currentTime);
            return true;
        }

        // We hit the limit
        return false;
    }
}
