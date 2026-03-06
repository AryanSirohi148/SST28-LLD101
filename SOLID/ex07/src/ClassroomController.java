public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) { this.reg = reg; }

    public void startClass() {
        PowerOnCapable projectorPower = reg.getFirstOfType(PowerOnCapable.class);
        projectorPower.powerOn();

        InputConnectable projectorInput = reg.getFirstOfType(InputConnectable.class);
        projectorInput.connectInput("HDMI-1");

        reg.getFirstOfType(BrightnessControllable.class).setBrightness(60);
        reg.getFirstOfType(TemperatureControllable.class).setTemperatureC(24);

        System.out.println("Attendance scanned: present=" +
                reg.getFirstOfType(AttendanceCapable.class).scanAttendance());
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        for (PowerOffCapable device : reg.getAllOfType(PowerOffCapable.class)) {
            device.powerOff();
        }
    }
}

// orchestrator which will use the lean interfaces and not concrete classes to function