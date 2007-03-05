/*******************************************************************************
 * Copyright (c) 2001, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.wsdl;


import org.eclipse.xsd.XSDSchema;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>XSD Schema Extensibility Element</b></em>'.
 * @since 1.0
 * @ignore
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement#getSchema <em>Schema</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.wst.wsdl.WSDLPackage#getXSDSchemaExtensibilityElement()
 * @model 
 * @generated
 */
public interface XSDSchemaExtensibilityElement extends ExtensibilityElement
{
  /**
   * Returns the value of the '<em><b>Schema</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Schema</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Schema</em>' containment reference.
   * @see #setSchema(XSDSchema)
   * @see org.eclipse.wst.wsdl.WSDLPackage#getXSDSchemaExtensibilityElement_Schema()
   * @model containment="true"
   * @generated
   */
  XSDSchema getSchema();

  /**
   * Sets the value of the '{@link org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement#getSchema <em>Schema</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Schema</em>' containment reference.
   * @see #getSchema()
   * @generated
   */
  void setSchema(XSDSchema value);

} // XSDSchemaExtensibilityElement
