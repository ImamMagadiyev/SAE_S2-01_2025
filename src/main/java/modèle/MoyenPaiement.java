package modèle;

public enum MoyenPaiement {

    CarteCrédits("Carte de crédits"),
    Paypal("Paypal"),
    Chèque("Chèque");

    private final String nom;

    private MoyenPaiement(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }

    public static MoyenPaiement getMoyenPaiement(String nom) {
        for (MoyenPaiement type : MoyenPaiement.values()) {
            if (type.getNom().equals(nom)) {
                return type;
            }
        }
        return null;
    }
}
