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
package org.eclipse.wst.command.env.core.common;

/**
 *  A ProgressMonitor that does nothing. 
 */
public class NullProgressMonitor implements ProgressMonitor
{

	/* (non-Javadoc)
	 * @see org.eclipse.wst.command.env.core.common.ProgressMonitor#getChildProgressMonitor()
	 */
	public ProgressMonitor getChildProgressMonitor() 
	{
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.wst.command.env.core.common.ProgressMonitor#isCancelRequested()
	 */
	public boolean isCancelRequested() 
	{
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.wst.command.env.core.common.ProgressMonitor#report(java.lang.String)
	 */
	public void report(String progress) 
	{
	}
}
