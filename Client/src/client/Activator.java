package client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import vendeur.AchatVoitureInterface;

public class Activator implements BundleActivator {
	private ServiceTracker achatVoitureTracker;
	static BundleContext leContext;

	@Override
	public void start(BundleContext context) throws Exception {
		leContext = context;
		System.out.println("Client Started");
		achatVoitureTracker = new ServiceTracker(leContext, AchatVoitureInterface.class.getName(), null);
		achatVoitureTracker.open();
		AchatVoitureInterface achatVoiture = (AchatVoitureInterface) achatVoitureTracker.getService();

		if (achatVoiture == null) {
			System.out.println("\t Resultat de recherche service achat moteur : indisponible.");
			System.out.println("\t Achat voiture : invoke methode acheterMoteur(); avec echec.");
		} else {
			// le bon cas
			achatVoiture.acheterVoiture();
			System.out.println("\t Achat voiture : invoke acheterMoteur() avec succés!");
		}

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("Client Stopped");
	}

}
