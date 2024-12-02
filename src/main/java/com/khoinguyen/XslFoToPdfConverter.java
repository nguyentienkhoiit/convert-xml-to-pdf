package com.khoinguyen;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.apps.FOUserAgent;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class XslFoToPdfConverter {

    public static void main(String[] args) {
        try {
            // Setup FOP factory
            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());

            // Setup output stream
            File pdfFile = new File("output.pdf");
            OutputStream out = new FileOutputStream(pdfFile);

            // Setup FOP with desired output format
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Setup XSLT transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(new InputStreamReader(new FileInputStream("input-test-1.xml"), StandardCharsets.UTF_8)));

            // Setup input stream for the XML file
            StreamSource src = new StreamSource(new FileInputStream("data-test.xml"), StandardCharsets.UTF_8.name());

            // Resulting SAX events (the generated FO) must be piped through to FOP
            SAXResult res = new SAXResult(fop.getDefaultHandler());

            // Start the transformation and rendering process
            transformer.transform(src, res);

            // Clean-up
            out.close();

            System.out.println("PDF created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}