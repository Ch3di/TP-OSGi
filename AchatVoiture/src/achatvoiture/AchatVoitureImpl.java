package achatvoiture;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import carroussier.AchatCarrosserieInterface;
import motoriste.AchatMoteurInterface;
import vendeur.AchatVoitureInterface;

public class AchatVoitureImpl implements BundleActivator, AchatVoitureInterface, ServiceListener {

	private ServiceTracker achatMoteurTracker;
	private ServiceTracker achatCarrosserieTracker;
	private ServiceRegistration registration;
	static BundleContext leContext;

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Start Achat Voiture");
		leContext = context;
		context.addServiceListener(this);
		Properties props = new Properties();
		props.put("Language", "French");
		registration = context.registerService(AchatVoitureInterface.class.getName(), new AchatVoitureImpl(),
				(Dictionary) props);
		System.out.println("\t Service achat voiture enregistré.");
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
		System.out.println("\t Service achat voiture supprimé.");
		System.out.println("Stop Achat Voiture");
	}

	@Override
	public void acheterVoiture() {
		// recherche d'un service moteur
		achatMoteurTracker = new ServiceTracker(leContext, AchatMoteurInterface.class.getName(), null);
		achatMoteurTracker.open();
		AchatMoteurInterface achatMoteur = (AchatMoteurInterface) achatMoteurTracker.getService();
		// recherche service carrosserie
		achatCarrosserieTracker = new ServiceTracker(leContext, AchatCarrosserieInterface.class.getName(), null);
		achatCarrosserieTracker.open();
		AchatCarrosserieInterface achatCarrosserie = (AchatCarrosserieInterface) achatCarrosserieTracker.getService();
		if ((achatCarrosserie == null) || (achatMoteur == null)) {
			if (achatCarrosserie == null) {
				System.out.println("\t Resultat de recherche service achat carrosserie: indisponible.");
			}
			if (achatMoteur == null) {
				System.out.println("\t Resultat de recherche service achat moteur: indisponible.");
			}
			System.out.println("\t Achat voiture : invoke methode acheterVoiture(); avec echec.");
		} else {
			// le bon cas
			achatMoteur.acheterMoteur();
			achatCarrosserie.AcheterCorrosserie();
			System.out
					.println("\t Achat voiture : invoke acheterMoteur(); et " + " acheterCorrosserie(); avec succés!");
		}
	}

	@Override
	public void serviceChanged(ServiceEvent event) {
		String[] objectClass = (String[]) event.getServiceReference().getProperty("objectClass");
		String src = event.getServiceReference().getBundle().getSymbolicName();
		if (((src.toUpperCase()).equals("AchatMoteur".toUpperCase()))
				|| ((src.toUpperCase()).equals("AchatMoteur".toUpperCase()))) {
			System.out.print("Evenement>> Bundle source : " + src + ",détecté à partir de bundle Achat Voiture: ");
			if (event.getType() == ServiceEvent.REGISTERED) {
				System.out.println(" Service de type " + objectClass[0] + "enregistré.");
			} else if (event.getType() == ServiceEvent.UNREGISTERING) {
				System.out.println(" Service de type " + objectClass[0] + "supprimé.");
			} else if (event.getType() == ServiceEvent.MODIFIED) {
				System.out.println(" Service de type " + objectClass[0] + " modifié.");
			}
		}
	}
}
