package hastranslator.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public interface DecodeHashCustomApi {

    public ArrayList<String> decodeHashes(ArrayList<String> hashes) throws IOException;
    public String decodeTask(Map<String, String> credentials, String hash) throws IOException;
}
