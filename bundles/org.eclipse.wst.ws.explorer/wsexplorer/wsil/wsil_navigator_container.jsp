<%
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
%>
<%@ page contentType="text/html; charset=UTF-8" import="org.eclipse.wst.ws.internal.explorer.platform.wsil.perspective.*,
                                                        org.eclipse.wst.ws.internal.explorer.platform.wsil.constants.*" %>

<jsp:useBean id="controller" class="org.eclipse.wst.ws.internal.explorer.platform.perspective.Controller" scope="session"/>
<%
   WSILPerspective wsilPerspective = controller.getWSILPerspective();
%>   
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<frameset rows="33,*" border=0 bgcolor="#ECE9D8">
  <frame name="<%=WsilFrameNames.WSIL_NAVIGATOR_TOOLBAR%>" title="<%=wsilPerspective.getMessage("FRAME_TITLE_NAVIGATOR_TOOLBAR")%>" src="<%=response.encodeURL("wsil_navigator_toolbar.jsp")%>" noresize scrolling="no" frameborder=0 marginwidth=0 marginheight=0>
  <frame name="<%=WsilFrameNames.WSIL_NAVIGATOR_CONTENT%>" title="<%=wsilPerspective.getMessage("FRAME_TITLE_NAVIGATOR_CONTENT")%>" src="<%=response.encodeURL("wsil_navigator_content.jsp")%>" noresize scrolling="no" frameborder=0 marginwidth=0 marginheight=0>
</frameset>
</html>
