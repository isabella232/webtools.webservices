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
package org.eclipse.wst.wsdl.ui.internal.adapters.commands;

import org.eclipse.gef.commands.Command;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.asd.facade.IInterface;
import org.eclipse.wst.wsdl.ui.internal.adapters.WSDLAdapterFactory;
import org.eclipse.wst.wsdl.ui.internal.commands.AddPortTypeCommand;
import org.eclipse.wst.wsdl.ui.internal.util.NameUtil;

public class W11AddInterfaceCommand extends Command {
	private Definition definition;
	private String newName;
	private PortType newPortType;
	
	public W11AddInterfaceCommand(Definition definition) {
		this.definition = definition;
	}
	
	public void setNewPortTypeName(String newName) {
		this.newName = newName;
	}
	
	public PortType getNewPortType() {
		return newPortType;
	}
	
	public void execute() {
		if (newName == null || newName.equals("")) {
			newName = NameUtil.buildUniquePortTypeName(definition, "NewPortType");
		}
		// Add the Port Type
		AddPortTypeCommand command = new AddPortTypeCommand(definition, newName, false);
		command.run();
		
		newPortType = (PortType) command.getWSDLElement();
		
		// Add the Operation
		IInterface iInterface = (IInterface) WSDLAdapterFactory.getInstance().adapt(newPortType);
		Command addOperationCommand = iInterface.getAddOperationCommand();
		addOperationCommand.execute();
	}
}
