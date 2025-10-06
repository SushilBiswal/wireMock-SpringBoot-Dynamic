// package com.example.demo.config;

// import com.github.tomakehurst.wiremock.WireMockServer;
// import com.github.tomakehurst.wiremock.client.WireMock;
// import com.github.tomakehurst.wiremock.extension.Parameters;
// import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
// import com.github.tomakehurst.wiremock.http.*;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import java.io.File;
// import java.nio.file.Files;

// import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

// @Configuration
// public class WireMockConfig {

//     @Bean(initMethod = "start", destroyMethod = "stop")
//     public WireMockServer wireMockServer() {
//         WireMockServer server = new WireMockServer(
//                 options().port(8089).extensions(new EmployeeResponseTransformer())
//         );

//         server.stubFor(WireMock.get(WireMock.urlMatching("/api/v1/employee/.*"))
//                 .willReturn(WireMock.aResponse()
//                         .withTransformers("employee-transformer")));

//         return server;
//     }

//     public static class EmployeeResponseTransformer extends ResponseTransformer {

//         @Override
//         public Response transform(Request request, Response response,
//                                   com.github.tomakehurst.wiremock.common.FileSource files,
//                                   Parameters parameters) {

//             try {
//                 String path = request.getUrl(); // e.g., /api/v1/employee/101
//                 String[] parts = path.split("/");
//                 String empId = parts[parts.length - 1];
//                 File empFile = new File("src/main/resources/__files/employee-" + empId + ".json");

//                 if (empFile.exists()) {
//                     String json = new String(Files.readAllBytes(empFile.toPath()));
//                     return Response.response()
//                             .status(200)
//                             .body(json)
//                             .headers(HttpHeaders.noHeaders())
//                             .build();
//                 } else {
//                     String notFound = String.format(
//                             "{\"errorCode\":\"EMPLOYEE_NOT_FOUND\",\"message\":\"Employee with id %s not found.\"}",
//                             empId
//                     );
//                     return Response.response()
//                             .status(404)
//                             .body(notFound)
//                             .headers(HttpHeaders.noHeaders())
//                             .build();
//                 }
//             } catch (Exception e) {
//                 return Response.response()
//                         .status(500)
//                         .body("{\"error\":\"Internal error occurred.\"}")
//                         .headers(HttpHeaders.noHeaders())
//                         .build();
//             }
//         }

//         @Override
//         public String getName() {
//             return "employee-transformer";
//         }
//     }
// }
