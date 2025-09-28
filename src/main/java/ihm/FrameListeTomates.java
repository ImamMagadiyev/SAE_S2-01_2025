package ihm;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import modèle.Tomates;
import modèle.TypeTomate;
import modèle.Utils;
import modèle.Tomate;
import modèle.Couleur;
import modèle.OutilsBaseDonneesTomates;
import modèle.Panier;

public class FrameListeTomates extends JFrame {
	private static final long serialVersionUID = -3085519760421237984L;
	
	private Tomates listeTomates;
	private Panier panier = new Panier();
	private List<Tomate> toutesLesTomates;
	
	private JPanel contentPane;
	private DefaultListModel<String> modelNomsTomates;
	private JList<String> listeNomsTomates;
	private JButton boutonPanier;
	private JComboBox<String> comboType;
	private JComboBox<String> comboCouleur;


	public static void main(String[] args) {
		new FrameListeTomates().setVisible(true);
	}

	public FrameListeTomates() {
		setTitle("Ô'Tomates - Liste des tomates");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 500);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		///// Header /////
		JPanel panelHeader = new JPanel(new BorderLayout());
		
		// Titre et icone
		JLabel titre = new JLabel("NOS GRAINES DE TOMATES");
		titre.setHorizontalAlignment(SwingConstants.CENTER);
		titre.setForeground(new Color(0, 128, 0));
		titre.setFont(new Font("Agency FB", Font.BOLD, 28));

		ImageIcon tomateIconOriginal = new ImageIcon(Utils.ICONS_PATH + "tomate.png");
		Image imgTomate = tomateIconOriginal.getImage();
		Image imgTomateReduite = imgTomate.getScaledInstance(60, 60, Image.SCALE_SMOOTH);

		JLabel labelTomate = new JLabel(new ImageIcon(imgTomateReduite));
		JPanel panelTitreAvecImage = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

		panelTitreAvecImage.add(labelTomate);
		panelTitreAvecImage.add(titre);
		panelHeader.add(panelTitreAvecImage, BorderLayout.CENTER);

		// Bouton panier
		int largeurPanier = 40;
		int hauteurPanier = 40;

		ImageIcon originalPanierIcon = new ImageIcon(Utils.ICONS_PATH + "panierIcon.png");
		Image imgPanier = originalPanierIcon.getImage();
		Image imgPanierReduite = imgPanier.getScaledInstance(largeurPanier, hauteurPanier, Image.SCALE_SMOOTH);	

		boutonPanier = new JButton();
		boutonPanier.setIcon(new ImageIcon(imgPanierReduite));
		boutonPanier.setText("0,00 €");
		boutonPanier.setForeground(new Color(0, 128, 0));
		boutonPanier.setBackground(Color.WHITE);
		boutonPanier.setHorizontalAlignment(SwingConstants.RIGHT);
		panelHeader.add(boutonPanier, BorderLayout.EAST);

		contentPane.add(panelHeader, BorderLayout.NORTH);

		///// Footer /////
		JPanel panelFooter = new JPanel(new BorderLayout());

		// Pannel filtres
		JPanel panelFiltres = new JPanel();
		panelFiltres.setLayout(new BoxLayout(panelFiltres, BoxLayout.X_AXIS));
		panelFiltres.setAlignmentX(Component.LEFT_ALIGNMENT);

