package com.jidesoft.validation;

import java.util.EventListener;

public abstract interface RowValidator extends EventListener
{
  public abstract ValidationResult validating(RowValidationObject paramRowValidationObject);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.validation.RowValidator
 * JD-Core Version:    0.6.0
 */