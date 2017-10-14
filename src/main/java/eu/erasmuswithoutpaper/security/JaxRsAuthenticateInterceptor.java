package eu.erasmuswithoutpaper.security;

import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.error.control.EwpSecWebApplicationException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.cert.X509Certificate;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class JaxRsAuthenticateInterceptor implements ContainerRequestFilter, ContainerResponseFilter  {
    private static final Logger logger = LoggerFactory.getLogger(JaxRsAuthenticateInterceptor.class);

    @Context
    private ResourceInfo resourceInfo;

    @Inject
    GlobalProperties properties;

    @Inject
    RegistryClient registryClient;
    
    @Inject
    HttpSignature httpSignature;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method method = resourceInfo.getResourceMethod();

        if (method.getAnnotation(EwpAuthenticate.class) == null) {
            return;
        }
        
        if (httpSignature.adaptsToHttpSignatureRequest(requestContext)) {
            httpSignature.verifyHttpSignatureRequest(requestContext);
        } else {
            verifyX509CertificateRequest(requestContext);
        }
    }
    
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        Method method = resourceInfo.getResourceMethod();

        if (method.getAnnotation(EwpAuthenticate.class) == null) {
            return;
        }
        
        if (httpSignature.adaptsToHttpSignatureRequest(requestContext)) {
            httpSignature.signResponse(requestContext, responseContext);
        }
    }
    
    private void verifyX509CertificateRequest(ContainerRequestContext requestContext) throws EwpSecWebApplicationException {
        X509Certificate[] certificates = (X509Certificate[]) requestContext.getProperty("javax.servlet.request.X509Certificate");
        if (certificates == null && !properties.isAllowMissingClientCertificate()) {
            throw new EwpSecWebApplicationException("No client certificates found in the request", javax.ws.rs.core.Response.Status.FORBIDDEN);
        }
        
        X509Certificate certificate = registryClient.getCertificateKnownInEwpNetwork(certificates);
        if (certificate == null && !properties.isAllowMissingClientCertificate()) {
            throw new EwpSecWebApplicationException("None of the client certificates is valid in the EWP network", javax.ws.rs.core.Response.Status.FORBIDDEN);
        }
        
        requestContext.setProperty("EwpRequestCertificate", certificate);
    }

}