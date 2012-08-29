package org.apache.log4j.or.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.or.ObjectRenderer;

public class MessageRenderer
  implements ObjectRenderer
{
  public String doRender(Object paramObject)
  {
    if ((paramObject instanceof Message))
    {
      StringBuffer localStringBuffer = new StringBuffer();
      Message localMessage = (Message)paramObject;
      try
      {
        localStringBuffer.append("DeliveryMode=");
        switch (localMessage.getJMSDeliveryMode())
        {
        case 1:
          localStringBuffer.append("NON_PERSISTENT");
          break;
        case 2:
          localStringBuffer.append("PERSISTENT");
          break;
        default:
          localStringBuffer.append("UNKNOWN");
        }
        localStringBuffer.append(", CorrelationID=");
        localStringBuffer.append(localMessage.getJMSCorrelationID());
        localStringBuffer.append(", Destination=");
        localStringBuffer.append(localMessage.getJMSDestination());
        localStringBuffer.append(", Expiration=");
        localStringBuffer.append(localMessage.getJMSExpiration());
        localStringBuffer.append(", MessageID=");
        localStringBuffer.append(localMessage.getJMSMessageID());
        localStringBuffer.append(", Priority=");
        localStringBuffer.append(localMessage.getJMSPriority());
        localStringBuffer.append(", Redelivered=");
        localStringBuffer.append(localMessage.getJMSRedelivered());
        localStringBuffer.append(", ReplyTo=");
        localStringBuffer.append(localMessage.getJMSReplyTo());
        localStringBuffer.append(", Timestamp=");
        localStringBuffer.append(localMessage.getJMSTimestamp());
        localStringBuffer.append(", Type=");
        localStringBuffer.append(localMessage.getJMSType());
      }
      catch (JMSException localJMSException)
      {
        LogLog.error("Could not parse Message.", localJMSException);
      }
      return localStringBuffer.toString();
    }
    return paramObject.toString();
  }
}

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     org.apache.log4j.or.jms.MessageRenderer
 * JD-Core Version:    0.6.0
 */