package com.example.demo.EmployeeMockUrls;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class EmployeeResponseTransformer extends ResponseTransformer {

    @Override
    public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
        // String[] parts = request.getUrl().split("/");
        // String employeeId = parts[parts.length - 1];
        // File employeeFile = new File("src/main/resources/__files/employee-" + employeeId + ".json");

        // try {
        //     if (employeeFile.exists()) {
        //         String body = new String(Files.readAllBytes(employeeFile.toPath()));
        //         return Response.Builder.like(response)
        //                 .status(200)
        //                 .headers(HttpHeaders.noHeaders())
        //                 .body(body)
        //                 .but()
        //                 .build();
        

        // try {
        //     // Extract employeeId from the URL
        //     String[] parts = request.getUrl().split("/");
        //     String employeeId = parts[parts.length - 1];

        //     // Define the file path in classpath
        //     String path = "__files/" + employeeId + ".json";
        //     ClassPathResource resource = new ClassPathResource(path);

        //     if (resource.exists()) {
        //         // ✅ Read file content
        //         byte[] content = Files.readAllBytes(resource.getFile().toPath());

        //         return Response.Builder.like(response)
        //                 .status(200)
        //                 .body(content)
        //                 .build();

         try {
            // Extract employeeId from URL
            String[] parts = request.getUrl().split("/");
            String employeeId = parts[parts.length - 1];

            // Define file path inside classpath
            String path = "__files/" + employeeId + ".json";
            ClassPathResource resource = new ClassPathResource(path);

            if (resource.exists()) {
                // ✅ Read directly from InputStream (works inside JAR/Docker too)
                try (InputStream is = resource.getInputStream()) {
                    byte[] content = is.readAllBytes();
                    return Response.Builder.like(response)
                            .status(200)
                            .body(content)
                            .build();
                }
            } else {
                String body = "{\"errorCode\":\"EMPLOYEE_NOT_FOUND\",\"message\":\"Employee with id "
                        + employeeId + " not found.\"}";
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
                    .body("{\"errorCode\":\"INTERNAL_ERROR\",\"message\":\"Error reading employee file\"}")
                    .but()
                    .build();
        }
    }

    @Override
    public String getName() {
        return "employeeResponseTransformer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}

