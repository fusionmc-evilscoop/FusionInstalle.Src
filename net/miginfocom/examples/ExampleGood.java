package net.miginfocom.examples;

import net.miginfocom.swt.MigLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class ExampleGood
{
  protected void buildControls(Composite paramComposite)
  {
    paramComposite.setLayout(new MigLayout("inset 0", "[fill, grow]", "[fill, grow]"));
    Table localTable = new Table(paramComposite, 2816);
    localTable.setLayoutData("id table, hmin 100, wmin 300");
    localTable.setHeaderVisible(true);
    localTable.setLinesVisible(true);
    Label localLabel = new Label(paramComposite, 2048);
    localLabel.setText("Label Text");
    localLabel.moveAbove(null);
    localLabel.setLayoutData("pos 0 0");
    for (int i = 0; i < 10; i++)
    {
      TableItem localTableItem = new TableItem(localTable, 0);
      localTableItem.setText("item #" + i);
    }
  }

  public static void main(String[] paramArrayOfString)
  {
    Display localDisplay = new Display();
    Shell localShell = new Shell(localDisplay);
    new ExampleGood().buildControls(localShell);
    localShell.open();
    while (!localShell.isDisposed())
    {
      if (localDisplay.readAndDispatch())
        continue;
      localDisplay.sleep();
    }
    localDisplay.dispose();
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.examples.ExampleGood
 * JD-Core Version:    0.6.0
 */