package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import modèle.Panier;
import modèle.Tomate;
import modèle.Utils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogPanier extends JDialog {
	private static final long serialVersionUID = -4875650324358606344L;
	
	private FrameListeTomates ihmListeTomate;
	private Panier panier;
	
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField sousTotalField;
	private JTextField fraisField;
	private JTextField totalField;
	private JButton validerButton;
	private JButton viderButton;
	private JButton continuerButton;

	public DialogPanier(FrameListeTomates ihmListeTomate, Panier panier) {
		this.ihmListeTomate = ihmListeTomate;
		this.panier = panier;
		
        setTitle("Ô'Tomates - Votre panier");
        setBounds(200, 200, 600, 600);
        getContentPane().setLayout(new BorderLayout());        

        ///// Header ///// 
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Image scaledImage = new ImageIcon(Utils.ICONS_PATH + "panierIcon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel icone = new JLabel(scaledIcon);
        JLabel titre = new JLabel("Votre panier");
        titre.setFont(new Font("Agency FB", Font.BOLD, 32));
        titre.setForeground(new Color(0, 128, 0));
        header.add(icone);
        header.add(titre);
        getContentPane().add(header, BorderLayout.NORTH);

        ///// Footer /////
        JPanel footer = new JPanel(new BorderLayout(10, 10));
        footer.setBackground(Color.LIGHT_GRAY);

        // Bouton recalculer
        JPanel recalculePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        recalculePanel.setBackground(Color.LIGHT_GRAY);
        JButton recalculer = new JButton("Recalculer le panier");
        recalculer.setPreferredSize(new Dimension(170, 30));
        recalculePanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 0, 0));
        recalculePanel.add(recalculer);

        // Totaux
        JPanel totauxPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        totauxPanel.setBorder(new EmptyBorder(10, 0, 0, 10));
        totauxPanel.setBackground(Color.LIGHT_GRAY);

        float sousTotal = this.panier.calculerSousTotal();
        sousTotalField = créerField(String.format("%.2f €", sousTotal));
        fraisField = créerField(String.format("%.2f €", Panier.FORFAIT_EXPEDITION));
        totalField = créerField(String.format("%.2f €", sousTotal + Panier.FORFAIT_EXPEDITION));
        JLabel labelTotal = new JLabel("TOTAL : ");
        labelTotal.setFont(labelTotal.getFont().deriveFont(Font.BOLD));
        labelTotal.setForeground(new Color(0, 128, 0));

        totauxPanel.add(new JLabel("Sous-Total : "));
        totauxPanel.add(sousTotalField);
        totauxPanel.add(new JLabel("Expédition (forfait) : "));
        totauxPanel.add(fraisField);
        totauxPanel.add(labelTotal);
        totauxPanel.add(totalField);

        JPanel pannelFooterHaut = new JPanel(new BorderLayout());
        pannelFooterHaut.setBackground(Color.LIGHT_GRAY);
        pannelFooterHaut.add(recalculePanel, BorderLayout.WEST);
        pannelFooterHaut.add(totauxPanel, BorderLayout.EAST);

        // Boutons
        JPanel pannelFooterBas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pannelFooterBas.setBackground(Color.LIGHT_GRAY);
        Dimension btnTaille = new Dimension(170, 30);

        validerButton = new JButton("Valider le panier");
        viderButton = new JButton("Vider le panier");
        continuerButton = new JButton("Continuer les achats");

        for (JButton b : new JButton[]{validerButton, viderButton, continuerButton}) {
            b.setPreferredSize(btnTaille);
            pannelFooterBas.add(b);
        }

        footer.add(pannelFooterHaut, BorderLayout.CENTER);
        footer.add(pannelFooterBas, BorderLayout.SOUTH);
        getContentPane().add(footer, BorderLayout.SOUTH);
        
        ///// Table /////
        generateTableIHM();
        mettreAJourTable();

        ///// Intéractivité /////
        recalculer.addActionListener(construireActionCalculerPanier());
        validerButton.addActionListener(construireActionValiderPanier());
        viderButton.addActionListener(construireActionConfirmationViderPanier());
        continuerButton.addActionListener(construireActionQuitter());
	}

	private ActionListener construireActionCalculerPanier() {
		return new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		mettreAJourTable();
        	}
        };
	}

	private ActionListener construireActionValiderPanier() {
		return new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				Utils.ouvrirDialog(DialogPanier.this, new DialogInformationClient(panier));
			}
		};
	}

	private ActionListener construireActionConfirmationViderPanier() {
		return new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		Utils.ouvrirDialog(DialogPanier.this, new DialogConfirmationViderPanier(DialogPanier.this, panier));
        		ihmListeTomate.mettreAJourBoutonPanier();
        	}
        };
	}

	private ActionListener construireActionQuitter() {
		return new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		ihmListeTomate.mettreAJourBoutonPanier();
        		dispose();
        	}
		};
	}

	private void generateTableIHM() {
		String[] colonnes = { "Image", "Produit", "Prix", "Quantité", "Total", "Tomate" };
        tableModel = new DefaultTableModel(new Object[][] {}, colonnes) {
			private static final long serialVersionUID = -5527912579057827458L;
			boolean[] columnEditables = new boolean[] {
        		false, false, false, true, false, false
        	};
        	public boolean isCellEditable(int row, int column) {
        		return columnEditables[column];
        	}
        };
        table = new JTable(tableModel);
        table.setRowHeight(60);
        
        // On cache la colonne de la Tomate
        table.getColumnModel().getColumn(5).setMinWidth(0);
        table.getColumnModel().getColumn(5).setMaxWidth(0);
        table.getColumnModel().getColumn(5).setWidth(0);
        table.getColumnModel().getColumn(5).setPreferredWidth(0);
        
        // Pour la colonne 0, on récupère sa valeur et on y met l'image associé au chemin
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 5881010984873511404L;
			@Override
        	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        		JLabel label = new JLabel();
        		label.setHorizontalAlignment(SwingConstants.CENTER);
                if (value != null) {
                    String path = (String) value;
                    ImageIcon icon = new ImageIcon(path);
                    label.setIcon(icon);
                }
                return label;
        	}
        });
        
        // Pour la colonne 3, on récupère la valeur et on y créer un spinner, qui a pour valeur le 1er élément de la liste et pour max le second
        table.getColumnModel().getColumn(3).setCellEditor(new SpinnerEditor(table, panier));
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
        	private static final long serialVersionUID = -6132426291986192439L;
        	@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                String texte = "";
                if (value instanceof int[]) {
                    texte = String.valueOf(((int[]) value)[0]);
                } else if (value instanceof Integer) {
                    texte = String.valueOf(value);
                }
                return super.getTableCellRendererComponent(table, texte, isSelected, hasFocus, row, column);
            }
        });
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	private void mettreAJourTable() {
		tableModel.setRowCount(0);
		for (Tomate t : this.panier.obtenirTomatesDansPannier()) {
        	int qte = panier.obtenirQuantitéTomate(t);
        	tableModel.addRow(new Object[] {
        			Utils.IMAGES_PATH + "Tomates40x40/" + t.getNomImage() + ".jpg",
        			t.getDésignation(),
        			String.format("%.2f €", t.getPrixTTC()),
        			new int[] {qte, t.getStock()},
        			String.format("%.2f €", qte * t.getPrixTTC()),
        			t
        	});
        }
		
		float sousTotal = panier.calculerSousTotal();
		sousTotalField.setText(String.format("%.2f €", sousTotal));
		totalField.setText(String.format("%.2f €", sousTotal + Panier.FORFAIT_EXPEDITION));
	}

    private JTextField créerField(String valeur) {
    	JTextField field = new JTextField();
    	field.setEditable(false);
    	field.setText(valeur);
    	field.setHorizontalAlignment(JTextField.RIGHT);
    	field.setBackground(new Color(255, 255, 224));
    	return field;
    }
}
