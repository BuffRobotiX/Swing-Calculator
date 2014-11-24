// 
// Name: Buff, David
// Project: #2
// Due: 10/30/2014
// Course: CS-245-01-w14 
// 
// Description: 
// 		A simple calculator using a grid layout and swing.
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Calculator implements ActionListener {
	JLabel display;
	int digits = 1;
	long first;
	long second;
	long result;
	String op = "";
	boolean opped = false; //if we are on the second operand, ie. an operator has been pressed
	boolean error = false;

	Calculator() {
		ImageIcon icon = new ImageIcon("Calculator.png");
		JFrame frame = new JFrame("Calculator");

		frame.setSize(188,263); //A width/height ratio of 5/7 seems to look good.
		frame.setLocationRelativeTo(null);
		frame.setIconImage(icon.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(2,1));

		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(1,1));
		displayPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		display = new JLabel("0", SwingConstants.RIGHT);

		displayPanel.add(display);
		frame.add(displayPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4,4));

		String buttons[] = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", "C", "=", "+"};
		JButton button;
		for (int i = 0; i < buttons.length; i++)
		{
			button = new JButton(buttons[i]);
			button.addActionListener(this);
			buttonPanel.add(button);
			if (buttons[i] == "C")
			{
				button.setMnemonic('C');
			}
			else if (buttons[i] == "=")
			{
				frame.getRootPane().setDefaultButton(button);
			}
		}

		frame.add(buttonPanel);

		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		char key = ae.getActionCommand().charAt(0);
		switch (key) {
			case'C' :
				if ( (ae.getModifiers() & KeyEvent.CTRL_MASK) != 0)
				{
					display.setText("(c) 2014 David Buff");
					error = true;
				}
				else
				{
					first = 0;
					second = 0;
					digits = 1;
					display.setText("0");
					error = false;
				}
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '0':
				if (!error)
				{
					if (Long.parseLong(display.getText()) == 0 || opped)
					{
						display.setText(key + "");
						opped = false;
					}
					else if (digits < 10)
					{
						display.setText(display.getText() + key);
						digits++;
					}
				}
				break;
			case '/':
			case '*':
			case '-':
			case '+':
				if (!error)
				{
					first = Long.parseLong(display.getText());
					op = key + "";
					digits = 1;
					opped = true;
				}
				break;
			case '=':
				if (!error && op != "")
				{
					second = Long.parseLong(display.getText());
					switch (op.charAt(0)) {
						case '/':
							if (second == 0)
							{
								display.setText("Div by 0");
								error = true;
							}
							else
								result = first / second;
							break;
						case '*':
							result = first * second;
							break;
						case '-':
							result = first - second;
							break;
						case '+':
							result = first + second;
							break;
					}
					if (!error)
					{
						if (Math.abs(result) <= 9999999999l)
						{
							display.setText(Long.toString(result));
							first = result;
							op = "";
							digits = 1;
							opped = true;
						}
						else
						{
							display.setText("Overflow");
							error = true;
						}
					}
				}
				break;
		}
	}

	public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calculator();
            }
        });
    }
}