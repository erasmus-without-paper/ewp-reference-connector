package eu.erasmuswithoutpaper.security;

import eu.erasmuswithoutpaper.api.client.auth.methods.cliauth.httpsig.CliauthHttpsig;
import eu.erasmuswithoutpaper.api.client.auth.methods.cliauth.none.CliauthAnonymous;
import eu.erasmuswithoutpaper.api.client.auth.methods.cliauth.tlscert.CliauthTlscert;
import eu.erasmuswithoutpaper.api.client.auth.methods.srvauth.httpsig.SrvauthHttpsig;
import eu.erasmuswithoutpaper.api.client.auth.methods.srvauth.tlscert.SrvauthTlscert;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.tomitribe.auth.signatures.*;

import eu.erasmuswithoutpaper.common.control.EwpKeyStore;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.error.control.EwpSecWebApplicationException;
import eu.erasmuswithoutpaper.error.control.EwpSecWebApplicationException.AuthMethod;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.UUID;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class HttpSignature {

    private static final Logger logger = LoggerFactory.getLogger(HttpSignature.class);

    @Inject
    EwpKeyStore keystoreController;

    @Inject
    GlobalProperties properties;

    @Inject
    RegistryClient registryClient;

    public boolean adaptsToHttpSignatureRequest(ContainerRequestContext requestContext) {
        return requestContext.getHeaders().getFirst("authorization") != null;
    }
    
    public void signResponse(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        MultivaluedMap<String, String> reqHeaders = requestContext.getHeaders();
        try {
            String requestID = reqHeaders.getFirst("X-Request-Id");
            String requestAuthorization = reqHeaders.getFirst("Authorization");
            Signature reqSig = null;
            if (requestAuthorization != null) {
                reqSig = Signature.fromString(requestAuthorization);
            }

            final Date today = new Date();
            final String stringToday = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US)
                    .format(today);

            byte[] bodyBytes = getByteArray(responseContext);
            final byte[] digest = MessageDigest.getInstance("SHA-256").digest(bodyBytes);
            final String digestHeader = "SHA-256=" + new String(Base64.encodeBase64(digest));

            List<String> headerNames = new ArrayList<>();
            final Map<String, String> headers = new HashMap<>();

            headers.put("Original-Date", stringToday);
            headers.put("Digest", digestHeader);

            if (requestID != null) {
                headers.put("X-Request-Id", requestID);
            }
            if (reqSig != null) {
                headers.put("X-Request-Signature", reqSig.getSignature());
            }

            // Update the other header lists as well
            headers.keySet().forEach((header) -> {
                String headerValue = headers.get(header);
                headerNames.add(header);
                responseContext.getHeaders().add(header, headerValue);
            });

            final Signature signature = new Signature(keystoreController.getOwnPublicKeyFingerprint(), "rsa-sha256",
                    null,
                    headerNames);
            final Key key = keystoreController.getOwnPrivateKey();

            final Signer signer = new Signer(key, signature);
            Signature signed;
            signed = signer.sign("", "", headers);

            responseContext.getHeaders().add("Authorization", signed.toString());
        } catch (IOException | NoSuchAlgorithmException e) {
            logger.error("Can't sign response", e);
        }
    }

    public void signRequest(Invocation.Builder request) {
        signRequest(request, "");
    }
    public void signRequest(Invocation.Builder request, String formData) {
        try {
            final Map<String, String> headers = new HashMap<>();
            
            String requestID = UUID.randomUUID().toString();
            headers.put("X-Request-Id", requestID);

            final Date today = new Date();
            final String stringToday = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US)
                    .format(today);
            headers.put("X-Request-Date", stringToday);

            byte[] bodyBytes = formData.getBytes();
            final byte[] digest = MessageDigest.getInstance("SHA-256").digest(bodyBytes);
            final String digestHeader = "SHA-256=" + new String(Base64.encodeBase64(digest));
            headers.put("Digest", digestHeader);

//            new Signature()
            //headers.put("X-Request-Signature", null);  // TODO

            List<String> headerNames = new ArrayList<>();
            headers.entrySet().forEach((header) -> {
                headerNames.add(header.getKey());
                request.header(header.getKey(), header.getValue());
            });

            final Signature signature = new Signature(keystoreController.getOwnPublicKeyFingerprint(), "rsa-sha256",
                    null,
                    headerNames);
            final Key key = keystoreController.getOwnPrivateKey();

            final Signer signer = new Signer(key, signature);
            Signature signed;
            signed = signer.sign("", "", headers);

            request.header("Authorization", signed.toString());
        } catch (IOException | NoSuchAlgorithmException e) {
            logger.error("Can't sign response", e);
        }
    }
    
    public void verifyHttpSignatureRequest(ContainerRequestContext requestContext) throws EwpSecWebApplicationException {
        MultivaluedMap<String, String> reqHeaders = requestContext.getHeaders();
        String authorization = reqHeaders.getFirst("authorization");
        logger.info("Verifying HTTP signature");

        Map<String, String> headers = new HashMap<>();
        reqHeaders.keySet().forEach((hkey) -> {
            headers.put(hkey.toLowerCase(), reqHeaders.getFirst(hkey));
        });

        Signature signature = Signature.fromString(authorization);
        logger.info("Signature: " + signature);

        try {
            if (headers.containsKey("digest")) {
                byte[] bodyBytes = getByteArray(requestContext);
                final byte[] digest;
                try {
                    digest = MessageDigest.getInstance("SHA-256").digest(bodyBytes);
                } catch (NoSuchAlgorithmException e) {
                    logger.warn("No such algorithm", e);
                    throw new EwpSecWebApplicationException("No such algorithm", javax.ws.rs.core.Response.Status.FORBIDDEN, AuthMethod.HTTPSIG);
                }
                final String digestCalculated = "SHA-256=" + new String(Base64.encodeBase64(digest));

                if (!digestCalculated.equals(reqHeaders.getFirst("digest"))) {
                    throw new EwpSecWebApplicationException("Digest mismatch! calculated (body length: " + bodyBytes.length + "): " + digestCalculated
                            + ", header: " + reqHeaders.getFirst("digest"), javax.ws.rs.core.Response.Status.FORBIDDEN, AuthMethod.HTTPSIG);
                }
            }

            String fingerprint = signature.getKeyId();
            RSAPublicKey publicKey = registryClient.findRsaPublicKey(fingerprint);
            if (publicKey == null) {
                throw new EwpSecWebApplicationException("Key not found for fingerprint: " + fingerprint, javax.ws.rs.core.Response.Status.FORBIDDEN, AuthMethod.HTTPSIG);
            }

            final Verifier verifier = new Verifier(publicKey, signature);

            logger.info("Verifying signature, fingerprint: {}", fingerprint);

            boolean verifies = verifier.verify(requestContext.getMethod().toLowerCase(), requestContext.getUriInfo().getRequestUri().toString(), headers);

            if (!verifies) {
                throw new EwpSecWebApplicationException("Signature verification: " + verifies, javax.ws.rs.core.Response.Status.FORBIDDEN, AuthMethod.HTTPSIG);
            }

            requestContext.setProperty("EwpRequestRSAPublicKey", publicKey);
        } catch (NoSuchAlgorithmException e) {
            logger.warn("No such algorithm", e);
            throw new EwpSecWebApplicationException("No such algorithm", javax.ws.rs.core.Response.Status.FORBIDDEN, AuthMethod.HTTPSIG);
        } catch (IOException e) {
            logger.warn("Error reading", e);
            throw new EwpSecWebApplicationException(e.getMessage(), javax.ws.rs.core.Response.Status.FORBIDDEN, AuthMethod.HTTPSIG);
        } catch (SignatureException e) {
            logger.warn("Signature error", e);
            throw new EwpSecWebApplicationException(e.getMessage(), javax.ws.rs.core.Response.Status.FORBIDDEN, AuthMethod.HTTPSIG);
        }
    }
    
    public void verifyHttpSignatureResponse(String method, String requestUri, MultivaluedMap<String, Object> reqHeaders, String raw) {
        String authorization = (String)reqHeaders.getFirst("authorization");
        logger.info("Verifying HTTP signature");

        Map<String, String> headers = new HashMap<>();
        reqHeaders.keySet().forEach((hkey) -> {
            headers.put(hkey.toLowerCase(), (String)reqHeaders.getFirst(hkey));
        });

        Signature signature = Signature.fromString(authorization);
        logger.info("Signature: " + signature);

        try {
            if (headers.containsKey("digest")) {
                byte[] bodyBytes = raw.getBytes();
                final byte[] digest;
                try {
                    digest = MessageDigest.getInstance("SHA-256").digest(bodyBytes);
                } catch (NoSuchAlgorithmException e) {
                    logger.warn("No such algorithm", e);
                    throw new EwpSecWebApplicationException("No such algorithm", javax.ws.rs.core.Response.Status.FORBIDDEN, AuthMethod.HTTPSIG);
                }
                final String digestCalculated = "SHA-256=" + new String(Base64.encodeBase64(digest));

                if (!digestCalculated.equals(reqHeaders.getFirst("digest"))) {
                    throw new EwpSecWebApplicationException("Digest mismatch! calculated (body length: " + bodyBytes.length + "): " + digestCalculated
                            + ", header: " + reqHeaders.getFirst("digest"), javax.ws.rs.core.Response.Status.FORBIDDEN, AuthMethod.HTTPSIG);
                }
            }

            String fingerprint = signature.getKeyId();
            RSAPublicKey publicKey = registryClient.findRsaPublicKey(fingerprint);
            if (publicKey == null) {
                throw new EwpSecWebApplicationException("Key not found for fingerprint: " + fingerprint, javax.ws.rs.core.Response.Status.FORBIDDEN, AuthMethod.HTTPSIG);
            }

            final Verifier verifier = new Verifier(publicKey, signature);

            logger.info("Verifying signature, fingerprint: {}", fingerprint);

            boolean verifies = verifier.verify(method.toLowerCase(), requestUri, headers);

            if (!verifies) {
                throw new EwpSecWebApplicationException("Signature verification: " + verifies, javax.ws.rs.core.Response.Status.FORBIDDEN, AuthMethod.HTTPSIG);
            }
        } catch (NoSuchAlgorithmException e) {
            logger.warn("No such algorithm", e);
            throw new EwpSecWebApplicationException("No such algorithm", javax.ws.rs.core.Response.Status.FORBIDDEN, AuthMethod.HTTPSIG);
        } catch (IOException e) {
            logger.warn("Error reading", e);
            throw new EwpSecWebApplicationException(e.getMessage(), javax.ws.rs.core.Response.Status.FORBIDDEN, AuthMethod.HTTPSIG);
        } catch (SignatureException e) {
            logger.warn("Signature error", e);
            throw new EwpSecWebApplicationException(e.getMessage(), javax.ws.rs.core.Response.Status.FORBIDDEN, AuthMethod.HTTPSIG);
        }
    }

    private byte[] getByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream buffer;
        byte[] bodyBytes;
        buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }   buffer.flush();
        bodyBytes = buffer.toByteArray();
        buffer.close();
        
        return bodyBytes;
    }
    private byte[] getByteArray(ContainerRequestContext requestContext) throws IOException {
        ByteArrayOutputStream buffer;
        byte[] bodyBytes;
        try (InputStream is = requestContext.getEntityStream()) {
            bodyBytes = getByteArray(is);
        }
        
        requestContext.setEntityStream(new ByteArrayInputStream(bodyBytes));
        return bodyBytes;
    }

    private byte[] getByteArray(ContainerResponseContext responseContext) throws IOException {
        try {
            byte[] bodyBytes;
            JAXBContext jaxbContext = JAXBContext.newInstance(
                    responseContext.getEntityClass(),
                    CliauthTlscert.class,
                    CliauthAnonymous.class,
                    CliauthHttpsig.class,
                    SrvauthHttpsig.class,
                    SrvauthTlscert.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                marshaller.marshal(responseContext.getEntity(), baos);
                bodyBytes = baos.toByteArray();
                responseContext.setEntity(bodyBytes);
            }
            return bodyBytes;
        }   catch (PropertyException ex) {
            logger.error("Property error", ex);
        }   catch (JAXBException ex) {
            logger.error("Jaxb error", ex);
        }
        return new byte[0];
    }
}
