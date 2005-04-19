/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.ws.internal.explorer.platform.favorites.actions;

import org.eclipse.wst.ws.internal.explorer.platform.constants.*;
import org.eclipse.wst.ws.internal.explorer.platform.datamodel.*;
import org.eclipse.wst.ws.internal.explorer.platform.favorites.datamodel.*;
import org.eclipse.wst.ws.internal.explorer.platform.favorites.perspective.*;
import org.eclipse.wst.ws.internal.explorer.platform.perspective.*;
import org.eclipse.wst.ws.internal.explorer.platform.uddi.actions.RegFindBusinessUUIDAction;
import org.eclipse.wst.ws.internal.explorer.platform.uddi.constants.UDDIActionInputs;
import org.eclipse.wst.ws.internal.explorer.platform.uddi.perspective.UDDIPerspective;

import java.util.Hashtable;

public class AddBusinessToUDDIPerspectiveAction extends AddToUDDIPerspectiveAction
{
  public AddBusinessToUDDIPerspectiveAction(Controller controller)
  {
    super(controller);
  }

  protected boolean executeSingleLinkAction()
  {
    int nodeID = Integer.parseInt((String) propertyTable_.get(ActionInputs.NODEID));
    FavoritesPerspective favPerspective = controller_.getFavoritesPerspective();
    UDDIPerspective uddiPerspective = controller_.getUDDIPerspective();
    NodeManager nodeManager = favPerspective.getNodeManager();
    Node selectedNode = nodeManager.getNode(nodeID);
    TreeElement selectedElement = selectedNode.getTreeElement();
    FavoritesUDDIBusinessElement busElement = (FavoritesUDDIBusinessElement) selectedElement;
    String businessName = busElement.getName();
    String inquiryAPI = busElement.getInquiryURL();
    String businessKey = busElement.getBusinessKey();
    // create the registry in the UDDI perspective
    if (!createRegistryInUDDIPerspective(inquiryAPI, null, inquiryAPI, null, true))
      return false;
    RegFindBusinessUUIDAction action = new RegFindBusinessUUIDAction(controller_);
    Hashtable propertyTable = action.getPropertyTable();
    propertyTable.put(UDDIActionInputs.QUERY_INPUT_UUID_BUSINESS_KEY, businessKey);
    propertyTable.put(UDDIActionInputs.QUERY_NAME, businessName);
    if (!action.run())
    {
      favPerspective.getMessageQueue().addMessage(favPerspective.getMessage("MSG_ERROR_ADD_TO_UDDI_PERSPECTIVE", businessName));
      return false;
    }
    uddiPerspective.getNavigatorManager().getSelectedNode().setVisibilityOfChildren(true);
    favPerspective.getMessageQueue().addMessage(favPerspective.getMessage("MSG_INFO_ADD_TO_UDDI_PERSPECTIVE_SUCCESSFUL", businessName));
    return true;
  }
}
