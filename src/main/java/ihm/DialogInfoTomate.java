package ihm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

import java.util.List;

import modèle.Panier;
import modèle.Tomate;
import modèle.Utils;

public class DialogInfoTomate extends JDialog {
	private static final long serialVersionUID = -5562898659056064527L;
	
	private Panier panier;
	private Tomate tomate;
	private int stock;
	private boolean estEnStock;
	private List<Tomate> tomatesApparentées;
	private FrameListeTomates ihmListeTomate;

	private JTextArea textAreaDescription;
    private JTextField textFieldNombreGraines;
    private JTextField textFieldPrix;
    private JSpinner spinnerQuantite;
    private JComboBox<String> comboBoxApparentées;
    
	public DialogInfoTomate(FrameListeTomates ihmListeTomate, Panier panier, Tomate tomate) {
    	construireIHM(ihmListeTomate, panier, tomate);
    }

	private void construireIHM(FrameListeTomates ihmListeTomate, Panier panier, Tomate tomate) {
		this.panier = panier;
    	this.tomate = tomate;
    	this.stock = tomate.getStock() - panier.obtenirQuantitéTomate(tomate);
    	this.estEnStock = this.stock <= 0;
    	this.ihmListeTomate = ihmListeTomate;
    	getContentPane().removeAll();
    	
        setTitle("Ô'Tomates - Info Tomate");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 420);
        getContentPane().setLayout(new BorderLayout(10, 10));
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        
        // Titre
        JLabel lblTitre = new JLabel(tomate.getDésignation());
        lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitre.setForeground(new Color(0, 128, 0));
        lblTitre.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
        mainPanel.add(lblTitre, BorderLayout.NORTH);

        // Partie gauche : image + "En Stock"
        JPanel panelGauche = new JPanel();
        panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.Y_AXIS));
        panelGauche.setPreferredSize(new Dimension(250, 0));
        mainPanel.add(panelGauche, BorderLayout.WEST);

        JLabel lblImage = new JLabel();
        ImageIcon icon = new ImageIcon(Utils.IMAGES_PATH + "Tomates200x200/" + tomate.getNomImage() + ".jpg");
        Image img = icon.getImage().getScaledInstance(220, 180, Image.SCALE_SMOOTH);
        lblImage.setIcon(new ImageIcon(img));
        lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelGauche.add(lblImage);

        JLabel lblEnStock = new JLabel(estEnStock ? "Hors stock" : "En Stock");
        lblEnStock.setForeground(estEnStock ? new Color(128, 0, 0) : new Color(0, 128, 0));
        lblEnStock.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblEnStock.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEnStock.setBorder(new EmptyBorder(10,0,0,0));
        panelGauche.add(lblEnStock);
        
        // Menu déroulant des tomates apprentées
        if (estEnStock) {
        	tomatesApparentées = tomate.getTomatesApparentées();
        	if (!tomatesApparentées.isEmpty()) {
        		JLabel lblApparentées = new JLabel("Tomates apparentées (" + tomatesApparentées.size() + ") :");
        		lblApparentées.setAlignmentX(Component.CENTER_ALIGNMENT);
        		lblApparentées.setBorder(new EmptyBorder(10, 0, 5, 0));
        		panelGauche.add(lblApparentées);
        		
        		comboBoxApparentées = new JComboBox<String>();
        		comboBoxApparentées.addItem("Sélectionnez une tomate...");
        		for (Tomate t : tomatesApparentées) {
        			comboBoxApparentées.addItem(t.getDésignation());
        		}
        		comboBoxApparentées.setMaximumSize(new Dimension(220, 25));
        		comboBoxApparentées.setAlignmentX(Component.CENTER_ALIGNMENT);
        		comboBoxApparentées.addActionListener(construireOuvrirTomateApparentée());
        		panelGauche.add(comboBoxApparentées);
        	}
        }

        // Partie droite : description et champs
        JPanel panelDroite = new JPanel();
        panelDroite.setLayout(new BorderLayout(10, 10));
        mainPanel.add(panelDroite, BorderLayout.CENTER);

        // Panel description avec bordure titrée verte
        JPanel panelDescription = new JPanel(new BorderLayout());
        TitledBorder titleBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 128, 0)), "Description");
        titleBorder.setTitleColor(new Color(0, 128, 0));
        panelDescription.setBorder(titleBorder);
        panelDroite.add(panelDescription, BorderLayout.NORTH);

        textAreaDescription = new JTextArea();
        textAreaDescription.setEditable(false);
        textAreaDescription.setLineWrap(true);
        textAreaDescription.setWrapStyleWord(true);
        textAreaDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textAreaDescription.setText(tomate.getDescription());
        panelDescription.add(new JScrollPane(textAreaDescription), BorderLayout.CENTER);
        textAreaDescription.setPreferredSize(new Dimension(300, 120));

        // Panel champs "Nombre de graines" et "Prix"
        JPanel panelChamps = new JPanel();
        panelDroite.add(panelChamps, BorderLayout.CENTER);

        GroupLayout layout = new GroupLayout(panelChamps);
        panelChamps.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel lblNbGraines = new JLabel("Nombre de graines");
        textFieldNombreGraines = new JTextField(Integer.toString(tomate.getNbGrainesParSachet()), 3);
        textFieldNombreGraines.setEditable(false);

        JLabel lblPrix = new JLabel("Prix :");
        textFieldPrix = new JTextField(String.format("%.2f€", tomate.getPrixTTC()), 5);
        textFieldPrix.setEditable(false);

        spinnerQuantite = new JSpinner(new SpinnerNumberModel(0, 0, this.stock, 1));
        spinnerQuantite.setEnabled(!estEnStock);
        spinnerQuantite.setPreferredSize(new Dimension(50, 25));
        
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblNbGraines)
                    .addComponent(lblPrix))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(textFieldNombreGraines)
                    .addComponent(textFieldPrix))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(spinnerQuantite))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNbGraines)
                    .addComponent(textFieldNombreGraines)
                    .addComponent(spinnerQuantite))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrix)
                    .addComponent(textFieldPrix))
        );

        // Panel boutons en bas
        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        getContentPane().add(panelBoutons, BorderLayout.SOUTH);

        JButton btnAjouter = new JButton("Ajouter au panier");
        btnAjouter.addActionListener(construireActionAjouterTomate());
        panelBoutons.add(btnAjouter);

        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
        panelBoutons.add(btnAnnuler);
	}

	private ActionListener construireOuvrirTomateApparentée() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tomateSélectionnée = comboBoxApparentées.getSelectedIndex();
				if (tomateSélectionnée != 0) { // Si pas "Sélectionnez une tomate..."
					Tomate tomate = tomatesApparentées.get(tomateSélectionnée - 1);
					construireIHM(ihmListeTomate, panier, tomate);
				}
			}
		};
	}

	private ActionListener construireActionAjouterTomate() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int qte = (int) spinnerQuantite.getValue();
				if (qte <= 0) {
					JOptionPane.showMessageDialog(DialogInfoTomate.this, "Quantité invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
					return;
				}
				panier.ajouterProduit(tomate, qte);
				ihmListeTomate.mettreAJourBoutonPanier();
				dispose();
			}
		};
	}
}