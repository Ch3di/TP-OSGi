package achatmoteur;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import motoriste.AchatMoteurInterface;

public class Activator implements BundleActivator {
	private ServiceRegistration registration;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Start Achat Moteur");
		Properties props = new Properties();
		props.put("Language", "French");
		registration = context.registerService(AchatMoteurInterface.class.getName(), new AchatMoteurImpl(),
				(Dictionary) props);
		System.out.println("\t Service achat moteur enregistré.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
		System.out.println("\t Service achat moteur supprimé.");
		System.out.println("Stop Achat Moteur");
	}
}