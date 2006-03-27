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
import org.eclipse.wst.wsdl.Port;
import org.eclipse.wst.wsdl.Service;
import org.eclipse.wst.wsdl.ui.internal.commands.AddPortCommand;
import org.eclipse.wst.wsdl.ui.internal.util.NameUtil;

public class W11AddEndPointCommand extends Command
{
    private Service service;
    
    public W11AddEndPointCommand(Service service) {
        this.service = service;
    }
    
    public void execute() {
        AddPortCommand command = new AddPortCommand(service, NameUtil.buildUniquePortName(service, "NewPort"));
        command.run();
        Port port = (Port) command.getWSDLElement();
        
		// Set a default address
		String address = "http://www.example.org/";
		W11SetAddressCommand addressCommand = new W11SetAddressCommand(port, address);
		addressCommand.execute();
    }  
}
