package modèle;

import java.util.Set;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * La classe Panier représente un panier d'acaht de tomates.
 */
public class Panier {
	/**
	 * Prix du forfait d'expédition d'une commande
	 */
	public static final float FORFAIT_EXPEDITION = 5.5f;
	/**
	 * Map des désignations des tomates présentes dans le panier avec leur quantité
	 */
	private Map<Tomate, Integer> listeProduits;
	
	/**
	 * Créer un nouveau panier vide.
	 */
	public Panier() {
		this.listeProduits = new IdentityHashMap<Tomate, Integer>();
	}
	
	/**
	 * Ajoute une quantité donnée d'une tomate au panier.<br>
	 * Si la tomate est déjà présente sa quantité augmente.
	 * @param tomate la tomate à ajouter
	 * @param quantité la quantité à ajouté (doit être strictement positive)
	 * @exception IllegalArgumentException si la quantité est négative ou nulle
	 */
	public void ajouterProduit(Tomate tomate, int quantité) throws IllegalArgumentException {
		if (quantité <= 0) {
			throw new IllegalArgumentException("La quantité doit être strictement positive, actuellement: " + quantité);
		}
		
		int nouvelleQuantité = this.obtenirQuantitéTomate(tomate) + quantité;
		if (nouvelleQuantité > tomate.getStock()) {
			throw new IllegalArgumentException("La quantité d'une tomate dans le panier ne peut être supérieur au stock de celle-ci");
		}
		
		this.listeProduits.put(tomate, nouvelleQuantité);
	}
	
	/**
	 * Défini une quantité à une toamte du panier.<br>
	 * Si la quantité est 0, le produit est retiré du panier.<br>
	 * Si le produit n'est pas dans le panier, le produit est ajouté au panier.
	 * @param tomate la tomate concernée
	 * @param quantité la nouvelle quantité à définir
	 */
	public void définirQuantité(Tomate tomate, int quantité) {
		if (quantité <= 0) {
			this.retirerProduit(tomate);
		} else {
			if (quantité > tomate.getStock()) {
				throw new IllegalArgumentException("La quantité d'une tomate dans le panier ne peut être supérieur au stock de celle-ci");
			}
			this.listeProduits.put(tomate, quantité);
		}
	}
	
	/**
	 * Retire une tomate du panier
	 * @param tomate la tomate à retirer
	 */
	public void retirerProduit(Tomate tomate) {
		this.listeProduits.remove(tomate);
	}
	
	/**
	 * Vide le panier de tous ses produits.
	 */
	public void viderPanier() {
		this.listeProduits.clear();
	}
	
	/**
	 * Permet de vérifier si un produit est dans le panier
	 * @param tomate la tomate à vérifier
	 * @return Renvoi vrai si la tomate est dans le panier, faux sinon
	 */
	public boolean estDansPanier(Tomate tomate) {
		return this.listeProduits.containsKey(tomate);
	}
	
	/**
	 * Obtient la quantité associée à une tomate du panier.
	 * @param tomate la tomate recherché
	 * @return la quantité de cette tomate, ou 0 si elle n'est pas dans le panier
	 */
	public int obtenirQuantitéTomate(Tomate tomate) {
		Integer quantité = this.listeProduits.get(tomate);
		return quantité != null ? quantité : 0;
	}
	
	/**
	 * Calcul le sous-total du panier (sans le forfait d'expédition).
	 * @return le montant total du sous-total du panier hors frais d'expédition
	 */
	public float calculerSousTotal() {
		float sousTotal = 0.0f;
		for (Entry<Tomate, Integer> entry : this.listeProduits.entrySet()) {
			Tomate tomate = entry.getKey();
			int quantité = entry.getValue();
			sousTotal += tomate.getPrixTTC() * quantité;
		}
		return sousTotal;
	}
	
	/**
	 * Renvoi l'ensemble contenant toutes les tomates du pannier
	 * @return l'ensemble des tomates dans le pannier (Set)
	 */
	public Set<Tomate> obtenirTomatesDansPannier() {
		return this.listeProduits.keySet();
	}
	
	/**
	 * Permet de sauvegarder les éléments payer, avec leurs nouvelle quantité, dans la base de donnée
	 * @param filePath chemin du fichier json de la base de donnée
	 * @throws IllegalStateException Erreur lorsque le panier est vide
	 */
	public void payer(String filePath) {
		if (this.listeProduits.size() == 0) {
			throw new IllegalStateException("Vous ne pouvez pas payer lorsque le panier est vide");
		}
		
		Tomates tomates = OutilsBaseDonneesTomates.générationBaseDeTomates(filePath);
		for (Entry<Tomate, Integer> entry : this.listeProduits.entrySet()) {
			Tomate t = entry.getKey();
			int qte = entry.getValue();
			tomates.getTomate(t.getDésignation()).setStock(t.getStock() - qte);
		}
		OutilsBaseDonneesTomates.sauvegarderBaseDeTomates(tomates, filePath);
		viderPanier();
	}
}
