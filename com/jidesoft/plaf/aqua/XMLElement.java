/*      */ package com.jidesoft.plaf.aqua;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.CharArrayReader;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.io.Writer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ 
/*      */ class XMLElement
/*      */ {
/*      */   static final long serialVersionUID = 6685035139346394777L;
/*      */   public static final int NANOXML_MAJOR_VERSION = 2;
/*      */   public static final int NANOXML_MINOR_VERSION = 2;
/*      */   private HashMap attributes;
/*      */   private ArrayList children;
/*      */   private String name;
/*      */   private String contents;
/*      */   private HashMap entities;
/*      */   private int lineNr;
/*      */   private boolean ignoreCase;
/*      */   private boolean ignoreWhitespace;
/*      */   private char charReadTooMuch;
/*      */   private Reader reader;
/*      */   private int parserLineNr;
/*      */ 
/*      */   public XMLElement()
/*      */   {
/*  200 */     this(new HashMap(), false, true, true);
/*      */   }
/*      */ 
/*      */   public XMLElement(HashMap entities)
/*      */   {
/*  221 */     this(entities, false, true, true);
/*      */   }
/*      */ 
/*      */   public XMLElement(boolean skipLeadingWhitespace)
/*      */   {
/*  241 */     this(new HashMap(), skipLeadingWhitespace, true, true);
/*      */   }
/*      */ 
/*      */   public XMLElement(HashMap entities, boolean skipLeadingWhitespace)
/*      */   {
/*  266 */     this(entities, skipLeadingWhitespace, true, true);
/*      */   }
/*      */ 
/*      */   public XMLElement(HashMap entities, boolean skipLeadingWhitespace, boolean ignoreCase)
/*      */   {
/*  293 */     this(entities, skipLeadingWhitespace, true, ignoreCase);
/*      */   }
/*      */ 
/*      */   protected XMLElement(HashMap entities, boolean skipLeadingWhitespace, boolean fillBasicConversionTable, boolean ignoreCase)
/*      */   {
/*  324 */     this.ignoreWhitespace = skipLeadingWhitespace;
/*  325 */     this.ignoreCase = ignoreCase;
/*  326 */     this.name = null;
/*  327 */     this.contents = "";
/*  328 */     this.attributes = new HashMap();
/*  329 */     this.children = new ArrayList();
/*  330 */     this.entities = entities;
/*  331 */     this.lineNr = 0;
/*  332 */     Iterator iter = this.entities.keySet().iterator();
/*  333 */     while (iter.hasNext()) {
/*  334 */       Object key = iter.next();
/*  335 */       Object value = this.entities.get(key);
/*  336 */       if ((value instanceof String)) {
/*  337 */         value = ((String)value).toCharArray();
/*  338 */         this.entities.put(key, value);
/*      */       }
/*      */     }
/*  341 */     if (fillBasicConversionTable) {
/*  342 */       this.entities.put("amp", new char[] { '&' });
/*  343 */       this.entities.put("quot", new char[] { '"' });
/*  344 */       this.entities.put("apos", new char[] { '\'' });
/*  345 */       this.entities.put("lt", new char[] { '<' });
/*  346 */       this.entities.put("gt", new char[] { '>' });
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addChild(XMLElement child)
/*      */   {
/*  369 */     this.children.add(child);
/*      */   }
/*      */ 
/*      */   public void setAttribute(String name, Object value)
/*      */   {
/*  398 */     if (this.ignoreCase) {
/*  399 */       name = name.toUpperCase();
/*      */     }
/*  401 */     this.attributes.put(name, value.toString());
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void addProperty(String name, Object value)
/*      */   {
/*  414 */     setAttribute(name, value);
/*      */   }
/*      */ 
/*      */   public void setIntAttribute(String name, int value)
/*      */   {
/*  440 */     if (this.ignoreCase) {
/*  441 */       name = name.toUpperCase();
/*      */     }
/*  443 */     this.attributes.put(name, Integer.toString(value));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void addProperty(String key, int value)
/*      */   {
/*  456 */     setIntAttribute(key, value);
/*      */   }
/*      */ 
/*      */   public void setDoubleAttribute(String name, double value)
/*      */   {
/*  482 */     if (this.ignoreCase) {
/*  483 */       name = name.toUpperCase();
/*      */     }
/*  485 */     this.attributes.put(name, Double.toString(value));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void addProperty(String name, double value)
/*      */   {
/*  498 */     setDoubleAttribute(name, value);
/*      */   }
/*      */ 
/*      */   public int countChildren()
/*      */   {
/*  513 */     return this.children.size();
/*      */   }
/*      */ 
/*      */   public Iterator enumerateAttributeNames()
/*      */   {
/*  546 */     return this.attributes.keySet().iterator();
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Iterator enumeratePropertyNames()
/*      */   {
/*  556 */     return enumerateAttributeNames();
/*      */   }
/*      */ 
/*      */   public Iterator iterateChildren()
/*      */   {
/*  571 */     return this.children.iterator();
/*      */   }
/*      */ 
/*      */   public ArrayList getChildren()
/*      */   {
/*      */     try
/*      */     {
/*  587 */       return (ArrayList)this.children.clone();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*  592 */     return null;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public String getContents()
/*      */   {
/*  603 */     return getContent();
/*      */   }
/*      */ 
/*      */   public String getContent()
/*      */   {
/*  613 */     return this.contents;
/*      */   }
/*      */ 
/*      */   public int getLineNr()
/*      */   {
/*  624 */     return this.lineNr;
/*      */   }
/*      */ 
/*      */   public Object getAttribute(String name)
/*      */   {
/*  643 */     return getAttribute(name, null);
/*      */   }
/*      */ 
/*      */   public Object getAttribute(String name, Object defaultValue)
/*      */   {
/*  664 */     if (this.ignoreCase) {
/*  665 */       name = name.toUpperCase();
/*      */     }
/*  667 */     Object value = this.attributes.get(name);
/*  668 */     if (value == null) {
/*  669 */       value = defaultValue;
/*      */     }
/*  671 */     return value;
/*      */   }
/*      */ 
/*      */   public Object getAttribute(String name, HashMap valueSet, String defaultKey, boolean allowLiterals)
/*      */   {
/*  701 */     if (this.ignoreCase) {
/*  702 */       name = name.toUpperCase();
/*      */     }
/*  704 */     Object key = this.attributes.get(name);
/*      */ 
/*  706 */     if (key == null) {
/*  707 */       key = defaultKey;
/*      */     }
/*  709 */     Object result = valueSet.get(key);
/*  710 */     if (result == null) {
/*  711 */       if (allowLiterals) {
/*  712 */         result = key;
/*      */       }
/*      */       else {
/*  715 */         throw invalidValue(name, (String)key);
/*      */       }
/*      */     }
/*  718 */     return result;
/*      */   }
/*      */ 
/*      */   public String getStringAttribute(String name)
/*      */   {
/*  737 */     return getStringAttribute(name, null);
/*      */   }
/*      */ 
/*      */   public String getStringAttribute(String name, String defaultValue)
/*      */   {
/*  758 */     return (String)getAttribute(name, defaultValue);
/*      */   }
/*      */ 
/*      */   public String getStringAttribute(String name, HashMap valueSet, String defaultKey, boolean allowLiterals)
/*      */   {
/*  789 */     return (String)getAttribute(name, valueSet, defaultKey, allowLiterals);
/*      */   }
/*      */ 
/*      */   public int getIntAttribute(String name)
/*      */   {
/*  808 */     return getIntAttribute(name, 0);
/*      */   }
/*      */ 
/*      */   public int getIntAttribute(String name, int defaultValue)
/*      */   {
/*  828 */     if (this.ignoreCase) {
/*  829 */       name = name.toUpperCase();
/*      */     }
/*  831 */     String value = (String)this.attributes.get(name);
/*  832 */     if (value == null) {
/*  833 */       return defaultValue;
/*      */     }
/*      */     try
/*      */     {
/*  837 */       return Integer.parseInt(value);
/*      */     } catch (NumberFormatException e) {
/*      */     }
/*  840 */     throw invalidValue(name, value);
/*      */   }
/*      */ 
/*      */   public int getIntAttribute(String name, HashMap valueSet, String defaultKey, boolean allowLiteralNumbers)
/*      */   {
/*  873 */     if (this.ignoreCase) {
/*  874 */       name = name.toUpperCase();
/*      */     }
/*  876 */     Object key = this.attributes.get(name);
/*      */ 
/*  878 */     if (key == null)
/*  879 */       key = defaultKey; Integer result;
/*      */     try {
/*  882 */       result = (Integer)valueSet.get(key);
/*      */     }
/*      */     catch (ClassCastException e) {
/*  885 */       throw invalidValueSet(name);
/*      */     }
/*  887 */     if (result == null) {
/*  888 */       if (!allowLiteralNumbers)
/*  889 */         throw invalidValue(name, (String)key);
/*      */       try
/*      */       {
/*  892 */         result = Integer.valueOf((String)key);
/*      */       }
/*      */       catch (NumberFormatException e) {
/*  895 */         throw invalidValue(name, (String)key);
/*      */       }
/*      */     }
/*  898 */     return result.intValue();
/*      */   }
/*      */ 
/*      */   public double getDoubleAttribute(String name)
/*      */   {
/*  916 */     return getDoubleAttribute(name, 0.0D);
/*      */   }
/*      */ 
/*      */   public double getDoubleAttribute(String name, double defaultValue)
/*      */   {
/*  936 */     if (this.ignoreCase) {
/*  937 */       name = name.toUpperCase();
/*      */     }
/*  939 */     String value = (String)this.attributes.get(name);
/*  940 */     if (value == null) {
/*  941 */       return defaultValue;
/*      */     }
/*      */     try
/*      */     {
/*  945 */       return Double.valueOf(value).doubleValue();
/*      */     } catch (NumberFormatException e) {
/*      */     }
/*  948 */     throw invalidValue(name, value);
/*      */   }
/*      */ 
/*      */   public double getDoubleAttribute(String name, HashMap valueSet, String defaultKey, boolean allowLiteralNumbers)
/*      */   {
/*  981 */     if (this.ignoreCase) {
/*  982 */       name = name.toUpperCase();
/*      */     }
/*  984 */     Object key = this.attributes.get(name);
/*      */ 
/*  986 */     if (key == null)
/*  987 */       key = defaultKey; Double result;
/*      */     try {
/*  990 */       result = (Double)valueSet.get(key);
/*      */     }
/*      */     catch (ClassCastException e) {
/*  993 */       throw invalidValueSet(name);
/*      */     }
/*  995 */     if (result == null) {
/*  996 */       if (!allowLiteralNumbers)
/*  997 */         throw invalidValue(name, (String)key);
/*      */       try
/*      */       {
/* 1000 */         result = Double.valueOf((String)key);
/*      */       }
/*      */       catch (NumberFormatException e) {
/* 1003 */         throw invalidValue(name, (String)key);
/*      */       }
/*      */     }
/* 1006 */     return result.doubleValue();
/*      */   }
/*      */ 
/*      */   public boolean getBooleanAttribute(String name, String trueValue, String falseValue, boolean defaultValue)
/*      */   {
/* 1032 */     if (this.ignoreCase) {
/* 1033 */       name = name.toUpperCase();
/*      */     }
/* 1035 */     Object value = this.attributes.get(name);
/* 1036 */     if (value == null) {
/* 1037 */       return defaultValue;
/*      */     }
/* 1039 */     if (value.equals(trueValue)) {
/* 1040 */       return true;
/*      */     }
/* 1042 */     if (value.equals(falseValue)) {
/* 1043 */       return false;
/*      */     }
/*      */ 
/* 1046 */     throw invalidValue(name, (String)value);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public int getIntProperty(String name, HashMap valueSet, String defaultKey)
/*      */   {
/* 1060 */     return getIntAttribute(name, valueSet, defaultKey, false);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public String getProperty(String name)
/*      */   {
/* 1070 */     return getStringAttribute(name);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public String getProperty(String name, String defaultValue)
/*      */   {
/* 1081 */     return getStringAttribute(name, defaultValue);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public int getProperty(String name, int defaultValue)
/*      */   {
/* 1092 */     return getIntAttribute(name, defaultValue);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public double getProperty(String name, double defaultValue)
/*      */   {
/* 1103 */     return getDoubleAttribute(name, defaultValue);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public boolean getProperty(String key, String trueValue, String falseValue, boolean defaultValue)
/*      */   {
/* 1117 */     return getBooleanAttribute(key, trueValue, falseValue, defaultValue);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Object getProperty(String name, HashMap valueSet, String defaultKey)
/*      */   {
/* 1131 */     return getAttribute(name, valueSet, defaultKey, false);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public String getStringProperty(String name, HashMap valueSet, String defaultKey)
/*      */   {
/* 1144 */     return getStringAttribute(name, valueSet, defaultKey, false);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public int getSpecialIntProperty(String name, HashMap valueSet, String defaultKey)
/*      */   {
/* 1157 */     return getIntAttribute(name, valueSet, defaultKey, true);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public double getSpecialDoubleProperty(String name, HashMap valueSet, String defaultKey)
/*      */   {
/* 1170 */     return getDoubleAttribute(name, valueSet, defaultKey, true);
/*      */   }
/*      */ 
/*      */   public String getName()
/*      */   {
/* 1180 */     return this.name;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public String getTagName()
/*      */   {
/* 1190 */     return getName();
/*      */   }
/*      */ 
/*      */   public void parseFromReader(Reader reader)
/*      */     throws IOException, XMLParseException
/*      */   {
/* 1210 */     parseFromReader(reader, 1);
/*      */   }
/*      */ 
/*      */   public void parseFromReader(Reader reader, int startingLineNr)
/*      */     throws IOException, XMLParseException
/*      */   {
/* 1232 */     this.name = null;
/* 1233 */     this.contents = "";
/* 1234 */     this.attributes = new HashMap();
/* 1235 */     this.children = new ArrayList();
/* 1236 */     this.charReadTooMuch = '\000';
/* 1237 */     this.reader = reader;
/* 1238 */     this.parserLineNr = startingLineNr;
/*      */     while (true)
/*      */     {
/* 1241 */       char ch = scanWhitespace();
/*      */ 
/* 1243 */       if (ch != '<') {
/* 1244 */         throw expectedInput("<");
/*      */       }
/*      */ 
/* 1247 */       ch = readChar();
/*      */ 
/* 1249 */       if ((ch == '!') || (ch == '?')) {
/* 1250 */         skipSpecialTag(0);
/*      */       }
/*      */       else {
/* 1253 */         unreadChar(ch);
/* 1254 */         scanElement(this);
/* 1255 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void parseString(String string)
/*      */     throws XMLParseException
/*      */   {
/*      */     try
/*      */     {
/* 1276 */       parseFromReader(new StringReader(string), 1);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void parseString(String string, int offset)
/*      */     throws XMLParseException
/*      */   {
/* 1301 */     parseString(string.substring(offset));
/*      */   }
/*      */ 
/*      */   public void parseString(String string, int offset, int end)
/*      */     throws XMLParseException
/*      */   {
/* 1324 */     parseString(string.substring(offset, end));
/*      */   }
/*      */ 
/*      */   public void parseString(String string, int offset, int end, int startingLineNr)
/*      */     throws XMLParseException
/*      */   {
/* 1349 */     string = string.substring(offset, end);
/*      */     try {
/* 1351 */       parseFromReader(new StringReader(string), startingLineNr);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void parseCharArray(char[] input, int offset, int end)
/*      */     throws XMLParseException
/*      */   {
/* 1378 */     parseCharArray(input, offset, end, 1);
/*      */   }
/*      */ 
/*      */   public void parseCharArray(char[] input, int offset, int end, int startingLineNr)
/*      */     throws XMLParseException
/*      */   {
/*      */     try
/*      */     {
/* 1404 */       Reader reader = new CharArrayReader(input, offset, end);
/* 1405 */       parseFromReader(reader, startingLineNr);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeChild(XMLElement child)
/*      */   {
/* 1430 */     this.children.remove(child);
/*      */   }
/*      */ 
/*      */   public void removeAttribute(String name)
/*      */   {
/* 1468 */     if (this.ignoreCase) {
/* 1469 */       name = name.toUpperCase();
/*      */     }
/* 1471 */     this.attributes.remove(name);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void removeProperty(String name)
/*      */   {
/* 1482 */     removeAttribute(name);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void removeChild(String name)
/*      */   {
/* 1493 */     removeAttribute(name);
/*      */   }
/*      */ 
/*      */   public XMLElement createAnotherElement()
/*      */   {
/* 1503 */     return new XMLElement(this.entities, this.ignoreWhitespace, false, this.ignoreCase);
/*      */   }
/*      */ 
/*      */   public void setContent(String content)
/*      */   {
/* 1516 */     this.contents = content;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setTagName(String name)
/*      */   {
/* 1527 */     setName(name);
/*      */   }
/*      */ 
/*      */   public void setName(String name)
/*      */   {
/* 1541 */     this.name = name;
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/*      */     try
/*      */     {
/* 1553 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 1554 */       OutputStreamWriter writer = new OutputStreamWriter(out);
/* 1555 */       write(writer);
/* 1556 */       writer.flush();
/* 1557 */       return new String(out.toByteArray());
/*      */     }
/*      */     catch (IOException e) {
/*      */     }
/* 1561 */     return super.toString();
/*      */   }
/*      */ 
/*      */   public void write(Writer writer)
/*      */     throws IOException
/*      */   {
/* 1578 */     if (this.name == null) {
/* 1579 */       writeEncoded(writer, this.contents);
/* 1580 */       return;
/*      */     }
/* 1582 */     writer.write(60);
/* 1583 */     writer.write(this.name);
/* 1584 */     if (!this.attributes.isEmpty()) {
/* 1585 */       Iterator iter = this.attributes.keySet().iterator();
/* 1586 */       while (iter.hasNext()) {
/* 1587 */         writer.write(32);
/* 1588 */         String key = (String)iter.next();
/* 1589 */         String value = (String)this.attributes.get(key);
/* 1590 */         writer.write(key);
/* 1591 */         writer.write(61);
/* 1592 */         writer.write(34);
/* 1593 */         writeEncoded(writer, value);
/* 1594 */         writer.write(34);
/*      */       }
/*      */     }
/* 1597 */     if ((this.contents != null) && (this.contents.length() > 0)) {
/* 1598 */       writer.write(62);
/* 1599 */       writeEncoded(writer, this.contents);
/* 1600 */       writer.write(60);
/* 1601 */       writer.write(47);
/* 1602 */       writer.write(this.name);
/* 1603 */       writer.write(62);
/*      */     }
/* 1605 */     else if (this.children.isEmpty()) {
/* 1606 */       writer.write(47);
/* 1607 */       writer.write(62);
/*      */     }
/*      */     else {
/* 1610 */       writer.write(62);
/* 1611 */       Iterator iter = iterateChildren();
/* 1612 */       while (iter.hasNext()) {
/* 1613 */         XMLElement child = (XMLElement)iter.next();
/* 1614 */         child.write(writer);
/*      */       }
/* 1616 */       writer.write(60);
/* 1617 */       writer.write(47);
/* 1618 */       writer.write(this.name);
/* 1619 */       writer.write(62);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void writeEncoded(Writer writer, String str)
/*      */     throws IOException
/*      */   {
/* 1636 */     for (int i = 0; i < str.length(); i++) {
/* 1637 */       char ch = str.charAt(i);
/* 1638 */       switch (ch) {
/*      */       case '<':
/* 1640 */         writer.write(38);
/* 1641 */         writer.write(108);
/* 1642 */         writer.write(116);
/* 1643 */         writer.write(59);
/* 1644 */         break;
/*      */       case '>':
/* 1646 */         writer.write(38);
/* 1647 */         writer.write(103);
/* 1648 */         writer.write(116);
/* 1649 */         writer.write(59);
/* 1650 */         break;
/*      */       case '&':
/* 1652 */         writer.write(38);
/* 1653 */         writer.write(97);
/* 1654 */         writer.write(109);
/* 1655 */         writer.write(112);
/* 1656 */         writer.write(59);
/* 1657 */         break;
/*      */       case '"':
/* 1659 */         writer.write(38);
/* 1660 */         writer.write(113);
/* 1661 */         writer.write(117);
/* 1662 */         writer.write(111);
/* 1663 */         writer.write(116);
/* 1664 */         writer.write(59);
/* 1665 */         break;
/*      */       case '\'':
/* 1667 */         writer.write(38);
/* 1668 */         writer.write(97);
/* 1669 */         writer.write(112);
/* 1670 */         writer.write(111);
/* 1671 */         writer.write(115);
/* 1672 */         writer.write(59);
/* 1673 */         break;
/*      */       default:
/* 1675 */         int unicode = ch;
/* 1676 */         if ((unicode < 32) || (unicode > 126)) {
/* 1677 */           writer.write(38);
/* 1678 */           writer.write(35);
/* 1679 */           writer.write(120);
/* 1680 */           writer.write(Integer.toString(unicode, 16));
/* 1681 */           writer.write(59);
/*      */         }
/*      */         else {
/* 1684 */           writer.write(ch);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void scanIdentifier(StringBuffer result)
/*      */     throws IOException
/*      */   {
/*      */     while (true)
/*      */     {
/* 1705 */       char ch = readChar();
/* 1706 */       if (((ch < 'A') || (ch > 'Z')) && ((ch < 'a') || (ch > 'z')) && ((ch < '0') || (ch > '9')) && (ch != '_') && (ch != '.') && (ch != ':') && (ch != '-') && (ch <= '~'))
/*      */       {
/* 1709 */         unreadChar(ch);
/* 1710 */         return;
/*      */       }
/* 1712 */       result.append(ch);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected char scanWhitespace()
/*      */     throws IOException
/*      */   {
/*      */     while (true)
/*      */     {
/* 1725 */       char ch = readChar();
/* 1726 */       switch (ch) {
/*      */       case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1731 */         break;
/*      */       default:
/* 1733 */         return ch;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected char scanWhitespace(StringBuffer result)
/*      */     throws IOException
/*      */   {
/*      */     while (true)
/*      */     {
/* 1750 */       char ch = readChar();
/* 1751 */       switch (ch) {
/*      */       case '\t':
/*      */       case '\n':
/*      */       case ' ':
/* 1755 */         result.append(ch);
/*      */       case '\r':
/* 1757 */         break;
/*      */       default:
/* 1759 */         return ch;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void scanString(StringBuffer string)
/*      */     throws IOException
/*      */   {
/* 1774 */     char delimiter = readChar();
/* 1775 */     if ((delimiter != '\'') && (delimiter != '"'))
/* 1776 */       throw expectedInput("' or \"");
/*      */     while (true)
/*      */     {
/* 1779 */       char ch = readChar();
/* 1780 */       if (ch == delimiter) {
/* 1781 */         return;
/*      */       }
/* 1783 */       if (ch == '&') {
/* 1784 */         resolveEntity(string);
/*      */       }
/*      */       else
/* 1787 */         string.append(ch);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void scanPCData(StringBuffer data)
/*      */     throws IOException
/*      */   {
/*      */     while (true)
/*      */     {
/* 1802 */       char ch = readChar();
/* 1803 */       if (ch == '<') {
/* 1804 */         ch = readChar();
/* 1805 */         if (ch == '!') {
/* 1806 */           checkCDATA(data);
/*      */         }
/*      */         else {
/* 1809 */           unreadChar(ch);
/* 1810 */           return;
/*      */         }
/*      */       }
/* 1813 */       else if (ch == '&') {
/* 1814 */         resolveEntity(data);
/*      */       }
/*      */       else {
/* 1817 */         data.append(ch);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean checkCDATA(StringBuffer buf)
/*      */     throws IOException
/*      */   {
/* 1831 */     char ch = readChar();
/* 1832 */     if (ch != '[') {
/* 1833 */       unreadChar(ch);
/* 1834 */       skipSpecialTag(0);
/* 1835 */       return false;
/*      */     }
/* 1837 */     if (!checkLiteral("CDATA[")) {
/* 1838 */       skipSpecialTag(1);
/* 1839 */       return false;
/*      */     }
/*      */ 
/* 1842 */     int delimiterCharsSkipped = 0;
/* 1843 */     while (delimiterCharsSkipped < 3) {
/* 1844 */       ch = readChar();
/* 1845 */       switch (ch) {
/*      */       case ']':
/* 1847 */         if (delimiterCharsSkipped < 2) {
/* 1848 */           delimiterCharsSkipped++; continue;
/*      */         }
/*      */ 
/* 1851 */         buf.append(']');
/* 1852 */         buf.append(']');
/* 1853 */         delimiterCharsSkipped = 0;
/*      */ 
/* 1855 */         break;
/*      */       case '>':
/* 1857 */         if (delimiterCharsSkipped < 2) {
/* 1858 */           for (int i = 0; i < delimiterCharsSkipped; i++) {
/* 1859 */             buf.append(']');
/*      */           }
/* 1861 */           delimiterCharsSkipped = 0;
/* 1862 */           buf.append('>'); continue;
/*      */         }
/*      */ 
/* 1865 */         delimiterCharsSkipped = 3;
/*      */ 
/* 1867 */         break;
/*      */       default:
/* 1869 */         for (int i = 0; i < delimiterCharsSkipped; i++) {
/* 1870 */           buf.append(']');
/*      */         }
/* 1872 */         buf.append(ch);
/* 1873 */         delimiterCharsSkipped = 0;
/*      */       }
/*      */     }
/* 1876 */     return true;
/*      */   }
/*      */ 
/*      */   protected void skipComment()
/*      */     throws IOException
/*      */   {
/* 1888 */     int dashesToRead = 2;
/* 1889 */     while (dashesToRead > 0) {
/* 1890 */       char ch = readChar();
/* 1891 */       if (ch == '-') {
/* 1892 */         dashesToRead--;
/*      */       }
/*      */       else {
/* 1895 */         dashesToRead = 2;
/*      */       }
/*      */     }
/* 1898 */     if (readChar() != '>')
/* 1899 */       throw expectedInput(">");
/*      */   }
/*      */ 
/*      */   protected void skipSpecialTag(int bracketLevel)
/*      */     throws IOException
/*      */   {
/* 1914 */     int tagLevel = 1;
/* 1915 */     char stringDelimiter = '\000';
/* 1916 */     if (bracketLevel == 0) {
/* 1917 */       char ch = readChar();
/* 1918 */       if (ch == '[') {
/* 1919 */         bracketLevel++;
/*      */       }
/* 1921 */       else if (ch == '-') {
/* 1922 */         ch = readChar();
/* 1923 */         if (ch == '[') {
/* 1924 */           bracketLevel++;
/*      */         }
/* 1926 */         else if (ch == ']') {
/* 1927 */           bracketLevel--;
/*      */         }
/* 1929 */         else if (ch == '-') {
/* 1930 */           skipComment();
/* 1931 */           return;
/*      */         }
/*      */       }
/*      */     }
/* 1935 */     while (tagLevel > 0) {
/* 1936 */       char ch = readChar();
/* 1937 */       if (stringDelimiter == 0) {
/* 1938 */         if ((ch == '"') || (ch == '\'')) {
/* 1939 */           stringDelimiter = ch;
/*      */         }
/* 1941 */         else if (bracketLevel <= 0) {
/* 1942 */           if (ch == '<') {
/* 1943 */             tagLevel++;
/*      */           }
/* 1945 */           else if (ch == '>') {
/* 1946 */             tagLevel--;
/*      */           }
/*      */         }
/* 1949 */         if (ch == '[') {
/* 1950 */           bracketLevel++;
/*      */         }
/* 1952 */         else if (ch == ']') {
/* 1953 */           bracketLevel--;
/*      */         }
/*      */ 
/*      */       }
/* 1957 */       else if (ch == stringDelimiter) {
/* 1958 */         stringDelimiter = '\000';
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean checkLiteral(String literal)
/*      */     throws IOException
/*      */   {
/* 1975 */     int length = literal.length();
/* 1976 */     for (int i = 0; i < length; i++) {
/* 1977 */       if (readChar() != literal.charAt(i)) {
/* 1978 */         return false;
/*      */       }
/*      */     }
/* 1981 */     return true;
/*      */   }
/*      */ 
/*      */   protected char readChar()
/*      */     throws IOException
/*      */   {
/* 1990 */     if (this.charReadTooMuch != 0) {
/* 1991 */       char ch = this.charReadTooMuch;
/* 1992 */       this.charReadTooMuch = '\000';
/* 1993 */       return ch;
/*      */     }
/*      */ 
/* 1996 */     int i = this.reader.read();
/* 1997 */     if (i < 0) {
/* 1998 */       throw unexpectedEndOfData();
/*      */     }
/* 2000 */     if (i == 10) {
/* 2001 */       this.parserLineNr += 1;
/* 2002 */       return '\n';
/*      */     }
/*      */ 
/* 2005 */     return (char)i;
/*      */   }
/*      */ 
/*      */   protected void scanElement(XMLElement elt)
/*      */     throws IOException
/*      */   {
/* 2021 */     StringBuffer buf = new StringBuffer();
/* 2022 */     scanIdentifier(buf);
/* 2023 */     String name = buf.toString();
/* 2024 */     elt.setName(name);
/* 2025 */     char ch = scanWhitespace();
/* 2026 */     while ((ch != '>') && (ch != '/')) {
/* 2027 */       buf.setLength(0);
/* 2028 */       unreadChar(ch);
/* 2029 */       scanIdentifier(buf);
/* 2030 */       String key = buf.toString();
/* 2031 */       ch = scanWhitespace();
/* 2032 */       if (ch != '=') {
/* 2033 */         throw expectedInput("=");
/*      */       }
/* 2035 */       unreadChar(scanWhitespace());
/* 2036 */       buf.setLength(0);
/* 2037 */       scanString(buf);
/* 2038 */       elt.setAttribute(key, buf);
/* 2039 */       ch = scanWhitespace();
/*      */     }
/* 2041 */     if (ch == '/') {
/* 2042 */       ch = readChar();
/* 2043 */       if (ch != '>') {
/* 2044 */         throw expectedInput(">");
/*      */       }
/* 2046 */       return;
/*      */     }
/* 2048 */     buf.setLength(0);
/* 2049 */     ch = scanWhitespace(buf);
/* 2050 */     if (ch != '<') {
/* 2051 */       unreadChar(ch);
/* 2052 */       scanPCData(buf);
/*      */     }
/*      */     else {
/*      */       while (true) {
/* 2056 */         ch = readChar();
/* 2057 */         if (ch != '!') break;
/* 2058 */         if (checkCDATA(buf)) {
/* 2059 */           scanPCData(buf);
/* 2060 */           break label272;
/*      */         }
/*      */ 
/* 2063 */         ch = scanWhitespace(buf);
/* 2064 */         if (ch != '<') {
/* 2065 */           unreadChar(ch);
/* 2066 */           scanPCData(buf);
/* 2067 */           break label272;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2072 */       if ((ch != '/') || (this.ignoreWhitespace)) {
/* 2073 */         buf.setLength(0);
/*      */       }
/* 2075 */       if (ch == '/') {
/* 2076 */         unreadChar(ch);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2082 */     label272: if (buf.length() == 0) {
/* 2083 */       while (ch != '/') {
/* 2084 */         if (ch == '!') {
/* 2085 */           ch = readChar();
/* 2086 */           if (ch != '-') {
/* 2087 */             throw expectedInput("Comment or Element");
/*      */           }
/* 2089 */           ch = readChar();
/* 2090 */           if (ch != '-') {
/* 2091 */             throw expectedInput("Comment or Element");
/*      */           }
/* 2093 */           skipComment();
/*      */         }
/*      */         else {
/* 2096 */           unreadChar(ch);
/* 2097 */           XMLElement child = createAnotherElement();
/* 2098 */           scanElement(child);
/* 2099 */           elt.addChild(child);
/*      */         }
/* 2101 */         ch = scanWhitespace();
/* 2102 */         if (ch != '<') {
/* 2103 */           throw expectedInput("<");
/*      */         }
/* 2105 */         ch = readChar();
/*      */       }
/* 2107 */       unreadChar(ch);
/*      */     }
/* 2110 */     else if (this.ignoreWhitespace) {
/* 2111 */       elt.setContent(buf.toString().trim());
/*      */     }
/*      */     else {
/* 2114 */       elt.setContent(buf.toString());
/*      */     }
/*      */ 
/* 2117 */     ch = readChar();
/* 2118 */     if (ch != '/') {
/* 2119 */       throw expectedInput("/");
/*      */     }
/* 2121 */     unreadChar(scanWhitespace());
/* 2122 */     if (!checkLiteral(name)) {
/* 2123 */       throw expectedInput(name);
/*      */     }
/* 2125 */     if (scanWhitespace() != '>')
/* 2126 */       throw expectedInput(">");
/*      */   }
/*      */ 
/*      */   protected void resolveEntity(StringBuffer buf)
/*      */     throws IOException
/*      */   {
/* 2142 */     char ch = '\000';
/* 2143 */     StringBuffer keyBuf = new StringBuffer();
/*      */     while (true) {
/* 2145 */       ch = readChar();
/* 2146 */       if (ch == ';') {
/*      */         break;
/*      */       }
/* 2149 */       keyBuf.append(ch);
/*      */     }
/* 2151 */     String key = keyBuf.toString();
/* 2152 */     if (key.charAt(0) == '#') {
/*      */       try {
/* 2154 */         if (key.charAt(1) == 'x') {
/* 2155 */           ch = (char)Integer.parseInt(key.substring(2), 16);
/*      */         }
/*      */         else
/* 2158 */           ch = (char)Integer.parseInt(key.substring(1), 10);
/*      */       }
/*      */       catch (NumberFormatException e)
/*      */       {
/* 2162 */         throw unknownEntity(key);
/*      */       }
/* 2164 */       buf.append(ch);
/*      */     }
/*      */     else {
/* 2167 */       char[] value = (char[])(char[])this.entities.get(key);
/* 2168 */       if (value == null) {
/* 2169 */         throw unknownEntity(key);
/*      */       }
/* 2171 */       buf.append(value);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void unreadChar(char ch)
/*      */   {
/* 2185 */     this.charReadTooMuch = ch;
/*      */   }
/*      */ 
/*      */   protected XMLParseException invalidValueSet(String name)
/*      */   {
/* 2197 */     String msg = "Invalid value set (entity name = \"" + name + "\")";
/* 2198 */     return new XMLParseException(getName(), this.parserLineNr, msg);
/*      */   }
/*      */ 
/*      */   protected XMLParseException invalidValue(String name, String value)
/*      */   {
/* 2213 */     String msg = "Attribute \"" + name + "\" does not contain a valid " + "value (\"" + value + "\")";
/*      */ 
/* 2215 */     return new XMLParseException(getName(), this.parserLineNr, msg);
/*      */   }
/*      */ 
/*      */   protected XMLParseException unexpectedEndOfData()
/*      */   {
/* 2223 */     String msg = "Unexpected end of data reached";
/* 2224 */     return new XMLParseException(getName(), this.parserLineNr, msg);
/*      */   }
/*      */ 
/*      */   protected XMLParseException syntaxError(String context)
/*      */   {
/* 2237 */     String msg = "Syntax error while parsing " + context;
/* 2238 */     return new XMLParseException(getName(), this.parserLineNr, msg);
/*      */   }
/*      */ 
/*      */   protected XMLParseException expectedInput(String charSet)
/*      */   {
/* 2251 */     String msg = "Expected: " + charSet;
/* 2252 */     return new XMLParseException(getName(), this.parserLineNr, msg);
/*      */   }
/*      */ 
/*      */   protected XMLParseException unknownEntity(String name)
/*      */   {
/* 2265 */     String msg = "Unknown or invalid entity: &" + name + ";";
/* 2266 */     return new XMLParseException(getName(), this.parserLineNr, msg);
/*      */   }
/*      */ }

/* Location:           D:\test\Fusion Installer.jar
 * Qualified Name:     com.jidesoft.plaf.aqua.XMLElement
 * JD-Core Version:    0.6.0
 */