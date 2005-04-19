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

package org.eclipse.wst.ws.internal.explorer.platform.perspective;

public abstract class ImportToFileSystemTool extends ActionTool {
    public ImportToFileSystemTool(ToolManager toolManager, String alt) {
        super(toolManager, "images/import_fs_enabled.gif", "images/import_fs_highlighted.gif", alt);
    }

}
