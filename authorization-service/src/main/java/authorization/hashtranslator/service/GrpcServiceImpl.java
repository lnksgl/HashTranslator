package authorization.hashtranslator.service;

import authorization.CreateRequestServiceGrpc;
import authorization.CreateRequestServiceOuterClass;
import authorization.GetDecryptedHashServiceGrpc;
import authorization.GetDecryptedHashServiceOuterClass;
import authorization.hashtranslator.dto.DecryptedHashDTO;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class GrpcServiceImpl {

    @GrpcClient("hash-translator")
    CreateRequestServiceGrpc.CreateRequestServiceBlockingStub createRequestService;
    @GrpcClient("hash-translator")
    GetDecryptedHashServiceGrpc.GetDecryptedHashServiceBlockingStub getDecryptedHashService;

    public String createRequest(CreateRequestServiceOuterClass.CreateRequest request) {
        return createRequestService.createRequest(request).getId();
    }

    public DecryptedHashDTO getDecryptedHash(
            GetDecryptedHashServiceOuterClass.GetDecryptedHashRequest request) {
        GetDecryptedHashServiceOuterClass.GetDecryptedHashResponse response =
                getDecryptedHashService.getDecodeHash(request);

        return DecryptedHashDTO.builder()
                .id(response.getId())
                .hashes(generateMap(response))
                .build();
    }

    private Map<String, String> generateMap(
            GetDecryptedHashServiceOuterClass.GetDecryptedHashResponse response) {
        Map<String, String> map = new HashMap<>();
        ArrayList<String> hashes = stringToArray(response.getHashes());
        ArrayList<String> decrypted = stringToArray(response.getDecrypted());

        if (decrypted.size() > 1 || !decrypted.get(0).equals("")) {
            for (int i = 0; i < hashes.size(); i++) {
                map.put(hashes.get(i), decrypted.get(i));
            }
            return map;
        }

        for (String hash : hashes) {
            map.put(hash, "");
        }
        return map;
    }

    private ArrayList<String> stringToArray(String data) {
        return new ArrayList<>(Arrays.asList(data.split(" ")));
    }
}
