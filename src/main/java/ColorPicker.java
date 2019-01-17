import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class ColorPicker extends JFrame implements MouseListener
{
	private static final String COLOR_PICKER_TITLE = "Color Picker";
	private static final Rectangle COLOR_PICKER_BOUNDS = new Rectangle(390, 120, 618, 233);
	private static final Image COLOR_PICKER_ICON = new ImageIcon(ColorPicker.class.getResource("colorpicker.png")).getImage();

	private static final JPanel colorPanel = new JPanel();
	private static final Rectangle COLOR_PANEL_BOUNDS = new Rectangle(5, 0, 600, 240);

	private static final JButton select = new JButton("Select");
	private static final Rectangle SELECT_BUTTON_BOUNDS = new Rectangle(330, 163, 80, 30);

	private static final JButton cancel = new JButton("Cancel");
	private static final Rectangle CANCEL_BUTTON_BOUNDS = new Rectangle(415, 163, 80, 30);

	private static final JColorChooser colorChooser = new JColorChooser();
	private Color color;

	ColorPicker()
	{
		setTitle(COLOR_PICKER_TITLE);
		setBounds(COLOR_PICKER_BOUNDS);
		setIconImage(COLOR_PICKER_ICON);
		setVisible(true);
		setResizable(false);

		select.setBounds(SELECT_BUTTON_BOUNDS);
		cancel.setBounds(CANCEL_BUTTON_BOUNDS);
		colorPanel.setBounds(COLOR_PANEL_BOUNDS);
		colorPanel.add(colorChooser.getChooserPanels()[1]);

		getLayeredPane().add(colorPanel, JLayeredPane.DEFAULT_LAYER);
		getLayeredPane().add(select, JLayeredPane.PALETTE_LAYER);
		getLayeredPane().add(cancel, JLayeredPane.PALETTE_LAYER);

		revalidate();

		select.addMouseListener(this);
		cancel.addMouseListener(this);
		colorChooser.addMouseListener(this);
	}

	Color getColor()
	{
		// Transparency breaks JButtons, remove transparency from the color before returning it
		if (color != null && color.getTransparency() > 0)
		{
			return new Color(color.getRed(), color.getGreen(), color.getBlue());
		}

		return color;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getComponent() instanceof JButton)
		{
			if (((JButton) e.getComponent()).getText().equals("Select"))
			{
				color = colorChooser.getColor();
				dispose();
			}
			if (((JButton) e.getComponent()).getText().equals("Cancel"))
			{
				dispose();
			}
		}

		e.consume();
	}

	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
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
}
