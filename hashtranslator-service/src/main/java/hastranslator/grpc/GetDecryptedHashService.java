package hastranslator.grpc;

import authorization.GetDecryptedHashServiceGrpc;
import authorization.GetDecryptedHashServiceOuterClass;
import hastranslator.entity.DecodeRequest;
import hastranslator.service.DecodeRequestService;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@GrpcService
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class GetDecryptedHashService extends GetDecryptedHashServiceGrpc.GetDecryptedHashServiceImplBase {

    DecodeRequestService decodeRequestService;

    @Override
    public void getDecodeHash(GetDecryptedHashServiceOuterClass.GetDecryptedHashRequest request,
                              StreamObserver<GetDecryptedHashServiceOuterClass.GetDecryptedHashResponse> responseObserver) {
        String id = request.getId();
        if (!id.isEmpty()) {
            responseObserver.onNext(next(id));
        } else {
            responseObserver.onError(error());
        }
        responseObserver.onCompleted();
    }

    private GetDecryptedHashServiceOuterClass.GetDecryptedHashResponse next(String id) {
        DecodeRequest decodeRequest = decodeRequestService.getDecodeRequestById(id);
        return GetDecryptedHashServiceOuterClass.GetDecryptedHashResponse.newBuilder()
                .setId(decodeRequest.getId())
                .setHashes(arrayToString(decodeRequest.getHashes()))
                .setDecrypted(arrayToString(decodeRequest.getDecrypted()))
                .build();
    }

    private String arrayToString(ArrayList<String> arrayList) {
        if (nonNull(arrayList)) {
            return String.join(" ", arrayList);
        }
        return "";
    }

    private StatusRuntimeException error() {
        return Status.INVALID_ARGUMENT
                .withDescription("Id.")
                .asRuntimeException();
    }
}
