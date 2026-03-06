public class LightsPanel implements PowerOffCapable, BrightnessControllable {
    @Override public void powerOff() { System.out.println("Lights OFF"); }
    @Override public void setBrightness(int pct) { System.out.println("Lights set to " + pct + "%"); }
}

// A concrete class implementing its capabilities via lean interfaces