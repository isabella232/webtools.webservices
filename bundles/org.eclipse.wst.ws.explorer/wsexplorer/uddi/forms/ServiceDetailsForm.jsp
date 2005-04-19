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
<%@ page contentType="text/html; charset=UTF-8" import="org.eclipse.wst.ws.internal.explorer.platform.uddi.datamodel.*,
                                                        org.eclipse.wst.ws.internal.explorer.platform.uddi.perspective.*,
                                                        org.eclipse.wst.ws.internal.explorer.platform.uddi.constants.*,
                                                        org.eclipse.wst.ws.internal.explorer.platform.uddi.util.Uddi4jHelper,
                                                        org.eclipse.wst.ws.internal.explorer.platform.perspective.*,
                                                        org.eclipse.wst.ws.internal.explorer.platform.datamodel.*,
                                                        org.eclipse.wst.ws.internal.explorer.platform.constants.*,
                                                        org.eclipse.wst.ws.internal.explorer.platform.util.*,
                                                        org.uddi4j.datatype.*,
                                                        org.uddi4j.util.*,
                                                        java.util.*" %>

<jsp:useBean id="controller" class="org.eclipse.wst.ws.internal.explorer.platform.perspective.Controller" scope="session"/>
<jsp:useBean id="sectionHeaderInfo" class="org.eclipse.wst.ws.internal.explorer.platform.perspective.SectionHeaderInfo" scope="request"/>
<%
   UDDIPerspective uddiPerspective = controller.getUDDIPerspective();
   NodeManager navigatorManager = uddiPerspective.getNavigatorManager();
   Node selectedNode = navigatorManager.getSelectedNode();
   ToolManager currentToolManager = selectedNode.getCurrentToolManager();
   FormTool formTool = (FormTool)(currentToolManager.getSelectedTool());
   ServiceElement serviceElement = (ServiceElement)selectedNode.getTreeElement();
   UDDIMainNode uddiMainNode = (UDDIMainNode)navigatorManager.getRootNode();
   RegistryNode regNode = uddiMainNode.getRegistryNode(selectedNode);
   RegistryElement regElement = (RegistryElement)regNode.getTreeElement();
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title><%=uddiPerspective.getMessage("FORM_TITLE_SERVICE_DETAILS")%></title>
  <link rel="stylesheet" type="text/css" href="<%=response.encodeURL(controller.getPathWithContext("css/windows.css"))%>">
