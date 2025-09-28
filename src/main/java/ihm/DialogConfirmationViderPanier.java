package ihm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modèle.Panier;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogConfirmationViderPanier extends JDialog {
	private static final long serialVersionUID = 3355410350772809393L;

	private Panier panier;
	private DialogPanier ihmParent;

	private final JPanel contentPanel = new JPanel();

	public DialogConfirmationViderPanier(DialogPanier ihmParent, Panier panier) {		
		this.panier = panier;
		this.ihmParent = ihmParent;
		
		setBounds(100, 100, 354, 170);
		setTitle("Ô'Tomate - Confirmation");
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lbl = new JLabel("Êtes-vous sûr de vouloir vider le panier ?");
			lbl.setHorizontalAlignment(SwingConstants.CENTER);
			lbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
			contentPanel.add(lbl);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnValider = new JButton("Valider");
				btnValider.addActionListener(construireViderPanier());
				btnValider.setActionCommand("OK");
				buttonPane.add(btnValider);
				getRootPane().setDefaultButton(btnValider);
			}
			{
				JButton btnAnnuler = new JButton("Annuler");
				btnAnnuler.addActionListener(construireQuitterDialog());
				btnAnnuler.setActionCommand("Cancel");
				buttonPane.add(btnAnnuler);
			}
		}
	}

	private ActionListener construireViderPanier() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panier.viderPanier();
				ihmParent.dispose();
				dispose();
			}
		};
	}
	
	private ActionListener construireQuitterDialog() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
	}

}