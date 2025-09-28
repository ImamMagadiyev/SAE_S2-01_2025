package ihm;

import javax.swing.*;
import javax.swing.border.*;

import modèle.Client;
import modèle.MoyenPaiement;
import modèle.Panier;
import modèle.Utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogInformationClient extends JDialog {
	private static final long serialVersionUID = -6305095261350545143L;
	
	private static final Color VERT_FONCE = new Color(0, 128, 0);
    private static final Font FONT_LABEL = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font FONT_TITRE = new Font("Serif", Font.BOLD, 24);
    
    private Panier panier;
    
    private JTextField fieldNom;
    private JTextField fieldPrénom;
    private JTextField fieldAdresse;
    private JTextField fieldCodePostal;
    private JTextField fieldVille;
    private JTextField fieldTéléphone;
    private JTextField fieldMail;
    private ButtonGroup groupNewsletter;
    private ButtonGroup groupPaiement;

    public DialogInformationClient(Panier panier) {
    	this.panier = panier;
    	
        setTitle("Ô'Tomates - Coordonnées client");
        setSize(600, 500);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout(10, 10));

        // Panel principal
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Titre centré avec icône
        JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        titrePanel.setBackground(Color.WHITE);
        JLabel titre = new JLabel("Vos coordonnées");
        titre.setFont(FONT_TITRE);
        titre.setForeground(VERT_FONCE);
        titre.setIcon(new ImageIcon(
        	new ImageIcon(Utils.ICONS_PATH + "clientData.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)
        ));
        titrePanel.add(titre);
        contentPanel.add(titrePanel);

        // Formulaire
        JPanel formPanel = générerFormulaire();
        
        contentPanel.add(formPanel);

        // Moyen de paiement
        JPanel paiementPanel = new JPanel();
        paiementPanel.setBackground(Color.WHITE);
        paiementPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(VERT_FONCE),
                "Moyen de paiement",
                TitledBorder.LEFT, TitledBorder.TOP,
                FONT_LABEL, VERT_FONCE
        ));
        paiementPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        groupPaiement = new ButtonGroup();
        for (MoyenPaiement type : MoyenPaiement.values()) {
        	JRadioButton radio = new JRadioButton(type.getNom());
        	radio.setActionCommand(type.getNom());
        	radio.setBackground(Color.WHITE);
        	groupPaiement.add(radio);
        	paiementPanel.add(radio);
        }
        contentPanel.add(paiementPanel);

        // Newsletter
        JPanel newsletterPanel = new JPanel();
        newsletterPanel.setBackground(Color.WHITE);
        newsletterPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(VERT_FONCE),
                "Abonnement à notre Newsletter",
                TitledBorder.LEFT, TitledBorder.TOP,
                FONT_LABEL, VERT_FONCE
        ));
        newsletterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JRadioButton radioNewsletterOui = new JRadioButton("Oui");
        radioNewsletterOui.setActionCommand("Oui");
        JRadioButton radioNewsletterNon = new JRadioButton("Non");
        radioNewsletterNon.setActionCommand("Non");
        radioNewsletterOui.setBackground(Color.WHITE);
        radioNewsletterNon.setBackground(Color.WHITE);

        groupNewsletter = new ButtonGroup();
        groupNewsletter.add(radioNewsletterOui);
        groupNewsletter.add(radioNewsletterNon);

        newsletterPanel.add(radioNewsletterOui);
        newsletterPanel.add(radioNewsletterNon);
        contentPanel.add(newsletterPanel);

        // Boutons OK / Annuler
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setPreferredSize(new Dimension(90, 30));
        cancelButton.setFont(FONT_LABEL);
        cancelButton.addActionListener(e -> dispose());
        
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPane.setBackground(Color.WHITE);
        JButton okButton = new JButton("Payer");
        okButton.setPreferredSize(new Dimension(80, 30));
        okButton.setFont(FONT_LABEL);
        okButton.addActionListener(construireValidationDonnées());

        buttonPane.add(cancelButton);
        buttonPane.add(okButton);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
    }

	private JPanel générerFormulaire() {
		JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        formPanel.setBackground(Color.WHITE);
        
        JLabel lblNom = new JLabel("Nom :");
        lblNom.setFont(FONT_LABEL);
        fieldNom = new JTextField();
        fieldNom.setName("Nom");
        formPanel.add(lblNom);
        formPanel.add(fieldNom);
        
        JLabel lblPrénom = new JLabel("Prénom :");
        lblPrénom.setFont(FONT_LABEL);
        fieldPrénom = new JTextField();
        fieldPrénom.setName("Prénom");
        formPanel.add(lblPrénom);
        formPanel.add(fieldPrénom);
        
        JLabel lblAdresse = new JLabel("Adresse :");
        lblAdresse.setFont(FONT_LABEL);
        fieldAdresse = new JTextField();
        fieldAdresse.setName("Adresse");
        formPanel.add(lblAdresse);
        formPanel.add(fieldAdresse);
        
        JLabel lblCodePostal = new JLabel("Code postal :");
        lblCodePostal.setFont(FONT_LABEL);
        fieldCodePostal = new JTextField();
        fieldCodePostal.setName("Code postal");
        formPanel.add(lblCodePostal);
        formPanel.add(fieldCodePostal);
        
        JLabel lblVille = new JLabel("Ville :");
        lblVille.setFont(FONT_LABEL);
        fieldVille = new JTextField();
        fieldVille.setName("Ville");
        formPanel.add(lblVille);
        formPanel.add(fieldVille);
        
        JLabel lblTéléphone = new JLabel("Téléphone :");
        lblTéléphone.setFont(FONT_LABEL);
        fieldTéléphone = new JTextField();
        fieldTéléphone.setName("Téléphone");
        formPanel.add(lblTéléphone);
        formPanel.add(fieldTéléphone);
        
        JLabel lblMail = new JLabel("Mail :");
        lblTéléphone.setFont(FONT_LABEL);
        fieldMail = new JTextField();
        fieldMail.setName("Mail");
        formPanel.add(lblMail);
        formPanel.add(fieldMail);
		return formPanel;
	}

	private boolean estStringValide(String text) {
		return text.length() > 0;
	}
	
	private boolean estCodePostalValide(String text) {
		return text.matches("^\\d{5}$");
	}
	
	private boolean estTéléphoneValide(String text) {
		return text.matches("\\d{10}");
	}
	
	private boolean estMailValide(String text) {
		return text.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
	}
	
	private void afficherErreurChamp(String fieldName) {
		JOptionPane.showMessageDialog(this, "Veuillez entrez une valeur pour tous les champs (" + fieldName + " manquant ou invalide)", "Erreur", JOptionPane.ERROR_MESSAGE);
	}
	
	private boolean tousLesChampsValides() {
		if (!estStringValide(fieldNom.getText().trim())) {
			afficherErreurChamp("Nom");
			return false;
		}
		if (!estStringValide(fieldPrénom.getText().trim())) {
			afficherErreurChamp("Prénom");
			return false;
		}
		if (!estStringValide(fieldAdresse.getText().trim())) {
			afficherErreurChamp("Adresse");
			return false;
		}
		if (!estCodePostalValide(fieldCodePostal.getText().trim())) {
			afficherErreurChamp("Code postal");
			return false;
		}
		if (!estStringValide(fieldVille.getText().trim())) {
			afficherErreurChamp("Ville");
			return false;
		}
		if (!estTéléphoneValide(fieldTéléphone.getText().trim())) {
			afficherErreurChamp("Téléphone");
			return false;
		}
		if (!estMailValide(fieldMail.getText().trim())) {
			afficherErreurChamp("Mail");
			return false;
		}
		
		ButtonGroup[] buttonGroupList = new ButtonGroup[] { groupPaiement, groupNewsletter };
		for (ButtonGroup btnGroup : buttonGroupList) {
			if (btnGroup.getSelection() == null) {
				String errorMsg = "Veuillez entrez une valeur pour tous les boutons radios (mode de paiement et newsletter)";
				JOptionPane.showMessageDialog(this, errorMsg, "Erreur", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		
		return true;
	}
	
	private void validerDonnée() {
		if (!tousLesChampsValides()) return;

		MoyenPaiement moyenPaiement = MoyenPaiement.getMoyenPaiement(groupPaiement.getSelection().getActionCommand());
		boolean estAbonné = groupNewsletter.getSelection().getActionCommand() == "Oui" ? true : false;
		Client client = new Client(fieldNom.getText().trim(), fieldPrénom.getText().trim(), fieldAdresse.getText().trim(), fieldCodePostal.getText().trim(), fieldVille.getText().trim(), fieldTéléphone.getText().trim(), fieldMail.getText().trim(), moyenPaiement, estAbonné);
		Utils.ouvrirDialog(this, new DialogFacture(client, panier));
	}
	
	private ActionListener construireValidationDonnées() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validerDonnée();
			}
		};
	}
}