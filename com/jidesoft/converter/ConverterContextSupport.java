package com.jidesoft.converter;

public abstract interface ConverterContextSupport
{
  public abstract void setConverterContext(ConverterContext paramConverterContext);

  public abstract ConverterContext getConverterContext();

  public abstract Class<?> getType();

  public abstract void setType(Class<?> paramClass);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.ConverterContextSupport
 * JD-Core Version:    0.6.0
 */