<script language="javascript" src="<%=response.encodeURL(controller.getPathWithContext("scripts/browserdetect.js"))%>">
</script>
<script language="javascript" src="<%=response.encodeURL(controller.getPathWithContext("scripts/resumeproxyloadpage.js"))%>">
</script>
<jsp:include page="/scripts/formsubmit.jsp" flush="true"/>
<jsp:include page="/uddi/scripts/detailstables.jsp" flush="true"/>
<script language="javascript">
  function setDefaults()
  {
    var serviceDetailsWSDLURLTable = getTable("serviceDetailsWSDLURL");
    var serviceDetailsNamesTable = getTable("serviceDetailsNames");
    var serviceDetailsDescriptionsTable = getTable("serviceDetailsDescriptions");
<%
   ListElement wsdlURLListElement = (ListElement)formTool.getProperty(UDDIActionInputs.QUERY_INPUT_ADVANCED_SERVICE_WSDL_URL);
   String wsdlURL = null;
   if (wsdlURLListElement == null)
   {
     try
     {
       Uddi4jHelper uddi4jHelper = new Uddi4jHelper();
       wsdlURL = uddi4jHelper.getWSDL(serviceElement.getBusinessService(), serviceElement.getRegistryElement().getProxy());
       uddi4jHelper.getWSDLDefinition(wsdlURL);
       if (wsdlURL == null)
         wsdlURL = "temp.wsdl";
     }
     catch (Throwable t)
     {
       wsdlURL = "";
       formTool.flagError(UDDIActionInputs.QUERY_INPUT_ADVANCED_SERVICE_WSDL_URL);
     }
     wsdlURLListElement = new ListElement(wsdlURL);
     wsdlURLListElement.setTargetViewToolInfo(-1,-1,0);
     formTool.setProperty(UDDIActionInputs.QUERY_INPUT_ADVANCED_SERVICE_WSDL_URL,wsdlURLListElement);
   }
   else
     wsdlURL = (String)wsdlURLListElement.getObject();
   boolean isError = !formTool.isInputValid(UDDIActionInputs.QUERY_INPUT_ADVANCED_SERVICE_WSDL_URL);
   boolean isNewItem = (wsdlURLListElement.getTargetViewId() == ActionInputs.VIEWID_DEFAULT);
%>
    addDetailsSingleItemRow("serviceDetailsWSDLURL",<%=isError||isNewItem%>);
    setDetailsWSDLURLSingleItemRow("serviceDetailsWSDLURL",<%=UDDIActionInputs.WSDL_TYPE_SERVICE%>,"<%=HTMLUtils.JSMangle(wsdlURL)%>");
<%
   if (isError)
   {
%>
    highlightErrantRow(serviceDetailsWSDLURLTable.rows[numberOfHeaderRows],"errantRow");
<%
   }

   Vector nameIndexVector = (Vector)formTool.getProperty(UDDIActionInputs.QUERY_INPUT_ADVANCED_SERVICE_NAMES);
   if (nameIndexVector != null)
   {
     for (int i=0;i<nameIndexVector.size();i++)
     {
       ListElement listElement = (ListElement)nameIndexVector.elementAt(i);
       Name name = (Name)listElement.getObject();
       isError = !formTool.isRowInputValid(UDDIActionInputs.QUERY_INPUT_ADVANCED_SERVICE_NAMES,i);
       isNewItem = (listElement.getTargetViewId() == ActionInputs.VIEWID_DEFAULT);
%>
    addDetailsLanguageInputRow("serviceDetailsNames","<%=HTMLUtils.JSMangle(uddiPerspective.getMessage("FORM_CONTROL_TITLE_NAME_TEXT_VALUE"))%>",<%=isError||isNewItem%>);
    setDetailsLanguageInputRow("serviceDetailsNames",<%=i%>,<%=listElement.getViewId()%>,"<%=name.getLang()%>","<%=HTMLUtils.JSMangle(name.getText())%>");
<%
       if (isError)
       {
%>
    highlightErrantRow(serviceDetailsNamesTable.rows[<%=i%>+numberOfHeaderRows],"errantRow");
<%
       }
     }
   }

   Vector descriptionIndexVector = (Vector)formTool.getProperty(UDDIActionInputs.QUERY_INPUT_ADVANCED_SERVICE_DESCRIPTIONS);
   if (descriptionIndexVector != null)
   {
     for (int i=0;i<descriptionIndexVector.size();i++)
     {
       ListElement listElement = (ListElement)descriptionIndexVector.elementAt(i);
       Description description = (Description)listElement.getObject();
       isError = !formTool.isRowInputValid(UDDIActionInputs.QUERY_INPUT_ADVANCED_SERVICE_DESCRIPTIONS,i);
       isNewItem = (listElement.getTargetViewId() == ActionInputs.VIEWID_DEFAULT);
%>
    addDetailsLanguageInputRow("serviceDetailsDescriptions","<%=HTMLUtils.JSMangle(uddiPerspective.getMessage("FORM_CONTROL_TITLE_DESCRIPTION_TEXT_VALUE"))%>",<%=isError||isNewItem%>);
    setDetailsLanguageInputRow("serviceDetailsDescriptions",<%=i%>,<%=listElement.getViewId()%>,"<%=description.getLang()%>","<%=HTMLUtils.JSMangle(description.getText())%>");
<%
       if (isError)
       {
%>
    highlightErrantRow(serviceDetailsDescriptionsTable.rows[<%=i%>+numberOfHeaderRows],"errantRow");
<%
       }
     }
   }

   Vector catVector = (Vector)formTool.getProperty(UDDIActionInputs.QUERY_INPUT_ADVANCED_SERVICE_CATEGORIES);
   if (catVector != null)
   {
     for (int i=0;i<catVector.size();i++)
     {
       ListElement listElement = (ListElement)catVector.elementAt(i);
       KeyedReference kr = (KeyedReference)listElement.getObject();
       isError = !formTool.isRowInputValid(UDDIActionInputs.QUERY_INPUT_ADVANCED_SERVICE_CATEGORIES,i);
       isNewItem = (listElement.getTargetViewId() == ActionInputs.VIEWID_DEFAULT);
%>
    addDetailsCategoryRow("serviceDetailsCategories",<%=isError||isNewItem%>);
    setDetailsCategoryRow("serviceDetailsCategories",<%=i%>,<%=listElement.getViewId()%>,"<%=HTMLUtils.JSMangle(kr.getTModelKey())%>","<%=HTMLUtils.JSMangle(kr.getKeyName())%>","<%=HTMLUtils.JSMangle(kr.getKeyValue())%>");
<%
     }
   }

   // Authentication.
   if (!regElement.isLoggedIn())
   {
     String publishURL = (String)formTool.getProperty(UDDIActionInputs.QUERY_INPUT_ADVANCED_PUBLISH_URL);
     String userId = (String)formTool.getProperty(UDDIActionInputs.QUERY_INPUT_ADVANCED_USERID);
     String password = (String)formTool.getProperty(UDDIActionInputs.QUERY_INPUT_ADVANCED_PASSWORD);
%>
    document.forms[0].<%=UDDIActionInputs.QUERY_INPUT_ADVANCED_PUBLISH_URL%>.value = "<%=HTMLUtils.JSMangle(publishURL)%>";
    document.forms[0].<%=UDDIActionInputs.QUERY_INPUT_ADVANCED_USERID%>.value = "<%=HTMLUtils.JSMangle(userId)%>";
    document.forms[0].<%=UDDIActionInputs.QUERY_INPUT_ADVANCED_PASSWORD%>.value = "<%=HTMLUtils.JSMangle(password)%>";
    document.getElementById("serviceDetailsAuthentication").style.display = "";
<%
   }
%>

    showMainForm();
  }

  function showMainForm()
  {
    closeAllUddiChildWindows();
    var loadScreenTable = document.getElementById("loadScreen");
    if (loadScreenTable.rows.length > 0)
      loadScreenTable.deleteRow(0);
    document.getElementById("mainScreen").style.display = "";
  }

  function processForm(form)
  {
    if (handleSubmit(form))
    {
      processDetailsSingleItemTable("serviceDetailsWSDLURL","<%=UDDIActionInputs.WSDL_URL_MODIFIED%>","<%=UDDIActionInputs.QUERY_INPUT_ADVANCED_SERVICE_WSDL_URL%>",form);
      processDetailsLanguageInputTable("serviceDetailsNames","<%=UDDIActionInputs.NAME_MODIFIED%>","<%=UDDIActionInputs.NAME_VIEWID%>","<%=UDDIActionInputs.QUERY_INPUT_ADVANCED_NAME_LANGUAGE%>","<%=UDDIActionInputs.QUERY_INPUT_ADVANCED_NAME%>",form);
      processDetailsLanguageInputTable("serviceDetailsDescriptions","<%=UDDIActionInputs.DESCRIPTION_MODIFIED%>","<%=UDDIActionInputs.DESCRIPTION_VIEWID%>","<%=UDDIActionInputs.QUERY_INPUT_ADVANCED_DESCRIPTION_LANGUAGE%>","<%=UDDIActionInputs.QUERY_INPUT_ADVANCED_DESCRIPTION%>",form);
      processDetailsCategoryTable("serviceDetailsCategories",form);
      return true;
    }
    return false;
  }
