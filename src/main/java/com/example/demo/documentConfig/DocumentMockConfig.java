// package com.example.demo.documentConfig;
// import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
// import static com.github.tomakehurst.wiremock.client.WireMock.get;
// import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
// import org.springframework.context.annotation.Bean;
// import com.github.tomakehurst.wiremock.WireMockServer;

// public class DocumentMockConfig {

//         @Bean
//     public PdfResponseTransformer pdfResponseTransformer() {
//         return new PdfResponseTransformer();
//     }

//     public void registerStubs(WireMockServer wireMockServer) {
//         wireMockServer.stubFor(get(urlPathMatching("/api/v1/document/pdf/(.*)"))
//                 .willReturn(aResponse()
//                         .withTransformers("pdfResponseTransformer")));
//     }

// }
