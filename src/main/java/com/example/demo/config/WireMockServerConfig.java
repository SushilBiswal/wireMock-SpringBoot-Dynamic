package com.example.demo.config;

import com.example.demo.EmployeeMockUrls.EmployeeResponseTransformer;
import com.example.demo.documentConfig.PdfResponseTransformer;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

// import com.example.demo.EmployeeMockUrls.EmployeeResponseTransformer;
// import com.example.demo.documentConfig.PdfResponseTransformer;
// import com.github.tomakehurst.wiremock.WireMockServer;
// import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

// import org.springframework.context.annotation.Bean;
// import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
// import static com.github.tomakehurst.wiremock.client.WireMock.get;
// import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
// import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Configuration
public class WireMockServerConfig {
    @Bean(destroyMethod = "stop")
    public WireMockServer wireMockServer(EmployeeResponseTransformer employeeTransformer,
                                         PdfResponseTransformer pdfTransformer) {

        WireMockServer server = new WireMockServer(options().port(8089)
                .extensions(employeeTransformer, pdfTransformer));

        // Employee mock
        server.stubFor(get(urlPathMatching("/api/v1/employee/(.*)"))
                .willReturn(aResponse().withTransformers(employeeTransformer.getName())));

        // Document mock
        server.stubFor(get(urlPathMatching("/api/v1/document/pdf/(.*)"))
                .willReturn(aResponse().withTransformers(pdfTransformer.getName())));

        // ✅ Start manually so it's guaranteed to bind to port
        server.start();
        System.out.println("✅ WireMock started on http://localhost:8089");
        return server;
    }

}
