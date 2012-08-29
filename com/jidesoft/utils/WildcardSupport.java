package com.jidesoft.utils;

public abstract interface WildcardSupport
{
  public abstract char getZeroOrOneQuantifier();

  public abstract char getZeroOrMoreQuantifier();

  public abstract char getOneOrMoreQuantifier();

  public abstract String convert(String paramString);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.utils.WildcardSupport
 * JD-Core Version:    0.6.0
 */