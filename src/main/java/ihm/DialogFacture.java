package ihm;

import modèle.Client;
import modèle.MoyenPaiement;
import modèle.OutilsBaseDonneesTomates;
import modèle.Panier;
import modèle.Tomate;
import modèle.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DialogFacture extends JDialog {
	private static final long serialVersionUID = 2036524216896017338L;
	
	private JScrollPane scrollPaneFacture;
    
    public static void main(String[] args) {
    	Panier p = new Panier();
    	p.ajouterProduit(
    		OutilsBaseDonneesTomates.générationBaseDeTomates(Utils.DatabasePaths.Prod.getPath()).getTomates().get(0),
    		1
    	);
    	new DialogFacture(new Client("Dupont", "Jean", "123 rue Exemple", "75000", "Paris", "0123456789", "jean.dupont@example.com", MoyenPaiement.CarteCrédits, false), p).setVisible(true);;
    }
	
	public DialogFacture(Client client, Panier panier) {
        setTitle("Ô'Tomates - Facture");
        setSize(700, 650);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fermerApplication();
            }
        });

        String nom = client.getNom();
        String prenom = client.getPrénom();
        String adresse = client.getAdresse();
        String codePostal = client.getCodePostal();
        String ville = client.getVille();
        String mail = client.getMail();
        String telephone = client.getTéléphone();
        MoyenPaiement moyenDePaiement = client.getMoyenDePaiement();

        JPanel contentPane = new JPanel(new BorderLayout(15, 15));
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setBackground(new Color(250, 255, 250));
        setContentPane(contentPane);

        ///// Header /////
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(250, 255, 250));

        JPanel panelHeaderLeft = new JPanel();
        panelHeaderLeft.setLayout(new BoxLayout(panelHeaderLeft, BoxLayout.X_AXIS));
        panelHeaderLeft.setOpaque(false);

        JLabel lblGauche = obtenirIconeRedimenssionée("facture.png", 50, 50);
        panelHeaderLeft.add(lblGauche);
        panelHeaderLeft.add(Box.createRigidArea(new Dimension(10, 0)));

        JLabel lblTitre = new JLabel("Votre facture");
        lblTitre.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitre.setForeground(new Color(0, 128, 0));
        lblTitre.setAlignmentY(Component.CENTER_ALIGNMENT);
        lblTitre.setHorizontalAlignment(SwingConstants.LEFT);
        panelHeaderLeft.add(lblTitre);

        topPanel.add(panelHeaderLeft, BorderLayout.WEST);

        JLabel lblDroite = obtenirIconeRedimenssionée("panier-fruit.jpg", 50, 50);
        topPanel.add(lblDroite, BorderLayout.EAST);

        contentPane.add(topPanel, BorderLayout.NORTH);

        ///// Facture /////
        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.setBackground(new Color(250, 255, 250));
        scrollPaneFacture = new JScrollPane(panelCenter);
        contentPane.add(scrollPaneFacture, BorderLayout.CENTER);

        JPanel panelFacture = new JPanel();
        panelFacture.setBackground(Color.WHITE);
        panelFacture.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 2),
            BorderFactory.createEmptyBorder(18, 24, 18, 24)
        ));
        panelFacture.setLayout(new BorderLayout(14, 14));

        // --- Infos client ---
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblMerci = new JLabel("Merci de votre visite !");
        lblMerci.setFont(new Font("Serif", Font.ITALIC, 15));
        lblMerci.setForeground(new Color(0, 128, 0));
        lblMerci.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0)));
        lblMerci.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblMerci.setHorizontalAlignment(SwingConstants.LEFT);
        infoPanel.add(lblMerci);
        
        infoPanel.add(Box.createVerticalStrut(10)); 

        JLabel lblSlogan = new JLabel("Ô'Tomates, redécouvrez le goût des tomates anciennes !");
        lblSlogan.setFont(new Font("Serif", Font.BOLD, 16));
        lblSlogan.setForeground(new Color(0, 128, 0));
        lblSlogan.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSlogan.setHorizontalAlignment(SwingConstants.LEFT);
        infoPanel.add(lblSlogan);
        
        // ESPACE entre le slogan et la date
        infoPanel.add(Box.createVerticalStrut(10));

        JLabel lblDateCommande = new JLabel("Commande du " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE d MMMM yyyy à HH:mm:ss")) + " heure d’été d’Europe centrale");
        lblDateCommande.setFont(new Font("Serif", Font.ITALIC, 13));
        lblDateCommande.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblDateCommande.setHorizontalAlignment(SwingConstants.LEFT);
        infoPanel.add(lblDateCommande);
        
        // ESPACE entre la date et les infos client
        infoPanel.add(Box.createVerticalStrut(10));

        JLabel lblNomClient = new JLabel(prenom + " " + nom.toUpperCase());
        lblNomClient.setFont(new Font("Serif", Font.BOLD, 14));
        lblNomClient.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblNomClient.setHorizontalAlignment(SwingConstants.LEFT);
        infoPanel.add(lblNomClient);

        JLabel lblAdresse = new JLabel("Adresse : " + adresse + " " + codePostal + " " + ville );
        lblAdresse.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblAdresse.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblAdresse.setHorizontalAlignment(SwingConstants.LEFT);
        infoPanel.add(lblAdresse);

        JLabel lblTelephone = new JLabel("Téléphone : " + telephone);
        lblTelephone.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblTelephone.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTelephone.setHorizontalAlignment(SwingConstants.LEFT);
        infoPanel.add(lblTelephone);

        JLabel lblMail = new JLabel("Mél : " + mail);
        lblMail.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblMail.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblMail.setHorizontalAlignment(SwingConstants.LEFT);
        infoPanel.add(lblMail);

        panelFacture.add(infoPanel, BorderLayout.NORTH);

        ///// Tableau des produits /////
        String[] columns = {"Produit", "Prix unitaire", "Quantité", "Prix TTC"};
        DefaultTableModel model = new DefaultTableModel(new Object[][] {}, columns) {
            private static final long serialVersionUID = -3016080799863652187L;
            public boolean isCellEditable(int row, int column) { return false; }
        };

        float sousTotal = panier.calculerSousTotal();
        float frais = Panier.FORFAIT_EXPEDITION;
        float total = sousTotal + frais;

        for (Tomate t : panier.obtenirTomatesDansPannier()) {
            int qte = panier.obtenirQuantitéTomate(t);
            float prix = t.getPrixTTC();
            float totalLigne = prix * qte;
            model.addRow(new Object[]{t.getDésignation(), String.format("%.2f €", prix), qte, String.format("%.2f €", totalLigne)});
        }
        
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        table.setFillsViewportHeight(true);
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(table, BorderLayout.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        panelFacture.add(tablePanel, BorderLayout.CENTER);

        ///// Résumé des totaux /////
        JPanel resumePanel = new JPanel();
        resumePanel.setLayout(new GridLayout(3, 1));
        resumePanel.setBackground(Color.WHITE);
        resumePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JLabel lblCommande = new JLabel("TOTAL TTC COMMANDE : " + String.format("%.2f €", sousTotal));
        lblCommande.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblCommande.setHorizontalAlignment(SwingConstants.LEFT);
        lblCommande.setAlignmentX(Component.LEFT_ALIGNMENT);
        resumePanel.add(lblCommande);

        JLabel lblFrais = new JLabel("FORFAIT FRAIS DE PORT : " + String.format("%.2f €", frais));
        lblFrais.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblFrais.setHorizontalAlignment(SwingConstants.LEFT);
        lblFrais.setAlignmentX(Component.LEFT_ALIGNMENT);
        resumePanel.add(lblFrais);

        JLabel lblFinal = new JLabel("PRIX TOTAL TTC : " + String.format("%.2f €", total) + " payé par " + moyenDePaiement.getNom());
        lblFinal.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblFinal.setHorizontalAlignment(SwingConstants.LEFT);
        lblFinal.setAlignmentX(Component.LEFT_ALIGNMENT);
        resumePanel.add(lblFinal);

        panelFacture.add(resumePanel, BorderLayout.SOUTH);

        panelCenter.add(panelFacture, BorderLayout.CENTER);

        ///// Boutons /////
        JPanel panelSouth = new JPanel();
        JButton btnPrint = new JButton("Imprimer");
        btnPrint.setFocusPainted(false);
        btnPrint.addActionListener(construireActionImprimerFacture());

        JButton btnQuit = new JButton("Quitter");
        btnQuit.addActionListener(construireActionQuitterApplication());

        panelSouth.add(btnPrint);
        panelSouth.add(btnQuit);
        contentPane.add(panelSouth, BorderLayout.SOUTH);

        // Paiement de la facutre
        //panier.payer(Utils.DatabasePaths.Prod.getPath());
    }
    
    private void lancerImpression() {
    	PrinterJob impression = PrinterJob.getPrinterJob();
    	impression.setJobName("Impression de la facture");
    	impression.setPrintable(printFn());
    	
    	if (impression.printDialog()) {
    		try {
    			impression.print();
    		} catch (PrinterException err) {
    			JOptionPane.showMessageDialog(DialogFacture.this, "Erreur lors de l'impression", "Erreur", JOptionPane.ERROR_MESSAGE);
    		}
    	}
    }

    private Printable printFn() {
        return new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) return NO_SUCH_PAGE;

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                // Récupérer les dimensions de la page imprimable
                double pageWidth = pageFormat.getImageableWidth();
                double pageHeight = pageFormat.getImageableHeight();

                // Récupérer les dimensions du composant à imprimer
                Dimension dim = scrollPaneFacture.getViewport().getView().getPreferredSize();

                double compWidth = dim.getWidth();
                double compHeight = dim.getHeight();

                // Calcul du ratio de redimensionnement
                double scaleX = pageWidth / compWidth;
                double scaleY = pageHeight / compHeight;
                double scale = Math.min(scaleX, scaleY); // On garde le plus petit pour tout faire tenir

                // Appliquer l'échelle
                g2d.scale(scale, scale);

                scrollPaneFacture.getViewport().getView().printAll(g2d);

                return PAGE_EXISTS;
            }
        };
    }

	private ActionListener construireActionImprimerFacture() {
		return new ActionListener() {        
			@Override
			public void actionPerformed(ActionEvent e) {
				lancerImpression();
			}
		};
	}
	
	private void fermerApplication() {
		for (Frame frame : Frame.getFrames()) {
		    frame.dispose();
		}
		FrameListeTomates.main(null);
	}

	private ActionListener construireActionQuitterApplication() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fermerApplication();
			}
		};
	}

    private JLabel obtenirIconeRedimenssionée(String imagePath, int width, int height) {
    	JLabel label = new JLabel();
    	ImageIcon icone = new ImageIcon(Utils.ICONS_PATH + imagePath);
		Image imgRedimenssionée = icone.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		label.setIcon(new ImageIcon(imgRedimenssionée));
		return label;
    }
}
