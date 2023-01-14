package hastranslator.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DecodeRequestDTO {

    private ArrayList<String> hashes;
}
