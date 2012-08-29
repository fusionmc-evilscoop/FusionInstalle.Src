package com.jidesoft.range;

import java.beans.PropertyChangeListener;

public abstract interface Range<T>
{
  public static final String PROPERTY_MIN = "min";
  public static final String PROPERTY_MAX = "max";

  public abstract T lower();

  public abstract T upper();

  public abstract void adjust(T paramT1, T paramT2);

  public abstract double minimum();

  public abstract double maximum();

  public abstract double size();

  public abstract boolean contains(T paramT);

  public abstract void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener);

  public abstract void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.range.Range
 * JD-Core Version:    0.6.0
 */