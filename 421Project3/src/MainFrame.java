
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainFrame extends JFrame {
	//static Dimension space = new Dimension(0,5);
	//static Dimension bsize = new Dimension(300,100);

	private static final long serialVersionUID = 1L;

	public MainFrame() {
		setTitle("Main Window");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,500);
		//setLocation(10,200);
		getContentPane().setLayout(
		       new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		
		
		getContentPane().add(Box.createVerticalGlue());
		JButton alt1 = new JButton("Register Player");
		alt1.setAlignmentX(Component.CENTER_ALIGNMENT);
		alt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alternative1();				
			}
		});
		getContentPane().add(alt1);

		getContentPane().add(Box.createVerticalGlue());
		JButton alt2 = new JButton("Announce match winner");
		alt2.setAlignmentX(Component.CENTER_ALIGNMENT);
		alt2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alternative2();				
			}
		});
		getContentPane().add(alt2);
		
		getContentPane().add(Box.createVerticalGlue());
		JButton alt3 = new JButton("Get tournament players");
		alt3.setAlignmentX(Component.CENTER_ALIGNMENT);
		alt3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alternative3();				
			}
		});
		getContentPane().add(alt3);
		
		getContentPane().add(Box.createVerticalGlue());
		JButton alt4 = new JButton("Update match venue");
		alt4.setAlignmentX(Component.CENTER_ALIGNMENT);
		alt4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alternative4();				
			}
		});
		getContentPane().add(alt4);
		
		getContentPane().add(Box.createVerticalGlue());
		JButton alt5 = new JButton("Alternative 5");
		alt5.setAlignmentX(Component.CENTER_ALIGNMENT);
		alt5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alternative5();				
			}
		});
		getContentPane().add(alt5);
		
		getContentPane().add(Box.createVerticalGlue());

	}
	
	/**
	 * REGISTER:
	 * 
	 */
	private void alternative1() {
		this.setEnabled(false);
		RegistrationForm form = new RegistrationForm();
		form.setVisible(true);
	}
	
	/**
	 * ANNONCE MATCH WINNER
	 * 
	 * @Description: Set match.winner, update the team's score and update all the players score
	 */
	private void alternative2() {
		this.setEnabled(false);
		AnnounceMatchWinner form = new AnnounceMatchWinner();
		form.setVisible(true);
	}
	
	/**
	 * LIST TOURNAMENT PLAYERS
	 * 
	 * @Description: List all players that plays in a team that has a match in the given tournament
	 */
	private void alternative3() {
		this.setEnabled(false);
		GetTournamentPlayer form = new GetTournamentPlayer();
		form.setVisible(true);
	}
	
	/**
	 * CHANGE MATCH VENUE
	 * 
	 * @Description: Change the start time of a match and the location relation
	 */
	private void alternative4() {
		this.setEnabled(false);
		UpdateVenue form = new UpdateVenue();
		form.setVisible(true);
	}
	
	/**
	 * DISQUALIFY TEAM
	 * @Description: Remove team from match and replace with another team that is not taking part in the match and is playing the same game
	 */
	private void alternative5() {
		this.setEnabled(false);
		DisqualifyTeam form = new DisqualifyTeam();
		form.setVisible(true);
	}
	
	
}