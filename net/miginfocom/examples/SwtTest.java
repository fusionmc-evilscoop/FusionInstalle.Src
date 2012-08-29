package net.miginfocom.examples;

import net.miginfocom.swt.MigLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class SwtTest
{
  public static void main(String[] paramArrayOfString)
  {
    Display localDisplay = new Display();
    Shell localShell = new Shell();
    Composite localComposite = new Composite(localShell, 536870912);
    localShell.setLayout(new FillLayout());
    MigLayout localMigLayout = new MigLayout("debug,wrap 2");
    localComposite.setLayout(localMigLayout);
    Label localLabel1 = new Label(localComposite, 64);
    localLabel1.setText("This is an even longer label that just goes on and on...");
    localLabel1.setLayoutData("wmin 50");
    Label localLabel2 = new Label(localComposite, 0);
    localLabel2.setText("Label 2");
    localLabel2 = new Label(localComposite, 0);
    localLabel2.setText("Label 3");
    localLabel2 = new Label(localComposite, 0);
    localLabel2.setText("Label 4");
    localShell.setSize(300, 300);
    localShell.open();
    localShell.layout();
    while (!localShell.isDisposed())
    {
      if (localDisplay.readAndDispatch())
        continue;
      localDisplay.sleep();
    }
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.examples.SwtTest
 * JD-Core Version:    0.6.0
 */