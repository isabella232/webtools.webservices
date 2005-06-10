/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.ws.internal.creation.ui.widgets.runtime;

import org.eclipse.core.resources.IProject;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jst.ws.internal.common.J2EEUtils;
import org.eclipse.jst.ws.internal.consumption.ui.common.ValidationUtils;
import org.eclipse.jst.ws.internal.consumption.ui.widgets.runtime.ClientRuntimeSelectionWidget;
import org.eclipse.jst.ws.internal.consumption.ui.widgets.runtime.ProjectSelectionWidget;
import org.eclipse.jst.ws.internal.consumption.ui.widgets.runtime.RuntimeServerSelectionWidget;
import org.eclipse.jst.ws.internal.consumption.ui.wsrt.WebServiceRuntimeExtensionUtils;
import org.eclipse.jst.ws.internal.data.TypeRuntimeServer;
import org.eclipse.jst.ws.internal.ui.common.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.command.internal.env.ui.widgets.SimpleWidgetDataContributor;
import org.eclipse.wst.command.internal.env.ui.widgets.WidgetDataEvents;
import org.eclipse.wst.command.internal.provisional.env.core.common.MessageUtils;
import org.eclipse.wst.command.internal.provisional.env.core.common.SimpleStatus;
import org.eclipse.wst.command.internal.provisional.env.core.common.Status;
import org.eclipse.wst.command.internal.provisional.env.core.selection.SelectionListChoices;

/*
 *
 * ServerRuntimeSelectionWidget
 * This widget is the parent widget for server and client deployment/environment settings 
 * 
 */
public class ServerRuntimeSelectionWidget extends SimpleWidgetDataContributor
{
  private String pluginId_ = "org.eclipse.jst.ws.consumption.ui";
  private String createPluginId_ = "org.eclipse.jst.ws.creation.ui";
  
  /* CONTEXT_ID PWRS0002 for the Wizard Scenario Service configuration of the Runtime Selection Page */
  private String INFOPOP_PWRS_GROUP_SERVICE =  "PWRS0002";
  
  private RuntimeServerSelectionWidget runtimeWidget_;
  private ProjectSelectionWidget       projectWidget_;
  private ClientRuntimeSelectionWidget clientWidget_;
  private SelectionListChoices         serviceChoices_;
  private TextModifyListener           textListener_;
  private MessageUtils msgUtils_;
  
  private boolean isClientWidgetVisible_ = true;
  
  public WidgetDataEvents addControls( Composite parent, Listener statusListener )
  {
    msgUtils_ = new MessageUtils( pluginId_ + ".plugin", this );
    UIUtils      uiUtils  = new UIUtils(msgUtils_, createPluginId_ ); 
    
    Composite root = uiUtils.createComposite( parent, 1 );
    
    Text messageText = new Text( root, SWT.WRAP | SWT.MULTI | SWT.READ_ONLY );
    
    GridData gd = new GridData(); 
    gd.horizontalIndent = 10;
    
    messageText.setLayoutData(gd);
    messageText.setText(
        msgUtils_.getMessage("MSG_GENERAL_PROJECT_AND_EAR", new String[] { msgUtils_.getMessage("MSG_SERVICE_SUB")})
        + "\n"
        + msgUtils_.getMessage("MSG_EAR_PROJECT_WILL_BE_CREATED"));
    
    Composite serverComp = uiUtils.createComposite( root, 1, 5, 0 );
    
    Group serverGroup = uiUtils.createGroup( serverComp, "LABEL_SELECTION_VIEW_TITLE",
                                             null, INFOPOP_PWRS_GROUP_SERVICE,
											 2, 5, 5);
    
    runtimeWidget_ = new RuntimeServerSelectionWidget( false );
    runtimeWidget_.addControls( serverGroup, statusListener );
    textListener_ = new TextModifyListener();
    runtimeWidget_.addModifyListener( textListener_ );
             
    projectWidget_ = new ProjectSelectionWidget();
    projectWidget_.addControls( serverGroup, statusListener );
    
    clientWidget_ = new ClientRuntimeSelectionWidget();
    clientWidget_.addControls( root, statusListener );

    return this;
  }
  
