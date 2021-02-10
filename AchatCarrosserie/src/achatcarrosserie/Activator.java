package achatcarrosserie;
import java.util.Dictionary;
import java.util.Properties;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import carroussier.AchatCarrosserieInterface;

public class Activator implements BundleActivator {
private ServiceRegistration registration;
public void start(BundleContext context) throws Exception {
System.out.println("Start Achat Carrossorie");
Properties props = new Properties();
props.put("Language", "French");
registration =
context.registerService(AchatCarrosserieInterface.class.getName(),
new AchatCarrosserie(), (Dictionary)props);
System.out.println("\t Service achat carrosserie enregistré.");
}
/*
* (non-Javadoc)
* @see
org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
*/
public void stop(BundleContext context) throws Exception {
registration.unregister();
System.out.println("\t Service achat carrosserie supprimé.");
System.out.println("Stop Achat Carrossorie");
}
}