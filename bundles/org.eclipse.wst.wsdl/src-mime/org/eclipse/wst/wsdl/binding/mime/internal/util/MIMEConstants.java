/*******************************************************************************
 * Copyright (c) 2001, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.wsdl.binding.mime.internal.util;


import org.w3c.dom.Element;


public final class MIMEConstants
{
  public static final String CONTENT_ELEMENT_TAG = "content";

  public static final String MIME_XML_ELEMENT_TAG = "mimeXml";

  public static final String MULTIPART_RELATED_ELEMENT_TAG = "multipartRelated";

  public static final String PART_ELEMENT_TAG = "part";

  public static final String MIME_NAMESPACE_URI = "http://schemas.xmlsoap.org/wsdl/mime/";

  public static final String PART_ATTRIBUTE = "part";

  public static final String TYPE_ATTRIBUTE = "type";

  public static String getAttribute(Element element, String attributeName)
  {
    return element.hasAttribute(attributeName) ? element.getAttribute(attributeName) : null;
  }
}
