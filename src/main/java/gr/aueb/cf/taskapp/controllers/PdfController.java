package gr.aueb.cf.taskapp.controllers;

import gr.aueb.cf.taskapp.service.PdfCreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/api"))
@RequiredArgsConstructor
public class PdfController {

    private final PdfCreatorService pdfService;


    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPdf(@RequestParam String text) {
        byte[] pdfBytes = pdfService.generatePdf(text);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=export.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
