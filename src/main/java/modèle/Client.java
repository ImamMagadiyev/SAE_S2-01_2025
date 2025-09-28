package modèle;

/**
 * Représente un client avec ses informations personnelles et de contact.
 */
public class Client {
	private final String nom;
	private final String prénom;
	private final String adresse;
	private final String codePostal;
	private final String ville;
	private final String téléphone;
	private final String mail;
	private final MoyenPaiement moyenDePaiement;
	private final boolean abonnementNewsletter;
	
	/**
	 * Construit un nouveau client avec les informations spécifiées.
	 *
	 * @param nom Le nom de famille du client.
	 * @param prénom Le prénom du client.
	 * @param adresse L'adresse postale du client.
	 * @param codePostal Le code postal de l'adresse du client.
	 * @param ville La ville de résidence du client.
	 * @param téléphone Le numéro de téléphone du client.
	 * @param mail L'adresse mail du client.
	 */
	public Client(String nom, String prénom, String adresse, String codePostal, String ville, String téléphone, String mail, MoyenPaiement moyenDePaiement, boolean abonnementNewsletter) {
		this.nom = nom;
		this.prénom = prénom;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;
		this.téléphone = téléphone;
		this.mail = mail;
		this.moyenDePaiement = moyenDePaiement;
		this.abonnementNewsletter = abonnementNewsletter;
	}

	/**
	 * Retourne le nom de famille du client.
	 *
	 * @return Le nom de famille.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Retourne le prénom du client.
	 *
	 * @return Le prénom.
	 */
	public String getPrénom() {
		return prénom;
	}

	/**
	 * Retourne l'adresse du client.
	 *
	 * @return L'adresse postale.
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * Retourne le code postal du client.
	 *
	 * @return Le code postal.
	 */
	public String getCodePostal() {
		return codePostal;
	}

	/**
	 * Retourne la ville de résidence du client.
	 *
	 * @return La ville.
	 */
	public String getVille() {
		return ville;
	}

	/**
	 * Retourne le numéro de téléphone du client.
	 *
	 * @return Le téléphone.
	 */
	public String getTéléphone() {
		return téléphone;
	}

	/**
	 * Retourne l'adresse mail du client.
	 *
	 * @return L'adresse mail.
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Retourne le moyen de paiement du client
	 *
	 * @return Le moyen de paiement
	 */
	public MoyenPaiement getMoyenDePaiement() {
		return moyenDePaiement;
	}

	/**
	 * Retourne vrai si le client s'abonne a la newsletter, faux sinon
	 *
	 * @return Si le client s'abonne a la newsletter
	 */
	public boolean getAbonnementNewsletter() {
		return this.abonnementNewsletter;
	}
}
