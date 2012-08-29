package net.miginfocom.examples;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

public class Example01
{
  private static JPanel createPanel()
  {
    JPanel localJPanel = new JPanel(new MigLayout());
    localJPanel.add(new JLabel("First Name"));
    localJPanel.add(new JTextField(10));
    localJPanel.add(new JLabel("Surname"), "gap unrelated");
    localJPanel.add(new JTextField(10), "wrap");
    localJPanel.add(new JLabel("Address"));
    localJPanel.add(new JTextField(), "span, grow");
    return localJPanel;
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
        JFrame localJFrame = new JFrame("Example 01");
        localJFrame.getContentPane().add(Example01.access$000());
        localJFrame.pack();
        localJFrame.setDefaultCloseOperation(3);
        localJFrame.setVisible(true);
      }
    });
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.examples.Example01
 * JD-Core Version:    0.6.0
 */