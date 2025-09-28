package modèle;

import java.awt.Window;

import javax.swing.JDialog;

public final class Utils {
	public static final String IMAGES_PATH = "src/main/resources/images/";
	public static final String ICONS_PATH = IMAGES_PATH + "icons/";
	
	public static enum DatabasePaths {
		Original("src/main/resources/data/tomates.json"),
		Prod("src/main/resources/data/tomatesSauvegarde.json"),
		Test("src/main/resources/data/tomatesTest.json");
		
		private final String path;
		private DatabasePaths(String path) {
			this.path = path;
		}
		public String getPath() {
			return path;
		}
	}
	
	public static void ouvrirDialog(Window parent, JDialog instance) {
		instance.setLocationRelativeTo(parent);
		instance.setModal(true);
		instance.setVisible(true);
		
	}
}
