package net.miginfocom.examples;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

public class BugTestApp
{
  private static JPanel createPanel()
  {
    JPanel localJPanel = new JPanel();
    localJPanel.setBackground(new Color(200, 255, 200));
    localJPanel.setLayout(new MigLayout("debug"));
    JLabel localJLabel = new JLabel("Qwerty");
    localJLabel.setFont(localJLabel.getFont().deriveFont(30.0F));
    localJPanel.add(localJLabel, "pos 0%+5 0%+5 50%-5  50%-5");
    localJPanel.add(new JTextField("Qwerty"));
    JFrame localJFrame = new JFrame();
    localJFrame.setDefaultCloseOperation(3);
    localJFrame.setLayout(new MigLayout());
    localJFrame.add(localJPanel, "w 400, h 100");
    localJFrame.setLocationRelativeTo(null);
    localJFrame.pack();
    localJFrame.setVisible(true);
    return null;
  }

  private static JPanel createPanel2()
  {
    JFrame localJFrame = new JFrame();
    localJFrame.setLayout(new MigLayout("debug, fillx", "", ""));
    localJFrame.add(new JTextField(), "span 2, grow, wrap");
    localJFrame.add(new JTextField(10));
    localJFrame.add(new JLabel("End"));
    localJFrame.setSize(600, 400);
    localJFrame.setDefaultCloseOperation(3);
    localJFrame.setVisible(true);
    return null;
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
        BugTestApp.access$000();
      }
    });
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.examples.BugTestApp
 * JD-Core Version:    0.6.0
 */