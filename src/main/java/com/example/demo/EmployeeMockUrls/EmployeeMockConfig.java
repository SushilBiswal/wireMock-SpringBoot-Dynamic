// package com.example.demo.EmployeeMockUrls;
// import com.github.tomakehurst.wiremock.WireMockServer;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import static com.github.tomakehurst.wiremock.client.WireMock.*;

// @Configuration
// public class EmployeeMockConfig {

//     @Bean
//     public EmployeeResponseTransformer employeeResponseTransformer() {
//         return new EmployeeResponseTransformer();
//     }

//     public void registerStubs(WireMockServer wireMockServer) {
//         wireMockServer.stubFor(get(urlPathMatching("/api/v1/employee/(.*)"))
//                 .willReturn(aResponse()
//                         .withTransformers("employeeResponseTransformer")));
//     }

// }
