
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;


public class UpdateVenue extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private HashMap<String,JTextField> fields = new HashMap<String,JTextField>();

	public UpdateVenue() {
		int ypos = 0;
		
		setTitle("Change venue");
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
		
		
		addLabelAndTextField("Match ID:",ypos++,mainPanel);
		addLabelAndTextField("Start date:",ypos++,mainPanel);
		addLabelAndTextField("Expected duration:",ypos++,mainPanel);
		addLabelAndTextField("Room number:",ypos++,mainPanel);
		addLabelAndTextField("Building:",ypos++,mainPanel);
		JButton rndButton = new JButton("RND");
		rndButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String buildingname = DatabaseConnection.getRandomBuilding();
				if(buildingname != null)
					fields.get("Building:").setText(buildingname);
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
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = fields.get("Start date:").getText();
		
		java.sql.Date date = null;
		
		try {
			java.util.Date udate = format.parse(startDate);
			date = new java.sql.Date(udate.getTime());
		}catch(ParseException pe) {
		      JOptionPane.showMessageDialog(this, "Error parsing the date, please enter date in the format: "+format.format(new java.util.Date()), "Parsing error", JOptionPane.WARNING_MESSAGE );
		      return;
		}

		
		int matchID;
		try {
			matchID = Integer.parseInt(fields.get("Match ID:").getText());
			if(matchID < 1) {
				throw new NumberFormatException();
			}
		}catch(NumberFormatException pe) {
		      JOptionPane.showMessageDialog(this, "Error parsing the match ID, please enter a valid number","Input format error", JOptionPane.WARNING_MESSAGE );
		      return;
		}
		
		int expDuration;
		try {
			expDuration = Integer.parseInt(fields.get("Expected duration:").getText());
			if(expDuration < 1) {
				throw new NumberFormatException();
			}
		}catch(NumberFormatException pe) {
		      JOptionPane.showMessageDialog(this, "Error parsing the expected duration, please enter a valid number","Input format error", JOptionPane.WARNING_MESSAGE );
		      return;
		}
		
		
		int roomnb;
		try {
			roomnb = Integer.parseInt(fields.get("Room number:").getText());
			if(roomnb < 1) {
				throw new NumberFormatException();
			}
		}catch(NumberFormatException pe) {
		      JOptionPane.showMessageDialog(this, "Error parsing the room number, please enter a valid number","Input format error", JOptionPane.WARNING_MESSAGE );
		      return;
		}
		
		
		//Execute the SQL
		
		String building = fields.get("Building:").getText();
		
		System.out.println("Submitting query to database . . .");

		this.dispose();
		DatabaseConnection.updateVenue(matchID,date,expDuration,roomnb,building);
		
		
	}
	
	
	

}
