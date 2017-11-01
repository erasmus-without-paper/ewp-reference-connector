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

        if (method == null || method.getAnnotation(EwpAuthenticate.class) == null) {
            return;
        }
        
        AuthenticateMethodResponse httpSignatureVerifyResponse = httpSignature.verifyHttpSignatureRequest(requestContext);
        if (httpSignatureVerifyResponse.isRequiredMethodInfoFulfilled()) {
            if (!httpSignatureVerifyResponse.isVerifiedOk()) {
                throw new EwpSecWebApplicationException(httpSignatureVerifyResponse.errorMessage(), httpSignatureVerifyResponse.status(), EwpSecWebApplicationException.AuthMethod.HTTPSIG);
            }
            return;
        }
        
        AuthenticateMethodResponse tlsCertVerifyResponse = verifyX509CertificateRequest(requestContext);
        if (tlsCertVerifyResponse.isRequiredMethodInfoFulfilled()) {
            if (!tlsCertVerifyResponse.isVerifiedOk()) {
                throw new EwpSecWebApplicationException(tlsCertVerifyResponse.errorMessage(), tlsCertVerifyResponse.status());
            }
            return;
        }
        
        throw new EwpSecWebApplicationException(httpSignatureVerifyResponse.hasErrorMessage() ? httpSignatureVerifyResponse.errorMessage() : "No authorization method found in the request", httpSignatureVerifyResponse.status());
    }
    
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        if (httpSignature.clientWantsSignedResponse(requestContext)) {
            httpSignature.signResponse(requestContext, responseContext);
        }

        if (responseContext.getStatus() == javax.ws.rs.core.Response.Status.UNAUTHORIZED.getStatusCode()) {
            responseContext.getHeaders().add("WWW-Authenticate", "Signature realm=\"EWP\"");
            responseContext.getHeaders().add("Want-Digest", "SHA-256");
        }
    }
    
    private AuthenticateMethodResponse verifyX509CertificateRequest(ContainerRequestContext requestContext) throws EwpSecWebApplicationException {
        X509Certificate[] certificates = (X509Certificate[]) requestContext.getProperty("javax.servlet.request.X509Certificate");
        logger.info("Verifying Client certificate");
        if (certificates == null && !properties.isAllowMissingClientCertificate()) {
            return AuthenticateMethodResponse.builder()
                    .withRequiredMethodInfoFulfilled(false)
                    .withResponseCode(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .build();
        }
        
        X509Certificate certificate = registryClient.getCertificateKnownInEwpNetwork(certificates);
        if (certificate == null && !properties.isAllowMissingClientCertificate()) {
            return AuthenticateMethodResponse.builder()
                    .withErrorMessage("None of the client certificates is valid in the EWP network")
                    .withResponseCode(javax.ws.rs.core.Response.Status.UNAUTHORIZED)
                    .build();
        }
        
        requestContext.setProperty("EwpRequestCertificate", certificate);
            return AuthenticateMethodResponse.builder().build();
        
    }

}