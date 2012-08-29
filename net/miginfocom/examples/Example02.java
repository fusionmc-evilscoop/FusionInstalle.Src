package net.miginfocom.examples;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import net.miginfocom.swing.MigLayout;

public class Example02
{
  private static JPanel createPanel()
  {
    JPanel localJPanel = new JPanel(new MigLayout());
    localJPanel.add(createLabel("West Panel"), "dock west");
    localJPanel.add(createLabel("North 1 Panel"), "dock north");
    localJPanel.add(createLabel("North 2 Panel"), "dock north");
    localJPanel.add(createLabel("South Panel"), "dock south");
    localJPanel.add(createLabel("East Panel"), "dock east");
    localJPanel.add(createLabel("Center Panel"), "grow, push");
    return localJPanel;
  }

  private static JLabel createLabel(String paramString)
  {
    JLabel localJLabel = new JLabel(paramString);
    localJLabel.setHorizontalAlignment(0);
    localJLabel.setBorder(new CompoundBorder(new EtchedBorder(), new EmptyBorder(5, 10, 5, 10)));
    return localJLabel;
  }

  public static void main(String[] paramArrayOfString)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        JFrame localJFrame = new JFrame("Example 02");
        localJFrame.getContentPane().add(Example02.access$000());
        localJFrame.pack();
        localJFrame.setDefaultCloseOperation(3);
        localJFrame.setVisible(true);
      }
    });
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.examples.Example02
 * JD-Core Version:    0.6.0
 */