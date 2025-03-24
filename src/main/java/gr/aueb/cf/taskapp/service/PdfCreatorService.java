package gr.aueb.cf.taskapp.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import gr.aueb.cf.taskapp.core.exceptions.UserNotFoundException;
import gr.aueb.cf.taskapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.taskapp.mapper.Mapper;
import gr.aueb.cf.taskapp.model.User;
import gr.aueb.cf.taskapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfCreatorService {

    private final UserRepository userRepository;
    private final Mapper mapper;

    public PdfCreatorService(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public byte[] generatePdf(Long userId) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found" + userId));
            UserReadOnlyDTO userReadOnlyDTO = mapper.mapToUserReadOnlyDTO(user);
            var tasksToReturn = userReadOnlyDTO.getTasks();
            String formatedText = " ";
            for (var task : tasksToReturn) {
                formatedText += "\n" + task + "\n";
            }


            // Προσθήκη περιεχομένου
            document.add(new Paragraph(formatedText.toString()));
            System.out.println(formatedText.toString());

            document.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}
