import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.math.BigDecimal;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.StrokeBorder;

public class Calculator extends JFrame implements KeyListener, MouseListener, ActionListener, WindowFocusListener
{
	private static final String CALCULATOR_TITLE = "Java Calculator by Daniel Atherton";
	private static final Rectangle CALCULATOR_BOUNDS = new Rectangle(500, 100, 406, 552);
	private static final Image CALCULATOR_ICON = new ImageIcon(Calculator.class.getResource("calculator.png")).getImage();

	private static final JMenuBar MENU_BAR = new JMenuBar();
	private static final JMenu FILE_MENU = new JMenu("File");
	private static final JMenu EDIT_MENU = new JMenu("Edit");
	private static final JMenuItem CLOSE_MENU_ITEM = new JMenuItem("Close");
	private static final JMenuItem COLOR_MENU_ITEM = new JMenuItem("Color");

	private static final JTextField DISPLAY = new JTextField();
	private static final Dimension DISPLAY_DIMENSIONS = new Dimension(400, 100);
	private static final String DISPLAY_DEFAULT_TEXT = "0  ";
	private static final Font FONT = new Font("Tahoma", Font.BOLD, 24);
	private static final StrokeBorder BORDER = new StrokeBorder(new BasicStroke());
	private static final Cursor DISPLAY_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);

	private static final Container CONTAINER = new Container();
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 100;

	private String operandOne = "";
	private String operandTwo = "";
	private String operator = "";

	private ColorPicker colorPicker;
	private boolean recolorEnabled;

	public static void main(String[] args)
	{
		new Calculator();
	}

	private Calculator()
	{
		setTitle(CALCULATOR_TITLE);
		setBounds(CALCULATOR_BOUNDS);
		setIconImage(CALCULATOR_ICON);
		setVisible(true);
		setResizable(false);

		createMenuBar();
		createDisplay();

		for (int i = 0; i < 16; i++)
		{
			createButton(i);
		}

		setContentPane(CONTAINER);
		revalidate();

		addKeyListener(this);
		addWindowFocusListener(this);
	}

	private void createMenuBar()
	{
		MENU_BAR.add(FILE_MENU);
		FILE_MENU.add(CLOSE_MENU_ITEM);

		MENU_BAR.add(EDIT_MENU);
		EDIT_MENU.add(COLOR_MENU_ITEM);

		setJMenuBar(MENU_BAR);

		CLOSE_MENU_ITEM.addActionListener(this);
		COLOR_MENU_ITEM.addActionListener(this);
	}

	private void createDisplay()
	{
		DISPLAY.setSize(DISPLAY_DIMENSIONS);
		DISPLAY.setText(DISPLAY_DEFAULT_TEXT);
		DISPLAY.setHorizontalAlignment(SwingConstants.RIGHT);
		DISPLAY.setFont(FONT);
		DISPLAY.setBorder(BORDER);
		DISPLAY.setCursor(DISPLAY_CURSOR);
		DISPLAY.setFocusable(false);

		CONTAINER.add(DISPLAY);
	}

	private void createButton(int number)
	{
		String text = "";

		JButton button = new JButton();
		button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		button.setFont(FONT);
		button.setBorder(BORDER);

		if (number <= 9)
		{
			text = String.valueOf(number);
			button.setToolTipText(text);

			switch (number)
			{
				case 1:
				case 2:
				case 3:
					button.setLocation(BUTTON_WIDTH * (number - 1), BUTTON_HEIGHT);
					break;
				case 4:
				case 5:
				case 6:
					button.setLocation(BUTTON_WIDTH * (number - 4), BUTTON_HEIGHT * 2);
					break;
				case 7:
				case 8:
				case 9:
					button.setLocation(BUTTON_WIDTH * (number - 7), BUTTON_HEIGHT * 3);
					break;
				case 0:
					button.setLocation(0, BUTTON_HEIGHT * 4);
					break;
			}
		}
		else
		{
			switch (number)
			{
				case 10:
					text = "c";
					button.setToolTipText("Clear");
					button.setLocation(BUTTON_WIDTH, BUTTON_HEIGHT * 4);
					break;
				case 11:
					text = "=";
					button.setToolTipText("Equals");
					button.setLocation(BUTTON_WIDTH * 2, BUTTON_HEIGHT * 4);
					break;
				case 12:
					text = "+";
					button.setToolTipText("Add");
					button.setLocation(BUTTON_WIDTH * 3, BUTTON_HEIGHT);
					break;
				case 13:
					text = "-";
					button.setToolTipText("Subtract");
					button.setLocation(BUTTON_WIDTH * 3, BUTTON_HEIGHT * 2);
					break;
				case 14:
					text = "*";
					button.setToolTipText("Multiply");
					button.setLocation(BUTTON_WIDTH * 3, BUTTON_HEIGHT * 3);
					break;
				case 15:
					text = "/";
					button.setToolTipText("Divide");
					button.setLocation(BUTTON_WIDTH * 3, BUTTON_HEIGHT * 4);
					break;
			}
		}

		button.setText(text);

		CONTAINER.add(button);

		button.addKeyListener(this);
		button.addMouseListener(this);
	}

	private void handleButtonSelected(String buttonText)
	{
		BigDecimal one, two;

		switch (buttonText)
		{
			case "0":
			case "1":
			case "2":
			case "3":
			case "4":
			case "5":
			case "6":
			case "7":
			case "8":
			case "9":
				// Allow user to start from scratch after hitting '=' on the previous calculation
				if (operator.equals("=") || operator.equals("\n"))
				{
					operandOne = "";
					operandOne = operandOne.concat(buttonText.concat("  "));
					operator = "";
				}
				// Replace default text with the number the user clicked on or typed in
				else if (operandOne.equals(DISPLAY_DEFAULT_TEXT))
				{
					operandOne = operandOne.replaceAll("0", "").replaceAll(" ", "").concat(buttonText.concat("  "));
				}
				// Add the number clicked on or typed in to the existing numbers displayed
				else
				{
					operandOne = operandOne.replaceAll(" ", "").concat(buttonText.concat("  "));
				}
				break;
			case "c":
				// Clear display and operator
				operandOne = DISPLAY_DEFAULT_TEXT;
				operator = "";
				break;
			case "=":
			case "\n":
				// Make sure we have two operands
				if (operandOne.equals("") || operandTwo.equals(""))
				{
					// If user hits '=' before typing any operands, display the default text
					if (operandOne.isEmpty())
					{
						operandOne = DISPLAY_DEFAULT_TEXT;
					}
					break;
				}

				// If the result produced an Arithmetic Exception, replace operandTwo with 0
				if (operandTwo.equals("Undefined"))
				{
					operandTwo = "0";
				}

				// Remove padding from operands, parse operand Strings to get their values into BigDecimals, and clear the Strings
				operandOne = operandOne.replaceAll(" ", "");
				operandTwo = operandTwo.replaceAll(" ", "");
				one = BigDecimal.valueOf(Double.valueOf(operandOne));
				two = BigDecimal.valueOf(Double.valueOf(operandTwo));
				operandOne = "";
				operandTwo = "";

				try
				{
					// Handle the actual calculation using BigDecimal for higher precision
					switch (operator)
					{
						case "+":
							operandOne = String.valueOf(two.add(one).stripTrailingZeros().toPlainString()).concat("  ");
							break;
						case "-":
							operandOne = String.valueOf(two.subtract(one).stripTrailingZeros().toPlainString()).concat("  ");
							break;
						case "*":
							operandOne = String.valueOf(two.multiply(one).stripTrailingZeros().toPlainString()).concat("  ");
							break;
						case "/":
							operandOne = String.valueOf(two.divide(one, 10, BigDecimal.ROUND_FLOOR).stripTrailingZeros().toPlainString()).concat("  ");
							break;
						case " ":
							operandOne = DISPLAY_DEFAULT_TEXT;
							break;
					}
				}
				catch (ArithmeticException e)
				{
					System.out.println("Arithmetic Error, caused by " + e.getMessage());
					operandOne = operandTwo.concat("Undefined  ");
				}
				operator = buttonText;
				break;
			// Place the number entered into operandTwo, display the default text, and set the operator to the one selected
			case "+":
				operandTwo = operandOne.replaceAll(" ", "");
				operandOne = DISPLAY_DEFAULT_TEXT;
				operator = buttonText;
				break;
			case "-":
				operandTwo = operandOne.replaceAll(" ", "");
				operandOne = DISPLAY_DEFAULT_TEXT;
				operator = buttonText;
				break;
			case "*":
				operandTwo = operandOne.replaceAll(" ", "");
				operandOne = DISPLAY_DEFAULT_TEXT;
				operator = buttonText;
				break;
			case "/":
				operandTwo = operandOne.replaceAll(" ", "");
				operandOne = DISPLAY_DEFAULT_TEXT;
				operator = buttonText;
				break;
		}

		// The number(s) the user selected are stored in operandOne, display the number selected/result
		DISPLAY.setText(operandOne);
		DISPLAY.setHorizontalAlignment(SwingConstants.RIGHT);

		revalidate();
	}

	private void recolorComponents()
	{
		// Recolor is enabled when the Edit > Color Menu Item is selected, and disabled after Recoloring Components
		if (recolorEnabled)
		{
			Color color = colorPicker.getColor();

			Container container = getRootPane().getContentPane();
			for (Component component : container.getComponents())
			{
				if (component instanceof JButton)
				{
					// Change button background to the user selected color
					component.setBackground(color);
				}
				if (component instanceof JTextField)
				{
					// Change display foreground color to the user selected color
					component.setForeground(color.darker());
				}
			}

			revalidate();
		}

		recolorEnabled = false;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyChar())
		{
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case 'c':
			case '=':
			case '\n':
			case '+':
			case '-':
			case '*':
			case '/':
				handleButtonSelected(String.valueOf(e.getKeyChar()));
				break;
		}

		e.consume();
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// Check if the display text is nearing the edge of the display
		if (DISPLAY.getText().length() > 22)
		{
			DISPLAY.setText("Overflow  ");
		}

		e.consume();
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.getComponent() instanceof JButton)
		{
			if (e.getButton() == MouseEvent.BUTTON1)
			{
				JButton button = (JButton) e.getComponent();
				handleButtonSelected(button.getText());

				// Check if the display text is nearing the edge of the display
				if (DISPLAY.getText().length() > 22)
				{
					DISPLAY.setText("Overflow  ");
				}
			}
		}

		e.consume();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("Close"))
		{
			dispose();
		}
		if (e.getActionCommand().equals("Color"))
		{
			colorPicker = new ColorPicker();
			recolorEnabled = true;
		}
	}

	@Override
	public void windowGainedFocus(WindowEvent e)
	{
		// If Calculator Window Gained Focus AFTER the user selected Edit > Color and hit Select on a color, update Component Colors
		if (e.getComponent() instanceof Calculator && colorPicker != null && colorPicker.getColor() != null)
		{
			recolorComponents();
			colorPicker = null;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	@Override
	public void windowLostFocus(WindowEvent e)
	{

	}
}
