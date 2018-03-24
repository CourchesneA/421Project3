
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;


public class DisqualifyTeam extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private HashMap<String,JTextField> fields = new HashMap<String,JTextField>();

	public DisqualifyTeam() {
		int ypos = 0;
		
		setTitle("Announce Winner");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        Application.mainframe.setEnabled(true);
		        dispose();
		    }
		});
		setSize(500,500);
		JPanel mainPanel = new JPanel();
		setContentPane(mainPanel);
		
		Border border = mainPanel.getBorder();
		Border margin = new EmptyBorder(10,10,10,10);
		mainPanel.setBorder(new CompoundBorder(border,margin));
		
		GridBagLayout panelGridBagLayout = new GridBagLayout();
		panelGridBagLayout.columnWidths = new int[] { 86, 86, 0 };
		panelGridBagLayout.rowHeights = new int[] { 20, 20, 20, 20, 20, 0 };
		panelGridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panelGridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		mainPanel.setLayout(panelGridBagLayout);
		
		
		addLabelAndTextField("Teamname:",ypos++,mainPanel);
		JButton rndButton = new JButton("RND");
		rndButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String teamname = DatabaseConnection.getRandomTeam();
				if(teamname != null)
					fields.get("Teamname:").setText(teamname);
			}
		});
		GridBagConstraints c2 = new GridBagConstraints();
		c2.insets = new Insets(0,10,5,5);
		c2.gridx=2;
		c2.gridy=ypos-1;
		add(rndButton,c2);
		
		JButton btn = new JButton("Submit");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonAction();
			}
		});
		GridBagConstraints btngbc = new GridBagConstraints();
		btngbc.anchor = GridBagConstraints.PAGE_END;
		btngbc.insets = new Insets(10,10,10,10);
		btngbc.gridy=ypos;
		add(btn,btngbc);
	}
	
	/**
	 * Helper method to create components in the gridbag layout (From SO)
	 * it also registers the component in the hashmap using the labeltext for futur uses
	 * @param labelText
	 * @param yPos
	 * @param containingPanel
	 */
	private void addLabelAndTextField(String labelText, int yPos, Container containingPanel) {

	    JLabel label = new JLabel(labelText);
	    GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
	    gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
	    gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);
	    gridBagConstraintForLabel.gridx = 0;
	    gridBagConstraintForLabel.gridy = yPos;
	    containingPanel.add(label, gridBagConstraintForLabel);

	    JTextField textField = new JTextField();
	    GridBagConstraints gridBagConstraintForTextField = new GridBagConstraints();
	    gridBagConstraintForTextField.fill = GridBagConstraints.BOTH;
	    gridBagConstraintForTextField.insets = new Insets(0, 0, 5, 0);
	    gridBagConstraintForTextField.gridx = 1;
	    gridBagConstraintForTextField.gridy = yPos;
	    containingPanel.add(textField, gridBagConstraintForTextField);
	    textField.setColumns(10);
	    
	    fields.put(labelText, textField);
	    
	}
	
	private void buttonAction() {

		//Execute the SQL
		String teamname = fields.get("Teamname:").getText();
		
		System.out.println("Submitting query to database . . .");

		this.dispose();
		DatabaseConnection.disqualifyTeam(teamname);
		
		
	}
}