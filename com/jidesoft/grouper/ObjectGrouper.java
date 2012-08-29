package com.jidesoft.grouper;

import com.jidesoft.comparator.ComparatorContext;
import com.jidesoft.converter.ConverterContext;

public abstract interface ObjectGrouper
{
  public abstract Object getValue(Object paramObject);

  public abstract Class<?> getType();

  public abstract String getName();

  public abstract ConverterContext getConverterContext();

  public abstract ComparatorContext getComparatorContext();
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.grouper.ObjectGrouper
 * JD-Core Version:    0.6.0
 */