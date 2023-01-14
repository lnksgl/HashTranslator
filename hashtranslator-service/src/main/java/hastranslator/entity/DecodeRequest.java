package hastranslator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecodeRequest {

    @Id
    private String id;
    private ArrayList<String> hashes;
    private ArrayList<String> decrypted;
}
