public class AirConditioner implements PowerOffCapable, TemperatureControllable {
    @Override public void powerOff() { System.out.println("AC OFF"); }
    @Override public void setTemperatureC(int c) { System.out.println("AC set to " + c + "C"); }
}

// A concrete class implementing its capabilities via lean interfaces