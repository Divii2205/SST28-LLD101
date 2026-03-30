public class Movie {
    private String id;
    private String title;
    private int durationMinutes;
    private double movieBasePrice;

    public Movie(String id, String title, int durationMinutes, double movieBasePrice) {
        this.id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.movieBasePrice = movieBasePrice;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getDurationMinutes() { return durationMinutes; }
    public double getMovieBasePrice() { return movieBasePrice; }
}