</script>
</head>
<body class="contentbodymargin" onUnload="closeAllUddiChildWindows()">
  <div id="contentborder">
    <form action="<%=response.encodeURL(controller.getPathWithContext("uddi/actions/UpdateServiceActionJSP.jsp"))%>" method="post" target="<%=FrameNames.PERSPECTIVE_WORKAREA%>" enctype="multipart/form-data" onSubmit="return processForm(this)">
      <table id="loadScreen">
        <tr>
          <td class="labels">
            <%=controller.getMessage("MSG_LOAD_IN_PROGRESS")%>
          </td>
        </tr>
      </table>
      <div id="mainScreen" style="display:none;">
<%
   String titleImagePath = "images/details_highlighted.gif";
   String title = uddiPerspective.getMessage("ALT_SERVICE_DETAILS");
%>
<%@ include file="/forms/formheader.inc" %>
        <input type="hidden" name="<%=UDDIActionInputs.QUERY_INPUT_UUID_BUSINESS_KEY%>" value="<%=formTool.getProperty(UDDIActionInputs.QUERY_INPUT_UUID_BUSINESS_KEY)%>">
        <input type="hidden" name="<%=UDDIActionInputs.QUERY_INPUT_UUID_SERVICE_KEY%>" value="<%=formTool.getProperty(UDDIActionInputs.QUERY_INPUT_UUID_SERVICE_KEY)%>">
        <table>
          <tr>
            <td class="labels">
              <%=uddiPerspective.getMessage("FORM_LABEL_DETAILS_EDITABLE",selectedNode.getNodeName())%>
            </td>
          </tr>
          <tr>
            <td height=20>&nbsp;</td>
          </tr>
        </table>
        <table width="95%" cellpadding=3 cellspacing=0 class="tableborder">
          <tr>
            <th class="singleheadercolor" height=20 valign="bottom" align="left">
              <%=uddiPerspective.getMessage("FORM_LABEL_SERVICE_KEY")%>
            </th>
          </tr>
          <tr>
            <td class="tablecells">
              <%=formTool.getProperty(UDDIActionInputs.QUERY_INPUT_UUID_SERVICE_KEY)%>
            </td>
          </tr>
        </table>
