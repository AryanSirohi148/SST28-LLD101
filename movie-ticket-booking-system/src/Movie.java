import java.util.Objects;

public class Movie {
    private final String id;
    private final String title;
    private final String language;
    private final int durationMins;

    public Movie(String id, String title, String language, int durationMins) {
        this.id = Objects.requireNonNull(id);
        this.title = Objects.requireNonNull(title);
        this.language = Objects.requireNonNull(language);
        this.durationMins = durationMins;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public int getDurationMins() {
        return durationMins;
    }

    @Override
    public String toString() {
        return "Movie{" +
            "id='" + id + '\'' +
            ", title='" + title + '\'' +
            ", language='" + language + '\'' +
            ", durationMins=" + durationMins +
            '}';
    }
}
