package ihm;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;

import modèle.Panier;
import modèle.Tomate;


public class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
	private static final long serialVersionUID = -7138785768555474629L;

	private Panier panier;
	
	private final JSpinner spinner = new JSpinner();
	private JTable table;
	
	public SpinnerEditor(JTable table, Panier panier) {
		this.table = table;
		this.panier = panier;
	}

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        int current = 0;
        int max = 0;
        if (value instanceof int[]) {
            int[] val = (int[]) value;
            current = val[0];
            max = val[1];
        }
        spinner.setModel(new SpinnerNumberModel(current, 0, max, 1));
        return spinner;
    }

    @Override
    public Object getCellEditorValue() {
    	SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
        return new int[] { (int) spinner.getValue(), (int) model.getMaximum() };
    }
    
    @Override
    public boolean stopCellEditing() {
    	int[] val = (int[]) getCellEditorValue();
        int nouvelleQuantite = val[0];
        int row = table.getEditingRow();
        
        if (row >= 0) {
        	Tomate t = (Tomate) table.getValueAt(row, 5);
        	panier.définirQuantité(t, nouvelleQuantite);
        }
		return super.stopCellEditing();
    }
}
