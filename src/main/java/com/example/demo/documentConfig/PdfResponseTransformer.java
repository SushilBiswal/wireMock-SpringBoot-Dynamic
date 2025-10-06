package com.example.demo.documentConfig;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.*;
import java.io.File;
import java.nio.file.Files;

import org.springframework.stereotype.Component;

@Component
public class PdfResponseTransformer extends ResponseTransformer {

    @Override
    public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
        String[] parts = request.getUrl().split("/");
        String pdfId = parts[parts.length - 1];
        File pdfFile = new File("src/main/resources/__files/document/" + pdfId + ".jpg");

        try {
            if (pdfFile.exists()) {
                byte[] content = Files.readAllBytes(pdfFile.toPath());
                return Response.Builder.like(response)
                        .status(200)
                        .headers(new HttpHeaders(HttpHeader.httpHeader("Content-Type", "image/jpeg")))
                        .body(content)
                        .but()
                        .build();
            } else {
                String body = "{\"errorCode\":\"DOCUMENT_NOT_FOUND\",\"message\":\"Document with identifier "
                        + pdfId + " does not exist.\"}";
                return Response.Builder.like(response)
                        .status(404)
                        .headers(HttpHeaders.noHeaders())
                        .body(body)
                        .but()
                        .build();
            }
        } catch (Exception e) {
            return Response.Builder.like(response)
                    .status(500)
                    .headers(HttpHeaders.noHeaders())
                    .body("{\"errorCode\":\"INTERNAL_ERROR\",\"message\":\"Error reading document file\"}")
                    .but()
                    .build();
        }
    }

    @Override
    public String getName() {
        return "pdfResponseTransformer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