<%
   sectionHeaderInfo.clear();
   sectionHeaderInfo.setContainerId("serviceDetailsWSDLURL");
   sectionHeaderInfo.setOtherProperties(controller.getMessage("FORM_LABEL_WSDL_URL"));
%>
<jsp:include page="/uddi/forms/detailsSingleItem_table.jsp" flush="true"/>
<%
   sectionHeaderInfo.clear();
   sectionHeaderInfo.setContainerId("serviceDetailsNames");
   String[] nameSpecificInfo = {"FORM_LABEL_NAMES","FORM_LABEL_NAME",String.valueOf(!formTool.isInputValid(UDDIActionInputs.QUERY_INPUT_ADVANCED_SERVICE_NAMES))};
   sectionHeaderInfo.setOtherProperties(nameSpecificInfo);
%>
<jsp:include page="/uddi/forms/detailsLanguageInput_table.jsp" flush="true"/>
<%
   sectionHeaderInfo.clear();
   sectionHeaderInfo.setContainerId("serviceDetailsDescriptions");
   String[] descSpecificInfo = {"FORM_LABEL_DESCRIPTIONS","FORM_LABEL_DESCRIPTION",String.valueOf(!formTool.isInputValid(UDDIActionInputs.QUERY_INPUT_ADVANCED_SERVICE_DESCRIPTIONS))};
   sectionHeaderInfo.setOtherProperties(descSpecificInfo);
%>
<jsp:include page="/uddi/forms/detailsLanguageInput_table.jsp" flush="true"/>
<%
   sectionHeaderInfo.clear();
   sectionHeaderInfo.setContainerId("serviceDetailsCategories");
%>
<jsp:include page="/uddi/forms/detailsCategories_table.jsp" flush="true"/>
<%
   sectionHeaderInfo.clear();
   sectionHeaderInfo.setContainerId("serviceDetailsAuthentication");
%>
<jsp:include page="/uddi/forms/authentication_table.jsp" flush="true"/>
<jsp:useBean id="currentToolManagerHash" class="java.util.Hashtable" scope="request">
<%
  currentToolManagerHash.put(ActionInputs.CURRENT_TOOL_MANAGER,currentToolManager);
%>
</jsp:useBean>
<jsp:include page="/forms/otherActions.jsp" flush="true"/>
<jsp:include page="/forms/simpleCommon_table.jsp" flush="true"/>
      </div>
    </form>
  </div>
<script language="javascript">
  setDefaults();
  resumeProxyLoadPage();
</script>
</body>
</html>
