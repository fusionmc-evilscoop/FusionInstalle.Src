package org.apache.log4j.jmx;

import java.lang.reflect.Method;

class MethodUnion
{
  Method readMethod;
  Method writeMethod;

  MethodUnion(Method paramMethod1, Method paramMethod2)
  {
    this.readMethod = paramMethod1;
    this.writeMethod = paramMethod2;
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.jmx.MethodUnion
 * JD-Core Version:    0.6.0
 */