		TitledBorder border = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(0, 128, 0), 2),
				"Filtres",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				new Color(0, 128, 0)
		);
		panelFiltres.setBorder(border);

		// Création des combo pour le type et la couleur des tomates
		comboType = new JComboBox<>(new String[] { "Toutes les tomates" });
		for (TypeTomate type : TypeTomate.values()) {
			comboType.addItem(type.getDénomination());
		}

		comboCouleur = new JComboBox<>(new String[] { "Toutes les couleurs" });
		for (Couleur c : Couleur.values()) {
			comboCouleur.addItem(c.getDénomination());
		}

		JPanel panelTypeAvecImage = créerComboAvecIcone(comboType, "toutesLesTomates.png");
		JPanel panelCouleurAvecImage = créerComboAvecIcone(comboCouleur, "toutesLesCouleurs.png");

		// Espacement entre les deux combo
		panelFiltres.add(panelTypeAvecImage);
		panelFiltres.add(Box.createRigidArea(new Dimension(0, 0)));
		panelFiltres.add(panelCouleurAvecImage);

		//// Conseil de culture ////
		JButton conseilCulture = new JButton();

		// Taille du bouton
		int largeur = 80;
		int hauteur = 70;
		conseilCulture.setPreferredSize(new Dimension(largeur, hauteur));

		ImageIcon originalIcon = new ImageIcon(Utils.ICONS_PATH + "imageArbreMain.jpg");
		Image img = originalIcon.getImage();
		Image imgReduite = img.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);

		conseilCulture.setIcon(new ImageIcon(imgReduite));
		panelFooter.add(conseilCulture, BorderLayout.EAST);
		panelFooter.add(panelFiltres, BorderLayout.CENTER);
		contentPane.add(panelFooter, BorderLayout.SOUTH);

		///// Liste des tomates /////
		modelNomsTomates = new DefaultListModel<String>();
		listeNomsTomates = new JList<String>(modelNomsTomates);
		listeNomsTomates.setBackground(new Color(240, 248, 240));
		JScrollPane scrollPane = new JScrollPane(listeNomsTomates);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		// Chargement des tomates via le JSON
		listeTomates = OutilsBaseDonneesTomates.générationBaseDeTomates(Utils.DatabasePaths.Prod.getPath());
		toutesLesTomates = listeTomates.getTomates();
		for (Tomate t : listeTomates.getTomates()) {
			modelNomsTomates.addElement(t.getDésignation());
		}

		///// Ajout de l'intéractivité /////
		conseilCulture.addActionListener(construireActionOuvrirConseilCulture());
		boutonPanier.addActionListener(construireActionAfficherPanier());
		listeNomsTomates.addMouseListener(construireDoubleCliqueListeTomates());
		comboType.addActionListener(construireActionFiltrer());
		comboCouleur.addActionListener(construireActionFiltrer());
	}
	
	private JPanel créerComboAvecIcone(JComboBox<String> combo, String imagePath) {
		JPanel panelAvecIcone = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		ImageIcon icone = new ImageIcon(Utils.ICONS_PATH + imagePath);
		Image imgCouleur = icone.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		panelAvecIcone.add(new JLabel(new ImageIcon(imgCouleur)));
		panelAvecIcone.add(combo);
		return panelAvecIcone;
	}

 	private ActionListener construireActionFiltrer() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filtrer();
			}
		};
	}

 	private ActionListener construireActionOuvrirConseilCulture() {
		return new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				Utils.ouvrirDialog(FrameListeTomates.this, new DialogConseilsCulture());
			}
		};
	}

	private ActionListener construireActionAfficherPanier() {
		return new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panier.obtenirTomatesDansPannier().size() > 0) {
					DialogPanier dialog = new DialogPanier(FrameListeTomates.this, panier);
					Utils.ouvrirDialog(FrameListeTomates.this, dialog);
				} else {
					JOptionPane.showMessageDialog(FrameListeTomates.this, "Le panier est vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}

	private MouseAdapter construireDoubleCliqueListeTomates() {
		return new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = listeNomsTomates.locationToIndex(e.getPoint());
					if (index >= 0 && index < toutesLesTomates.size()) {
						DialogInfoTomate dialog = new DialogInfoTomate(FrameListeTomates.this, panier, toutesLesTomates.get(index));
						Utils.ouvrirDialog(FrameListeTomates.this, dialog);
					}
				}
			}
		};
	}

	private void filtrer() {
		modelNomsTomates.clear();
		String typeChoisi = (String) comboType.getSelectedItem();
		String couleurChoisie = (String) comboCouleur.getSelectedItem();
		List<Tomate> nouvelleListeTomates;

		if (typeChoisi.equals("Toutes les tomates") && couleurChoisie.equals("Toutes les couleurs")) {
			nouvelleListeTomates = listeTomates.getTomates();
		} else if (typeChoisi.equals("Toutes les tomates") && !couleurChoisie.equals("Toutes les couleurs")) {
			nouvelleListeTomates = listeTomates.tomatesDeCouleur(Couleur.getCouleur(couleurChoisie));
		} else if (!typeChoisi.equals("Toutes les tomates") && couleurChoisie.equals("Toutes les couleurs")) {
			nouvelleListeTomates = listeTomates.tomatesDeType(TypeTomate.getTypeTomate(typeChoisi));
		} else {
			nouvelleListeTomates = listeTomates.tomatesDetypeDeCouleur(TypeTomate.getTypeTomate(typeChoisi), Couleur.getCouleur(couleurChoisie));
		}
		toutesLesTomates = nouvelleListeTomates;

		for (Tomate t : nouvelleListeTomates) {
			modelNomsTomates.addElement(t.getDésignation());
		}
	}

	public void mettreAJourBoutonPanier() {
		boutonPanier.setText(String.format("%.2f €", panier.calculerSousTotal()));
	}
}