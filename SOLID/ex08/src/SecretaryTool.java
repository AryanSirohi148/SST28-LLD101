public class SecretaryTool implements MinutesCapable {
    private final MinutesBook book;
    public SecretaryTool(MinutesBook book) { this.book = book; }

    @Override public void addMinutes(String text) { book.add(text); }
}

// implements only what needed by utilizing the respective lean interface
