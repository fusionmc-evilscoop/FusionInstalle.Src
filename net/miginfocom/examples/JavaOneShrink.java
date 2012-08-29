package net.miginfocom.examples;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import net.miginfocom.swing.MigLayout;

public class JavaOneShrink
{
  private static JComponent createPanel(String[] paramArrayOfString)
  {
    JPanel localJPanel = new JPanel(new MigLayout("nogrid"));
    localJPanel.add(createTextArea(paramArrayOfString[0].replace(", ", "\n    ")), paramArrayOfString[0] + ", w 200");
    localJPanel.add(createTextArea(paramArrayOfString[1].replace(", ", "\n    ")), paramArrayOfString[1] + ", w 200");
    localJPanel.add(createTextArea(paramArrayOfString[2].replace(", ", "\n    ")), paramArrayOfString[2] + ", w 200");
    localJPanel.add(createTextArea(paramArrayOfString[3].replace(", ", "\n    ")), paramArrayOfString[3] + ", w 200");
    JSplitPane localJSplitPane = new JSplitPane(1, true, localJPanel, new JPanel());
    localJSplitPane.setOpaque(true);
    localJSplitPane.setBorder(null);
    return localJSplitPane;
  }

  private static JComponent createTextArea(String paramString)
  {
    JTextArea localJTextArea = new JTextArea("\n\n    " + paramString, 6, 20);
    localJTextArea.setBorder(new LineBorder(new Color(200, 200, 200)));
    localJTextArea.setFont(new Font("Helvetica", 1, 20));
    localJTextArea.setMinimumSize(new Dimension(20, 20));
    localJTextArea.setFocusable(false);
    return localJTextArea;
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
        JFrame localJFrame = new JFrame("JavaOne Shrink Demo");
        Container localContainer = localJFrame.getContentPane();
        localContainer.setLayout(new MigLayout("wrap 1"));
        localContainer.add(JavaOneShrink.access$000(new String[] { "", "", "", "" }));
        localContainer.add(JavaOneShrink.access$000(new String[] { "shrinkprio 1", "shrinkprio 1", "shrinkprio 2", "shrinkprio 3" }));
        localContainer.add(JavaOneShrink.access$000(new String[] { "shrink 25", "shrink 50", "shrink 75", "shrink 100" }));
        localContainer.add(JavaOneShrink.access$000(new String[] { "shrinkprio 1, shrink 50", "shrinkprio 1, shrink 100", "shrinkprio 2, shrink 50", "shrinkprio 2, shrink 100" }));
        localJFrame.pack();
        localJFrame.setDefaultCloseOperation(3);
        localJFrame.setLocationRelativeTo(null);
        localJFrame.setVisible(true);
      }
    });
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.examples.JavaOneShrink
 * JD-Core Version:    0.6.0
 */