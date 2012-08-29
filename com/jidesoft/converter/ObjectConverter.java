package com.jidesoft.converter;

public abstract interface ObjectConverter
{
  public abstract String toString(Object paramObject, ConverterContext paramConverterContext);

  public abstract boolean supportToString(Object paramObject, ConverterContext paramConverterContext);

  public abstract Object fromString(String paramString, ConverterContext paramConverterContext);

  public abstract boolean supportFromString(String paramString, ConverterContext paramConverterContext);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.converter.ObjectConverter
 * JD-Core Version:    0.6.0
 */