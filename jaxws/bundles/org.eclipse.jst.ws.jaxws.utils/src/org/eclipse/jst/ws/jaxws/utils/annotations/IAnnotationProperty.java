/*******************************************************************************
 * Copyright (c) 2009 by SAP AG, Walldorf. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SAP AG - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.ws.jaxws.utils.annotations;

import org.eclipse.jst.ws.jaxws.utils.internal.annotations.impl.AttributeTypeEnum;

public interface IAnnotationProperty
{
	public String getAnnotationName();

	public String getAttributeName();
	
	public String getValue();
	
	public AttributeTypeEnum getAttributeType();
}
