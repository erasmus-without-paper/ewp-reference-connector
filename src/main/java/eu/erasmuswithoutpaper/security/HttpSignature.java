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
import java.net.URI;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
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

    public boolean clientWantsSignedResponse(ContainerRequestContext requestContext) {
        // Check if client wants signed response and that the header is correct
        return requestContext.getHeaders().containsKey("accept-signature") &&
                Arrays.stream(requestContext.getHeaders().getFirst("accept-signature").split(",\\s?"))
                        .anyMatch(m -> "rsa-sha256".equalsIgnoreCase(m));
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

            responseContext.getHeaders().add("Signature", signed.toString().replace("Signature ", ""));
        } catch (IOException | NoSuchAlgorithmException e) {
            logger.error("Can't sign response", e);
        }
    }

    public void signRequest(String method, URI uri, Invocation.Builder request, String requestId) {
        signRequest(method, uri, request, "", requestId);
    }
    public void signRequest(String method, URI uri, Invocation.Builder request, String formData, String requestId) {
        try {
            final Map<String, String> headers = new HashMap<>();
            
            headers.put("X-Request-Id", requestId);

            final Date today = new Date();
            final String stringToday = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US)
                    .format(today);
            headers.put("Original-Date", stringToday);
            
            headers.put("host", uri.getHost());

            byte[] bodyBytes = formData.getBytes();
            final byte[] digest = MessageDigest.getInstance("SHA-256").digest(bodyBytes);
            final String digestHeader = "SHA-256=" + new String(Base64.encodeBase64(digest));
            headers.put("Digest", digestHeader);

            List<String> headerNames = new ArrayList<>();
            headerNames.add("(request-target)");
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
            String queryParams = uri.getQuery() == null ? "" : "?" + uri.getQuery();
            signed = signer.sign(method, uri.getPath() + queryParams , headers);

            request.header("Authorization", signed.toString());
            
            // Want signed response
            request.header("Accept-Signature", "RSA-SHA256");
        } catch (IOException | NoSuchAlgorithmException e) {
            logger.error("Can't sign response", e);
        }
    }
    
    public AuthenticateMethodResponse verifyHttpSignatureRequest(ContainerRequestContext requestContext) {
        MultivaluedMap<String, String> reqHeaders = requestContext.getHeaders();
        String authorization = reqHeaders.getFirst("authorization");
        logger.info("Verifying HTTP signature");
        
        if (authorization == null || !authorization.toLowerCase().startsWith("signature")) {
            return AuthenticateMethodResponse.builder()
                    .withRequiredMethodInfoFulfilled(false)
                    .withResponseCode(javax.ws.rs.core.Response.Status.UNAUTHORIZED)
                    .build();
        }
        Signature signature = Signature.fromString(authorization);
        logger.info("Signature: " + signature);
        if (signature.getAlgorithm() != Algorithm.RSA_SHA256) {
            return AuthenticateMethodResponse.builder()
                    .withRequiredMethodInfoFulfilled(false)
                    .withErrorMessage("Only signature algorithm rsa-sha256 is supported.")
                    .withResponseCode(javax.ws.rs.core.Response.Status.UNAUTHORIZED)
                    .build();
        }

        Map<String, String> headers = new HashMap<>();
        reqHeaders.entrySet().forEach((entry) -> {
            headers.put(entry.getKey().toLowerCase(), String.join(", ", entry.getValue()));
        });
        Optional<AuthenticateMethodResponse> authenticateMethodResponse = checkRequiredSignedHeaders(signature, "(request-target)", "host", "date|original-date", "digest", "x-request-id");
        if (authenticateMethodResponse.isPresent()) {
            return authenticateMethodResponse.get();
        }
        
        if (headers.containsKey("host") &&
                !headers.get("host").equals(requestContext.getUriInfo().getRequestUri().getHost())) {
            return AuthenticateMethodResponse.builder()
                    .withErrorMessage("Host does not match")
                    .withResponseCode(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .build();
        }
        
        if (headers.containsKey("x-request-id") &&
                !headers.get("x-request-id").matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")) {
            return AuthenticateMethodResponse.builder()
                    .withErrorMessage("Authentication with non-canonical X-Request-ID")
                    .withResponseCode(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .build();
        }

        if ((headers.containsKey("date") && !isDateWithinTimeThreshold(headers.get("date"))) ||
                (headers.containsKey("original-date") && !isDateWithinTimeThreshold(headers.get("original-date")))) {
            return AuthenticateMethodResponse.builder()
                    .withErrorMessage("The date cannot be parsed or the date does not match your server clock within a certain threshold of timeDate.")
                    .withResponseCode(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .build();
        }
        
        try {
            if (headers.containsKey("digest")) {
                byte[] bodyBytes = getByteArray(requestContext);
                final byte[] digest;
                try {
                    digest = MessageDigest.getInstance("SHA-256").digest(bodyBytes);
                } catch (NoSuchAlgorithmException e) {
                    logger.warn("No such algorithm", e);
                    return AuthenticateMethodResponse.builder()
                            .withErrorMessage("No such algorithm")
                            .withResponseCode(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                            .build();
                }
                final String digestCalculated = "SHA-256=" + new String(Base64.encodeBase64(digest));

                String requestDigest = null;
                if (reqHeaders.containsKey("digest")) {
                    requestDigest = Arrays
                            .stream(reqHeaders.getFirst("digest").split(","))
                            .filter(d -> d.startsWith("SHA-256=")).findFirst().orElse(null);
                }
                if (!digestCalculated.equals(requestDigest)) {
                    return AuthenticateMethodResponse.builder()
                            .withErrorMessage("Digest mismatch! calculated (body length: " + bodyBytes.length + "): " + digestCalculated
                                                + ", header: " + reqHeaders.getFirst("digest"))
                            .withResponseCode(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                            .build();
                }
            }

            String fingerprint = signature.getKeyId();
            RSAPublicKey publicKey = registryClient.findClientRsaPublicKey(fingerprint);
            if (publicKey == null) {
                return AuthenticateMethodResponse.builder()
                        .withErrorMessage("Key not found for fingerprint: " + fingerprint)
                        .withResponseCode(javax.ws.rs.core.Response.Status.FORBIDDEN)
                        .build();
            }

            final Verifier verifier = new Verifier(publicKey, signature);

            logger.info("Verifying signature, fingerprint: {}", fingerprint);

            String queryParams = requestContext.getUriInfo().getRequestUri().getQuery();
            String requestString = requestContext.getUriInfo().getRequestUri().getRawPath() + 
                    (queryParams == null ? "" : "?" + queryParams);
            boolean verifies = verifier.verify(requestContext.getMethod().toLowerCase(), requestString, headers);

            if (!verifies) {
                return AuthenticateMethodResponse.builder()
                        .withErrorMessage("Signature verification: " + verifies)
                        .withResponseCode(javax.ws.rs.core.Response.Status.UNAUTHORIZED)
                        .build();
            }

            requestContext.setProperty("EwpRequestRSAPublicKey", publicKey);
        } catch (NoSuchAlgorithmException e) {
            logger.warn("No such algorithm", e);
            return AuthenticateMethodResponse.builder()
                    .withErrorMessage("No such algorithm")
                    .withResponseCode(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .build();
        } catch (IOException e) {
            logger.warn("Error reading", e);
            return AuthenticateMethodResponse.builder()
                    .withErrorMessage(e.getMessage())
                    .withResponseCode(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .build();
        } catch (SignatureException e) {
            logger.warn("Signature error", e);
            return AuthenticateMethodResponse.builder()
                    .withErrorMessage(e.getMessage())
                    .withResponseCode(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .build();
        } catch (MissingRequiredHeaderException e) {
            logger.warn("Signature error, missing header", e);
            return AuthenticateMethodResponse.builder()
                    .withErrorMessage(e.getMessage())
                    .withResponseCode(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                    .build();
        }

        return AuthenticateMethodResponse.builder().build();
    }
    
    public String verifyHttpSignatureResponse(String method, String requestUri, MultivaluedMap<String, Object> responseHeaders, String raw, String requestID) {
        if (!requestID.equals(responseHeaders.getFirst("X-Request-Id"))) {
            return "Header X-Request-Id does not match the id sent in the request";
        }
        
        if (!responseHeaders.containsKey("signature")) {
            return "Missing Signature header in response";
        }
        String signatureHeader = (String)responseHeaders.getFirst("signature");
        logger.info("Verifying HTTP signature");

        Signature signature = Signature.fromString(signatureHeader);
        logger.info("Signature: " + signature);
        if (signature.getAlgorithm() != Algorithm.RSA_SHA256) {
            return "Only signature algorithm rsa-sha256 is supported.";
        }
        
        Optional<AuthenticateMethodResponse> authenticateMethodResponse = checkRequiredSignedHeaders(signature, "date|original-date", "digest", "x-request-id", "x-request-signature");
        if (authenticateMethodResponse.isPresent()) {
            return authenticateMethodResponse.get().errorMessage();
        }
        
        Map<String, String> headers = new HashMap<>();
        responseHeaders.keySet().forEach((hkey) -> {
            headers.put(hkey.toLowerCase(), (String)responseHeaders.getFirst(hkey));
        });

        if ((headers.containsKey("date") && !isDateWithinTimeThreshold(headers.get("date"))) ||
                (headers.containsKey("original-date") && !isDateWithinTimeThreshold(headers.get("original-date")))) {
            return "The date cannot be parsed or the date does not match your server clock within a certain threshold of timeDate.";
        }
        
        try {
            if (headers.containsKey("digest")) {
                byte[] bodyBytes = raw.getBytes();
                final byte[] digest;
                try {
                    digest = MessageDigest.getInstance("SHA-256").digest(bodyBytes);
                } catch (NoSuchAlgorithmException e) {
                    logger.warn("No such algorithm", e);
                    return "No such algorithm";
                }
                final String digestCalculated = "SHA-256=" + new String(Base64.encodeBase64(digest));

                if (!digestCalculated.equals(responseHeaders.getFirst("digest"))) {
                    return "Digest mismatch! calculated (body length: " + bodyBytes.length + "): " + digestCalculated
                            + ", header: " + responseHeaders.getFirst("digest");
                }
            }

            String fingerprint = signature.getKeyId();
            RSAPublicKey publicKey = registryClient.findRsaPublicKey(fingerprint);
            if (publicKey == null) {
                return "Key not found for fingerprint: " + fingerprint;
            }

            final Verifier verifier = new Verifier(publicKey, signature);

            logger.debug("Verifying signature, fingerprint: {}", fingerprint);

            boolean verifies = verifier.verify(method.toLowerCase(), requestUri, headers);

            if (!verifies) {
                return "Signature verification: " + verifies;
            }
        } catch (NoSuchAlgorithmException e) {
            logger.warn("No such algorithm", e);
            return "No such algorithm: " + e.getMessage();
        } catch (IOException e) {
            logger.warn("Error reading", e);
            return e.getMessage();
        } catch (SignatureException e) {
            logger.warn("Signature error", e);
            return e.getMessage();
        } catch (MissingRequiredHeaderException e) {
            logger.warn("Signature error, missing header", e);
            return e.getMessage();
        }
        return null;
    }
    
    private Optional<AuthenticateMethodResponse> checkRequiredSignedHeaders(Signature signature, String... headers) {
        return Arrays.stream(headers).map(header -> {
            if (Arrays.stream(header.split("\\|")).noneMatch(h -> signature.getHeaders().contains(h))) {
                return AuthenticateMethodResponse.builder()
                        .withRequiredMethodInfoFulfilled(false)
                        .withErrorMessage("Missing required signed header '" + header + "'")
                        .withResponseCode(javax.ws.rs.core.Response.Status.BAD_REQUEST)
                        .build();
            } else {
                return null;
            }
        }).filter(Objects::nonNull).findFirst();
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

    private boolean isDateWithinTimeThreshold(String dateString) {
        final Date today = new Date();
        try {
            final Date requestDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US)
                    .parse(dateString);
            // Check that time diff is less than five minutes
            return Math.abs(today.getTime() - requestDate.getTime()) <= 5 * 60 * 1000;
        } catch (ParseException e) {
            logger.warn("Can't parse date: " + dateString, e);
        }
        return false;
    }
}
