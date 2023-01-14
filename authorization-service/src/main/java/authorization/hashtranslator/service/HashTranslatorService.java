package authorization.hashtranslator.service;

import authorization.CreateRequestServiceOuterClass;
import authorization.GetDecryptedHashServiceOuterClass;
import authorization.hashtranslator.dto.DecodeRequestDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HashTranslatorService {

    GrpcServiceImpl grpcService;

    public ResponseEntity<?> createDecodeRequest(DecodeRequestDTO decodeRequestDTO) {
        if (validData(decodeRequestDTO.getHashes())) {
            return ResponseEntity.accepted().body(grpcService.createRequest(
                    CreateRequestServiceOuterClass.CreateRequest.newBuilder()
                            .setHashes(String.join(" ", decodeRequestDTO.getHashes()))
                            .build()
            ));
        } else {
            return ResponseEntity.badRequest().body("Invalid Data.");
        }

    }

    private boolean validData(ArrayList<String> elements) {
        for (String element : elements)
            if (element.length() != 32) return false;
        return true;
    }

    public ResponseEntity<?> getDecryptedHashById(String id) {
        return ResponseEntity.ok(grpcService.getDecryptedHash(
                GetDecryptedHashServiceOuterClass.GetDecryptedHashRequest.newBuilder()
                        .setId(id)
                        .build()
        ));
    }
}
