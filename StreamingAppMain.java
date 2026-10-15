import java.util.*;
import java.util.stream.Collectors;

// ---------------- Plan ----------------
class Plan {
    private final int planId;
    private String name;
    private double monthlyPrice;
    private int screens;
    private String quality;
    private int subscriberCount;

    public Plan(int planId, String name, double monthlyPrice, int screens, String quality) {
        this.planId = planId;
        this.name = name;
        setMonthlyPrice(monthlyPrice);
        this.screens = screens;
        this.quality = quality;
    }

    public int getPlanId() { return planId; }
    public String getName() { return name; }
    public double getMonthlyPrice() { return monthlyPrice; }
    public int getScreens() { return screens; }
    public String getQuality() { return quality; }
    public int getSubscriberCount() { return subscriberCount; }

    // Encapsulation: guard price changes
    public void setMonthlyPrice(double price) {
        if (price <= 0) throw new IllegalArgumentException("Price must be positive");
        this.monthlyPrice = price;
    }

    void addSubscriber() { subscriberCount++; }
    void removeSubscriber() { if (subscriberCount > 0) subscriberCount--; }
    public double revenue() { return subscriberCount * monthlyPrice; }

    @Override
    public String toString() {
        return name + " (Rs." + monthlyPrice + ", " + screens + " screens, " + quality + ")";
    }
}

// ---------------- Content (base) ----------------
class Content {
    private final int contentId;
    private String title;
    private String type;
    private String genre;
    private int year;
    private double rating;       // average rating 0-10
    private int ratingCount;
    private int watchCount;

    public Content(int contentId, String title, String type, String genre, int year, double rating) {
        this.contentId = contentId;
        this.title = title;
        this.type = type;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
        this.ratingCount = 1;
    }

    public int getContentId() { return contentId; }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public String getGenre() { return genre; }
    public int getYear() { return year; }
    public double getRating() { return rating; }
    public int getWatchCount() { return watchCount; }
    public void setTitle(String title) { this.title = title; }

    // Encapsulation: guard rating updates (running average, 0-10 only)
    public void addRating(double newRating) {
        if (newRating < 0 || newRating > 10)
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        rating = (rating * ratingCount + newRating) / (++ratingCount);
    }

    protected void incrementWatch() { watchCount++; }

    // Overridden by Movie and Series
    public String play(User user) {
        incrementWatch();
        return user.getName() + " is playing " + title;
    }

    @Override
    public String toString() {
        return String.format("%s [%s, %s, %d, %.1f*]", title, type, genre, year, rating);
    }
}

// ---------------- Movie ----------------
class Movie extends Content {
    private int durationMins;

    public Movie(int id, String title, String genre, int year, double rating, int durationMins) {
        super(id, title, "Movie", genre, year, rating);
        this.durationMins = durationMins;
    }

    public int getDurationMins() { return durationMins; }

    @Override
    public String play(User user) {
        incrementWatch();
        return user.getName() + " is watching movie '" + getTitle() + "' (" + durationMins + " min)";
    }
}

// ---------------- Series ----------------
class Series extends Content {
    private int episodes;
    private Map<Integer, Integer> progress = new HashMap<>(); // userId -> last episode watched

    public Series(int id, String title, String genre, int year, double rating, int episodes) {
        super(id, title, "Series", genre, year, rating);
        this.episodes = episodes;
    }

    public int getEpisodes() { return episodes; }

    // Plays the NEXT episode for this user (wraps to ep 1 after finishing)
    @Override
    public String play(User user) {
        int last = progress.getOrDefault(user.getUserId(), 0);
        int next = (last % episodes) + 1;
        progress.put(user.getUserId(), next);
        incrementWatch();
        return user.getName() + " is watching '" + getTitle() + "' episode " + next + "/" + episodes;
    }
}

// ---------------- User ----------------
class User {
    private final int userId;
    private String name;
    private String email;
    private Plan activePlan;
    private List<Content> watchlist = new ArrayList<>();
    private List<Content> history = new ArrayList<>();

    public User(int userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Plan getActivePlan() { return activePlan; }
    public List<Content> getWatchlist() { return Collections.unmodifiableList(watchlist); }
    public List<Content> getHistory() { return Collections.unmodifiableList(history); }
    public void setEmail(String email) { this.email = email; }

    // Encapsulation: guard plan changes
    public void subscribe(Plan plan) {
        if (plan == null) throw new IllegalArgumentException("Plan cannot be null");
        if (plan == activePlan) {
            System.out.println(name + " is already on " + plan.getName());
            return;
        }
        if (activePlan != null) activePlan.removeSubscriber();
        activePlan = plan;
        plan.addSubscriber();
    }

    public void addToWatchlist(Content c) {
        if (!watchlist.contains(c)) watchlist.add(c);
    }

    public void recordHistory(Content c) { history.add(c); }

    public boolean hasWatched(Content c) { return history.contains(c); }
}

// ---------------- StreamingService ----------------
class StreamingService {
    private String serviceName;
    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Plan> plans = new HashMap<>();
    private List<Content> catalog = new ArrayList<>();

    public StreamingService(String serviceName) { this.serviceName = serviceName; }

    public String getServiceName() { return serviceName; }
    public void addPlan(Plan p) { plans.put(p.getPlanId(), p); }
    public void addUser(User u) { users.put(u.getUserId(), u); }
    public void addContent(Content c) { catalog.add(c); }
    public User getUser(int id) { return users.get(id); }
    public Plan getPlan(int id) { return plans.get(id); }

