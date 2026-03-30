import java.util.List;

public class Theatre {
    private String id;
    private String name;
    private String city;
    private double theatreBasePrice;
    private List<Screen> screens;

    public Theatre(String id, String name, String city, double theatreBasePrice, List<Screen> screens) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.theatreBasePrice = theatreBasePrice;
        this.screens = screens;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public double getTheatreBasePrice() { return theatreBasePrice; }
    public List<Screen> getScreens() { return screens; }
}
