package authorization.hashtranslator.controller;

import authorization.hashtranslator.dto.DecodeRequestDTO;
import authorization.hashtranslator.service.HashTranslatorService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HashTranslatorController {

    HashTranslatorService hashTranslatorService;

    @PostMapping
    public ResponseEntity<?> createDecodeRequest(@RequestBody DecodeRequestDTO decodeRequestDTO) {
        return hashTranslatorService.createDecodeRequest(decodeRequestDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDecryptedHashById(@PathVariable String id) {
        return hashTranslatorService.getDecryptedHashById(id);
    }
}