  //If generate proxy is not selected, don't show the client portion of the page.
  public void setGenerateProxy( Boolean value )
  {
    clientWidget_.setVisible( value.booleanValue() ); 
    isClientWidgetVisible_ = value.booleanValue();
  }
  
  public TypeRuntimeServer getServiceTypeRuntimeServer()
  {
    return runtimeWidget_.getTypeRuntimeServer();  
  }
   
  public void setServiceTypeRuntimeServer( TypeRuntimeServer ids )
  {
    runtimeWidget_.removeModifyListener( textListener_ );
    runtimeWidget_.setTypeRuntimeServer( ids );  
    projectWidget_.setTypeRuntimeServer(ids);
    runtimeWidget_.addModifyListener( textListener_ );
  }
  
  public TypeRuntimeServer getClientTypeRuntimeServer()
  {
    return clientWidget_.getClientTypeRuntimeServer();  
  }
   
  public void setClientTypeRuntimeServer( TypeRuntimeServer ids )
  {
    clientWidget_.setClientTypeRuntimeServer( ids );  
  }
  
  public SelectionListChoices getServiceProject2EARProject()
  {
    return projectWidget_.getProjectChoices();
  }
  
  public void setServiceProject2EARProject(SelectionListChoices serviceProject2EARProject)
  {
    projectWidget_.setProjectChoices(serviceProject2EARProject);
  }
  
  public SelectionListChoices getRuntime2ClientTypes()
  {
    return clientWidget_.getRuntime2ClientTypes();
  }  
  
  public void setRuntime2ClientTypes(SelectionListChoices runtime2ClientTypes)
  {
    clientWidget_.setRuntime2ClientTypes(runtime2ClientTypes);
  }
  
  public String getServiceJ2EEVersion()
  {
    return runtimeWidget_.getJ2EEVersion();
  }
  
  public void setServiceJ2EEVersion(String j2eeVersion)
  {
    runtimeWidget_.setJ2EEVersion(j2eeVersion);
    projectWidget_.setJ2EEVersion(j2eeVersion);
  }
  
  public String getClientJ2EEVersion()
  {
    return clientWidget_.getJ2EEVersion();
  }
  
  public void setClientJ2EEVersion(String j2eeVersion)
  {
    clientWidget_.setJ2EEVersion(j2eeVersion);
  }
  
  public boolean getServiceNeedEAR()
  {
    return projectWidget_.getNeedEAR();
  }
  
  public void setServiceNeedEAR(boolean b)
  {
    projectWidget_.setNeedEAR(b);
  }
  
  public boolean getClientNeedEAR()
  {
    return clientWidget_.getClientNeedEAR();
  }
  
  public void setClientNeedEAR(boolean b)
  {
    clientWidget_.setClientNeedEAR(b);
  }  
  
  public String getServiceComponentName()
  {
	return projectWidget_.getComponentName();  
  }
  
  public void setServiceComponentName( String name )
  {
    projectWidget_.setComponentName( name );
  }
  
  public String getServiceEarComponentName()
  {
    return projectWidget_.getEarComponentName();	  
  }
  
  public void setServiceEarComponentName( String name )
  {
	projectWidget_.setEarComponentName( name );  
  }
  
  public void setServiceComponentType( String type )
  {
	projectWidget_.setComponentType( type );  
  }
  
  public String getClientComponentName()
  {
	return clientWidget_.getClientComponentName();  
  }
  
  public void setClientComponentName( String name )
  {
    clientWidget_.setClientComponentName( name );
  }
  
  public String getClientEarComponentName()
  {
    return clientWidget_.getClientEarComponentName();	  
  }
  