    public void subscribe(int userId, int planId) {
        users.get(userId).subscribe(plans.get(planId));
    }

    // Polymorphism: Content reference -> Movie/Series play()
    public void play(User user, Content c) {
        if (user.getActivePlan() == null) {
            System.out.println(user.getName() + " needs an active plan to watch.");
            return;
        }
        System.out.println(c.play(user) + " in " + user.getActivePlan().getQuality());
        user.recordHistory(c);
    }

    public void playAll(User user, List<Content> list) {
        for (Content c : list) play(user, c);
    }

    // ---- Overloaded recommend() ----
    public List<Content> recommend(User u, String genre) {
        return catalog.stream()
                .filter(c -> c.getGenre().equalsIgnoreCase(genre) && !u.hasWatched(c))
                .collect(Collectors.toList());
    }

    public List<Content> recommend(User u, int fromYear) {
        return catalog.stream()
                .filter(c -> c.getYear() >= fromYear && !u.hasWatched(c))
                .collect(Collectors.toList());
    }

    public List<Content> recommend(User u, double minRating) {
        return catalog.stream()
                .filter(c -> c.getRating() >= minRating && !u.hasWatched(c))
                .collect(Collectors.toList());
    }

    // Personalized: favourite genre from history, fall back to top rated
    public List<Content> recommend(User u) {
        Map<String, Long> counts = u.getHistory().stream()
                .collect(Collectors.groupingBy(Content::getGenre, Collectors.counting()));
        Optional<String> fav = counts.entrySet().stream()
                .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey);
        List<Content> result = fav.isPresent() ? recommend(u, fav.get()) : new ArrayList<>();
        if (result.isEmpty()) result = recommend(u, 8.0);
        return result;
    }

    public void printTopWatched(int n) {
        System.out.println("\n=== Top " + n + " Watched ===");
        catalog.stream()
                .sorted((a, b) -> b.getWatchCount() - a.getWatchCount())
                .limit(n)
                .forEach(c -> System.out.println(c.getTitle() + " - " + c.getWatchCount() + " views"));
    }

    public void printPlanRevenue() {
        System.out.println("\n=== Plan-wise Revenue ===");
        double total = 0;
        for (Plan p : plans.values()) {
            System.out.printf("%-10s subscribers=%d revenue=Rs.%.2f%n",
                    p.getName(), p.getSubscriberCount(), p.revenue());
            total += p.revenue();
        }
        System.out.printf("Total monthly revenue: Rs.%.2f%n", total);
    }
}

// ---------------- Main ----------------
public class StreamingAppMain {
    public static void main(String[] args) {
        StreamingService svc = new StreamingService("StreamIt");

        // Plans
        svc.addPlan(new Plan(1, "Basic", 199, 1, "SD"));
        svc.addPlan(new Plan(2, "Standard", 499, 2, "HD"));
        svc.addPlan(new Plan(3, "Premium", 799, 4, "4K"));

        // Content
        Content m1 = new Movie(101, "Inception", "SciFi", 2010, 8.8, 148);
        Content m2 = new Movie(102, "Interstellar", "SciFi", 2014, 8.7, 169);
        Content m3 = new Movie(103, "The Hangover", "Comedy", 2009, 7.7, 100);
        Content s1 = new Series(201, "Dark", "SciFi", 2017, 8.7, 26);
        Content s2 = new Series(202, "Friends", "Comedy", 1994, 8.9, 236);
        Content s3 = new Series(203, "Money Heist", "Thriller", 2017, 8.2, 41);
        for (Content c : new Content[]{m1, m2, m3, s1, s2, s3}) svc.addContent(c);

        // Users
        User u1 = new User(1, "Asha", "asha@mail.com");
        User u2 = new User(2, "Ravi", "ravi@mail.com");
        User u3 = new User(3, "Meena", "meena@mail.com");
        svc.addUser(u1); svc.addUser(u2); svc.addUser(u3);

        // Subscribe
        svc.subscribe(1, 3);
        svc.subscribe(2, 1);
        svc.subscribe(3, 2);
        svc.subscribe(2, 2); // Ravi upgrades

        // Watchlists
        u1.addToWatchlist(m1); u1.addToWatchlist(s1);
        u2.addToWatchlist(m3); u2.addToWatchlist(s2);

        // Polymorphic playback via List<Content>
        System.out.println("=== Playback ===");
        svc.playAll(u1, u1.getWatchlist());
        svc.play(u1, s1); // next episode of Dark
        svc.playAll(u2, u2.getWatchlist());
        svc.play(u3, s2);

        // Encapsulation checks
        try { m1.addRating(11); }
        catch (IllegalArgumentException e) { System.out.println("Guarded: " + e.getMessage()); }
        m1.addRating(9.5);
        try { u1.subscribe(null); }
        catch (IllegalArgumentException e) { System.out.println("Guarded: " + e.getMessage()); }

        // Reports
        svc.printTopWatched(3);
        svc.printPlanRevenue();

        // Overloaded recommendations
        System.out.println("\n=== Recommendations for " + u1.getName() + " ===");
        System.out.println("By genre (Comedy): " + svc.recommend(u1, "Comedy"));
        System.out.println("By year (>=2015): " + svc.recommend(u1, 2015));
        System.out.println("By rating (>=8.5): " + svc.recommend(u1, 8.5));
        System.out.println("Personalized: " + svc.recommend(u1));
    }
}