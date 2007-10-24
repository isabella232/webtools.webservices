/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.ws.service.policy;

import java.util.List;


public class PolicyEnumerationListImpl implements IPolicyEnumerationList
{
  private List<IStateEnumerationItem> enumList;
  private IServicePolicy              policy;
  
  public PolicyEnumerationListImpl( List<IStateEnumerationItem> enumList, IServicePolicy policy )
  {
    this.enumList = enumList;
    this.policy   = policy;
  }
  
  public List<IStateEnumerationItem> getEnumerationList()
  {
    return enumList;
  }

  public IServicePolicy getPolicy()
  {
    return policy;
  }
}
