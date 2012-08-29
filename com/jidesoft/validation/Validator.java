package com.jidesoft.validation;

import java.util.EventListener;

public abstract interface Validator extends EventListener
{
  public abstract ValidationResult validating(ValidationObject paramValidationObject);
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.validation.Validator
 * JD-Core Version:    0.6.0
 */