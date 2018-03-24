import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class ListDisplayer extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panel = new JPanel();
	JList<String> list = new JList<String>();
	
	public ListDisplayer(JList<String> rlist) {
		int ypos = 0;
		list = rlist;
		
		setTitle("Result");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        Application.mainframe.setEnabled(true);
		        Application.mainframe.setVisible(true);
		        dispose();
		    }
		});
		setSize(500,500);
		JScrollPane jcp = new JScrollPane(list);
		panel.setLayout(new GridLayout(0,1));
		panel.add(jcp);
		JPanel mainPanel = new JPanel();
		setContentPane(mainPanel);
		
		Border border = mainPanel.getBorder();
		Border margin = new EmptyBorder(10,10,10,10);
		mainPanel.setBorder(new CompoundBorder(border,margin));
		
		/*GridBagLayout panelGridBagLayout = new GridBagLayout();
		panelGridBagLayout.columnWidths = new int[] { 86, 86, 0 };
		panelGridBagLayout.rowHeights = new int[] { 20, 20, 20, 20, 20, 0 };
		panelGridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panelGridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };*/
		GridLayout gl = new GridLayout(0,1);
		mainPanel.setLayout(gl);
		
		mainPanel.add(panel);
		
	}
	
}
