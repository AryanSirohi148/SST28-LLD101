public class Exporter {
    Encoder encoder;

    Exporter(Encoder encoder){
        this.encoder = encoder;
    }

    public  ExportResult export(ExportRequest req){
        if(req==null){
            throw new IllegalArgumentException("Request cannot be null");   //exception accept here bec it's an base contract
        }

        return encoder.encode(req);
    }

}
