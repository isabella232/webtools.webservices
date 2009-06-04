/*******************************************************************************
 * Copyright (c) 2009 Shane Clarke.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Shane Clarke - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.ws.internal.jaxws.core.annotations.validation;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.eclipse.jst.ws.annotations.core.processor.AbstractAnnotationProcessor;
import org.eclipse.jst.ws.internal.jaxws.core.JAXWSCoreMessages;

import com.sun.mirror.declaration.AnnotationMirror;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.AnnotationTypeElementDeclaration;
import com.sun.mirror.declaration.AnnotationValue;
import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.Declaration;

/**
 * 
 * @author sclarke
 *
 */
public class WebServiceSEIPresentNoWebMethodRule extends AbstractAnnotationProcessor {
    
    private static final String ENDPOINT_INTERFACE = "endpointInterface"; //$NON-NLS-1$
    
    public WebServiceSEIPresentNoWebMethodRule() {
    }
    
    @Override
    public void process() {
        AnnotationTypeDeclaration annotationDeclaration = (AnnotationTypeDeclaration) environment
                .getTypeDeclaration(WebService.class.getName());

        Collection<Declaration> annotatedTypes = environment
                .getDeclarationsAnnotatedWith(annotationDeclaration);

        for (Declaration declaration : annotatedTypes) {
            if (declaration instanceof ClassDeclaration) {
                Collection<AnnotationMirror> annotationMirrors = declaration.getAnnotationMirrors();
    
                for (AnnotationMirror mirror : annotationMirrors) {
                    Map<AnnotationTypeElementDeclaration, AnnotationValue> valueMap = mirror.getElementValues();
                    Set<Map.Entry<AnnotationTypeElementDeclaration, AnnotationValue>> valueSet = valueMap
                            .entrySet();
                    for (Map.Entry<AnnotationTypeElementDeclaration, AnnotationValue> annotationKeyValue : valueSet) {
                        if (annotationKeyValue.getKey().getSimpleName().equals(ENDPOINT_INTERFACE)) {
                            checkWebMethods();
                        }
                    }
                }
            }
        }
    }
    
    private void checkWebMethods() {
        AnnotationTypeDeclaration webMethodDeclaration = (AnnotationTypeDeclaration) environment
            .getTypeDeclaration(WebMethod.class.getName());

        Collection<Declaration> webMethodAnnotatedTypes = environment
            .getDeclarationsAnnotatedWith(webMethodDeclaration);

        for (Declaration declaration : webMethodAnnotatedTypes) {
            Collection<AnnotationMirror> annotationMirrors = declaration.getAnnotationMirrors();
            for (AnnotationMirror mirror : annotationMirrors) {
                if (mirror.getAnnotationType().toString().equals(webMethodDeclaration
                        .getQualifiedName())) {
                    printError(mirror.getPosition(), JAXWSCoreMessages
                     .WEBSERVICE_ENPOINTINTERFACE_NO_WEBMETHODS_ERROR_MESSAGE); 
                }
            }
        }
    }
}
