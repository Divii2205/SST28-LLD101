import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== RATE LIMITER SYSTEM INITIALIZATION ==========");
        
        // Setup Strategy: We only allow 5 requests every 1000 milliseconds (1 second)
        RateLimitingStrategy slidingWindow = new SlidingWindowStrategy(5, 1000);
        
        RemoteResource realBillingApi = new ExternalApiService();
        
        // Setup Proxy (Wraps the real API with the boundary strategy)
        RemoteResource rateLimiterProxy = new RateLimitingProxy(realBillingApi, slidingWindow);
        
        // Setup Orchestrator (The orchestrator only knows about the interface, perfectly abstracted!)
        Orchestrator orchestrator = new Orchestrator(rateLimiterProxy);

        System.out.println("\n========== TEST: CONSTANT CONCURRENT ABUSE ==========");
        
        // Spawning 15 parallel users all attempting to hammer the endpoint at once
        ExecutorService executor = Executors.newFixedThreadPool(15);
        for (int i = 1; i <= 15; i++) {
            final int id = i;
            executor.submit(() -> {
                Request myReq = new Request("Client_" + id, "UserData");
                String response = orchestrator.routeRequest(myReq);
                System.out.println("Client_" + id + " Response -> " + response);
            });
        }
        
        // Giving the threads a moment to finish firing
        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.SECONDS);

        System.out.println("\n========== TEST: ROLLING WINDOW REFRESH ==========");
        System.out.println("Waiting for the 1-second limit sliding window to expire...");
        Thread.sleep(1000); 

        // Let's send exactly 3 requests (Should succeed natively since limit of 5 is theoretically cleared by the time slip)
        System.out.println("\nSending 3 new requests post-expiration:");
        for (int i = 16; i <= 18; i++) {
            Request freshReq = new Request("Client_" + i, "PostWaitData");
            String response = orchestrator.routeRequest(freshReq);
            System.out.println("Client_" + i + " Response -> " + response);
        }
    }
}
