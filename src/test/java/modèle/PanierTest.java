package modèle;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PanierTest {
	private final String DB_TEST_PATH = Utils.DatabasePaths.Test.getPath();
	
	private Panier panier;
	private Tomate tomate1;
	private Tomate tomate2;

	@Before
	public void setUp() throws Exception {
		this.panier = new Panier();
		this.tomate1 = new Tomate(TypeTomate.TOMATES, Couleur.ROUGE, "Tomate 1", "Sous titre", "image", "Une tomate1", 5, 10, 10f);
		this.tomate2 = new Tomate(TypeTomate.TOMATES_CERISES, Couleur.JAUNE, "Tomate 2", "Sous titre", "image", "Une tomate2", 10, 10, 7f);
	}

	@After
	public void tearDown() throws Exception {
		this.panier = null;
		this.tomate1 = null;
		this.tomate2 = null;
	}
	
	@Test
	public void testEstDansPanier() {
		assertFalse(this.panier.estDansPanier(tomate1));
		this.panier.ajouterProduit(tomate1, 5);
		assertTrue(this.panier.estDansPanier(tomate1));
	}
	
	@Test
	public void testEstDansPanierTomateModifié() {
		this.panier.ajouterProduit(tomate1, 5);
		tomate1.setStock(2);
		assertTrue(this.panier.estDansPanier(tomate1));
	}

	@Test
	public void testAjouterProduit() {
		this.panier.ajouterProduit(tomate1, 2);
		assertEquals(2, this.panier.obtenirQuantitéTomate(tomate1));
	}
	
	@Test
	public void testAjouterProduitQuandDéjàDansPanier() {
		this.panier.ajouterProduit(tomate1, 2);
		this.panier.ajouterProduit(tomate1, 3);
		assertEquals(5, this.panier.obtenirQuantitéTomate(tomate1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAjouterProduitSupérieurStock() {
		this.panier.ajouterProduit(tomate1, tomate1.getStock()+1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAjouterProduitQuantitéNulle() {
		this.panier.ajouterProduit(tomate1, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAjouterProduitQuantitéNégative() {
		this.panier.ajouterProduit(tomate1, -1);
	}

	@Test
	public void testDefinirQuantité() {
		this.panier.définirQuantité(tomate1, 2);
		assertEquals(2, this.panier.obtenirQuantitéTomate(tomate1));
	}
	
	@Test
	public void testDéfinirPanierModifierTomateExistante() {
		this.panier.définirQuantité(tomate1, 2);
		this.panier.définirQuantité(tomate1, 3);
		assertEquals(3, this.panier.obtenirQuantitéTomate(tomate1));
	}
	
	@Test
	public void testDéfinirPanierSupprimerTomate() {
		this.panier.définirQuantité(tomate1, 2);
		this.panier.définirQuantité(tomate1, 0);
		assertEquals(0, this.panier.obtenirQuantitéTomate(tomate1));
		assertFalse(this.panier.estDansPanier(tomate1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDefinirQuantitéSupérieurStock() {
		this.panier.définirQuantité(tomate1, tomate1.getStock()+1);
	}

	@Test
	public void testRetirerProduit() {
		this.panier.ajouterProduit(tomate1, 5);
		this.panier.retirerProduit(tomate1);
		assertFalse(this.panier.estDansPanier(tomate1));
	}

	@Test
	public void testViderPanier() {
		this.panier.ajouterProduit(tomate1, 2);
		this.panier.ajouterProduit(tomate2, 1);
		this.panier.viderPanier();
		assertFalse(this.panier.estDansPanier(tomate1));
		assertFalse(this.panier.estDansPanier(tomate2));
	}

	@Test
	public void testObtenirQuantitéTomate() {
		assertEquals(0, this.panier.obtenirQuantitéTomate(tomate1));
		this.panier.ajouterProduit(tomate1, 3);
		assertEquals(3, this.panier.obtenirQuantitéTomate(tomate1));
	}

	@Test
	public void testCalculerSousTotal() {
		this.panier.ajouterProduit(tomate1, 2);
		this.panier.ajouterProduit(tomate2, 3);
		assertEquals(41.0f, this.panier.calculerSousTotal(), 0.0f);
	}
	
	@Test
	public void testObtenirTomatesDansPannier() {
		this.panier.ajouterProduit(tomate1, 1);
		Set<Tomate> tomates = this.panier.obtenirTomatesDansPannier();
		assertTrue(tomates.contains(tomate1));
		assertFalse(tomates.contains(tomate2));
		
		this.panier.ajouterProduit(tomate2, 2);
		assertTrue(tomates.contains(tomate2));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testPayerPanierVide() {
	    panier.payer(DB_TEST_PATH);
	}
	
	@Test
	public void testPayerPanierAvecProduits() throws Exception {
		Tomates tomates = OutilsBaseDonneesTomates.générationBaseDeTomates(Utils.DatabasePaths.Original.getPath());
		OutilsBaseDonneesTomates.sauvegarderBaseDeTomates(tomates, DB_TEST_PATH); // Copie du fichier
		Tomate t = tomates.getTomates().get(0);
		panier.ajouterProduit(t, 5);
		panier.payer(DB_TEST_PATH);
		
		tomates = OutilsBaseDonneesTomates.générationBaseDeTomates(DB_TEST_PATH);
		Tomate t2 = tomates.getTomates().get(0);
		assertEquals(5, t2.getStock());
	}
	
	@Test
	public void testPayerVideLePanier() {
		Tomates tomates = OutilsBaseDonneesTomates.générationBaseDeTomates(Utils.DatabasePaths.Original.getPath());
		OutilsBaseDonneesTomates.sauvegarderBaseDeTomates(tomates, DB_TEST_PATH); // Copie du fichier
		
	    panier.ajouterProduit(tomates.getTomates().get(0), 1);
	    panier.payer(DB_TEST_PATH);
	    assertTrue(panier.obtenirTomatesDansPannier().isEmpty());
	}
}
