package br.com.douglas.service.relatorio;

import org.springframework.http.MediaType;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.net.URLConnection;
import java.util.Base64;
import java.util.Optional;

public final class FileUtil {

    private FileUtil() {
    }

    public static MediaType getMediaType(String contentType) {
        MimeType mimeType = MimeTypeUtils.parseMimeType(contentType);
        return new MediaType(mimeType.getType(), mimeType.getSubtype());
    }

    public static String removeBase64Data(String base64) {
        return base64.replaceFirst("^data:[^;]*/[^;]*;base64,?", "");
    }

    public static byte[] convertFromBase64(String base64) {
        return Base64.getDecoder().decode(removeBase64Data(base64));
    }

    public static String getExtension(String file) {
        return Optional.ofNullable(file)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(file.lastIndexOf(".") + 1))
                .orElse(file);
    }

    public static String getContentType(String file) {
        String contentTypeFor = URLConnection.getFileNameMap().getContentTypeFor(file);

        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }

        return contentTypeFor;
    }
}
