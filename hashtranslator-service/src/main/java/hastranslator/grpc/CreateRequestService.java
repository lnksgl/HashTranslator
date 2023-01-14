package hastranslator.grpc;

import authorization.CreateRequestServiceGrpc;
import authorization.CreateRequestServiceOuterClass;
import hastranslator.service.DecodeRequestService;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.devh.boot.grpc.server.service.GrpcService;

import static lombok.AccessLevel.PRIVATE;

@GrpcService
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CreateRequestService extends CreateRequestServiceGrpc.CreateRequestServiceImplBase {

    DecodeRequestService decodeRequestService;

    @Override
    public void createRequest(CreateRequestServiceOuterClass.CreateRequest request,
                              StreamObserver<CreateRequestServiceOuterClass.CreateResponse> responseObserver) {
        String hashes = request.getHashes();
        if (!hashes.isEmpty()) {
            responseObserver.onNext(next(hashes));
        } else {
            responseObserver.onError(error());
        }
        responseObserver.onCompleted();
    }

    private CreateRequestServiceOuterClass.CreateResponse next(String hashes) {
        return CreateRequestServiceOuterClass.CreateResponse.newBuilder()
                .setId(decodeRequestService.createDecodeRequest(hashes))
                .build();
    }

    private StatusRuntimeException error() {
        return Status.INVALID_ARGUMENT
                .withDescription("Hashes.")
                .asRuntimeException();
    }
}
