package net.miginfocom.examples;

import java.io.PrintStream;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.ConstraintParser;
import net.miginfocom.layout.IDEUtil;
import net.miginfocom.layout.LayoutUtil;

public class TestPrefCC
{
  public static void main(String[] paramArrayOfString)
  {
    LayoutUtil.setDesignTime(null, true);
    String str1 = "height pref!";
    System.out.println("initial: " + str1);
    CC localCC = ConstraintParser.parseComponentConstraint(str1);
    String str2 = IDEUtil.getConstraintString(localCC, false);
    System.out.println("constraintString: " + str2);
    System.out.println("good: " + str1.equals(str2));
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     net.miginfocom.examples.TestPrefCC
 * JD-Core Version:    0.6.0
 */