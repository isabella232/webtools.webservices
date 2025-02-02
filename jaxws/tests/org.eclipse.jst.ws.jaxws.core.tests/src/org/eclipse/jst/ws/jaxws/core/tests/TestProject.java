/*******************************************************************************
 * Copyright (c) 2009 Shane Clarke.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Shane Clarke - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.ws.jaxws.core.tests;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

public class TestProject {
    IProgressMonitor monitor = new NullProgressMonitor();

    private IProject testProject;
    
    public TestProject(String projectName) throws CoreException {
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        testProject = workspaceRoot.getProject(projectName);
        testProject.create(monitor);
        testProject.open(monitor);
    }
    
    public IProject getProject() {
        return testProject;
    }
    
    public void addProjectNature(IProject project, String nature) {
        try {
            IProjectDescription projectDescription = project.getDescription();
            String[] previousNatures = projectDescription.getNatureIds();
            String[] newNatures = new String[previousNatures.length + 1];
            System.arraycopy(previousNatures, 0, newNatures, 0, previousNatures.length);
            newNatures[previousNatures.length] = nature;
            projectDescription.setNatureIds(newNatures);
            project.setDescription(projectDescription, null);
        } catch (CoreException ce) {
            ce.printStackTrace();
        }
    }
    
    public void mkdirs(final IFolder folder) {
    	try {
	        if (!folder.exists() && folder.getParent().isAccessible()) {
	            folder.create(true, true, null);
	        }
		} catch (CoreException ce) {
			ce.printStackTrace();
		}
    }
}
