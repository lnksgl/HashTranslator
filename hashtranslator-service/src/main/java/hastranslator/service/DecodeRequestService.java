package hastranslator.service;

import hastranslator.entity.DecodeRequest;
import hastranslator.exception.DecodeRequestNotFoundException;
import hastranslator.exception.FailedDecodeRequestException;
import hastranslator.repository.DecodeRequestRepository;
import hastranslator.utils.DecodeHashCustomApiImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DecodeRequestService {

    DecodeRequestRepository decodeRequestRepository;
    DecodeHashCustomApiImpl decodeHashCustomApiImpl;

    public String createDecodeRequest(String hashes) {
        DecodeRequest decodeRequest = decodeRequestRepository.save(
                DecodeRequest.builder()
                        .hashes(new ArrayList<>(Arrays.asList(hashes.split(" "))))
                        .build());

        CompletableFuture<DecodeRequest> task =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        return processRequest(decodeRequest);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        if (nonNull(decodeRequest.getId())) {
            return decodeRequest.getId();
        } else {
            throw new FailedDecodeRequestException();
        }
    }

    private DecodeRequest processRequest(DecodeRequest decodeRequest) throws IOException {
        decodeRequest.setDecrypted(decodeHashCustomApiImpl.decodeHashes(decodeRequest.getHashes()));
        return decodeRequestRepository.save(decodeRequest);
    }

    public DecodeRequest getDecodeRequestById(String id) {
        return decodeRequestRepository.findById(id).orElseThrow(() ->
                new DecodeRequestNotFoundException(id));
    }
}
