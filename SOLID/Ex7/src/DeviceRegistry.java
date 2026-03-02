import java.util.*;

public class DeviceRegistry {
    private final List<Device> devices = new ArrayList<>();


    public void add(Device d) { devices.add(d); }

    public Device getFirstOfType(String simpleName) {
        for (Device d : devices) {
            if (d.getClass().getSimpleName().equals(simpleName)) return d;
        }
        throw new IllegalStateException("Missing: " + simpleName);
    }
}
