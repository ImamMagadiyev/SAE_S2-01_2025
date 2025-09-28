package ihm;

import javax.swing.*;
import javax.swing.border.*;

import modèle.Tomates;

import java.awt.*;

public class DialogConseilsCulture extends JDialog {
	private static final long serialVersionUID = -6853797332733460593L;

	public DialogConseilsCulture() {
		setTitle("Ô'Tomates - Conseils de culture");
		setSize(600, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel contentPane = new JPanel(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);

		// Titre principal
		JLabel titre = new JLabel("Conseils de culture", SwingConstants.CENTER);
		titre.setFont(new Font("Serif", Font.BOLD, 26));
		titre.setForeground(new Color(0, 128, 0));
		contentPane.add(titre, BorderLayout.NORTH);

		// Panel central
		JPanel panelCentre = new JPanel();
		panelCentre.setLayout(new BoxLayout(panelCentre, BoxLayout.Y_AXIS));
		panelCentre.setBackground(Color.WHITE);
		contentPane.add(panelCentre, BorderLayout.CENTER);

		// Bloc vert clair contenant les infos clés
		JPanel blocInfos = new JPanel();
		blocInfos.setLayout(new BoxLayout(blocInfos, BoxLayout.Y_AXIS));
		blocInfos.setBackground(new Color(235, 255, 235));
		blocInfos.setBorder(BorderFactory.createCompoundBorder(
			new LineBorder(new Color(0, 128, 0), 2),
			new EmptyBorder(10, 20, 10, 20)
		));

		JTextArea textAreaTitre = new JTextArea(Tomates.CONSEILS_DE_CULTURE_TITRE);
		textAreaTitre.setFont(new Font("SansSerif", Font.BOLD, 14));
		textAreaTitre.setLineWrap(true);
		textAreaTitre.setWrapStyleWord(true);
		textAreaTitre.setEditable(false);
		textAreaTitre.setForeground(new Color(0, 128, 0));
		textAreaTitre.setBackground(blocInfos.getBackground());
		blocInfos.add(textAreaTitre);

		panelCentre.add(blocInfos);
		panelCentre.add(Box.createVerticalStrut(15));

		// Texte principal avec conseils
		JTextArea textAreaConseils = new JTextArea(Tomates.CONSEILS_DE_CULTURE);
		textAreaConseils.setFont(new Font("SansSerif", Font.PLAIN, 13));
		textAreaConseils.setLineWrap(true);
		textAreaConseils.setWrapStyleWord(true);
		textAreaConseils.setEditable(false);
		textAreaConseils.setMargin(new Insets(10, 10, 10, 10));

		JScrollPane scrollPane = new JScrollPane(textAreaConseils);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		panelCentre.add(scrollPane);

		// Bouton OK
		JButton btnFermer = new JButton("Fermer");
		btnFermer.setPreferredSize(new Dimension(100, 30));
		btnFermer.addActionListener(e -> dispose());

		JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelBas.setBackground(Color.WHITE);
		panelBas.add(btnFermer);

		contentPane.add(panelBas, BorderLayout.SOUTH);
	}
}
