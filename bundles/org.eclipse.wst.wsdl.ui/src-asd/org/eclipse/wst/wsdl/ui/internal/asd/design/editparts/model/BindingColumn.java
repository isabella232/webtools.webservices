/*******************************************************************************
 * Copyright (c) 2001, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.wsdl.ui.internal.asd.design.editparts.model;

import org.eclipse.wst.wsdl.ui.internal.asd.facade.IDescription;
import org.eclipse.wst.wsdl.ui.internal.asd.outline.ITreeElement;

public class BindingColumn extends AbstractModelCollection {
	  public BindingColumn(IDescription description) {
	    super(description, "BindingColumn"); //$NON-NLS-1$
	  }
	  
	  public ITreeElement[] getChildren() {
			Object array[] = ((IDescription)model).getBindings().toArray();
			ITreeElement treeElement[] = new ITreeElement[array.length];
			for (int index = 0; index < array.length; index++) {
				treeElement[index] = (ITreeElement) array[index];
			}
			
			return treeElement;
	  }
	  
	  
	  public boolean hasChildren() {
		  ITreeElement treeElement[] = getChildren();
		  if (treeElement.length > 0) {
			  return true;
		  }
		  
		  return false;
	  }
	  
	  public String getText() {
		  return "definition"; //$NON-NLS-1$
	  }
}
