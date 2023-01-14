package authorization.hashtranslator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class DecryptedHashDTO {

    String id;
    Map<String, String> hashes;
}
