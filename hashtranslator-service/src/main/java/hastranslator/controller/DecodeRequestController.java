package hastranslator.controller;

import hastranslator.dto.DecodeRequestDTO;
import hastranslator.service.DecodeRequestService;
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

//@RestController
//@RequestMapping("/api/applications")
//@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DecodeRequestController {

    DecodeRequestService decodeRequestService;

//    @PostMapping
//    public ResponseEntity<?> createDecodeRequest(@RequestBody DecodeRequestDTO decodeRequestDTO) {
//        return decodeRequestService.createDecodeRequest(decodeRequestDTO);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getDecodeRequestById(@PathVariable String id) {
//        return decodeRequestService.getDecodeRequestById(id);
//    }
}
