import io.github.seanieStack.embed.RJScriptModule;
import io.github.seanieStack.stdlib.NativeFunction;

import java.util.ArrayList;
import java.util.List;

public class SensorModule implements RJScriptModule {

    private final SensorMonitor monitor;

    public SensorModule(SensorMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public String name(){
        return "sensor";
    }

    @Override
    public List<NativeFunction> functions() {
        List<NativeFunction> fns = new ArrayList<>();

        fns.add(new NativeFunction() {
            public String name(){return "getTemp";}
            public int arity(){return 0;}
            public Object call(List<Object> args) {
                return monitor.getCurrentTemp();
            }
        });

        fns.add(new NativeFunction() {
            public String name(){return "getHumidity";}
            public int arity(){return 0;}
            public Object call(List<Object> args) {
                return monitor.getCurrentHumidity();
            }
        });

        fns.add(new NativeFunction() {
            public String name(){return "getPressure";}
            public int arity(){return 0;}
            public Object call(List<Object> args) {
                return monitor.getCurrentPressure();
            }
        });

        fns.add(new NativeFunction() {
            public String name(){return "getReadingIndex";}
            public int arity(){return 0;}
            public Object call(List<Object> args) {
                return (double) monitor.getCurrentIndex();
            }
        });

        fns.add(new NativeFunction() {
            public String name(){return "alert";}
            public int arity(){return 2;}
            public Object call(List<Object> args) {
                String level = (String) args.get(0);
                String message = (String) args.get(1);
                monitor.addAlert(level, message);
                return null;
            }
        });

        fns.add(new NativeFunction() {
            public String name(){return "log";}
            public int arity(){return 1;}
            public Object call(List<Object> args) {
                String message = (String) args.get(0);
                System.out.println("[LOG]" + message);
                return null;
            }
        });

        return fns;
    }
}