  public void setClientEarComponentName( String name )
  {
	clientWidget_.setClientEarComponentName( name );  
  }
  
  public String getClientComponentType()
  {
    return clientWidget_.getClientComponentType();
  }  
  
  public void setComponentType( String type )
  {
	clientWidget_.setClientComponentType( type );  
  }
  
  public String getServiceProjectName()
  {
    return projectWidget_.getProjectName();  
  }
  
  public String getServiceEarProjectName()
  {
	return projectWidget_.getEarProjectName();  
  }
  
  public String getClientProjectName()
  {
    return clientWidget_.getClientProjectName();  
  }
  
  public String getClientEarProjectName()
  {
	return clientWidget_.getClientEarProjectName();  
  }
  
  private class TextModifyListener implements ModifyListener 
  {
  	public void modifyText(ModifyEvent e)
  	{
		if( clientWidget_.isVisible() )
  		{  	  
		  TypeRuntimeServer serviceIds = runtimeWidget_.getTypeRuntimeServer();
  		  TypeRuntimeServer clientIds  = clientWidget_.getClientTypeRuntimeServer();
  		
  		  clientIds.setRuntimeId( serviceIds.getRuntimeId() );
  		  clientIds.setServerId( serviceIds.getServerId() );
  		  clientIds.setServerInstanceId( serviceIds.getServerInstanceId() );

  		  clientWidget_.setClientTypeRuntimeServer( clientIds );
  		  clientWidget_.setJ2EEVersion(runtimeWidget_.getJ2EEVersion());  	
  		}
		
		//Set the current server selection and J2EE level on the ProjectSelectionWidget
		projectWidget_.setTypeRuntimeServer(runtimeWidget_.getTypeRuntimeServer());
		projectWidget_.setJ2EEVersion(runtimeWidget_.getJ2EEVersion());
  	}
  }
 
  
  /* (non-Javadoc)
   * @see org.eclipse.wst.command.env.ui.widgets.WidgetContributor#getStatus()
   */
  public Status getStatus() 
  {
    Status serviceStatus = runtimeWidget_.getStatus();
    Status projectStatus = projectWidget_.getStatus();
    Status clientStatus  = clientWidget_.getStatus();    
    Status finalStatus   = new SimpleStatus( "" );
    
    // call child widgets' getStatus()
    if( serviceStatus.getSeverity() == Status.ERROR )
    {
      finalStatus = serviceStatus;
    }
    else if( clientStatus.getSeverity() == Status.ERROR )
    {
      if (isClientWidgetVisible_)
        finalStatus = clientStatus;
    }
    else if ( projectStatus.getSeverity()== Status.ERROR) {
      finalStatus = projectStatus;
    }
    
    //Validate service side server target and J2EE level
	ValidationUtils valUtils = new ValidationUtils();
	String serviceEARName  = projectWidget_.getEarProjectName();
	String serviceProjName = projectWidget_.getProjectName();
	String serviceServerFactoryId = runtimeWidget_.getTypeRuntimeServer().getServerId();
	String serviceJ2EElevel = runtimeWidget_.getJ2EEVersion();
  String serviceComponentName = projectWidget_.getComponentName();
  String serviceEARComponentName = projectWidget_.getEarComponentName();
	Status serviceProjectStatus = valUtils.validateProjectTargetAndJ2EE(serviceProjName,serviceComponentName, serviceEARName, serviceEARComponentName, serviceServerFactoryId, serviceJ2EElevel);
	if(serviceProjectStatus.getSeverity()==Status.ERROR)
	{
		finalStatus = serviceProjectStatus;
	}
    
    //Ensure the service project type (Web/EJB) is valid
    if (serviceProjName!=null && serviceProjName.length()>0)
    {
      IProject serviceProj = ProjectUtilities.getProject(serviceProjName);
      if (serviceProj.exists())
      {
        //Determine whether an EJB project is required
        String webServiceRuntimeId = runtimeWidget_.getTypeRuntimeServer().getRuntimeId();
        String webServiceTypeId = runtimeWidget_.getTypeRuntimeServer().getTypeId();
		// rskreg
        //WebServiceServerRuntimeTypeRegistry wssrtRegistry = WebServiceServerRuntimeTypeRegistry.getInstance();
        //String serverTypeId = wssrtRegistry.getWebServiceServerByFactoryId(serviceServerFactoryId).getId();
		//String serverTypeId = wssrtRegistry.getWebServiceServerByFactoryId(serviceServerFactoryId).getId();
        /* rskejb
        boolean isEJBRequired = WebServiceRuntimeExtensionUtils.requiresEJBModuleFor(serviceServerFactoryId, webServiceRuntimeId, webServiceTypeId);
        if (!isEJBRequired)
        {
          //Check the Web service type to see if an EJB project is required
          //isEJBRequired = wssrtRegistry.requiresEJBProject(webServiceTypeId);
			isEJBRequired = WebServiceRuntimeExtensionUtils.requiresEJBProject(webServiceTypeId);
        }
        rskejb */
        boolean isEJBRequired = WebServiceRuntimeExtensionUtils.requiresEJBProject(webServiceRuntimeId, webServiceTypeId);
        if (isEJBRequired && serviceComponentName!=null && serviceComponentName.length()>0 && !J2EEUtils.isEJBComponent(serviceProj, serviceComponentName))
        {
          finalStatus = new SimpleStatus("",msgUtils_.getMessage("MSG_INVALID_EJB_PROJECT",new String[]{serviceProjName}),Status.ERROR);          
        }
        if (!isEJBRequired && serviceComponentName!=null && serviceComponentName.length()>0 && !J2EEUtils.isWebComponent(serviceProj, serviceComponentName))
        {
          finalStatus = new SimpleStatus("",msgUtils_.getMessage("MSG_INVALID_WEB_PROJECT",new String[]{serviceProjName}),Status.ERROR);
        }
      }
    }
    
    if (isClientWidgetVisible_) 
    {
	    String clientEARName   = clientWidget_.getClientEarProjectName();
	    String clientProjName  = clientWidget_.getClientProjectName();
	
  		String clientComponentName = clientWidget_.getClientComponentName();
		
	    // check same EAR-ness -----
	    String warning_msg = getEARProjectWarningMessage(serviceEARName, clientEARName);
	    
		if (serviceComponentName.equalsIgnoreCase(clientComponentName)){
			  String err_msg = msgUtils_.getMessage( "MSG_SAME_CLIENT_AND_SERVICE_COMPONENTS", new String[]{ "WEB" } );
			  finalStatus = new SimpleStatus( "", err_msg, Status.ERROR );				
		}
		
	    if( clientProjName != null && serviceProjName != null && 
	        clientProjName.equalsIgnoreCase( serviceProjName ))
	    {
		  String error_msg = msgUtils_.getMessage("MSG_SAME_CLIENT_AND_SERVICE_PROJECTS");
		  finalStatus = new SimpleStatus("", error_msg, Status.ERROR);
	    }
	    
		if (warning_msg != null)
	    {
	      if (finalStatus.getSeverity()!=Status.ERROR)
	      	return new SimpleStatus( "", warning_msg, Status.WARNING );          
	    }         
      
    }
    
    return finalStatus;
  }
  
  private String getEARProjectWarningMessage(String serviceEARName, String clientEARName ) {

    // check if service and client share the same EAR
    if (serviceEARName!=null && clientEARName!=null) {
    
      if (clientEARName.equalsIgnoreCase(serviceEARName) && clientEARName.length()>0) {
        return msgUtils_.getMessage("MSG_SAME_CLIENT_AND_SERVICE_EARS", new String[]{ "EAR" });
      }
    }

    return null;

  }  
}
