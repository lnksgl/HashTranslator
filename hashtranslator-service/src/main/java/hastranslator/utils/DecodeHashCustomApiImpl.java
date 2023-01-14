package hastranslator.utils;

import hastranslator.exception.FailedDecodeRequestException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DecodeHashCustomApiImpl implements DecodeHashCustomApi {

    public ArrayList<String> decodeHashes(ArrayList<String> hashes) throws IOException {
        Map<String, String> credentials = getCredentials();
        ArrayList<String> result = new ArrayList<>();

        hashes.forEach(hash -> {
            try {
                result.add(decodeTask(credentials, hash));
            } catch (IOException e) {
                throw new FailedDecodeRequestException();
            }
        });
        return result;
    }

    private Map<String, String> getCredentials() throws IOException {
        URL url = new URL("https://www.codepunker.com/tools/string-converter");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        Map<String, String> credentials = new HashMap<>();
        credentials.put("codpunkersess",
                parseCodpunkersess(httpConn.getHeaderField("set-cookie")));
        credentials.put("csrf", parseCSRF(httpConn));
        return credentials;
    }

    private String parseCodpunkersess(String codpunkersess) {
        int indexCodpunkersess = codpunkersess.indexOf("CODPUNKERSESS=");

        return codpunkersess.substring(indexCodpunkersess + 14, indexCodpunkersess + 46);
    }

    private String parseCSRF(HttpURLConnection httpConn) throws IOException {
        InputStream responseStream =
                httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream() : httpConn.getErrorStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(responseStream));
        String result = String.valueOf(read.lines().skip(32).findFirst());
        read.close();

        int indexCSRF = result.indexOf("CSRF_STRING = ");

        return result.substring(indexCSRF + 15, indexCSRF + 47);
    }

    public String decodeTask(Map<String, String> credentials, String hash) throws IOException {
        URL url = new URL("https://www.codepunker.com/tools/http-requests");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("cookie", "CODPUNKERSESS=" + credentials.get("codpunkersess"));
        httpConn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
        httpConn.setDoOutput(true);

        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("string=" + hash +
                "&method=unhash" +
                "&execute=executeStringConversion" +
                "&csrf=" + credentials.get("csrf"));
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        return parseResponse(httpConn);
    }

    private String parseResponse(HttpURLConnection httpConn) throws IOException {
        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner scan = new Scanner(responseStream).useDelimiter("\\A");

        if (scan.hasNext()) {
            String response = scan.next();
            if (response.indexOf("unhashed") != 0) {
                Pattern pattern = Pattern.compile("unhashed\":\".*\"},");
                Matcher matcher = pattern.matcher(response);
                matcher.find();

                return response.substring(matcher.start() + 11, (matcher.end()) - 3);
            }
        }
        return "";
    }
}
