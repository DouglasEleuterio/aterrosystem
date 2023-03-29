package br.com.douglas.service.relatorio;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Objects;

public record ReportData(String name, ReportType type, byte[] data) {
   
	public String getFileName() {
        if (!name.endsWith(type.getName())) {
            return name + type.getName();
        }
        return name;
    }

    public ResponseEntity<Resource> toResponseEntity() {
        try {
            var header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + getFileName());
            var resource = new InputStreamResource(new ByteArrayInputStream(data));
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.noCache())
                    .contentLength(data.length)
                    .headers(header)
                    .contentType(FileUtil.getMediaType(type().getMimeType()))
                    .body(resource);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportData that = (ReportData) o;
        return Objects.equals(name, that.name) && type == that.type && Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, type);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return getFileName();
    }
}
