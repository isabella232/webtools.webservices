/*******************************************************************************
 * Copyright (c) 2002, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.ws.internal.explorer.platform.wsdl.datamodel;

import org.eclipse.wst.ws.internal.datamodel.*;
import org.eclipse.wst.ws.internal.explorer.platform.constants.*;
import org.eclipse.wst.ws.internal.explorer.platform.datamodel.*;
import org.eclipse.wst.ws.internal.explorer.platform.util.*;
import org.eclipse.wst.ws.internal.explorer.platform.wsdl.constants.*;

import org.w3c.dom.*;

import javax.xml.parsers.*;

public class WSDLCommonElement extends TreeElement
{

  public WSDLCommonElement(String name, Model model) {
    super(name, model);
  }

  public final TreeElement getParentElement() {
      return (TreeElement)getElements(ModelConstants.REL_OWNER).nextElement();
  }
  
  protected final void setDocumentation(Node documentationElement)
  {
    String documentation = "";
    try
    {
      if (documentationElement != null)
      {
        Document doc = XMLUtils.createNewDocument(null);
        DocumentFragment df = doc.createDocumentFragment();
        NodeList documentationNodes = documentationElement.getChildNodes();
        for (int i=0;i<documentationNodes.getLength();i++)
          df.appendChild(doc.importNode(documentationNodes.item(i),true));
        documentation = XMLUtils.serialize(df,true);
      }
    }
    catch (ParserConfigurationException e)
    {
    }
    setPropertyAsString(WSDLModelConstants.PROP_DOCUMENTATION,documentation);
  }
}
