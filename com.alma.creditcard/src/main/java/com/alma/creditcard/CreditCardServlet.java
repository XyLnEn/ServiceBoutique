package com.alma.creditcard;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.*;
import java.io.*;

/**
 * Servlet exposant le webservice de validation de carte de crédit
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class CreditCardServlet extends HttpServlet {

    private static final String serverUri = "httpp///alma.creditcard.com";
    private static final String prefix = "creditcard";

    /**
     * Méthode utilitaire créant une enveloppe SOAP pour la réponse
     * @param number Le numéro de la carte de crédit validée
     * @param response Le message de validation
     * @return L'enveloppe SOAP
     * @throws SOAPException
     */
    private SOAPMessage buildSOAP(int number, String response) throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(prefix, serverUri);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement validationResponse = soapBody.addChildElement("ValidationResponse", prefix);

        SOAPElement cardNumber = validationResponse.addChildElement("cardNumber", prefix);
        cardNumber.addTextNode(String.valueOf(number));

        SOAPElement validationValue = validationResponse.addChildElement("validationValue", prefix);
        validationValue.addTextNode(response);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverUri  + "ValidationResponse");

        soapMessage.saveChanges();

        return soapMessage;
    }

    /**
     * Méthode utilitaire créant une enveloppe SOAP pour une erreur
     * @param message Le message d'erreur
     * @return L'enveloppe SOAP
     * @throws SOAPException
     */
    private SOAPMessage buildSOAPError(String message) throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(prefix, serverUri);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement validationResponse = soapBody.addChildElement("ValidationError", prefix);

        SOAPElement cardNumber = validationResponse.addChildElement("error", prefix);
        cardNumber.addTextNode(message);
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverUri  + "ValidationError");

        soapMessage.saveChanges();

        return soapMessage;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        CreditCardValidator validator = new CreditCardValidator();
        resp.setContentType("application/xml");
        PrintWriter out = resp.getWriter();
        try {
            if(req.getParameter("number") == null) {
                buildSOAPError("'number' parameter doesn't exist in URL").writeTo(stream);
            } else {
                int number = Integer.parseInt(req.getParameter("number"));
                buildSOAP(number, validator.validate(number)).writeTo(stream);
            }
        } catch(SOAPException e) {
            e.printStackTrace();
        }

        out.write(new String(stream.toByteArray(), "utf-8") );
    }
}
