package gr.aueb.cf.taskapp.controllers;

import gr.aueb.cf.taskapp.service.PdfCreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/api"))
@RequiredArgsConstructor
public class PdfController {

    private final PdfCreatorService pdfService;


    @GetMapping("/export/pdf/{userId}")
    public ResponseEntity<byte[]> exportPdf(@PathVariable Long userId) {
        byte[] pdfBytes = pdfService.generatePdf(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=export.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
